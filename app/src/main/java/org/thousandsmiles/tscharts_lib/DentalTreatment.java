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

public class DentalTreatment implements Serializable {

    private int m_patient;
    private int m_clinic;
    private int m_id;
    private String m_comment = "";
    private String m_username = "";

    private boolean m_exam = false;
    private String m_examComment = "";

    private boolean m_prophy = false;
    private String m_prophyComment = "";

    private boolean m_srpUR = false;
    private boolean m_srpLR = false;
    private boolean m_srpUL = false;
    private boolean m_srpLL = false;
    private String m_srpComment = "";

    private boolean m_xraysViewed = false;
    private String m_xraysViewedComment = "";

    private boolean m_headNeckOralCancerExam = false;
    private String m_headNeckOralCancerExamComment = "";

    private boolean m_oralHygieneInstruction = false;
    private String m_oralHygieneInstructionComment = "";

    private boolean m_flourideTxVarnish = false;
    private String m_flourideTxVarnishComment = "";

    private boolean m_nutritionalCounseling = false;
    private String m_nutritionalCounselingComment = "";

    private boolean m_orthoEvaluation = false;
    private String m_orthoEvaluationComment = "";
    private boolean m_orthoTx = false;
    private String m_orthoTxComment = "";

    private boolean m_oralSurgeryEvaluation = false;
    private String m_oralSurgeryEvaluationComment = "";
    private boolean m_oralSurgeryTx = false;
    private String m_oralSurgeryTxComment = "";

    private boolean m_localAnestheticBenzocaine = false;
    private boolean m_localAnestheticLidocaine = false;
    private boolean m_localAnestheticSeptocaine = false;
    private boolean m_localAnestheticOther = false;
    private int m_localAnestheticNumberCarps = 0;
    private String m_localAnestheticComment = "";

    public boolean getExam() {
        return m_exam;
    }

    public void setExam(boolean m_exam) {
        this.m_exam = m_exam;
    }

    public String getExamComment() {
        return m_examComment;
    }

    public void setExamComment(String m_examComment) {
        this.m_examComment = m_examComment;
    }

    public boolean getProphy() {
        return m_prophy;
    }

    public void setProphy(boolean m_prophy) {
        this.m_prophy = m_prophy;
    }

    public String getProphyComment() {
        return m_prophyComment;
    }

    public void setProphyComment(String m_prophyComment) {
        this.m_prophyComment = m_prophyComment;
    }

    public boolean getSrpUR() {
        return m_srpUR;
    }

    public void setSrpUR(boolean m_srpUR) {
        this.m_srpUR = m_srpUR;
    }

    public boolean getSrpLR() {
        return m_srpLR;
    }

    public void setSrpLR(boolean m_srpLR) {
        this.m_srpLR = m_srpLR;
    }

    public boolean getSrpUL() {
        return m_srpUL;
    }

    public void setSrpUL(boolean m_srpUL) {
        this.m_srpUL = m_srpUL;
    }

    public boolean getSrpLL() {
        return m_srpLL;
    }

    public void setSrpLL(boolean m_srpLL) {
        this.m_srpLL = m_srpLL;
    }

    public String getSrpComment() {
        return m_srpComment;
    }

    public void setSrpComment(String m_srpComment) {
        this.m_srpComment = m_srpComment;
    }

    public boolean getXraysViewed() {
        return m_xraysViewed;
    }

    public void setXraysViewed(boolean m_xraysViewed) {
        this.m_xraysViewed = m_xraysViewed;
    }

    public String getXraysViewedComment() {
        return m_xraysViewedComment;
    }

    public void setXraysViewedComment(String m_xraysViewedComment) {
        this.m_xraysViewedComment = m_xraysViewedComment;
    }

    public boolean getHeadNeckOralCancerExam() {
        return m_headNeckOralCancerExam;
    }

    public void setHeadNeckOralCancerExam(boolean m_headNeckOralCancerExam) {
        this.m_headNeckOralCancerExam = m_headNeckOralCancerExam;
    }

    public String getHeadNeckOralCancerExamComment() {
        return m_headNeckOralCancerExamComment;
    }

    public void setHeadNeckOralCancerExamComment(String m_headNeckOralCancerExamComment) {
        this.m_headNeckOralCancerExamComment = m_headNeckOralCancerExamComment;
    }

