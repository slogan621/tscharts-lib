/*
 * (C) Copyright Syd Logan 2023
 * (C) Copyright Thousand Smiles Foundation 2023
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

import com.zebra.sdk.comm.ConnectionException

object ZebraWristbandPrinterStatus : WristbandPrinterStatus() {
    private var m_printerStatus = WristbandPrinter.PrinterStatus.Idle
    override fun checkPrinterStatus(printer: WristbandPrinter): WristbandPrinter.PrinterStatus {
        val zwp : ZebraWristbandPrinter = printer as ZebraWristbandPrinter
        var newStatus :  WristbandPrinter.PrinterStatus = m_printerStatus
        try {
            val xlinkOsPrinter = zwp.m_printer
            val printerStatus = xlinkOsPrinter?.currentStatus
            if (printerStatus?.isReadyToPrint == true) {
                newStatus = WristbandPrinter.PrinterStatus.Idle
            } else if (printerStatus?.isPaperOut == true) {
                newStatus = WristbandPrinter.PrinterStatus.OutOfPaper
            } else if (printerStatus?.isHeadOpen == true) {
                newStatus = WristbandPrinter.PrinterStatus.HeadOpen
            } else if (printerStatus?.isPaused == true) {
                newStatus = WristbandPrinter.PrinterStatus.Paused
            }
        } catch (e: ConnectionException) {
            //setStatus(e.getMessage(), Color.RED);
        }
        if (newStatus != m_printerStatus) {
            m_printerStatus = newStatus
            for (item in m_listeners) {
                if (item == zwp) {
                    item.OnStatusChange(-1, m_printerStatus)
                }
            }
        }
        return m_printerStatus
    }
}