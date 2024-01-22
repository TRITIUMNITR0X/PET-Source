package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data;

public class MapCarouselData {

    private MapSizeData[] mapSizeData;

    private int mapCurrent = 0;

    public MapCarouselData() {
    }

    public void setMapCurrentIndex(int index) {
        this.mapCurrent = index;
    }

    public void setMapSizeData(String[] allNames, int[] allSizes) {
        if (allNames.length == allSizes.length) {
            mapSizeData = new MapSizeData[allSizes.length];
            for (int i = 0; i < allNames.length; i++) {
                mapSizeData[i] = new MapSizeData(allNames[i], allSizes[i]);
            }
        }
    }

    public int getMapCurrentIndex() {
        return mapCurrent;
    }

    public int getMapCount() {
        if (mapSizeData == null) {
            return 0;
        }
        return mapSizeData.length;
    }

    public String getMapCurrentName() {
        if (mapSizeData != null) {
            return mapSizeData[getMapCurrentIndex()].name();
        }
        return "???";
    }

    public int getMapCurrentSize() {
        if (mapSizeData != null) {
            return mapSizeData[getMapCurrentIndex()].size();
        }
        return 1;
    }


    public boolean hasMapSizeData() {
        return mapSizeData == null;
    }

}
