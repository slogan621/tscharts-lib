/*
 * (C) Copyright Syd Logan 2017-2020
 * (C) Copyright Thousand Smiles Foundation 2017-2020
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

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PatientREST extends RESTful {
    private final Object m_lock = new Object();

    private class ResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {
            synchronized (m_lock) {
                CommonSessionSingleton sess = CommonSessionSingleton.getInstance();
                setStatus(200);
                onSuccess(200, "", response);
                m_lock.notify();
            }
        }
    }

    private class PostResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {

            synchronized (m_lock) {
                CommonSessionSingleton sess = CommonSessionSingleton.getInstance();
                try {
                    int id = response.getInt("id");
                    setStatus(200);
                    onSuccess(200, "", response);
                } catch (JSONException e) {
                    setStatus(500);
                    onFail(500, "");
                }
                m_lock.notify();
            }
        }
    }

    private class ArrayResponseListener implements Response.Listener<JSONArray> {

        @Override
        public void onResponse(JSONArray response) {

            synchronized (m_lock) {
                CommonSessionSingleton sess = CommonSessionSingleton.getInstance();
                setStatus(200);
                onSuccess(200, "", response);
                m_lock.notify();
            }
        }
    }

    private class PutResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {

            synchronized (m_lock) {
                setStatus(200);
                onSuccess(200, "");
                m_lock.notify();
            }
        }
    }

    private class ErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {

            synchronized (m_lock) {
                if (error.networkResponse == null) {
                    if (error.getCause() instanceof java.net.ConnectException || error.getCause() instanceof java.net.UnknownHostException) {
                        setStatus(101);
                        onFail(101, error.getMessage());
                    } else {
                        setStatus(-1);
                        onFail(-1, error.getMessage());
                    }
                } else {
                    setStatus(error.networkResponse.statusCode);
                    onFail(error.networkResponse.statusCode, error.networkResponse.toString());
                }
                m_lock.notify();
            }
        }
    }

    public class AuthJSONObjectRequest extends JsonObjectRequest {
        public AuthJSONObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener listener, ErrorListener errorListener) {
            super(method, url, jsonRequest, listener, errorListener);
        }

        @Override
        public Map getHeaders() throws AuthFailureError {
            Map headers = new HashMap();
            headers.put("Authorization", CommonSessionSingleton.getInstance().getToken());
            return headers;
        }
    }

    public class AuthJSONArrayRequest extends JsonArrayRequest {

        public AuthJSONArrayRequest(String url, JSONArray jsonRequest,
                                    Response.Listener<JSONArray> listener, ErrorListener errorListener) {
            super(url, listener, errorListener);
        }

        public AuthJSONArrayRequest(String url, Response.Listener<JSONArray> listener,
                                    Response.ErrorListener errorListener, String username, String password) {
            super(url, listener, errorListener);

        }

        private Map<String, String> headers = new HashMap<String, String>();

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            //return headers;
            Map headers = new HashMap();
            headers.put("Authorization", CommonSessionSingleton.getInstance().getToken());
            return headers;
        }

    }

    public PatientREST(Context context) {
        setContext(context);
    }

    public Object getAllPatientData() {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/patient/", getProtocol(), getIP(), getPort());

        AuthJSONArrayRequest request = new AuthJSONArrayRequest(url, null, new ArrayResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

        return m_lock;
    }

    public Object createPatient(PatientData pd) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        JSONObject data = pd.toJSONObject(getContext());

        String url = String.format("%s://%s:%s/tscharts/v1/patient/", getProtocol(), getIP(), getPort());

        AuthJSONObjectRequest request = new AuthJSONObjectRequest(Request.Method.POST, url, data, new PostResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    public Object getPatientData(int patientid) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/patient/%d/", getProtocol(), getIP(), getPort(), patientid);

        AuthJSONObjectRequest request = new AuthJSONObjectRequest(Request.Method.GET, url, null, new ResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    public Object findPatientsByDOB(Date d) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String url = String.format("%s://%s:%s/tscharts/v1/patient?dob=%s/%s/%s", getProtocol(), getIP(), getPort(),
                month, day, year);

        AuthJSONArrayRequest request = new AuthJSONArrayRequest(url, null, new ArrayResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

        return m_lock;
    }

    public Object findPatientsByDOB(Date d, int clinicId, int categoryId) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String url = String.format("%s://%s:%s/tscharts/v1/patient?dob=%s/%s/%s&clinic=%d&category=%d", getProtocol(), getIP(), getPort(),
                month, day, year, clinicId, categoryId);

        AuthJSONArrayRequest request = new AuthJSONArrayRequest(url, null, new ArrayResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

        return m_lock;
    }

    public Object findPatientsByName(String name) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/patient?name=%s", getProtocol(), getIP(), getPort(), name);

        AuthJSONArrayRequest request = new AuthJSONArrayRequest(url, null, new ArrayResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

        return m_lock;
    }

    public Object findPatientsByName(String name, int clinicId, int categoryId) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/patient?name=%s&clinic=%d&category=%d", getProtocol(), getIP(),
                getPort(), name, clinicId, categoryId);

        AuthJSONArrayRequest request = new AuthJSONArrayRequest(url, null, new ArrayResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

        return m_lock;
    }

    public Object findPatientsByClinic(int clinicId) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/patient?clinic=%d", getProtocol(), getIP(),
                getPort(), clinicId);

        AuthJSONArrayRequest request = new AuthJSONArrayRequest(url, null, new ArrayResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

        return m_lock;
    }

    public Object findPatientsByNameAndClinicId(String name, int clinicId) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/patient?name=%s&clinic=%d", getProtocol(), getIP(), getPort(), name, clinicId);

        AuthJSONArrayRequest request = new AuthJSONArrayRequest(url, null, new ArrayResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

        return m_lock;
    }

    public Object findPatientsByNameAndClinicId(String name, int clinicId, int categoryId) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/patient?name=%s&clinic=%d&category=%d", getProtocol(), getIP(), getPort(), name,
                clinicId, categoryId);

        AuthJSONArrayRequest request = new AuthJSONArrayRequest(url, null, new ArrayResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

        return m_lock;
    }

    public Object updatePatient(Context ctx, PatientData pd) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        JSONObject data = pd.toJSONObject(ctx);

        String url = String.format("%s://%s:%s/tscharts/v1/patient/%d/", getProtocol(), getIP(), getPort(), pd.getId());

        AuthJSONObjectRequest request = new AuthJSONObjectRequest(Request.Method.PUT, url, data, new PutResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    public Object updatePatientOldId(int patientId, int oldId) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        JSONObject data = new JSONObject();

        try {
            data.put("oldid", oldId);
        } catch (Exception e) {
            return null;
        }

        String url = String.format("%s://%s:%s/tscharts/v1/patient/%d/", getProtocol(), getIP(), getPort(), patientId);

        AuthJSONObjectRequest request = new AuthJSONObjectRequest(Request.Method.PUT, url, data, new PutResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

        return m_lock;
    }
}
