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

package org.thousandsmiles.tscharts_lib;

import android.app.Activity;
import android.os.Environment;

import java.io.File;

public class CommonSessionSingleton {
    private static CommonSessionSingleton m_instance;
    private static String m_token = "";
    private File m_storageDir = null;
    private int m_headshotTag = 676;
    private int m_patientId;
    private int m_clinicId;

    public int getClinicId() {
        return m_clinicId;
    }

    public int getPatientId()
    {
        return m_patientId;
    }

    public void setPatientId(int id)
    {
        m_patientId = id;
    }

    public int getActivePatientId()
    {
        return getPatientId();
    }

    int getHeadshotTag()
    {
        return m_headshotTag;
    }

    public void setToken(String token) {
        m_token = String.format("Token %s", token);
    }

    public String getToken() {
        return m_token;
    }

    void setStorageDir(Activity activity) {
        m_storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    File getStorageDir() {
        return m_storageDir;
    }

    public static CommonSessionSingleton getInstance() {
        if (m_instance == null) {
            m_instance = new CommonSessionSingleton();
        }
        return m_instance;
    }
}
