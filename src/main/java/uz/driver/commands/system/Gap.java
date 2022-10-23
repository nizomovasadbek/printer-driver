package uz.driver.commands.system;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;
import uz.driver.exceptions.LabelParserException;

import static uz.driver.DriverConstant.*;
import static uz.driver.commands.label.TSPLLabelUtils.hasFloatDecimals;

@Data
@Builder
public class Gap implements TSPLCommand {
    private Float labelDistance;
    private Float labelOffsetDistance;

    @Builder.Default
    private MeasurementSystem measurementSystem = MeasurementSystem.ENGLISH;

    @Override
    public String getCommand() {
        if (labelDistance == null && labelOffsetDistance != null) {
            throw new LabelParserException("ParseException GAP Command: "
                    + "label distance and label offset should be specified");
        }

        StringBuilder commandBuilder = new StringBuilder(SystemCommand.GAP.name());
        commandBuilder.append(EMPTY_SPACE);

        if (!hasFloatDecimals(labelDistance)) {
            commandBuilder.append(labelDistance.intValue());
        } else {
            commandBuilder.append(labelDistance);
        }

        if (measurementSystem == MeasurementSystem.METRIC) {
            commandBuilder.append(EMPTY_SPACE).append(UNIT_MM);
        } else if (measurementSystem == MeasurementSystem.DOT) {
            commandBuilder.append(EMPTY_SPACE).append("dot");
        }

        commandBuilder.append(COMMA);

        if (!hasFloatDecimals(labelOffsetDistance)) {
            commandBuilder.append(labelOffsetDistance.intValue());
        } else {
            commandBuilder.append(labelOffsetDistance);
        }

        if (measurementSystem == MeasurementSystem.METRIC) {
            commandBuilder.append(EMPTY_SPACE).append(UNIT_MM);
        } else if (measurementSystem == MeasurementSystem.DOT) {
            commandBuilder.append(EMPTY_SPACE).append("dot");
        }

        commandBuilder.append(LF);

        return commandBuilder.toString();
    }
}
