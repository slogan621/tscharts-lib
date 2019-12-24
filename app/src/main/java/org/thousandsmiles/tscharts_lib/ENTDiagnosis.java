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

public class ENTDiagnosis implements Serializable {

    private ENTHistory.EarSide m_hlConductive;
    private ENTHistory.EarSide m_hl;
    private ENTHistory.EarSide m_hlMixed;
    private ENTHistory.EarSide m_hlSensory;
    private ENTHistory.EarSide m_externalCerumenImpaction;
    private ENTHistory.EarSide m_externalEarCanalFB;
    private ENTHistory.EarSide m_externalMicrotia;

    private ENTHistory.EarSide m_tympanicAtelectasis;
    private ENTHistory.EarSide m_tympanicGranuloma;
    private ENTHistory.EarSide m_tympanicMonomer;
    private ENTHistory.EarSide m_tympanicTube;
    private ENTHistory.EarSide m_tympanicPerf;

    private ENTHistory.EarSide m_middleEarCholesteatoma;
    private ENTHistory.EarSide m_middleEarEustTubeDysTMRetraction;
    private ENTHistory.EarSide m_middleEarOtitisMedia;
    private ENTHistory.EarSide m_middleEarSerousOtitisMedia;

    private boolean m_oralAnkyloglossia;
    private boolean m_oralTonsilEnlarge;
    private boolean m_oralCleftLipRepairDeformity;
    private boolean m_oralCleftLipUnilateral;
    private boolean m_oralCleftLipBilateral;
    private boolean m_oralCleftLipUnrepaired;
    private boolean m_oralCleftLipRepaired;
    private boolean m_oralCleftPalateUnilateral;
    private boolean m_oralCleftPalateBilateral;
    private boolean m_oralCleftPalateUnrepaired;
    private boolean m_oralCleftPalateRepaired;
    private boolean m_oralSpeechProblem;


    private boolean m_noseDeviatedSeptum;
    private boolean m_noseTurbinateHypertrophy;
    private boolean m_noseDeformitySecondaryToCleftPalate;

    private ENTHistory.EarSide m_syndromeHemifacialMicrosomia;
    private ENTHistory.EarSide m_syndromePierreRobin;

    private int m_patient;
    private int m_clinic;
    private int m_id;
    private String m_comment;
    private String m_username;

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

