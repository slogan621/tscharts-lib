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

public class ENTExam implements Serializable {

    public enum ENTTube {
        ENT_TUBE_IN_PLACE,
        ENT_TUBE_EXTRUDING,
        ENT_TUBE_IN_CANAL,
        ENT_TUBE_NONE
    }

    public enum ENTTympano {
        ENT_TYMPANOSCLEROSIS_ANTERIOR,
        ENT_TYMPANOSCLEROSIS_POSTERIOR,
        ENT_TYMPANOSCLEROSIS_25,
        ENT_TYMPANOSCLEROSIS_50,
        ENT_TYMPANOSCLEROSIS_75,
        ENT_TYMPANOSCLEROSIS_TOTAL,
        ENT_TYMPANOSCLEROSIS_NONE
    }

    public enum ENTPerf {
        ENT_PERF_ANTERIOR,
        ENT_PERF_POSTERIOR,
        ENT_PERF_MARGINAL,
        ENT_PERF_25,
        ENT_PERF_50,
        ENT_PERF_75,
        ENT_PERF_TOTAL,
        ENT_PERF_NONE
    }

    public enum ENTVoiceTest {
        ENT_VOICE_TEST_NORMAL,
        ENT_VOICE_TEST_ABNORMAL,
        ENT_VOICE_TEST_NONE
    }

    public enum ENTForkTest {
        ENT_FORK_TEST_A_GREATER_B,
        ENT_FORK_TEST_B_GREATER_A,
        ENT_FORK_TEST_EQUAL,
        ENT_FORK_TEST_NONE
    }

    public enum ENTBC {
        ENT_BC_AD_LAT_TO_AD,
        ENT_BC_AD_LAT_TO_AS,
        ENT_BC_AS_LAT_TO_AD,
        ENT_BC_AS_LAT_TO_AS,
        ENT_BC_NONE
    }

    public enum ENTFork {
        ENT_FORK_256,
        ENT_FORK_512,
        ENT_FORK_NONE
    }

    private ENTHistory.EarSide m_normal;
    private ENTHistory.EarSide m_microtia;
    private ENTHistory.EarSide m_wax;
    private ENTHistory.EarSide m_drainage;
    private ENTHistory.EarSide m_externalOtitis;
    private ENTHistory.EarSide m_fb;

    private ENTTube m_tubeRight;
    private ENTTube m_tubeLeft;

    private ENTTympano m_tympanoRight;
    private ENTTympano m_tympanoLeft;

    private ENTHistory.EarSide m_tmGranulations;
    private ENTHistory.EarSide m_tmRetraction;
    private ENTHistory.EarSide m_tmAtelectasis;

    private ENTPerf m_perfRight;
    private ENTPerf m_perfLeft;

    private ENTVoiceTest m_voiceTest;

    private ENTForkTest m_forkAD;
    private ENTForkTest m_forkAS;

    private ENTBC m_bc;

    private ENTFork m_fork;

    private int m_patient;
    private int m_clinic;
    private int m_id;
    private String m_comment;
    private String m_username;

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

    public ENTTube tubeToEnum(String val)
    {
        ENTTube ret = ENTTube.ENT_TUBE_NONE;

        if (val.equals("in place")) {
            ret = ENTTube.ENT_TUBE_IN_PLACE;
        } else if (val.equals("extruding")) {
            ret = ENTTube.ENT_TUBE_EXTRUDING;
        } else if (val.equals("in canal")) {
            ret = ENTTube.ENT_TUBE_IN_CANAL;
        }
        return ret;
    }

    public String tubeToString(ENTTube val)
    {
        String ret = "none";

        if (val == ENTTube.ENT_TUBE_IN_PLACE) {
            ret = "in place";
        } else if (val == ENTTube.ENT_TUBE_EXTRUDING) {
            ret = "extruding";
        } else if (val == ENTTube.ENT_TUBE_IN_CANAL) {
            ret = "in canal";
        }

        return ret;
    }

    public ENTTympano tympanoToEnum(String val)
    {
        ENTTympano ret = ENTTympano.ENT_TYMPANOSCLEROSIS_NONE;

        if (val.equals("anterior")) {
            ret = ENTTympano.ENT_TYMPANOSCLEROSIS_ANTERIOR;
        } else if (val.equals("posterior")) {
            ret = ENTTympano.ENT_TYMPANOSCLEROSIS_POSTERIOR;
        } else if (val.equals("25 percent")) {
            ret = ENTTympano.ENT_TYMPANOSCLEROSIS_25;
        } else if (val.equals("50 percent")) {
            ret = ENTTympano.ENT_TYMPANOSCLEROSIS_50;
        } else if (val.equals("75 percent")) {
            ret = ENTTympano.ENT_TYMPANOSCLEROSIS_75;
        } else if (val.equals("total")) {
            ret = ENTTympano.ENT_TYMPANOSCLEROSIS_TOTAL;
        } 
        return ret;
    }

