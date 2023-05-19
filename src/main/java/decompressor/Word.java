package decompressor;

public class Word {
    short bitRepresentation;
    String code;
    Word left;
    Word right;

    Word(short bitRepresentation){
        this.bitRepresentation = bitRepresentation;
        this.left = null;
        this.right = null;
    }

    public void setBitRepresentation(short bit){
        this.bitRepresentation = bit;
    }

    public void setCode(String code){
        this.code = code;
    }
}

