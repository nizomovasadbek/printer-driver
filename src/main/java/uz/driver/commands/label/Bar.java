package uz.driver.commands.label;

import uz.driver.commands.TSPLCommand;
import static uz.driver.DriverConstant.*;

public class Bar implements TSPLCommand {

    private Integer xCoordinate;
    private Integer yCoordinate;
    private Integer width;
    private Integer height;

    @Override
    public String getCommand() {
        if (xCoordinate == null || yCoordinate == null) {
            System.err.println("BAR: x and y positions are required");
        }

        if (width == null) {
            System.err.println("BAR: width is required");
        }

        if (height == null) {
            System.err.println("BAR: height is required");
        }

        return LabelFormatCommand.BAR.name() + EMPTY_SPACE
                + xCoordinate + COMMA
                + yCoordinate + COMMA
                + width + COMMA
                + height
                + LF;
    }
}
