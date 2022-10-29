import java.util.Arrays;

public class Key {
    private byte[] initializationVector;
    private byte[] key;
    private byte[] nonce;


    public byte[] getInitializationVector() {
        return initializationVector;
    }

    public byte[] getKey() {
        return key;
    }

    public byte[] getNonce(){
        return nonce;
    }

    public void setInitializationVector(byte[] initializationVector) {
        this.initializationVector = initializationVector;
    }

    public void setNonce(byte[] nonce) {
        this.nonce = nonce;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public byte[] getKeyBytes() {
        return Arrays.copyOfRange(key, 0, 8);
    }

}