				
package IR;

import REG.*;
import MIPS.*;

public class IRcommand_Store_Into_Field extends IRcommand 
{
    public TEMP class;
    public TEMP value; 
    public int offset;
    public String fieldName;

    public IRcommand_Store_Into_Field(TEMP class,TEMP value, int offset, String fieldName)
    {
        this.class = class;
        this.value = value;
        this.offset = offset;
        this.fieldName = fieldName;
    }

}
            
