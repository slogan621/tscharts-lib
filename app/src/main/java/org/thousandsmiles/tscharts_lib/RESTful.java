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
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/* assumption - application has no thread local storage, and it is safe (if not required) to invoke all callbacks on UI thread */

public abstract class RESTful {
    private Context m_context;
    private int m_status;
    private int m_timeout = 5000;  // Milliseconds
    private int m_retries = 3;
    private ArrayList<RESTCompletionListener> m_listener = new ArrayList<RESTCompletionListener>();
    private Object lock = new Object();

    public void addListener(RESTCompletionListener o) {
        synchronized(lock) {
            m_listener.add(o);
        }
    }

    public void removeListener(RESTCompletionListener o) {
        synchronized(lock) {
            m_listener.remove(o);
        }
    }

    protected void onSuccess(final int code, final String message, final JSONArray a)
    {
        synchronized(lock) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    for (RESTCompletionListener x : m_listener) {
                        x.onSuccess(code, message, a);
                    }
                }
            });
        }
    }

    protected void onSuccess(final int code, final String message, final JSONObject o)
    {
        synchronized(lock) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    for (RESTCompletionListener x : m_listener) {
                        x.onSuccess(code, message, o);
                    }
                }
            });
        }
    }

    protected void onSuccess(final int code, final String message)
    {
        synchronized(lock) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    for (RESTCompletionListener x : m_listener) {
                        x.onSuccess(code, message);
                    }
                }
            });
        }
    }

    protected int getTimeoutInMillis()
    {
        return m_timeout;
    }

    protected int getRetries()
    {
        return m_retries;
    }

    protected void onFail(final int code, final String message)
    {
        synchronized(lock) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    for (RESTCompletionListener x : m_listener) {
                        x.onFail(code, message);
                    }
                }
            });
        }
    }

    public int getStatus() {
        return m_status;
    }

    protected void setStatus(int status) {
        m_status = status;
    }

    protected void setContext(Context context) {
        m_context = context;
    }

    protected Context getContext() {
        return m_context;
    }

    public String getPort() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(m_context);
        String val = sharedPref.getString("port", "443");

        return val;
    }

    public String getIP() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(m_context);
        String val = sharedPref.getString("ipAddress", "192.168.0.128");
        return val;
    }

    public String getProtocol() {
        return "https";
    }
}
