package uz.driver.commands.label;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;
import uz.driver.exceptions.LabelParserException;

import static uz.driver.DriverConstant.*;
import static uz.driver.commands.label.TSPLLabelUtils.hasFloatDecimals;

@Data
@Builder
public class Text implements TSPLCommand {

    private Integer xCoordinate;
    private Integer yCoordinate;
    private String fontName;
    private BarcodeRotation rotation;
    private Float xMultiplicationFactor;
    private Float yMultiplicationFactor;
    private BarcodeAlignment alignment;
    private String content;


    @Override
    public String getCommand() {
        if (xCoordinate == null || yCoordinate == null) {
            throw new LabelParserException("TEXT: x and y positions are required");
        }

        if (xMultiplicationFactor != null && (xMultiplicationFactor <= 0
                || xMultiplicationFactor > 10)) {
            throw new LabelParserException("TEXT: Available xMultiplication factors: 1~10");
        }

        if (yMultiplicationFactor != null && (yMultiplicationFactor <= 0
                || yMultiplicationFactor > 10)) {
            throw new LabelParserException("TEXT: Available yMultiplication factors: 1~10");
        }

        if (content == null) {
            throw new LabelParserException("TEXT: content is required");
        }

        StringBuilder commandBuilder = new StringBuilder(LabelFormatCommand.TEXT.name());
        commandBuilder.append(EMPTY_SPACE)
                .append(xCoordinate).append(COMMA)
                .append(yCoordinate).append(COMMA)
                .append(ESCAPED_DOUBLE_QUOTE).append(fontName).append(ESCAPED_DOUBLE_QUOTE)
                .append(COMMA);

        if (rotation != null) {
            commandBuilder.append(rotation.getRotation()).append(COMMA);
        }

        if (!hasFloatDecimals(xMultiplicationFactor)) {
            commandBuilder.append(xMultiplicationFactor.intValue());
        } else {
            commandBuilder.append(xMultiplicationFactor);
        }

        commandBuilder.append(COMMA);



        if (!hasFloatDecimals(yMultiplicationFactor)) {
            commandBuilder.append(yMultiplicationFactor.intValue());
        } else {
            commandBuilder.append(yMultiplicationFactor);
        }

        commandBuilder.append(COMMA);

        if (alignment != null) {
            commandBuilder.append(alignment.getAlignment()).append(COMMA);
        }

        commandBuilder.append(ESCAPED_DOUBLE_QUOTE).append(content).append(ESCAPED_DOUBLE_QUOTE)
                .append(LF);

        return commandBuilder.toString();
    }

}
