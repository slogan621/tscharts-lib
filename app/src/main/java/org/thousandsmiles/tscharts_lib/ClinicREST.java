/*
 * (C) Copyright Syd Logan 2017-2022
 * (C) Copyright Thousand Smiles Foundation 2017-2022
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

public class ClinicREST extends RESTful {
    private final Object m_lock = new Object();

    private class ResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {

            synchronized (m_lock) {
                CommonSessionSingleton sess = CommonSessionSingleton.getInstance();
                setStatus(200);
                try {
                    sess.setClinicId(response.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                m_lock.notify();
            }
        }
    }

    private class GetAllClinicsResponseListener implements Response.Listener<JSONArray> {

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

    private class GetByIdResponseListener implements Response.Listener<JSONObject> {
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

    public ClinicREST(Context context) {
        setContext(context);
    }

    public Object getClinicData(int year, int month, int day) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/clinic/?date=%s/%s/%s", getProtocol(), getIP(), getPort(), month, day, year);

        AuthJSONObjectRequest request = new AuthJSONObjectRequest(Request.Method.GET, url, null, new ResponseListener(), new ErrorListener());

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    public Object getClinicById(int id) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/clinic/%d", getProtocol(), getIP(), getPort(), id);

        AuthJSONObjectRequest request = new AuthJSONObjectRequest(Request.Method.GET, url, null, new GetByIdResponseListener(), new ErrorListener());

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    public Object getAllClinics() {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/clinic/", getProtocol(), getIP(), getPort());

        ClinicREST.AuthJSONArrayRequest request = new ClinicREST.AuthJSONArrayRequest(url, null, new ClinicREST.GetAllClinicsResponseListener(), new ClinicREST.ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

        return m_lock;
    }
}
