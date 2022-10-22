package uz.driver.commands.label;

import uz.driver.commands.TSPLCommand;

public class AztecBarcode implements TSPLCommand {

    private Integer xCoordinate;
    private Integer yCoordinate;
    private BarcodeRotation rotation;
    private Integer moduleSize;
    private Integer errorControl;
    private Boolean escapeFlag;

    @Override
    public String getCommand() {
        return "";
    }
}
