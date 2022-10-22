package uz.driver.commands.label;

public enum BarcodeType {
    CODE_128("128"),
    CODE_128M("128M"),
    EAN128("EAN128"),
    CODE_25("CODE_25"),
    CODE_25C("CODE_25C"),
    CODE_39("CODE_39"),
    CODE_39C("CODE_39C"),
    CODE_39S("CODE_39S"),
    CODE_93("CODE_93"),
    EAN13("EAN13"),
    EAN13_2("EAN13+2"),
    EAN13_5("EAN13+5"),
    EAN8("EAN8"),
    EAN8_2("EAN8+2"),
    EAN8_5("EAN8+5"),
    CODA("CODA"),
    POST("POST"),
    UPCA("UPCA"),
    UPCA_2("UPCA+2"),
    UPCA_5("UPCA+5"),
    CPOST("CPOST"),
    MSI("MSI"),
    MSIC("MSIC"),
    PLESSEY("PLESSEY"),
    ITF14("ITF14"),
    EAN14("EAN14"),
    CODE11("11"),
    TELEPEN("TELEPEN"),
    TELEPENN("TELEPENN"),
    PLANET("PLANET"),
    CODE49("CODE49"),
    DPI("DPI"),
    DPL("DPL");

    private String codeType;

    BarcodeType(String codeType){
        this.codeType = codeType;
    }

    public String getCodeType(){
        return codeType;
    }
}
