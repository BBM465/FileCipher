import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EncryptionModes {
    private final Key key;
    private final byte[] input;

    public EncryptionModes(byte[] input, Key key) {
        this.input = input;
        this.key = key;
    }

    public byte[] GetBytesOfCipherText() {
        return input;
    }


    public byte[] PaddingToPlainText() {
        List<Byte> inputBytes = convertBytesToList(input);
        if (inputBytes.size() % 8 != 0) {
            int remainingBytes = (8 - inputBytes.size() % 8); // how many bytes we need to create a whole block
            // first byte is filled with 0x80
            inputBytes.add((byte) 0x80);
            remainingBytes = remainingBytes - 1;
            while (remainingBytes > 0) {
                inputBytes.add((byte) 0x00); // remaining bytes are filled with 0x00
                remainingBytes -= 1;
            }
        }
        byte[] byteArray = new byte[inputBytes.size()];
        for (int i = 0; i < inputBytes.size(); i++) {
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

    public static byte[] convert2Dto1DArray(byte[][] array) {
        byte[] newArray = new byte[array.length * array[0].length];
        for (int i = 0; i < array.length; i++) {
            System.arraycopy(array[i], 0, newArray, (i * array[i].length), array[i].length);
        }
        return newArray;
    }

    public static void writeToFile(String text, String fileName) {
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

    public static byte[] removePadding(byte[] cipherText) {
        List<Byte> bytes = new ArrayList<Byte>();
        int i = 0;
        if(cipherText.length%8 == 0){
            return  cipherText;
        }
        while (cipherText[i] != -128) {
            System.out.println(i);
            bytes.add(cipherText[i]);
            i++;
        }
        byte[] newCipher = new byte[bytes.size()];
        for (int j = 0; j < newCipher.length; j++) {
            newCipher[j] = bytes.get(j);
        }
        return newCipher;
    }
}


