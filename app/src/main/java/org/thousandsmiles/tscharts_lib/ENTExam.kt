/*
 * (C) Copyright Syd Logan 2019-2021
 * (C) Copyright Thousand Smiles Foundation 2019-2021
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

package org.thousandsmiles.tscharts_lib

import org.json.JSONException
import org.json.JSONObject
import org.thousandsmiles.tscharts_lib.ENTHistory.EarSide
import java.io.Serializable

class ENTExam : Serializable {

    enum class TristateBoolean {
        EAR_TRI_STATE_BOOLEAN_NA, EAR_TRI_STATE_BOOLEAN_YES, EAR_TRI_STATE_BOOLEAN_NO
    }

    enum class ENTTube {
        ENT_TUBE_IN_PLACE, ENT_TUBE_EXTRUDING, ENT_TUBE_IN_CANAL, ENT_TUBE_NONE
    }

    enum class ENTTympano {
        ENT_TYMPANOSCLEROSIS_ANTERIOR, ENT_TYMPANOSCLEROSIS_POSTERIOR, ENT_TYMPANOSCLEROSIS_25, ENT_TYMPANOSCLEROSIS_50, ENT_TYMPANOSCLEROSIS_75, ENT_TYMPANOSCLEROSIS_TOTAL, ENT_TYMPANOSCLEROSIS_NONE
    }

    enum class ENTPerf {
        ENT_PERF_ANTERIOR, ENT_PERF_POSTERIOR, ENT_PERF_MARGINAL, ENT_PERF_25, ENT_PERF_50, ENT_PERF_75, ENT_PERF_TOTAL, ENT_PERF_NONE
    }

    enum class ENTVoiceTest {
        ENT_VOICE_TEST_NORMAL, ENT_VOICE_TEST_ABNORMAL, ENT_VOICE_TEST_NONE
    }

    enum class ENTForkTest {
        ENT_FORK_TEST_A_GREATER_B, ENT_FORK_TEST_B_GREATER_A, ENT_FORK_TEST_EQUAL, ENT_FORK_TEST_NONE
    }

    enum class ENTBC {
        ENT_BC_AD_LAT_TO_AD, ENT_BC_AD_LAT_TO_AS, ENT_BC_AS_LAT_TO_AD, ENT_BC_AS_LAT_TO_AS, ENT_BC_NONE
    }

    enum class ENTFork {
        ENT_FORK_256, ENT_FORK_512, ENT_FORK_NONE
    }

    var cleft_lip: Boolean
    var cleft_palate: Boolean
    var repaired_lip: TristateBoolean
    var repaired_palate: TristateBoolean
    var normal: EarSide
    var microtia: EarSide
    var wax: EarSide
    var effusion: EarSide
    var middleEarInfection: EarSide
    var drainage: EarSide
    var externalOtitis: EarSide
    var fb: EarSide
    var tubeRight: ENTTube
    var tubeLeft: ENTTube
    var tympanoRight: ENTTympano
    var tympanoLeft: ENTTympano
    var tmGranulations: EarSide
    var tmRetraction: EarSide
    var tmAtelectasis: EarSide
    var perfRight: ENTPerf
    var perfLeft: ENTPerf
    var voiceTest: ENTVoiceTest
    var forkAD: ENTForkTest
    var forkAS: ENTForkTest
    var bc: ENTBC
    var fork: ENTFork
    var patient: Int
    var clinic: Int
    var id: Int
    var comment: String
    var username: String

    fun stringToBool(`val`: String): Boolean {
        var ret = false
        if (`val` == "true") {
            ret = true
        }
        return ret
    }

    fun boolToString(`val`: Boolean): String? {
        var ret = "false"
        if (`val` == true) {
            ret = "true"
        }
        return ret
    }

    fun tristateBooleanToEnum(side: String): TristateBoolean {
        var ret = TristateBoolean.EAR_TRI_STATE_BOOLEAN_NA
        if (side == "yes") {
            ret = TristateBoolean.EAR_TRI_STATE_BOOLEAN_YES
        } else if (side == "no") {
            ret = TristateBoolean.EAR_TRI_STATE_BOOLEAN_NO
        }
        return ret
    }

    fun tristateBooleanToString(value: TristateBoolean): String {
        var ret = "na"
        if (value == TristateBoolean.EAR_TRI_STATE_BOOLEAN_YES) {
            ret = "yes"
        } else if (value == TristateBoolean.EAR_TRI_STATE_BOOLEAN_NO) {
            ret = "no"
        }
        return ret
    }

    fun earSideToEnum(side: String): EarSide {
        var ret = EarSide.EAR_SIDE_BOTH
        if (side == "left") {
            ret = EarSide.EAR_SIDE_LEFT
        } else if (side == "right") {
            ret = EarSide.EAR_SIDE_RIGHT
        } else if (side == "none") {
            ret = EarSide.EAR_SIDE_NONE
        }
        return ret
    }

    fun earSideToString(side: EarSide): String {
        var ret = "both"
        if (side == EarSide.EAR_SIDE_LEFT) {
            ret = "left"
        } else if (side == EarSide.EAR_SIDE_RIGHT) {
            ret = "right"
        } else if (side == EarSide.EAR_SIDE_NONE) {
            ret = "none"
        }
        return ret
    }

    fun tubeToEnum(`val`: String): ENTTube {
        var ret = ENTTube.ENT_TUBE_NONE
        if (`val` == "in place") {
            ret = ENTTube.ENT_TUBE_IN_PLACE
        } else if (`val` == "extruding") {
            ret = ENTTube.ENT_TUBE_EXTRUDING
        } else if (`val` == "in canal") {
            ret = ENTTube.ENT_TUBE_IN_CANAL
        }
        return ret
    }

    fun tubeToString(`val`: ENTTube): String {
        var ret = "none"
        if (`val` == ENTTube.ENT_TUBE_IN_PLACE) {
            ret = "in place"
        } else if (`val` == ENTTube.ENT_TUBE_EXTRUDING) {
            ret = "extruding"
        } else if (`val` == ENTTube.ENT_TUBE_IN_CANAL) {
            ret = "in canal"
        }
        return ret
    }

    fun tympanoToEnum(`val`: String): ENTTympano {
        var ret = ENTTympano.ENT_TYMPANOSCLEROSIS_NONE
        if (`val` == "anterior") {
            ret = ENTTympano.ENT_TYMPANOSCLEROSIS_ANTERIOR
        } else if (`val` == "posterior") {
            ret = ENTTympano.ENT_TYMPANOSCLEROSIS_POSTERIOR
        } else if (`val` == "25 percent") {
            ret = ENTTympano.ENT_TYMPANOSCLEROSIS_25
        } else if (`val` == "50 percent") {
            ret = ENTTympano.ENT_TYMPANOSCLEROSIS_50
        } else if (`val` == "75 percent") {
            ret = ENTTympano.ENT_TYMPANOSCLEROSIS_75
        } else if (`val` == "total") {
            ret = ENTTympano.ENT_TYMPANOSCLEROSIS_TOTAL
        }
        return ret
    }

    fun tympanoToString(`val`: ENTTympano): String {
        var ret = "none"
        if (`val` == ENTTympano.ENT_TYMPANOSCLEROSIS_ANTERIOR) {
            ret = "anterior"
        } else if (`val` == ENTTympano.ENT_TYMPANOSCLEROSIS_POSTERIOR) {
            ret = "posterior"
        } else if (`val` == ENTTympano.ENT_TYMPANOSCLEROSIS_25) {
            ret = "25 percent"
        } else if (`val` == ENTTympano.ENT_TYMPANOSCLEROSIS_50) {
            ret = "50 percent"
        } else if (`val` == ENTTympano.ENT_TYMPANOSCLEROSIS_75) {
            ret = "75 percent"
        } else if (`val` == ENTTympano.ENT_TYMPANOSCLEROSIS_TOTAL) {
            ret = "total"
        }
        return ret
    }

    fun perfToEnum(`val`: String): ENTPerf {
        var ret = ENTPerf.ENT_PERF_NONE
        if (`val` == "anterior") {
            ret = ENTPerf.ENT_PERF_ANTERIOR
        } else if (`val` == "posterior") {
            ret = ENTPerf.ENT_PERF_POSTERIOR
        } else if (`val` == "marginal") {
            ret = ENTPerf.ENT_PERF_MARGINAL
        } else if (`val` == "25 percent") {
            ret = ENTPerf.ENT_PERF_25
        } else if (`val` == "50 percent") {
            ret = ENTPerf.ENT_PERF_50
        } else if (`val` == "75 percent") {
            ret = ENTPerf.ENT_PERF_75
        } else if (`val` == "total") {
            ret = ENTPerf.ENT_PERF_TOTAL
        }
        return ret
    }

    fun perfToString(`val`: ENTPerf): String {
        var ret = "none"
        if (`val` == ENTPerf.ENT_PERF_ANTERIOR) {
            ret = "anterior"
        } else if (`val` == ENTPerf.ENT_PERF_POSTERIOR) {
            ret = "posterior"
        } else if (`val` == ENTPerf.ENT_PERF_MARGINAL) {
            ret = "marginal"
        } else if (`val` == ENTPerf.ENT_PERF_25) {
            ret = "25 percent"
        } else if (`val` == ENTPerf.ENT_PERF_50) {
            ret = "50 percent"
        } else if (`val` == ENTPerf.ENT_PERF_75) {
            ret = "75 percent"
        } else if (`val` == ENTPerf.ENT_PERF_TOTAL) {
            ret = "total"
        }
        return ret
    }

    fun voiceTestToEnum(`val`: String): ENTVoiceTest {
        var ret = ENTVoiceTest.ENT_VOICE_TEST_NONE
        if (`val` == "normal") {
            ret = ENTVoiceTest.ENT_VOICE_TEST_NORMAL
        } else if (`val` == "abnormal") {
            ret = ENTVoiceTest.ENT_VOICE_TEST_ABNORMAL
        }
        return ret
    }

    fun voiceTestToString(`val`: ENTVoiceTest): String {
        var ret = "none"
        if (`val` == ENTVoiceTest.ENT_VOICE_TEST_NORMAL) {
            ret = "normal"
        } else if (`val` == ENTVoiceTest.ENT_VOICE_TEST_ABNORMAL) {
            ret = "abnormal"
        }
        return ret
    }

    fun forkTestToEnum(`val`: String): ENTForkTest {
        var ret = ENTForkTest.ENT_FORK_TEST_NONE
        if (`val` == "a greater b") {
            ret = ENTForkTest.ENT_FORK_TEST_A_GREATER_B
        } else if (`val` == "b greater a") {
            ret = ENTForkTest.ENT_FORK_TEST_B_GREATER_A
        } else if (`val` == "a equal b") {
            ret = ENTForkTest.ENT_FORK_TEST_EQUAL
        }
        return ret
    }

    fun forkTestToString(`val`: ENTForkTest): String {
        var ret = "none"
        if (`val` == ENTForkTest.ENT_FORK_TEST_A_GREATER_B) {
            ret = "a greater b"
        } else if (`val` == ENTForkTest.ENT_FORK_TEST_B_GREATER_A) {
            ret = "b greater a"
        } else if (`val` == ENTForkTest.ENT_FORK_TEST_EQUAL) {
            ret = "a equal b"
        }
        return ret
    }

    fun bcToEnum(`val`: String): ENTBC {
        var ret = ENTBC.ENT_BC_NONE
        if (`val` == "ad lat ad") {
            ret = ENTBC.ENT_BC_AD_LAT_TO_AD
        } else if (`val` == "ad lat as") {
            ret = ENTBC.ENT_BC_AD_LAT_TO_AS
        } else if (`val` == "as lat ad") {
            ret = ENTBC.ENT_BC_AS_LAT_TO_AD
        } else if (`val` == "as lat as") {
            ret = ENTBC.ENT_BC_AS_LAT_TO_AS
        }
        return ret
    }

    fun bcToString(`val`: ENTBC): String {
        var ret = "none"
        if (`val` == ENTBC.ENT_BC_AD_LAT_TO_AD) {
            ret = "ad lat ad"
        } else if (`val` == ENTBC.ENT_BC_AD_LAT_TO_AS) {
            ret = "ad lat as"
        } else if (`val` == ENTBC.ENT_BC_AS_LAT_TO_AD) {
            ret = "as lat ad"
        } else if (`val` == ENTBC.ENT_BC_AS_LAT_TO_AS) {
            ret = "as lat as"
        }
        return ret
    }

    fun forkToEnum(`val`: String): ENTFork {
        var ret = ENTFork.ENT_FORK_NONE
        if (`val` == "256") {
            ret = ENTFork.ENT_FORK_256
        } else if (`val` == "512") {
            ret = ENTFork.ENT_FORK_512
        }
        return ret
    }

    fun forkToString(`val`: ENTFork): String {
        var ret = "none"
        if (`val` == ENTFork.ENT_FORK_256) {
            ret = "256"
        } else if (`val` == ENTFork.ENT_FORK_512) {
            ret = "512"
        }
        return ret
    }

    fun fromJSONObject(o: JSONObject): Int {
        var ret = 0
        try {
            normal = earSideToEnum(o.getString("normal"))
            microtia = earSideToEnum(o.getString("microtia"))
            wax = earSideToEnum(o.getString("wax"))
            effusion = earSideToEnum(o.getString("effusion"))
            middleEarInfection = earSideToEnum(o.getString("middle_ear_infection"))
            drainage = earSideToEnum(o.getString("drainage"))
            externalOtitis = earSideToEnum(o.getString("externalOtitis"))
            fb = earSideToEnum(o.getString("fb"))
            tubeRight = tubeToEnum(o.getString("tubeRight"))
            tubeLeft = tubeToEnum(o.getString("tubeLeft"))
            tympanoRight = tympanoToEnum(o.getString("tympanoRight"))
            tympanoLeft = tympanoToEnum(o.getString("tympanoLeft"))
            tmGranulations = earSideToEnum(o.getString("tmGranulations"))
            tmRetraction = earSideToEnum(o.getString("tmRetraction"))
            tmAtelectasis = earSideToEnum(o.getString("tmAtelectasis"))
            perfRight = perfToEnum(o.getString("perfRight"))
            perfLeft = perfToEnum(o.getString("perfLeft"))
            voiceTest = voiceTestToEnum(o.getString("voiceTest"))
            forkAD = forkTestToEnum(o.getString("forkAD"))
            forkAS = forkTestToEnum(o.getString("forkAS"))
            bc = bcToEnum(o.getString("bc"))
            fork = forkToEnum(o.getString("fork"))
            id = o.getInt("id")
            patient = o.getInt("patient")
            clinic = o.getInt("clinic")
            comment = o.getString("comment")
            username = o.getString("username")
            cleft_lip = stringToBool(o.getString("cleft_lip"))
            cleft_palate = stringToBool(o.getString("cleft_palate"))
            repaired_lip = tristateBooleanToEnum(o.getString("repaired_lip"))
            repaired_palate = tristateBooleanToEnum(o.getString("repaired_palate"))
        } catch (e: JSONException) {
            ret = -1
        }
        return ret
    }

    fun toJSONObject(includeId: Boolean): JSONObject? {
        var data: JSONObject? = JSONObject()
        try {
            if (includeId == true) {
                data!!.put("id", id)
            }
            data!!.put("patient", patient)
            data.put("clinic", clinic)
            data.put("comment", comment)
            data.put("username", username)
            data.put("cleft_lip", boolToString(cleft_lip))
            data.put("cleft_palate", boolToString(cleft_palate))
            data.put("repaired_lip", tristateBooleanToString(repaired_lip))
            data.put("repaired_palate", tristateBooleanToString(repaired_palate))
            data.put("normal", earSideToString(normal))
            data.put("microtia", earSideToString(microtia))
            data.put("wax", earSideToString(wax))
            data.put("effusion", earSideToString(effusion))
            data.put("middle_ear_infection", earSideToString(middleEarInfection))
            data.put("drainage", earSideToString(drainage))
            data.put("externalOtitis", earSideToString(externalOtitis))
            data.put("fb", earSideToString(fb))
            data.put("tubeRight", tubeToString(tubeRight))
            data.put("tubeLeft", tubeToString(tubeLeft))
            data.put("tympanoRight", tympanoToString(tympanoRight))
            data.put("tympanoLeft", tympanoToString(tympanoLeft))
            data.put("tmGranulations", earSideToString(tmGranulations))
            data.put("tmRetraction", earSideToString(tmRetraction))
            data.put("tmAtelectasis", earSideToString(tmAtelectasis))
            data.put("perfRight", perfToString(perfRight))
            data.put("perfLeft", perfToString(perfLeft))
            data.put("voiceTest", voiceTestToString(voiceTest))
            data.put("forkAD", forkTestToString(forkAD))
            data.put("forkAS", forkTestToString(forkAS))
            data.put("bc", bcToString(bc))
            data.put("fork", forkToString(fork))
        } catch (e: Exception) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
            data = null
        }
        return data
    }

    constructor() {
        cleft_lip = false
        cleft_palate = false
        repaired_lip = TristateBoolean.EAR_TRI_STATE_BOOLEAN_NA
        repaired_palate = TristateBoolean.EAR_TRI_STATE_BOOLEAN_NA
        normal = EarSide.EAR_SIDE_NONE
        microtia = EarSide.EAR_SIDE_NONE
        wax = EarSide.EAR_SIDE_NONE
        effusion = EarSide.EAR_SIDE_NONE
        middleEarInfection = EarSide.EAR_SIDE_NONE
        drainage = EarSide.EAR_SIDE_NONE
        externalOtitis = EarSide.EAR_SIDE_NONE
        fb = EarSide.EAR_SIDE_NONE
        tubeRight = ENTTube.ENT_TUBE_NONE
        tubeLeft = ENTTube.ENT_TUBE_NONE
        tympanoRight = ENTTympano.ENT_TYMPANOSCLEROSIS_NONE
        tympanoLeft = ENTTympano.ENT_TYMPANOSCLEROSIS_NONE
        tmGranulations = EarSide.EAR_SIDE_NONE
        tmRetraction = EarSide.EAR_SIDE_NONE
        tmAtelectasis = EarSide.EAR_SIDE_NONE
        perfRight = ENTPerf.ENT_PERF_NONE
        perfLeft = ENTPerf.ENT_PERF_NONE
        voiceTest = ENTVoiceTest.ENT_VOICE_TEST_NONE
        forkAD = ENTForkTest.ENT_FORK_TEST_NONE
        forkAS = ENTForkTest.ENT_FORK_TEST_NONE
        bc = ENTBC.ENT_BC_NONE
        fork = ENTFork.ENT_FORK_NONE
        patient = 0
        clinic = 0
        id = 0
        comment = ""
        username = ""
    }

    constructor(rhs: ENTExam) {
        cleft_lip = rhs.cleft_lip
        cleft_palate = rhs.cleft_palate
        repaired_lip = rhs.repaired_lip
        repaired_palate = rhs.repaired_palate
        normal = rhs.normal
        microtia = rhs.microtia
        wax = rhs.wax
        effusion = rhs.effusion
        middleEarInfection = rhs.middleEarInfection
        drainage = rhs.drainage
        externalOtitis = rhs.externalOtitis
        fb = rhs.fb
        tubeRight = rhs.tubeRight
        tubeLeft = rhs.tubeLeft
        tympanoRight = rhs.tympanoRight
        tympanoLeft = rhs.tympanoLeft
        tmGranulations = rhs.tmGranulations
        tmRetraction = rhs.tmRetraction
        tmAtelectasis = rhs.tmAtelectasis
        perfRight = rhs.perfRight
        perfLeft = rhs.perfLeft
        voiceTest = rhs.voiceTest
        forkAD = rhs.forkAD
        forkAS = rhs.forkAS
        bc = rhs.bc
        fork = rhs.fork
        patient = rhs.patient
        clinic = rhs.clinic
        id = rhs.id
        comment = rhs.comment
        username = rhs.username
    }
}