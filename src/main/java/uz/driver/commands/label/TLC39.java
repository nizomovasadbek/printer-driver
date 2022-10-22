package uz.driver.commands.label;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;
import uz.driver.exceptions.LabelParserException;

import static uz.driver.DriverConstant.*;

@Data
@Builder
public class TLC39 implements TSPLCommand {

    private Integer xCoordinate;
    private Integer yCoordinate;
    private BarcodeRotation rotation;
    private Integer height;
    private Integer narrow;
    private Integer wide;
    private Integer cellWidth;
    private Integer cellHeight;
    private Integer eciNumber;
    private String serialNumber;
    private String additionalData;

    @Override
    public String getCommand() {

        if (xCoordinate == null || yCoordinate == null) {
            throw new LabelParserException("TLC39: x and y positions are required");
        }

        if (rotation == null) {
            throw new LabelParserException("TLC39: please specify rotation");
        }

        if (narrow != null && height == null) {
            throw new LabelParserException("TLC39: height must be specified with narrow");
        }

        if (wide != null && (height == null || narrow == null)) {
            throw new LabelParserException("TLC39: height and narrow must be specified with wide");
        }

        if (cellWidth != null && (wide == null || height == null
                || narrow == null)) {
            throw new LabelParserException("TLC39: height, narrow and wide must be "
                    + "specified with cellWidth");
        }

        if (cellHeight != null && (cellWidth == null || wide == null
                || height == null || narrow == null)) {
            throw new LabelParserException("TLC39: height, narrow, wide and cellWidth "
                    + "must be specified with cellHeight");
        }

        if (eciNumber == null || eciNumber.toString().length() != 6) {
            throw new LabelParserException("TLC39: ECI number is required and must be 6 digits");
        }

        if (serialNumber == null) {
            throw new LabelParserException("TLC39: Serial number is required");
        }

        StringBuilder commandBuilder = new StringBuilder(LabelFormatCommand.TLC39.name());
        commandBuilder.append(EMPTY_SPACE)
                .append(xCoordinate).append(COMMA)
                .append(yCoordinate).append(COMMA)
                .append(rotation.getRotation()).append(COMMA);

        if (height != null) {
            commandBuilder.append(height).append(COMMA);
        }

        if (narrow != null) {
            commandBuilder.append(narrow).append(COMMA);
        }

        if (wide != null) {
            commandBuilder.append(wide).append(COMMA);
        }

        if (cellWidth != null) {
            commandBuilder.append(cellWidth).append(COMMA);
        }

        if (cellHeight != null) {
            commandBuilder.append(cellHeight).append(COMMA);
        }

        commandBuilder.append(ESCAPED_DOUBLE_QUOTE)
                .append(eciNumber).append(COMMA)
                .append(serialNumber);

        if (additionalData != null) {
            commandBuilder.append(COMMA)
                    .append(additionalData);
        }

        commandBuilder.append(ESCAPED_DOUBLE_QUOTE)
                .append(LF);

        return commandBuilder.toString();
    }

}
