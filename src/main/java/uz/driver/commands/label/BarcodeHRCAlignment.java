package uz.driver.commands.label;

public enum BarcodeHRCAlignment {

    NO_HRC_DISPLAY(0),
    HRC_ALIGN_LEFT(1),
    HRC_ALIGN_CENTER(2),
    HRC_ALIGN_RIGHT(3);

    private int alignment;

    BarcodeHRCAlignment(int alignment){
        this.alignment = alignment;
    }

    public int getAlignment(){
        return alignment;
    }

}
