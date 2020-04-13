/*
 * (C) Copyright Syd Logan 2020
 * (C) Copyright Thousand Smiles Foundation 2020
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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AudiogramREST extends RESTful {
    private final Object m_lock = new Object();

    private class CreateOrUpdateAudiogramResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {
            synchronized (m_lock) {
                setStatus(200);
                onSuccess(200, "", response);
                m_lock.notify();
            }
        }
    }

    private class GetAudiogramResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {
            synchronized (m_lock) {
                setStatus(200);
                onSuccess(200, "", response);
                CommonSessionSingleton sess = CommonSessionSingleton.getInstance();
                sess.setPatientAudiogram(response);
                m_lock.notify();
            }
        }
    }

    private class GetAudiogramArraySingleResponseListener implements Response.Listener<JSONArray> {

        @Override
        public void onResponse(JSONArray response) {
            synchronized (m_lock) {
                if (response.length() != 1) {
                    setStatus(500);
                    onFail(500, "Request for single audiogram returned multiple items");
                } else {
                    setStatus(200);
                    CommonSessionSingleton sess = CommonSessionSingleton.getInstance();
                    try {
                        sess.setPatientAudiogram(response.getJSONObject(0));
                    } catch (Exception e) {
                    }
                    onSuccess(200, "", response);
                }
                m_lock.notify();
            }
        }
    }

    private class GetAudiogramArrayResponseListener implements Response.Listener<JSONArray> {

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

    private class ErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {

            synchronized (m_lock) {
                int code;
                if (error.networkResponse == null) {
                    if (error.getCause() instanceof java.net.ConnectException || error.getCause() instanceof  java.net.UnknownHostException) {
                        code = 101;
                    } else {
                        code = -1;
                    }
                } else {
                    code = error.networkResponse.statusCode;
                }
                setStatus(code);
                onFail(code, "");
                m_lock.notify();
            }
        }
    }

    public class AuthJSONObjectRequest extends JsonObjectRequest
    {
        public AuthJSONObjectRequest(int method, String url, JSONObject jsonRequest,Response.Listener listener, ErrorListener errorListener)
        {
            super(method, url, jsonRequest, listener, errorListener);
        }

        @Override
        public Map getHeaders() throws AuthFailureError {
            Map headers = new HashMap();
            headers.put("Authorization", CommonSessionSingleton.getInstance().getToken());
            return headers;
        }
    }

    public class AuthJSONArrayRequest extends JsonArrayRequest{

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

    public AudiogramREST(Context context) {
        setContext(context);
    }

    public Object getAudiogram(int clinicId, int patientId) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/audiogram/?patient=%d&clinic=%d", getProtocol(), getIP(), getPort(), patientId, clinicId);

        AudiogramREST.AuthJSONArrayRequest request = new AuthJSONArrayRequest(url, null, new GetAudiogramArrayResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add((JsonArrayRequest) request);

        return m_lock;
    }

    public Object getAudiogram(int clinicId, int patientId, int imageId) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/audiogram/?patient=%d&clinic=%d&image=%d", getProtocol(), getIP(), getPort(), patientId, clinicId, imageId);

        AudiogramREST.AuthJSONArrayRequest request = new AuthJSONArrayRequest(url, null, new GetAudiogramArraySingleResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add((JsonArrayRequest) request);

        return m_lock;
    }

    public Object getAudiogram(int id) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/audiogram/%d/", getProtocol(), getIP(), getPort(), id);


        AudiogramREST.AuthJSONObjectRequest request = new AudiogramREST.AuthJSONObjectRequest(Request.Method.GET, url, null,  new AudiogramREST.GetAudiogramResponseListener(), new AudiogramREST.ErrorListener());

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    public Object createAudiogram(Audiogram audiogram) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/audiogram/", getProtocol(), getIP(), getPort());

        JSONObject data = new JSONObject();

        try {
            data.put("clinic", audiogram.getClinic());
            data.put("patient", audiogram.getPatient());
            data.put("image", audiogram.getImage());
            data.put("comment", audiogram.getComment());
        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
        }

        AudiogramREST.AuthJSONObjectRequest request = new AudiogramREST.AuthJSONObjectRequest(Request.Method.POST, url, data,  new AudiogramREST.CreateOrUpdateAudiogramResponseListener(), new AudiogramREST.ErrorListener());

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    public Object updateAudiogram(Audiogram audiogram) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        JSONObject data = audiogram.toJSONObject(true);

        String url = String.format("%s://%s:%s/tscharts/v1/audiogram/%d/", getProtocol(), getIP(), getPort(), audiogram.getId());

        AuthJSONObjectRequest request = new AuthJSONObjectRequest(Request.Method.PUT, url, data, new CreateOrUpdateAudiogramResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }
}
