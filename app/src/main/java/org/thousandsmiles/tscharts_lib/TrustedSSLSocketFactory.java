package org.thousandsmiles.tscharts_lib;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
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
