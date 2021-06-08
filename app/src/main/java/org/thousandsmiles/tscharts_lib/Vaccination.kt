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
import org.json.JSONException
import org.json.JSONObject

class Vaccination {
    var id = 0
    var clinic = 0
    var patient = 0
    var covid19 = false
    var covid19_doses = 0
    var covid19_date: String? = null
    var covid19_booster = false
    var covid19_booster_date: String? = null
    var dtap = false
    var dtap_date: String? = null
    var dt = false
    var dt_date: String? = null
    var hib = false
    var hib_date: String? = null
    var hepa = false
    var hepa_date: String? = null
    var hepb = false
    var hepb_date: String? = null
    var hpv = false
    var hpv_date: String? = null
    var iiv = false
    var iiv_date: String? = null
    var laiv4 = false
    var laiv4_date: String? = null
    var mmr = false
    var mmr_date: String? = null
    var menacwy = false
    var menacwy_date: String? = null
    var menb = false
    var menb_date: String? = null
    var pcv13 = false
    var pcv13_date: String? = null
    var ppsv23 = false
    var ppsv23_date: String? = null
    var ipv = false
    var ipv_date: String? = null
    var rv = false
    var rv_date: String? = null
    var tap = false
    var tap_date: String? = null
    var td = false
    var td_date: String? = null
    var varicella = false                    // in the backend, this is "var"
    var varicella_date: String? = null
    var dtap_hepb_ipv = false
    var dtap_hepb_ipv_date: String? = null
    var dtap_ipv_hib = false
    var dtap_ipv_hib_date: String? = null
    var dtap_ipv = false
    var dtap_ipv_date: String? = null
    var dtap_ipv_hib_hepb = false
    var dtap_ipv_hib_hepb_date: String? = null
    var mmvr = false
    var mmvr_date: String? = null

    override fun equals(obj: Any?): Boolean {
        var ret = true
        if (obj == null) {
            ret = false
        }

        val other = obj as Vaccination
        if (covid19 != other.covid19) {
            ret = false
        }
        if (covid19_doses != other.covid19_doses) {
            ret = false
        }
        if (covid19_date != other.covid19_date) {
            ret = false
        }
        if (covid19_booster != other.covid19_booster) {
            ret = false
        }
        if (covid19_booster_date != other.covid19_booster_date) {
            ret = false
        }
        if (dtap != other.dtap) {
            ret = false
        }
        if (dtap_date != other.dtap_date) {
            ret = false
        }
        if (dt != other.dt) {
            ret = false
        }
        if (dt_date != other.dt_date) {
            ret = false
        }
        if (hib != other.hib) {
            ret = false
        }
        if (hib_date != other.hib_date) {
            ret = false
        }
        if (hepa != other.hepa) {
            ret = false
        }
        if (hepa_date != other.hepa_date) {
            ret = false
        }
        if (hepb != other.hepb) {
            ret = false
        }
        if (hepb_date != other.hepb_date) {
            ret = false
        }
        if (hpv != other.hpv) {
            ret = false
        }
        if (hpv_date != other.hpv_date) {
            ret = false
        }
        if (iiv != other.iiv) {
            ret = false
        }
        if (iiv_date != other.iiv_date) {
            ret = false
        }
        if (laiv4 != other.laiv4) {
            ret = false
        }
        if (laiv4_date != other.laiv4_date) {
            ret = false
        }
        if (mmr != other.mmr) {
            ret = false
        }
        if (mmr_date != other.mmr_date) {
            ret = false
        }
        if (menacwy != other.menacwy) {
            ret = false
        }
        if (menacwy_date != other.menacwy_date) {
            ret = false
        }
        if (menb != other.menb) {
            ret = false
        }
        if (menb_date != other.menb_date) {
            ret = false
        }
        if (pcv13 != other.pcv13) {
            ret = false
        }
        if (pcv13_date != other.pcv13_date) {
            ret = false
        }
        if (ppsv23 != other.ppsv23) {
            ret = false
        }
        if (ppsv23_date != other.ppsv23_date) {
            ret = false
        }
        if (ipv != other.ipv) {
            ret = false
        }
        if (ipv_date != other.ipv_date) {
            ret = false
        }
        if (rv != other.rv) {
            ret = false
        }
        if (rv_date != other.rv_date) {
            ret = false
        }
        if (tap != other.tap) {
            ret = false
        }
        if (tap_date != other.tap_date) {
            ret = false
        }
        if (td != other.td) {
            ret = false
        }
        if (td_date != other.td_date) {
            ret = false
        }
        if (varicella != other.varicella) {
            ret = false
        }
        if (varicella_date != other.varicella_date) {
            ret = false
        }
        if (dtap_hepb_ipv != other.dtap_hepb_ipv) {
            ret = false
        }
        if (dtap_hepb_ipv_date != other.dtap_hepb_ipv_date) {
            ret = false
        }
        if (dtap_ipv_hib != other.dtap_ipv_hib) {
            ret = false
        }
        if (dtap_ipv_hib_date != other.dtap_ipv_hib_date) {
            ret = false
        }
        if (dtap_ipv != other.dtap_ipv) {
            ret = false
        }
        if (dtap_ipv_date != other.dtap_ipv_date) {
            ret = false
        }
        if (dtap_ipv_hib_hepb != other.dtap_ipv_hib_hepb) {
            ret = false
        }
        if (dtap_ipv_hib_hepb_date != other.dtap_ipv_hib_hepb_date) {
            ret = false
        }
        if (mmvr != other.mmvr) {
            ret = false
        }
        if (mmvr_date != other.mmvr_date) {
            ret = false
        }
        return ret
    }

