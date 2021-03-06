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

public class ENTTreatment implements Serializable {

    private int m_patient;
    private int m_clinic;
    private int m_id;
    private String m_comment;
    private String m_username;

    private ENTHistory.EarSide m_earCleanedSide = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_earCleanedComment;
    private ENTHistory.EarSide m_audiogramSide = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_audiogramComment;
    private boolean m_audiogramRightAway = false;
    private String m_audiogramRightAwayComment;
    private ENTHistory.EarSide m_tympanogramSide = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_tympanogramComment;
    private boolean m_tympanogramRightAway = false;
    private String m_tympanogramRightAwayComment;
    private ENTHistory.EarSide m_mastoidDebridedSide = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_mastoidDebridedComment;
    private boolean m_mastoidDebridedHearingAidEval = false;
    private String m_mastoidDebridedHearingAidEvalComment;
    private boolean m_antibioticDrops = false;
    private String m_antibioticDropsComment;
    private boolean m_antibioticOrally = false;
    private String m_antibioticOrallyComment;
    private boolean m_antibioticAcuteInfection = false;
    private String m_antibioticAcuteInfectionComment;
    private boolean m_antibioticAfterWaterExposureInfectionPrevention = false;
    private String m_antibioticAfterWaterExposureInfectionPreventionComment;
    private boolean m_boricAcidToday = false;
    private String m_boricAcidTodayComment;
    private boolean m_boricAcidForHomeUse = false;
    private String m_boricAcidForHomeUseComment;
    private ENTHistory.EarSide m_boricAcidSide = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_boricAcidSideComment;
    private ENTHistory.EarSide m_foreignBodyRemoved = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_foreignBodyRemovedComment;
    private boolean m_return3Months = false;
    private boolean m_return6Months = false;
    private boolean m_returnPrn = false;
    private String m_returnComment;
    private boolean m_referredPvtENTEnsenada = false;
    private String m_referredPvtENTEnsenadaComment;
    private boolean m_referredChildrensHospitalTJ = false;
    private String m_referredChildrensHospitalTJComment;

    private ENTHistory.EarSide m_tubesTomorrow = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_tubesTomorrowComment;
    private ENTHistory.EarSide m_tPlastyTomorrow = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_tPlastyTomorrowComment;
    private ENTHistory.EarSide m_euaTomorrow = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_euaTomorrowComment;
    private ENTHistory.EarSide m_fbRemovalTomorrow = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_fbRemovalTomorrowComment;
    private ENTHistory.EarSide m_middleEarExploreMyringotomyTomorrow = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_middleEarExploreMyringotomyTomorrowComment;
    private ENTHistory.EarSide m_cerumenTomorrow = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_cerumenTomorrowComment;
    private ENTHistory.EarSide m_granulomaTomorrow = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_granulomaTomorrowComment;
    private boolean m_septorhinoplastyTomorrow = false;
    private String m_septorhinoplastyTomorrowComment;
    private boolean m_scarRevisionCleftLipTomorrow = false;
    private String m_scarRevisionCleftLipTomorrowComment;
    private boolean m_frenulectomyTomorrow = false;
    private String m_frenulectomyTomorrowComment;

    private ENTHistory.EarSide m_tubesFuture = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_tubesFutureComment;
    private ENTHistory.EarSide m_tPlastyFuture = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_tPlastyFutureComment;
    private ENTHistory.EarSide m_euaFuture = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_euaFutureComment;
    private ENTHistory.EarSide m_fbRemovalFuture = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_fbRemovalFutureComment;
    private ENTHistory.EarSide m_middleEarExploreMyringotomyFuture = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_middleEarExploreMyringotomyFutureComment;
    private ENTHistory.EarSide m_cerumenFuture = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_cerumenFutureComment;
    private ENTHistory.EarSide m_granulomaFuture = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_granulomaFutureComment;
    private boolean m_septorhinoplastyFuture = false;
    private String m_septorhinoplastyFutureComment;
    private boolean m_scarRevisionCleftLipFuture = false;
    private String m_scarRevisionCleftLipFutureComment;
    private boolean m_frenulectomyFuture = false;
    private String m_frenulectomyFutureComment;

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

    public ENTHistory.EarSide getEarCleanedSide() {
        return m_earCleanedSide;
    }

    public void setEarCleanedSide(ENTHistory.EarSide earCleanedSide) {
        m_earCleanedSide = earCleanedSide;
    }

    public String getEarCleanedComment() {
        return m_earCleanedComment;
    }

    public void setEarCleanedComment(String earCleanedComment) {
        m_earCleanedComment = earCleanedComment;
    }

    public ENTHistory.EarSide getAudiogramSide() {
        return m_audiogramSide;
    }

    public void setAudiogramSide(ENTHistory.EarSide audiogramSide) {
        m_audiogramSide = audiogramSide;
    }

    public String getAudiogramComment() {
        return m_audiogramComment;
    }

    public void setAudiogramComment(String audiogramComment) {
        m_audiogramComment = audiogramComment;
    }

    public boolean getAudiogramRightAway() {
        return m_audiogramRightAway;
    }

    public void setAudiogramRightAway(boolean audiogramRightAway) {
        m_audiogramRightAway = audiogramRightAway;
    }

    public String getAudiogramRightAwayComment() {
        return m_audiogramRightAwayComment;
    }

    public void setAudiogramRightAwayComment(String audiogramRightAwayComment) {
        m_audiogramRightAwayComment = audiogramRightAwayComment;
    }

    public ENTHistory.EarSide getTympanogramSide() {
        return m_tympanogramSide;
    }

    public void setTympanogramSide(ENTHistory.EarSide tympanogramSide) {
        m_tympanogramSide = tympanogramSide;
    }

