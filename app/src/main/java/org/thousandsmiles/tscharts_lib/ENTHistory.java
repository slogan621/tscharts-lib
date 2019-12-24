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

    public enum ENTType {
        ENT_TYPE_HEARING_LOSS,
        ENT_TYPE_DRAINAGE,
        ENT_TYPE_PAIN,
        ENT_TYPE_OTHER
    }

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
        EAR_SIDE_BOTH
    }

    private ENTType m_type;
    private ENTDuration m_duration;
    private EarSide m_side;
    private int m_patient;
    private int m_clinic;
    private int m_id;
    private String m_name;
    private String m_comment;
    private String m_username;

    public ENTType entTypeToEnum(String type)
    {
        ENTType ret = ENTType.ENT_TYPE_OTHER;

        if (type.equals("hearing loss")) {
            ret = ENTType.ENT_TYPE_HEARING_LOSS;
        } else if (type.equals("drainage")) {
            ret = ENTType.ENT_TYPE_DRAINAGE;
        } else if (type.equals("pain")) {
            ret = ENTType.ENT_TYPE_PAIN;
        }
        return ret;
    }

    public String entTypeToString(ENTType type)
    {
        String ret = "other";

        if (type == ENTType.ENT_TYPE_HEARING_LOSS) {
            ret = "heading loss";
        } else if (type == ENTType.ENT_TYPE_DRAINAGE ) {
            ret = "drainage";
        } else if (type == ENTType.ENT_TYPE_PAIN) {
            ret = "pain";
        }

        return ret;
    }

    public ENTDuration entDurationToEnum(String duration)
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

    public String entDurationToString(ENTDuration duration)
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

    public EarSide earSideToEnum(String side)
    {
        EarSide ret = EarSide.EAR_SIDE_BOTH;

        if (side.equals("left")) {
            ret = EarSide.EAR_SIDE_LEFT;
        } else if (side.equals("right")) {
            ret = EarSide.EAR_SIDE_RIGHT;
        }
        return ret;
    }

    public String earSideToString(EarSide side)
    {
        String ret = "both";

        if (side == EarSide.EAR_SIDE_LEFT) {
            ret = "left";
        } else if (side == EarSide.EAR_SIDE_RIGHT) {
            ret = "right";
        }

        return ret;
    }

    public String getTypeAsString()
    {
        return entTypeToString(m_type);
    }

    public String getDurationAsString()
    {
        return entDurationToString(m_duration);
    }

    public String getSideAsString()
    {
        return earSideToString(m_side);
    }

    public int fromJSONObject(JSONObject o)
    {
        int ret = 0;

        try {
            setType(entTypeToEnum(o.getString("type")));
            setDuration(entDurationToEnum(o.getString("duration")));
            setSide(earSideToEnum(o.getString("side")));
            setId(o.getInt("id"));
            setPatient(o.getInt("patient"));
            setClinic(o.getInt("clinic"));
            setName(o.getString("name"));
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

            data.put("type", getType());
            data.put("duration", getDuration());
            data.put("side", getSide());
            data.put("patient", getPatient());
            data.put("clinic", getClinic());
            data.put("name", getName());
            data.put("comment", getComment());
            data.put("username", getUsername());

        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
            data = null;
        }
        return data;
    }

    public ENTType getType() {
        return m_type;
    }

    public void setType(ENTType type) {
        this.m_type = type;
    }

    public ENTDuration getDuration() {
        return m_duration;
    }

    public void setDuration(ENTDuration duration) {
        this.m_duration = duration;
    }

    public void setSide(EarSide side) {
        this.m_side = side;
    }

    public EarSide getSide() {
        return m_side;
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

    public String getName() {
        return m_name;
    }

    public void setName(String m_name) {
        this.m_name = m_name;
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
        this.m_type = rhs.m_type;
        this.m_duration = rhs.m_duration;
        this.m_side = rhs.m_side;
        this.m_patient = rhs.m_patient;
        this.m_clinic = rhs.m_clinic;
        this.m_id = rhs.m_id;
        this.m_name = rhs.m_name;
        this.m_username = rhs.m_username;
        this.m_comment = rhs.m_comment;
    }
}
