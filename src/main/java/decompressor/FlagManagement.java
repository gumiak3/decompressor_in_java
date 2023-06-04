package decompressor;

import java.util.ArrayList;
import java.util.Arrays;

public class FlagManagement {
    // sprawdzenie czy uzytkownik podal poprawne flagi, jezeli nie -> return help
    // funkcje dla -help, -h, -v, -t
    // -help -h -> help
    // -v -> additional information
    // -t -> print tree structure
    public boolean printTree;
    public boolean printAdditionalInfo;
    public boolean printDictionary;
    String[] args;
    FlagManagement(String[] args){
        this.args = removeFilesFromArray(args);
        flagValidationCheck(this.args);
    }
    private String[] removeFilesFromArray(String[] args){
        String[] newArray = new String[args.length - 2];
        System.arraycopy(args,2,newArray,0,args.length - 2);
        return newArray;
    }
    String[] possibleFlags = {"-help", "-h", "-v", "-t", "-s"};
    private boolean contains(String e, String[] array){
        for(String arrayElement : array){
            if(e.equals(arrayElement)){
                return true;
            }
        }
        return false;
    }
    public static void printHelp(){
        System.out.println("This is file decompressor\n" +
                "java -jar target/decompressor.jar {infile} {outfile} [flags]\n" +
                "possible flags:\n" +
                "-help, -h -> show this help and exit the program\n" +
                "-v -> print additional information about the compression of file\n" +
                "-t -> print tree structure in command line\n");
        System.exit(0);
    }
    public void flagValidationCheck(String[] args){
        for(String element : args){
            if(!contains(element, this.possibleFlags)){
                printHelp();
            }else if(element.equals("-v")){
                printAdditionalInfo = true;
            }else if(element.equals("-help") || element.equals("-h")){
                printHelp();
            }else if(element.equals("-s")){
                   printDictionary = true;
            }else{
                printTree = true;
            }
        }
    }

}