    public String getTympanogramComment() {
        return m_tympanogramComment;
    }

    public void setTympanogramComment(String tympanogramComment) {
        m_tympanogramComment = tympanogramComment;
    }

    public boolean getTympanogramRightAway() {
        return m_tympanogramRightAway;
    }

    public void setTympanogramRightAway(boolean tympanogramRightAway) {
        m_tympanogramRightAway = tympanogramRightAway;
    }

    public String getTympanogramRightAwayComment() {
        return m_tympanogramRightAwayComment;
    }

    public void setTympanogramRightAwayComment(String tympanogramRightAwayComment) {
        m_tympanogramRightAwayComment = tympanogramRightAwayComment;
    }

    public ENTHistory.EarSide getMastoidDebridedSide() {
        return m_mastoidDebridedSide;
    }

    public void setMastoidDebridedSide(ENTHistory.EarSide mastoidDebridedSide) {
        m_mastoidDebridedSide = mastoidDebridedSide;
    }

    public String getMastoidDebridedComment() {
        return m_mastoidDebridedComment;
    }

    public void setMastoidDebridedComment(String mastoidDebridedComment) {
        m_mastoidDebridedComment = mastoidDebridedComment;
    }

    public boolean getMastoidDebridedHearingAidEval() {
        return m_mastoidDebridedHearingAidEval;
    }

    public void setMastoidDebridedHearingAidEval(boolean mastoidDebridedHearingAidEval) {
        m_mastoidDebridedHearingAidEval = mastoidDebridedHearingAidEval;
    }

    public String getMastoidDebridedHearingAidEvalComment() {
        return m_mastoidDebridedHearingAidEvalComment;
    }

    public void setMastoidDebridedHearingAidEvalComment(String mastoidDebridedHearingAidEvalComment) {
        m_mastoidDebridedHearingAidEvalComment = mastoidDebridedHearingAidEvalComment;
    }

    public boolean getAntibioticDrops() {
        return m_antibioticDrops;
    }

    public void setAntibioticDrops(boolean antibioticDrops) {
        m_antibioticDrops = antibioticDrops;
    }

    public String getAntibioticDropsComment() {
        return m_antibioticDropsComment;
    }

    public void setAntibioticDropsComment(String antibioticDropsComment) {
        m_antibioticDropsComment = antibioticDropsComment;
    }

    public boolean getAntibioticOrally() {
        return m_antibioticOrally;
    }

    public void setAntibioticOrally(boolean antibioticOrally) {
        m_antibioticOrally = antibioticOrally;
    }

    public String getAntibioticOrallyComment() {
        return m_antibioticOrallyComment;
    }

    public void setAntibioticOrallyComment(String antibioticOrallyComment) {
        m_antibioticOrallyComment = antibioticOrallyComment;
    }

    public boolean getAntibioticAcuteInfection() {
        return m_antibioticAcuteInfection;
    }

    public void setAntibioticAcuteInfection(boolean antibioticAcuteInfection) {
        m_antibioticAcuteInfection = antibioticAcuteInfection;
    }

    public String getAntibioticAcuteInfectionComment() {
        return m_antibioticAcuteInfectionComment;
    }

    public void setAntibioticAcuteInfectionComment(String antibioticAcuteInfectionComment) {
        m_antibioticAcuteInfectionComment = antibioticAcuteInfectionComment;
    }

    public boolean getAntibioticAfterWaterExposureInfectionPrevention() {
        return m_antibioticAfterWaterExposureInfectionPrevention;
    }

    public void setAntibioticAfterWaterExposureInfectionPrevention(boolean antibioticAfterWaterExposureInfectionPrevention) {
        m_antibioticAfterWaterExposureInfectionPrevention = antibioticAfterWaterExposureInfectionPrevention;
    }

    public String getAntibioticAfterWaterExposureInfectionPreventionComment() {
        return m_antibioticAfterWaterExposureInfectionPreventionComment;
    }

    public void setAntibioticAfterWaterExposureInfectionPreventionComment(String antibioticAfterWaterExposureInfectionPreventionComment) {
        m_antibioticAfterWaterExposureInfectionPreventionComment = antibioticAfterWaterExposureInfectionPreventionComment;
    }

    public boolean getBoricAcidToday() {
        return m_boricAcidToday;
    }

    public void setBoricAcidToday(boolean boricAcidToday) {
        m_boricAcidToday = boricAcidToday;
    }

    public String getBoricAcidTodayComment() {
        return m_boricAcidTodayComment;
    }

    public void setBoricAcidTodayComment(String boricAcidTodayComment) {
        m_boricAcidTodayComment = boricAcidTodayComment;
    }

    public boolean getBoricAcidForHomeUse() {
        return m_boricAcidForHomeUse;
    }

    public void setBoricAcidForHomeUse(boolean boricAcidForHomeUse) {
        m_boricAcidForHomeUse = boricAcidForHomeUse;
    }

    public String getBoricAcidForHomeUseComment() {
        return m_boricAcidForHomeUseComment;
    }

    public void setBoricAcidForHomeUseComment(String boricAcidForHomeUseComment) {
        m_boricAcidForHomeUseComment = boricAcidForHomeUseComment;
    }

    public ENTHistory.EarSide getBoricAcidSide() {
        return m_boricAcidSide;
    }

    public void setBoricAcidSide(ENTHistory.EarSide boricAcidSide) {
        m_boricAcidSide = boricAcidSide;
    }

    public String getBoricAcidSideComment() {
        return m_boricAcidSideComment;
    }

    public void setBoricAcidSideComment(String boricAcidSideComment) {
        m_boricAcidSideComment = boricAcidSideComment;
    }

