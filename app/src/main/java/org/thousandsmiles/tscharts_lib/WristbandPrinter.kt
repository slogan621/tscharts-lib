/*
 * (C) Copyright Syd Logan 2022-2023
 * (C) Copyright Thousand Smiles Foundation 2022-2023
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

abstract class WristbandPrinter(var ipAddr: String?, var port: Int?) : WristbandStatusPublisher {

    var m_connectedStatus : ConnectedStatus = ConnectedStatus.Disconnected
    var m_printerStatus : PrinterStatus = PrinterStatus.Idle
    var m_listeners = ArrayList<WristbandStatusListener>()

    enum class ConnectedStatus {
        Connected,
        Disconnected,
    }

    enum class PrinterStatus {
        Idle,
        OutOfPaper,
        Jammed,
        HeadOpen,
        Paused,
        Printing,
        Restarting,
    }

    abstract fun checkPrinterStatus() : PrinterStatus

    abstract fun print(job : Int, patient : PatientData, numberOfCopies: Int) : Boolean

    abstract fun reachable() : Boolean

    override fun registerListener(listener : WristbandStatusListener) {
        m_listeners.add(listener);
    }

    override fun removeListener(listener : WristbandStatusListener) {
        m_listeners.remove(listener);
    }
}