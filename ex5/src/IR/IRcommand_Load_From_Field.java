package IR;

import REG.*;
import MIPS.*;
//import REG.*;
//import MIPS.*;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import HelperFunctions.*;
import TYPES.*;

public class IRcommand_Load_From_Field extends IRcommand 
{
    public TEMP dst;
    public TYPE_CLASS_DEC tClass;
    public int offset;
    public String fieldName;

    public IRcommand_Load_From_Field(TEMP dst, TYPE_CLASS_DEC tClass, int offset, String fieldName)
    {
        this.dst = dst;
        this.tClass = tClass;
        this.offset = offset;
        this.fieldName = fieldName;
    }

}
