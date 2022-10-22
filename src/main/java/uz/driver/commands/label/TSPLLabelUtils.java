package uz.driver.commands.label;

import uz.driver.DriverConstant;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class TSPLLabelUtils {

    private TSPLLabelUtils(){

    }

    public static byte[] parseAndGetLabelContent(String labelTemplate,
                                                 Map<String, String> parameters){
        String localTemplate = labelTemplate;
        for(Map.Entry<String, String> entry : parameters.entrySet()){
            localTemplate = localTemplate.replaceAll("<<<" + entry.getKey() + ">>>", entry.getValue());
        }

        return localTemplate.getBytes(StandardCharsets.US_ASCII);
    }

    public static boolean hasFloatDecimals(Float f){
        return (f - f.intValue() != 0);
    }

}
