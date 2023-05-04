package decompressor;

import java.util.ArrayList;

public class Dictionary {
    int dictionarySize;
    ArrayList<Word> dictionary;

    Dictionary(){
        dictionarySize = 0;
        dictionary = new ArrayList<>();
    }
    private String completeWithZeros(String binary){
        int amountToComplete = 8 - binary.length();
        String temp = "";
        for(int i=0;i<amountToComplete;i++){
            temp+='0';
        }
        binary = temp + binary;
        return binary;
    }

    private int byteToInt(byte a){
        return (int) (a & 0xFF);
    }
    private int getDictionarySize(byte a, byte b){
        String temp1 = Integer.toBinaryString((char) byteToInt(a) );
        String temp2 = Integer.toBinaryString((char) byteToInt(b) );
        String sizeInBinary = completeWithZeros(temp1) + "" + completeWithZeros(temp2);// do naprawy!
        System.out.println(sizeInBinary);
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
    private String toBinary(byte a)
    {
        int b = byteToInt(a);
        return completeWithZeros(Integer.toBinaryString((char) b));
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
                        System.out.println(dictionary.get(0).bitRepresentation);
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
                temp = toBinary(dataArray.get(dataIndex++).byteValue());
                tempIndex = 0;
                amountToRemove++;
            }
        }
        if(dictionarySize > 0){
            data.removeItems(0,amountToRemove);
        }
    }
}
