import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Key {
    private String initializationVector;
    private String key;
    private String nonce;


    public byte[] getInitializationVector() {
        return initializationVector.getBytes(StandardCharsets.UTF_8);
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
        return Arrays.copyOfRange(key.getBytes(StandardCharsets.UTF_8), 0, 8);
    }

}

