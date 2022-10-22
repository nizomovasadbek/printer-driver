package uz.driver.commands.device;

import lombok.Builder;
import lombok.Data;
import uz.driver.DriverConstant;
import uz.driver.commands.TSPLCommand;

@Data
@Builder
public class CounterExpression implements TSPLCommand {

    private Integer counterNumber;
    private String expression;

    @Override
    public String getCommand() {
        if(counterNumber == null){
            System.err.println("Counter couldn't be null");
        }

        if(counterNumber < 0 || counterNumber > 60){
            System.err.println("range only in 0-60");
        }

        StringBuilder commandBuilder = new StringBuilder("@");
        commandBuilder.append(counterNumber)
                .append("=")
                .append(DriverConstant.ESCAPED_DOUBLE_QUOTE).append(expression).append(DriverConstant.ESCAPED_DOUBLE_QUOTE)
                .append(DriverConstant.LF);

        return commandBuilder.toString();
    }
}
