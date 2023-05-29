package decompressor;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;

public class Main{
    public static void main(String[] args){
        DataReader data = new DataReader();
        if(!data.readData("testFiles/test2_compressed_16.jpeg")){
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
        if(!FileValidator.checkCorrectnessOfDictionary()){
            FileValidator.printCorruptionInfo();
        }
        HuffmanTree huffmanTree = new HuffmanTree(dictionary);
        CompressedData compressedData = new CompressedData();
        compressedData.setRest2Value(sumController.rest2, data.getData());
        if(!FileValidator.checkCorrectnessOfFile(sumController.sumControl,data.getData(),compressedData.rest2Value)){
            FileValidator.printCorruptionInfo();
        }
        char[] dataToDecompress = compressedData.getCompressedData(data.getData(), sumController.rest1, sumController.rest2);
        try {
            Decompressor decompressor = new Decompressor(dictionary);
            decompressor.decompress(dataToDecompress, sumController.compressionRatio, sumController.rest2, compressedData.rest2Value);
            decompressor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
