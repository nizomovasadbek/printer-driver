package uz.driver.commands.label;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;
import static uz.driver.DriverConstant.*;
import static uz.driver.commands.label.TSPLLabelUtils.*;

@Data
@Builder
public class Box implements TSPLCommand {

    private Integer xCoordinate;
    private Integer yCoordinate;
    private Integer xEndCoordinate;
    private Integer yEndCoordinate;
    private Integer lineThickness;
    private Float radius;

    @Override
    public String getCommand() {
        StringBuilder commandBuilder = new StringBuilder(LabelFormatCommand.BOX.name());
        commandBuilder.append(EMPTY_SPACE)
                .append(xCoordinate).append(COMMA)
                .append(yCoordinate).append(COMMA)
                .append(xEndCoordinate).append(COMMA)
                .append(yEndCoordinate).append(COMMA)
                .append(lineThickness);

        if (radius != null) {
            commandBuilder.append(COMMA);
            if (!hasFloatDecimals(radius)) {
                commandBuilder.append(radius.intValue());
            } else {
                commandBuilder.append(radius);
            }
        }

        commandBuilder.append(LF);

        return commandBuilder.toString();
    }
}
