import java.util.ArrayList;
import java.util.List;

public class EncryptionModes {
    private final Key key;
    private final String input;

    public EncryptionModes(String input, Key key) {
        this.input = input;
        this.key = key;
    }

    public List<Byte> Padding(){
       List<Byte> inputBytes = convertBytesToList(input.getBytes());
       System.out.println(inputBytes.size());
       if (inputBytes.size() % 8 != 0){
           int remainingBytes = (8 - inputBytes.size() % 8); // how many bytes we need to create a whole block
           // first byte is filled with 0x80
           inputBytes.add((byte) 0x80);
           remainingBytes = remainingBytes - 1;
           while (remainingBytes > 0){
               inputBytes.add((byte) 0x00); // remaining bytes are filled with 0x00
               remainingBytes -= 1;
           }
       }
       return inputBytes;
    }

    private static List<Byte> convertBytesToList(byte[] bytes) {
        final List<Byte> list = new ArrayList<>();
        for (byte b : bytes) {
            list.add(b);
        }
        return list;
    }
}
