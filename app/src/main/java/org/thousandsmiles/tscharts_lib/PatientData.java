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

public class PatientData implements Parcelable {
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

    public PatientData(Context ctx) {
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

    public PatientData(JSONObject o) {
        m_valid = false;
        if (o != null) {
            fromJSONObject(o);
        }
    }

    protected PatientData(Parcel in) {
        m_id = in.readInt();
        m_oldId = in.readInt();
        m_fatherLast = in.readString();
        m_motherLast = in.readString();
        m_first = in.readString();
        m_middle = in.readString();
        m_dob = in.readString();
        m_gender = in.readString();
        m_street1 = in.readString();
        m_street2 = in.readString();
        m_colonia = in.readString();
        m_city = in.readString();
        m_state = in.readString();
        m_phone1 = in.readString();
        m_phone2 = in.readString();
        m_email = in.readString();
        m_emergencyFullName = in.readString();
        m_emergencyPhone = in.readString();
        m_emergencyEmail = in.readString();
        m_curp = in.readString();
        m_valid = in.readByte() != 0;
        m_isCurrentXray = in.readByte() != 0;
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

        dest.writeByte((byte) (m_isCurrentXray == false ? 0 : 1));
        dest.writeByte((byte) (m_valid == false ? 0 : 1));
        dest.writeString(m_curp);
        dest.writeString(m_emergencyEmail);
        dest.writeString(m_emergencyPhone);
        dest.writeString(m_emergencyFullName);
        dest.writeString(m_email);
        dest.writeString(m_phone2);
        dest.writeString(m_phone1);
        dest.writeString(m_state);
        dest.writeString(m_city);
        dest.writeString(m_colonia);
        dest.writeString(m_street2);
        dest.writeString(m_street1);
        dest.writeString(m_gender);
        dest.writeString(m_dob);
        dest.writeString(m_middle);
        dest.writeString(m_first);
        dest.writeString(m_motherLast);
        dest.writeString(m_fatherLast);
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
        return m_curp;
    }

    public void setCURP(String curp) {
        m_curp = curp;
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

    public String getFatherLast() {
        return m_fatherLast;
    }

    public void setFatherLast(String fatherLast) {
        m_fatherLast = fatherLast;
    }

    public String getMotherLast() {
        return m_motherLast;
    }

    public void setMotherLast(String motherLast) {
        m_motherLast = motherLast;
    }

    public String getFirst() {
        return m_first;
    }

    public void setFirst(String first) {
        m_first = first;
    }

    public String getMiddle() {
        return m_middle;
    }

    public void setMiddle(String middle) {
        m_middle = middle;
    }

    public String getDob() {
        return m_dob;
    }

    public String getDobMilitary() {

        String ret;

        // value is mm/dd/YYYY, convert to ddMMMYYYY, where MMM is a 3-character month string

        try {
            String delims = "[/]";

            String[] tokens = m_dob.split(delims);
            int month = Integer.parseInt(tokens[0]);
            ret = String.format("%s%s%s", tokens[1], m_months.get(month - 1).toUpperCase().substring(0, 3), tokens[2]);
        } catch(NumberFormatException ex){
            ret = m_dob;
        }
        return ret;
    }

    public void setDob(String dob) {
        m_dob = dob;
    }

    public String getGender() {
        return m_gender;
    }

    public void setGender(String gender) {
        m_gender = gender;
    }

    public String getStreet1() {
        return m_street1;
    }

    public void setStreet1(String street1) {
        m_street1 = street1;
    }

    public String getStreet2() {
        return m_street2;
    }

    public void setStreet2(String street2) {
        m_street2 = street2;
    }

    public String getColonia() {
        return m_colonia;
    }

    public void setColonia(String colonia) {
        m_colonia = colonia;
    }

    public String getCity() {
        return m_city;
    }

    public void setCity(String city) {
        m_city = city;
    }

    public String getState() {
        return m_state;
    }

    public void setState(String state) {
        m_state = state;
    }

    public String getPhone1() {
        return m_phone1;
    }

    public void setPhone1(String phone1) {
        m_phone1 = phone1;
    }

    public String getPhone2() {
        return m_phone2;
    }

    public void setPhone2(String phone2) {
        m_phone2 = phone2;
    }

    public String getEmail() {
        return m_email;
    }

    public void setEmail(String email) {
        m_email = email;
    }

    public String getEmergencyFullName() {
        return m_emergencyFullName;
    }

    public void setEmergencyFullName(String emergencyFullName) {
        m_emergencyFullName = emergencyFullName;
    }

    public String getEmergencyPhone() {
        return m_emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        m_emergencyPhone = emergencyPhone;
    }

    public String getEmergencyEmail() {
        return m_emergencyEmail;
    }

    public void setEmergencyEmail(String emergencyEmail) {
        m_emergencyEmail = emergencyEmail;
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
            setCURP(o.getString("curp"));
            setFatherLast(o.getString("paternal_last"));
            setMotherLast(o.getString("maternal_last"));
            setFirst(o.getString("first"));
            setMiddle(o.getString("middle"));
            setDob(o.getString("dob"));
            setGender(o.getString("gender"));
            setStreet1(o.getString("street1"));
            setStreet2(o.getString("street2"));
            setColonia(o.getString("colonia"));
            setCity(o.getString("city"));
            setState(o.getString("state"));
            setPhone1(o.getString("phone1"));
            setPhone2(o.getString("phone2"));
            setEmail(o.getString("email"));
            setEmergencyFullName(o.getString("emergencyfullname"));
            setEmergencyPhone(o.getString("emergencyphone"));
            setEmergencyEmail(o.getString("emergencyemail"));
            m_valid = true;
            m_isCurrentXray = false;
        } catch (JSONException e) {
            ret = -1;
        }
        return ret;
    }

    public JSONObject toJSONObject()
    {
        JSONObject data = new JSONObject();
        try {
            if (getId() != -1) {
                data.put("id", getId());
            }
            data.put("oldid", getOldId());
            data.put("curp", getCURP());
            data.put("paternal_last", getFatherLast());
            data.put("maternal_last", getMotherLast());
            data.put("first", getFirst());
            data.put("middle", getMiddle());
            data.put("dob", getDob());
            data.put("gender", getGender());
            data.put("street1", getStreet1());
            data.put("street2", getStreet2());
            data.put("colonia", getColonia());
            data.put("city", getCity());
            data.put("state", getState());
            data.put("phone1", getPhone1());
            data.put("phone2", getPhone2());
            data.put("email", getEmail());
            data.put("emergencyfullname", getEmergencyFullName());
            data.put("emergencyphone", getEmergencyPhone());
            data.put("emergencyemail", getEmergencyEmail());

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
}