    public int fromJSONObject(JSONObject o)
    {
        int ret = 0;

        try {
            m_hlConductive = earSideToEnum(o.getString("hlConductive"));
            m_hl = earSideToEnum(o.getString("hl"));
            m_hlMixed = earSideToEnum(o.getString("hlMixed"));
            m_hlSensory = earSideToEnum(o.getString("hlSensory"));
            m_externalCerumenImpaction = earSideToEnum(o.getString("externalCerumenImpaction"));
            m_externalEarCanalFB = earSideToEnum(o.getString("externalEarCanalFB"));
            m_externalMicrotia = earSideToEnum(o.getString("externalMicrotia"));

            m_tympanicAtelectasis = earSideToEnum(o.getString("tympanicAtelectasis"));
            m_tympanicGranuloma = earSideToEnum(o.getString("tympanicGranuloma"));
            m_tympanicMonomer = earSideToEnum(o.getString("tympanicMonomer"));
            m_tympanicTube = earSideToEnum(o.getString("tympanicTube"));
            m_tympanicPerf = earSideToEnum(o.getString("tympanicPerf"));

            m_middleEarCholesteatoma = earSideToEnum(o.getString("middleEarCholesteatoma"));
            m_middleEarEustTubeDysTMRetraction = earSideToEnum(o.getString("middleEarEustTubeDysTMRetraction"));
            m_middleEarOtitisMedia = earSideToEnum(o.getString("middleEarOtitisMedia"));
            m_middleEarSerousOtitisMedia = earSideToEnum(o.getString("middleEarSerousOtitisMedia"));

            m_oralAnkyloglossia = stringToBool(o.getString("oralAnkyloglossia"));
            m_oralTonsilEnlarge = stringToBool(o.getString("oralTonsilEnlarge"));
            m_oralCleftLipRepairDeformity = stringToBool(o.getString("oralCleftLipRepairDeformity"));
            m_oralCleftLipUnilateral = stringToBool(o.getString("oralCleftLipUnilateral"));
            m_oralCleftLipBilateral = stringToBool(o.getString("oralCleftLipBilateral"));
            m_oralCleftLipUnrepaired = stringToBool(o.getString("oralCleftLipUnrepaired"));
            m_oralCleftLipRepaired = stringToBool(o.getString("oralCleftLipRepaired"));
            m_oralCleftPalateUnilateral = stringToBool(o.getString("oralCleftPalateUnilateral"));
            m_oralCleftPalateBilateral = stringToBool(o.getString("oralCleftPalateBilateral"));
            m_oralCleftPalateUnrepaired = stringToBool(o.getString("oralCleftPalateUnrepaired"));
            m_oralCleftPalateRepaired = stringToBool(o.getString("oralCleftPalateRepaired"));
            m_oralSpeechProblem = stringToBool(o.getString("oralSpeechProblem"));

            m_noseDeviatedSeptum = stringToBool(o.getString("noseDeviatedSeptum"));
            m_noseTurbinateHypertrophy = stringToBool(o.getString("noseTurbinateHypertrophy"));
            m_noseDeformitySecondaryToCleftPalate = stringToBool(o.getString("noseDeformitySecondaryToCleftPalate"));

            m_syndromeHemifacialMicrosomia = earSideToEnum(o.getString("syndromeHemifacialMicrosomia"));
            m_syndromePierreRobin = earSideToEnum(o.getString("syndromePierreRobin"));

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

            data.put("hlConductive", earSideToString(m_hlConductive));
            data.put("hl", earSideToString(m_hl));
            data.put("hlMixed", earSideToString(m_hlMixed));
            data.put("hlSensory", earSideToString(m_hlSensory));
            data.put("externalCerumenImpaction", earSideToString(m_externalCerumenImpaction));
            data.put("externalEarCanalFB", earSideToString(m_externalEarCanalFB));
            data.put("externalMicrotia", earSideToString(m_externalMicrotia));

            data.put("tympanicAtelectasis", earSideToString(m_tympanicAtelectasis));
            data.put("tympanicGranuloma", earSideToString(m_tympanicGranuloma));
            data.put("tympanicMonomer", earSideToString(m_tympanicMonomer));
            data.put("tympanicTube", earSideToString(m_tympanicTube));
            data.put("tympanicPerf", earSideToString(m_tympanicPerf));

            data.put("middleEarCholesteatoma", earSideToString(m_middleEarCholesteatoma));
            data.put("middleEarEustTubeDysTMRetraction", earSideToString(m_middleEarEustTubeDysTMRetraction));
            data.put("middleEarOtitisMedia", earSideToString(m_middleEarOtitisMedia));
            data.put("middleEarSerousOtitisMedia", earSideToString(m_middleEarSerousOtitisMedia));

            data.put("oralAnkyloglossia", boolToString(m_oralAnkyloglossia));
            data.put("oralTonsilEnlarge", boolToString(m_oralTonsilEnlarge));
            data.put("oralCleftLipRepairDeformity", boolToString(m_oralCleftLipRepairDeformity));
            data.put("oralCleftLipUnilateral", boolToString(m_oralCleftLipUnilateral));
            data.put("oralCleftLipBilateral", boolToString(m_oralCleftLipBilateral));
            data.put("oralCleftLipUnrepaired", boolToString(m_oralCleftLipUnrepaired));
            data.put("oralCleftLipRepaired", boolToString(m_oralCleftLipRepaired));
            data.put("oralCleftPalateUnilateral", boolToString(m_oralCleftPalateUnilateral));
            data.put("oralCleftPalateBilateral", boolToString(m_oralCleftPalateBilateral));
            data.put("oralCleftPalateUnrepaired", boolToString(m_oralCleftPalateUnrepaired));
            data.put("oralCleftPalateRepaired", boolToString(m_oralCleftPalateRepaired));
            data.put("oralSpeechProblem", boolToString(m_oralSpeechProblem));

            data.put("noseDeviatedSeptum", boolToString(m_noseDeviatedSeptum));
            data.put("noseTurbinateHypertrophy", boolToString(m_noseTurbinateHypertrophy));
            data.put("noseDeformitySecondaryToCleftPalate", boolToString(m_noseDeformitySecondaryToCleftPalate));

            data.put("syndromeHemifacialMicrosomia", earSideToString(m_syndromeHemifacialMicrosomia));
            data.put("syndromePierreRobin", earSideToString(m_syndromePierreRobin));

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


    public ENTDiagnosis() {
    }

    public ENTDiagnosis(ENTDiagnosis rhs) {
        this.m_patient = rhs.m_patient;
        this.m_clinic = rhs.m_clinic;
        this.m_id = rhs.m_id;
        this.m_username = rhs.m_username;
        this.m_comment = rhs.m_comment;

        this.m_hlConductive = rhs.m_hlConductive;
        this.m_hl = rhs.m_hl;
        this.m_hlMixed = rhs.m_hlMixed;
        this.m_hlSensory = rhs.m_hlSensory;
        this.m_externalCerumenImpaction = rhs.m_externalCerumenImpaction;
        this.m_externalEarCanalFB = rhs.m_externalEarCanalFB;
        this.m_externalMicrotia = rhs.m_externalMicrotia;

        this.m_tympanicAtelectasis = rhs.m_tympanicAtelectasis;
        this.m_tympanicGranuloma = rhs.m_tympanicGranuloma;
        this.m_tympanicMonomer = rhs.m_tympanicMonomer;
        this.m_tympanicTube = rhs.m_tympanicTube;
        this.m_tympanicPerf = rhs.m_tympanicPerf;

        this.m_middleEarCholesteatoma = rhs.m_middleEarCholesteatoma;
        this.m_middleEarEustTubeDysTMRetraction = rhs.m_middleEarEustTubeDysTMRetraction;
        this.m_middleEarOtitisMedia = rhs.m_middleEarOtitisMedia;
        this.m_middleEarSerousOtitisMedia = rhs.m_middleEarSerousOtitisMedia;

        this.m_oralAnkyloglossia = rhs.m_oralAnkyloglossia;
        this.m_oralTonsilEnlarge = rhs.m_oralTonsilEnlarge;
        this.m_oralCleftLipRepairDeformity = rhs.m_oralCleftLipRepairDeformity;
        this.m_oralCleftLipUnilateral = rhs.m_oralCleftLipUnilateral;
        this.m_oralCleftLipBilateral = rhs.m_oralCleftLipBilateral;
        this.m_oralCleftLipUnrepaired = rhs.m_oralCleftLipUnrepaired;
        this.m_oralCleftLipRepaired = rhs.m_oralCleftLipRepaired;
        this.m_oralCleftPalateUnilateral = rhs.m_oralCleftPalateUnilateral;
        this.m_oralCleftPalateBilateral = rhs.m_oralCleftPalateBilateral;
        this.m_oralCleftPalateUnrepaired = rhs.m_oralCleftPalateUnrepaired;
        this.m_oralCleftPalateRepaired = rhs.m_oralCleftPalateRepaired;
        this.m_oralSpeechProblem = rhs.m_oralSpeechProblem;

        this.m_noseDeviatedSeptum = rhs.m_noseDeviatedSeptum;
        this.m_noseTurbinateHypertrophy = rhs.m_noseTurbinateHypertrophy;
        this.m_noseDeformitySecondaryToCleftPalate = rhs.m_noseDeformitySecondaryToCleftPalate;

        this.m_syndromeHemifacialMicrosomia = rhs.m_syndromeHemifacialMicrosomia;
        this.m_syndromePierreRobin = rhs.m_syndromePierreRobin;
    }
}
