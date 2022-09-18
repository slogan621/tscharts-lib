/*
 * (C) Copyright Syd Logan 2021
 * (C) Copyright Thousand Smiles Foundation 2021
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

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class COVIDTypeDialogFragment extends DialogFragment {

    private View m_view;
    Vaccination m_vaccineData;
    private TextView m_textView;
    private NumberPicker m_picker;
    private String[] m_covid19Types;
    private CommonSessionSingleton m_sess = CommonSessionSingleton.getInstance();

    private void getVaccineData() {
        m_vaccineData = m_sess.getPatientVaccination();
    }

    public void init() {
        getVaccineData();
    }

    public void setTextField(TextView view)
    {
        m_textView = view;
    }

    private String getTextField()
    {
        String ret = new String();

        if (m_textView != null) {
            ret = m_textView.getText().toString();
        }
        return ret;
    }

    private String getSelectedState()
    {
        int selected = m_picker.getValue();
        String state = m_covid19Types[selected];
        return state;
    }

    private void setTextField(String str)
    {
        if (m_textView != null) {
            m_textView.setText(str);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        m_view = inflater.inflate(R.layout.select_covid19_dialog, null);
        m_picker = (NumberPicker) m_view.findViewById(R.id.covid19_type_picker);
        m_picker.setWrapSelectorWheel(false);
        String str = getTextField();

        ArrayList<String> covid19Types = m_sess.getCOVID19TypesList();
        m_covid19Types = covid19Types.toArray(new String[0]);
        if (covid19Types != null && covid19Types.size() > 0) {
            if (m_picker != null) {
                String choice;
                if (str.length() > 0) {
                    choice = str;           // aleady has a value
                } else {
                    choice = m_vaccineData.getCovid19_type();  // get configured value, if any
                }
                m_picker.setMinValue(0);
                m_picker.setMaxValue(covid19Types.size() - 1);
                m_picker.setDisplayedValues(m_covid19Types);
                for (int i = 0; i < covid19Types.size(); i++) {
                    if (choice != null && choice.equals(covid19Types.get(i))) {
                        m_picker.setValue(i);
                        break;
                    }
                }
            }
        }

        builder.setView(m_view)
                // Add action buttons
                .setPositiveButton(R.string.select_covid19_type_select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String state = getSelectedState();
                        setTextField(state);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.select_covid19_type_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        Dialog ret = builder.create();
        ret.setCanceledOnTouchOutside(false);
        ret.setCancelable(false);
        ret.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ret.setTitle(R.string.title_select_covid19_type_dialog);
        return ret;
    }
}