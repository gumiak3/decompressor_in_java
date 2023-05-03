package decompressor;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class SumController {
    String header;
    int compressionRatio;

    byte rest1;
    byte rest2;
    int sumControll;
    SumController(DataReader data){
        header = "#LP#";
    }


    private String getHeader(ArrayList<Byte> data){
        String newHeader = "";
        for(int i=0;i<4;i++){
            newHeader+=(char) data.get(i).byteValue();
        }
        return newHeader;
    }
    public void getValuesFromFile(DataReader data){
        getCompressionRatio(data);
        getRests(data);
        getSumControll(data);
    }
    private void getSumControll(DataReader data){
        this.sumControll = data.getData().get(0);
        data.getData().remove(0);
    }
    private void getRests(DataReader data){
        this.rest1 = data.getData().get(0);
        this.rest2 = data.getData().get(1);
        data.removeItems(0,2);
    }
    private void getCompressionRatio(DataReader data){
        this.compressionRatio = data.getData().get(0);
        data.getData().remove(0);
    }
    public boolean checkHeader(DataReader data){
        if(getHeader(data.getData()).equals(this.header)){
            data.removeItems(0,4);
            return true;
        }
        return false;
    }

}
