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

package org.thousandsmiles.tscharts_lib

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.*
import java.text.DateFormatSymbols
import java.util.*

class AppVaccineFragment : FormDirtyNotifierFragment(), OnDateSetListener, FormSaveListener, PatientCheckoutListener {
    private var m_activity: FormSaveAndPatientCheckoutNotifierActivity? = null
    private var m_commonSessionSingleton: CommonSessionSingleton? = null
    private var m_vaccination: Vaccination? = null
    private var m_dirty = false
    private var m_view: View? = null
    private lateinit var m_nextActivity : Class<*>
    private val m_listeners: ArrayList<FormDirtyListener?> = ArrayList<FormDirtyListener?>()
    private var m_curTextView: Int = 0
    private var m_switchEnableDependencies = arrayOf<SwitchEnableDependencies>(
        SwitchEnableDependencies(R.id.vaccine_covid19, arrayOf<Int>(R.id.vaccine_covid19_date, R.id.vaccine_covid19_type,
            R.id.radio_button_vaccine_covid19_dosages_one, R.id.radio_button_vaccine_covid19_dosages_two)),
        SwitchEnableDependencies(R.id.vaccine_covid19_booster, arrayOf<Int>(R.id.vaccine_covid19_booster_date)),
        SwitchEnableDependencies(R.id.vaccine_dtap, arrayOf<Int>(R.id.vaccine_dtap_date)),
        SwitchEnableDependencies(R.id.vaccine_dt, arrayOf<Int>(R.id.vaccine_dt_date)),
        SwitchEnableDependencies(R.id.vaccine_hib, arrayOf<Int>(R.id.vaccine_hib_date)),
        SwitchEnableDependencies(R.id.vaccine_hepa, arrayOf<Int>(R.id.vaccine_hepa_date)),
        SwitchEnableDependencies(R.id.vaccine_hepb, arrayOf<Int>(R.id.vaccine_hepb_date)),
        SwitchEnableDependencies(R.id.vaccine_hpv, arrayOf<Int>(R.id.vaccine_hpv_date)),
        SwitchEnableDependencies(R.id.vaccine_iiv, arrayOf<Int>(R.id.vaccine_iiv_date)),
        SwitchEnableDependencies(R.id.vaccine_laiv4, arrayOf<Int>(R.id.vaccine_laiv4_date)),
        SwitchEnableDependencies(R.id.vaccine_mmr, arrayOf<Int>(R.id.vaccine_mmr_date)),
        SwitchEnableDependencies(R.id.vaccine_menacwy, arrayOf<Int>(R.id.vaccine_menacwy_date)),
        SwitchEnableDependencies(R.id.vaccine_menb, arrayOf<Int>(R.id.vaccine_menb_date)),
        SwitchEnableDependencies(R.id.vaccine_pcv13, arrayOf<Int>(R.id.vaccine_pcv13_date)),
        SwitchEnableDependencies(R.id.vaccine_ppsv23, arrayOf<Int>(R.id.vaccine_ppsv23_date)),
        SwitchEnableDependencies(R.id.vaccine_ipv, arrayOf<Int>(R.id.vaccine_ipv_date)),
        SwitchEnableDependencies(R.id.vaccine_rv, arrayOf<Int>(R.id.vaccine_rv_date)),
        SwitchEnableDependencies(R.id.vaccine_tap, arrayOf<Int>(R.id.vaccine_tap_date)),
        SwitchEnableDependencies(R.id.vaccine_td, arrayOf<Int>(R.id.vaccine_td_date)),
        SwitchEnableDependencies(R.id.vaccine_vari, arrayOf<Int>(R.id.vaccine_vari_date)),
        SwitchEnableDependencies(R.id.vaccine_dtap_hepb_ipv, arrayOf<Int>(R.id.vaccine_dtap_hepb_ipv_date)),
        SwitchEnableDependencies(R.id.vaccine_dtap_ipv_hib, arrayOf<Int>(R.id.vaccine_dtap_ipv_hib_date)),
        SwitchEnableDependencies(R.id.vaccine_dtap_ipv, arrayOf<Int>(R.id.vaccine_dtap_ipv_date)),
        SwitchEnableDependencies(R.id.vaccine_dtap_ipv_hib_hepb, arrayOf<Int>(R.id.vaccine_dtap_ipv_hib_hepb_date)),
        SwitchEnableDependencies(R.id.vaccine_mmvr, arrayOf<Int>(R.id.vaccine_mmvr_date)),
    )