    public String tympanoToString(ENTTympano val)
    {
        String ret = "none";

        if (val == ENTTympano.ENT_TYMPANOSCLEROSIS_ANTERIOR) {
            ret = "anterior";
        } else if (val == ENTTympano.ENT_TYMPANOSCLEROSIS_POSTERIOR) {
            ret = "posterior";
        } else if (val == ENTTympano.ENT_TYMPANOSCLEROSIS_25) {
            ret = "25 percent";
        } else if (val == ENTTympano.ENT_TYMPANOSCLEROSIS_50) {
            ret = "50 percent";
        } else if (val == ENTTympano.ENT_TYMPANOSCLEROSIS_75) {
            ret = "75 percent";
        } else if (val == ENTTympano.ENT_TYMPANOSCLEROSIS_TOTAL) {
            ret = "total";
        } 

        return ret;
    }

    public ENTPerf perfToEnum(String val)
    {
        ENTPerf ret = ENTPerf.ENT_PERF_NONE;

        if (val.equals("anterior")) {
            ret = ENTPerf.ENT_PERF_ANTERIOR;
        } else if (val.equals("posterior")) {
            ret = ENTPerf.ENT_PERF_POSTERIOR;
        } else if (val.equals("marginal")) {
            ret = ENTPerf.ENT_PERF_MARGINAL;
        } else if (val.equals("25 percent")) {
            ret = ENTPerf.ENT_PERF_25;
        } else if (val.equals("50 percent")) {
            ret = ENTPerf.ENT_PERF_50;
        } else if (val.equals("75 percent")) {
            ret = ENTPerf.ENT_PERF_75;
        } else if (val.equals("total")) {
            ret = ENTPerf.ENT_PERF_TOTAL;
        } 
        return ret;
    }

    public String perfToString(ENTPerf val)
    {
        String ret = "none";

        if (val == ENTPerf.ENT_PERF_ANTERIOR) {
            ret = "anterior";
        } else if (val == ENTPerf.ENT_PERF_POSTERIOR) {
            ret = "posterior";
        } else if (val == ENTPerf.ENT_PERF_MARGINAL) {
            ret = "marginal";
        } else if (val == ENTPerf.ENT_PERF_25) {
            ret = "25 percent";
        } else if (val == ENTPerf.ENT_PERF_50) {
            ret = "50 percent";
        } else if (val == ENTPerf.ENT_PERF_75) {
            ret = "75 percent";
        } else if (val == ENTPerf.ENT_PERF_TOTAL) {
            ret = "total";
        } 

        return ret;
    }

    public ENTVoiceTest voiceTestToEnum(String val)
    {
        ENTVoiceTest ret = ENTVoiceTest.ENT_VOICE_TEST_NONE;

        if (val.equals("normal")) {
            ret = ENTVoiceTest.ENT_VOICE_TEST_NORMAL;
        } else if (val.equals("abnormal")) {
            ret = ENTVoiceTest.ENT_VOICE_TEST_ABNORMAL;
        } 
        return ret;
    }

    public String voiceTestToString(ENTVoiceTest val)
    {
        String ret = "none";

        if (val == ENTVoiceTest.ENT_VOICE_TEST_NORMAL) {
            ret = "normal";
        } else if (val == ENTVoiceTest.ENT_VOICE_TEST_ABNORMAL) {
            ret = "abnormal";
        } 
        return ret;
    }

    public ENTForkTest forkTestToEnum(String val)
    {
        ENTForkTest ret = ENTForkTest.ENT_FORK_TEST_NONE;

        if (val.equals("a greater b")) {
            ret = ENTForkTest.ENT_FORK_TEST_A_GREATER_B;
        } else if (val.equals("b greater a")) {
            ret = ENTForkTest.ENT_FORK_TEST_B_GREATER_A;
        } else if (val.equals("equal")) {
            ret = ENTForkTest.ENT_FORK_TEST_EQUAL;
        } 
        return ret;
    }

    public String forkTestToString(ENTForkTest val)
    {
        String ret = "none";

        if (val == ENTForkTest.ENT_FORK_TEST_A_GREATER_B) {
            ret = "a greater b";
        } else if (val == ENTForkTest.ENT_FORK_TEST_B_GREATER_A) {
            ret = "b greater a";
        } else if (val == ENTForkTest.ENT_FORK_TEST_EQUAL) {
            ret = "equal";
        } 
        return ret;
    }

    public ENTBC bcToEnum(String val)
    {
        ENTBC ret = ENTBC.ENT_BC_NONE;

        if (val.equals("ad lat ad")) {
            ret = ENTBC.ENT_BC_AD_LAT_TO_AD;
        } else if (val.equals("ad lat as")) {
            ret = ENTBC.ENT_BC_AD_LAT_TO_AS;
        } else if (val.equals("as lat ad")) {
            ret = ENTBC.ENT_BC_AS_LAT_TO_AD;
        } else if (val.equals("as lat as")) {
            ret = ENTBC.ENT_BC_AS_LAT_TO_AS;
        } 
        return ret;
    }

    public String bcToString(ENTBC val)
    {
        String ret = "none";

        if (val == ENTBC.ENT_BC_AD_LAT_TO_AD) {
            ret = "ad lat ad";
        } else if (val == ENTBC.ENT_BC_AD_LAT_TO_AS) {
            ret = "ad lat as";
        } else if (val == ENTBC.ENT_BC_AS_LAT_TO_AD) {
            ret = "as lat ad";
        } else if (val == ENTBC.ENT_BC_AS_LAT_TO_AS) {
            ret = "as lat as";
        } 
        return ret;
    }

