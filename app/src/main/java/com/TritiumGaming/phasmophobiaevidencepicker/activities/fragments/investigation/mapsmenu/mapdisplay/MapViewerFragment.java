
package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.data.MapData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.views.InteractiveMapView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MapMenuViewModel;

/*
 * MapViewerFragment class
 *
 * @author TritiumGamingStudios
 */
public class MapViewerFragment extends Fragment {

    private MapMenuViewModel mapViewViewModel;

    private InteractiveMapView imageDisplay;

    private MapLayerSelectorGroup selectorGroup;
    private AppCompatTextView layerName;

    /*
     *
     * MapViewerFragment constructor
     */
    public MapViewerFragment() {
        super(R.layout.fragment_mapview);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        if (mapViewViewModel == null) {
            mapViewViewModel = new ViewModelProvider(requireActivity()).get(MapMenuViewModel.class);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LinearLayout selectorLayout = view.findViewById(R.id.linearlayout_floorindicators);

        AppCompatImageButton button_nextLayer = view.findViewById(R.id.controller_nextLayerButton);
        AppCompatImageButton button_prevLayer = view.findViewById(R.id.controller_prevLayerButton);

        // INITIALIZE VIEWS
        AppCompatTextView label_goto_left = view.findViewById(R.id.label_goto_left);
        AppCompatImageView icon_goto_left = view.findViewById(R.id.icon_goto_left);
        View listener_goto_left = view.findViewById(R.id.listener_goto_left);

        AppCompatTextView mapName = view.findViewById(R.id.textview_title);

        imageDisplay = view.findViewById(R.id.interactiveMapView);

        layerName = view.findViewById(R.id.textview_floorname);


        mapViewViewModel.getCurrentMapModel().setCurrentLayer(
                mapViewViewModel.getCurrentMapModel().getFloor(
                        mapViewViewModel.getCurrentMapData().getCurrentFloor())
                        .getFloorLayer());

        // SET NAVIGATION ITEMS
        label_goto_left.setText(R.string.general_maps_button);

        button_nextLayer.setOnClickListener(v -> {
            if (mapViewViewModel != null && mapViewViewModel.hasMapData()) {

                int layerIndex = mapViewViewModel.getCurrentMapData().getCurrentFloor();
                if (++layerIndex >= mapViewViewModel.getCurrentMapData().getFloorCount()) {
                    layerIndex = 0;
                }

                MapData d;
                if ((d = mapViewViewModel.getCurrentMapData()) != null) {
                    d.setCurrentFloor(layerIndex);

                    imageDisplay.resetRoomSelection();
                    mapViewViewModel.getCurrentMapModel().setCurrentLayer(
                        mapViewViewModel.getCurrentMapModel().getFloor(layerIndex).getFloorLayer());

                    Log.d("Maps", mapViewViewModel.getCurrentMapModel().getCurrentFloor().getFloorName());
                }

                updateComponents();
            }
        });

        button_prevLayer.setOnClickListener(v -> {
            if (mapViewViewModel != null && mapViewViewModel.hasMapData()) {
                int layerIndex = mapViewViewModel.getCurrentMapData().getCurrentFloor();
                if (--layerIndex < 0) {
                    layerIndex = mapViewViewModel.getCurrentMapData().getFloorCount() - 1;
                }

                MapData d;
                if ((d = mapViewViewModel.getCurrentMapData()) != null) {
                    d.setCurrentFloor(layerIndex);

                    imageDisplay.resetRoomSelection();
                    mapViewViewModel.getCurrentMapModel().setCurrentLayer(
                            mapViewViewModel.getCurrentMapModel().getFloor(layerIndex).getFloorLayer());
                    Log.d("Maps", mapViewViewModel.getCurrentMapModel().getCurrentFloor().getFloorName());
                }

                updateComponents();
            }
        });

        listener_goto_left.setOnClickListener(v -> {
            saveStates();
            Navigation.findNavController(v).popBackStack();
        });

        // LISTENERS
        initNavListeners(
                listener_goto_left,
                null,
                null,
                null,
                null,
                icon_goto_left,
                null,
                null,

                null,
                null);


        if (getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        }

        /*
        if (imageDisplay != null) {
            imageDisplay.init(controllerData);
        }

        if (touchInput != null) {
            touchInput.init(controllerData, imageDisplay);
        }
        */
        imageDisplay.init(mapViewViewModel);

        if (mapViewViewModel != null && imageDisplay != null) {
            imageDisplay.setMapData(mapViewViewModel.getCurrentMapData());

            MapData tempData = mapViewViewModel.getCurrentMapData();
            if (tempData != null) {
                selectorGroup = new MapLayerSelectorGroup(tempData.getFloorCount());
                for (int i = 0; i < selectorGroup.getSize(); i++) {
                    selectorLayout.addView(selectorGroup.getSelectors()[i]);
                }
                mapName.setText(tempData.getMapName());
            }
        }

        startThreads();
        updateComponents();
    }

    private void initNavListeners(View lstnr_navLeft,
                                  View lstnr_navMedLeft,
                                  View lstnr_navCenter,
                                  View lstnr_navMedRight,
                                  View lstnr_navRight,
                                  AppCompatImageView icon_navLeft,
                                  AppCompatImageView icon_navMedLeft,
                                  AppCompatImageView icon_navCenter,
                                  AppCompatImageView icon_navMedRight,
                                  AppCompatImageView icon_navRight) {
        if (lstnr_navLeft != null) {
            ((View) lstnr_navLeft.getParent()).setVisibility(View.VISIBLE);
            icon_navLeft.setImageResource(R.drawable.icon_evidence);
            lstnr_navLeft.setOnClickListener(v -> Navigation.findNavController(v)
                    .popBackStack()
            );
        }

        if (lstnr_navMedLeft != null) {
        }

        if (lstnr_navCenter != null) {
        }

        if (lstnr_navMedRight != null) {
        }

        if (lstnr_navRight != null) {
        }

    }

    /*
     * startThreads
     * <p>
     * Starts a Thread which loads images into the InteractiveMapDisplayView
     */
    public void startThreads() {
        stopThreads();

        if (mapViewViewModel.getImageDisplayThread() == null) {
            mapViewViewModel.setImageDisplayThread(new Thread(() -> {
                if (imageDisplay != null) {
                    imageDisplay.setMapImages(getActivity());
                    imageDisplay.setPoiImages(getActivity());
                }
            }));
            mapViewViewModel.getImageDisplayThread().start();
        }
    }

    /*
     *
     * stopThreads
     */
    public void stopThreads() {
        if (mapViewViewModel.getImageDisplayThread() != null) {
            mapViewViewModel.getImageDisplayThread().interrupt();
            mapViewViewModel.setImageDisplayThread(null);
        }
    }

    /*
     *
     * updateComponents
     */
    public void updateComponents() {
        if (mapViewViewModel != null && mapViewViewModel.hasCurrentMapData()) {
            if (selectorGroup != null) {
                selectorGroup.setSelected(mapViewViewModel.getCurrentMapData().getCurrentFloor());
            }
            if (layerName != null) {
                layerName.setText(getResources().getString(
                        mapViewViewModel.getCurrentMapData().getFloorName()));
            }
            if (imageDisplay != null) {
                imageDisplay.invalidate();
            }
        }
    }

    /*
     *
     * saveStates
     * <p>
     * Saves states of the MapViewer to the MapViewModel
     */
    public void saveStates() {
        if (mapViewViewModel != null && mapViewViewModel.hasCurrentMapData()) {
            mapViewViewModel.getCurrentMapData().setDefaultFloor(
                    mapViewViewModel.getCurrentMapData().getCurrentFloor());
        }
    }

    /*
     *
     * onPause
     * <p>
     * Destroys all threads and releases resource memory
     */
    @Override
    public void onPause() {
        stopThreads();
        if (imageDisplay != null) {
            imageDisplay.recycleBitmaps();
        }

        super.onPause();
    }

    /*
    @Override
    public void onResume() {

        Log.d("State", "Resuming");

        imageDisplay.init(mapViewViewModel);
        startThreads();
        updateComponents();

        super.onResume();
    }
    */

    /*
     *
     * onDestroy
     * <p>
     * Destroys all threads and releases resource memory
     */
    @Override
    public void onDestroy() {
        stopThreads();
        if (imageDisplay != null) {
            imageDisplay.recycleBitmaps();
        }

        super.onDestroy();
    }

    /*
     *
     * Forces garbage collection on low memory
     */
    @Override
    public void onLowMemory() {
        System.gc();

        super.onLowMemory();
    }

    /*
     *
     * LayerSelectorGroup class
     * <p>
     * The group of selectors which act to cycle between image layers
     */
    private class MapLayerSelectorGroup {

        private final MapLayerSelector[] selectors;

        /*
         *
         * LayerSelectorGroup constructor
         *
         * @param count - the total number of Selectors, based on map layers
         */
        public MapLayerSelectorGroup(int count) {
            selectors = new MapLayerSelector[count];
            for (int i = 0; i < selectors.length; i++) {
                selectors[i] = new MapLayerSelector(getContext());
            }
            if (mapViewViewModel != null) {
                setSelected(mapViewViewModel.getCurrentMapData().getCurrentFloor());
            }
        }

        /*
         *
         * setSelected
         * <p>
         * Selects a specific Selector
         *
         * @param index - the index of Selector
         */
        public void setSelected(int index) {
            deSelectAll();

            if (selectors[index] != null) {
                selectors[index].setSelected(true);
            }
        }

        /*
         *
         * deSelectAll
         * <p>
         * Deselects all Selectors
         */
        public void deSelectAll() {
            for (MapLayerSelector selector : selectors) {
                if (selector != null) {
                    selector.setSelected(false);
                }
            }
        }

        /*
         *
         * getSelectors
         *
         * @return Selector array
         */
        public MapLayerSelector[] getSelectors() {
            return selectors;
        }

        /*
         *
         * getSize
         *
         * @return number of Selectors
         */
        public int getSize() {
            if (selectors == null) {
                return 0;
            }

            return selectors.length;
        }

        /*
         *
         * Selector class
         * <p>
         * A Selector which represents the current layer of the selected map
         */
        private class MapLayerSelector extends androidx.appcompat.widget.AppCompatImageView {

            private final int[] selectorImages = new int[]{
                    R.drawable.icon_selector_unselected,
                    R.drawable.icon_selector_selected};
            private boolean isSelected = false;

            /*
             *
             * Selector constructor
             */
            public MapLayerSelector(Context context) {
                super(context);

                setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT, 1f));
                setScaleType(ImageView.ScaleType.FIT_CENTER);
                setAdjustViewBounds(true);
                setPadding(10, 2, 2, 2);
                setColorFilter(Color.WHITE);
            }

            /*
             *
             * setSelected
             *
             * @param isSelected - the state of the Selector
             *                   Sets the Selector as selected
             */
            public void setSelected(boolean isSelected) {
                this.isSelected = isSelected;

                updateImage();
            }

            /*
             *
             * updateImage
             * <p>
             * Updates the Selector icon to reflect its current selection state
             */
            private void updateImage() {
                if (selectorImages != null && selectorImages.length == 2) {
                    if (!isSelected) {
                        setImageResource(selectorImages[0]);
                    } else {
                        setImageResource(selectorImages[1]);
                    }
                }
            }

            /*
             *
             * isSelected
             *
             * @return whether or not the Selector is selected
             */
            public boolean isSelected() {
                return isSelected;
            }
        }
    }
}