    private var m_switchTextPairs = arrayOf<SwitchTextPairs>(
        SwitchTextPairs(R.id.vaccine_covid19, R.id.vaccine_covid19_date),
        SwitchTextPairs(R.id.vaccine_covid19_booster, R.id.vaccine_covid19_booster_date),
        SwitchTextPairs(R.id.vaccine_dtap, R.id.vaccine_dtap_date),
        SwitchTextPairs(R.id.vaccine_dt, R.id.vaccine_dt_date),
        SwitchTextPairs(R.id.vaccine_hib, R.id.vaccine_hib_date),
        SwitchTextPairs(R.id.vaccine_hepa, R.id.vaccine_hepa_date),
        SwitchTextPairs(R.id.vaccine_hepb, R.id.vaccine_hepb_date),
        SwitchTextPairs(R.id.vaccine_hpv, R.id.vaccine_hpv_date),
        SwitchTextPairs(R.id.vaccine_iiv, R.id.vaccine_iiv_date),
        SwitchTextPairs(R.id.vaccine_laiv4, R.id.vaccine_laiv4_date),
        SwitchTextPairs(R.id.vaccine_mmr, R.id.vaccine_mmr_date),
        SwitchTextPairs(R.id.vaccine_menacwy, R.id.vaccine_menacwy_date),
        SwitchTextPairs(R.id.vaccine_menb, R.id.vaccine_menb_date),
        SwitchTextPairs(R.id.vaccine_pcv13, R.id.vaccine_pcv13_date),
        SwitchTextPairs(R.id.vaccine_ppsv23, R.id.vaccine_ppsv23_date),
        SwitchTextPairs(R.id.vaccine_ipv, R.id.vaccine_ipv_date),
        SwitchTextPairs(R.id.vaccine_rv, R.id.vaccine_rv_date),
        SwitchTextPairs(R.id.vaccine_tap, R.id.vaccine_tap_date),
        SwitchTextPairs(R.id.vaccine_td, R.id.vaccine_td_date),
        SwitchTextPairs(R.id.vaccine_vari, R.id.vaccine_vari_date),
        SwitchTextPairs(R.id.vaccine_dtap_hepb_ipv, R.id.vaccine_dtap_hepb_ipv_date),
        SwitchTextPairs(R.id.vaccine_dtap_ipv_hib, R.id.vaccine_dtap_ipv_hib_date),
        SwitchTextPairs(R.id.vaccine_dtap_ipv, R.id.vaccine_dtap_ipv_date),
        SwitchTextPairs(R.id.vaccine_dtap_ipv_hib_hepb, R.id.vaccine_dtap_ipv_hib_hepb_date),
        SwitchTextPairs(R.id.vaccine_mmvr, R.id.vaccine_mmvr_date),
    )