    fun fromJSONObject(o: JSONObject): Int {
        var ret = 0
        try {
            id = o.getInt("id")
            clinic = o.getInt("clinic")
            patient = o.getInt("patient")

            covid19 = o.getBoolean("covid19")
            covid19_doses = o.getInt("covid19_doses")
            covid19_date = o.getString("covid19_date") ?: null
            covid19_booster = o.getBoolean("covid19_booster")
            covid19_booster_date = o.getString("covid19_booster_date") ?: null
            dtap = o.getBoolean("dtap")
            dtap_date = o.getString("dtap_date") ?: null
            dt = o.getBoolean("dt")
            dt_date = o.getString("dt_date") ?: null
            hib = o.getBoolean("hib")
            hib_date = o.getString("hib_date") ?: null
            hepa = o.getBoolean("hepa")
            hepa_date = o.getString("hepa_date") ?: null
            hepb = o.getBoolean("hepb")
            hepb_date = o.getString("hepb_date") ?: null
            hpv = o.getBoolean("hpv")
            hpv_date = o.getString("hpv_date") ?: null
            iiv = o.getBoolean("iiv")
            iiv_date = o.getString("iiv_date") ?: null
            laiv4 = o.getBoolean("laiv4")
            laiv4_date = o.getString("laiv4_date") ?: null
            mmr = o.getBoolean("mmr")
            mmr_date = o.getString("mmr_date") ?: ""
            menacwy = o.getBoolean("menacwy")
            menacwy_date = o.getString("menacwy_date") ?: null
            menb = o.getBoolean("menb")
            menb_date = o.getString("menb_date") ?: null
            pcv13 = o.getBoolean("pcv13")
            pcv13_date = o.getString("pcv13_date") ?: null
            ppsv23 = o.getBoolean("ppsv23")
            ppsv23_date = o.getString("ppsv23_date") ?: null
            ipv = o.getBoolean("ipv")
            ipv_date = o.getString("ipv_date") ?: null
            rv = o.getBoolean("rv")
            rv_date = o.getString("rv_date") ?: null
            tap = o.getBoolean("tap")
            tap_date = o.getString("tap_date") ?: null
            td = o.getBoolean("td")
            td_date = o.getString("td_date") ?: null
            varicella = o.getBoolean("varicella")                    // in the backend, this is "var"
            varicella_date = o.getString("varicella_date") ?: null
            dtap_hepb_ipv = o.getBoolean("dtap_hepb_ipv")
            dtap_hepb_ipv_date = o.getString("dtap_hepb_ipv_date") ?: null
            dtap_ipv_hib = o.getBoolean("dtap_ipv_hib")
            dtap_ipv_hib_date = o.getString("dtap_ipv_hib_date") ?: null
            dtap_ipv = o.getBoolean("dtap_ipv")
            dtap_ipv_date = o.getString("dtap_ipv_date") ?: null
            dtap_ipv_hib_hepb = o.getBoolean("dtap_ipv_hib_hepb")
            dtap_ipv_hib_hepb_date = o.getString("dtap_ipv_hib_hepb_date") ?: null
            mmvr = o.getBoolean("mmvr")
            mmvr_date = o.getString("mmvr_date") ?: null
        } catch (e: JSONException) {
            ret = -1
        }
        return ret
    }

