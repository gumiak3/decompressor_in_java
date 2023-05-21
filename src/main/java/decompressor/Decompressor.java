package decompressor;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Decompressor {

    private HashMap<String, Short> huffmanMap = new HashMap<>();
    private BufferedOutputStream writer;

    Decompressor(Dictionary dictionary) throws IOException {
        // Create a map for quick lookup of the Huffman code to its bit representation
        for (Word word : dictionary.dictionary) {
            huffmanMap.put(word.code, word.bitRepresentation);
        }
        writer = new BufferedOutputStream(new FileOutputStream("wyniki.txt"));
    }

    public void decompress(char[] dataToDecompress) throws IOException {
        // Decompress the data
        ArrayList<Byte> toWrite = new ArrayList<>();
        StringBuilder temp = new StringBuilder();
        for (char c : dataToDecompress) {
            temp.append(c);
            Short bitRepresentation = huffmanMap.get(temp.toString());
            if (bitRepresentation != null) {

                toWrite.add ((byte) bitRepresentation.shortValue());
                temp.setLength(0);  // Reset the temp string builder
            }
        }
        for(int i=0;i<toWrite.size();i++){
            writer.write(toWrite.get(i));
        }
    }

    public void close() throws IOException {
        writer.close();
    }
}
