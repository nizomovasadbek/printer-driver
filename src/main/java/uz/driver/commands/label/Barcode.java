package uz.driver.commands.label;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;
import static uz.driver.DriverConstant.*;
import static uz.driver.commands.label.TSPLLabelUtils.*;

@Data
@Builder
public class Barcode implements TSPLCommand {

    private Float xCoordinate;
    private Float yCoordinate;
    private Integer height;
    private BarcodeType codeType;
    private BarcodeHRCAlignment hrcAlignment;
    private BarcodeRotation rotation;
    private Integer narrow;
    private Integer wide;
    private BarcodeAlignment alignment;
    private String content;

    @Override
    public String getCommand() {
        if (xCoordinate == null || yCoordinate == null) {
            System.err.println("BARCODE: x and y positions are required");
        }

        if (codeType == null) {
            System.err.println("BARCODE: codeType is required");
        }

        if (content == null || content.trim().length() == 0) {
            System.err.println("BARCODE: content is required");
        }

        if (hrcAlignment == null) {
            hrcAlignment = BarcodeHRCAlignment.NO_HRC_DISPLAY;
        }

        if (rotation == null) {
            rotation = BarcodeRotation.NO_ROTATION;
        }

        StringBuilder commandBuilder = new StringBuilder(LabelFormatCommand.BARCODE.name());
        commandBuilder.append(EMPTY_SPACE);

        if (!hasFloatDecimals(xCoordinate)) {
            commandBuilder.append(xCoordinate.intValue());
        } else {
            commandBuilder.append(xCoordinate);
        }

        commandBuilder.append(COMMA);

        if (!hasFloatDecimals(yCoordinate)) {
            commandBuilder.append(yCoordinate.intValue());
        } else {
            commandBuilder.append(yCoordinate);
        }

        commandBuilder.append(COMMA)
                .append(EMPTY_SPACE)
                .append(ESCAPED_DOUBLE_QUOTE)
                .append(codeType.getCodeType()).append(ESCAPED_DOUBLE_QUOTE).append(COMMA)
                .append(height).append(COMMA)
                .append(hrcAlignment.getAlignment()).append(COMMA)
                .append(rotation.getRotation()).append(COMMA)
                .append(narrow).append(COMMA)
                .append(wide).append(COMMA);

        if (alignment != null) {
            commandBuilder.append(alignment.getAlignment()).append(COMMA);
        }

        commandBuilder.append(ESCAPED_DOUBLE_QUOTE).append(content).append(ESCAPED_DOUBLE_QUOTE)
                .append(LF);

        return commandBuilder.toString();
    }
}
