package org.thousandsmiles.tscharts_lib;

import android.view.View;

public class WristbandConnectivityThread extends Thread {
    private View m_view;
    protected WristbandPrinter m_printer;
    protected ThreadLocal<WristbandPrinter> m_wbPrinter = new ThreadLocal<WristbandPrinter>();

    public WristbandConnectivityThread(WristbandPrinter printer, View view) {
        this.m_view = view;
        this.m_printer = printer;
        m_wbPrinter.set(m_printer);
    }
}
