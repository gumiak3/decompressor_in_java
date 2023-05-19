package decompressor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Decompressor {

    private HashMap<String, Short> huffmanMap = new HashMap<>();
    private BufferedWriter writer;

    Decompressor(Dictionary dictionary) throws IOException {
        // Create a map for quick lookup of the Huffman code to its bit representation
        for (Word word : dictionary.dictionary) {
            huffmanMap.put(word.code, word.bitRepresentation);
        }

        writer = new BufferedWriter(new FileWriter("wyniki.txt"));
    }

    public void decompress(String dataToDecompress) throws IOException {
        // Decompress the data
        StringBuilder temp = new StringBuilder();
        for (char c : dataToDecompress.toCharArray()) {
            temp.append(c);
            Short bitRepresentation = huffmanMap.get(temp.toString());
            if (bitRepresentation != null) {
                writer.write((char) bitRepresentation.shortValue());
                temp.setLength(0);  // Reset the temp string builder
            }
        }
    }

    public void close() throws IOException {
        writer.close();
    }
}
