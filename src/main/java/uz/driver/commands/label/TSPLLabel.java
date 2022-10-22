package uz.driver.commands.label;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import uz.driver.commands.TSPLCommand;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class TSPLLabel {

    @Singular
    private List<TSPLCommand> elements;

    public String getTsplCode(){
        return elements.stream()
                .map(TSPLCommand::getCommand).collect(Collectors.joining(""));
    }

}
