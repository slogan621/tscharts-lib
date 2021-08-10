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

class Registration {
    var id = 0
    var clinic = 0
    var patient = 0
    var dateTimeIn: String? = null
    var dateTimeOut: String? = null

    override fun equals(obj: Any?): Boolean {
        var ret = true
        if (obj == null) {
            ret = false
        }

        val other = obj as Registration

        if (dateTimeIn != other.dateTimeIn) {
            ret = false
        }
        if (dateTimeOut != other.dateTimeOut) {
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

            dateTimeIn = o.getString("timein") ?: null
            dateTimeOut = o.getString("timeout") ?: null
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
            data.put("dateTimeIn", this.dateTimeIn)
            data.put("dateTimeIn", this.dateTimeOut)

        } catch (e: Exception) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
            data = null
        }
        return data
    }
}