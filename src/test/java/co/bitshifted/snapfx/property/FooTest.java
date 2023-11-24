package co.bitshifted.snapfx.property;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FooTest {
    private String stringValue;
    private int intValue;
    private boolean boolValue;
    private double doubleValue;
    private float floatValue;
    private BarTest barTest;
    private List<String> listValue;
    private Set<String> setValue;
    private Map<Integer,String> mapValue;

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public void setBoolValue(boolean boolValue) {
        this.boolValue = boolValue;
    }

    public boolean isBoolValue() {
        return boolValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setBarTest(BarTest barTest) {
        this.barTest = barTest;
    }

    public BarTest getBarTest() {
        return barTest;
    }

    public List<String> getListValue() {
        return listValue;
    }

    public void setListValue(List<String> listValue) {
        this.listValue = listValue;
    }

    public Set<String> getSetValue() {
        return setValue;
    }

    public void setSetValue(Set<String> setValue) {
        this.setValue = setValue;
    }

    public Map<Integer, String> getMapValue() {
        return mapValue;
    }

    public void setMapValue(Map<Integer, String> mapValue) {
        this.mapValue = mapValue;
    }
}
