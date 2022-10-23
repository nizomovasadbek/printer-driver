package uz.driver.commands.system;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;
import uz.driver.exceptions.LabelParserException;

import static uz.driver.DriverConstant.*;
import static uz.driver.commands.system.SystemCommand.DIRECTION;

@Data
@Builder
public class Direction implements TSPLCommand {
    private Boolean printPositionAsFeed;
    private Boolean printMirrorImage;

    @Override
    public String getCommand() {
        if (printPositionAsFeed == null) {
            throw new LabelParserException("ParseException Direction Command: print "
                    + "position must be set");
        }

        StringBuilder commandBuilder = new StringBuilder(DIRECTION.name());
        commandBuilder.append(EMPTY_SPACE)
                .append(printPositionAsFeed ? "1" : "0");

        if (printMirrorImage != null) {
            commandBuilder.append(COMMA)
                    .append(printMirrorImage ? "1" : "0");
        }

        commandBuilder.append(LF);

        return commandBuilder.toString();
    }
}
