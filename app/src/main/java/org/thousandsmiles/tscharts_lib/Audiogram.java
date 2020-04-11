/*
 * (C) Copyright Syd Logan 2020
 * (C) Copyright Thousand Smiles Foundation 2020
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

public class Audiogram implements Serializable {

    private int m_patient;
    private int m_clinic;
    private int m_image;
    private String m_comment;
    private int m_id;

    public int fromJSONObject(JSONObject o)
    {
        int ret = 0;

        try {
            setId(o.getInt("id"));
            setImage(o.getInt("image"));
            setPatient(o.getInt("patient"));
            setClinic(o.getInt("clinic"));
            setComment(o.getString("comment"));
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
            data.put("image", this.getImage());
            data.put("patient", this.getPatient());
            data.put("clinic", this.getClinic());
            data.put("comment", this.getComment());
        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
            data = null;
        }
        return data;
    }

    public String getComment() {
        return m_comment;
    }

    public void setComment(String comment) {
        this.m_comment = comment;
    }

    public int getPatient() {
        return m_patient;
    }

    public void setPatient(int patient) {
        this.m_patient = patient;
    }

    public int getClinic() {
        return m_clinic;
    }

    public void setClinic(int clinic) {
        this.m_clinic = clinic;
    }

    public int getImage() {
        return m_image;
    }

    public void setImage(int image) {
        this.m_image = image;
    }

    public int getId() {
        return m_id;
    }

    public void setId(int m_id) {
        this.m_id = m_id;
    }

    public Audiogram() {
    }

    public Audiogram(Audiogram rhs) {
        this.m_comment = rhs.m_comment;
        this.m_image = rhs.m_image;
        this.m_patient = rhs.m_patient;
        this.m_clinic = rhs.m_clinic;
        this.m_id = rhs.m_id;
    }
}
