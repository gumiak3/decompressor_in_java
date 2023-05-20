package decompressor;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class SumController {
    String header;
    int compressionRatio;

    int rest1;
    int rest2;
    int sumControl;
    SumController(DataReader data){
        header = "#LP#";
    }


    private String getHeader(ArrayList<Byte> data){
        String newHeader = "";
        for(int i=0;i<4;i++){
            if(i < data.size()){
                newHeader+=(char) data.get(i).byteValue();
            }else{
                return newHeader;
            }
        }
        return newHeader;
    }
    public void getValuesFromFile(DataReader data){
        getCompressionRatio(data);
        getRests(data);
        getSumControl(data);
    }
    private void getSumControl(DataReader data){
        this.sumControl = data.getData().get(0);
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

    public void printSums(){
        System.out.println("Naglowek: " + this.header);
        System.out.println("Stopien kompresji: " + this.compressionRatio);
        System.out.println("Reszta1: " + this.rest1);
        System.out.println("Reszta2: " + this.rest2);
        System.out.println("Suma kontrolna: " + this.sumControl);
    }

}
