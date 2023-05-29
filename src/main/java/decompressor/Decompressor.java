package decompressor;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Decompressor {

    private HashMap<String, Short> huffmanMap = new HashMap<>();
    private BufferedOutputStream writer;
    private byte buffor = 0;
    private int bufforIndex = 0;
    Decompressor(Dictionary dictionary) throws IOException {
        // Create a map for quick lookup of the Huffman code to its bit representation
        for (Word word : dictionary.dictionary) {
            huffmanMap.put(word.code, word.bitRepresentation);
        }
        writer = new BufferedOutputStream(new FileOutputStream("wyniki.jpeg"));
    }
    private void write8Bits(short toWrite) throws IOException {
        writer.write((byte) toWrite);
    }
    private void write12Bits(short bits) throws IOException {
        if(this.bufforIndex == 0){
            short toWrite = (short) ((bits >>4) & 0x00FF);
            this.buffor = (byte) (bits << 4);
            write8Bits(toWrite);
            this.bufforIndex = 4;
        }else{
            short toWrite = (short) (this.buffor | ((bits >> 8) & 0x00FF));
            write8Bits(toWrite);
            write8Bits(bits);
            bufforIndex = 0;
        }
    }
    private void write16Bits(short bits) throws IOException {
        write8Bits((short) (bits >> 8));
        write8Bits((short) (bits));
    }
    private void writeToFile(short toWrite, int compressionRatio) throws IOException {
        switch(compressionRatio){
            case 8:
                write8Bits(toWrite);
                break;
            case 12:
                write12Bits(toWrite);
                break;
            case 16:
                write16Bits(toWrite);
                break;
        }
    }
    public void decompress(char[] dataToDecompress, int compressionRatio, int restControl, byte restValue) throws IOException {
        // Decompress the data
        ArrayList<Byte> toWrite = new ArrayList<>();
        StringBuilder temp = new StringBuilder();

        for (char c : dataToDecompress) {
            temp.append(c);
            Short bitRepresentation = huffmanMap.get(temp.toString());
            if (bitRepresentation != null) {
//                toWrite.add ((byte) bitRepresentation.shortValue());
                writeToFile(bitRepresentation.shortValue(), compressionRatio);
                temp.setLength(0);  // Reset the temp string builder
            }
        }
//        for(int i=0;i<toWrite.size();i++){
//            writer.write(toWrite.get(i));
//            writeToFile(toWrite.get(i), compressionRatio, buffor, bufforIndex);
//        }
        switch(restControl){
            case 4:
                buffor |= ((restValue>>4) & 0x000F);
                write8Bits(buffor);
                break;
            case 8:
                write8Bits(restValue);
                break;
        }
    }

    public void close() throws IOException {
        writer.close();
    }
}