    public ENTHistory.EarSide getForeignBodyRemoved() {
        return m_foreignBodyRemoved;
    }

    public void setForeignBodyRemoved(ENTHistory.EarSide foreignBodyRemoved) {
        m_foreignBodyRemoved = foreignBodyRemoved;
    }

    public String getForeignBodyRemovedComment() {
        return m_foreignBodyRemovedComment;
    }

    public void setForeignBodyRemovedComment(String foreignBodyRemovedComment) {
        m_foreignBodyRemovedComment = foreignBodyRemovedComment;
    }

    public boolean getReturn3Months() {
        return m_return3Months;
    }

    public void setReturn3Months(boolean return3Months) {
        m_return3Months = return3Months;
    }

    public boolean getReturn6Months() {
        return m_return6Months;
    }

    public void setReturn6Months(boolean return6Months) {
        m_return6Months = return6Months;
    }

    public boolean getReturnPrn() {
        return m_returnPrn;
    }

    public void setReturnPrn(boolean returnPrn) {
        m_returnPrn = returnPrn;
    }

    public String getReturnComment() {
        return m_returnComment;
    }

    public void setReturnComment(String returnComment) {
        m_returnComment = returnComment;
    }

    public boolean getReferredPvtENTEnsenada() {
        return m_referredPvtENTEnsenada;
    }

    public void setReferredPvtENTEnsenada(boolean referredPvtENTEnsenada) {
        m_referredPvtENTEnsenada = referredPvtENTEnsenada;
    }

    public String getReferredPvtENTEnsenadaComment() {
        return m_referredPvtENTEnsenadaComment;
    }

    public void setReferredPvtENTEnsenadaComment(String referredPvtENTEnsenadaComment) {
        m_referredPvtENTEnsenadaComment = referredPvtENTEnsenadaComment;
    }

    public boolean getReferredChildrensHospitalTJ() {
        return m_referredChildrensHospitalTJ;
    }

    public void setReferredChildrensHospitalTJ(boolean referredChildrensHospitalTJ) {
        m_referredChildrensHospitalTJ = referredChildrensHospitalTJ;
    }

    public String getReferredChildrensHospitalTJComment() {
        return m_referredChildrensHospitalTJComment;
    }

    public void setReferredChildrensHospitalTJComment(String referredChildrensHospitalTJComment) {
        m_referredChildrensHospitalTJComment = referredChildrensHospitalTJComment;
    }

    public ENTHistory.EarSide getTubesTomorrow() {
        return m_tubesTomorrow;
    }

    public void setTubesTomorrow(ENTHistory.EarSide tubesTomorrow) {
        m_tubesTomorrow = tubesTomorrow;
    }

    public String getTubesTomorrowComment() {
        return m_tubesTomorrowComment;
    }

    public void setTubesTomorrowComment(String tubesTomorrowComment) {
        m_tubesTomorrowComment = tubesTomorrowComment;
    }

    public ENTHistory.EarSide getTPlastyTomorrow() {
        return m_tPlastyTomorrow;
    }

    public void setTPlastyTomorrow(ENTHistory.EarSide tPlastyTomorrow) {
        m_tPlastyTomorrow = tPlastyTomorrow;
    }

    public String getTPlastyTomorrowComment() {
        return m_tPlastyTomorrowComment;
    }

    public void setTPlastyTomorrowComment(String tPlastyTomorrowComment) {
        m_tPlastyTomorrowComment = tPlastyTomorrowComment;
    }

    public ENTHistory.EarSide getEuaTomorrow() {
        return m_euaTomorrow;
    }

    public void setEuaTomorrow(ENTHistory.EarSide euaTomorrow) {
        m_euaTomorrow = euaTomorrow;
    }

    public String getEuaTomorrowComment() {
        return m_euaTomorrowComment;
    }

    public void setEuaTomorrowComment(String euaTomorrowComment) {
        m_euaTomorrowComment = euaTomorrowComment;
    }

    public ENTHistory.EarSide getFbRemovalTomorrow() {
        return m_fbRemovalTomorrow;
    }

    public void setFbRemovalTomorrow(ENTHistory.EarSide fbRemovalTomorrow) {
        m_fbRemovalTomorrow = fbRemovalTomorrow;
    }

    public String getFbRemovalTomorrowComment() {
        return m_fbRemovalTomorrowComment;
    }

    public void setFbRemovalTomorrowComment(String fbRemovalTomorrowComment) {
        m_fbRemovalTomorrowComment = fbRemovalTomorrowComment;
    }

    public ENTHistory.EarSide getMiddleEarExploreMyringotomyTomorrow() {
        return m_middleEarExploreMyringotomyTomorrow;
    }

    public void setMiddleEarExploreMyringotomyTomorrow(ENTHistory.EarSide middleEarExploreMyringotomyTomorrow) {
        m_middleEarExploreMyringotomyTomorrow = middleEarExploreMyringotomyTomorrow;
    }

    public String getMiddleEarExploreMyringotomyTomorrowComment() {
        return m_middleEarExploreMyringotomyTomorrowComment;
    }

    public void setMiddleEarExploreMyringotomyTomorrowComment(String middleEarExploreMyringotomyTomorrowComment) {
        m_middleEarExploreMyringotomyTomorrowComment = middleEarExploreMyringotomyTomorrowComment;
    }

    public ENTHistory.EarSide getCerumenTomorrow() {
        return m_cerumenTomorrow;
    }

    public void setCerumenTomorrow(ENTHistory.EarSide cerumenTomorrow) {
        m_cerumenTomorrow = cerumenTomorrow;
    }

    public String getCerumenTomorrowComment() {
        return m_cerumenTomorrowComment;
    }

    public void setCerumenTomorrowComment(String cerumenTomorrowComment) {
        m_cerumenTomorrowComment = cerumenTomorrowComment;
    }

