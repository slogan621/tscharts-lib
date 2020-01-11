/*
 * (C) Copyright Syd Logan 2018-2019
 * (C) Copyright Thousand Smiles Foundation 2018-2019
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
    private ArrayList<HeadshotImage> m_headshotImages = new ArrayList<HeadshotImage>();
    private ArrayList<HeadshotImage> m_headshotJobs = new ArrayList<HeadshotImage>();
    private ConcurrentHashMap<Integer, String> m_headshotIdToPath = new ConcurrentHashMap<Integer, String>();
    private ArrayList<String> m_medicationsList = new ArrayList<String>();
    private MedicalHistory m_patientMedicalHistory = null;
    private XRay m_patientXRay = null;
    private ENTHistory m_patientENTHistory = null;
    private ENTDiagnosis m_patientENTDiagnosis = null;
    private ENTDiagnosisExtra m_patientENTDiagnosisExtra = null;
    private ENTHistoryExtra m_patientENTHistoryExtra = null;
    private ENTExam m_patientENTExam = null;
    private ENTSurgicalHistory m_patientENTSurgicalHistory = null;
    private ENTTreatment m_patientENTTreatment = null;
    private static HashMap<Integer, Boolean> m_isNewPatientMap = new HashMap<Integer, Boolean>();
    private static HashMap<Integer, Boolean> m_hasCurrentXRayMap = new HashMap<Integer, Boolean>();
    private static HashMap<Integer, JSONObject> m_clinicMap = new HashMap<Integer, JSONObject>();

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
            if (!memoryInfo.lowMemory) {
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

    public void createHeadshot(final RESTCompletionListener listener) {

        Thread thread = new Thread() {
            public void run() {
                // note we use session context because this may be called after onPause()
                ImageREST rest = new ImageREST(getContext());
                rest.addListener(listener);
                Object lock;
                int status;

                File file = new File(getPhotoPath());

                lock = rest.createImage(file);

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
                            //Toast.makeText(getContext(), getContext().getString(R.string.msg_unable_to_save_headshot_photo), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            //Toast.makeText(getContext(), getContext().getString(R.string.msg_successfully_saved_headshot_photo), Toast.LENGTH_LONG).show();
                        }
                    });
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
}
