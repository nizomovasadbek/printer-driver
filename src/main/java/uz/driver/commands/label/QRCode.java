package uz.driver.commands.label;

import uz.driver.commands.TSPLCommand;
import uz.driver.exceptions.LabelParserException;

import static uz.driver.DriverConstant.*;

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

    @Override
    public String getCommand() {

        if (xCoordinate == null || yCoordinate == null) {
            throw new LabelParserException("QRCODE: x and y positions are required");
        }

        if (errorCorrectionLevel == null) {
            throw new LabelParserException("QRCODE: please specify error correction level");
        }

        if (cellWidth == null) {
            throw new LabelParserException("QRCODE: please specify cell width in dots");
        }

        if (mode == null) {
            throw new LabelParserException("QRCODE: please specify encoding mode");
        }

        if (rotation == null) {
            throw new LabelParserException("QRCODE: please specify rotation");
        }

        StringBuilder commandBuilder = new StringBuilder(LabelFormatCommand.QRCODE.name());
        commandBuilder.append(EMPTY_SPACE)
                .append(xCoordinate).append(COMMA)
                .append(yCoordinate).append(COMMA)
                .append(errorCorrectionLevel.name()).append(COMMA)
                .append(cellWidth).append(COMMA)
                .append(mode.name()).append(COMMA)
                .append(rotation.getRotation()).append(COMMA);

        if (justification != null) {
            commandBuilder.append("J").append(justification).append(COMMA);
        }

        if (model != null) {
            commandBuilder.append(model.name()).append(COMMA);
        }

        if (mask != null) {
            commandBuilder.append(mask.name()).append(COMMA);
        }

        if (area != null) {
            commandBuilder.append("X").append(area).append(COMMA);
        }

        commandBuilder.append(ESCAPED_DOUBLE_QUOTE)
                .append(content).append(ESCAPED_DOUBLE_QUOTE)
                .append(LF);

        return commandBuilder.toString();
    }

}
