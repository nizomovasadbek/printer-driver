package uz.driver.commands.system;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;
import uz.driver.exceptions.LabelParserException;

import static uz.driver.DriverConstant.*;
import static uz.driver.commands.system.SystemCommand.PRINT;

@Data
@Builder
public class Print implements TSPLCommand {
    private Integer nbLabels;
    private Integer nbCopies;

    @Override
    public String getCommand() {
        if (nbLabels == null) {
            throw new LabelParserException("ParseException PRINT Command: "
                    + "number of sets of labels is required");
        }

        StringBuilder commandBuilder = new StringBuilder(PRINT.name());
        commandBuilder.append(EMPTY_SPACE)
                .append(nbLabels);

        if (nbCopies != null) {
            commandBuilder.append(COMMA)
                    .append(nbCopies);
        }

        commandBuilder.append(LF);

        return commandBuilder.toString();
    }
}
