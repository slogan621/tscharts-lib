/*
 * (C) Copyright Syd Logan 2018
 * (C) Copyright Thousand Smiles Foundation 2017-2018
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

public class ConsentREST extends RESTful {
    private final Object m_lock = new Object();

    private class CreateConsentResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {
            synchronized (m_lock) {
                setStatus(200);
                onSuccess(200, "", response);
                m_lock.notify();
            }
        }
    }

    private class GetorDeleteConsentResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {
            synchronized (m_lock) {
                setStatus(200);
                onSuccess(200, "", response);
                m_lock.notify();
            }
        }
    }

    private class SearchConsentResponseListener implements Response.Listener<JSONArray> {

        @Override
        public void onResponse(JSONArray response) {
            synchronized (m_lock) {
                onSuccess(200, "", response);
                setStatus(200);
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
                        onFail(101, "");
                    } else {
                        setStatus(-1);
                        onFail(101, "");
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

    public ConsentREST(Context context) {
        setContext(context);
    }

    public Object createConsent(int patient, int clinic, int registration, boolean photo, boolean consent) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/consent/", getProtocol(), getIP(), getPort());

        JSONObject data = new JSONObject();

        try {
            data.put("patient", patient);
            data.put("clinic", clinic);
            data.put("registration", registration);
            data.put("general_consent", consent);
            data.put("photo_consent", photo);
        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
        }

        ConsentREST.AuthJSONObjectRequest request = new ConsentREST.AuthJSONObjectRequest(Request.Method.POST, url, data,  new ConsentREST.CreateConsentResponseListener(), new ConsentREST.ErrorListener());

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    /**
     *
     * Search for a consent record based on patient, clinic, or registration
     *
     * @param patient patient id, -1 if any patient
     * @param clinic clinic id, -1 if any clinic
     * @param registration registration id, -1 if any registration

     * @return
     */
    public Object getConsent(int patient, int clinic, int registration) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/consent/", getProtocol(), getIP(), getPort());

        JSONObject data = new JSONObject();

        try {
            if (patient != -1) {
                data.put("patient", patient);
            }
            if (clinic != -1) {
                data.put("clinic", clinic);
            }
            if (registration != -1) {
                data.put("registration", registration);
            }
        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
        }

        AuthJSONArrayRequest request = new AuthJSONArrayRequest(url, null, new SearchConsentResponseListener(), new ConsentREST.ErrorListener());

        queue.add((JsonArrayRequest) request);

        return m_lock;
    }

    public Object getConsent(int id) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/consent/%d/", getProtocol(), getIP(), getPort(), id);


        ConsentREST.AuthJSONObjectRequest request = new ConsentREST.AuthJSONObjectRequest(Request.Method.GET, url, null,  new ConsentREST.GetorDeleteConsentResponseListener(), new ConsentREST.ErrorListener());

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    public Object deleteConsent(int id) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/consent/%d/", getProtocol(), getIP(), getPort(), id);


        ConsentREST.AuthJSONObjectRequest request = new ConsentREST.AuthJSONObjectRequest(Request.Method.DELETE, url, null,  new ConsentREST.GetorDeleteConsentResponseListener(), new ConsentREST.ErrorListener());

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }
}
