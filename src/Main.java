public class Main {
    public static void main(String[] args){
        FileReader fileReader = new FileReader("/Users/imrekosdik/Desktop/Assignment1/src/Key.txt", "/Users/imrekosdik/Desktop/Assignment1/src/Text.txt");
        String input = fileReader.ReadInputFile();
        Key key = fileReader.ReadKeyFile();

        EncryptionModes encryptionModes = new EncryptionModes(input, key);
        encryptionModes.Padding();
    }
}
