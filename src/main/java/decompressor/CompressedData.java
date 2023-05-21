package decompressor;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class CompressedData extends DataManagement {
    int size;
    byte rest2Value;
    CompressedData() {
        this.size = 0;
    }
    public void setRest2Value(int rest2, ArrayList<Byte> data){
        if(rest2>0){
            this.rest2Value = data.get(data.size()-1);
        }
    }
    private void setNumberBitsToRead(int size, int rest1,int rest2){
        this.size = size*8;
        if(rest2 > 0){
            this.size-=8;
        }
        if(rest1 > 0){
            this.size -= (8-rest1);
        }
    }
    public char[] getCompressedData(ArrayList<Byte> data, int rest1, int rest2){
        setNumberBitsToRead(data.size(), rest1, rest2);
        int bitsToRead = this.size;
        int dataIndex = 0;
        int outputIndex = 0;
        char[] output = new char[bitsToRead];
        while(dataIndex < data.size() && bitsToRead >=8){
            String temp = toBinary(data.get(dataIndex++).byteValue());
            for(int i=0;i<8;i++){
                output[outputIndex++] = temp.charAt(i);
            }
            bitsToRead-=8;
        }
        if(bitsToRead > 0) {
            String temp = toBinary(data.get(dataIndex).byteValue());
            for(int i=0;i<bitsToRead;i++){
                output[outputIndex++]+= temp.charAt(i);
            }
        }
        return output;
    }
}
