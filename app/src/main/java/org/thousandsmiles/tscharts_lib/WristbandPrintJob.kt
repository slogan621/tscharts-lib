/*
 * (C) Copyright Syd Logan 2022
 * (C) Copyright Thousand Smiles Foundation 2022
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

class WristbandPrintJob(val printer : WristbandPrinter, var id : Int, var patient_data : PatientData) {
    var m_id : Int = id
    val m_printer : WristbandPrinter = printer
    val m_patientData : PatientData = patient_data
    var m_thread : Thread? = null

    enum class JobStatus {
        NotStarted,
        Running,
        Stopped,
        Finished
    }

    fun startJob(numberOfCopies: Int) : Boolean {
        var ret : Boolean = false

        m_thread = Thread {
            m_printer.print(id, m_patientData, numberOfCopies)
        }
        m_thread!!.start()

        return ret
    }
}