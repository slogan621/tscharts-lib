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

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class HeadshotImage implements ImageReadyListener {
    private int m_id;
    private ImageView m_imageView;
    private Context m_context;
    private Activity m_activity;
    private ImageDataReader m_reader = null;
    private Thread m_thread = null;
    private String m_imageType = "Headshot";
    private ArrayList<ImageDisplayedListener> m_listener = new ArrayList<ImageDisplayedListener>();   // callback on success or error

    private void sendOnImageDisplayed(int id, String path) {
        for (int i = 0; i < m_listener.size(); i++) {
            m_listener.get(i).onImageDisplayed(id, path);
        }
    }

    private void sendOnImageError(int id, String path, int code) {
        for (int i = 0; i < m_listener.size(); i++) {
            m_listener.get(i).onImageError(id, path, code);
        }
    }

    public void registerListener(ImageDisplayedListener listener)
    {
        m_listener.add(listener);
    }

    public void setImageView(ImageView imageView) {
        m_imageView = imageView;
    }

    public void setImageType(String imgType) {
        m_imageType = imgType;
    }

    public Thread getImage(int id) {
        m_id = id;
        if (m_reader == null) {
            m_reader = new ImageDataReader(m_context, m_id);
            m_reader.setImageType(m_imageType);
            m_reader.registerListener(this);
            m_thread = new Thread(){
                public void run() {
                    m_reader.read(m_id);
                }
            };
        }
        return m_thread;
    }

    public  void start()
    {
        m_thread.start();
    }

    public  String getImageFileAbsolutePath()
    {
        String ret = null;

        if (m_reader != null) {
            ret = m_reader.getImageFileAbsolutePath();
        }
        return ret;
    }

    public void cancelPendingRequest(int tag)
    {
        if (m_reader != null)
        {
            m_reader.cancelPendingRequest(tag);
        }
    }

    public void setActivity(Activity activity) {
        m_activity = activity;
        m_context = activity.getApplicationContext();
    }

    @Override
    public void onImageRead(final File file) {
        m_activity.runOnUiThread(new Runnable() {
            public void run() {
                //int maxSize = 1024 * 1024 * 10;
                //Picasso p = new Picasso.Builder(m_context).memoryCache(new LruCache(maxSize)).build();
                //m_imageView.setBackgroundColor(m_activity.getResources().getColor(R.color.white));
                m_imageView.setBackground(null);
                if (m_imageType == "Headshot") {
                    Picasso.get().load(file).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).fit().into(m_imageView);
                } else {
                    m_imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    m_imageView.setAdjustViewBounds(true);
                    Picasso.get().load(file).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(m_imageView);
                }


                //int w = m_imageView.getMeasuredWidth();
                //int h = m_imageView.getMeasuredHeight();


                sendOnImageDisplayed(m_id, getImageFileAbsolutePath());

                //Picasso.with(m_context).load(file).fit().centerInside().into(m_imageView);
                //p.with(m_context).load(file).into(m_imageView);
            }
        });
    }

    @Override
    public void onImageError(int code) {
        sendOnImageError(m_id, getImageFileAbsolutePath(), code);
    }
}
