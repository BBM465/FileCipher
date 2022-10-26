import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EncryptionModes {
    private final Key key;
    private final String input;

    public EncryptionModes(String input, Key key) {
        this.input = input;
        this.key = key;
    }

    public byte[] Padding(){
       List<Byte> inputBytes = convertBytesToList(input.getBytes());
       System.out.println(inputBytes.size());
       byte[] byteArray = new byte[inputBytes.size()];
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
       for (int i = 0; i < inputBytes.size(); i++){
           byteArray[i] = inputBytes.get(i);
       }
       return byteArray;
    }

    public byte[] CBC() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] inputBytes = Padding(); // apply padding to the input
        byte[] keyBytes = key.getKeyBytes(); // get 8 bytes from the key string to use in DES
        SecretKey secretKey = new SecretKeySpec(keyBytes,"DES"); // create secret key to be used iN DES from the key bytes
        // activate DES cipher with ECB encryption mode
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = new byte[inputBytes.length];
        byte[] iv = key.getInitializationVector(); // get initialization vector
        for (int i = 0; i < inputBytes.length; i += 8){
            byte[] block = Arrays.copyOfRange(inputBytes, i, i + 8);
            byte[] xorBlock = new byte[block.length]; // it will be the next initialization vector
            for (int j = 0; j < block.length; j++){
                xorBlock[j] = (byte) (block[j] ^ iv[j]);
            }
            byte[] cipherBlock = cipher.doFinal(xorBlock);
            iv = Arrays.copyOf(cipherBlock, cipherBlock.length); //  update iv
            for (int k = i; k < cipherBlock.length + i; k++){
                encrypted[k] = cipherBlock[k - i];
            }
        }
        return encrypted;
    }

    private static List<Byte> convertBytesToList(byte[] bytes) {
        final List<Byte> list = new ArrayList<>();
        for (byte b : bytes) {
            list.add(b);
        }
        return list;
    }

   
}
