package uz.driver.commands.label;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;

@Data
@Builder
public class CodaBlockF implements TSPLCommand {

    private Float xCoordinate;
    private Float yCoordinate;
    private BarcodeRotation rotation;
    private Integer rowHeight;
    private Integer moduleWidth;
    private String content;

    @Override
    public String getCommand() {
        if (xCoordinate == null || yCoordinate == null) {
            throw new LabelParserException("CODABLOCK: x and y co-ordinates are required");
        }

        if (rotation == null) {
            throw new LabelParserException("CODABLOCK: please specify a rotation");
        }

        StringBuilder commandBuilder = new StringBuilder(LabelFormatCommand.CODABLOCK.name());
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
                .append(rotation.getRotation()).append(COMMA);

        if (rowHeight != null) {
            commandBuilder.append(rowHeight).append(COMMA);
        }

        if (moduleWidth != null) {
            commandBuilder.append(moduleWidth).append(COMMA);
        }

        commandBuilder.append(EMPTY_SPACE)
                .append(ESCAPED_DOUBLE_QUOTE).append(content).append(ESCAPED_DOUBLE_QUOTE)
                .append(LF);

        return commandBuilder.toString();
    }
}

}
