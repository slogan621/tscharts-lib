/*
 * (C) Copyright Syd Logan 2021
 * (C) Copyright Thousand Smiles Foundation 2021
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
import java.util.*

class MilitaryDate {
    private var m_months: ArrayList<String>? = null
    private var m_abbreviatedMonths: ArrayList<String>? = null

    private fun initAbbreviatedMonthStrings(ctx: Context) {
        if (m_abbreviatedMonths == null || m_abbreviatedMonths!!.size == 0) {
            m_abbreviatedMonths = ArrayList()
            m_abbreviatedMonths!!.add(ctx.resources.getString(R.string.JAN))
            m_abbreviatedMonths!!.add(ctx.resources.getString(R.string.FEB))
            m_abbreviatedMonths!!.add(ctx.resources.getString(R.string.MAR))
            m_abbreviatedMonths!!.add(ctx.resources.getString(R.string.APR))
            m_abbreviatedMonths!!.add(ctx.resources.getString(R.string.MAY))
            m_abbreviatedMonths!!.add(ctx.resources.getString(R.string.JUN))
            m_abbreviatedMonths!!.add(ctx.resources.getString(R.string.JUL))
            m_abbreviatedMonths!!.add(ctx.resources.getString(R.string.AUG))
            m_abbreviatedMonths!!.add(ctx.resources.getString(R.string.SEP))
            m_abbreviatedMonths!!.add(ctx.resources.getString(R.string.OCT))
            m_abbreviatedMonths!!.add(ctx.resources.getString(R.string.NOV))
            m_abbreviatedMonths!!.add(ctx.resources.getString(R.string.DEC))
        }
    }

    private fun initMonthStrings(ctx: Context) {
        if (m_months == null || m_months!!.size == 0) {
            m_months = ArrayList()
            m_months!!.add(ctx.resources.getString(R.string.January))
            m_months!!.add(ctx.resources.getString(R.string.February))
            m_months!!.add(ctx.resources.getString(R.string.March))
            m_months!!.add(ctx.resources.getString(R.string.April))
            m_months!!.add(ctx.resources.getString(R.string.May))
            m_months!!.add(ctx.resources.getString(R.string.June))
            m_months!!.add(ctx.resources.getString(R.string.July))
            m_months!!.add(ctx.resources.getString(R.string.August))
            m_months!!.add(ctx.resources.getString(R.string.September))
            m_months!!.add(ctx.resources.getString(R.string.October))
            m_months!!.add(ctx.resources.getString(R.string.November))
            m_months!!.add(ctx.resources.getString(R.string.December))
        }
    }

    fun getDobMilitary(ctx: Context?, dob: String): String? {
        var ret: String?

        // lazy instantiate the month LUT
        if (ctx != null) {
            initMonthStrings(ctx)
        }

        if (dob == null) {
            return dob
        }

        // value is mm/dd/YYYY, convert to ddMMMYYYY, where MMM is a 3-character month string
        try {
            val delims = "/"
            val tokens: Array<String> = dob.split(delims).toTypedArray()
            val month = tokens[0].toInt()
            ret = m_months?.get(month - 1)?.toUpperCase()?.let {
                String.format(
                    "%s%s%s",
                    tokens[1], it.substring(0, 3), tokens[2]
                )
            }
        } catch (ex: NumberFormatException) {
            if (dob == null || dob == "null") { // vaccinations
                ret = null
            } else {
                ret = dob
            }
        }
        return ret
    }

    fun fromDobMilitary(ctx: Context?, dob: String): String? {
        var ret: String? = null

        // lazy instantiate the month LUT
        initAbbreviatedMonthStrings(ctx!!)
        initMonthStrings(ctx)

        // value is ddMMMYYYY, convert to mm/dd/YYYY, where MMM is a 3-character month string
        // if is already in mm/dd/YYYY, try should throw an exception, so just set ret to m_dob
        try {
            val delims = "/"
            val tokens = dob.split(delims).toTypedArray()
            val month = tokens[0].toInt()
            val tmp = String.format(
                "%s%s%s",
                tokens[1], m_months!![month - 1].toUpperCase().substring(0, 3), tokens[2]
            )
            // all good, was already in proper format
            ret = dob
        } catch (ex: java.lang.NumberFormatException) {

            // string was not in mm/dd/YYYY so must be (XXX) military, so convert it.
            val day = dob.substring(0, 2)
            var month = dob.substring(2, 5)
            month = String.format("%02d", m_abbreviatedMonths!!.indexOf(month) + 1)
            val year = dob.substring(5, 9)
            ret = String.format("%s/%s/%s", month, day, year)
        }
        return ret
    }
}