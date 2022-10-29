import javax.crypto.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        FileReader fileReader1 = new FileReader("/Users/imrekosdik/Desktop/Assignment1/src/Key.txt", "/Users/imrekosdik/Desktop/Assignment1/src/Text.txt");
        byte[] input1 = fileReader1.ReadInputFile();
        Key key1 = fileReader1.ReadKeyFile();

        OFB cbc1 = new OFB(input1, key1);
        cbc1.EncryptWithDES();

        FileReader fileReader = new FileReader( "/Users/imrekosdik/Desktop/Assignment1/src/Key.txt", "/Users/imrekosdik/Desktop/Assignment1/ofb_encrypt_text.txt");
        byte[] input = fileReader.ReadInputFile();
        Key key = fileReader.ReadKeyFile();

        OFB cbc = new OFB(input, key);
        cbc.DecryptWithDES();

    }
}

