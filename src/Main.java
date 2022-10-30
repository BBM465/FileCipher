import javax.crypto.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        FileReader fileReader1 = new FileReader("/Users/imrekosdik/Desktop/Assignment1/src/Key.txt", "/Users/imrekosdik/Desktop/Assignment1/src/Text.txt");
        byte[] input1 = fileReader1.ReadInputFile();
        Key key1 = fileReader1.ReadKeyFile();

        CFB cbc1 = new CFB(input1, key1);
        cbc1.EncryptWithDES(false);

        FileReader fileReader = new FileReader( "/Users/imrekosdik/Desktop/Assignment1/src/Key.txt", "/Users/imrekosdik/Desktop/Assignment1/cfb_encrypt_text.txt");
        byte[] input = fileReader.ReadInputFile();
        Key key = fileReader.ReadKeyFile();

        CFB cbc = new CFB(input, key);
        cbc.DecryptWithDES(false);

    }
}