    fun toJSONObject(includeId: Boolean): JSONObject? {
        var data: JSONObject? = JSONObject()
        try {
            if (includeId == true) {
                data!!.put("id", id)
            }
            data!!.put("clinic", CommonSessionSingleton.getInstance().clinicId)
            data.put("patient", this.patient)
            data.put("covid19", covid19)
            data.put("covid19_doses", covid19_doses)
            data.put("covid19_date", covid19_date ?: JSONObject.NULL)
            data.put("covid19_booster", covid19_booster)
            data.put("covid19_booster_date", covid19_booster_date ?: JSONObject.NULL)
            data.put("dtap", dtap)
            data.put("dtap_date", dtap_date ?: JSONObject.NULL)
            data.put("dt", dt)
            data.put("dt_date", dt_date ?: JSONObject.NULL)
            data.put("hib", hib)
            data.put("hib_date", hib_date ?: JSONObject.NULL)
            data.put("hepa", hepa)
            data.put("hepa_date", hepa_date ?: JSONObject.NULL)
            data.put("hepb", hepb)
            data.put("hepb_date", hepb_date ?: JSONObject.NULL)
            data.put("hpv", hpv)
            data.put("hpv_date", hpv_date ?: JSONObject.NULL)
            data.put("iiv", iiv)
            data.put("iiv_date", iiv_date ?: JSONObject.NULL)
            data.put("laiv4", laiv4)
            data.put("laiv4_date", laiv4_date ?: JSONObject.NULL)
            data.put("mmr", mmr)
            data.put("mmr_date", mmr_date ?: JSONObject.NULL)
            data.put("menacwy", menacwy)
            data.put("menacwy_date", menacwy_date ?: JSONObject.NULL)
            data.put("menb", menb)
            data.put("menb_date", menb_date ?: JSONObject.NULL)
            data.put("pcv13", pcv13)
            data.put("pcv13_date", pcv13_date ?: JSONObject.NULL)
            data.put("ppsv23", ppsv23)
            data.put("ppsv23_date", ppsv23_date ?: JSONObject.NULL)
            data.put("ipv", ipv)
            data.put("ipv_date", ipv_date ?: JSONObject.NULL)
            data.put("rv", rv)
            data.put("rv_date", rv_date ?: JSONObject.NULL)
            data.put("tap", tap)
            data.put("tap_date", tap_date ?: JSONObject.NULL)
            data.put("td", td)
            data.put("td_date", td_date ?: JSONObject.NULL)
            data.put("varicella", varicella)
            data.put("varicella_date", varicella_date ?: JSONObject.NULL)
            data.put("dtap_hepb_ipv", dtap_hepb_ipv)
            data.put("dtap_hepb_ipv_date", dtap_hepb_ipv_date ?: JSONObject.NULL)
            data.put("dtap_ipv_hib", dtap_ipv_hib)
            data.put("dtap_ipv_hib_date", dtap_ipv_hib_date ?: JSONObject.NULL)
            data.put("dtap_ipv", dtap_ipv)
            data.put("dtap_ipv_date", dtap_ipv_date ?: JSONObject.NULL)
            data.put("dtap_ipv_hib_hepb", dtap_ipv_hib_hepb)
            data.put("dtap_ipv_hib_hepb_date", dtap_ipv_hib_hepb_date ?: JSONObject.NULL)
            data.put("mmvr", mmvr)
            data.put("mmvr_date", mmvr_date ?: JSONObject.NULL)

        } catch (e: Exception) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
            data = null
        }
        return data
    }
}