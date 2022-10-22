package uz.driver.commands.device;

import lombok.Builder;
import lombok.Data;
import uz.driver.DriverConstant;
import uz.driver.commands.TSPLCommand;


@Data
@Builder
public class Counter implements TSPLCommand {

    private Integer counterNumber;
    private Integer step;



    @Override
    public String getCommand() {

        if(counterNumber == null){
            System.err.println("COUNTER required");
        }

        if(step == null){
            System.err.println("increment is required");
        }

        if(counterNumber < 0 || counterNumber > 60){
            System.err.println("Counter is only available for range 0-60");
        }

        if(step > 999999999 || step < -999999999){
            System.err.println("Sonlarni chegarasi jura katta");
        }

        StringBuilder commandBuilder = new StringBuilder(DriverConstant.SET_PREFIX);
        commandBuilder.append(DriverConstant.EMPTY_SPACE)
                .append("COUNTER")
                .append(DriverConstant.EMPTY_SPACE)
                .append("@").append(counterNumber)
                .append(DriverConstant.EMPTY_SPACE);

        if(step >= 0){
            commandBuilder.append("+");
        }

        commandBuilder.append(step).append(DriverConstant.LF);

        return commandBuilder.toString();
    }
}
