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
import java.util.HashMap;
import java.util.Map;

public class ENTTreatment implements Serializable {

    public enum ENTTreatmentType {
        ENT_TREATMENT_CLEANED,
        ENT_TREATMENT_AUDIOGRAM,
        ENT_TREATMENT_TYMPANOGRAM,
        ENT_TREATMENT_MASTOID_DEBRIDED,
        ENT_TREATMENT_HEARING_AID_EVAL, 
        ENT_TREATMENT_ANTIBIOTIC_DROPS, 
        ENT_TREATMENT_ANTIBIOTIC_ORALLY,
        ENT_TREATMENT_ANTIBIOTIC_FOR_ACUTE_INFECTION, 
        ENT_TREATMENT_ANTIBIOTIC_FOR_AFTER_WATER_INFECTION_PROTECTION,
        ENT_TREATMENT_BORIC_ACID_TODAY, 
        ENT_TREATMENT_BORIC_ACID_AT_HOME, 
        ENT_TREATMENT_TUBE_REMOVED, 
        ENT_TREATMENT_FOREIGN_BODY_REMOVED, 
        ENT_TREATMENT_REFERRED_PVT_ENSENADA, 
        ENT_TREATMENT_REFERRED_CHILDRENS_HOSP_TIJUANA, 
        ENT_TREATMENT_SURGERY_TUBES, 
        ENT_TREATMENT_SURGERY_TPLASTY, 
        ENT_TREATMENT_SURGERY_EUA, 
        ENT_TREATMENT_SURGERY_FB, 
        ENT_TREATMENT_SURGERY_MIDDLE_EAR_MYRINGOTOMY, 
        ENT_TREATMENT_SURGERY_CERUMEN_REMOVAL, 
        ENT_TREATMENT_SURGERY_GRANULOMA_REMOVAL, 
        ENT_TREATMENT_SURGERY_SEPTORHINOPLASTY, 
        ENT_TREATMENT_SURGERY_SCAR_REVISION_CLEFT_LIP, 
        ENT_TREATMENT_SURGERY_FRENULECTOMY, 
        ENT_TREATMENT_OTHER
    }

    private Map<ENTTreatmentType, String> m_entToString  = new HashMap<ENTTreatmentType, String>() {{
        put(ENTTreatmentType.ENT_TREATMENT_CLEANED, "cleaned");
        put(ENTTreatmentType.ENT_TREATMENT_AUDIOGRAM, "audiogram");
        put(ENTTreatmentType.ENT_TREATMENT_TYMPANOGRAM, "tympanogram");
        put(ENTTreatmentType.ENT_TREATMENT_MASTOID_DEBRIDED, "mastoid debrided");
        put(ENTTreatmentType.ENT_TREATMENT_HEARING_AID_EVAL, "hearing aid eval");
        put(ENTTreatmentType.ENT_TREATMENT_ANTIBIOTIC_DROPS, "antibiotic drops");
        put(ENTTreatmentType.ENT_TREATMENT_ANTIBIOTIC_ORALLY, "antibiotic orally");
        put(ENTTreatmentType.ENT_TREATMENT_ANTIBIOTIC_FOR_ACUTE_INFECTION, "antibiotic acute");
        put(ENTTreatmentType.ENT_TREATMENT_ANTIBIOTIC_FOR_AFTER_WATER_INFECTION_PROTECTION, "antibiotic water");
        put(ENTTreatmentType.ENT_TREATMENT_BORIC_ACID_TODAY, "boric acid today");
        put(ENTTreatmentType.ENT_TREATMENT_BORIC_ACID_AT_HOME, "boric acid home");
        put(ENTTreatmentType.ENT_TREATMENT_TUBE_REMOVED, "tube removed");
        put(ENTTreatmentType.ENT_TREATMENT_FOREIGN_BODY_REMOVED, "foreign body removed");
        put(ENTTreatmentType.ENT_TREATMENT_REFERRED_PVT_ENSENADA, "referred ensenada");
        put(ENTTreatmentType.ENT_TREATMENT_REFERRED_CHILDRENS_HOSP_TIJUANA, "referred childrensi tijuana");
        put(ENTTreatmentType.ENT_TREATMENT_SURGERY_TUBES, "surgery tubs");
        put(ENTTreatmentType.ENT_TREATMENT_SURGERY_TPLASTY, "surgery tplasty");
        put(ENTTreatmentType.ENT_TREATMENT_SURGERY_EUA, "surgery eua");
        put(ENTTreatmentType.ENT_TREATMENT_SURGERY_FB, "surgery fb");
        put(ENTTreatmentType.ENT_TREATMENT_SURGERY_MIDDLE_EAR_MYRINGOTOMY, "surgery myringotomy");
        put(ENTTreatmentType.ENT_TREATMENT_SURGERY_CERUMEN_REMOVAL, "surgery cerumen removal");
        put(ENTTreatmentType.ENT_TREATMENT_SURGERY_GRANULOMA_REMOVAL, "surgery granuloma removal");
        put(ENTTreatmentType.ENT_TREATMENT_SURGERY_SEPTORHINOPLASTY, "surgery septorhinoplasty");
        put(ENTTreatmentType.ENT_TREATMENT_SURGERY_SCAR_REVISION_CLEFT_LIP, "surgery scar revision cleft");
        put(ENTTreatmentType.ENT_TREATMENT_SURGERY_FRENULECTOMY, "surgery frenulectomy");
        put(ENTTreatmentType.ENT_TREATMENT_OTHER, "other");
    }};

