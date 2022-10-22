package uz.driver.commands.label;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;
import uz.driver.exceptions.LabelParserException;

import static uz.driver.DriverConstant.*;
import static uz.driver.commands.label.LabelFormatCommand.ELLIPSE;

@Data
@Builder
public class Ellipse implements TSPLCommand {

    private Integer xCoordinate;
    private Integer yCoordinate;
    private Integer width;
    private Integer height;
    private Integer lineThickness;

    @Override
    public String getCommand() {
        if (xCoordinate == null || yCoordinate == null) {
            throw new LabelParserException("ELLIPSE: x and y positions are required");
        }

        if (width == null) {
            throw new LabelParserException("ELLIPSE: please specify the width");
        }

        if (height == null) {
            throw new LabelParserException("ELLIPSE: please specify the height");
        }

        if (lineThickness == null) {
            throw new LabelParserException("ELLIPSE: please specify line thickness");
        }

        StringBuilder commandBuilder = new StringBuilder(ELLIPSE.name());
        commandBuilder.append(EMPTY_SPACE)
                .append(xCoordinate).append(COMMA)
                .append(yCoordinate).append(COMMA)
                .append(width).append(COMMA)
                .append(height).append(COMMA)
                .append(lineThickness)
                .append(LF);

        return commandBuilder.toString();
    }

}
