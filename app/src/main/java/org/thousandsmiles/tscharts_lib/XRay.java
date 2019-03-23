/*
 * (C) Copyright Syd Logan 2019
 * (C) Copyright Thousand Smiles Foundation 2019
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

public class XRay {

    public enum XRayType {
        XRAY_TYPE_NONE,
        XRAY_TYPE_FULL,
        XRAY_TYPE_ANTERIORS_BITEWINGS
    }

    public enum XRayMouthType {
        XRAY_MOUTH_TYPE_NONE,
        XRAY_MOUTH_TYPE_CHILD,
        XRAY_MOUTH_TYPE_ADULT
    }

    private long m_teeth;
    private XRayType m_type;
    private XRayMouthType m_mouthType;
    private int m_patient;
    private int m_clinic;
    private int m_id;


    public XRayMouthType mouthTypeToEnum(String type)
    {
        XRayMouthType ret = XRayMouthType.XRAY_MOUTH_TYPE_NONE;

        if (type.equals("adult")) {
            ret = XRayMouthType.XRAY_MOUTH_TYPE_ADULT;
        } else if (type.equals("child")) {
            ret = XRayMouthType.XRAY_MOUTH_TYPE_CHILD;
        }
        return ret;
    }

    public XRayType xRayTypeToEnum(String type)
    {
        XRayType ret = XRayType.XRAY_TYPE_NONE;

        if (type.equals("full")) {
            ret = XRayType.XRAY_TYPE_FULL;
        } else if (type.equals("anteriors_bitewings")) {
            ret = XRayType.XRAY_TYPE_ANTERIORS_BITEWINGS;
        }
        return ret;
    }

    public String mouthTypeToString(XRayMouthType type)
    {
        String ret = "";

        if (type == XRayMouthType.XRAY_MOUTH_TYPE_ADULT) {
            ret = "adult";
        } else if (type == XRayMouthType.XRAY_MOUTH_TYPE_CHILD) {
            ret = "child";
        }
        return ret;
    }

    public String xRayTypeToString(XRayType type)
    {
        String ret = "";

        if (type == XRayType.XRAY_TYPE_FULL) {
            ret = "full";
        } else if (type == XRayType.XRAY_TYPE_ANTERIORS_BITEWINGS) {
            ret = "anteriors_bitewings";
        }
        return ret;
    }

    public String getMouthTypeAsString()
    {
        return mouthTypeToString(m_mouthType);
    }

    public String getTypeAsString()
    {
        return xRayTypeToString(m_type);
    }

    public int fromJSONObject(JSONObject o)
    {
        int ret = 0;

        try {
            setId(o.getInt("id"));
            setTeeth(o.getLong("teeth"));
            setPatient(o.getInt("patient"));
            setClinic(o.getInt("clinic"));
            setMouthType(mouthTypeToEnum(o.getString("mouth_type")));
            setType(xRayTypeToEnum(o.getString("xray_type")));
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
            data.put("teeth", this.getTeeth());
            data.put("patient", this.getPatient());
            data.put("clinic", this.getClinic());
            data.put("type", this.getTypeAsString());
            data.put("mouth_type", this.getMouthTypeAsString());
        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
            data = null;
        }
        return data;
    }

    public long getTeeth() {
        return m_teeth;
    }

    public void setTeeth(long m_teeth) {
        this.m_teeth = m_teeth;
    }

    public XRayType getType() {
        return m_type;
    }

    public void setType(XRayType type) {
        this.m_type = type;
    }

    public XRayMouthType getMouthType() {
        return m_mouthType;
    }

    public void setMouthType(XRayMouthType mouthType) {
        this.m_mouthType = mouthType;
    }

    public int getPatient() {
        return m_patient;
    }

    public void setPatient(int m_patient) {
        this.m_patient = m_patient;
    }

    public int getClinic() {
        return m_clinic;
    }

    public void setClinic(int m_clinic) {
        this.m_clinic = m_clinic;
    }

    public int getId() {
        return m_id;
    }

    public void setId(int m_id) {
        this.m_id = m_id;
    }

    public XRay() {
        this.m_teeth = 0;
        this.m_type = XRayType.XRAY_TYPE_FULL;
        this.m_mouthType = XRayMouthType.XRAY_MOUTH_TYPE_CHILD;
    }

    public XRay(XRay rhs) {
        this.m_teeth = rhs.m_teeth;
        this.m_type = rhs.m_type;
        this.m_mouthType = rhs.m_mouthType;
        this.m_patient = rhs.m_patient;
        this.m_clinic = rhs.m_clinic;
        this.m_id = rhs.m_id;
    }
}
