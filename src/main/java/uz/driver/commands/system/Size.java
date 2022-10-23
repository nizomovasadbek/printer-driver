package uz.driver.commands.system;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;
import uz.driver.exceptions.LabelParserException;

import static uz.driver.DriverConstant.*;
import static uz.driver.commands.label.TSPLLabelUtils.hasFloatDecimals;

@Data
@Builder
public class Size implements TSPLCommand {

    private Float labelWidth;
    private Float labelLength;

    @Builder.Default
    private MeasurementSystem sizeMeasurementSystem = MeasurementSystem.ENGLISH;

    @Override
    public String getCommand() {
        if (labelWidth == null || labelLength == null) {
            throw new LabelParserException("ParseException SIZE Command: "
                    + "label width and label length should be specified");
        }

        StringBuilder commandBuilder = new StringBuilder(SystemCommand.SIZE.name());
        commandBuilder.append(EMPTY_SPACE);

        if (!hasFloatDecimals(labelWidth)) {
            commandBuilder.append(labelWidth.intValue());
        } else {
            commandBuilder.append(labelWidth);
        }

        if (sizeMeasurementSystem == MeasurementSystem.METRIC) {
            commandBuilder.append(EMPTY_SPACE).append(UNIT_MM);
        } else if (sizeMeasurementSystem == MeasurementSystem.DOT) {
            commandBuilder.append(EMPTY_SPACE).append("dot");
        }

        commandBuilder.append(COMMA);

        if (!hasFloatDecimals(labelLength)) {
            commandBuilder.append(labelLength.intValue());
        } else {
            commandBuilder.append(labelLength);
        }

        if (sizeMeasurementSystem == MeasurementSystem.METRIC) {
            commandBuilder.append(EMPTY_SPACE).append(UNIT_MM);
        } else if (sizeMeasurementSystem == MeasurementSystem.DOT) {
            commandBuilder.append(EMPTY_SPACE).append("dot");
        }

        commandBuilder.append(LF);

        return commandBuilder.toString();
    }

}
