package decompressor;

import javax.xml.crypto.Data;
import java.io.IOException;

public class Main{
    public static void main(String[] args){
        DataReader data = new DataReader();
        if(!data.readData("testFiles/pan_tadeusz_compressed.txt")){
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
        // Create and print the Huffman tree
        HuffmanTree huffmanTree = new HuffmanTree(dictionary);
        huffmanTree.printTree();
        huffmanTree.printTreeStructure();
        CompressedData compressedData = new CompressedData();
        compressedData.setRest2Value(sumController.rest2, data.getData());
        if(!FileValidator.checkCorrectnessOfFile(sumController.sumControl,data.getData(),compressedData.rest2Value)){
            System.out.println("File is corrupted!");
            System.exit(1);
        }
        char[] dataToDecompress = compressedData.getCompressedData(data.getData(), sumController.rest1, sumController.rest2);
        try {
            Decompressor decompressor = new Decompressor(dictionary);
            decompressor.decompress(dataToDecompress);
            decompressor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
