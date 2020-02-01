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

public class ENTHistory implements Serializable {

    public enum ENTDuration {
        EAR_DURATION_NONE,
        EAR_DURATION_DAYS,
        EAR_DURATION_WEEKS,
        EAR_DURATION_MONTHS,
        EAR_DURATION_INTERMITTENT
    }

    public enum EarSide {
        EAR_SIDE_LEFT,
        EAR_SIDE_RIGHT,
        EAR_SIDE_BOTH,
        EAR_SIDE_NONE
    }

    private ENTDuration m_painDuration = ENTDuration.EAR_DURATION_NONE;
    private EarSide m_painSide = EarSide.EAR_SIDE_NONE;
    private ENTDuration m_drainageDuration = ENTDuration.EAR_DURATION_NONE;
    private EarSide m_drainageSide = EarSide.EAR_SIDE_NONE;
    private ENTDuration m_hearingLossDuration  = ENTDuration.EAR_DURATION_NONE;
    private EarSide m_hearingLossSide = EarSide.EAR_SIDE_NONE;
    private int m_patient;
    private int m_clinic;
    private int m_id;
    private String m_comment = "";
    private String m_username = "";

    public static ENTDuration entDurationToEnum(String duration)
    {
        ENTDuration ret = ENTDuration.EAR_DURATION_NONE;

        if (duration.equals("days")) {
            ret = ENTDuration.EAR_DURATION_DAYS;
        } else  if (duration.equals("weeks")) {
            ret = ENTDuration.EAR_DURATION_WEEKS;
        } else  if (duration.equals("months")) {
            ret = ENTDuration.EAR_DURATION_MONTHS;
        }  if (duration.equals("intermittent")) {
        ret = ENTDuration.EAR_DURATION_INTERMITTENT;
    }
        return ret;
    }

    public static String entDurationToString(ENTDuration duration)
    {
        String ret = "none";

        if (duration == ENTDuration.EAR_DURATION_DAYS) {
            ret = "days";
        } else if (duration == ENTDuration.EAR_DURATION_WEEKS ) {
            ret = "weeks";
        } else if (duration == ENTDuration.EAR_DURATION_MONTHS) {
            ret = "months";
        } else if (duration == ENTDuration.EAR_DURATION_INTERMITTENT) {
            ret = "intermittent";
        }

        return ret;
    }

    public static EarSide earSideToEnum(String side)
    {
        EarSide ret = EarSide.EAR_SIDE_BOTH;

        if (side.equals("left")) {
            ret = EarSide.EAR_SIDE_LEFT;
        } else if (side.equals("right")) {
            ret = EarSide.EAR_SIDE_RIGHT;
        } else if (side.equals("none")) {
            ret = EarSide.EAR_SIDE_NONE;
        }
        return ret;
    }

    public static String earSideToString(EarSide side)
    {
        String ret = "both";

        if (side == EarSide.EAR_SIDE_LEFT) {
            ret = "left";
        } else if (side == EarSide.EAR_SIDE_RIGHT) {
            ret = "right";
        } else if (side == EarSide.EAR_SIDE_NONE) {
            ret = "none";
        }

        return ret;
    }

    public String getPainDurationAsString()
    {
        return entDurationToString(m_painDuration);
    }

    public String getPainSideAsString()
    {
        return earSideToString(m_painSide);
    }

    public String getHearingLossDurationAsString()
    {
        return entDurationToString(m_hearingLossDuration);
    }

    public String getHearingLossSideAsString()
    {
        return earSideToString(m_hearingLossSide);
    }

    public String getDrainageDurationAsString()
    {
        return entDurationToString(m_drainageDuration);
    }

    public String getDrainageSideSideAsString()
    {
        return earSideToString(m_drainageSide);
    }

    public int fromJSONObject(JSONObject o)
    {
        int ret = 0;

        try {
            setPainDuration(entDurationToEnum(o.getString("painDuration")));
            setPainSide(earSideToEnum(o.getString("painSide")));
            setHearingLossDuration(entDurationToEnum(o.getString("hearingLossDuration")));
            setHearingLossSide(earSideToEnum(o.getString("hearingLossSide")));
            setDrainageDuration(entDurationToEnum(o.getString("drainageDuration")));
            setDrainageSide(earSideToEnum(o.getString("drainageSide")));
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

            data.put("painDuration", entDurationToString(getPainDuration()));
            data.put("painSide", earSideToString(getPainSide()));
            data.put("hearingLossDuration",  entDurationToString(getHearingLossDuration()));
            data.put("hearingLossSide", earSideToString(getHearingLossSide()));
            data.put("drainageDuration",  entDurationToString(getDrainageDuration()));
            data.put("drainageSide", earSideToString(getDrainageSide()));
            data.put("patient", getPatient());
            data.put("clinic", getClinic());
            data.put("comment", getComment());
            data.put("username", getUsername());

        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
            data = null;
        }
        return data;
    }

    public ENTDuration getDrainageDuration() {
        return m_drainageDuration;
    }

    public void setDrainageDuration(ENTDuration duration) {
        this.m_drainageDuration = duration;
    }

    public EarSide getDrainageSide() {
        return m_drainageSide;
    }

    public void setDrainageSide(EarSide side) {
        this.m_drainageSide = side;
    }

    public ENTDuration getPainDuration() {
        return m_painDuration;
    }

    public void setPainDuration(ENTDuration duration) {
        this.m_painDuration = duration;
    }

    public void setPainSide(EarSide side) {
        this.m_painSide = side;
    }

    public EarSide getPainSide() {
        return m_painSide;
    }

    public ENTDuration getHearingLossDuration() {
        return m_hearingLossDuration;
    }

    public void setHearingLossDuration(ENTDuration duration) {
        this.m_hearingLossDuration = duration;
    }

    public EarSide getHearingLossSide() {
        return m_hearingLossSide;
    }

    public void setHearingLossSide(EarSide side) {
        this.m_hearingLossSide = side;
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


    public ENTHistory() {
    }

    public ENTHistory(ENTHistory rhs) {
        this.m_painDuration = rhs.m_painDuration;
        this.m_painSide = rhs.m_painSide;
        this.m_drainageDuration = rhs.m_drainageDuration;
        this.m_drainageSide = rhs.m_drainageSide;
        this.m_hearingLossDuration = rhs.m_hearingLossDuration;
        this.m_hearingLossSide = rhs.m_hearingLossSide;
        this.m_patient = rhs.m_patient;
        this.m_clinic = rhs.m_clinic;
        this.m_id = rhs.m_id;
        this.m_username = rhs.m_username;
        this.m_comment = rhs.m_comment;
    }
}