    public ENTFork forkToEnum(String val)
    {
        ENTFork ret = ENTFork.ENT_FORK_NONE;

        if (val.equals("256")) {
            ret = ENTFork.ENT_FORK_256;
        } else if (val.equals("512")) {
            ret = ENTFork.ENT_FORK_512;
        } 
        return ret;
    }

    public String forkToString(ENTFork val)
    {
        String ret = "none";

        if (val == ENTFork.ENT_FORK_256) {
            ret = "256";
        } else if (val == ENTFork.ENT_FORK_512) {
            ret = "512";
        } 
        return ret;
    }

    public int fromJSONObject(JSONObject o)
    {
        int ret = 0;

        try {

            m_normal = earSideToEnum(o.getString("normal"));
            m_microtia = earSideToEnum(o.getString("microtia"));
            m_wax = earSideToEnum(o.getString("wax"));
            m_drainage = earSideToEnum(o.getString("drainage"));
            m_externalOtitis = earSideToEnum(o.getString("externalOtitis"));
            m_fb = earSideToEnum(o.getString("fb"));
            m_tubeRight = tubeToEnum(o.getString("tubeRight"));
            m_tubeLeft = tubeToEnum(o.getString("tubeLeft"));

            m_tympanoRight = tympanoToEnum(o.getString("tympanoRight"));
            m_tympanoLeft = tympanoToEnum(o.getString("tympanoLeft"));

            m_tmGranulations = earSideToEnum(o.getString("tmGranulations"));
            m_tmRetraction = earSideToEnum(o.getString("tmRetraction"));
            m_tmAtelectasis = earSideToEnum(o.getString("tmAtelectasis"));

            m_perfRight = perfToEnum(o.getString("perfRight"));
            m_perfLeft = perfToEnum(o.getString("perfLeft"));

            m_voiceTest = voiceTestToEnum(o.getString("voiceTest"));

            m_forkAD = forkTestToEnum(o.getString("forkAD"));
            m_forkAS = forkTestToEnum(o.getString("forkAS"));

            m_bc = bcToEnum(o.getString("bc"));

            m_fork = forkToEnum(o.getString("fork"));

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

            data.put("normal", earSideToString(m_normal));
            data.put("microtia", earSideToString(m_microtia));
            data.put("wax", earSideToString(m_wax));
            data.put("drainage", earSideToString(m_drainage));
            data.put("externalOtitis", earSideToString(m_externalOtitis));
            data.put("fb", earSideToString(m_fb));
            data.put("tubeRight", tubeToString(m_tubeRight));
            data.put("tubeLeft", tubeToString(m_tubeLeft));
            
            data.put("tympanoRight", tympanoToString(m_tympanoRight));
            data.put("tympanoLeft", tympanoToString(m_tympanoLeft));
            
            data.put("tmGranulations", earSideToString(m_tmGranulations));
            data.put("tmRetraction", earSideToString(m_tmRetraction));
            data.put("tmAtelectasis", earSideToString(m_tmAtelectasis));
            
            data.put("perfRight", perfToString(m_perfRight));
            data.put("perfLeft", perfToString(m_perfLeft));
            
            data.put("voiceTest", voiceTestToString(m_voiceTest));
            
            data.put("forkAD", forkTestToString(m_forkAD));
            data.put("forkAS", forkTestToString(m_forkAS));
            
            data.put("bc", bcToString(m_bc));
            
            data.put("fork", forkToString(m_fork));
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


    public ENTExam() {
    }

    public ENTExam(ENTExam rhs) {
        this.m_normal = rhs.m_normal;
        this.m_microtia = rhs.m_microtia;
        this.m_wax = rhs.m_wax;
        this.m_drainage = rhs.m_drainage;
        this.m_externalOtitis = rhs.m_externalOtitis;
        this.m_fb = rhs.m_fb;

        this.m_tubeRight = rhs.m_tubeRight;
        this.m_tubeLeft = rhs.m_tubeLeft;

        this.m_tympanoRight = rhs.m_tympanoRight;
        this.m_tympanoLeft = rhs.m_tympanoLeft;

        this.m_tmGranulations = rhs.m_tmGranulations;
        this.m_tmRetraction = rhs.m_tmRetraction;
        this.m_tmAtelectasis = rhs.m_tmAtelectasis;

        this.m_perfRight = rhs.m_perfRight;
        this.m_perfLeft = rhs.m_perfLeft;

        this.m_voiceTest = rhs.m_voiceTest;

        this.m_forkAD = rhs.m_forkAD;
        this.m_forkAS = rhs.m_forkAS;

        this.m_bc = rhs.m_bc;

        this.m_fork = rhs.m_fork;

        this.m_patient = rhs.m_patient;
        this.m_clinic = rhs.m_clinic;
        this.m_id = rhs.m_id;
        this.m_comment = rhs.m_comment;
        this.m_username = rhs.m_username;
    }
}
