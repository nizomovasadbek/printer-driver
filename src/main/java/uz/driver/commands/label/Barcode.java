package uz.driver.commands.label;

import lombok.Builder;
import lombok.Data;
import uz.driver.commands.TSPLCommand;

@Data
@Builder
public class Barcode implements TSPLCommand {

    private Float xCoordinate;
    private Float yCoordinate;
    private Integer height;
    private BarcodeType codeType;
    private BarcodeHRCAlignment hrcAlignment;

    @Override
    public String getCommand() {
        return null;
    }
}
