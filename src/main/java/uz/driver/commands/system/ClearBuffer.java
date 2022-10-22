package uz.driver.commands.system;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;

import static uz.driver.DriverConstant.LF;

@Data
@Builder
public class ClearBuffer implements TSPLCommand {

    @Override
    public String getCommand(){
        return SystemCommand.CLS.name() + LF;
    }

}