    public ENTHistory.EarSide getGranulomaTomorrow() {
        return m_granulomaTomorrow;
    }

    public void setGranulomaTomorrow(ENTHistory.EarSide granulomaTomorrow) {
        m_granulomaTomorrow = granulomaTomorrow;
    }

    public String getGranulomaTomorrowComment() {
        return m_granulomaTomorrowComment;
    }

    public void setGranulomaTomorrowComment(String granulomaTomorrowComment) {
        m_granulomaTomorrowComment = granulomaTomorrowComment;
    }

    public boolean getSeptorhinoplastyTomorrow() {
        return m_septorhinoplastyTomorrow;
    }

    public void setSeptorhinoplastyTomorrow(boolean septorhinoplastyTomorrow) {
        m_septorhinoplastyTomorrow = septorhinoplastyTomorrow;
    }

    public String getSeptorhinoplastyTomorrowComment() {
        return m_septorhinoplastyTomorrowComment;
    }

    public void setSeptorhinoplastyTomorrowComment(String septorhinoplastyTomorrowComment) {
        m_septorhinoplastyTomorrowComment = septorhinoplastyTomorrowComment;
    }

    public boolean getScarRevisionCleftLipTomorrow() {
        return m_scarRevisionCleftLipTomorrow;
    }

    public void setScarRevisionCleftLipTomorrow(boolean scarRevisionCleftLipTomorrow) {
        m_scarRevisionCleftLipTomorrow = scarRevisionCleftLipTomorrow;
    }

    public String getScarRevisionCleftLipTomorrowComment() {
        return m_scarRevisionCleftLipTomorrowComment;
    }

    public void setScarRevisionCleftLipTomorrowComment(String scarRevisionCleftLipTomorrowComment) {
        m_scarRevisionCleftLipTomorrowComment = scarRevisionCleftLipTomorrowComment;
    }

    public boolean getFrenulectomyTomorrow() {
        return m_frenulectomyTomorrow;
    }

    public void setFrenulectomyTomorrow(boolean frenulectomyTomorrow) {
        m_frenulectomyTomorrow = frenulectomyTomorrow;
    }

    public String getFrenulectomyTomorrowComment() {
        return m_frenulectomyTomorrowComment;
    }

    public void setFrenulectomyTomorrowComment(String frenulectomyTomorrowComment) {
        m_frenulectomyTomorrowComment = frenulectomyTomorrowComment;
    }

    public ENTHistory.EarSide getTubesFuture() {
        return m_tubesFuture;
    }

    public void setTubesFuture(ENTHistory.EarSide tubesFuture) {
        m_tubesFuture = tubesFuture;
    }

    public String getTubesFutureComment() {
        return m_tubesFutureComment;
    }

    public void setTubesFutureComment(String tubesFutureComment) {
        m_tubesFutureComment = tubesFutureComment;
    }

    public ENTHistory.EarSide getTPlastyFuture() {
        return m_tPlastyFuture;
    }

    public void setTPlastyFuture(ENTHistory.EarSide tPlastyFuture) {
        m_tPlastyFuture = tPlastyFuture;
    }

    public String getTPlastyFutureComment() {
        return m_tPlastyFutureComment;
    }

    public void setTPlastyFutureComment(String tPlastyFutureComment) {
        m_tPlastyFutureComment = tPlastyFutureComment;
    }

    public ENTHistory.EarSide getEuaFuture() {
        return m_euaFuture;
    }

    public void setEuaFuture(ENTHistory.EarSide euaFuture) {
        m_euaFuture = euaFuture;
    }

    public String getEuaFutureComment() {
        return m_euaFutureComment;
    }

    public void setEuaFutureComment(String euaFutureComment) {
        m_euaFutureComment = euaFutureComment;
    }

    public ENTHistory.EarSide getFbRemovalFuture() {
        return m_fbRemovalFuture;
    }

    public void setFbRemovalFuture(ENTHistory.EarSide fbRemovalFuture) {
        m_fbRemovalFuture = fbRemovalFuture;
    }

    public String getFbRemovalFutureComment() {
        return m_fbRemovalFutureComment;
    }

    public void setFbRemovalFutureComment(String fbRemovalFutureComment) {
        m_fbRemovalFutureComment = fbRemovalFutureComment;
    }

    public ENTHistory.EarSide getMiddleEarExploreMyringotomyFuture() {
        return m_middleEarExploreMyringotomyFuture;
    }

    public void setMiddleEarExploreMyringotomyFuture(ENTHistory.EarSide middleEarExploreMyringotomyFuture) {
        m_middleEarExploreMyringotomyFuture = middleEarExploreMyringotomyFuture;
    }

    public String getMiddleEarExploreMyringotomyFutureComment() {
        return m_middleEarExploreMyringotomyFutureComment;
    }

    public void setMiddleEarExploreMyringotomyFutureComment(String middleEarExploreMyringotomyFutureComment) {
        m_middleEarExploreMyringotomyFutureComment = middleEarExploreMyringotomyFutureComment;
    }

    public ENTHistory.EarSide getCerumenFuture() {
        return m_cerumenFuture;
    }

    public void setCerumenFuture(ENTHistory.EarSide cerumenFuture) {
        m_cerumenFuture = cerumenFuture;
    }

    public String getCerumenFutureComment() {
        return m_cerumenFutureComment;
    }

    public void setCerumenFutureComment(String cerumenFutureComment) {
        m_cerumenFutureComment = cerumenFutureComment;
    }

    public ENTHistory.EarSide getGranulomaFuture() {
        return m_granulomaFuture;
    }

    public void setGranulomaFuture(ENTHistory.EarSide granulomaFuture) {
        m_granulomaFuture = granulomaFuture;
    }

