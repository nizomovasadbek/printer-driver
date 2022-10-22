package uz.driver.commands.label;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;
import uz.driver.exceptions.LabelParserException;

import java.util.Map;

import static uz.driver.DriverConstant.*;

@Data
@Builder
public class PDF417 implements TSPLCommand {

    private Integer xCoordinate;
    private Integer yCoordinate;
    private Integer width;
    private Integer height;
    private BarcodeRotation rotation;
    private Map<String, Object> options;
    private String content;

    @Override
    public String getCommand() {
        if (xCoordinate == null || yCoordinate == null) {
            throw new LabelParserException("PDF417: x and y positions are required");
        }

        if (width == null) {
            throw new LabelParserException("PDF417: please specify width");
        }

        if (height == null) {
            throw new LabelParserException("PDF417: please specify height");
        }

        if (rotation == null) {
            throw new LabelParserException("PDF417: please specify rotation");
        }

        if (content == null) {
            throw new LabelParserException("PDF417: please specify content");
        }

        if (content.length() > 2048) {
            throw new LabelParserException("PDF417: content is more than 2048 characters");
        }

        StringBuilder commandBuilder = new StringBuilder(LabelFormatCommand.PDF417.name());
        commandBuilder.append(EMPTY_SPACE)
                .append(xCoordinate).append(COMMA)
                .append(yCoordinate).append(COMMA)
                .append(width).append(COMMA)
                .append(height).append(COMMA)
                .append(rotation.getRotation()).append(COMMA);

        if (options != null) {
            if (options.containsKey("P")) {
                commandBuilder.append("P").append(((Integer) options.get("P")).intValue())
                        .append(COMMA);
            }

            if (options.containsKey("E")) {
                commandBuilder.append("E").append(((Integer) options.get("E")).intValue())
                        .append(COMMA);
            }

            if (options.containsKey("M")) {
                commandBuilder.append("M").append(((Integer) options.get("M")).intValue())
                        .append(COMMA);
            }

            if (options.containsKey("U")) {
                commandBuilder.append("U").append(options.get("U"))
                        .append(COMMA);
            }

            if (options.containsKey("W")) {
                commandBuilder.append("W").append(((Integer) options.get("W")).intValue())
                        .append(COMMA);
            }

            if (options.containsKey("H")) {
                commandBuilder.append("H").append(((Integer) options.get("H")).intValue())
                        .append(COMMA);
            }

            if (options.containsKey("R")) {
                commandBuilder.append("R").append(((Integer) options.get("R")).intValue())
                        .append(COMMA);
            }

            if (options.containsKey("C")) {
                commandBuilder.append("C").append(((Integer) options.get("C")).intValue())
                        .append(COMMA);
            }

            if (options.containsKey("T")) {
                commandBuilder.append("T").append(((Integer) options.get("T")).intValue())
                        .append(COMMA);
            }

            if (options.containsKey("L")) {
                commandBuilder.append("L").append(((Integer) options.get("L")).intValue())
                        .append(COMMA);
            }
        }

        if (options == null || !options.containsKey("L")) {
            commandBuilder.append(ESCAPED_DOUBLE_QUOTE);
        }
        commandBuilder.append(content);

        if (options == null || !options.containsKey("L")) {
            commandBuilder.append(ESCAPED_DOUBLE_QUOTE);
        }

        commandBuilder.append(LF);

        return commandBuilder.toString();
    }

}
