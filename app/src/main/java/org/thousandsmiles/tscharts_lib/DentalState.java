/*
 * (C) Copyright Syd Logan 2020
 * (C) Copyright Thousand Smiles Foundation 2020
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class DentalState implements Serializable {

    private int m_patient;
    private int m_clinic;
    private int m_id;
    private String m_comment;
    private String m_username;
    private int m_tooth;
    private int m_code;
    private Location m_location;

    public enum Location {
        DENTAL_LOCATION_TOP,
        DENTAL_LOCATION_BOTTOM
    }

    public enum State {
        DENTAL_STATE_NONE,
        DENTAL_STATE_UNTREATED,
        DENTAL_STATE_TREATED,
        DENTAL_STATE_MISSING,
        DENTAL_STATE_OTHER
    }

    public enum Surface {
        DENTAL_SURFACE_NONE,
        DENTAL_SURFACE_BUCCAL,
        DENTAL_SURFACE_DISTAL,
        DENTAL_SURFACE_LINGUAL,
        DENTAL_SURFACE_MESIAL,
        DENTAL_SURFACE_OCCLUSAL,
        DENTAL_SURFACE_LABIAL,
        DENTAL_SURFACE_INCISAL,
        DENTAL_SURFACE_WHOLE_MOUTH_OR_VISIT,
        DENTAL_SURFACE_OTHER
    }

    private ArrayList<Surface> m_surfaces = new ArrayList<Surface>();
    private State m_state = State.DENTAL_STATE_NONE;

    public int getTooth() {
        return m_tooth;
    }

    public void setTooth(int m_tooth) {
        this.m_tooth = m_tooth;
    }

    public Location getLocation() {
        return m_location;
    }

    public void setLocation(Location val) {
        this.m_location = val;
    }

    public int getCode() {
        return m_code;
    }

    public void setCode(int m_code) {
        this.m_code = m_code;
    }

    public State getState() {
        return m_state;
    }

    public void setState(State m_state) {
        this.m_state = m_state;
    }

    public ArrayList<Surface> getSurfaces() {
        return m_surfaces;
    }

    public void addSurface(Surface surface) {
        this.m_surfaces.add(surface);
    }

    public void setSurfaces(ArrayList<Surface> values) {
        this.m_surfaces = values;
    }

    public void removeSurface(Surface surface) {
        this.m_surfaces.remove(surface);
    }

    public void clearSurfaces() {
        this.m_surfaces.clear();
    }

    public int getPatient() {
        return m_patient;
    }

    public void setPatient(int patient) {
        m_patient = patient;
    }

    public int getClinic() {
        return m_clinic;
    }

    public void setClinic(int clinic) {
        m_clinic = clinic;
    }

    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        m_id = id;
    }

    public String getComment() {
        return m_comment;
    }

    public void setComment(String comment) {
        m_comment = comment;
    }

    public String getUsername() {
        return m_username;
    }

    public void setUsername(String username) {
        m_username = username;
    }

    public Location locationToEnum(String val)
    {
        Location ret = Location.DENTAL_LOCATION_TOP;

        if (val.equals("top")) {
            ret = Location.DENTAL_LOCATION_TOP;
        } else if (val.equals("bottom")) {
            ret = Location.DENTAL_LOCATION_BOTTOM;
        }
        return ret;
    }

    public String locationToString(Location val)
    {
        String ret = "top";

        if (val == Location.DENTAL_LOCATION_TOP) {
            ret = "top";
        } else if (val == Location.DENTAL_LOCATION_BOTTOM) {
            ret = "bottom";
        }

        return ret;
    }

    public State stateToEnum(String val)
    {
        State ret = State.DENTAL_STATE_NONE;

        if (val.equals("treated")) {
            ret = State.DENTAL_STATE_TREATED;
        } else if (val.equals("untreated")) {
            ret = State.DENTAL_STATE_UNTREATED;
        } else if (val.equals("missing")) {
            ret = State.DENTAL_STATE_MISSING;
        } else if (val.equals("other")) {
            ret = State.DENTAL_STATE_OTHER;
        }
        return ret;
    }

    public String stateToString(State val)
    {
        String ret = "none";

        if (val == State.DENTAL_STATE_TREATED) {
            ret = "treated";
        } else if (val == State.DENTAL_STATE_UNTREATED) {
            ret = "untreated";
        } else if (val == State.DENTAL_STATE_OTHER) {
            ret = "other";
        } else if (val == State.DENTAL_STATE_MISSING) {
            ret = "missing";
        }

        return ret;
    }

    public String surfaceToString(Surface val) {
        String ret = "none";

        if (val == Surface.DENTAL_SURFACE_NONE) {
            ret = "none";
        } else if (val == Surface.DENTAL_SURFACE_BUCCAL) {
            ret = "buccal";
        } else if (val == Surface.DENTAL_SURFACE_LINGUAL) {
            ret = "lingual";
        } else if (val == Surface.DENTAL_SURFACE_MESIAL) {
            ret = "mesial";
        } else if (val == Surface.DENTAL_SURFACE_OCCLUSAL) {
            ret = "occlusal";
        } else if (val == Surface.DENTAL_SURFACE_LABIAL) {
            ret = "labial";
        } else if (val == Surface.DENTAL_SURFACE_DISTAL) {
            ret = "distal";
        } else if (val == Surface.DENTAL_SURFACE_INCISAL) {
            ret = "incisal";
        } else if (val == Surface.DENTAL_SURFACE_WHOLE_MOUTH_OR_VISIT) {
            ret = "whole_mouth_or_visit";
        } else if (val == Surface.DENTAL_SURFACE_OTHER) {
            ret = "other";
        }
        return ret;
    }

    public Surface surfaceToEnum(String val) {
        Surface ret = Surface.DENTAL_SURFACE_NONE;

        if (val.equals("none")) {
            ret = Surface.DENTAL_SURFACE_NONE;
        } else if (val.equals("buccal")) {
             ret = Surface.DENTAL_SURFACE_BUCCAL;
        } else if (val.equals("buccal")) {
            ret = Surface.DENTAL_SURFACE_LINGUAL;
        } else if (val.equals("mesial")) {
            ret = Surface.DENTAL_SURFACE_MESIAL;
        } else if (val.equals("distal")) {
            ret = Surface.DENTAL_SURFACE_DISTAL;
        } else if (val.equals("occlusal")) {
            ret = Surface.DENTAL_SURFACE_OCCLUSAL;
        } else if (val.equals("labial")) {
            ret = Surface.DENTAL_SURFACE_LABIAL;
        } else if (val.equals("incisal")) {
            ret =  Surface.DENTAL_SURFACE_INCISAL;
        } else if (val.equals("whole_mouth_or_visit")) {
            ret = Surface.DENTAL_SURFACE_WHOLE_MOUTH_OR_VISIT;
        } else if (val.equals("other")) {
            ret = Surface.DENTAL_SURFACE_OTHER;
        }
        return ret;
    }

    public ArrayList<Surface> CSVToSurfaceList(String csv) {
      ArrayList<Surface> ret = new ArrayList<Surface>();

      String[] values = csv.split(",");
      for (int i = 0; i < values.length; i++) {
          ret.add(surfaceToEnum(values[i]));
      }

      return ret;
    }

    public String surfaceListToCSV(ArrayList<Surface> list) {
        String ret = "";
        for (int i = 0; i < list.size(); i++) {
            String surface = surfaceToString(list.get(i));
            if (ret.length() > 0) {
                ret += ",";
            }
            ret += surface;
        }
        return ret;
    }

    public int fromJSONObject(JSONObject o)
    {
        int ret = 0;

        try {
            setTooth(o.getInt("tooth"));
            setCode(o.getInt("code"));
            setState(stateToEnum(o.getString("state")));
            setLocation(locationToEnum(o.getString("location")));
            setSurfaces(CSVToSurfaceList(o.getString("surface")));

            setId(o.getInt("id"));
            setPatient(o.getInt("patient"));
            setClinic(o.getInt("clinic"));
            setComment(o.getString("comment"));
            setUsername(o.getString("username"));
        } catch (JSONException e) {
            ret = -1;
        }
        return ret;
    }

    public JSONObject toJSONObject(boolean includeId)
    {
        JSONObject data = new JSONObject();
        try {
            if (includeId == true) {
                data.put("id", getId());
            }

            data.put("patient", getPatient());
            data.put("clinic", getClinic());
            data.put("comment", getComment());
            data.put("username", getUsername());

            data.put("tooth", getTooth());
            data.put("code", getCode());
            data.put("state", stateToString(getState()));
            data.put("location", locationToString(getLocation()));
            data.put("surface", surfaceListToCSV(getSurfaces()));

        } catch(Exception e) {
            // not sure this would ever happen, ignore. Continue on with the request with the expectation it fails
            // because of the bad JSON sent
            data = null;
        }
        return data;
    }

    public DentalState() {
    }

    public DentalState(DentalState rhs) {
        m_patient = rhs.m_patient;
        m_clinic = rhs.m_clinic;
        m_id = rhs.m_id;
        m_comment = rhs.m_comment;
        m_username = rhs.m_username;

        m_state = rhs.m_state;
        m_location = rhs.m_location;
        m_surfaces = (ArrayList<Surface>) rhs.m_surfaces.clone();
        m_code = rhs.m_code;
        m_tooth = rhs.m_tooth;
    }
}
