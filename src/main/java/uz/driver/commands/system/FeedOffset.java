package uz.driver.commands.system;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;
import uz.driver.exceptions.LabelParserException;

import static uz.driver.DriverConstant.EMPTY_SPACE;
import static uz.driver.DriverConstant.UNIT_MM;
import static uz.driver.DriverConstant.LF;
import static uz.driver.commands.label.TSPLLabelUtils.hasFloatDecimals;

@Data
@Builder
public class FeedOffset implements TSPLCommand {

    private Float offsetDistance;

    @Override
    public String getCommand() {
        if (offsetDistance == null) {
            throw new LabelParserException("ParseException OFFSET Command: offset can't be empty");
        }

        StringBuilder commandBuilder = new StringBuilder(SystemCommand.OFFSET.name());
        commandBuilder.append(EMPTY_SPACE);

        if (!hasFloatDecimals(offsetDistance)) {
            commandBuilder.append(offsetDistance.intValue());
        } else {
            commandBuilder.append(offsetDistance);
        }

        commandBuilder.append(EMPTY_SPACE).append(UNIT_MM)
                .append(LF);

        return commandBuilder.toString();
    }

}
