/*
 * (C) Copyright Syd Logan 2022
 * (C) Copyright Thousand Smiles Foundation 2022
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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class WristbandPrinterListFragment extends Fragment implements WristbandStatusListener, WristbandStatusPublisher {
    private Activity m_activity = null;
    private View m_view = null;
    private Context m_context;
    ArrayList<WristbandPrinter> m_printers = null;
    ArrayList<WristbandPrinterFragment> m_fragments = null;
    ArrayList<WristbandStatusListener> m_listeners = null;

    public WristbandPrinterListFragment(WristbandStatusListener parent) {
        registerListener(parent);
    }

    public static WristbandPrinterListFragment newInstance(WristbandStatusListener parent) {
        return new WristbandPrinterListFragment(parent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        m_context = context;
        if (context instanceof Activity) {
            m_activity = (Activity) context;
            createPrinterFragments();
        }
    }

    @Override
    public void OnSuccess(int job, @NonNull WristbandPrinter.PrinterStatus status) {
        if (m_listeners != null) {
            for (int i = 0; i < m_listeners.size(); i++) {
                m_listeners.get(i).OnSuccess(job, status);
            }
        }
    }

    @Override
    public void OnError(int job, @NonNull WristbandPrinter.PrinterStatus status, @NonNull String msg) {
        if (m_listeners != null) {
            for (int i = 0; i < m_listeners.size(); i++) {
                m_listeners.get(i).OnError(job, status, msg);
            }
        }
    }

    @Override
    public void OnStatusChange(int job, @NonNull WristbandPrinter.PrinterStatus status) {
        if (m_listeners != null) {
            for (int i = 0; i < m_listeners.size(); i++) {
                m_listeners.get(i).OnStatusChange(job, status);
            }
        }
    }

    @Override
    public void OnConnectionStatusChange(int job, @NonNull WristbandPrinter.ConnectedStatus status) {
        if (m_listeners != null) {
            for (int i = 0; i < m_listeners.size(); i++) {
                m_listeners.get(i).OnConnectionStatusChange(job, status);
            }
        }
    }

    private  void createPrinterFragments() {
        m_fragments = new ArrayList<WristbandPrinterFragment>();
        WristbandPrintManager manager = WristbandPrintManager.Companion.getInstance(m_context);
        m_printers = manager.getPrinterList();
        int[] resource_ids = {R.id.printer_1, R.id.printer_2, R.id.printer_3};
        for (int i = 0; i < m_printers.size(); i++) {
            m_printers.get(i).registerListener(this);
            m_context = getContext();
            Bundle arguments = new Bundle();
            WristbandPrinterFragment frag = new WristbandPrinterFragment(m_printers.get(i));
            m_fragments.add(frag);
            frag.setArguments(arguments);
            //m_fragment.setNextActivity(VaccinationActivity.class);
            getChildFragmentManager().beginTransaction()
                    .replace(resource_ids[i], frag)
                    .commit();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wristband_printer_controller_fragment_layout, container, false);
        m_view = view;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
   }

    @Override
    public void registerListener(@NonNull WristbandStatusListener listener) {
        if (m_listeners == null) {
            m_listeners = new ArrayList<WristbandStatusListener>();
        }
        m_listeners.add(listener);
    }

    @Override
    public void removeListener(@NonNull WristbandStatusListener listener) {
        if (m_listeners != null) {
            m_listeners.remove(listener);
        }
    }
}