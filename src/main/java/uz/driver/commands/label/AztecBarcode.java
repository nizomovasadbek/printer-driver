package uz.driver.commands.label;

import uz.driver.DriverConstant;
import uz.driver.commands.TSPLCommand;
import static uz.driver.commands.label.LabelFormatCommand.AZTEC;
public class AztecBarcode implements TSPLCommand {

    private Integer xCoordinate;
    private Integer yCoordinate;
    private BarcodeRotation rotation;
    private Integer moduleSize;
    private Integer errorControl;
    private Boolean escapeFlag;
    private Boolean menu;
    private Integer multi;
    private Boolean rev;
    private Integer bytes;
    private String content;

    @Override
    public String getCommand() {
        if(xCoordinate == null || yCoordinate == null){
            System.err.println("Coordinates wrong");
        }

        if(rotation == null){
            System.err.println("Rotation is null");
        }

        if(moduleSize != null && (moduleSize < 1 || moduleSize > 20)){
            System.err.println("AZTEC: invalid module size value");
        }

        if(errorControl!=null && (errorControl == 100) || (errorControl > 104 && errorControl <= 200)
        || (errorControl > 232 && errorControl < 300) || errorControl > 300){
            System.err.println("Invalid parameter");
        }

        StringBuilder commandBuilder = new StringBuilder(AZTEC.name());
        commandBuilder.append(DriverConstant.EMPTY_SPACE)
                .append(xCoordinate).append(DriverConstant.COMMA)
                .append(yCoordinate).append(DriverConstant.COMMA)
                .append(rotation.getRotation()).append(DriverConstant.COMMA);

        if(moduleSize == null){
            commandBuilder.append("6").append(DriverConstant.COMMA);
        } else {
            commandBuilder.append(errorControl).append(DriverConstant.COMMA);
        }

        commandBuilder.append(escapeFlag).append(DriverConstant.COMMA);

        if(menu == null){
            commandBuilder.append("0").append(DriverConstant.COMMA);
        } else {
            commandBuilder.append(menu ? "1" : "0").append(DriverConstant.COMMA);
        }

        if(multi == null){
            commandBuilder.append("6").append(DriverConstant.COMMA);
        } else {
            commandBuilder.append(multi).append(DriverConstant.COMMA);
        }

        if(rev == null){
            commandBuilder.append("0").append(DriverConstant.COMMA);
        } else {
            commandBuilder.append(rev ? "1" : "0").append(DriverConstant.COMMA);
        }

        if(bytes != null){
            commandBuilder.append(bytes).append(DriverConstant.COMMA).append("\"");
        }

        commandBuilder.append(content);

        if(bytes != null){
            commandBuilder.append("\"");
        }

        commandBuilder.append(DriverConstant.LF);
        return commandBuilder.toString();
    }
}
