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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
    List of cdt codes
 */

public class CDTCodesModelList {
    static private CDTCodesModelList m_instance = null;
    private List<CDTCodesModel> m_list = new ArrayList<CDTCodesModel>();
    private ArrayList<JSONObject> m_objs = new ArrayList<JSONObject>();
    private ArrayList<String> m_strs = new ArrayList<String>();

    public List<CDTCodesModel> getModel() {
        return m_list;
    }

    public String[] getModelStringArray() {
        String [] ret;

        ret = m_strs.toArray(new String[0]);
        return ret;
    }

    public ArrayList<String> getModelStrings() {
        return m_strs;
    }

    private CDTCodesModelList(){}

    synchronized static public CDTCodesModelList getInstance()
    {
        if (m_instance == null) {
            m_instance = new CDTCodesModelList();
        }
        return m_instance;
    }

    public boolean setModelData(JSONArray items) {
        boolean ret = true;
        m_strs.clear();
        m_list.clear();
        for (int i = 0; i < items.length(); i++) {
            try {
                CDTCodesModel model = new CDTCodesModel(items.getJSONObject(i), false);
                m_list.add(model);
                m_strs.add(model.repr());
            } catch (Exception e) {
                ret = false;
                break;
            }
        }
        return ret;
    }
}
