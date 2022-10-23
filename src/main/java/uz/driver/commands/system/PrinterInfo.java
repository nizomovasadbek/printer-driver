package uz.driver.commands.system;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;

import static uz.driver.DriverConstant.EMPTY_SPACE;
import static uz.driver.DriverConstant.LF;
import static uz.driver.commands.system.SystemCommand.SELFTEST;

@Data
@Builder
public class PrinterInfo implements TSPLCommand {
    private PrinterInfoPage page;

    @Override
    public String getCommand() {
        StringBuilder commandBuilder = new StringBuilder(SELFTEST.name());
        if (page != null) {
            commandBuilder.append(EMPTY_SPACE)
                    .append(page.name());
        }

        commandBuilder.append(LF);

        return commandBuilder.toString();
    }

}
