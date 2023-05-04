package decompressor;

public class Word {
    short bitRepresentation;
    String code;
   Word(short bitRepresentation){
       this.bitRepresentation = bitRepresentation;
   }
    public void setBitRepresentation(short bit){
        this.bitRepresentation = bit;
    }
    public void setCode(String code){
        this.code = code;
    }
}
