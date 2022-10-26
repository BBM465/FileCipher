import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Key {
    private String initializationVector;
    private String key;
    private String nonce;


    public byte[] getInitializationVector() {
        return initializationVector.getBytes();
    }

    public String getKey() {
        return key;
    }

    public String getNonce(){
        return nonce;
    }

    public void setInitializationVector(String initializationVector) {
        this.initializationVector = initializationVector;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public byte[] getKeyBytes() {
        // shuffle the characters of the key
        List<String> letters = Arrays.asList(key.split(""));
        Collections.shuffle(letters);
        StringBuilder shuffled = new StringBuilder();
        for (String letter : letters)
            shuffled.append(letter);
        // get the first 8 bytes of the shuffled key
        return Arrays.copyOfRange(shuffled.toString().getBytes(), 0,8 );
    }

}

