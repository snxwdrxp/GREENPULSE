package com.example.appli_mobile;

public class Appliance {
    private String name;
    private int wattage;
    private boolean isChecked;

    public Appliance(String name, int wattage) {
        this.name = name;
        this.wattage = wattage;
        this.isChecked = false;
    }

    public String getName() {
        return name;
    }

    public int getWattage() {
        return wattage;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}


