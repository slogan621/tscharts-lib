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
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class CommonSessionSingleton {
    private static CommonSessionSingleton m_instance;
    private static Context m_ctx;
    private static String m_token = "";
    private File m_storageDir = null;
    private int m_headshotTag = 676;
    private int m_patientId;
    private String m_photoPath = "";
    private int m_clinicId;
    private ArrayList<HeadshotImage> m_headshotImages = new ArrayList<HeadshotImage>();
    private ArrayList<HeadshotImage> m_headshotJobs = new ArrayList<HeadshotImage>();
    private ConcurrentHashMap<Integer, String> m_headshotIdToPath = new ConcurrentHashMap<Integer, String>();

    public void addHeadshotJob(HeadshotImage hs)
    {
        m_headshotJobs.add(hs);
    }

    public void startNextHeadshotJob()
    {
        HeadshotImage hs;
        if (m_headshotJobs.size() > 0) {
            hs = m_headshotJobs.remove(0);
            hs.start();
        }
    }

    public void clearHeadShotCache() {
        m_headshotIdToPath.clear();
    }

    public void addHeadShotPath(int id, String path) {
        m_headshotIdToPath.put(id, path);
    }

    public void addHeadshotImage(HeadshotImage o)
    {
        m_headshotImages.add(o);
    }

    public void cancelHeadshotImages()
    {
        m_headshotJobs.clear();
        for (int i = 0; i < m_headshotImages.size(); i++) {
            m_headshotImages.get(i).cancelPendingRequest(CommonSessionSingleton.getInstance().getHeadshotTag());
        }
        m_headshotImages.clear();
    }

    public String getPhotoPath() {
        return m_photoPath;
    }

    public void setPhotoPath(String path) {
        m_photoPath = path;
    }

    public void createHeadshot(final RESTCompletionListener listener) {
        boolean ret = false;

        Thread thread = new Thread() {
            public void run() {
                // note we use session context because this may be called after onPause()
                ImageREST rest = new ImageREST(getContext());
                rest.addListener(listener);
                Object lock;
                int status;

                File file = new File(getPhotoPath());

                lock = rest.createImage(file);

                synchronized (lock) {
                    // we loop here in case of race conditions or spurious interrupts
                    while (true) {
                        try {
                            lock.wait();
                            break;
                        } catch (InterruptedException e) {
                            continue;
                        }
                    }
                }
                status = rest.getStatus();
                if (status != 200) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            //Toast.makeText(getContext(), getContext().getString(R.string.msg_unable_to_save_headshot_photo), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            //Toast.makeText(getContext(), getContext().getString(R.string.msg_successfully_saved_headshot_photo), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        };
        thread.start();
    }

    public void removeHeadShotPath(int id) {
        m_headshotIdToPath.remove(id);
    }

    public String getHeadShotPath(int id) {
        String ret = null;

        try {
            ret = m_headshotIdToPath.get(id);
        } catch(Exception e) {
        }
        return ret;
    }

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

    public int getHeadshotTag()
    {
        return m_headshotTag;
    }

    public void setToken(String token) {
        m_token = String.format("Token %s", token);
    }

    public String getToken() {
        return m_token;
    }

    public void setStorageDir(Activity activity) {
        m_storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    public File getStorageDir() {
        return m_storageDir;
    }

    public static CommonSessionSingleton getInstance() {
        if (m_instance == null) {
            m_instance = new CommonSessionSingleton();
        }
        return m_instance;
    }

    public void setContext(Context ctx) {
        m_ctx = ctx;
    }
    public Context getContext() {
        return m_ctx;
    }
}
