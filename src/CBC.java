import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CBC extends EncryptionModes{
    private final byte[] keyBytes;
    private final byte[] iv;

    public CBC(String input, Key key) {
        super(input, key);
        this.keyBytes = key.getKeyBytes();
        this.iv = key.getInitializationVector();
    }

    public void EncryptWithDES() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] plainTextBytes = PaddingToPlainText(); // convert the plaintext into its byte representation and apply padding to the representation
        System.out.println(Arrays.toString(plainTextBytes));
        SecretKey secretKey = new SecretKeySpec(keyBytes,"DES"); // create secret key to be used iN DES from the key bytes
        // activate DES cipher with ECB encryption mode
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] vector = Arrays.copyOf(iv, iv.length); // get the initialization vector
        byte[][] encrypted = new byte[plainTextBytes.length / 8][];
        int counter = 0;
        for (int i = 0; i < plainTextBytes.length; i += 8){
            byte[] block = Arrays.copyOfRange(plainTextBytes, i, i + 8);
            byte[] xorBlock = new byte[block.length];
            for (int j = 0; j < block.length; j++){
                xorBlock[j] = (byte) (block[j] ^ vector[j]);
            }
            byte[] cipherBlock = cipher.doFinal(xorBlock); // encrypted block is the new iv
            vector = Arrays.copyOf(cipherBlock, cipherBlock.length); //  update iv
            encrypted[counter] = cipherBlock;
            counter++;
        }
        writeToFile(new String(convert2Dto1DArray(encrypted), StandardCharsets.UTF_8), "cbc_encrypt_text.txt");
    }

    public void DecryptWithDES() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] cipherTextBytes = GetBytesOfCipherText(); // convert the ciphertext into its byte representation
        System.out.println(Arrays.toString(cipherTextBytes));
        SecretKey secretKey = new SecretKeySpec(keyBytes,"DES"); // create secret key to be used iN DES from the key bytes
        // activate DES cipher with ECB decryption mode
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] vector = Arrays.copyOf(iv, iv.length); // get the initialization vector
        byte[][] decrypted = new byte[cipherTextBytes.length / 8][];
        int counter = 0;
        for (int i = 0; i < cipherTextBytes.length; i += 8){
            byte[] block = Arrays.copyOfRange(cipherTextBytes, i, i + 8); // get 8 byte block
            byte[] decryptBlock = cipher.doFinal(block); //decrypt the current block
            byte[] plainTextBlock = new byte[block.length];
            for (int j = 0; j < block.length; j++){
                // block of plain text
                plainTextBlock[j] = (byte) (decryptBlock[j] ^ vector[j]); // xor decrypted block with the initialization vector
            }
            vector = Arrays.copyOf(decryptBlock, decryptBlock.length); //  update iv with current ciphertext
            decrypted[counter] = plainTextBlock; // fill out the plain text bytes
            counter++;
        }
        System.out.println(new String(convert2Dto1DArray(decrypted), StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8).length);
        writeToFile(new String(convert2Dto1DArray(decrypted), StandardCharsets.UTF_8), "cbc_decrypt_text.txt");
    }
}