    public String getGranulomaFutureComment() {
        return m_granulomaFutureComment;
    }

    public void setGranulomaFutureComment(String granulomaFutureComment) {
        m_granulomaFutureComment = granulomaFutureComment;
    }

    public boolean getSeptorhinoplastyFuture() {
        return m_septorhinoplastyFuture;
    }

    public void setSeptorhinoplastyFuture(boolean septorhinoplastyFuture) {
        m_septorhinoplastyFuture = septorhinoplastyFuture;
    }

    public String getSeptorhinoplastyFutureComment() {
        return m_septorhinoplastyFutureComment;
    }

    public void setSeptorhinoplastyFutureComment(String septorhinoplastyFutureComment) {
        m_septorhinoplastyFutureComment = septorhinoplastyFutureComment;
    }

    public boolean getScarRevisionCleftLipFuture() {
        return m_scarRevisionCleftLipFuture;
    }

    public void setScarRevisionCleftLipFuture(boolean scarRevisionCleftLipFuture) {
        m_scarRevisionCleftLipFuture = scarRevisionCleftLipFuture;
    }

    public String getScarRevisionCleftLipFutureComment() {
        return m_scarRevisionCleftLipFutureComment;
    }

    public void setScarRevisionCleftLipFutureComment(String scarRevisionCleftLipFutureComment) {
        m_scarRevisionCleftLipFutureComment = scarRevisionCleftLipFutureComment;
    }

    public boolean getFrenulectomyFuture() {
        return m_frenulectomyFuture;
    }

    public void setFrenulectomyFuture(boolean frenulectomyFuture) {
        m_frenulectomyFuture = frenulectomyFuture;
    }

    public String getFrenulectomyFutureComment() {
        return m_frenulectomyFutureComment;
    }

