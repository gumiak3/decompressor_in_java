package decompressor;

import javax.xml.crypto.Data;

public class Main{
    public static void main(String[] args){
        DataReader data = new DataReader();
        if(!data.readData("test.txt")){
            System.exit(1);
        }
        SumController sumController = new SumController(data);
        if(!sumController.checkHeader(data)){
            System.out.println("File is not compressed!");
            System.exit(1);
        }
        sumController.getValuesFromFile(data);
        sumController.printSums();
        Dictionary dictionary = new Dictionary();
        dictionary.getDictionary(data, sumController.compressionRatio);
        CompressedData compressedData = new CompressedData();
        compressedData.setRest2Value(sumController.rest2, data.getData());
        String dataToDecompress = compressedData.getCompressedData(data.getData(), sumController.rest1, sumController.rest2);
        if(!FileValidator.checkCorrectnessOfFile(sumController.sumControl,data.getData(),compressedData.rest2Value)){
            System.out.println("File is corrupted!");
            System.exit(1);
        }

    }
}
