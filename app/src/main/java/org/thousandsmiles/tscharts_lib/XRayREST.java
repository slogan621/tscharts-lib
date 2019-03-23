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

import java.util.HashMap;
import java.util.Map;

public class XRayREST extends RESTful {
    private final Object m_lock = new Object();

    private class GetXRayResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {
            synchronized (m_lock) {
                CommonSessionSingleton sess = CommonSessionSingleton.getInstance();
                setStatus(200);
                onSuccess(200, "", response);
                sess.setPatientXRay(response);
                m_lock.notify();
            }
        }
    }

    private class GetAllXRaysListener implements Response.Listener<JSONArray> {

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
                if (error.networkResponse == null) {
                    if (error.getCause() instanceof java.net.ConnectException || error.getCause() instanceof  java.net.UnknownHostException) {
                        setStatus(101);
                    } else {
                        setStatus(-1);
                    }
                } else {
                   setStatus(error.networkResponse.statusCode);
                }
                m_lock.notify();
            }
        }
    }

    public class AuthJSONObjectRequest extends JsonObjectRequest
    {
        public AuthJSONObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener listener, ErrorListener errorListener)
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

    private class PostResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {

            synchronized (m_lock) {
                setStatus(200);
                onSuccess(200, "");

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

    public XRayREST(Context context) {
        setContext(context);
    }

    public Object getXRay(int clinicId, int patientId) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/xray?patient=%d&clinic=%d", getProtocol(), getIP(), getPort(), patientId, clinicId);

        XRayREST.AuthJSONObjectRequest request = new XRayREST.AuthJSONObjectRequest(Request.Method.GET, url, null,  new XRayREST.GetXRayResponseListener(), new XRayREST.ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    public Object getAllXRaysForPatient(int patientId) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/xray?patient=%d", getProtocol(), getIP(), getPort(), patientId);

        AuthJSONArrayRequest request = new AuthJSONArrayRequest(url, null, new GetAllXRaysListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add((JsonArrayRequest) request);

        return m_lock;
    }

    public Object getAllXRaysForClinic(int clinicid) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/xray?clinic=%d", getProtocol(), getIP(), getPort(), clinicid);

        AuthJSONArrayRequest request = new AuthJSONArrayRequest(url, null, new GetAllXRaysListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add((JsonArrayRequest) request);

        return m_lock;
    }

    public Object getAllXRays(int clinicid, int patientid) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/xray?clinic=%d&patient=%d", getProtocol(), getIP(), getPort(), clinicid, patientid);

        AuthJSONArrayRequest request = new AuthJSONArrayRequest(url, null, new GetAllXRaysListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add((JsonArrayRequest) request);

        return m_lock;
    }

    public Object createXRay(int patientId, int clinicId, long teeth, String type, String mouth_type) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        CommonSessionSingleton sess = CommonSessionSingleton.getInstance();

        JSONObject data = new JSONObject();

        try {
            data.put("patient", patientId);
            data.put("clinic", clinicId);
            data.put("teeth", teeth);
            data.put("xray_type", type);
            data.put("mouth_type", mouth_type);
        } catch (JSONException e) {
        }

        String url = String.format("%s://%s:%s/tscharts/v1/xray/", getProtocol(), getIP(), getPort());

        XRayREST.AuthJSONObjectRequest request = new XRayREST.AuthJSONObjectRequest(Request.Method.POST, url, data, new PostResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    public Object updateXRay(XRay xray) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        JSONObject data = xray.toJSONObject(true);

        String url = String.format("%s://%s:%s/tscharts/v1/xray/%d/", getProtocol(), getIP(), getPort(), xray.getId());

        AuthJSONObjectRequest request = new AuthJSONObjectRequest(Request.Method.PUT, url, data, new PutResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }
}
