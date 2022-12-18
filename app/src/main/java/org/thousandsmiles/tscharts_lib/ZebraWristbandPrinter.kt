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

import com.zebra.sdk.comm.Connection
import com.zebra.sdk.comm.ConnectionException
import com.zebra.sdk.comm.TcpConnection
import com.zebra.sdk.printer.PrinterStatus
import com.zebra.sdk.printer.SGD
import com.zebra.sdk.printer.ZebraPrinter
import com.zebra.sdk.printer.ZebraPrinterFactory
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException

class ZebraWristbandPrinter(ipAddr: String, port: Int) : WristbandPrinter(ipAddr, port) {

    var m_port : Int = port
    var m_ipAddr : String = ipAddr
    private var connection: Connection? = null

    override fun print(job: Int, patient: PatientData) : Boolean {

        // code to print to zebra goes here.
        // On success, call notifyOnSuccess with latest status reported by printer
        // On detecting status changes, call notifyOnStatusChange with status
        // On error,  call notifyOnSuccess with latest status reported by printer and custom msg or ""

        var printer = connect(job);
        if (printer == null) {
            notifyOnError(job, m_printerStatus, "Unable to connect to printer")
            return false
        }

        // ....

        disconnect(job);
        notifyOnSuccess(job, m_printerStatus)
        return true
    }

    fun connect(job :Int): ZebraPrinter? {
        //setStatus("Connecting...", Color.YELLOW)
        connection = null
        try {
            val port: Int = m_port
            connection = TcpConnection(m_ipAddr, port)
            //SettingsHelper.saveIp(this, getTcpAddress())
            //SettingsHelper.savePort(this, getTcpPortNumber())
        } catch (e: NumberFormatException) {
            //setStatus("Port Number Is Invalid", Color.RED)
            return null
        }

        try {
            (connection as TcpConnection).open()
            //setStatus("Connected", Color.GREEN)
        } catch (e: ConnectionException) {
            //setStatus("Comm Error! Disconnecting", Color.RED)
            //DemoSleeper.sleep(1000)
            disconnect(job)
        }
        var printer: ZebraPrinter? = null
        if ((connection as TcpConnection).isConnected()) {
            try {
                printer = ZebraPrinterFactory.getInstance(connection)
                //setStatus("Determining Printer Language", Color.YELLOW)
                val pl = SGD.GET("device.languages", connection)
                //setStatus("Printer Language $pl", Color.BLUE)
            } catch (e: ConnectionException) {
                //setStatus("Unknown Printer Language", Color.RED)
                printer = null
                //DemoSleeper.sleep(1000)
                disconnect(job)
            } catch (e: ZebraPrinterLanguageUnknownException) {
                //setStatus("Unknown Printer Language", Color.RED)
                printer = null
                //DemoSleeper.sleep(1000)
                disconnect(job)
            }
        }
        changeConnectionStatus(job, ConnectedStatus.Connected)
        return printer
    }

    fun disconnect(job : Int) {
        try {
            //setStatus("Disconnecting", Color.RED)
            if (connection != null) {
                connection!!.close()
            }
            changeConnectionStatus(job, ConnectedStatus.Disconnected)
            //setStatus("Not Connected", Color.RED)
        } catch (e: ConnectionException) {
            //setStatus("COMM Error! Disconnected", Color.RED)
        } finally {
            //enableTestButton(true)
        }
    }

    fun changeConnectionStatus(job: Int, status: ConnectedStatus) {
        m_connectedStatus = status;
        notifyOnConnectionStatusChange(job, status)
    }

    fun notifyOnSuccess(job : Int, status : PrinterStatus) {
        for (x in m_listeners) {
            x.OnSuccess(job, status)
        }
    }

    fun notifyOnError(job : Int, status : PrinterStatus, msg : String) {
        for (x in m_listeners) {
            x.OnError(job, status, msg)
        }
    }

    // call this anytime there is a printer status change

     fun notifyOnStatusChange(job : Int, status : PrinterStatus) {
         for (x in m_listeners) {
             x.OnStatusChange(job, status)
         }
     }

    fun notifyOnConnectionStatusChange(job : Int, status : ConnectedStatus) {
        for (x in m_listeners) {
            x.OnConnectionStatusChange(job, status)
        }
    }

}