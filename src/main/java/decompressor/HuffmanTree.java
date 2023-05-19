package decompressor;

public class HuffmanTree {
    private Word root;

    public HuffmanTree(Dictionary dictionary) {
        root = new Word((short) -1);  // dummy root

        // Build the Huffman tree from the dictionary
        for (Word word : dictionary.dictionary) {
            addWord(root, word, word.code);
        }
    }

    private void addWord(Word current, Word word, String code) {
        if (code.length() == 0) {
            current.bitRepresentation = word.bitRepresentation;
            current.code = word.code;
        } else {
            char direction = code.charAt(0);
            if (direction == '0') {
                if (current.left == null) {
                    current.left = new Word((short) -1);
                }
                addWord(current.left, word, code.substring(1));
            } else {
                if (current.right == null) {
                    current.right = new Word((short) -1);
                }
                addWord(current.right, word, code.substring(1));
            }
        }
    }

    public void printTree() {
        printTree(root, "");
    }

    private void printTree(Word current, String prefix) {
        if (current == null) {
            return;
        }
        if (current.bitRepresentation != -1) {  // not a dummy node
            System.out.println("Code: " + prefix + ", Bit representation: " + current.bitRepresentation);
        }
        printTree(current.left, prefix + "0");
        printTree(current.right, prefix + "1");
    }


    public void printTreeStructure() {
        printTreeStructure(root, "", "-");
    }


    private void printTreeStructure(Word current, String indent, String bit) {
        if (current != null) {
            if (indent.isEmpty()) {  // node is the root
                System.out.println("Root:");
            } else if (current.left == null && current.right == null) {  // node is a leaf
                System.out.println(indent + "-- Bit: " + bit + ", Bit Representation: " + current.bitRepresentation);
            } else {
                System.out.println(indent + "-- Bit: " + bit);
            }
            indent += "   ";
            printTreeStructure(current.left, indent + "|", "0");
            printTreeStructure(current.right, indent + " ", "1");
        }
    }



}
