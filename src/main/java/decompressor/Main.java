package decompressor;

public class Main {
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
        dictionary.printExtraInfo();
    }
}
