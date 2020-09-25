/*
 * (C) Copyright Syd Logan 2017
 * (C) Copyright Thousand Smiles Foundation 2017
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
import android.util.Base64;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageREST extends RESTful {
    private final Object m_lock = new Object();
    private File m_file = null;
    RequestQueue m_queue;

    private class ResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {

            synchronized (m_lock) {
                if (m_file == null) {
                    setStatus(500);
                    onFail(500, "");
                } else {
                    try {
                        String data = response.getString("data");
                        byte[] decoded = Base64.decode(data, Base64.DEFAULT);
                        try {
                            FileOutputStream stream = new FileOutputStream(m_file.getAbsolutePath());
                            try {
                                stream.write(decoded);
                            } finally {
                                stream.close();
                                stream = null;
                            }
                            setStatus(200);
                            onSuccess(200, "", response);
                        } catch (IOException e) {
                            setStatus(500);
                            onFail(500, "");
                        }
                    } catch (JSONException e) {
                        setStatus(500);
                        onFail(500, "");
                    }
                }
                m_lock.notify();
            }
        }
    }

    private class ArrayResponseListener implements Response.Listener<JSONArray> {

        @Override
        public void onResponse(JSONArray response) {

            synchronized (m_lock) {
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
                setStatus(200);
                onSuccess(200, "", response);
                m_lock.notify();
            }
        }
    }

    public void cancelPendingRequest(int tag)
    {
       m_queue.cancelAll(tag);
    }

    private class ErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {

            synchronized (m_lock) {
                if (error.networkResponse == null) {
                    if (error.getCause() instanceof java.net.ConnectException) {
                        setStatus(101);
                        onFail(101, "connection exception");
                    } else if (error.getCause() instanceof java.net.UnknownHostException) {
                        setStatus(101);
                        onFail(101, "unknown host exception");
                    } else if (error.getCause() instanceof com.android.volley.TimeoutError) {
                        setStatus(101);
                        onFail(101, "timeout exception");
                    } else {
                        setStatus(101);
                        onFail(101, "unknown network error");
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

    public ImageREST(Context context) {
        setContext(context);
    }

    public Object getImageData(int imageid, File file) {

        m_file = file;

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        m_queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/image/%d/", getProtocol(), getIP(), getPort(), imageid);

        ImageREST.AuthJSONObjectRequest request = new ImageREST.AuthJSONObjectRequest(Request.Method.GET, url, null, new ImageREST.ResponseListener(), new ImageREST.ErrorListener());

        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        m_queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    public Object getMostRecentPatientImageData(int patientid, File file, String imgType) {

        m_file = file;

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        String url = String.format("%s://%s:%s/tscharts/v1/image?patient=%d&newest=true&type=%s", getProtocol(), getIP(), getPort(), patientid, imgType);

        ImageREST.AuthJSONObjectRequest request = new ImageREST.AuthJSONObjectRequest(Request.Method.GET, url, null, new ImageREST.ResponseListener(), new ImageREST.ErrorListener());

        request.setTag(CommonSessionSingleton.getInstance().getHeadshotTag());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        m_queue = volley.getQueue();
        m_queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    public Object createImage(File file, int clinic, int patient, String imageType) {

        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        m_queue = volley.getQueue();

        CommonSessionSingleton sess = CommonSessionSingleton.getInstance();

        JSONObject data = new JSONObject();

        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[(int) file.length()];
            ios = new FileInputStream(file);
            int read = 0;
            read = ios.read(buffer);
            if (read == file.length()) {
                String encoded = Base64.encodeToString(buffer, 0, read, Base64.NO_WRAP);
                try {
                    data.put("patient", patient);
                    data.put("clinic", clinic);
                    data.put("type", imageType);
                    data.put("data", encoded);
                } catch (JSONException e) {
                }
            } else {
                // fail
            }
            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        } catch (IOException e) {
            // fail
        }

        String url = String.format("%s://%s:%s/tscharts/v1/image/", getProtocol(), getIP(), getPort());

        ImageREST.AuthJSONObjectRequest request = new ImageREST.AuthJSONObjectRequest(Request.Method.POST, url, data, new PostResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeoutInMillis(), getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        m_queue.add((JsonObjectRequest) request);

        return m_lock;
    }

    public Object getTypedImagesForPatientAndClinic(int clinicid, int patientid, String imgType)
    {
        VolleySingleton volley = VolleySingleton.getInstance();

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/image?clinicid=%d&patient=%d&type=%s", getProtocol(), getIP(), getPort(), clinicid, patientid, imgType);

        AuthJSONArrayRequest request = new AuthJSONArrayRequest(url, null, new ArrayResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

        return m_lock;
    }

    public Object getAllPatientImages(int patientid, File file, boolean sort) {

        VolleySingleton volley = VolleySingleton.getInstance();

        String sortArg = "false";
        m_file = file;

        if (sort == true) {
            sortArg = "true";
        }

        volley.initQueueIf(getContext());

        RequestQueue queue = volley.getQueue();

        String url = String.format("%s://%s:%s/tscharts/v1/image?patient=%d&sort=%s", getProtocol(), getIP(), getPort(), patientid, sortArg);

        AuthJSONArrayRequest request = new AuthJSONArrayRequest(url, null, new ArrayResponseListener(), new ErrorListener());
        request.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

        return m_lock;
    }
}
