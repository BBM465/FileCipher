import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;

public class FileCipher {
    public static void main(String[] args) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Instant start = Instant.now();
        String log = args[2] + " " + args[4];
        if (args[0].equals("-e")){
            FileReader fileReader = new FileReader(args[7], args[2]);
            Key key = fileReader.ReadKeyFile();
            byte[] input = fileReader.ReadInputFile();
            log += " enc ";
            switch (args[6]){
                case "CBC":
                    CBC cbc = new CBC(input, key);
                    cbc.EncryptWithDES(!args[5].equals("DES"), args[4]);
                    if (args[5].equals("DES"))
                        log += "DES CBC ";
                    else
                        log += "3DES CBC ";
                    break;
                case "CFB":
                    CFB cfb = new CFB(input, key);
                    cfb.EncryptWithDES(!args[5].equals("DES"), args[4]);
                    if (args[5].equals("DES"))
                        log += "DES CFB ";
                    else
                        log += "3DES CFB ";
                    break;
                case "OFB":
                    OFB ofb = new OFB(input, key);
                    ofb.EncryptWithDES(!args[5].equals("DES"), args[4]);
                    if (args[5].equals("DES"))
                        log += "DES OFB ";
                    else
                        log += "3DES OFB ";
                    break;
                default:
                    CTR ctr = new CTR(input, key);
                    ctr.EncryptWithDES(!args[5].equals("DES"), args[4]);
                    if (args[5].equals("DES"))
                        log += "DES CTR ";
                    else
                        log += "3DES CTR ";
            }
        }
        else if (args[1].equals("-d")){
            FileReader fileReader = new FileReader(args[7], args[2]);
            Key key = fileReader.ReadKeyFile();
            byte[] input = fileReader.ReadInputFile();
            log += " dec ";
            switch (args[6]) {
                case "CBC":
                    CBC cbc = new CBC(input, key);
                    cbc.DecryptWithDES(!args[5].equals("DES"), args[4]);
                    if (args[5].equals("DES"))
                        log += "DES CBC ";
                    else
                        log += "3DES CBC ";
                    break;
                case "CFB":
                    CFB cfb = new CFB(input, key);
                    cfb.DecryptWithDES(!args[5].equals("DES"), args[4]);
                    if (args[5].equals("DES"))
                        log += "DES CBC ";
                    else
                        log += "3DES CBC ";
                    break;
                case "OFB":
                    OFB ofb = new OFB(input, key);
                    ofb.DecryptWithDES(!args[5].equals("DES"), args[4]);
                    if (args[5].equals("DES"))
                        log += "DES CBC ";
                    else
                        log += "3DES CBC ";
                    break;
                default:
                    CTR ctr = new CTR(input, key);
                    ctr.DecryptWithDES(!args[5].equals("DES"), args[4]);
                    if (args[5].equals("DES"))
                        log += "DES CBC ";
                    else
                        log += "3DES CBC ";
            }
        }
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        log += timeElapsed.toMillis() + "\n";
        writeToLogFile(log);
    }

    public static void writeToLogFile(String log) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("run.log", true));
        out.write(log);
        out.close();
    }
}

