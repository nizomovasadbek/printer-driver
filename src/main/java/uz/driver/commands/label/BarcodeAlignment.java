package uz.driver.commands.label;

public enum BarcodeAlignment {

    DEFAULT_LEFT(0),
    LEFT(1),
    CENTER(2),
    RIGHT(3);


    private int alignment;
    BarcodeAlignment(int alignment){
        this.alignment = alignment;
    }

    public int getAlignment(){
        return alignment;
    }

}
