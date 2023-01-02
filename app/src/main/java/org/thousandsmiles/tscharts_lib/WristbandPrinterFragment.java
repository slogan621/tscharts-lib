/*
 * (C) Copyright Syd Logan 2022-2023
 * (C) Copyright Thousand Smiles Foundation 2022-2023
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
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class WristbandPrinterFragment extends Fragment implements WristbandStatusListener {
    private View m_view = null;
    WristbandPrinter m_printer;
    private volatile Thread m_connectivityThread;
    private volatile Thread m_jobStatusThread;

    public void stopConnectivityThread() {
        m_connectivityThread = null;
    }

    public Thread getM_connectivityThread() {
        return m_connectivityThread;
    }

    public void stopJobStatusThread() {
        m_jobStatusThread = null;
    }

    public WristbandPrinterFragment(WristbandPrinter printer) {
        m_printer = printer;
        m_printer.registerListener(this);
    }

    public static WristbandPrinterFragment newInstance(WristbandPrinter printer) {
        return new WristbandPrinterFragment(printer);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // kill the threads
        m_connectivityThread = null;
        m_jobStatusThread = null;
    }

    void displayPrintJobChangeToast(int job, @NonNull WristbandPrinter.PrinterStatus status, String msg) {
        String displayMsg = "";

        if (job != 1) {
            displayMsg = String.format("Print job: %d: ", job);
        }
        switch(status) {
            case Printing:
                displayMsg += getActivity().getString(R.string.msg_printer_is_printing);
                break;
            case Jammed:
                displayMsg += getActivity().getString(R.string.msg_printer_is_jammed);
                break;
            case OutOfPaper:
                displayMsg += getActivity().getString(R.string.msg_printer_is_out_of_paper);
                break;
            case Idle:
                displayMsg += getActivity().getString(R.string.msg_printer_is_idle);
                break;
            case Restarting:
                displayMsg += getActivity().getString(R.string.msg_printer_is_restarting);
                break;
            default:
                displayMsg += getActivity().getString(R.string.msg_printer_unknown_status);
                break;
        }

        if (msg.length() != 0) {
            displayMsg += ": ";
            displayMsg += msg;
        }
        Toast.makeText(getActivity(), displayMsg, Toast.LENGTH_LONG).show();
    }

    void displayConnectionChangeToast(int job, @NonNull WristbandPrinter.ConnectedStatus status, String msg) {
        String displayMsg = "";

        if (job != 1) {
            displayMsg = String.format("Print job: %d: ", job);
        }
        if (status == WristbandPrinter.ConnectedStatus.Connected) {
            displayMsg += getActivity().getString(R.string.msg_printer_is_online);
        } else {
            displayMsg += getActivity().getString(R.string.msg_printer_is_offline);
        }

        if (msg.length() != 0) {
            displayMsg += ": ";
            displayMsg += msg;
        }
        Toast.makeText(getActivity(), displayMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnSuccess(int job, @NonNull WristbandPrinter.PrinterStatus status) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                String displayMsg = String.format(getString(R.string.msg_print_job_success), job, status.toString());
                AlertDialog alert = new AlertDialog.Builder(getActivity())
                        .setTitle(getActivity().getString(R.string.title_printer_success))
                        .setMessage(displayMsg)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setCancelable(false).create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
                displayPrintJobChangeToast(job, status, getActivity().getString(R.string.msg_wristband_printed_successfully));
            }
        });
    }

    @Override
    public void OnError(int job, @NonNull WristbandPrinter.PrinterStatus status, @NonNull String msg) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                String displayMsg = String.format(getString(R.string.msg_print_job_failed), job, status.toString(), msg);
                AlertDialog alert = new AlertDialog.Builder(getActivity())
                        .setTitle(getActivity().getString(R.string.title_printer_error))
                        .setMessage(displayMsg)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setCancelable(false).create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
                displayPrintJobChangeToast(job, status, getActivity().getString(R.string.msg_wristband_failed_to_print) +": "+msg);
            }
        });
    }

    @Override
    public void OnStatusChange(int job, @NonNull WristbandPrinter.PrinterStatus status) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                String msg;
                if (status == WristbandPrinter.PrinterStatus.Printing) {
                    msg = getActivity().getString(R.string.msg_printer_is_online);

                } else {
                    msg = getActivity().getString(R.string.msg_printer_is_offline);
                }
                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void OnConnectionStatusChange(int job, @NonNull WristbandPrinter.ConnectedStatus status) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                displayConnectionChangeToast(job, status, "");
            }
        });
    }

    private synchronized void startPrintJob(final PatientData pd, int numberOfCopies) {
        Thread thread = new Thread() {
            public void run() {
                WristbandPrintManager manager = WristbandPrintManager.Companion.getInstance(m_view.getContext());
                int jobId = manager.createJob(m_printer, pd);
                if(jobId != -1) {
                    manager.startJob(jobId, numberOfCopies);
                } else {
                    OnError(jobId, m_printer.getM_printerStatus(), m_printer.getM_connectedStatus().toString());
                }
            }
        };
        thread.start();
    }

    public void printWristbandCb(View v) {
        PatientData pd = WristbandPrintManager.Companion.getInstance(m_view.getContext()).getPatientData();
        AlertDialog alertDialog = new AlertDialog.Builder(m_view.getContext()).create();
        alertDialog.setTitle(getString(R.string.title_print_wristband));
        alertDialog.setMessage(getString(R.string.msg_select_number_of_wristbands));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.print_one_wristband), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startPrintJob(pd, 1);
                alertDialog.dismiss();
            } });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertDialog.dismiss();
            }});

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.print_two_wristbands), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startPrintJob(pd, 2);
                alertDialog.dismiss();

            }});

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void startConnectivityThread() {
        m_connectivityThread = new Thread() {
            public void run() {
                while (Thread.currentThread() == m_connectivityThread) {
                    boolean isReachable = false;
                    if (m_printer != null) {
                        isReachable = m_printer.reachable();
                    }
                    boolean finalIsReachable = isReachable;
                    Activity activity = getActivity();
                    if (activity == null) {
                        break;
                    }
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            TextView txt = m_view.findViewById(R.id.printer_status);
                            if (txt != null) {
                                if (finalIsReachable) {
                                    txt.setText("Able to connect");
                                    Button button = m_view.findViewById(R.id.print);
                                    button.setEnabled(true);
                                } else {
                                    txt.setText("Not able to connect");
                                    Button button = m_view.findViewById(R.id.print);
                                    button.setEnabled(false);
                                }
                            }
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // ignore
                    }
                }
            }
        };
        m_connectivityThread.setName("wristband connectivity status thread");
        m_connectivityThread.start();
    }

    private void startJobStatusThread() {
        m_jobStatusThread = new Thread() {
            public void run() {
                while (Thread.currentThread() == m_jobStatusThread) {
                    Activity activity = getActivity();
                    if (activity != null) {
                        activity.runOnUiThread(new Runnable() {
                            public void run() {
                                TextView txt = m_view.findViewById(R.id.job_status);
                                if (txt != null) {
                                    if (m_printer != null) {
                                        txt.setText(m_printer.getM_printerStatus().toString());
                                    }
                                }
                            }
                        });
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        // ignore
                    }
                }
            }
        };
        m_jobStatusThread.setName("wristband job status thread");
        m_jobStatusThread.start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (m_view != null) {
            TextView txt = m_view.findViewById(R.id.ip_addr);
            txt.setText(m_printer.getIpAddr());
            txt = m_view.findViewById(R.id.port);
            txt.setText(m_printer.getPort().toString());
            Button button = m_view.findViewById(R.id.print);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    printWristbandCb(view);
                }
            });
        }
        stopConnectivityThread();
        startJobStatusThread();
        startConnectivityThread();
        startJobStatusThread();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wristband_printer_fragment_layout, container, false);
        m_view = view;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
   }
}