    public boolean getOralHygieneInstruction() {
        return m_oralHygieneInstruction;
    }

    public void setOralHygieneInstruction(boolean m_oralHygieneInstruction) {
        this.m_oralHygieneInstruction = m_oralHygieneInstruction;
    }

    public String getOralHygieneInstructionComment() {
        return m_oralHygieneInstructionComment;
    }

    public void setOralHygieneInstructionComment(String m_oralHygieneInstructionComment) {
        this.m_oralHygieneInstructionComment = m_oralHygieneInstructionComment;
    }

    public boolean getFlourideTxVarnish() {
        return m_flourideTxVarnish;
    }

    public void setFlourideTxVarnish(boolean m_flourideTxVarnish) {
        this.m_flourideTxVarnish = m_flourideTxVarnish;
    }

    public String getFlourideTxVarnishComment() {
        return m_flourideTxVarnishComment;
    }

    public void setFlourideTxVarnishComment(String m_flourideTxVarnishComment) {
        this.m_flourideTxVarnishComment = m_flourideTxVarnishComment;
    }

    public boolean getNutritionalCounseling() {
        return m_nutritionalCounseling;
    }

    public void setNutritionalCounseling(boolean m_nutritionalCounseling) {
        this.m_nutritionalCounseling = m_nutritionalCounseling;
    }

    public String getNutritionalCounselingComment() {
        return m_nutritionalCounselingComment;
    }

    public void setNutritionalCounselingComment(String m_nutritionalCounselingComment) {
        this.m_nutritionalCounselingComment = m_nutritionalCounselingComment;
    }

    public boolean getOrthoEvaluation() {
        return m_orthoEvaluation;
    }

    public void setOrthoEvaluation(boolean m_orthoEvaluation) {
        this.m_orthoEvaluation = m_orthoEvaluation;
    }

    public String getOrthoEvaluationComment() {
        return m_orthoEvaluationComment;
    }

    public void setOrthoEvaluationComment(String m_orthoEvaluationComment) {
        this.m_orthoEvaluationComment = m_orthoEvaluationComment;
    }

    public boolean getOrthoTx() {
        return m_orthoTx;
    }

    public void setOrthoTx(boolean m_orthoTx) {
        this.m_orthoTx = m_orthoTx;
    }

    public String getOrthoTxComment() {
        return m_orthoTxComment;
    }

    public void setOrthoTxComment(String m_orthoTxComment) {
        this.m_orthoTxComment = m_orthoTxComment;
    }

    public boolean getOralSurgeryEvaluation() {
        return m_oralSurgeryEvaluation;
    }

    public void setOralSurgeryEvaluation(boolean m_oralSurgeryEvaluation) {
        this.m_oralSurgeryEvaluation = m_oralSurgeryEvaluation;
    }

    public String getOralSurgeryEvaluationComment() {
        return m_oralSurgeryEvaluationComment;
    }

    public void setOralSurgeryEvaluationComment(String m_oralSurgeryEvaluationComment) {
        this.m_oralSurgeryEvaluationComment = m_oralSurgeryEvaluationComment;
    }

    public boolean getOralSurgeryTx() {
        return m_oralSurgeryTx;
    }

    public void setOralSurgeryTx(boolean m_oralSurgeryTx) {
        this.m_oralSurgeryTx = m_oralSurgeryTx;
    }

    public String getOralSurgeryTxComment() {
        return m_oralSurgeryTxComment;
    }

    public void setOralSurgeryTxComment(String m_oralSurgeryTxComment) {
        this.m_oralSurgeryTxComment = m_oralSurgeryTxComment;
    }

    public boolean getLocalAnestheticBenzocaine() {
        return m_localAnestheticBenzocaine;
    }

    public void setLocalAnestheticBenzocaine(boolean val) {
        this.m_localAnestheticBenzocaine = val;
    }

    public boolean getLocalAnestheticLidocaine() {
        return m_localAnestheticLidocaine;
    }

    public void setLocalAnestheticLidocaine(boolean val) {
        this.m_localAnestheticLidocaine = val;
    }

    public boolean getLocalAnestheticSeptocaine() {
        return m_localAnestheticSeptocaine;
    }

