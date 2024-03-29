/*
 * (C) Copyright Syd Logan 2018-2020
 * (C) Copyright Thousand Smiles Foundation 2018-2020
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
import android.os.Looper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ImageDataReader {
    private File m_storageDir = null;
    private CommonSessionSingleton m_sess = CommonSessionSingleton.getInstance();
    private Context m_context = null;
    private File m_file = null;
    private String m_imageFileName = null;
    private int m_id;                               // id of resource in DB, e.g., patient ID
    private ArrayList<ImageReadyListener> m_listener = new ArrayList<ImageReadyListener>();   // callback on success or error
    private boolean m_isCached = false;             // image data is already cached in file
    private ImageREST m_imageData;
    private String m_imageType = "Headshot";

    public ImageDataReader(Context context, int id) {
        m_context = context;
        m_id = id;
    }

    public File getFile() {
        return m_file;
    }

    public void setImageType(String imageType) {
        m_imageType = imageType;
    }

    public void cancelPendingRequest(int tag)
    {
        if (m_imageData != null)
        {
            m_imageData.cancelPendingRequest(tag);
        }
    }

    public String getImageFileAbsolutePath() {
        String ret = null;
        if (m_file == null) {
            try {
                m_file = createFile();

            } catch (IOException e) {
            }
        }
        if (m_file != null) {
            ret = m_file.getAbsolutePath();
        }
        return ret;
    }

    synchronized private File createFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String idString = String.format("%d", m_id);
        m_imageFileName = "JPEG_" + idString + "_" + timeStamp + "_";

        m_file = File.createTempFile(
                m_imageFileName,                /* prefix */
                ".jpg",                         /* suffix */
                m_sess.getStorageDir()          /* directory */
        );
        m_file.deleteOnExit();
        return m_file;
    }

    private void onImageRead() {
        for (int i = 0; i < m_listener.size(); i++) {
            m_listener.get(i).onImageRead(m_file);
        }
    }

    private void onImageError(int code) {
        for (int i = 0; i < m_listener.size(); i++) {
            m_listener.get(i).onImageError(code);
        }
    }

    public void read(int id)
    {
        if (m_file != null) {
            // notify the listener, if registered
            onImageRead();
        } else {
            if (m_context == null) {
                onImageError(500);
            }
            if (m_file == null) {
                try {
                    createFile();
                } catch(IOException e) {
                }
            }
            if (m_file == null) {
                onImageError(500);
            } else if (Looper.myLooper() != Looper.getMainLooper()) {
                m_imageData = new ImageREST(m_context);
                Object lock;
                if (m_imageType == "Headshot") {
                    lock = m_imageData.getMostRecentPatientImageData(id, m_file, m_imageType);
                } else {
                    lock = m_imageData.getImageData(id, m_file);
                }

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

                int status = m_imageData.getStatus();
                if (status == 200) {
                    m_isCached = true;
                    onImageRead();
                } else {
                    onImageError(status);
                }
            } else {
                onImageError(500);
            }
        }
    }

    public void registerListener(ImageReadyListener listener)
    {
        m_listener.add(listener);
    }
}
