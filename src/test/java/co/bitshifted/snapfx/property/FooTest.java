package co.bitshifted.snapfx.property;

public class FooTest {
    private String stringValue;
    private int intValue;
    private boolean boolValue;
    private double doubleValue;
    private float floatValue;
    private BarTest barTest;

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
}
