package decompressor;

import java.util.ArrayList;

public abstract class DataManagement {
    ArrayList<Byte> data;
    public ArrayList<Byte> getData(){
        return data;
    }

    public void removeItems(int firstIndex, int lastIndex){
        for(int i=firstIndex;i<lastIndex;i++){
            data.remove(0);
        }
    }
    public void printData(){
        for(byte a : data){
            System.out.print((char) a);
        }
    }
}
