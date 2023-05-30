package decompressor;

import java.io.*;
import java.util.ArrayList;

public class DataReader extends DataManagement implements iDataReader{

    DataReader()
    {
        data = new ArrayList<>();
    }

    private InputStream openFile(String fileName){
        try{
            InputStream file = new FileInputStream(fileName);
            return file;
        }catch(FileNotFoundException e){
            System.out.println("Couldn't open the file!");
            return null;
        }
    }
    @Override
    public boolean readData(String fileName){
        InputStream file = openFile(fileName);
        if(file == null){
            return false;
        }
        byte[] buffer = new byte[4096];
        int bytesRead = 0;
        try{
            while((bytesRead = file.read(buffer)) != -1){
                for(int i=0;i<bytesRead;i++){
                    data.add(buffer[i]);
                }
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

}
