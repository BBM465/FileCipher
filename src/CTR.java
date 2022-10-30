import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CTR extends EncryptionModes {
    private final byte[] keyBytes;
    private final byte[] nonce;

    public CTR(byte[] input, Key key) {
        super(input, key);
        this.keyBytes = key.getKeyBytes();
        this.nonce= key.getNonce();
    }

    public void EncryptWithDES(boolean tripleDes, String fileName) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] plainTextBytes = PaddingToPlainText(); // convert the plaintext into its byte representation and apply padding to the representation
        SecretKey secretKey = new SecretKeySpec(keyBytes,"DES"); // create secret key to be used iN DES from the key bytes
        // activate DES cipher with ECB encryption mode
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[][] encrypted = new byte[plainTextBytes.length / 8][];
        int counter = 0;
        byte nonceCount=0;
        for (int i = 0; i < plainTextBytes.length; i += 8){
            nonce[7]= nonceCount;
            nonceCount++;
            byte[] encryptedPart;
            if(tripleDes){
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] firstStep = cipher.doFinal(nonce);
                cipher.init(Cipher.DECRYPT_MODE,secretKey);
                byte[] secondStep = cipher.doFinal(firstStep);
                cipher.init(Cipher.ENCRYPT_MODE,secretKey);
                encryptedPart = cipher.doFinal(secondStep);
            }else {
                encryptedPart = cipher.doFinal(nonce);
            }
            byte[] block = Arrays.copyOfRange(plainTextBytes, i, i + 8);
            byte[] xorBlock = new byte[block.length];
            for (int j = 0; j < block.length; j++){
                xorBlock[j] = (byte) (block[j] ^ encryptedPart[j]);
            }
            encrypted[counter] = xorBlock;
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
        byte[][] decrypted = new byte[cipherTextBytes.length / 8][];
        int counter = 0;
        byte nonceCount=0;
        for (int i = 0; i < cipherTextBytes.length; i += 8){
            nonce[7]= nonceCount;
            nonceCount= (byte) (nonceCount+1);
            byte[] decryptBlock;
            if(tripleDes){
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] firstStep = cipher.doFinal(nonce);
                cipher.init(Cipher.DECRYPT_MODE,secretKey);
                byte[] secondStep = cipher.doFinal(firstStep);
                cipher.init(Cipher.ENCRYPT_MODE,secretKey);
                decryptBlock = cipher.doFinal(secondStep);
            }
            else {
                decryptBlock = cipher.doFinal(nonce); //decrypt the current block
            }
            byte[] block = Arrays.copyOfRange(cipherTextBytes, i, i + 8);
            byte[] xorBlock = new byte[block.length];
            for (int j = 0; j < block.length; j++){
                xorBlock[j] = (byte) (block[j] ^ decryptBlock[j]);
            }
            decrypted[counter] = xorBlock;
            counter++;
        }
        writeToFile(new String(removePadding(convert2Dto1DArray(decrypted)), StandardCharsets.UTF_8), fileName);
    }
}