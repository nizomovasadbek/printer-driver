package uz.driver.commands.label;

import uz.driver.commands.TSPLCommand;
import static uz.driver.DriverConstant.*;
import static uz.driver.commands.label.LabelFormatCommand.CIRCLE;

public class Circle implements TSPLCommand {

    private Integer xStart;
    private Integer yStart;
    private Integer diameter;
    private Integer thickness;

    @Override
    public String getCommand(){
        if (xStart == null || yStart == null) {
            System.err.println("CIRCLE: x and y positions are required");
        }

        if (diameter == null) {
            System.err.println("CIRCLE: please specify diameter");
        }

        if (thickness == null) {
            System.err.println("CIRCLE: please specify thickness");
        }

        StringBuilder commandBuilder = new StringBuilder(CIRCLE.name());
        commandBuilder.append(EMPTY_SPACE)
                .append(xStart).append(COMMA)
                .append(yStart).append(COMMA)
                .append(diameter).append(COMMA)
                .append(thickness)
                .append(LF);

        return commandBuilder.toString();
    }

}
