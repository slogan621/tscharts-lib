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

public class CDTCodesModel {
    private String m_repr;
    private String m_code;
    private String m_category;
    private String m_desc;
    private int m_id;
    private JSONObject m_obj;

    public CDTCodesModel(JSONObject obj) throws Exception {
        CDTCodesModelInit(obj);
    }

    public CDTCodesModel(CDTCodesModel rhs) {
        this.m_repr = rhs.m_repr;
        this.m_code = rhs.m_code;
        this.m_category = rhs.m_category;
        this.m_desc = rhs.m_desc;
        this.m_id = rhs.m_id;
        this.m_obj = rhs.m_obj;
    }

    public void CDTCodesModelInit(JSONObject obj) throws Exception {
        this.m_obj = obj;

        try {
            this.m_id = m_obj.getInt("id");
            this.m_category = m_obj.getString("category");
            this.m_code = m_obj.getString("code");
            this.m_desc = m_obj.getString("desc");
            this.m_repr = String.format("%s %s %s", m_category, m_code, m_desc);   // what is displayed and searched
        }
        catch (JSONException e) {
            throw e;
        }
    }

    public int getId() {
        return m_id;
    }

    public String getCategory() {
        return m_category;
    }

    public String getCode() {
        return m_code;
    }

    public String getDesc() {
        return m_desc;
    }

    public String repr() {
        return m_repr;
    }
}
