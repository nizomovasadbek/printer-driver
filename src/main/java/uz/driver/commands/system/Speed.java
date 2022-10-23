package uz.driver.commands.system;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;
import uz.driver.exceptions.LabelParserException;

import static uz.driver.DriverConstant.*;
import static uz.driver.commands.label.TSPLLabelUtils.hasFloatDecimals;

@Data
@Builder
public class Speed implements TSPLCommand {

    private Float printSpeed;

    @Override
    public String getCommand() {
        if (printSpeed == null) {
            throw new LabelParserException("ParseException SPEED Command: speed can't be empty");
        }

        StringBuilder commandBuilder = new StringBuilder(SystemCommand.SPEED.name());
        commandBuilder.append(EMPTY_SPACE);

        if (!hasFloatDecimals(printSpeed)) {
            commandBuilder.append(printSpeed.intValue());
        } else {
            commandBuilder.append(printSpeed);
        }

        commandBuilder.append(LF);

        return commandBuilder.toString();
    }

}