    fun setNextActivity(activity: Class<*>) {
        m_nextActivity = activity
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            m_activity = context as FormSaveAndPatientCheckoutNotifierActivity
            VaccinationDataFromREST
            try {
                (m_activity as FormSavePublisher).subscribeSave(this as FormSaveListener)
                (m_activity as PatientCheckoutPublisher).subscribeCheckout(this as PatientCheckoutListener)
            } catch (e: Exception) {
            }
        }
    }

    private fun validateFields(): Boolean {
        var ret = true

        return ret
    }

    fun handleNextButtonPress(v: View?) {
        val vaccinations: Vaccination? = copyVaccinationDataFromUI()
        val valid: Boolean
        valid = validateFields()
        if (valid == false) {
            val builder = AlertDialog.Builder(
                requireActivity()
            )
            builder.setTitle(m_activity?.getString(R.string.title_missing_patient_data))
            builder.setMessage(m_activity?.getString(R.string.msg_please_enter_required_patient_data))
            builder.setPositiveButton(
                m_activity?.getString(R.string.button_ok),
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {}
                })
            val alert = builder.create()
            alert.show()
        } else if (m_dirty || vaccinations?.equals(m_vaccination) === false) {
            val builder = AlertDialog.Builder(
                requireActivity()
            )
            builder.setTitle(m_activity?.getString(R.string.title_unsaved_vaccinations))
            builder.setMessage(m_activity?.getString(R.string.msg_save_vaccinations))
            builder.setPositiveButton(
                m_activity?.getString(R.string.button_yes),
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        m_commonSessionSingleton?.updatePatientVaccination(vaccinations)
                        dialog.dismiss()
                        startActivity(Intent(m_activity, m_nextActivity))
                        m_activity?.finish()
                    }
                })
            builder.setNegativeButton(
                m_activity?.getString(R.string.button_no),
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        dialog.dismiss()
                    }
                })
            val alert = builder.create()
            alert.show()
        } else {
            startActivity(Intent(m_activity, m_nextActivity))
            m_activity?.finish()
        }
    }

    private fun copyVaccinationDataToUI() {
        var sw: Switch
        var tx: TextView
        var rb: RadioButton
        var bv: Boolean
        var military: MilitaryDate = MilitaryDate()

        if (m_vaccination == null) {
            return
        }

        sw = m_view!!.findViewById<View>(R.id.vaccine_covid19) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.covid19
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_covid19_type) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.covid19_type?.let { it })
            tx.setEnabled(sw.isChecked)
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_covid19_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.covid19_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        /*
        rb = m_view!!.findViewById<View>(R.id.radio_button_vaccine_covid19_dosages_zero) as RadioButton
        if (rb != null) {
            rb.isChecked = m_vaccination!!.covid19_doses == 0
        }

         */
        rb = m_view!!.findViewById<View>(R.id.radio_button_vaccine_covid19_dosages_one) as RadioButton
        if (rb != null) {
            rb.isChecked = m_vaccination!!.covid19_doses == 1
        }
        rb = m_view!!.findViewById<View>(R.id.radio_button_vaccine_covid19_dosages_two) as RadioButton
        if (rb != null) {
            rb.isChecked = m_vaccination!!.covid19_doses == 2
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_covid19_booster) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.covid19_booster
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_covid19_booster_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.covid19_booster_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.dtap
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.dtap_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_dt) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.dt
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dt_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.dt_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_hib) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.hib
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_hib_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.hib_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_hepa) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.hepa
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_hepa_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.hepa_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_hepb) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.hepb
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_hepb_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.hepb_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_hpv) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.hpv
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_hpv_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.hpv_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_iiv) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.iiv
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_iiv_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.iiv_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_laiv4) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.laiv4
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_laiv4_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.laiv4_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_mmr) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.mmr
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_mmr_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.mmr_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_menacwy) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.menacwy
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_menacwy_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.menacwy_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_menb) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.menb
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_menb_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.menb_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_pcv13) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.pcv13
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_pcv13_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.pcv13_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_ppsv23) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.ppsv23
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_ppsv23_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.ppsv23_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_ipv) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.ipv
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_ipv_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.ipv_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_rv) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.rv
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_rv_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.rv_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_tap) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.tap
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_tap_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.tap_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_td) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.td
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_td_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.td_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_vari) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.varicella
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_vari_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.varicella_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap_hepb_ipv) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.dtap_hepb_ipv
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_hepb_ipv_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.dtap_hepb_ipv_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_hib) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.dtap_ipv_hib
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_hib_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.dtap_ipv_hib_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.dtap_ipv
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.dtap_ipv_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_hib_hepb) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.dtap_ipv_hib_hepb
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_hib_hepb_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.dtap_ipv_hib_hepb_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_mmvr) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.mmvr
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_mmvr_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.mmvr_date?.let { military.getDobMilitary(m_activity, it) })
            tx.setEnabled(sw.isChecked)
        }

        clearDirty()
    }

    private fun setDirty() {
        m_dirty = true
        for (i in m_listeners.indices) {
            m_listeners[i]?.dirty(true)
        }
    }

    private fun clearDirty() {
        m_dirty = false
        for (i in m_listeners.indices) {
            m_listeners[i]?.dirty(false)
        }
    }

    private fun setDate(calendar: Calendar) {
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val day = calendar[Calendar.DAY_OF_MONTH]
        val dateString = String.format(
            "%02d%s%04d",
            day,
            DateFormatSymbols().months[month - 1].substring(0, 3).toUpperCase(),
            year
        )
        (m_view!!.findViewById<View>(m_curTextView) as TextView).text = dateString
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        val c = Calendar.getInstance()
        c[year, month] = day
        setDate(c)
    }

    private fun setDatePickerListeners() {
        var tx: TextView

        for (x in m_switchTextPairs) {
            tx = m_view!!.findViewById<View>(x.tx) as TextView
            if (tx != null) {
                tx.setShowSoftInputOnFocus(false)
                tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                    if (hasFocus) {
                        val fragment = DatePickerFragment()
                        m_curTextView = x.tx
                        fragment.setListeningActivity(this@AppVaccineFragment)
                        fragment.show(m_activity!!.fragmentManager, "date")
                    }
                })
                tx.setOnClickListener(View.OnClickListener {
                    val fragment = DatePickerFragment()
                    m_curTextView = x.tx
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                })
            }
        }
    }

    class SwitchEnableDependencies(sw: Int, dependencies: Array<Int>) {
        var sw: Int = sw
        var dependencies: Array<Int> = dependencies
    }

    class SwitchTextPairs(sw: Int, tx: Int) {
        var sw: Int = sw
        var tx: Int = tx
    }

    private fun setViewDirtyListeners() {

        var sw: Switch
        var tx: TextView
        var rb: RadioButton

        for (x in m_switchEnableDependencies) {
            sw = m_view!!.findViewById<View>(x.sw) as Switch
            if (sw != null) {
                sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                        setDirty()
                        if (isChecked) {
                            for (y in x.dependencies) {
                                (m_view!!.findViewById<View>(y) as View).setEnabled(
                                    true
                                )
                            }
                        } else {
                            for (y in x.dependencies) {
                                (m_view!!.findViewById<View>(y) as View).setEnabled(
                                    false
                                )
                            }
                        }
                    }
                })
            }
            for (y in x.dependencies) {
                tx = m_view!!.findViewById<View>(y) as TextView
                if (tx != null) {
                    tx.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable) {}
                        override fun beforeTextChanged(
                            s: CharSequence, start: Int,
                            count: Int, after: Int
                        ) {
                        }

                        override fun onTextChanged(
                            s: CharSequence, start: Int,
                            before: Int, count: Int
                        ) {
                            setDirty()
                        }
                    })
                }
            }
        }

        /*
        rb = m_view!!.findViewById<View>(R.id.radio_button_vaccine_covid19_dosages_zero) as RadioButton
        if (rb != null) {
            rb.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        */

        rb = m_view!!.findViewById<View>(R.id.radio_button_vaccine_covid19_dosages_one) as RadioButton
        if (rb != null) {
            rb.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        rb = m_view!!.findViewById<View>(R.id.radio_button_vaccine_covid19_dosages_two) as RadioButton
        if (rb != null) {
            rb.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }

        val tx1 = m_view!!.findViewById<View>(R.id.vaccine_covid19_type) as TextView
        if (tx1 != null) {

            tx1.showSoftInputOnFocus = false
            tx1.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val mld = COVIDTypeDialogFragment()
                    mld.init()
                    mld.setTextField(tx1)
                    mld.show(
                        fragmentManager,
                        m_activity!!.getString(R.string.title_covid19_type_dialog)
                    )
                }
            }
            tx1.setOnClickListener { view
                    val mld = COVIDTypeDialogFragment()
                    mld.init()
                    mld.setTextField(tx1)
                    mld.show(
                        fragmentManager,
                        m_activity!!.getString(R.string.title_covid19_type_dialog)
                    )
                }
            };
    }

    private fun copyVaccinationDataFromUI(): Vaccination? {
        var sw: Switch
        var tx: TextView
        var rb: RadioButton

        var vaccinations: Vaccination?
        vaccinations = m_vaccination
        if (vaccinations == null) {
            vaccinations = Vaccination()
            m_vaccination = vaccinations
        }

        val military: MilitaryDate = MilitaryDate()

        sw = m_view!!.findViewById<View>(R.id.vaccine_covid19) as Switch
        if (sw != null) {
            vaccinations.covid19 = sw.isChecked()
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_covid19_type) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.covid19_type = v
            } else {
                vaccinations.covid19_type = null
            }
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_covid19_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.covid19_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.covid19_date = null
            }
        }

        /*
        rb = m_view!!.findViewById<View>(R.id.radio_button_vaccine_covid19_dosages_zero) as RadioButton
        if (rb != null) {
            if (rb.isChecked()) {
                vaccinations.covid19_doses = 0
            }
        }
         */
        rb = m_view!!.findViewById<View>(R.id.radio_button_vaccine_covid19_dosages_one) as RadioButton
        if (rb != null) {
            if (rb.isChecked()) {
                vaccinations.covid19_doses = 1
            }
        }
        rb = m_view!!.findViewById<View>(R.id.radio_button_vaccine_covid19_dosages_two) as RadioButton
        if (rb != null) {
            if (rb.isChecked()) {
                vaccinations.covid19_doses = 2
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_covid19_booster) as Switch
        if (sw != null) {
            vaccinations.covid19_booster = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_covid19_booster_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.covid19_booster_date = military.fromDobMilitary(m_activity, v)
            }
            else {
                vaccinations.covid19_booster_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap) as Switch
        if (sw != null) {
            vaccinations.dtap = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.dtap_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.dtap_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_dt) as Switch
        if (sw != null) {
            vaccinations.dt = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dt_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.dt_date = military.fromDobMilitary(m_activity, v)
            }  else {
                vaccinations.dt_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_hib) as Switch
        if (sw != null) {
            vaccinations.hib = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_hib_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.hib_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.hib_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_hepa) as Switch
        if (sw != null) {
            vaccinations.hepa = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_hepa_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.hepa_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.hepa_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_hepb) as Switch
        if (sw != null) {
            vaccinations.hepb = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_hepb_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.hepb_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.hepb_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_hpv) as Switch
        if (sw != null) {
            vaccinations.hpv = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_hpv_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.hpv_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.hpv_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_iiv) as Switch
        if (sw != null) {
            vaccinations.iiv = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_iiv_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.iiv_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.iiv_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_laiv4) as Switch
        if (sw != null) {
            vaccinations.laiv4 = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_laiv4_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.laiv4_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.laiv4_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_mmr) as Switch
        if (sw != null) {
            vaccinations.mmr = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_mmr_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.mmr_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.mmr_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_menacwy) as Switch
        if (sw != null) {
            vaccinations.menacwy = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_menacwy_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.menacwy_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.menacwy_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_menb) as Switch
        if (sw != null) {
            vaccinations.menb = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_menb_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.menb_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.menb_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_pcv13) as Switch
        if (sw != null) {
            vaccinations.pcv13 = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_pcv13_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.pcv13_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.pcv13_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_ppsv23) as Switch
        if (sw != null) {
            vaccinations.ppsv23 = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_ppsv23_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.ppsv23_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.ppsv23_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_ipv) as Switch
        if (sw != null) {
            vaccinations.ipv = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_ipv_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.ipv_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.ipv_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_rv) as Switch
        if (sw != null) {
            vaccinations.rv = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_rv_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.rv_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.rv_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_tap) as Switch
        if (sw != null) {
            vaccinations.tap = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_tap_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.tap_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.tap_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_td) as Switch
        if (sw != null) {
            vaccinations.td = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_td_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.td_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.td_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_vari) as Switch
        if (sw != null) {
            vaccinations.varicella = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_vari_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.varicella_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.varicella_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap_hepb_ipv) as Switch
        if (sw != null) {
            vaccinations.dtap_hepb_ipv = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_hepb_ipv_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.dtap_hepb_ipv_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.dtap_hepb_ipv_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_hib) as Switch
        if (sw != null) {
            vaccinations.dtap_ipv_hib = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_hib_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.dtap_ipv_hib_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.dtap_ipv_hib_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv) as Switch
        if (sw != null) {
            vaccinations.dtap_ipv = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.dtap_ipv_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.dtap_ipv_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_hib_hepb) as Switch
        if (sw != null) {
            vaccinations.dtap_ipv_hib_hepb = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_hib_hepb_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.dtap_ipv_hib_hepb_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.dtap_ipv_hib_hepb_date = null
            }
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_mmvr) as Switch
        if (sw != null) {
            vaccinations.mmvr = sw.isChecked()
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_mmvr_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.mmvr_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.mmvr_date = null
            }
        }
        return vaccinations
    }

    private val VaccinationDataFromREST: Unit
        private get() {
            m_commonSessionSingleton = CommonSessionSingleton.getInstance()
            Thread {
                val thread: Thread = object : Thread() {
                    override fun run() {
                        m_vaccination = m_commonSessionSingleton?.getVaccination(
                            m_commonSessionSingleton!!.getClinicId(),
                            m_commonSessionSingleton!!.getPatientId(),
                        )
                        if (m_vaccination == null) {
                            m_activity!!.runOnUiThread(Runnable {
                                m_commonSessionSingleton?.resetPatientVaccination()
                                m_vaccination = m_commonSessionSingleton?.getNewPatientVaccination()
                                copyVaccinationDataToUI()
                                m_commonSessionSingleton?.setIsNewVaccination(true)
                                Toast.makeText(
                                    m_activity,
                                    m_activity!!.getString(R.string.msg_unable_to_get_vaccination),
                                    Toast.LENGTH_SHORT
                                ).show()
                            })
                        } else {
                            m_activity?.runOnUiThread(Runnable {
                                Toast.makeText(
                                    m_activity,
                                    m_activity!!.getString(R.string.msg_successfully_got_vaccination),
                                    Toast.LENGTH_SHORT
                                ).show()
                                copyVaccinationDataToUI()
                            })
                        }
                    }
                }
                thread.start()
            }.start()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    private fun initialize() {
        setViewDirtyListeners()
        setDatePickerListeners()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    private fun getCOVID19Types() {
        Thread { m_commonSessionSingleton?.getCOVID19Types() }.start()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.app_vaccine_layout, container, false)
        m_view = view

        m_commonSessionSingleton = CommonSessionSingleton.getInstance()
        getCOVID19Types()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    companion object {
        fun newInstance(): AppVaccineFragment {
            return AppVaccineFragment()
        }
    }

    private fun validate(): Boolean {
        return validateFields()
    }

    fun updateVaccinations() {
        val ret = false
        val thread: Thread = object : Thread() {
            override fun run() {
                // note we use session context because this may be called after onPause()
                val rest = VaccinationREST(m_commonSessionSingleton?.getContext())
                val lock: Any
                val status: Int
                if (m_commonSessionSingleton?.getIsNewVaccination() === true) {
                    lock = rest.createVaccination(copyVaccinationDataFromUI())
                } else {
                    lock = rest.updateVaccination(copyVaccinationDataFromUI())
                }
                synchronized(lock) {
                    // we loop here in case of race conditions or spurious interrupts
                    while (true) {
                        try {
                            (lock as java.lang.Object).wait()
                            break
                        } catch (e: InterruptedException) {
                            continue
                        }
                    }
                }
                status = rest.status
                if (status != 200) {
                    val handler = Handler(Looper.getMainLooper())
                    handler.post {
                        Toast.makeText(
                            m_activity,
                            m_activity!!.getString(R.string.msg_unable_to_save_vaccination),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    m_commonSessionSingleton?.setIsNewVaccination(false)
                    val handler = Handler(Looper.getMainLooper())
                    handler.post {
                        clearDirty()
                        m_vaccination = copyVaccinationDataFromUI()
                        Toast.makeText(
                            m_activity,
                            m_activity!!.getString(R.string.msg_successfully_saved_vaccination),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
        thread.start()
    }

    private fun saveInternal(showReturnToClinic: Boolean): Boolean {
        val ret: Boolean = validate()
        if (ret == true) {
            val builder = androidx.appcompat.app.AlertDialog.Builder(
                m_activity!!
            )
            builder.setTitle(m_activity!!.getString(R.string.title_unsaved_vaccinations))
            builder.setMessage(m_activity!!.getString(R.string.msg_save_vaccinations))
            builder.setPositiveButton(
                m_activity!!.getString(R.string.button_yes)
            ) { dialog, which ->
                updateVaccinations()
                if (showReturnToClinic == true) {
                    notifyReadyForCheckout(true)
                } else {
                    notifySaveDone(true)
                }
                dialog.dismiss()
            }
            builder.setNegativeButton(
                m_activity!!.getString(R.string.button_no)
            ) { dialog, which ->
                if (showReturnToClinic == true) {
                    notifyReadyForCheckout(false)
                } else {
                    notifySaveDone(false)
                }
                dialog.dismiss()
            }
            val alert = builder.create()
            alert.show()
        }
        return ret
    }

    override fun save(): Boolean {
        var ret = true
        if (m_dirty) {
            ret = saveInternal(false)
        } else {
            notifySaveDone(true);
        }
        return ret
    }

    override fun checkout(): Boolean {
        if (m_dirty) {
            saveInternal(true)
        } else {
            notifyReadyForCheckout(true)
        }
        return true
    }

    fun notifyReadyForCheckout(success: Boolean) {
        m_activity?.fragmentReadyForCheckout(success)
    }

    fun notifySaveDone(success: Boolean) {
        m_activity?.fragmentSaveDone(success)
    }

    override fun subscribeDirty(instance: FormDirtyListener?) {
        m_listeners.add(instance)
    }

    override fun unsubscribeDirty(instance: FormDirtyListener?) {
        m_listeners.remove(instance)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (m_activity != null) {
            m_activity!!.unsubscribeSave(this)
            m_activity!!.unsubscribeCheckout(this)
        }
    }
}