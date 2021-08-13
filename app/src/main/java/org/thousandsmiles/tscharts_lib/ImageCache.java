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

package org.thousandsmiles.tscharts_lib;

import java.util.HashMap;

public class ImageCache {
    private HashMap<Integer, HeadshotImage> m_cache = new HashMap<Integer, HeadshotImage>();

    public HeadshotImage getHeadshotImage(int patient) {
        HeadshotImage headshot = null;
        try {
            headshot = m_cache.get(patient);
            if (headshot != null) {
                return headshot;
            }
        } catch (Exception e) {
            //
        }
        return headshot;
    }

    public void add(Integer id, HeadshotImage hs) {
        if (m_cache.containsKey(id) == false) {
            m_cache.put(id, hs);
        }
    }

    public void remove(Integer id) {
        m_cache.remove(id);
    }
}
