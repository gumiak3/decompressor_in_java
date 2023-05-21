package decompressor;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class Dictionary extends DataManagement {
    int dictionarySize;
    ArrayList<Word> dictionary;
    public static boolean corrupted;
    Dictionary(){
        dictionarySize = 0;
        dictionary = new ArrayList<>();
        corrupted = false;
    }

    private int getDictionarySize(byte a, byte b){
        String temp1 = Integer.toBinaryString((char) byteToInt(a) );
        String temp2 = Integer.toBinaryString((char) byteToInt(b) );
        String sizeInBinary = completeWithZeros(temp1) + "" + completeWithZeros(temp2);
        return Integer.parseInt(sizeInBinary,2);
    }
    public void printDictionary(){
        for(Word w : dictionary){
            System.out.println(w.bitRepresentation + " -> "+w.code);
        }
    }
    public void printExtraInfo(){
        printDictionary();
        System.out.println("Dictionary Size: " + this.dictionarySize);
    }

    public void getDictionary(DataReader data, int compressionRatio) {
        this.dictionarySize = getDictionarySize(data.getData().get(0).byteValue(), data.getData().get(1).byteValue());
        data.removeItems(0, 2);
        ArrayList<Byte> dataArray = data.getData();
        int codeLength = compressionRatio;
        int dataIndex = 0;
        String temp = toBinary(dataArray.get(dataIndex++).byteValue());
        String buffor = "";
        int tempIndex = 0;
        int codeIndex = 0;
        int whichData = 0;
        int i=0;
        int amountToRemove = 1;
        while(i<this.dictionarySize) {
            while (tempIndex < 8 && codeIndex < codeLength) {
                buffor += temp.charAt(tempIndex++);
                codeIndex++;
            }
            if (codeIndex == codeLength) {
                switch (whichData % 3) {
                    case 0: {
                        dictionary.add(new Word((short) Integer.parseInt(buffor, 2)));
                        codeLength = 8;
                        break;
                    }
                    case 1: {
                        codeLength = Integer.parseInt(buffor, 2);
                        break;
                    }
                    case 2: {
                        dictionary.get(i).setCode(buffor);
                        i++;
                        codeLength = compressionRatio;
                        break;
                    }
                }
                whichData++;
                buffor = "";
                codeIndex = 0;
            }
            if (tempIndex == 8 && i < this.dictionarySize){
                if(dataIndex == dataArray.size()){
                    i = this.dictionarySize;
                    corrupted = true;
                }else{
                    temp = toBinary(dataArray.get(dataIndex++).byteValue());
                    tempIndex = 0;
                    amountToRemove++;
                }
            }
        }
        if(dictionarySize > 0){
            data.removeItems(0,amountToRemove);
        }
    }
}
