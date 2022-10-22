package uz.driver.commands.label;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;
import uz.driver.exceptions.LabelParserException;

import static uz.driver.DriverConstant.COMMA;
import static uz.driver.DriverConstant.EMPTY_SPACE;
import static uz.driver.DriverConstant.LF;

@Data
@Builder
public class Erase implements TSPLCommand {
    private Integer xCoordinate;
    private Integer yCoordinate;
    private Integer width;
    private Integer height;

    @Override
    public String getCommand() {
        if (xCoordinate == null || yCoordinate == null) {
            throw new LabelParserException("ERASE: x and y positions are required");
        }

        if (width == null) {
            throw new LabelParserException("ERASE: please specify width");
        }

        if (height == null) {
            throw new LabelParserException("ERASE: please specify height");
        }

        StringBuilder commandBuilder = new StringBuilder(LabelFormatCommand.ERASE.name());
        commandBuilder.append(EMPTY_SPACE)
                .append(xCoordinate).append(COMMA)
                .append(yCoordinate).append(COMMA)
                .append(width).append(COMMA)
                .append(height)
                .append(LF);

        return commandBuilder.toString();
    }
}
