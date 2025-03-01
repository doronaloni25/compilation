package IR;

import REG.*;
import MIPS.*;

public class IRcommand_Load_From_Array extends IRcommand 
{
    public TEMP dst;
    public TEMP array;
    public TEMP index;

    public IRcommand_Load_From_Array(TEMP dst, TEMP array, TEMP index)
    {
        this.dst = dst;
        this.array = array;
        this.index = index;
    }

}