    public void setFrenulectomyFutureComment(String frenulectomyFutureComment) {
        m_frenulectomyFutureComment = frenulectomyFutureComment;
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

    public int fromJSONObject(JSONObject o)
    {
        int ret = 0;

        try {

            setEarCleanedSide(earSideToEnum(o.getString("earCleanedSide")));
            setEarCleanedComment(o.getString("earCleanedComment"));
            setAudiogramSide(earSideToEnum(o.getString("audiogramSide")));
            setAudiogramComment(o.getString("audiogramComment"));
            setAudiogramRightAway(stringToBool(o.getString("audiogramRightAway")));
            setAudiogramRightAwayComment(o.getString("audiogramRightAwayComment"));
            setTympanogramSide(earSideToEnum(o.getString("tympanogramSide")));
            setTympanogramComment(o.getString("tympanogramComment"));
            setTympanogramRightAway(stringToBool(o.getString("tympanogramRightAway")));
            setTympanogramRightAwayComment(o.getString("tympanogramRightAwayComment"));
            setMastoidDebridedSide(earSideToEnum(o.getString("mastoidDebridedSide")));
            setMastoidDebridedComment(o.getString("mastoidDebridedComment"));
            setMastoidDebridedHearingAidEval(stringToBool(o.getString("mastoidDebridedHearingAidEval")));
            setMastoidDebridedHearingAidEvalComment(o.getString("mastoidDebridedHearingAidEvalComment"));
            setAntibioticDrops(stringToBool(o.getString("antibioticDrops")));
            setAntibioticDropsComment(o.getString("antibioticDropsComment"));
            setAntibioticOrally(stringToBool(o.getString("antibioticOrally")));
            setAntibioticOrallyComment(o.getString("antibioticOrallyComment"));
            setAntibioticAcuteInfection(stringToBool(o.getString("antibioticAcuteInfection")));
            setAntibioticAcuteInfectionComment(o.getString("antibioticAcuteInfectionComment"));
            setAntibioticAfterWaterExposureInfectionPrevention(stringToBool(o.getString("antibioticAfterWaterExposureInfectionPrevention")));
            setAntibioticAfterWaterExposureInfectionPreventionComment(o.getString("antibioticAfterWaterExposureInfectionPreventionComment"));
            setBoricAcidToday(stringToBool(o.getString("boricAcidToday")));
            setBoricAcidTodayComment(o.getString("boricAcidTodayComment"));
            setBoricAcidForHomeUse(stringToBool(o.getString("boricAcidForHomeUse")));
            setBoricAcidForHomeUseComment(o.getString("boricAcidForHomeUseComment"));
            setBoricAcidSide(earSideToEnum(o.getString("boricAcidSide")));
            setBoricAcidSideComment(o.getString("boricAcidSideComment"));
            setForeignBodyRemoved(earSideToEnum(o.getString("foreignBodyRemoved")));
            setForeignBodyRemovedComment(o.getString("foreignBodyRemovedComment"));
            setReturn3Months(stringToBool(o.getString("return3Months")));
            setReturn6Months(stringToBool(o.getString("return6Months")));
            setReturnPrn(stringToBool(o.getString("returnPrn")));
            setReturnComment(o.getString("returnComment"));
            setReferredPvtENTEnsenada(stringToBool(o.getString("referredPvtENTEnsenada")));
            setReferredPvtENTEnsenadaComment(o.getString("referredPvtENTEnsenadaComment"));
            setReferredChildrensHospitalTJ(stringToBool(o.getString("referredChildrensHospitalTJ")));
            setReferredChildrensHospitalTJComment(o.getString("referredChildrensHospitalTJComment"));

            setTubesTomorrow(earSideToEnum(o.getString("tubesTomorrow")));
            setTubesTomorrowComment(o.getString("tubesTomorrowComment"));
            setTPlastyTomorrow(earSideToEnum(o.getString("tPlastyTomorrow")));
            setTPlastyTomorrowComment(o.getString("tPlastyTomorrowComment"));
            setEuaTomorrow(earSideToEnum(o.getString("euaTomorrow")));
            setEuaTomorrowComment(o.getString("euaTomorrowComment"));
            setFbRemovalTomorrow(earSideToEnum(o.getString("fbRemovalTomorrow")));
            setFbRemovalTomorrowComment(o.getString("fbRemovalTomorrowComment"));
            setMiddleEarExploreMyringotomyTomorrow(earSideToEnum(o.getString("middleEarExploreMyringotomyTomorrow")));
            setMiddleEarExploreMyringotomyTomorrowComment(o.getString("middleEarExploreMyringotomyTomorrowComment"));
            setCerumenTomorrow(earSideToEnum(o.getString("cerumenTomorrow")));
            setCerumenTomorrowComment(o.getString("cerumenTomorrowComment"));
            setGranulomaTomorrow(earSideToEnum(o.getString("granulomaTomorrow")));
            setGranulomaTomorrowComment(o.getString("granulomaTomorrowComment"));
            setSeptorhinoplastyTomorrow(stringToBool(o.getString("septorhinoplastyTomorrow")));
            setSeptorhinoplastyTomorrowComment(o.getString("septorhinoplastyTomorrowComment"));
            setScarRevisionCleftLipTomorrow(stringToBool(o.getString("scarRevisionCleftLipTomorrow")));
            setScarRevisionCleftLipTomorrowComment(o.getString("scarRevisionCleftLipTomorrowComment"));
            setFrenulectomyTomorrow(stringToBool(o.getString("frenulectomyTomorrow")));
            setFrenulectomyTomorrowComment(o.getString("frenulectomyTomorrowComment"));

            setTubesFuture(earSideToEnum(o.getString("tubesFuture")));
            setTubesFutureComment(o.getString("tubesFutureComment"));
            setTPlastyFuture(earSideToEnum(o.getString("tPlastyFuture")));
            setTPlastyFutureComment(o.getString("tPlastyFutureComment"));
            setEuaFuture(earSideToEnum(o.getString("euaFuture")));
            setEuaFutureComment(o.getString("euaFutureComment"));
            setFbRemovalFuture(earSideToEnum(o.getString("fbRemovalFuture")));
            setFbRemovalFutureComment(o.getString("fbRemovalFutureComment"));
            setMiddleEarExploreMyringotomyFuture(earSideToEnum(o.getString("middleEarExploreMyringotomyFuture")));
            setMiddleEarExploreMyringotomyFutureComment(o.getString("middleEarExploreMyringotomyFutureComment"));
            setCerumenFuture(earSideToEnum(o.getString("cerumenFuture")));
            setCerumenFutureComment(o.getString("cerumenFutureComment"));
            setGranulomaFuture(earSideToEnum(o.getString("granulomaFuture")));
            setGranulomaFutureComment(o.getString("granulomaFutureComment"));
            setSeptorhinoplastyFuture(stringToBool(o.getString("septorhinoplastyFuture")));
            setSeptorhinoplastyFutureComment(o.getString("septorhinoplastyFutureComment"));
            setScarRevisionCleftLipFuture(stringToBool(o.getString("scarRevisionCleftLipFuture")));
            setScarRevisionCleftLipFutureComment(o.getString("scarRevisionCleftLipFutureComment"));
            setFrenulectomyFuture(stringToBool(o.getString("frenulectomyFuture")));
            setFrenulectomyFutureComment(o.getString("frenulectomyFutureComment"));

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
            data.put("earCleanedSide", earSideToString(getEarCleanedSide()));
            data.put("earCleanedComment", getEarCleanedComment());
            data.put("audiogramSide", earSideToString(getAudiogramSide()));
            data.put("audiogramComment", getAudiogramComment());
            data.put("audiogramRightAway", boolToString(getAudiogramRightAway()));
            data.put("audiogramRightAwayComment", getAudiogramRightAwayComment());
            data.put("tympanogramSide", earSideToString(getTympanogramSide()));
            data.put("tympanogramComment", getTympanogramComment());
            data.put("tympanogramRightAway", boolToString(getTympanogramRightAway()));
            data.put("tympanogramRightAwayComment", getTympanogramRightAwayComment());
            data.put("mastoidDebridedSide", earSideToString(getMastoidDebridedSide()));
            data.put("mastoidDebridedComment", getMastoidDebridedComment());
            data.put("mastoidDebridedHearingAidEval", boolToString(getMastoidDebridedHearingAidEval()));
            data.put("mastoidDebridedHearingAidEvalComment", getMastoidDebridedHearingAidEvalComment());
            data.put("antibioticDrops", boolToString(getAntibioticDrops()));
            data.put("antibioticDropsComment", getAntibioticDropsComment());
            data.put("antibioticOrally", boolToString(getAntibioticOrally()));
            data.put("antibioticOrallyComment", getAntibioticOrallyComment());
            data.put("antibioticAcuteInfection", boolToString(getAntibioticAcuteInfection()));
            data.put("antibioticAcuteInfectionComment", getAntibioticAcuteInfectionComment());
            data.put("antibioticAfterWaterExposureInfectionPrevention", boolToString(getAntibioticAfterWaterExposureInfectionPrevention()));
            data.put("antibioticAfterWaterExposureInfectionPreventionComment", getAntibioticAfterWaterExposureInfectionPreventionComment());
            data.put("boricAcidToday", boolToString(getBoricAcidToday()));
            data.put("boricAcidTodayComment", getBoricAcidTodayComment());
            data.put("boricAcidForHomeUse", boolToString(getBoricAcidForHomeUse()));
            data.put("boricAcidForHomeUseComment", getBoricAcidForHomeUseComment());
            data.put("boricAcidSide", earSideToString(getBoricAcidSide()));
            data.put("boricAcidSideComment", getBoricAcidSideComment());
            data.put("foreignBodyRemoved", earSideToString(getForeignBodyRemoved()));
            data.put("foreignBodyRemovedComment", getForeignBodyRemovedComment());
            data.put("return3Months", boolToString(getReturn3Months()));
            data.put("return6Months", boolToString(getReturn6Months()));
            data.put("returnPrn", boolToString(getReturnPrn()));
            data.put("returnComment", getReturnComment());
            data.put("referredPvtENTEnsenada", boolToString(getReferredPvtENTEnsenada()));
            data.put("referredPvtENTEnsenadaComment", getReferredPvtENTEnsenadaComment());
            data.put("referredChildrensHospitalTJ", boolToString(getReferredChildrensHospitalTJ()));
            data.put("referredChildrensHospitalTJComment", getReferredChildrensHospitalTJComment());
            data.put("tubesTomorrow", earSideToString(getTubesTomorrow()));
            data.put("tubesTomorrowComment", getTubesTomorrowComment());
            data.put("tPlastyTomorrow", earSideToString(getTPlastyTomorrow()));
            data.put("tPlastyTomorrowComment", getTPlastyTomorrowComment());
            data.put("euaTomorrow", earSideToString(getEuaTomorrow()));
            data.put("euaTomorrowComment", getEuaTomorrowComment());
            data.put("fbRemovalTomorrow", earSideToString(getFbRemovalTomorrow()));
            data.put("fbRemovalTomorrowComment", getFbRemovalTomorrowComment());
            data.put("middleEarExploreMyringotomyTomorrow", earSideToString(getMiddleEarExploreMyringotomyTomorrow()));
            data.put("middleEarExploreMyringotomyTomorrowComment", getMiddleEarExploreMyringotomyTomorrowComment());
            data.put("cerumenTomorrow", earSideToString(getCerumenTomorrow()));
            data.put("cerumenTomorrowComment", getCerumenTomorrowComment());
            data.put("granulomaTomorrow", earSideToString(getGranulomaTomorrow()));
            data.put("granulomaTomorrowComment", getGranulomaTomorrowComment());
            data.put("septorhinoplastyTomorrow", boolToString(getSeptorhinoplastyTomorrow()));
            data.put("septorhinoplastyTomorrowComment", getSeptorhinoplastyTomorrowComment());
            data.put("scarRevisionCleftLipTomorrow", boolToString(getScarRevisionCleftLipTomorrow()));
            data.put("scarRevisionCleftLipTomorrowComment", getScarRevisionCleftLipTomorrowComment());
            data.put("frenulectomyTomorrow", boolToString(getFrenulectomyTomorrow()));
            data.put("frenulectomyTomorrowComment", getFrenulectomyTomorrowComment());
            data.put("tubesFuture", earSideToString(getTubesFuture()));
            data.put("tubesFutureComment", getTubesFutureComment());
            data.put("tPlastyFuture", earSideToString(getTPlastyFuture()));
            data.put("tPlastyFutureComment", getTPlastyFutureComment());
            data.put("euaFuture", earSideToString(getEuaFuture()));
            data.put("euaFutureComment", getEuaFutureComment());
            data.put("fbRemovalFuture", earSideToString(getFbRemovalFuture()));
            data.put("fbRemovalFutureComment", getFbRemovalFutureComment());
            data.put("middleEarExploreMyringotomyFuture", earSideToString(getMiddleEarExploreMyringotomyFuture()));
            data.put("middleEarExploreMyringotomyFutureComment", getMiddleEarExploreMyringotomyFutureComment());
            data.put("cerumenFuture", earSideToString(getCerumenFuture()));
            data.put("cerumenFutureComment", getCerumenFutureComment());
            data.put("granulomaFuture", earSideToString(getGranulomaFuture()));
            data.put("granulomaFutureComment", getGranulomaFutureComment());
            data.put("septorhinoplastyFuture", boolToString(getSeptorhinoplastyFuture()));
            data.put("septorhinoplastyFutureComment", getSeptorhinoplastyFutureComment());
            data.put("scarRevisionCleftLipFuture", boolToString(getScarRevisionCleftLipFuture()));
            data.put("scarRevisionCleftLipFutureComment", getScarRevisionCleftLipFutureComment());
            data.put("frenulectomyFuture", boolToString(getFrenulectomyFuture()));
            data.put("frenulectomyFutureComment", getFrenulectomyFutureComment());
        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
            data = null;
        }
        return data;
    }


    public ENTTreatment() {
    }

    public ENTTreatment(ENTTreatment rhs) {
         m_patient = rhs.m_patient;
         m_clinic = rhs.m_clinic;
         m_id = rhs.m_id;
         m_comment = rhs.m_comment;
         m_username = rhs.m_username;

         m_earCleanedSide = rhs.m_earCleanedSide;
         m_earCleanedComment = rhs.m_earCleanedComment;
         m_audiogramSide = rhs.m_audiogramSide;
         m_audiogramComment = rhs.m_audiogramComment;
         m_audiogramRightAway = rhs.m_audiogramRightAway;
         m_audiogramRightAwayComment = rhs.m_audiogramRightAwayComment;
         m_tympanogramSide = rhs.m_tympanogramSide;
         m_tympanogramComment = rhs.m_tympanogramComment;
         m_tympanogramRightAway = rhs.m_tympanogramRightAway;
         m_tympanogramRightAwayComment = rhs.m_tympanogramRightAwayComment;
         m_mastoidDebridedSide = rhs.m_mastoidDebridedSide;
         m_mastoidDebridedComment = rhs.m_mastoidDebridedComment;
         m_mastoidDebridedHearingAidEval = rhs.m_mastoidDebridedHearingAidEval;
         m_mastoidDebridedHearingAidEvalComment = rhs.m_mastoidDebridedHearingAidEvalComment;
         m_antibioticDrops = rhs.m_antibioticDrops;
         m_antibioticDropsComment = rhs.m_antibioticDropsComment;
         m_antibioticOrally = rhs.m_antibioticOrally;
         m_antibioticOrallyComment = rhs.m_antibioticOrallyComment;
         m_antibioticAcuteInfection = rhs.m_antibioticAcuteInfection;
         m_antibioticAcuteInfectionComment = rhs.m_antibioticAcuteInfectionComment;
         m_antibioticAfterWaterExposureInfectionPrevention = rhs.m_antibioticAfterWaterExposureInfectionPrevention;
         m_antibioticAfterWaterExposureInfectionPreventionComment = rhs.m_antibioticAfterWaterExposureInfectionPreventionComment;
         m_boricAcidToday = rhs.m_boricAcidToday;
         m_boricAcidTodayComment = rhs.m_boricAcidTodayComment;
         m_boricAcidForHomeUse = rhs.m_boricAcidForHomeUse;
         m_boricAcidForHomeUseComment = rhs.m_boricAcidForHomeUseComment;
         m_boricAcidSide = rhs.m_boricAcidSide;
         m_boricAcidSideComment = rhs.m_boricAcidSideComment;
         m_foreignBodyRemoved = rhs.m_foreignBodyRemoved;
         m_foreignBodyRemovedComment = rhs.m_foreignBodyRemovedComment;
         m_return3Months = rhs.m_return3Months;
         m_return6Months = rhs.m_return6Months;
         m_returnPrn = rhs.m_returnPrn;
         m_returnComment = rhs.m_returnComment;
         m_referredPvtENTEnsenada = rhs.m_referredPvtENTEnsenada;
         m_referredPvtENTEnsenadaComment = rhs.m_referredPvtENTEnsenadaComment;
         m_referredChildrensHospitalTJ = rhs.m_referredChildrensHospitalTJ;
         m_referredChildrensHospitalTJComment = rhs.m_referredChildrensHospitalTJComment;

         m_tubesTomorrow = rhs.m_tubesTomorrow;
         m_tubesTomorrowComment = rhs.m_tubesTomorrowComment;
         m_tPlastyTomorrow = rhs.m_tPlastyTomorrow;
         m_tPlastyTomorrowComment = rhs.m_tPlastyTomorrowComment;
         m_euaTomorrow = rhs.m_euaTomorrow;
         m_euaTomorrowComment = rhs.m_euaTomorrowComment;
         m_fbRemovalTomorrow = rhs.m_fbRemovalTomorrow;
         m_fbRemovalTomorrowComment = rhs.m_fbRemovalTomorrowComment;
         m_middleEarExploreMyringotomyTomorrow = rhs.m_middleEarExploreMyringotomyTomorrow;
         m_middleEarExploreMyringotomyTomorrowComment = rhs.m_middleEarExploreMyringotomyTomorrowComment;
         m_cerumenTomorrow = rhs.m_cerumenTomorrow;
         m_cerumenTomorrowComment = rhs.m_cerumenTomorrowComment;
         m_granulomaTomorrow = rhs.m_granulomaTomorrow;
         m_granulomaTomorrowComment = rhs.m_granulomaTomorrowComment;
         m_septorhinoplastyTomorrow = rhs.m_septorhinoplastyTomorrow;
         m_septorhinoplastyTomorrowComment = rhs.m_septorhinoplastyTomorrowComment;
         m_scarRevisionCleftLipTomorrow = rhs.m_scarRevisionCleftLipTomorrow;
         m_scarRevisionCleftLipTomorrowComment = rhs.m_scarRevisionCleftLipTomorrowComment;
         m_frenulectomyTomorrow = rhs.m_frenulectomyTomorrow;
         m_frenulectomyTomorrowComment = rhs.m_frenulectomyTomorrowComment;

         m_tubesFuture = rhs.m_tubesFuture;
         m_tubesFutureComment = rhs.m_tubesFutureComment;
         m_tPlastyFuture = rhs.m_tPlastyFuture;
         m_tPlastyFutureComment = rhs.m_tPlastyFutureComment;
         m_euaFuture = rhs.m_euaFuture;
         m_euaFutureComment = rhs.m_euaFutureComment;
         m_fbRemovalFuture = rhs.m_fbRemovalFuture;
         m_fbRemovalFutureComment = rhs.m_fbRemovalFutureComment;
         m_middleEarExploreMyringotomyFuture = rhs.m_middleEarExploreMyringotomyFuture;
         m_middleEarExploreMyringotomyFutureComment = rhs.m_middleEarExploreMyringotomyFutureComment;
         m_cerumenFuture = rhs.m_cerumenFuture;
         m_cerumenFutureComment = rhs.m_cerumenFutureComment;
         m_granulomaFuture = rhs.m_granulomaFuture;
         m_granulomaFutureComment = rhs.m_granulomaFutureComment;
         m_septorhinoplastyFuture = rhs.m_septorhinoplastyFuture;
         m_septorhinoplastyFutureComment = rhs.m_septorhinoplastyFutureComment;
         m_scarRevisionCleftLipFuture = rhs.m_scarRevisionCleftLipFuture;
         m_scarRevisionCleftLipFutureComment = rhs.m_scarRevisionCleftLipFutureComment;
         m_frenulectomyFuture = rhs.m_frenulectomyFuture;
         m_frenulectomyFutureComment = rhs.m_frenulectomyFutureComment;
    }
}
