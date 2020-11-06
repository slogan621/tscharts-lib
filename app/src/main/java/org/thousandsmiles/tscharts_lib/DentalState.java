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

public class DentalState implements Serializable {

    private int m_patient;
    private int m_clinic;
    private int m_id;
    private String m_comment;
    private String m_username;
    private int m_tooth;
    private int m_code;

    public enum State {
        DENTAL_STATE_NONE,
        DENTAL_STATE_UNTREATED,
        DENTAL_STATE_TREATED,
        DENTAL_STATE_OTHER
    }

    private State m_state = State.DENTAL_STATE_NONE;

    public int getTooth() {
        return m_tooth;
    }

    public void setTooth(int m_tooth) {
        this.m_tooth = m_tooth;
    }

    public int getCode() {
        return m_code;
    }

    public void setCode(int m_code) {
        this.m_code = m_code;
    }

    public State getState() {
        return m_state;
    }

    public void setState(State m_state) {
        this.m_state = m_state;
    }

    public int getPatient() {
        return m_patient;
    }

    public void setPatient(int patient) {
        m_patient = patient;
    }

    public int getClinic() {
        return m_clinic;
    }

    public void setClinic(int clinic) {
        m_clinic = clinic;
    }

    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        m_id = id;
    }

    public String getComment() {
        return m_comment;
    }

    public void setComment(String comment) {
        m_comment = comment;
    }

    public String getUsername() {
        return m_username;
    }

    public void setUsername(String username) {
        m_username = username;
    }

    public State stateToEnum(String val)
    {
        State ret = State.DENTAL_STATE_NONE;

        if (val.equals("treated")) {
            ret = State.DENTAL_STATE_TREATED;
        } else if (val.equals("untreated")) {
            ret = State.DENTAL_STATE_UNTREATED;
        } else if (val.equals("other")) {
            ret = State.DENTAL_STATE_OTHER;
        }
        return ret;
    }

    public String stateToString(State val)
    {
        String ret = "none";

        if (val == State.DENTAL_STATE_TREATED) {
            ret = "treated";
        } else if (val == State.DENTAL_STATE_UNTREATED) {
            ret = "untreated";
        } else if (val == State.DENTAL_STATE_OTHER) {
            ret = "other";
        }

        return ret;
    }

    public int fromJSONObject(JSONObject o)
    {
        int ret = 0;

        try {

            setTooth(o.getInt("tooth"));
            setCode(o.getInt("code"));
            setState(stateToEnum(o.getString("state")));

            setId(o.getInt("id"));
            setPatient(o.getInt("patient"));
            setClinic(o.getInt("clinic"));
            setComment(o.getString("comment"));
            setUsername(o.getString("username"));
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
                data.put("id", getId());
            }

            data.put("patient", getPatient());
            data.put("clinic", getClinic());
            data.put("comment", getComment());
            data.put("username", getUsername());

            data.put("tooth", getTooth());
            data.put("code", getCode());
            data.put("state", stateToString(getState()));

        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
            data = null;
        }
        return data;
    }

    public DentalState() {
    }

    public DentalState(DentalState rhs) {
        m_patient = rhs.m_patient;
        m_clinic = rhs.m_clinic;
        m_id = rhs.m_id;
        m_comment = rhs.m_comment;
        m_username = rhs.m_username;

        m_state = rhs.m_state;
        m_code = rhs.m_code;
        m_tooth = rhs.m_tooth;
    }
}
