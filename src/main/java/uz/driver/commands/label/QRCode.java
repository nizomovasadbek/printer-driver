package uz.driver.commands.label;

import uz.driver.commands.TSPLCommand;

public class QRCode implements TSPLCommand {

    private Integer xCoordinate;
    private Integer yCoordinate;
    private ErrorCorrectionLevel errorCorrectionLevel;
    private Integer cellWidth;
    private QREncodeMode mode;
    private BarcodeRotation rotation;
    private Integer justification;
    private QRModel model;
    private QRMask mask;
    private Integer area;
    private String content;

}