    public void setLocalAnestheticSeptocaine(boolean val) {
        this.m_localAnestheticSeptocaine = val;
    }

    public boolean getLocalAnestheticOther() {
        return m_localAnestheticOther;
    }

    public void setLocalAnestheticOther(boolean val) {
        this.m_localAnestheticOther = val;
    }

    public int getLocalAnestheticNumberCarps() {
        return m_localAnestheticNumberCarps;
    }

    public void setLocalAnestheticNumberCarps(int m_localAnestheticNumberCarps) {
        this.m_localAnestheticNumberCarps = m_localAnestheticNumberCarps;
    }

    public String getLocalAnestheticComment() {
        return m_localAnestheticComment;
    }

    public void setLocalAnestheticComment(String m_localAnestheticComment) {
        this.m_localAnestheticComment = m_localAnestheticComment;
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

            setExam(stringToBool(o.getString("exam")));

            setExamComment(o.getString("examComment"));

            setProphy(stringToBool(o.getString("prophy")));

            setProphyComment(o.getString("prophyComment"));

            setSrpUR(stringToBool(o.getString("srpUR")));

            setSrpLR(stringToBool(o.getString("srpLR")));

            setSrpUL(stringToBool(o.getString("srpUL")));

            setSrpLL(stringToBool(o.getString("srpLL")));

            setSrpComment(o.getString("srpComment"));

            setXraysViewed(stringToBool(o.getString("xraysViewed")));

            setXraysViewedComment(o.getString("xraysViewedComment"));

            setHeadNeckOralCancerExam(stringToBool(o.getString("headNeckOralCancerExam")));

            setHeadNeckOralCancerExamComment(o.getString("headNeckOralCancerExamComment"));

            setOralHygieneInstruction(stringToBool(o.getString("oralHygieneInstruction")));

            setOralHygieneInstructionComment(o.getString("oralHygieneInstructionComment"));

            setFlourideTxVarnish(stringToBool(o.getString("flourideTxVarnish")));

            setFlourideTxVarnishComment(o.getString("flourideTxVarnishComment"));

            setNutritionalCounseling(stringToBool(o.getString("nutritionalCounseling")));

            setNutritionalCounselingComment(o.getString("nutritionalCounselingComment"));

            setOrthoEvaluation(stringToBool(o.getString("orthoEvaluation")));

            setOrthoEvaluationComment(o.getString("orthoEvaluationComment"));

            setOrthoTx(stringToBool(o.getString("orthoTx")));

            setOrthoTxComment(o.getString("orthoTxComment"));

            setOralSurgeryEvaluation(stringToBool(o.getString("oralSurgeryEvaluation")));

            setOralSurgeryEvaluationComment(o.getString("oralSurgeryEvaluationComment"));

            setOralSurgeryTx(stringToBool(o.getString("oralSurgeryTx")));

            setOralSurgeryTxComment(o.getString("oralSurgeryTxComment"));

            setLocalAnestheticBenzocaine(stringToBool(o.getString("localAnestheticBenzocaine")));
            setLocalAnestheticLidocaine(stringToBool(o.getString("localAnestheticLidocaine")));
            setLocalAnestheticSeptocaine(stringToBool(o.getString("localAnestheticSeptocaine")));
            setLocalAnestheticOther(stringToBool(o.getString("localAnestheticOther")));

            setLocalAnestheticNumberCarps(o.getInt("LocalAnestheticNumberCarps"));

            setLocalAnestheticComment(o.getString("localAnestheticComment"));
            
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

            data.put("exam", getExam());
            data.put("examComment", getExamComment());

            data.put("prophy", getProphy());
            data.put("prophyComment", getProphyComment());

            data.put("srpUR", getSrpUR());
            data.put("srpLR", getSrpLR());
            data.put("srpUL", getSrpUL());
            data.put("srpLL", getSrpLL());
            data.put("srpComment", getSrpComment());

            data.put("xraysViewed", getXraysViewed());
            data.put("xraysViewedComment", getXraysViewedComment());

            data.put("headNeckOralCancerExam", getHeadNeckOralCancerExam());
            data.put("headNeckOralCancerExamComment", getHeadNeckOralCancerExamComment());

            data.put("oralHygieneInstruction", getOralHygieneInstruction());
            data.put("oralHygieneInstructionComment", getOralHygieneInstructionComment());

            data.put("flourideTxVarnish", getFlourideTxVarnish());
            data.put("flourideTxVarnishComment", getFlourideTxVarnishComment());

            data.put("nutritionalCounseling", getNutritionalCounseling());
            data.put("nutritionalCounselingComment", getNutritionalCounselingComment());

            data.put("orthoEvaluation", getOrthoEvaluation());
            data.put("orthoEvaluationComment", getOrthoEvaluationComment());
            data.put("orthoTx", getOrthoTx());
            data.put("orthoTxComment", getOrthoTxComment());

            data.put("oralSurgeryEvaluation", getOralSurgeryEvaluation());
            data.put("oralSurgeryEvaluationComment", getOralSurgeryEvaluationComment());
            data.put("oralSurgeryTx", getOralSurgeryTx());
            data.put("oralSurgeryTxComment", getOralSurgeryTxComment());

            data.put("localAnestheticBenzocaine", getLocalAnestheticBenzocaine());
            data.put("localAnestheticLidocaine", getLocalAnestheticLidocaine());
            data.put("localAnestheticSeptocaine", getLocalAnestheticSeptocaine());
            data.put("localAnestheticOther", getLocalAnestheticOther());
            data.put("localAnestheticNumberCarps", getLocalAnestheticNumberCarps());
            data.put("localAnestheticComment", getLocalAnestheticComment());
        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
            data = null;
        }
        return data;
    }

    public DentalTreatment() {
    }

    public DentalTreatment(DentalTreatment rhs) {
        m_patient = rhs.m_patient;
        m_clinic = rhs.m_clinic;
        m_id = rhs.m_id;
        m_comment = rhs.m_comment;
        m_username = rhs.m_username;

        m_exam = rhs.m_exam;
        m_examComment = rhs.m_examComment;

        m_prophy = rhs.m_prophy;
        m_prophyComment = rhs.m_prophyComment;

        m_srpUR = rhs.m_srpUR;
        m_srpLR = rhs.m_srpLR;
        m_srpUL = rhs.m_srpUL;
        m_srpLL = rhs.m_srpLL;
        m_srpComment = rhs.m_srpComment;

        m_xraysViewed = rhs.m_xraysViewed;
        m_xraysViewedComment = rhs.m_xraysViewedComment;

        m_headNeckOralCancerExam = rhs.m_headNeckOralCancerExam;
        m_headNeckOralCancerExamComment = rhs.m_headNeckOralCancerExamComment;

        m_oralHygieneInstruction = rhs.m_oralHygieneInstruction;
        m_oralHygieneInstructionComment = rhs.m_oralHygieneInstructionComment;

        m_flourideTxVarnish = rhs.m_flourideTxVarnish;
        m_flourideTxVarnishComment = rhs.m_flourideTxVarnishComment;

        m_nutritionalCounseling = rhs.m_nutritionalCounseling;
        m_nutritionalCounselingComment = rhs.m_nutritionalCounselingComment;

        m_orthoEvaluation = rhs.m_orthoEvaluation;
        m_orthoEvaluationComment = rhs.m_orthoEvaluationComment;
        m_orthoTx = rhs.m_orthoTx;
        m_orthoTxComment = rhs.m_orthoTxComment;

        m_oralSurgeryEvaluation = rhs.m_oralSurgeryEvaluation;
        m_oralSurgeryEvaluationComment = rhs.m_oralSurgeryEvaluationComment;
        m_oralSurgeryTx = rhs.m_oralSurgeryTx;
        m_oralSurgeryTxComment = rhs.m_oralSurgeryTxComment;

        m_localAnestheticBenzocaine = rhs.m_localAnestheticBenzocaine;
        m_localAnestheticLidocaine = rhs.m_localAnestheticLidocaine;
        m_localAnestheticSeptocaine = rhs.m_localAnestheticSeptocaine;
        m_localAnestheticOther = rhs.m_localAnestheticOther;
        m_localAnestheticNumberCarps = rhs.m_localAnestheticNumberCarps;
        m_localAnestheticComment = rhs.m_localAnestheticComment;
    }
}
