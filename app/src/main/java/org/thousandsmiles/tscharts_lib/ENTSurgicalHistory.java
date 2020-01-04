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

public class ENTSurgicalHistory implements Serializable {

    private ENTHistory.EarSide m_tubes;
    private String m_tubescomment;
    private ENTHistory.EarSide m_tplasty;
    private String m_tplastycomment;
    private ENTHistory.EarSide m_eua;
    private String m_euacomment;
    private ENTHistory.EarSide m_fb;
    private String m_fbcomment;
    private ENTHistory.EarSide m_myringotomy;
    private String m_myringotomycomment;
    private ENTHistory.EarSide m_cerumen;
    private String m_cerumencomment;
    private ENTHistory.EarSide m_granuloma;
    private String m_granulomacomment;
    private boolean m_septorhinoplasty;
    private String m_septorhinoplastycomment;
    private boolean m_scarrevision;
    private String m_scarrevisioncomment;
    private boolean m_frenulectomy;
    private String m_frenulectomycomment;

    private int m_patient;
    private int m_clinic;
    private int m_id;
    private String m_username;

    public ENTHistory.EarSide earSideToEnum(String side)
    {
        ENTHistory.EarSide ret = ENTHistory.EarSide.EAR_SIDE_BOTH;

        if (side.equals("left")) {
            ret = ENTHistory.EarSide.EAR_SIDE_LEFT;
        } else if (side.equals("right")) {
            ret = ENTHistory.EarSide.EAR_SIDE_RIGHT;
        } else if (side.equals("none")) {
            ret = ENTHistory.EarSide.EAR_SIDE_NONE;
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
        } else if (side == ENTHistory.EarSide.EAR_SIDE_NONE) {
            ret = "none";
        }

        return ret;
    }


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

    public int fromJSONObject(JSONObject o)
    {
        int ret = 0;

        try {

            m_tubes = earSideToEnum(o.getString("tubes"));
            m_tubescomment = o.getString("tubescomment");
            m_tplasty = earSideToEnum(o.getString("tplasty"));
            m_tplastycomment = o.getString("tplastycomment");
            m_eua = earSideToEnum(o.getString("eua"));
            m_euacomment = o.getString("euacomment");
            m_fb = earSideToEnum(o.getString("fb"));
            m_fbcomment = o.getString("fbcomment");
            m_myringotomy = earSideToEnum(o.getString("myringotomy"));
            m_myringotomycomment = o.getString("myringotomycomment");
            m_cerumen = earSideToEnum(o.getString("cerumen"));
            m_cerumencomment = o.getString("cerumencomment");
            m_granuloma = earSideToEnum(o.getString("granuloma"));
            m_granulomacomment = o.getString("granulomacomment");
            m_septorhinoplasty = stringToBool(o.getString("septorhinoplasty"));
            m_septorhinoplastycomment = o.getString("septorhinoplastycomment");
            m_scarrevision = stringToBool(o.getString("scarrevision"));
            m_scarrevisioncomment = o.getString("scarrevisioncomment");
            m_frenulectomy = stringToBool(o.getString("frenulectomy"));
            m_frenulectomycomment = o.getString("frenulectomycomment");
 
            setId(o.getInt("id"));
            setPatient(o.getInt("patient"));
            setClinic(o.getInt("clinic"));
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
            data.put("username", getUsername());

            data.put("tubes", earSideToString(m_tubes));
            data.put("tubescomment", m_tubescomment);
            data.put("tplasty", earSideToString(m_tplasty));
            data.put("tplastycomment", m_tplastycomment);
            data.put("eua", earSideToString(m_eua));
            data.put("euacomment", m_euacomment);
            data.put("fb", earSideToString(m_fb));
            data.put("fbcomment", m_fbcomment);
            data.put("myringotomy", earSideToString(m_myringotomy));
            data.put("myringotomycomment", m_myringotomycomment);
            data.put("cerumen", earSideToString(m_cerumen));
            data.put("cerumencomment", m_cerumencomment);
            data.put("granuloma", earSideToString(m_granuloma));
            data.put("granulomacomment", m_granulomacomment);
            data.put("septorhinoplasty", boolToString(m_septorhinoplasty));
            data.put("septorhinoplastycomment", m_septorhinoplastycomment);
            data.put("scarrevision", boolToString(m_scarrevision));
            data.put("scarrevisioncomment", m_scarrevisioncomment);
            data.put("frenulectomy", boolToString(m_frenulectomy));
            data.put("frenulectomycomment", m_frenulectomycomment);
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

    public String getUsername() {
        return m_username;
    }

    public void setUsername(String m_username) {
        this.m_username = m_username;
    }

    public ENTSurgicalHistory() {
    }

    public ENTSurgicalHistory(ENTSurgicalHistory rhs) {

        this.m_tubes = rhs.m_tubes;;
        this.m_tubescomment = rhs.m_tubescomment;
        this.m_tplasty = rhs.m_tplasty;
        this.m_tplastycomment = rhs.m_tplastycomment;
        this.m_eua = rhs.m_eua;
        this.m_euacomment = rhs.m_euacomment;
        this.m_fb = rhs.m_fb;
        this.m_fbcomment = rhs.m_fbcomment;
        this.m_myringotomy = rhs.m_myringotomy;
        this.m_myringotomycomment = rhs.m_myringotomycomment;
        this.m_cerumen = rhs.m_cerumen;
        this.m_cerumencomment = rhs.m_cerumencomment;
        this.m_granuloma = rhs.m_granuloma;
        this.m_granulomacomment = rhs.m_granulomacomment;
        this.m_septorhinoplasty = rhs.m_septorhinoplasty;
        this.m_septorhinoplastycomment = rhs.m_septorhinoplastycomment;
        this.m_scarrevision = rhs.m_scarrevision;
        this.m_scarrevisioncomment = rhs.m_scarrevisioncomment;
        this.m_frenulectomy = rhs.m_frenulectomy;
        this.m_frenulectomycomment = rhs.m_frenulectomycomment;

        this.m_patient = rhs.m_patient;
        this.m_clinic = rhs.m_clinic;
        this.m_id = rhs.m_id;
        this.m_username = rhs.m_username;
    }
}
