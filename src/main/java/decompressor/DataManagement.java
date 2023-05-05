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

    public static boolean checkCorrectnessOfFile(int sumControl, ArrayList<Byte> data,byte rest2Value ){
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
    public String toBinary(byte a)
    {
        int b = byteToInt(a);
        return completeWithZeros(Integer.toBinaryString((char) b));
    }
    public int byteToInt(byte a){
        return (int) (a & 0xFF);
    }
    public String completeWithZeros(String binary){
        int amountToComplete = 8 - binary.length();
        String temp = "";
        for(int i=0;i<amountToComplete;i++){
            temp+='0';
        }
        binary = temp + binary;
        return binary;
    }
}
