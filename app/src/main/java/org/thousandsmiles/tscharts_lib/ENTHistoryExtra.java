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

public class ENTHistoryExtra implements Serializable {

    private int m_id;
    private int m_history;
    private ENTHistory.ENTDuration m_duration = ENTHistory.ENTDuration.EAR_DURATION_NONE;
    private ENTHistory.EarSide m_side = ENTHistory.EarSide.EAR_SIDE_NONE;
    private String m_name;

    public int fromJSONObject(JSONObject o)
    {
        int ret = 0;

        try {
            setId(o.getInt("id"));
            setSide(ENTHistory.earSideToEnum(o.getString("side")));
            setDuration(ENTHistory.entDurationToEnum(o.getString("duration")));
            setName(o.getString("name"));

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

            data.put("duration", getDuration());
            data.put("side", getSide());
            data.put("name", getName());
        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
            data = null;
        }
        return data;
    }


    public int getId() {
        return m_id;
    }

    public void setId(int m_id) {
        this.m_id = m_id;
    }

    public String getDuration()
    {
        return ENTHistory.entDurationToString(m_duration);
    }

    public String getSide()
    {
        return ENTHistory.earSideToString(m_side);
    }

    public String getName()
    {
        return m_name;
    }

    public void setDuration(ENTHistory.ENTDuration duration) {
        this.m_duration = duration;
    }

    public void setSide(ENTHistory.EarSide side) {
        this.m_side = side;
    }
    public void setName(String name) {
        this.m_name = name;
    }
    public ENTHistoryExtra() {
    }

    public ENTHistoryExtra(ENTHistoryExtra rhs) {
        this.m_history = rhs.m_history;

        this.m_id = rhs.m_id;
        this.m_duration = rhs.m_duration;
        this.m_side = rhs.m_side;
        this.m_name = rhs.m_name;
    }
}
