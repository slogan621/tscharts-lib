/*
 * (C) Copyright Syd Logan 2018
 * (C) Copyright Thousand Smiles Foundation 2018
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

/* based on https://stackoverflow.com/questions/28191023/ssl-pinning-with-volley-network-library-on-android */

package org.thousandsmiles.tscharts_lib;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class TrustedSSLSocketFactory {

    private static SSLSocketFactory m_sf = null;
    private static TrustedSSLSocketFactory m_instance = null;

    public static TrustedSSLSocketFactory getInstance() {
        if (m_instance == null) {
            m_instance = new TrustedSSLSocketFactory();
        }
        return m_instance;
    }

    public SSLSocketFactory getSocketFactory(Context aCtx) {

        CertificateFactory cf = null;

        if (m_sf != null) {
            return m_sf;
        }
        try {
            cf = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            return null;
        }

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        Certificate ca = null;
        // Generate the certificate using the certificate file under res/raw/cert.cer
        InputStream caInput = new BufferedInputStream(aCtx.getResources().openRawResource(R.raw.cert));
        try {
            ca = cf.generateCertificate(caInput);
            try {
                caInput.close();
            } catch(java.io.IOException e) {
            }
        } catch (CertificateException e) {
            return null;
        }

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore trusted = null;
        try {
            trusted = KeyStore.getInstance(keyStoreType);
            trusted.load(null, null);
            trusted.setCertificateEntry("ca", ca);
        } catch (java.security.NoSuchAlgorithmException | java.security.KeyStoreException | java.io.IOException | java.security.cert.CertificateException e ) {
            return null;
        }

        // Create a TrustManager that trusts the CAs in our KeyStore

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = null;
        try {
            tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(trusted);
        } catch (java.security.KeyStoreException | java.security.NoSuchAlgorithmException e) {
            return null;
        }

        // Create an SSLContext that uses our TrustManager
        SSLContext context = null;

        try {
            context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);
        } catch (java.security.KeyManagementException | java.security.NoSuchAlgorithmException e) {
            return null;
        }

        m_sf = context.getSocketFactory();
        return m_sf;
    }
}
