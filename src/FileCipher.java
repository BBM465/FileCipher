import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileCipher {
    public static void main(String[] args) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (args[1].equals("-e")){
            FileReader fileReader = new FileReader(args[8], args[3]);
            Key key = fileReader.ReadKeyFile();
            byte[] input = fileReader.ReadInputFile();
            switch (args[7]){
                case "CBC":
                    CBC cbc = new CBC(input, key);
                    cbc.EncryptWithDES(args[6].equals("DES"), args[5]);
                    break;
                case "CFB":
                    CFB cfb = new CFB(input, key);
                    cfb.EncryptWithDES(args[6].equals("DES"), args[5]);
                    break;
                case "OFB":
                    OFB ofb = new OFB(input, key);
                    ofb.EncryptWithDES(args[6].equals("DES"), args[5]);
                    break;
                default:
                    System.out.println("here");
                    CTR ctr = new CTR(input, key);
                    ctr.EncryptWithDES(args[6].equals("DES"), args[5]);
            }
        }
        else if (args[1].equals("-d")){
            FileReader fileReader = new FileReader(args[8], args[3]);
            Key key = fileReader.ReadKeyFile();
            byte[] input = fileReader.ReadInputFile();
            switch (args[7]){
                case "CBC":
                    CBC cbc = new CBC(input, key);
                    cbc.DecryptWithDES(args[6].equals("DES"), args[5]);
                    break;
                case "CFB":
                    CFB cfb = new CFB(input, key);
                    cfb.DecryptWithDES(args[6].equals("DES"), args[5]);
                    break;
                case "OFB":
                    OFB ofb = new OFB(input, key);
                    ofb.DecryptWithDES(args[6].equals("DES"), args[5]);
                    break;
                default:
                    CTR ctr = new CTR(input, key);
                    ctr.DecryptWithDES(args[6].equals("DES"), args[5]);
            }
        }
    }
}

