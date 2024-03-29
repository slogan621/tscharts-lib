/*
 * (C) Copyright Syd Logan 2018-2021
 * (C) Copyright Thousand Smiles Foundation 2018-2021
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.thousandsmiles.tscharts_lib;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import static android.content.Context.ACTIVITY_SERVICE;

public class CommonSessionSingleton {
    private static CommonSessionSingleton m_instance;
    private static Context m_ctx;
    private static String m_token = "";
    private File m_storageDir = null;
    private int m_headshotTag = 676;
    private int m_patientId;
    private int m_patientRoutingSlipId;
    private String m_photoPath = "";
    private int m_clinicId = -1;
    private int m_registrationId;
    private ArrayList<HeadshotImage> m_headshotImages = new ArrayList<HeadshotImage>();
    private ArrayList<HeadshotImage> m_headshotJobs = new ArrayList<HeadshotImage>();
    private ConcurrentHashMap<Integer, String> m_headshotIdToPath = new ConcurrentHashMap<Integer, String>();
    private ArrayList<String> m_medicationsList = new ArrayList<String>();
    private CDTCodesModelList m_cdtCodesList;
    private MedicalHistory m_patientMedicalHistory = null;
    private Vaccination m_patientVaccination = null;
    private XRay m_patientXRay = null;
    private ENTHistory m_patientENTHistory = null;
    private ENTDiagnosis m_patientENTDiagnosis = null;
    private ENTDiagnosisExtra m_patientENTDiagnosisExtra = null;
    private ENTHistoryExtra m_patientENTHistoryExtra = null;
    private ENTExam m_patientENTExam = null;
    private ENTSurgicalHistory m_patientENTSurgicalHistory = null;
    private ENTTreatment m_patientENTTreatment = null;
    private Audiogram m_patientAudiogram = null;
    private DentalTreatment m_patientDentalTreatment = null;
    private static HashMap<Integer, Boolean> m_isNewPatientMap = new HashMap<Integer, Boolean>();
    private static HashMap<Integer, Boolean> m_hasCurrentXRayMap = new HashMap<Integer, Boolean>();
    private static HashMap<Integer, JSONObject> m_clinicMap = new HashMap<Integer, JSONObject>();
    private ArrayList<String> m_covid19Types = new ArrayList<String>();
    private boolean m_isNewPatient = false;
    private boolean m_isNewVaccination = false;
    private JSONArray m_registrationSearchResults = null;
    private JSONArray m_allClinics = null;
    private HashMap<Integer, Registration> m_clinicRegistrationResults = new HashMap<Integer, Registration>();

    public void clearRegistrationSearchResultData()
    {
        m_registrationSearchResults = null;
    }

    public void setRegistrationSearchResults(JSONArray response) {
        m_registrationSearchResults = response;
    }

    public void setClinicRegistrationResults(JSONArray response) {

        for (int i = 0; i < response.length(); i++) {
            Registration r = new Registration();
            try {
                JSONObject o = response.getJSONObject(i);
                r.setClinic(o.getInt("clinic"));
                r.setId(o.getInt("id"));
                r.setDateTimeIn(o.getString("timein"));
                r.setDateTimeOut(o.getString("timeout"));
                r.setPatient(o.getInt("patient"));
                m_clinicRegistrationResults.put(r.getPatient(), r);
            } catch (JSONException e) {
                // XXX continue on
            }
        }
    }

    public void clearClinicRegistrations() {
        m_clinicRegistrationResults.clear();
    }

    public HashMap<Integer, Registration> getClinicRegistrations() {
        return m_clinicRegistrationResults;
    }

    public void setIsNewVaccination(boolean isNew) {
        m_isNewVaccination = isNew;
    }

    public boolean getIsNewVaccination() {
        return m_isNewVaccination;
    }

    public Vaccination getVaccination(int clinicid, int patientid)
    {
        boolean ret = false;
        Vaccination vacc = null;

        if (Looper.myLooper() != Looper.getMainLooper()) {
            final VaccinationREST vaccData = new VaccinationREST(getContext());
            Object lock = vaccData.getVaccinationData(clinicid, patientid);

            synchronized (lock) {
                // we loop here in case of race conditions or spurious interrupts
                while (true) {
                    try {
                        lock.wait();
                        break;
                    } catch (InterruptedException e) {
                        continue;
                    }
                }
            }

            int status = vaccData.getStatus();
            if (status == 200) {
                vacc = getPatientVaccination();
            }
        }
        return vacc;
    }

    public void createVaccination(final RESTCompletionListener listener) {
        boolean ret = false;

        Thread thread = new Thread() {
            public void run() {
                // note we use session context because this may be called after onPause()
                VaccinationREST rest = new VaccinationREST(getContext());
                rest.addListener(listener);
                Object lock;
                int status;

                getPatientVaccination().setPatient(getPatientId());
                lock = rest.createVaccination(getPatientVaccination());

                synchronized (lock) {
                    // we loop here in case of race conditions or spurious interrupts
                    while (true) {
                        try {
                            lock.wait();
                            break;
                        } catch (InterruptedException e) {
                            continue;
                        }
                    }
                }
                status = rest.getStatus();
                if (status != 200) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(), getContext().getString(R.string.msg_unable_to_save_vaccination), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    setIsNewVaccination(false);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(), getContext().getString(R.string.msg_successfully_saved_vaccination), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        };
        thread.start();
    }

    public void addCOVID19Types(JSONArray response) {
        m_covid19Types.clear();
        for (int i = 0; i < response.length(); i++) {
            try {
                String s = response.getJSONObject(i).getString("name");
                m_covid19Types.add(s);
            } catch (JSONException e) {
            }
        }
    }

    public ArrayList<String> getCOVID19TypesList()
    {
        return m_covid19Types;
    }

    public boolean getCOVID19Types() {
        boolean ret = false;

        if (m_covid19Types.size() > 0) {
            ret = true;
        } else {
            m_covid19Types.clear();
            if (Looper.myLooper() != Looper.getMainLooper()) {
                final COVIDVacREST covid19TypesData = new COVIDVacREST(getContext());
                Object lock = covid19TypesData.getCOVID19Types();

                synchronized (lock) {
                    // we loop here in case of race conditions or spurious interrupts
                    while (true) {
                        try {
                            lock.wait();
                            break;
                        } catch (InterruptedException e) {
                            continue;
                        }
                    }
                }

                int status = covid19TypesData.getStatus();
                if (status == 200) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    public void updateVaccination(final RESTCompletionListener listener)
    {
        boolean ret = false;

        Thread thread = new Thread(){
            public void run() {
                // note we use session context because this may be called after onPause()
                VaccinationREST rest = new VaccinationREST(getContext());
                rest.addListener(listener);
                Object lock;
                int status;

                lock = rest.updateVaccination(getPatientVaccination());

                synchronized (lock) {
                    // we loop here in case of race conditions or spurious interrupts
                    while (true) {
                        try {
                            lock.wait();
                            break;
                        } catch (InterruptedException e) {
                            continue;
                        }
                    }
                }
                status = rest.getStatus();
                if (status != 200) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(), getContext().getString(R.string.msg_unable_to_update_vaccination), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(), getContext().getString(R.string.msg_successfully_updated_vaccination), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        };
        thread.start();
    }

    public void setIsNewPatient(boolean isNew) {
        m_isNewPatient = isNew;
    }

    public boolean getIsNewPatient() {
        return m_isNewPatient;
    }

    private class IsNewPatientListener implements RESTCompletionListener
    {
        private int m_patientId = 0;

        public void setPatientId(int id)
        {
            m_patientId = id;
        }

        public void onSuccess(int code, String message, JSONArray a)
        {
            if (code == 200) {
                if (a.length() > 1) {
                    m_isNewPatientMap.put(m_patientId, false);
                }
                else {
                    m_isNewPatientMap.put(m_patientId, true);
                }
            }
        }

        public void onSuccess(int code, String message, JSONObject a)
        {
        }

        public void onSuccess(int code, String message)
        {
        }

        public void onFail(int code, String message)
        {
        }
    }

    public void setPatientRoutingSlipId(int id)
    {
        m_patientRoutingSlipId = id;
    }

    public boolean isNewPatient(final int patientId) {
        boolean ret = false;
        try {
            ret = m_isNewPatientMap.get(patientId);
        }
        catch(Exception e) {
            Thread thread = new Thread() {
                public void run() {
                // note we use session context because this may be called after onPause()
                RoutingSlipREST rest = new RoutingSlipREST(getContext());
                IsNewPatientListener listener = new IsNewPatientListener();

                listener.setPatientId(patientId);

                rest.addListener(listener);
                Object lock;

                lock = rest.getAllRoutingSlipsForPatient(patientId);

                synchronized (lock) {
                    // we loop here in case of race conditions or spurious interrupts
                    while (true) {
                        try {
                            lock.wait();
                            break;
                        } catch (InterruptedException e) {
                            continue;
                        }
                    }
                }
                }
            };
            thread.start();
        }
        return ret;
    }

    private class HasCurrentXRayListener implements RESTCompletionListener
    {
        private int m_patientId = 0;
        private int m_days = 365;     // within last year

        public void setPatientId(int id)
        {
            m_patientId = id;
        }
        public void setDays(int days)
        {
            m_days = days;
        }

        public void onSuccess(int code, String message, JSONArray a)
        {
            if (code == 200) {
                if (a.length() > 0) {
                    for (int i = 0; i < a.length(); i++) {
                        Date date;

                        Calendar today = new GregorianCalendar(TimeZone.getTimeZone("GMT"));

                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        sdf.setLenient(false);
                        try {
                            date = sdf.parse(a.getJSONObject(i).getString("time"), new ParsePosition(0));
                            if (date != null) {
                                Calendar d = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
                                d.setTime(date);
                                long diff = today.getTime().getTime() - d.getTime().getTime();
                                diff = diff / (1000 * 60 * 60 * 24);
                                if (diff < m_days) {
                                    m_hasCurrentXRayMap.put(m_patientId, true);
                                    break;
                                }
                            }
                        } catch (JSONException e) {

                        }
                    }
                }
            }
        }

        public void onSuccess(int code, String message, JSONObject a)
        {
        }

        public void onSuccess(int code, String message)
        {
        }

        public void onFail(int code, String message)
        {
        }
    }

    public boolean hasCurrentXRay(final int patientId, final int days) {
        boolean ret = false;
        try {
            ret = m_hasCurrentXRayMap.get(patientId);
        }
        catch(Exception e) {
            Thread thread = new Thread() {
                public void run() {
                    // note we use session context because this may be called after onPause()
                    XRayREST rest = new XRayREST(getContext());
                    HasCurrentXRayListener listener = new HasCurrentXRayListener();

                    listener.setPatientId(patientId);
                    listener.setDays(days);

                    rest.addListener(listener);
                    Object lock;

                    lock = rest.getAllXRaysForPatient(patientId);

                    synchronized (lock) {
                        // we loop here in case of race conditions or spurious interrupts
                        while (true) {
                            try {
                                lock.wait();
                                break;
                            } catch (InterruptedException e) {
                                continue;
                            }
                        }
                    }
                }
            };
            thread.start();
        }
        return ret;
    }

    public int getPatientRoutingSlipId()
    {
        return m_patientRoutingSlipId;
    }

    /** vaccinations */

    public void resetPatientVaccination() {
        m_patientVaccination = null;
    }

    public void setVaccinationId(int id) {
        m_patientVaccination.setId(id);
    }

    public void setPatientVaccination(JSONObject o)
    {
        if (m_patientVaccination == null) {
            m_patientVaccination = new Vaccination();
        }
        m_patientVaccination.fromJSONObject(o);
    }

    public void updatePatientVaccination(Vaccination vaccination) {
        m_patientVaccination = vaccination;
    }

    public Vaccination getPatientVaccination()
    {
        return m_patientVaccination;
    }

    public Vaccination getNewPatientVaccination()
    {
        if (m_patientVaccination == null) {
            m_patientVaccination = new Vaccination();
            m_patientVaccination.setClinic(getClinicId());
            m_patientVaccination.setPatient(getPatientId());
        }
        return m_patientVaccination;
    }

    /** end vaccinations */

    public void resetPatientMedicalHistory() {
        m_patientMedicalHistory = null;
    }

    public void setMedicalHistoryId(int id) {
        m_patientMedicalHistory.setId(id);
    }

    public void setPatientMedicalHistory(JSONObject o)
    {
        if (m_patientMedicalHistory == null) {
            m_patientMedicalHistory = new MedicalHistory();
        }
        m_patientMedicalHistory.fromJSONObject(o);
    }

    public void updatePatientMedicalHistory(MedicalHistory mh) {
        m_patientMedicalHistory = mh;
    }

    public MedicalHistory getPatientMedicalHistory()
    {
        return m_patientMedicalHistory;
    }

    public MedicalHistory getNewPatientMedicalHistory()
    {
        if (m_patientMedicalHistory == null) {
            m_patientMedicalHistory = new MedicalHistory();
        }
        return m_patientMedicalHistory;
    }

    public void setPatientXRay(JSONObject o)
    {
        if (m_patientXRay == null) {
            m_patientXRay = new XRay();
        }
        m_patientXRay.fromJSONObject(o);
    }

    public void updatePatientXRay(XRay xray) {
        m_patientXRay = xray;
    }

    public XRay getPatientXray()
    {
        return m_patientXRay;
    }

    public XRay getNewPatientXray()
    {
        if (m_patientXRay == null) {
            m_patientXRay = new XRay();
        }
        return m_patientXRay;
    }

    public void setPatientENTHistory(JSONObject o)
    {
        if (m_patientENTHistory == null) {
            m_patientENTHistory = new ENTHistory();
        }
        m_patientENTHistory.fromJSONObject(o);
    }

    public void updatePatientENTHistory(ENTHistory entHistory) {
        m_patientENTHistory = entHistory;
    }

    public ENTHistory getPatientENTHistory()
    {
        return m_patientENTHistory;
    }

    public ENTHistory getNewPatientENTHistory()
    {
        if (m_patientENTHistory == null) {
            m_patientENTHistory = new ENTHistory();
        }
        return m_patientENTHistory;
    }

    public void setPatientENTDiagnosis(JSONObject o)
    {
        if (m_patientENTDiagnosis == null) {
            m_patientENTDiagnosis = new ENTDiagnosis();
        }
        m_patientENTDiagnosis.fromJSONObject(o);
    }

    public void updatePatientENTDiagnosis(ENTDiagnosis entDiagnosis) {
        m_patientENTDiagnosis = entDiagnosis;
    }

    public ENTDiagnosis getPatientENTDiagnosis()
    {
        return m_patientENTDiagnosis;
    }

    public ENTDiagnosis getNewPatientENTDiagnosis()
    {
        if (m_patientENTDiagnosis == null) {
            m_patientENTDiagnosis = new ENTDiagnosis();
        }
        return m_patientENTDiagnosis;
    }

    public void setPatientENTDiagnosisExtra(JSONObject o)
    {
        if (m_patientENTDiagnosisExtra == null) {
            m_patientENTDiagnosisExtra = new ENTDiagnosisExtra();
        }
        m_patientENTDiagnosisExtra.fromJSONObject(o);
    }

    public void updatePatientENTDiagnosisExtra(ENTDiagnosisExtra entDiagnosisExtra) {
        m_patientENTDiagnosisExtra = entDiagnosisExtra;
    }

    public ENTDiagnosisExtra getPatientENTDiagnosisExtra()
    {
        return m_patientENTDiagnosisExtra;
    }

    public ENTDiagnosisExtra getNewPatientENTDiagnosisExtra()
    {
        if (m_patientENTDiagnosisExtra == null) {
            m_patientENTDiagnosisExtra = new ENTDiagnosisExtra();
        }
        return m_patientENTDiagnosisExtra;
    }

    public void setPatientENTHistoryExtra(JSONObject o)
    {
        if (m_patientENTHistoryExtra == null) {
            m_patientENTHistoryExtra = new ENTHistoryExtra();
        }
        m_patientENTHistoryExtra.fromJSONObject(o);
    }

    public void updatePatientENTHistoryExtra(ENTHistoryExtra entHistoryExtra) {
        m_patientENTHistoryExtra = entHistoryExtra;
    }

    public ENTHistoryExtra getPatientENTHistoryExtra()
    {
        return m_patientENTHistoryExtra;
    }

    public ENTHistoryExtra getNewPatientENTHistoryExtra()
    {
        if (m_patientENTHistoryExtra == null) {
            m_patientENTHistoryExtra = new ENTHistoryExtra();
        }
        return m_patientENTHistoryExtra;
    }

    public void setPatientENTTreatment(JSONObject o)
    {
        if (m_patientENTTreatment == null) {
            m_patientENTTreatment = new ENTTreatment();
        }
        m_patientENTTreatment.fromJSONObject(o);
    }

    public void updatePatientENTTreatment(ENTTreatment treatment) {
        m_patientENTTreatment = treatment;
    }

    public ENTTreatment getPatientENTTreatment()
    {
        return m_patientENTTreatment;
    }

    public ENTTreatment getNewPatientENTTreatment()
    {
        if (m_patientENTTreatment == null) {
            m_patientENTTreatment = new ENTTreatment();
        }
        return m_patientENTTreatment;
    }

    public void setPatientENTExam(JSONObject o)
    {
        if (m_patientENTExam == null) {
            m_patientENTExam = new ENTExam();
        }
        m_patientENTExam.fromJSONObject(o);
    }

    public void updatePatientENTExam(ENTExam entExam) {
        m_patientENTExam = entExam;
    }

    public ENTExam getPatientENTExam()
    {
        return m_patientENTExam;
    }

    public ENTExam getNewPatientENTExam()
    {
        if (m_patientENTExam == null) {
            m_patientENTExam = new ENTExam();
        }
        return m_patientENTExam;
    }

    public void setPatientENTSurgicalHistory(JSONObject o)
    {
        if (m_patientENTSurgicalHistory == null) {
            m_patientENTSurgicalHistory = new ENTSurgicalHistory();
        }
        m_patientENTSurgicalHistory.fromJSONObject(o);
    }

    public void updatePatientENTSurgicalHistory(ENTSurgicalHistory entSurgicalHistory) {
        m_patientENTSurgicalHistory = entSurgicalHistory;
    }

    public ENTSurgicalHistory getPatientENTSurgicalHistory()
    {
        return m_patientENTSurgicalHistory;
    }

    public ENTSurgicalHistory getNewPatientENTSurgicalHistory()
    {
        if (m_patientENTSurgicalHistory == null) {
            m_patientENTSurgicalHistory = new ENTSurgicalHistory();
        }
        return m_patientENTSurgicalHistory;
    }

    public void setPatientAudiogram(JSONObject o)
    {
        if (m_patientAudiogram == null) {
            m_patientAudiogram = new Audiogram();
        }
        m_patientAudiogram.fromJSONObject(o);
    }

    public void updatePatientAudiogram(Audiogram audiogram) {
        m_patientAudiogram = audiogram;
    }

    public Audiogram getPatientAudiogram()
    {
        return m_patientAudiogram;
    }

    public Audiogram getNewPatientAudiogram()
    {
        if (m_patientAudiogram == null) {
            m_patientAudiogram = new Audiogram();
        }
        return m_patientAudiogram;
    }

    public void setPatientDentalTreatment(JSONObject o)
    {
        if (m_patientDentalTreatment == null) {
            m_patientDentalTreatment = new DentalTreatment();
        }
        m_patientDentalTreatment.fromJSONObject(o);
    }

    public void updatePatientDentalTreatment(DentalTreatment dentalTreatment) {
        m_patientDentalTreatment = dentalTreatment;
    }

    public DentalTreatment getPatientDentalTreatment()
    {
        return m_patientDentalTreatment;
    }

    public DentalTreatment getNewPatientDentalTreatment()
    {
        if (m_patientDentalTreatment == null) {
            m_patientDentalTreatment = new DentalTreatment();
        }
        return m_patientDentalTreatment;
    }

    public void setClinicId(int id) {
        m_clinicId = id;
    }

    public void addHeadshotJob(HeadshotImage hs)
    {
        m_headshotJobs.add(hs);
    }

    public ActivityManager.MemoryInfo getAvailableMemory() {
        ActivityManager.MemoryInfo memoryInfo = null;
        if (m_ctx != null) {
            ActivityManager activityManager = (ActivityManager) m_ctx.getSystemService(ACTIVITY_SERVICE);
            memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
        }
        return memoryInfo;
    }

    public void startNextHeadshotJob()
    {
        ActivityManager.MemoryInfo memoryInfo = getAvailableMemory();

        if (memoryInfo != null) {
            if (!memoryInfo.lowMemory && ((float) memoryInfo.availMem / memoryInfo.totalMem) > 0.2) {
                HeadshotImage hs;
                if (m_headshotJobs.size() > 0) {
                    hs = m_headshotJobs.remove(0);
                    hs.start();
                }
            } else {
                m_headshotJobs.clear();
            }
        }
    }

    public void clearHeadShotCache() {
        m_headshotIdToPath.clear();
    }

    public void addHeadShotPath(int id, String path) {
        m_headshotIdToPath.put(id, path);
    }

    public void addHeadshotImage(HeadshotImage o)
    {
        m_headshotImages.add(o);
    }

    public void cancelHeadshotImages()
    {
        m_headshotJobs.clear();
        for (int i = 0; i < m_headshotImages.size(); i++) {
            m_headshotImages.get(i).cancelPendingRequest(CommonSessionSingleton.getInstance().getHeadshotTag());
        }
        m_headshotImages.clear();
    }

    public String getPhotoPath() {
        return m_photoPath;
    }

    public void setPhotoPath(String path) {
        m_photoPath = path;
    }

    public void createImage(final int clinic, final int patient, final String imageType, final RESTCompletionListener listener) {

        Thread thread = new Thread() {
            public void run() {
                // note we use session context because this may be called after onPause()
                ImageREST rest = new ImageREST(getContext());
                rest.addListener(listener);
                Object lock;

                File file = new File(getPhotoPath());

                lock = rest.createImage(file, clinic, patient, imageType);

                synchronized (lock) {
                    // we loop here in case of race conditions or spurious interrupts
                    while (true) {
                        try {
                            lock.wait();
                            break;
                        } catch (InterruptedException e) {
                            continue;
                        }
                    }
                }
            }
        };
        thread.start();
    }

    public void removeHeadShotPath(int id) {
        m_headshotIdToPath.remove(id);
    }

    public String getHeadShotPath(int id) {
        String ret = null;

        try {
            ret = m_headshotIdToPath.get(id);
        } catch(Exception e) {
        }
        return ret;
    }

    private class GetClinicByIdListener implements RESTCompletionListener {
        private int m_clinicId;

        public void setClinicId(int id) {m_clinicId = id;}

        public void onSuccess(int code, String message, JSONArray a) {
        }

        public void onSuccess(int code, String message, JSONObject a) {
           m_clinicMap.put(m_clinicId, a);
        }

        public void onSuccess(int code, String message) {
        }

        public void onFail(int code, String message) {
        }
    }

    public JSONObject getClinicById(final int id) {

        JSONObject ret = null;

        try {
            ret = m_clinicMap.get(id);
        }
        catch(Exception e) {
        }

        if (ret == null) {
            final Thread thread = new Thread() {
                public void run() {
                    final ClinicREST clinicREST = new ClinicREST(getContext());
                    final GetClinicByIdListener listener = new GetClinicByIdListener();

                    listener.setClinicId(id);
                    clinicREST.addListener(listener);
                    final Object lock;

                    lock = clinicREST.getClinicById(id);
                    synchronized (lock) {
                        // we loop here in case of race conditions or spurious interrupts
                        while (true) {
                            try {
                                lock.wait();
                                break;
                            } catch (InterruptedException e) {
                                continue;
                            }
                        }
                    }
                }
            };
            thread.start();
        }
        return ret;
    }

    private class GetAllClinicsListener implements RESTCompletionListener {

        public void onSuccess(int code, String message, JSONArray a) {
            m_allClinics = a;
        }

        public void onSuccess(int code, String message, JSONObject a) {
        }

        public void onSuccess(int code, String message) {
        }

        public void onFail(int code, String message) {
        }
    }

    public JSONArray getAllClinics() {

        JSONArray ret = null;
        if (m_allClinics != null && m_allClinics.length() > 0) {
            return m_allClinics;
        }

        final Thread thread = new Thread() {
            public void run() {
                final ClinicREST clinicREST = new ClinicREST(getContext());
                final GetAllClinicsListener listener = new GetAllClinicsListener();
                clinicREST.addListener(listener);
                final Object lock;

                lock = clinicREST.getAllClinics();
                synchronized (lock) {
                    // we loop here in case of race conditions or spurious interrupts
                    while (true) {
                        try {
                            lock.wait();
                            break;
                        } catch (InterruptedException e) {
                            continue;
                        }
                    }
                }
            }
        };
        thread.start();
        ret = m_allClinics;
        return ret;
    }

    public int getClinicId() {
        return m_clinicId;
    }

    public int getPatientId()
    {
        return m_patientId;
    }

    public void setPatientId(int id)
    {
        m_patientId = id;
    }

    public int getActivePatientId()
    {
        return getPatientId();
    }

    public int getHeadshotTag()
    {
        return m_headshotTag;
    }

    public void setToken(String token) {
        m_token = String.format("Token %s", token);
    }

    public String getToken() {
        return m_token;
    }

    public void setStorageDir(Activity activity) {
        m_storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    static void traverseDelete(File dir) {
        if (dir != null && dir.exists()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; ++i) {
                File file = files[i];
                if (file.isDirectory()) {
                    traverseDelete(file);
                } else {
                    file.delete();
                }
            }
        }
    }

    public void clearStorageDir() {
        traverseDelete(getStorageDir());
    }

    public File getStorageDir() {
        return m_storageDir;
    }

    public void setMedicationsList(JSONArray a)
    {
        m_medicationsList.clear();
        for (int i = 0; i < a.length(); i++) {
            try {
                m_medicationsList.add(a.getString(i));
            } catch (JSONException e) {
            }
        }
    }


    public ArrayList<String> getMedicationsList()
    {
        return m_medicationsList;
    }

    public String[] getMedicationsListStringArray()
    {
        return m_medicationsList.toArray(new String[0]);
    }

    public void setCDTCodesList(JSONArray a)
    {
        m_cdtCodesList = CDTCodesModelList.getInstance();
        m_cdtCodesList.setModelData(a);
    }

    public ArrayList<String> getCDTCodesList()
    {
        return m_cdtCodesList.getReprStrings();
    }

    public String[] getCDTCodesListStringArray()
    {
        if (m_cdtCodesList != null) {
            return m_cdtCodesList.getReprStringArray();
        }
        String [] ret = new String[] {};
        return ret;
    }

    public CDTCodesModelList getCDTCodeModelList() {
        return m_cdtCodesList;
    }

    public static CommonSessionSingleton getInstance() {
        if (m_instance == null) {
            m_instance = new CommonSessionSingleton();
        }
        return m_instance;
    }

    public void setContext(Context ctx) {
        m_ctx = ctx;
    }
    public Context getContext() {
        return m_ctx;
    }


    public void setRegistrationId(JSONObject response) {
        try {
            String m;
            m_registrationId = response.getInt("id");
            m = String.format("setRegistrationId: %d", m_registrationId);
            Log.e("setRegistrationId", m);
        } catch (JSONException e) {
            Log.e("setRegistrationId", "FAILED");
        }
    }

    public int getRegistrationId() {
        return m_registrationId;
    }

    public String militaryToConventionalDateString(String s)
    {
        boolean ret = true;

        if (s.length() != "MMMddYYYY".length()) {
            ret = false;
        } else {
            String m = s.substring(2, 5);
            String d = s.substring(0, 2);
            String y = s.substring(5, 9);
            String monthStr = "";

            // no question this is not super efficient, but it is robust to locale changes and
            // does not require a change as new languages are supported.

            if (m.equalsIgnoreCase(getContext().getResources().getString(R.string.January).substring(0, 3))) {
                monthStr = "01";
            } else if (m.equalsIgnoreCase(getContext().getResources().getString(R.string.February).substring(0, 3))) {
                monthStr = "02";
            } else if (m.equalsIgnoreCase(getContext().getResources().getString(R.string.March).substring(0, 3))) {
                monthStr = "03";
            } else if (m.equalsIgnoreCase(getContext().getResources().getString(R.string.April).substring(0, 3))) {
                monthStr = "04";
            } else if (m.equalsIgnoreCase(getContext().getResources().getString(R.string.May).substring(0, 3))) {
                monthStr = "05";
            } else if (m.equalsIgnoreCase(getContext().getResources().getString(R.string.June).substring(0, 3))) {
                monthStr = "06";
            } else if (m.equalsIgnoreCase(getContext().getResources().getString(R.string.July).substring(0, 3))) {
                monthStr = "07";
            } else if (m.equalsIgnoreCase(getContext().getResources().getString(R.string.August).substring(0, 3))) {
                monthStr = "08";
            } else if (m.equalsIgnoreCase(getContext().getResources().getString(R.string.September).substring(0, 3))) {
                monthStr = "09";
            } else if (m.equalsIgnoreCase(getContext().getResources().getString(R.string.October).substring(0, 3))) {
                monthStr = "10";
            } else if (m.equalsIgnoreCase(getContext().getResources().getString(R.string.November).substring(0, 3))) {
                monthStr = "11";
            } else if (m.equalsIgnoreCase(getContext().getResources().getString(R.string.December).substring(0, 3))) {
                monthStr = "12";
            } else {
                ret = false;
            }

            if (ret == true) {
                int val;

                try {
                    val = Integer.parseInt(d);
                } catch (NumberFormatException e) {
                    ret = false;
                }
                try {
                    val = Integer.parseInt(y);
                } catch (NumberFormatException e) {
                    ret = false;
                }
            }
            if (ret == true) {

                // passes the military test, so convert to something isDateString can recognize

                s = String.format("%s/%s/%s", monthStr, d, y);
            }
        }
        return s;
    }

    public boolean isValidPatientBirthDate(String dateStr)
    {
        final Date date = CommonSessionSingleton.getInstance().isDateString(dateStr);
        boolean ret = false;
        Date today;

        /*
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        sdf.setLenient(true);
        date = sdf.parse(dateStr, new ParsePosition(0));

        if (date == null) {
            sdf = new SimpleDateFormat("MM/dd/yyyy");
            sdf.setLenient(true);
            date = sdf.parse(dateStr, new ParsePosition(0));
        }
        */

        if (date != null) {
            today = new Date();
            if (date.compareTo(today) < 0) {
                ret = true;
            }
        }
        return ret;
    }

    public Date isDateString(String s) {

        s = militaryToConventionalDateString(s); // convert if necessary

        // supports variations where year is yyyy or yy day is dd and month is MM

        Date ret = null;

        String[] formats = {
                "MM-dd-yy",
                "MM/dd/yy",
                "MM dd yy",
                "MM-dd-yyyy",
                "MM/dd/yyyy",
                "MM dd yyyy"
        };

        for (int i = 0; i < formats.length; i++){
            try {
                DateFormat df = new SimpleDateFormat(formats[i], Locale.ENGLISH);
                Date date = df.parse(s);
                ret = date;
                break;
            } catch (ParseException pe) {
            }
        }
        return ret;
    }
}
