public class Key {
    private String initializationVector;
    private String key;
    private String nonce;


    public String getInitializationVector() {
        return initializationVector;
    }

    public String getKey() {
        return key;
    }

    public String getNonce() {
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
}
