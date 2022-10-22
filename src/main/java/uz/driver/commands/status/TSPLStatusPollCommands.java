package uz.driver.commands.status;

import uz.driver.commands.TSPLCommand;

import static uz.driver.DriverConstant.*;

public enum TSPLStatusPollCommands implements TSPLCommand {

    STATUS("?", "Printer Status"),
    STATUS_LONG("S", "Printer Status"),
    PAUSE("P", "Pause Printer"),
    CANCEL_PAUSE("R", "Reset Printer"),
    FEED_LABEL("F", "Feed Label"),
    CANCEL_PRINT(".", "Cancel Print");

    private String description;
    private String command;

    TSPLStatusPollCommands(String command, String description){
        this.command = command;
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    @Override
    public String getCommand(){
        return ((char) 27 + STATUS_COMMAND_PREFIX + command + LF);
    }

}
