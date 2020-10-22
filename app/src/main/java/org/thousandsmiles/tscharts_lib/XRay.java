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
import java.util.ArrayList;

public class XRay implements Serializable {

    public static final String XRAY_TYPE_FULL = "f";
    public static final String XRAY_TYPE_ANTERIORS_BITEWINGS = "a";
    public static final String XRAY_TYPE_PANORAMIC_VIEW = "p";
    public static final String XRAY_TYPE_CEPHALOMETRIC = "c";

    public enum XRayMouthType {
        XRAY_MOUTH_TYPE_NONE,
        XRAY_MOUTH_TYPE_CHILD,
        XRAY_MOUTH_TYPE_ADULT
    }

    private long m_teeth;
    private String m_type;               // represented in this object in DB format, e.g., "fa" vs. "full,anteriors_bitewings"
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

    public String convertCSVToDBXrayType(String type)
    {
        String ret = "";

        if (type.contains("full")) {
            ret += XRAY_TYPE_FULL;
        }
        if (type.contains("anteriors_bitewings")) {
            ret += XRAY_TYPE_ANTERIORS_BITEWINGS;
        }
        if (type.contains("panoramic_view")) {
            ret += XRAY_TYPE_PANORAMIC_VIEW;
        }
        if (type.contains("cephalometric")) {
            ret += XRAY_TYPE_CEPHALOMETRIC;
        }

        return ret;
    }

    public ArrayList<String> getXrayTypeList()
    {
        ArrayList<String> ret = new ArrayList<String>();

        if (m_type.contains(XRAY_TYPE_FULL)) {
            ret.add(XRAY_TYPE_FULL);
        }
        if (m_type.contains(XRAY_TYPE_ANTERIORS_BITEWINGS)) {
            ret.add(XRAY_TYPE_ANTERIORS_BITEWINGS);
        }
        if (m_type.contains(XRAY_TYPE_PANORAMIC_VIEW)) {
            ret.add(XRAY_TYPE_PANORAMIC_VIEW);
        }
        if (m_type.contains(XRAY_TYPE_CEPHALOMETRIC)) {
            ret.add(XRAY_TYPE_CEPHALOMETRIC);
        }

        return ret;
    }

    public String convertFromDBXrayTypeToCSV(String type)
    {
        String ret = "";

        if (type.contains(XRAY_TYPE_FULL)) {
            ret += "full";
            ret += " ";
        }
        if (type.contains(XRAY_TYPE_ANTERIORS_BITEWINGS)) {
            ret += "anteriors_bitewings";
            ret += " ";
        }
        if (type.contains(XRAY_TYPE_PANORAMIC_VIEW)) {
            ret += "panoramic_view";
            ret += " ";
        }
        if (type.contains(XRAY_TYPE_CEPHALOMETRIC)) {
            ret += "cephalometric";
            ret += " ";
        }

        // create a CSV by stripping off the last ' ', and replacing remaining with ","

        ret = ret.trim();
        ret = ret.replace(" ", ",");

        return ret;
    }

    public String getMouthTypeAsString()
    {
        return mouthTypeToString(m_mouthType);
    }

    private String getTypeAsAPIString()
    {
        return convertFromDBXrayTypeToCSV(m_type);
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
            setType(convertCSVToDBXrayType(o.getString("xray_type")));
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
            data.put("xray_type", this.convertFromDBXrayTypeToCSV(getType()));
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

    public String getType() {
        return m_type;
    }

    private boolean validateType(String type) {
        return  type.equals(XRAY_TYPE_FULL) ||
                type.equals(XRAY_TYPE_ANTERIORS_BITEWINGS) || type.equals(XRAY_TYPE_PANORAMIC_VIEW) ||
                type.equals(XRAY_TYPE_CEPHALOMETRIC);
    }

    public void clearType() {
        this.m_type = "";
    }

    // if m_type is "fb" and this function is called with "p", m_type will become "fbp"

    public boolean addType(String type) {
        boolean ret = true;
        if (validateType(type) == false) {
            ret = false;
        } else if (m_type.contains(type) == false) {
            this.m_type += type;
        }
        return ret;
    }

    // if m_type is "fbp" and this function is called with "p", m_type will become "fb"

    public boolean removeType(String type) {
        boolean ret = true;
        if (validateType(type) == false) {
            ret = false;
        } else if (m_type.contains(type) == true) {
            this.m_type = this.m_type.replace(type, "");
        }
        return ret;
    }

    public void setType(String type) {
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
        this.m_type = "";
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
