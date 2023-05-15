package decompressor;

import java.util.ArrayList;

public class FileValidator {

    public static boolean checkCorrectnessOfFile(int sumControl, ArrayList<Byte> data, byte rest2Value ){
        for(int i=0;i<data.size();i++){
            sumControl^=data.get(i);
        }
        if(rest2Value > 0){
            sumControl^=rest2Value;
        }
        if(sumControl == 0){
            return true;
        }
        return false;
    }
}
