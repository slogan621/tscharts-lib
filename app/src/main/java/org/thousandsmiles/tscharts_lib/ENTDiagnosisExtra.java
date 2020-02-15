/*
 * (C) Copyright Syd Logan 2019-2020
 * (C) Copyright Thousand Smiles Foundation 2019-2020
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ENTDiagnosisExtra implements Serializable {

    private int m_id;
    private int m_diagnosis;
    private String m_name = "";
    private String m_value = "";

    public int fromJSONObject(JSONObject o)
    {
        int ret = 0;

        try {
            setId(o.getInt("id"));
            setDiagnosis(o.getInt("entdiagnosis"));
            setName(o.getString("name"));
            setValue(o.getString("value"));

        } catch (JSONException e) {
            ret = -1;
        }
        return ret;
    }

    public JSONObject toJSONObject(boolean includeId)
    {
        JSONObject data = new JSONObject();
        try {
            if (includeId == true) {
                data.put("id", this.getId());
            }

            data.put("diagnosis", getDiagnosis());
            data.put("name", getName());
            data.put("value", getValue());
        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
            data = null;
        }
        return data;
    }


    public int getDiagnosis() {
        return m_diagnosis;
    }

    public void setDiagnosis(int diagnosis) {
        this.m_diagnosis = diagnosis;
    }

    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        this.m_id = id;
    }

    public String getName() {
        return m_name;
    }

    public void setName(String val) {
        this.m_name = val;
    }

    public String getValue() {
        return m_value;
    }

    public void setValue(String val) {
        this.m_value = val;
    }


    public ENTDiagnosisExtra() {
    }

    public ENTDiagnosisExtra(ENTDiagnosisExtra rhs) {
        this.m_diagnosis = rhs.m_diagnosis;
        this.m_name = rhs.m_name;
        this.m_id = rhs.m_id;
        this.m_value = rhs.m_value;
    }
}
