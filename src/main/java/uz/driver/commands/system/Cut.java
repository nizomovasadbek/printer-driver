package uz.driver.commands.system;

import lombok.Builder;
import uz.driver.commands.TSPLCommand;

import static uz.driver.DriverConstant.LF;
import static uz.driver.commands.system.SystemCommand.CUT;

@Builder
public class Cut implements TSPLCommand {
    @Override
    public String getCommand() {
        return CUT.name() + LF;
    }
}
