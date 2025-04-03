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
    @Override
   public Set<String> getLiveGen()
   {
       Set<String> liveGen = new HashSet<String>();
       liveGen.add(String.valueOf(this.array.getSerialNumber()));
       liveGen.add(String.valueOf(this.index.getSerialNumber()));
       liveGen.add(String.valueOf(this.value.getSerialNumber()));
       return liveGen;
   }
    //kill is not needed here, implemented IRcommand

}
