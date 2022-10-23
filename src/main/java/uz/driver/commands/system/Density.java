package uz.driver.commands.system;

import lombok.Builder;
import lombok.Data;
import uz.driver.DriverConstant;
import uz.driver.commands.TSPLCommand;
import uz.driver.exceptions.LabelParserException;

import static uz.driver.commands.system.SystemCommand.*;
import static uz.driver.DriverConstant.*;

@Data
@Builder
public class Density implements TSPLCommand {

    private Integer darkness;

    @Override
    public String getCommand() {
        if (darkness == null) {
            throw new LabelParserException("ParseException DENSITY Command: "
                    + "darkness can't be empty");
        }
        return DENSITY.name() + EMPTY_SPACE + darkness + LF;
    }
}
