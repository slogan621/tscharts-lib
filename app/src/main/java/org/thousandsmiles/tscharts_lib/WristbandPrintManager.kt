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

import android.content.Context
import android.preference.PreferenceManager

class WristbandPrintManager(context: Context) : WristbandStatusListener {

    var m_nextJobId : Int = 0
    lateinit var m_patientData : PatientData
    var m_context : Context? = context
    lateinit var m_printers : ArrayList<WristbandPrinter>

    fun getPrinterList() :  ArrayList<WristbandPrinter> {
        enumeratePrinters();
        return m_printers;
    }

    fun setPatientData(data : PatientData) {
        m_patientData = data;
    }

    fun getPatientData() : PatientData {
        return m_patientData;
    }

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
            if (createJob(x, data) != -1) {
                break;
            }
        }
        return id
    }

    fun createJob(printer: WristbandPrinter, data : PatientData) : Int {
        var id : Int = -1

        var printerStatus = printer.m_printerStatus
        var connectedStatus = printer.m_connectedStatus
        if (printerStatus == WristbandPrinter.PrinterStatus.Idle) {
            printer.registerListener(this)
            id = getNextJob()
            m_jobs[id] = WristbandPrintJob(printer, id, data)
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
        fun getInstance(context: Context): WristbandPrintManager {
            if (obj == null) {
                obj = WristbandPrintManager(context)
            }
            var wbp = obj as WristbandPrintManager
            wbp.enumeratePrinters()
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

    override fun OnConnectionStatusChange(job: Int, status: WristbandPrinter.ConnectedStatus) {
        for (item in m_listeners) {
            item.OnConnectionStatusChange(job, status)
        }
    }

    var m_listeners = ArrayList<WristbandStatusListener>()

    fun registerListener(job: Int, listener : WristbandStatusListener) {
        m_listeners.add(listener)
    }

    fun removeListener(job: Int, listener : WristbandStatusListener) {
        m_listeners.remove(listener)
    }

    private fun enumeratePrinters() {
        m_printers = ArrayList<WristbandPrinter>()

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(m_context)

        val ip_printer_1 = sharedPref.getString("WBPrinterIpAddress1", "")
        val port_printer_1 = sharedPref.getString("WBPrinterPort1", "")
        val ip_printer_2 = sharedPref.getString("WBPrinterIpAddress2", "")
        val port_printer_2 = sharedPref.getString("WBPrinterPort2", "")
        val ip_printer_3 = sharedPref.getString("WBPrinterIpAddress3", "")
        val port_printer_3 = sharedPref.getString("WBPrinterPort3", "")

        if (ip_printer_1 != "" && port_printer_1 != "") {
            val wb = ip_printer_1?.let { ZebraWristbandPrinter(it, port_printer_1?.toInt()) }
            if (wb != null) {
                m_printers.add(wb)
            };
        }

        if (ip_printer_2 != "" && port_printer_2 != "") {
            val wb = ip_printer_2?.let { ZebraWristbandPrinter(it, port_printer_2?.toInt()) }
            if (wb != null) {
                m_printers.add(wb)
            };
        }

        if (ip_printer_3 != "" && port_printer_3 != "") {
            val wb = ip_printer_3?.let { ZebraWristbandPrinter(it, port_printer_3?.toInt()) }
            if (wb != null) {
                m_printers.add(wb)
            };
        }
    }
}