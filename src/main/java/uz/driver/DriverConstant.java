package uz.driver;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class DriverConstant {

    public static final String SET_PREFIX = "SET";

    public static final String STATUS_COMMAND_PREFIX = "!";

    public static final String COMMA = ",";

    public static final String EMPTY_SPACE = " ";

    public static final String LF = "\n";

    public static final String CR = "\r";

    public static final String CR_LF = "\r\n";

    public static final String ESCAPED_DOUBLE_QUOTE = "\"";

    public static final String UNIT_MM = "mm";

    public static final byte[] LF_BYTES = LF.getBytes(US_ASCII);

    public static final byte[] CR_BYTES = CR.getBytes(US_ASCII);

    public static final byte[] CR_LF_BYTES = CR_LF.getBytes(US_ASCII);

    public DriverConstant(){

    }

}
