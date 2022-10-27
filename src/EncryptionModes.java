import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EncryptionModes {
    private final Key key;
    private final String input;

    public EncryptionModes(String input, Key key) {
        this.input = input;
        this.key = key;
    }

    public byte[] GetBytesOfCipherText(){
        return input.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] PaddingToPlainText(){
       List<Byte> inputBytes = convertBytesToList(input.getBytes(StandardCharsets.UTF_8));
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
       byte[] byteArray = new byte[inputBytes.size()];
       for (int i = 0; i < inputBytes.size(); i++){
           byteArray[i] = inputBytes.get(i);
       }
       return byteArray;
    }

    public static List<Byte> convertBytesToList(byte[] bytes) {
        final List<Byte> list = new ArrayList<>();
        for (byte b : bytes) {
            list.add(b);
        }
        return list;
    }

    public static byte[] convert2Dto1DArray(byte[][] array){
        byte[] newArray = new byte[array.length * array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                newArray[i + (j * array.length)] = array[i][j];
            }
        }
        return newArray;
    }

    public static void writeToFile(String text, String fileName){
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(text);
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
   
}
