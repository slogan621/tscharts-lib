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
import com.zebra.sdk.printer.*

class ZebraWristbandPrinter(ipAddr: String?, port: Int?) : WristbandPrinter(ipAddr, port) {

    var m_port : Int? = port
    var m_ipAddr : String? = ipAddr
    var m_printer: ZebraPrinter? = null
    var m_connection : Connection? = null;

    override fun print(job: Int, patient: PatientData) : Boolean {

        // code to print to zebra goes here.
        // On success, call notifyOnSuccess with latest status reported by printer
        // On detecting status changes, call notifyOnStatusChange with status
        // On error,  call notifyOnSuccess with latest status reported by printer and custom msg or ""

        m_printer = connect(job);
        if (m_printer == null) {
            notifyOnError(job, m_printerStatus, "Unable to connect to printer")
            return false
        }
        var configLabel: ByteArray? = null

        try {
            val printerLanguage: PrinterLanguage = m_printer!!.getPrinterControlLanguage()
            SGD.SET("device.languages", "zpl", m_connection)
            if (printerLanguage == PrinterLanguage.ZPL) {

                /*
                     String position of the text depending on wristband size. QR position will be screwed up.
                     String position = "400";      // For 6" wristband
                     String position = "400";      // for 7" wristband
                 */

                val position = "2000" // for 11" wristband
                /*
                   Format the QR code. this does position, QR Code magnification, and inserts the ID,FATHER_LAST,FIRST_NAME
                    All comma separated
                 */
                val qrcode = "^FO80,1800^BQN,2,6^FDQA," + patient.getId() + "," + patient.getFatherLast() + "," + patient.getFirst() + "^FS"
                /*
                    Format the text line data:
                        Line 1: ID CURP
                        Line 2: Father's Last, Mother's last
                        Line 3: First Name, Middle Name
                        Line 4: DOB, Male/Female
                 */
                val line1Text = "ID: " + patient.getId() + "  CURP: " + patient.getCURP()
                val line2Text: String = patient.getFatherLast() + ", " + patient.getMotherLast()
                val line3Text: String = patient.getFirst() + " " + patient.getMiddle()
                val line4Text: String = patient.getDob() + " " + patient.getGender()
                /*
                    Format the four line with position and font info
                */
                val line1 = "^XA^FO1,10^FS^FT225,$position^A0R,40,40^FD$line1Text ^FS"
                val line2 = "^FO1,10^FS^FT168,$position^A0R,40,40^FD$line2Text ^FS"
                val line3 = "^FO1,10^FS^FT111,$position^A0R,40,40^FD$line3Text^FS"
                val line4 = "^FO1,10^FS^FT54,$position^A0R,40,40^FD$line4Text^FS$qrcode^XZ"
                /*
                    Concatenate the four line into a single string and
                    return the complete image to the calling routine to print
                */
                val fullLine = line1 + line2 + line3 + line4
                configLabel = fullLine.toByteArray()
            } else if (printerLanguage == PrinterLanguage.CPCL) {
                return false
            }
        } catch (e: ConnectionException) {
        }
        m_connection?.write(configLabel)
        /*
         *  TODO ENABLE SECOND connection.write() to print the second wristband;
         *  There is a ^PQ command that is support to support the count, but
         *  I could not get it to work
         */

//      m_connection.write(configLabel)
        disconnect(job);
        notifyOnSuccess(job, m_printerStatus)
        return true
    }

    override fun reachable(): Boolean {
        var ret = false
        val printer = connect();
        if (printer != null) {
            ret = true;
            disconnect()
        }
        return ret
    }

    private fun connect(job :Int): ZebraPrinter? {
        val printer = connect();
        if (printer != null) {
            changeConnectionStatus(job, ConnectedStatus.Connected)
        } else {
            disconnect(job)
        }
        return printer
    }

    private fun connect() : ZebraPrinter? {
        //setStatus("Connecting...", Color.YELLOW)

        try {
            val port: Int = m_port!!
            m_connection = TcpConnection(m_ipAddr, port)
            //SettingsHelper.saveIp(this, getTcpAddress())
            //SettingsHelper.savePort(this, getTcpPortNumber())
        } catch (e: NumberFormatException) {
            //setStatus("Port Number Is Invalid", Color.RED)
            return null
        }

        try {
            (m_connection as TcpConnection).open()
            //setStatus("Connected", Color.GREEN)
        } catch (e: ConnectionException) {
            //setStatus("Comm Error! Disconnecting", Color.RED)
            //DemoSleeper.sleep(1000)
            return null
        }
        var printer: ZebraPrinter? = null
        if ((m_connection as TcpConnection).isConnected()) {
            try {
                printer = ZebraPrinterFactory.getInstance(m_connection)
                //setStatus("Determining Printer Language", Color.YELLOW)
                val pl = SGD.GET("device.languages", m_connection)
                //setStatus("Printer Language $pl", Color.BLUE)
            } catch (e: ConnectionException) {
                //setStatus("Unknown Printer Language", Color.RED)
                printer = null
                //DemoSleeper.sleep(1000)
            } catch (e: ZebraPrinterLanguageUnknownException) {
                //setStatus("Unknown Printer Language", Color.RED)
                printer = null
                //DemoSleeper.sleep(1000)
            }
        }
        return printer
    }

    fun disconnect(job : Int) {
        disconnect()
        changeConnectionStatus(job, ConnectedStatus.Disconnected)
    }

    private fun disconnect() {
        try {
            //setStatus("Disconnecting", Color.RED)
            if (m_connection != null) {
                m_connection!!.close()
                m_connection = null
            }
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