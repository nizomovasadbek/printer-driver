package uz.driver.commands.label;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;
import uz.driver.exceptions.LabelParserException;

import static uz.driver.DriverConstant.*;
import static uz.driver.commands.label.LabelFormatCommand.DMATRIX;

@Data
@Builder
public class DataMatrix implements TSPLCommand {

    private Integer xCoordinate;
    private Integer yCoordinate;
    private Integer width;
    private Integer height;
    private Integer escapeSequenceCharacter;
    private Integer moduleSize;
    private BarcodeRotation rotation;
    private boolean isRectangle;
    private Integer nbRows;
    private Integer nbCols;
    private String content;

    @Override
    public String getCommand() {
        if (xCoordinate == null || yCoordinate == null) {
            throw new LabelParserException("DMATRIX: x and y co-ordinates are required");
        }

        if (width == null) {
            throw new LabelParserException("DMATRIX: please specify the width");
        }

        if (height == null) {
            throw new LabelParserException("DMATRIX: please specify height");
        }

        if (content == null) {
            throw new LabelParserException("DMATRIX: content is required");
        }

        StringBuilder dataMatrix = new StringBuilder(DMATRIX.name());
        dataMatrix.append(EMPTY_SPACE)
                .append(xCoordinate).append(COMMA)
                .append(yCoordinate).append(COMMA)
                .append(width).append(COMMA)
                .append(height).append(COMMA);
        if (escapeSequenceCharacter != null) {
            dataMatrix.append("c").append(escapeSequenceCharacter).append(COMMA);
        }

        if (moduleSize != null) {
            dataMatrix.append("x").append(moduleSize).append(COMMA);
        }

        if (rotation != null) {
            dataMatrix.append("r").append(rotation.getRotation()).append(COMMA);
        }

        if (isRectangle) {
            dataMatrix.append("a").append(isRectangle ? 1 : 0).append(COMMA);
        }

        if (nbRows != null) {
            dataMatrix.append(nbRows).append(COMMA);
        }

        if (nbCols != null) {
            dataMatrix.append(nbCols).append(COMMA);
        }

        dataMatrix.append(EMPTY_SPACE)
                .append(ESCAPED_DOUBLE_QUOTE).append(content).append(ESCAPED_DOUBLE_QUOTE)
                .append(LF);

        return dataMatrix.toString();
    }

}
