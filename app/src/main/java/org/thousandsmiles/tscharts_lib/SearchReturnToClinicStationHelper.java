/*
 * (C) Copyright Syd Logan 2019
 * (C) Copyright Thousand Smiles Foundation 2019
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

/*
   This class is used to find matching return to clinic station records. A registered callback is invoked
   with matches on completion, passing back the JSON Array object returned by the REST call. Teh listener
   is one shot, and is removed after then callback has been issued.
 */

package org.thousandsmiles.tscharts_lib;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchReturnToClinicStationHelper extends AsyncTask<Object, Object, Object> {
    private Context m_ctx;

    private int m_requestingStation = -1;
    private String m_state = null;
    private int m_clinic = -1;
    private int m_patient = -1;
    private ArrayList<SearchReturnToClinicStationListener> m_listeners = new ArrayList<SearchReturnToClinicStationListener>();

    public void addListener(SearchReturnToClinicStationListener listener)
    {
       m_listeners.add(listener);
    }

    private void notifyListeners(JSONArray result) {
        SearchReturnToClinicStationListener l;

        for (int i = 0; i < m_listeners.size(); i++) {
            l = m_listeners.get(i);
            l.onCompletion(result, true);
            m_listeners.remove(l);
        }
    }

    public void setRequestingStation(int id) {
        m_requestingStation = id;
    }

    public void setClinic(int id) {
        m_clinic = id;
    }

    public void setContext(Context ctx) {
        m_ctx = ctx;
    }

    public void setPatient(int id) {
        m_patient = id;
    }

    public void setState(String state) {
        m_state = state;
    }

    private boolean m_success = false;
    private JSONArray m_ret = null;
    private String m_msg = "";

    private class GetReturnToClinicStationHandler implements RESTCompletionListener {
        public void onFail(int code, String msg)
        {
            m_success = false;
            m_msg = msg;
        }

        public void onSuccess(int code, String msg)
        {
        }

        public void onSuccess(int code, String msg, JSONObject o)
        {
        }

        public void onSuccess(int code, String msg, JSONArray a)
        {
            m_ret = a;
            m_success = true;
            notifyListeners(a);
        }
    }

    @Override
    protected String doInBackground(Object... params) {
        run();
        return "";
    }

    private void run()
    {
        Object lock;

        final ReturnToClinicStationREST rtcsREST = new ReturnToClinicStationREST(m_ctx);
        rtcsREST.addListener(new GetReturnToClinicStationHandler());
        lock = rtcsREST.getReturnToClinicStationRequesting(m_clinic, m_patient, m_requestingStation, m_state);

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

    // This is called from background thread but runs in UI
    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
        // Do things like update the progress bar
    }

    // This runs in UI when background thread finishes
    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);

        // Do things like hide the progress bar or change a TextView
    }
}