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
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.text.DateFormatSymbols
import java.util.*

class AppVaccineFragment : Fragment(), OnDateSetListener {
    private var m_activity: Activity? = null
    private var m_commonSessionSingleton: CommonSessionSingleton? = null
    private var m_vaccination: Vaccination? = null
    private var m_dirty = false
    private var m_view: View? = null
    private lateinit var m_nextActivity : Class<*>
    private var m_curTextView: Int = 0

    fun setNextActivity(activity: Class<*>) {
        m_nextActivity = activity
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            m_activity = context as Activity
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
        tx = m_view!!.findViewById<View>(R.id.vaccine_covid19_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.covid19_date?.let { military.getDobMilitary(m_activity, it) })
        }
        rb = m_view!!.findViewById<View>(R.id.radio_button_vaccine_covid19_dosages_zero) as RadioButton
        if (rb != null) {
            rb.isChecked = m_vaccination!!.covid19_doses == 0
        }
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
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.dtap
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.dtap_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_dt) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.dt
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dt_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.dt_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_hib) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.hib
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_hib_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.hib_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_hepa) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.hepa
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_hepa_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.hepa_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_hepb) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.hepb
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_hepb_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.hepb_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_hpv) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.hpv
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_hpv_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.hpv_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_iiv) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.iiv
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_iiv_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.iiv_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_laiv4) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.laiv4
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_laiv4_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.laiv4_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_mmr) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.mmr
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_mmr_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.mmr_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_menacwy) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.menacwy
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_menacwy_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.menacwy_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_menb) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.menb
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_menb_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.menb_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_pcv13) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.pcv13
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_pcv13_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.pcv13_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_ppsv23) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.ppsv23
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_ppsv23_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.ppsv23_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_ipv) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.ipv
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_ipv_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.ipv_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_rv) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.rv
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_rv_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.rv_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_tap) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.tap
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_tap_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.tap_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_td) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.td
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_td_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.td_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_vari) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.varicella
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_vari_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.varicella_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap_hepb_ipv) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.dtap_hepb_ipv
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_hepb_ipv_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.dtap_hepb_ipv_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_hib) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.dtap_ipv_hib
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_hib_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.dtap_ipv_hib_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.dtap_ipv
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.dtap_ipv_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_hib_hepb) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.dtap_ipv_hib_hepb
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_hib_hepb_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.dtap_ipv_hib_hepb_date?.let { military.getDobMilitary(m_activity, it) })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_mmvr) as Switch
        if (sw != null) {
            sw.isChecked = m_vaccination!!.mmvr
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_mmvr_date) as TextView
        if (tx != null) {
            tx.setText(m_vaccination!!.mmvr_date?.let { military.getDobMilitary(m_activity, it) })
        }

        clearDirty()
    }

    private fun setDirty() {
        m_dirty = true
    }

    private fun clearDirty() {
        m_dirty = false
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
        //m_curTextView?.text = dateString // XXX m_curTextView is a somewhat hacky way of associating
                                         // this callback to a text view, but should work since only
                                         // one should be active at any time. Better option would be
                                         // a view class that wraps all of this.
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        val c = Calendar.getInstance()
        c[year, month] = day
        setDate(c)
    }

    private fun setDatePickerListeners() {
        var tx: TextView

        tx = m_view!!.findViewById<View>(R.id.vaccine_covid19_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_covid19_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_covid19_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_covid19_booster_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_covid19_booster_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_covid19_booster_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_dtap_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_dtap_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_dt_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_dt_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_dt_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_hib_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_hib_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_hib_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_hepa_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_hepa_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_hepa_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_hepb_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_hepb_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_hepb_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_hpv_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_hpv_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_hpv_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_iiv_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_iiv_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_iiv_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_laiv4_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_laiv4_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_laiv4_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_mmr_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_mmr_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_mmr_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_menacwy_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_menacwy_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_menacwy_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_menb_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_menb_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_menb_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_pcv13_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_pcv13_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_pcv13_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_ppsv23_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_ppsv23_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_ppsv23_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_ipv_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_ipv_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_ipv_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_rv_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_rv_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_rv_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_tap_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_tap_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_tap_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_td_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_td_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_td_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_vari_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_vari_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_vari_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_hepb_ipv_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_dtap_hepb_ipv_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_dtap_hepb_ipv_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_hib_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_dtap_ipv_hib_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_dtap_ipv_hib_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_dtap_ipv_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_dtap_ipv_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_hib_hepb_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_dtap_ipv_hib_hepb_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_dtap_ipv_hib_hepb_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }

        tx = m_view!!.findViewById<View>(R.id.vaccine_mmvr_date) as TextView
        if (tx != null) {
            tx.setShowSoftInputOnFocus(false)
            tx.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    val fragment = DatePickerFragment()
                    m_curTextView = R.id.vaccine_mmvr_date
                    fragment.setListeningActivity(this@AppVaccineFragment)
                    fragment.show(m_activity!!.fragmentManager, "date")
                }
            })
            tx.setOnClickListener(View.OnClickListener {
                val fragment = DatePickerFragment()
                m_curTextView = R.id.vaccine_mmvr_date
                fragment.setListeningActivity(this@AppVaccineFragment)
                fragment.show(m_activity!!.fragmentManager, "date")
            })
        }
    }

    private fun setViewDirtyListeners() {

        var sw: Switch
        var tx: TextView
        var rb: RadioButton

        sw = m_view!!.findViewById<View>(R.id.vaccine_covid19) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_covid19_date) as TextView
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
        rb = m_view!!.findViewById<View>(R.id.radio_button_vaccine_covid19_dosages_zero) as RadioButton
        if (rb != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        rb = m_view!!.findViewById<View>(R.id.radio_button_vaccine_covid19_dosages_one) as RadioButton
        if (rb != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        rb = m_view!!.findViewById<View>(R.id.radio_button_vaccine_covid19_dosages_two) as RadioButton
        if (rb != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        sw = m_view!!.findViewById<View>(R.id.vaccine_covid19_booster) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_covid19_booster_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_dt) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dt_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_hib) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_hib_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_hepa) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_hepa_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_hepb) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_hepb_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_hpv) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_hpv_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_iiv) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_iiv_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_laiv4) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_laiv4_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_mmr) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_mmr_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_menacwy) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_menacwy_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_menb) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_menb_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_pcv13) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_pcv13_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_ppsv23) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_ppsv23_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_ipv) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_ipv_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_rv) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_rv_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_tap) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_tap_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_td) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_td_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_vari) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_vari_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap_hepb_ipv) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_hepb_ipv_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_hib) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_hib_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_hib_hepb) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_dtap_ipv_hib_hepb_date) as TextView
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
        sw = m_view!!.findViewById<View>(R.id.vaccine_mmvr) as Switch
        if (sw != null) {
            sw.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    setDirty()
                }
            })
        }
        tx = m_view!!.findViewById<View>(R.id.vaccine_mmvr_date) as TextView
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
        tx = m_view!!.findViewById<View>(R.id.vaccine_covid19_date) as TextView
        if (tx != null) {
            var v: String = tx.getText().toString()
            if (v != null && v != "" && v != "null") {
                vaccinations.covid19_date = military.fromDobMilitary(m_activity, v)
            } else {
                vaccinations.covid19_date = null
            }
        }
        rb = m_view!!.findViewById<View>(R.id.radio_button_vaccine_covid19_dosages_zero) as RadioButton
        if (rb != null) {
            if (rb.isChecked()) {
                vaccinations.covid19_doses = 0
            }
        }
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
        if (m_commonSessionSingleton?.getIsNewPatient() === false) {
            m_vaccination = m_commonSessionSingleton?.getPatientVaccination()
            if (m_vaccination == null) {
                VaccinationDataFromREST
            } else {
                copyVaccinationDataToUI()
            }
        } else {
            m_vaccination = m_commonSessionSingleton?.getNewPatientVaccination()
            copyVaccinationDataToUI()
        }
        setViewDirtyListeners()
        setDatePickerListeners()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.app_vaccine_layout, container, false)
        m_view = view

        m_commonSessionSingleton = CommonSessionSingleton.getInstance()

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
}