    private ENTHistory.EarSide m_side;
    private ENTTreatmentType m_treatment;

    private int m_patient;
    private int m_clinic;
    private int m_id;
    private String m_comment; // general comment, or description of ENT_TREATMENT_OTHER
    private String m_username;
    private boolean m_future;

    public boolean stringToBool(String val)
    {
        boolean ret = false;

        if (val.equals("true")) {
            ret = true;
        }
        return ret;
    }

    public String boolToString(boolean val)
    {
        String ret = "false";

        if (val == true) {
            ret = "true";
        }

        return ret;
    }

    public ENTHistory.EarSide earSideToEnum(String side)
    {
        ENTHistory.EarSide ret = ENTHistory.EarSide.EAR_SIDE_BOTH;

        if (side.equals("left")) {
            ret = ENTHistory.EarSide.EAR_SIDE_LEFT;
        } else if (side.equals("right")) {
            ret = ENTHistory.EarSide.EAR_SIDE_RIGHT;
        }
        return ret;
    }

    public String earSideToString(ENTHistory.EarSide side)
    {
        String ret = "both";

        if (side == ENTHistory.EarSide.EAR_SIDE_LEFT) {
            ret = "left";
        } else if (side == ENTHistory.EarSide.EAR_SIDE_RIGHT) {
            ret = "right";
        }

        return ret;
    }

    public String treatmentToString(ENTTreatmentType treatment)
    {
        String ret;

        ret = m_entToString.get(treatment);
        return ret;
    }

    public ENTTreatmentType treatmentToEnum(String treatment) {
        ENTTreatmentType ret = null;
        for (Map.Entry<ENTTreatmentType, String> entry : m_entToString.entrySet()) {
            if (entry.getValue().equals("treatment")) {
                ret = entry.getKey();
            }
        }
        return ret;
    }

    public int fromJSONObject(JSONObject o)
    {
        int ret = 0;

        try {
            m_side = earSideToEnum(o.getString("side"));
            m_future = stringToBool(o.getString("future"));
            m_treatment = treatmentToEnum(o.getString("treatment"));

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
                data.put("id", this.getId());
            }

            data.put("patient", getPatient());
            data.put("clinic", getClinic());
            data.put("comment", getComment());
            data.put("username", getUsername());

            data.put("side", earSideToString(m_side));
            data.put("treatment", treatmentToString(m_treatment));
            data.put("future", boolToString(m_future));
        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
            data = null;
        }
        return data;
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

    public String getComment() {
        return m_comment;
    }

    public void setComment(String m_comment) {
        this.m_comment = m_comment;
    }

    public String getUsername() {
        return m_username;
    }

    public void setUsername(String m_username) {
        this.m_username = m_username;
    }


    public ENTTreatment() {
    }

    public ENTTreatment(ENTTreatment rhs) {
        this.m_patient = rhs.m_patient;
        this.m_clinic = rhs.m_clinic;
        this.m_id = rhs.m_id;
        this.m_username = rhs.m_username;
        this.m_comment = rhs.m_comment;
        this.m_future = rhs.m_future;

        this.m_side = rhs.m_side;
        this.m_treatment = rhs.m_treatment;
    }
}
