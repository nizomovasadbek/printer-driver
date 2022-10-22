package uz.driver.commands.device;

import uz.driver.commands.TSPLCommand;

public enum TSPLDeviceConfigurationCommands implements TSPLCommand {
;
//    PARTIAL_CUTTER_OFF(DeviceConfigCommand.PARTIAL_CUTTER, PartialCutterValue.OFF)

    @Override
    public String getCommand() {
        return "test";
    }
}
