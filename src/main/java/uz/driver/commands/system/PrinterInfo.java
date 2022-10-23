package uz.driver.commands.system;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;

@Data
@Builder
public class PrinterInfo implements TSPLCommand {
    private PrinterInfoPage page;
}
