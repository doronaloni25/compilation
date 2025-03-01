package IR;

import REG.*;
import MIPS.*;

public class IRcommand_Store_Into_Array extends IRcommand 
{
    public TEMP array;
    public TEMP index;
    public TEMP value;

    public IRcommand_Store_Into_Array(TEMP array, TEMP index, TEMP value)
    {
        this.array = array;
        this.index = index;
        this.value = value;
    }

}
