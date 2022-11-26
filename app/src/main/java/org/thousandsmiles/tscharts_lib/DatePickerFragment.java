/*
 * (C) Copyright Syd Logan 2018-2022
 * (C) Copyright Thousand Smiles Foundation 2018-2022
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

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener  {

    private DatePickerDialog.OnDateSetListener m_listener = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog ret;
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        ret = new DatePickerDialog(getActivity(),
                R.style.customDatePickerStyle,
                this,
                year, month, day);
        ret.setCancelable(false);
        ret.setCanceledOnTouchOutside(false);
        return ret;
    }

    public void setListeningActivity(DatePickerDialog.OnDateSetListener listener)
    {
        m_listener = listener;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (m_listener != null) {
            m_listener.onDateSet(view, year, month, day);
        }
    }
}