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

class WristbandPrintManager : WristbandStatusListener {

    var m_nextJobId : Int = 0
    lateinit var m_printers : ArrayList<WristbandPrinter>

    private fun getNextJob() : Int {
        var ret = m_nextJobId;
        m_nextJobId += 1
        return ret
    }

    var m_jobs = mutableMapOf<Int, WristbandPrintJob>()

    // returns -1 on fail, otherwise job id

    fun createJob(data : PatientData) : Int {
        var id : Int = -1
        // find a suitable printer
        for (x in m_printers) {
            var printerStatus = x.m_printerStatus
            var connectedStatus = x.m_connectedStatus
            if (printerStatus == WristbandPrinter.PrinterStatus.Idle && connectedStatus == WristbandPrinter.ConnectedStatus.Disconnected) {
                x.registerListener(this)
                id = getNextJob()
                m_jobs[id] = WristbandPrintJob(x, id, data)
            }
        }
        return id
    }

    fun startJob(id : Int) : Boolean {
        var ret : Boolean = false
        // find job, start
        if (m_jobs.contains(id) == true) {
            ret = m_jobs[id]?.startJob() ?: false;
        }
        return ret
    }

    companion object {
        private var obj: WristbandPrintManager? = null
        fun getInstance(): WristbandPrintManager {
            if (obj == null) {
                obj = WristbandPrintManager()
            }
            var wbp = obj as WristbandPrintManager
            wbp.EnumeratePrinters()
            return wbp
        }
    }

    override fun OnSuccess(job: Int, status: WristbandPrinter.PrinterStatus) {
        for (item in m_listeners) {
            item.OnSuccess(job, status)
        }
    }

    override fun OnError(
        job: Int,
        status: WristbandPrinter.PrinterStatus,
        msg: String
    ) {
        for (item in m_listeners) {
            item.OnError(job, status, msg)
        }
    }

    override fun OnStatusChange(job: Int, status: WristbandPrinter.PrinterStatus) {
        for (item in m_listeners) {
            item.OnStatusChange(job, status)
        }
    }

    var m_listeners = ArrayList<WristbandStatusListener>()

    fun registerListener(job: Int, listener : WristbandStatusListener) {
        m_listeners.add(listener)
    }

    fun removeListener(job: Int, listener : WristbandStatusListener) {
        m_listeners.remove(listener)
    }

    private fun EnumeratePrinters() {
        // read prefs
        // create list of printers based on configured prefs, for x in addr/port pairs
    }
}