/*
 * (C) Copyright Syd Logan 2018-2020
 * (C) Copyright Thousand Smiles Foundation 2018-2020
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


package org.thousandsmiles.tscharts_lib;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PatientData implements Parcelable, Comparable<PatientData> {
    private int m_id;
    private int m_oldId;
    private String m_fatherLast = "";
    private String m_motherLast = "";
    private String m_first = "";
    private String m_middle = "";
    private String m_dob = "";
    private String m_gender = "";
    private String m_street1 = "";
    private String m_street2 = "";
    private String m_colonia = "";
    private String m_city = "";
    private String m_state = "";
    private String m_phone1 = "";
    private String m_phone2 = "";
    private String m_email = "";
    private String m_emergencyFullName = "";
    private String m_emergencyPhone = "";
    private String m_emergencyEmail = "";
    private String m_curp = "";
    private boolean m_valid;
    private boolean m_isCurrentXray;
    private ArrayList<String> m_months = null;
    private ArrayList<String> m_abbreviatedMonths = null;
    private String m_timeOfArrival;

    private void initAbbreviatedMonthStrings(Context ctx) {
        if (m_abbreviatedMonths == null || m_abbreviatedMonths.size() == 0) {
            m_abbreviatedMonths = new ArrayList<String>();

            m_abbreviatedMonths.add(ctx.getResources().getString(R.string.JAN));
            m_abbreviatedMonths.add(ctx.getResources().getString(R.string.FEB));
            m_abbreviatedMonths.add(ctx.getResources().getString(R.string.MAR));
            m_abbreviatedMonths.add(ctx.getResources().getString(R.string.APR));
            m_abbreviatedMonths.add(ctx.getResources().getString(R.string.MAY));
            m_abbreviatedMonths.add(ctx.getResources().getString(R.string.JUN));
            m_abbreviatedMonths.add(ctx.getResources().getString(R.string.JUL));
            m_abbreviatedMonths.add(ctx.getResources().getString(R.string.AUG));
            m_abbreviatedMonths.add(ctx.getResources().getString(R.string.SEP));
            m_abbreviatedMonths.add(ctx.getResources().getString(R.string.OCT));
            m_abbreviatedMonths.add(ctx.getResources().getString(R.string.NOV));
            m_abbreviatedMonths.add(ctx.getResources().getString(R.string.DEC));
        }
    }

    private void initMonthStrings(Context ctx) {
        if (m_months == null || m_months.size() == 0) {
            m_months = new ArrayList<String>();

            m_months.add(ctx.getResources().getString(R.string.January));
            m_months.add(ctx.getResources().getString(R.string.February));
            m_months.add(ctx.getResources().getString(R.string.March));
            m_months.add(ctx.getResources().getString(R.string.April));
            m_months.add(ctx.getResources().getString(R.string.May));
            m_months.add(ctx.getResources().getString(R.string.June));
            m_months.add(ctx.getResources().getString(R.string.July));
            m_months.add(ctx.getResources().getString(R.string.August));
            m_months.add(ctx.getResources().getString(R.string.September));
            m_months.add(ctx.getResources().getString(R.string.October));
            m_months.add(ctx.getResources().getString(R.string.November));
            m_months.add(ctx.getResources().getString(R.string.December));
        }
    }

    public PatientData() {
        m_id = -1;
        m_oldId = -1;
        m_fatherLast = "";
        m_motherLast = "";
        m_first = "";
        m_middle = "";
        m_dob = "";
        m_gender = "Female";
        m_street1 = "";
        m_street2 = "";
        m_colonia = "";
        m_city = "";
        m_state = "Baja California";
        m_phone1 = "";
        m_phone2 = "";
        m_email = "";
        m_emergencyFullName = "";
        m_emergencyPhone = "";
        m_emergencyEmail = "";
        m_curp = "";
        m_valid = true;
        m_isCurrentXray = false;
        m_timeOfArrival = "";
    }

    public PatientData(JSONObject o) {
        m_valid = false;
        if (o != null) {
            fromJSONObject(o);
        }
    }

    protected PatientData(Parcel in) {
        m_id = in.readInt();
        m_oldId = in.readInt();
        m_fatherLast = in.readString().trim();
        m_motherLast = in.readString().trim();
        m_first = in.readString().trim();
        m_middle = in.readString().trim();
        m_dob = in.readString().trim();
        m_gender = in.readString().trim();
        m_street1 = in.readString().trim();
        m_street2 = in.readString().trim();
        m_colonia = in.readString().trim();
        m_city = in.readString().trim();
        m_state = in.readString().trim();
        m_phone1 = in.readString().trim();
        m_phone2 = in.readString().trim();
        m_email = in.readString().trim();
        m_emergencyFullName = in.readString().trim();
        m_emergencyPhone = in.readString().trim();
        m_emergencyEmail = in.readString().trim();
        m_curp = in.readString().trim();
        m_valid = in.readByte() != 0;
        m_isCurrentXray = in.readByte() != 0;
        m_timeOfArrival = in.readString().trim();
    }

    public static final Creator<PatientData> CREATOR = new Creator<PatientData>() {
        @Override
        public PatientData createFromParcel(Parcel in) {
            return new PatientData(in);
        }

        @Override
        public PatientData[] newArray(int size) {
            return new PatientData[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(m_timeOfArrival.trim());
        dest.writeByte((byte) (m_isCurrentXray == false ? 0 : 1));
        dest.writeByte((byte) (m_valid == false ? 0 : 1));
        dest.writeString(m_curp.trim());
        dest.writeString(m_emergencyEmail.trim());
        dest.writeString(m_emergencyPhone.trim());
        dest.writeString(m_emergencyFullName.trim());
        dest.writeString(m_email.trim());
        dest.writeString(m_phone2.trim());
        dest.writeString(m_phone1.trim());
        dest.writeString(m_state.trim());
        dest.writeString(m_city.trim());
        dest.writeString(m_colonia.trim());
        dest.writeString(m_street2.trim());
        dest.writeString(m_street1.trim());
        dest.writeString(m_gender.trim());
        dest.writeString(m_dob.trim());
        dest.writeString(m_middle.trim());
        dest.writeString(m_first.trim());
        dest.writeString(m_motherLast.trim());
        dest.writeString(m_fatherLast.trim());
        dest.writeInt(m_oldId);
        dest.writeInt(m_id);
    }

    public String getPatientFullName(boolean lastFirst) {
        String ret;

        if (lastFirst == true) {
            ret = String.format("%s-%s, %s %s", m_fatherLast, m_motherLast, m_first, m_middle);
        } else {
            ret = String.format("%s %s, %s-%s", m_first, m_middle, m_fatherLast, m_motherLast);
        }
        return ret;
    }

    public String getCURP() {
        return m_curp.trim();
    }

    public void setCURP(String curp) {
        m_curp = curp.trim();
    }

    public int getOldId() {
        return m_oldId;
    }

    public void setOldId(int id) {
        m_oldId = id;
    }

    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        m_id = id;
    }

    public String getTimeOfArrival() {
        return m_timeOfArrival;
    }

    public void setTimeOfArrival(String value) {
        m_timeOfArrival = value;
    }

    public String getFatherLast() {
        return m_fatherLast.trim();
    }

    public void setFatherLast(String fatherLast) {
        m_fatherLast = fatherLast.trim();
    }

    public String getMotherLast() {
        return m_motherLast.trim();
    }

    public void setMotherLast(String motherLast) {
        m_motherLast = motherLast.trim();
    }

    public String getFirst() {
        return m_first.trim();
    }

    public void setFirst(String first) {
        m_first = first.trim();
    }

    public String getMiddle() {
        return m_middle.trim();
    }

    public void setMiddle(String middle) {
        m_middle = middle.trim();
    }

    public String getDob() {
        return m_dob.trim();
    }

    public String getDobMilitary(Context ctx) {

        String ret;

        // lazy instantiate the month LUT

        initMonthStrings(ctx);

        // value is mm/dd/YYYY, convert to ddMMMYYYY, where MMM is a 3-character month string

        try {
            String delims = "[/]";

            String[] tokens = m_dob.split(delims);
            int month = Integer.parseInt(tokens[0]);
            ret = String.format("%s%s%s", tokens[1], m_months.get(month - 1).toUpperCase().substring(0, 3), tokens[2]);
        } catch(NumberFormatException ex){
            ret = m_dob;
        }
        return ret.trim();
    }

    public String fromDobMilitary(Context ctx, String m_dob) {

        String ret;

        // lazy instantiate the month LUT

        initAbbreviatedMonthStrings(ctx);
        initMonthStrings(ctx);

        // value is ddMMMYYYY, convert to mm/dd/YYYY, where MMM is a 3-character month string
        // if is already in mm/dd/YYYY, try should throw an exception, so just set ret to m_dob

        try {
            String delims = "[/]";

            String[] tokens = m_dob.split(delims);
            int month = Integer.parseInt(tokens[0]);
            String tmp  = String.format("%s%s%s", tokens[1], m_months.get(month - 1).toUpperCase().substring(0, 3), tokens[2]);
            // all good, was already in proper format
            ret = m_dob;
        } catch(NumberFormatException ex) {

            // string was not in mm/dd/YYYY so must be (XXX) military, so convert it.

            String day = m_dob.substring(0, 2);
            String month = m_dob.substring(2, 5);
            month = String.format("%02d", m_abbreviatedMonths.indexOf(month) + 1);
            String year = m_dob.substring(5, 9);

            ret = String.format("%s/%s/%s", month, day, year);
        }
        return ret.trim();
    }

    public void setDob(String dob) {
        m_dob = dob.trim();
    }

    public String getGender() {
        return m_gender.trim();
    }

    public void setGender(String gender) {
        m_gender = gender.trim();
    }

    public String getStreet1() {
        return m_street1.trim();
    }

    public void setStreet1(String street1) {
        m_street1 = street1.trim();
    }

    public String getStreet2() {
        return m_street2.trim();
    }

    public void setStreet2(String street2) {
        m_street2 = street2.trim();
    }

    public String getColonia() {
        return m_colonia.trim();
    }

    public void setColonia(String colonia) {
        m_colonia = colonia.trim();
    }

    public String getCity() {
        return m_city.trim();
    }

    public void setCity(String city) {
        m_city = city.trim();
    }

    public String getState() {
        return m_state.trim();
    }

    public void setState(String state) {
        m_state = state.trim();
    }

    public String getPhone1() {
        return m_phone1.trim();
    }

    public void setPhone1(String phone1) {
        m_phone1 = phone1.trim();
    }

    public String getPhone2() {
        return m_phone2.trim();
    }

    public void setPhone2(String phone2) {
        m_phone2 = phone2.trim();
    }

    public String getEmail() {
        return m_email.trim();
    }

    public void setEmail(String email) {
        m_email = email.trim();
    }

    public String getEmergencyFullName() {
        return m_emergencyFullName.trim();
    }

    public void setEmergencyFullName(String emergencyFullName) {
        m_emergencyFullName = emergencyFullName.trim();
    }

    public String getEmergencyPhone() {
        return m_emergencyPhone.trim();
    }

    public void setEmergencyPhone(String emergencyPhone) {
        m_emergencyPhone = emergencyPhone.trim();
    }

    public String getEmergencyEmail() {
        return m_emergencyEmail.trim();
    }

    public void setEmergencyEmail(String emergencyEmail) {
        m_emergencyEmail = emergencyEmail.trim();
    }

    public boolean getValid()
    {
        return m_valid;
    }

    public boolean getIsCurrentXray() {
        return m_isCurrentXray;
    }

    public void setIsCurrentXray(boolean isCurrentXray) {
        m_isCurrentXray = isCurrentXray;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!PatientData.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final PatientData other = (PatientData) obj;

        m_valid = true;

        if (this.m_curp.equals(other.m_curp)) {
            return false;
        }

        if (this.m_fatherLast.equals(other.m_fatherLast)) {
            return false;
        }

        if (this.m_motherLast.equals(other.m_motherLast)) {
            return false;
        }

        if (this.m_first.equals(other.m_first)) {
            return false;
        }

        if (this.m_middle.equals(other.m_middle)) {
            return false;
        }

        if (this.m_dob.equals(other.m_dob)) {
            return false;
        }

        if (this.m_gender.equals(other.m_gender)) {
            return false;
        }

        if (this.m_street1.equals(other.m_street1)) {
            return false;
        }

        if (this.m_street2.equals(other.m_street2)) {
            return false;
        }

        if (this.m_colonia.equals(other.m_colonia)) {
            return false;
        }

        if (this.m_city.equals(other.m_city)) {
            return false;
        }

        if (this.m_state.equals(other.m_state)) {
            return false;
        }

        if (this.m_phone1.equals(other.m_phone1)) {
            return false;
        }

        if (this.m_phone2.equals(other.m_phone2)) {
            return false;
        }

        if (this.m_email.equals(other.m_email)) {
            return false;
        }

        if (this.m_emergencyFullName.equals(other.m_emergencyFullName)) {
            return false;
        }

        if (this.m_emergencyPhone.equals(other.m_emergencyPhone)) {
            return false;
        }

        if (this.m_emergencyEmail.equals(other.m_emergencyEmail)) {
            return false;
        }

        return true;
    }

    public int fromJSONObject(JSONObject o)
    {
        int ret = 0;

        try {
            setId(o.getInt("id"));
            try {
                setOldId(o.getInt("oldid"));
            } catch (Exception e) {
                setOldId(-1);
            }
            setCURP(o.getString("curp").trim());
            setFatherLast(o.getString("paternal_last").trim());
            setMotherLast(o.getString("maternal_last").trim());
            setFirst(o.getString("first").trim());
            setMiddle(o.getString("middle").trim());
            setDob(o.getString("dob").trim());
            setGender(o.getString("gender").trim());
            setStreet1(o.getString("street1").trim());
            setStreet2(o.getString("street2").trim());
            setColonia(o.getString("colonia").trim());
            setCity(o.getString("city").trim());
            setState(o.getString("state").trim());
            setPhone1(o.getString("phone1").trim());
            setPhone2(o.getString("phone2").trim());
            setEmail(o.getString("email").trim());
            setEmergencyFullName(o.getString("emergencyfullname").trim());
            setEmergencyPhone(o.getString("emergencyphone").trim());
            setEmergencyEmail(o.getString("emergencyemail").trim());
            setTimeOfArrival(""); // decorator, not in database but comes from registration
            m_valid = true;
            m_isCurrentXray = false;
        } catch (JSONException e) {
            ret = -1;
        }
        return ret;
    }

    public JSONObject toJSONObject(Context ctx)
    {
        JSONObject data = new JSONObject();
        try {
            if (getId() != -1) {
                data.put("id", getId());
            }
            data.put("oldid", getOldId());
            data.put("curp", getCURP());
            data.put("paternal_last", getFatherLast().trim());
            data.put("maternal_last", getMotherLast().trim());
            data.put("first", getFirst().trim());
            data.put("middle", getMiddle().trim());
            data.put("dob", fromDobMilitary(ctx, getDob().trim()));
            data.put("gender", getGender().trim());
            data.put("street1", getStreet1().trim());
            data.put("street2", getStreet2().trim());
            data.put("colonia", getColonia().trim());
            data.put("city", getCity().trim());
            data.put("state", getState().trim());
            data.put("phone1", getPhone1().trim());
            data.put("phone2", getPhone2().trim());
            data.put("email", getEmail().trim());
            data.put("emergencyfullname", getEmergencyFullName().trim());
            data.put("emergencyphone", getEmergencyPhone().trim());
            data.put("emergencyemail", getEmergencyEmail().trim());

            // we don't current support the following but the backend requires them

            data.put("prefix", "");
            data.put("suffix", "");
        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
            data = null;
        }
        return data;
    }

    // sort based on time of arrival

    @Override
    public int compareTo(PatientData patientData) {
        return this.getTimeOfArrival().compareTo(patientData.getTimeOfArrival());
    }
}

