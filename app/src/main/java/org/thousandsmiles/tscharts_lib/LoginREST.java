/*
 * (C) Copyright Syd Logan 2017-2021
 * (C) Copyright Thousand Smiles Foundation 2017-2021
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
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginREST extends RESTful {
    private final Object m_lock = new Object();

    private class SignonResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {

            synchronized (m_lock) {
                setStatus(200);
                try {
                    CommonSessionSingleton.getInstance().setToken(response.getString("token"));
                } catch (JSONException e) {
                    setStatus(500);
                }
                m_lock.notify();
            }
        }
    }

    private class AuthenticationAPIResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {

            synchronized (m_lock) {
                setStatus(200);
                m_lock.notify();
            }
        }
    }

    private class SignoffResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {

            synchronized (m_lock) {
                setStatus(200);
                m_lock.notify();
            }
        }
    }

    private class ErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {

            synchronized (m_lock) {
                setMessage(error.getMessage());
                if (error.networkResponse == null) {
                    if (error.getCause() instanceof java.net.ConnectException || error.getCause() instanceof java.net.UnknownHostException) {
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
            String token = CommonSessionSingleton.getInstance().getToken();
            if (token != null && !token.equals("")) {
                headers.put("Authorization", token);
            }
            return headers;
        }
    }

    public LoginREST(Context context)  {
        setContext(context);
    }

    public Object signIn(String username, String password) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/login/", getProtocol(), getIP(), getPort());

        JSONObject data = new JSONObject();

        try {
            data.put("username", username);
            data.put("password", password);
        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
        }

        AuthJSONObjectRequest request = new AuthJSONObjectRequest(Request.Method.POST, url, data,  new SignonResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    public Object signIn(String username, String password, String pin) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/login/", getProtocol(), getIP(), getPort());

        JSONObject data = new JSONObject();

        try {
            data.put("username", username);
            if (password != null && password.length() > 0) {
                data.put("password", password);
            }
            if (pin != null && pin.length() > 0) {
                data.put("pin", pin);
            }
        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
        }

        AuthJSONObjectRequest request = new AuthJSONObjectRequest(Request.Method.POST, url, data,  new SignonResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    public Object signOut() {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s/tscharts/v1/logout/", getProtocol(), getIP());

        JSONObject data = new JSONObject();

        AuthJSONObjectRequest request = new AuthJSONObjectRequest(Request.Method.POST, url, data,  new SignoffResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    public Object createUser(String first, String last, String password, String eMail, String pin) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/createuser/", getProtocol(), getIP(), getPort());

        JSONObject data = new JSONObject();

        try {
            data.put("first", first);
            data.put("last", last);
            data.put("password", password);
            data.put("email", eMail);
            data.put("pin", pin);
        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
        }

        AuthJSONObjectRequest request = new AuthJSONObjectRequest(Request.Method.POST, url, data,  new AuthenticationAPIResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    public Object updatePassword(String username, String password) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/updatepassword/", getProtocol(), getIP(), getPort());

        JSONObject data = new JSONObject();

        try {
            data.put("username", username);
            data.put("password", password);
        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
        }

        AuthJSONObjectRequest request = new AuthJSONObjectRequest(Request.Method.PUT, url, data,  new AuthenticationAPIResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    public Object updatePIN(String username, String pin) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/updatepin/", getProtocol(), getIP(), getPort());

        JSONObject data = new JSONObject();

        try {
            data.put("username", username);
            data.put("pin", pin);
        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
        }

        AuthJSONObjectRequest request = new AuthJSONObjectRequest(Request.Method.PUT, url, data,  new AuthenticationAPIResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add((JsonObjectRequest) request);

        return m_lock;
    }

}
