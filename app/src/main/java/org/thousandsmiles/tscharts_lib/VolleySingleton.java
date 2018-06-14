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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import javax.net.ssl.SSLSocketFactory;

public class VolleySingleton {
    private static VolleySingleton m_instance;
    private static RequestQueue m_queue;

    public void initQueueIf(Context context) {
        if (m_queue == null) {
            TrustedSSLSocketFactory tssf = TrustedSSLSocketFactory.getInstance();
            SSLSocketFactory sf = tssf.getSocketFactory(context);
            HurlStack hs = new HurlStack(null, sf);
            m_queue = Volley.newRequestQueue(context, hs);
        }
    }

    public RequestQueue getQueue() {
        return m_queue;
    }

    public static VolleySingleton getInstance() {
        if (m_instance == null) {
            m_instance = new VolleySingleton();
        }
        return m_instance;
    }
}

