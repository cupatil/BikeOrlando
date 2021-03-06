/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.Orlando.opensource.bikeorlando;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.Orlando.opensource.bikeorlando.controller.BikeMapController;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * @author Ian Thomas <toxicbakery@gmail.com>
 */
@SuppressWarnings("WeakerAccess")
public class MapsActivity extends FragmentActivity {

    private static final double ORLANDO_LAT = 28.5383355;
    private static final double ORLANDO_LNG = -81.3792365;
    private static final int ZOOM_CITY = 11;
    private static final int ZOOM_STATE = 7;
    private static final String KEY_FIRST_RUN = "KEY_FIRST_RUN";

    private BikeMapController mapController;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(KEY_FIRST_RUN, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mapController.destroy();
    }

    private void setUpMapIfNeeded(Bundle savedInstanceState) {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
        }

        if (map != null && mapController == null) {
            mapController = new BikeMapController(this, map);

            if (savedInstanceState == null) {
                // Look at Orlando from state level
                LatLng orlandoLatLng = new LatLng(ORLANDO_LAT, ORLANDO_LNG);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(orlandoLatLng, ZOOM_STATE));

                // Animate zoom to city level
                map.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_CITY));
            }
        }
    }

}
