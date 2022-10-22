package uz.driver.commands.label;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;
import uz.driver.exceptions.LabelParserException;

import static uz.driver.DriverConstant.*;

@Data
@Builder
public class MaxiCode implements TSPLCommand {
    private Integer xCoordinate;
    private Integer yCoordinate;
    private Integer mode;
    private Integer serviceClass;
    private Integer countryCode;
    private String postCode;
    private Integer expressionLength;
    private String content;

    @Override
    public String getCommand() {

        if (xCoordinate == null || yCoordinate == null) {
            throw new LabelParserException("MAXICODE: x and y positions are required");
        }

        if (mode == null) {
            throw new LabelParserException("MAXICODE: please specify a mode");
        }

        if ((mode == 2 || mode == 3)
                && (serviceClass == null || countryCode == null || postCode == null)) {
            throw new LabelParserException("MAXICODE: class, country and post must "
                    + "be specified for mode 2 and 3");
        }

        if (mode < 2 || mode > 5) {
            throw new LabelParserException("MAXICODE: mode must be 2,3,4 or 5");
        }

        if (mode == 2 && countryCode == 840 && postCode.length() != 10) {
            throw new LabelParserException("MAXICODE: post for USA is in 99999,9999 format");
        }

        if (mode == 3 && postCode.length() != 6) {
            throw new LabelParserException("MAXICODE: post for mode is 6 alphanumeric");
        }

        StringBuilder commandBuilder = new StringBuilder(LabelFormatCommand.MAXICODE.name());
        commandBuilder.append(EMPTY_SPACE)
                .append(xCoordinate).append(COMMA)
                .append(yCoordinate).append(COMMA)
                .append(mode).append(COMMA);

        if (mode == 2 || mode == 3) {
            commandBuilder.append(serviceClass).append(COMMA)
                    .append(countryCode).append(COMMA);

            if (mode == 3) {
                commandBuilder.append(ESCAPED_DOUBLE_QUOTE);
            }
            commandBuilder.append(postCode);

            if (mode == 3) {
                commandBuilder.append(ESCAPED_DOUBLE_QUOTE);
            }
            commandBuilder.append(COMMA)
                    .append(ESCAPED_DOUBLE_QUOTE)
                    .append(content).append(ESCAPED_DOUBLE_QUOTE);
        } else {
            if (expressionLength == null) {
                commandBuilder.append(ESCAPED_DOUBLE_QUOTE);
            } else {
                commandBuilder.append("L").append(expressionLength)
                        .append(COMMA);
            }

            commandBuilder.append(content);

            if (expressionLength == null) {
                commandBuilder.append(ESCAPED_DOUBLE_QUOTE);
            }
        }

        commandBuilder.append(LF);

        return commandBuilder.toString();
    }
}
