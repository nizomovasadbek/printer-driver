package uz.driver.commands.label;

public enum BarcodeRotation {
    NO_ROTATION(0),
    DEGREES_90(90),
    DEGRESS_180(180),
    DEGRESS_270(270);

    private int rotation;

    BarcodeRotation(int rotation){
        this.rotation = rotation;
    }

    public int getRotation(){
        return rotation;
    }
}
