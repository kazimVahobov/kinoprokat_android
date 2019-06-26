package com.example.kinoprokat.models;

import java.util.ArrayList;
import java.util.List;

public class Theater {

    private String _id;
    private String distId;
    private String name;
    private List<Hole> holes = new ArrayList<>();
    private String regionId;

    public String get_id() {
        return _id;
    }

    public String getDistId() {
        return distId;
    }

    public String getName() {
        return name;
    }

    public List<Hole> getHoles() {
        return holes;
    }

    public String getRegionId() {
        return regionId;
    }

    public String getHoleId(int i) {
        return holes.get(i).get_id();
    }

    public String getHoleName(int i) {
        return holes.get(i).getName();
    }

    public int getHolePlaceCount(int i) {
       return holes.get(i).getPlaceCount();
    }

    public int getHolesCount() {
        return holes.size();
    }

}
