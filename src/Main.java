import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        FileReader fileReader1 = new FileReader("/Users/imrekosdik/Desktop/Assignment1/src/Key.txt", "/Users/imrekosdik/Desktop/Assignment1/src/Text.txt");
        String input1 = fileReader1.ReadInputFile();
        Key key1 = fileReader1.ReadKeyFile();

        CBC cbc1 = new CBC(input1, key1);
        cbc1.EncryptWithDES();

        FileReader fileReader = new FileReader("/Users/imrekosdik/Desktop/Assignment1/src/Key.txt", "/Users/imrekosdik/Desktop/Assignment1/cbc_encrypt_text.txt");
        String input = fileReader.ReadInputFile();
        Key key = fileReader.ReadKeyFile();

        CBC cbc = new CBC(input, key);
        cbc.DecryptWithDES();
    }
}
