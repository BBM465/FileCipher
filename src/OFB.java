import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class OFB  extends EncryptionModes{
    private final byte[] keyBytes;
    private final byte[] iv;

    public OFB(byte[] input, Key key) {
        super(input, key);
        this.keyBytes = key.getKeyBytes();
        this.iv = key.getInitializationVector();
    }

    public void EncryptWithDES(Boolean tripleDes, String fileName) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] plainTextBytes = PaddingToPlainText(); // convert the plaintext into its byte representation and apply padding to the representation
        SecretKey secretKey = new SecretKeySpec(keyBytes,"DES"); // create secret key to be used iN DES from the key bytes
        // activate DES cipher with ECB encryption mode
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] vector = Arrays.copyOf(iv, 8); // get the initialization vector
        byte[][] encrypted = new byte[plainTextBytes.length / 8][];
        int counter = 0;
        for (int i = 0; i < plainTextBytes.length; i += 8){
            byte[] block = Arrays.copyOfRange(plainTextBytes, i, i + 8);
            byte[] pseudorandomNumber;
            if (tripleDes){
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] firstStep = cipher.doFinal(vector);
                cipher.init(Cipher.DECRYPT_MODE,secretKey);
                byte[] secondStep = cipher.doFinal(firstStep);
                cipher.init(Cipher.ENCRYPT_MODE,secretKey);
                pseudorandomNumber = cipher.doFinal(secondStep);
            }
            else {
                pseudorandomNumber = cipher.doFinal(vector); // generate pseudorandom number by encrypting the initialization vector
            }
            byte[] encryptedPart = new byte[block.length];
            for (int j = 0; j < block.length; j++){
                encryptedPart[j] = (byte) (block[j] ^ pseudorandomNumber[j]);
            }
            vector = Arrays.copyOf(pseudorandomNumber, pseudorandomNumber.length); // update the iv
            encrypted[counter] = encryptedPart;
            counter++;
        }
        try (FileOutputStream stream = new FileOutputStream(fileName)) {
            stream.write(convert2Dto1DArray(encrypted));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void DecryptWithDES(boolean tripleDes, String fileName) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] cipherTextBytes = GetBytesOfCipherText(); // convert the ciphertext into its byte representation
        SecretKey secretKey = new SecretKeySpec(keyBytes,"DES"); // create secret key to be used iN DES from the key bytes
        // activate DES cipher with ECB encryption mode
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] vector = Arrays.copyOf(iv, 8); // get the initialization vector
        byte[][] decrypted = new byte[cipherTextBytes.length / 8][];
        int counter = 0;
        for (int i = 0; i < cipherTextBytes.length; i += 8){
            byte[] block = Arrays.copyOfRange(cipherTextBytes, i, i + 8);
            byte[] pseudorandomNumber;
            if(tripleDes){
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] firstStep = cipher.doFinal(vector);
                cipher.init(Cipher.DECRYPT_MODE,secretKey);
                byte[] secondStep = cipher.doFinal(firstStep);
                cipher.init(Cipher.ENCRYPT_MODE,secretKey);
                pseudorandomNumber = cipher.doFinal(secondStep);
            }
            else {
                pseudorandomNumber = cipher.doFinal(vector); // generate pseudorandom number by encrypting the initialization vector
            }
            byte[] plainTextBlock = new byte[block.length];
            for (int j = 0; j < plainTextBlock.length; j++){
                // block of plain text
                plainTextBlock[j] = (byte) (block[j] ^ pseudorandomNumber[j]); // cipher text xored with the pseudorandom
            }
            vector = Arrays.copyOf(pseudorandomNumber, pseudorandomNumber.length); //  update iv with current pseudorandom number
            decrypted[counter] = plainTextBlock; // fill out the plain text bytes
            counter++;
        }
        writeToFile(new String(removePadding(convert2Dto1DArray(decrypted)), StandardCharsets.UTF_8), fileName);
    }
}
