import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    private final String keyFile;
    private final String inputFile;


    public FileReader(String keyFile, String inputFile) {
        this.keyFile = keyFile;
        this.inputFile = inputFile;
    }

    public Key ReadKeyFile(){
        Key key = new Key();
        try{
            File file = new File(keyFile);
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(" - ");
            while(scanner.hasNext()){
                key.setInitializationVector(scanner.next());
                key.setKey(scanner.next());
                key.setNonce(scanner.next());
            }
            scanner.close();
        }
        catch (FileNotFoundException e){
           System.out.println("Key file is not found!");
           e.printStackTrace();
        }
        System.out.println(key.getInitializationVector() + " " + key.getKey() + " " + key.getNonce());
        return key;
    }

    public String ReadInputFile(){
        String text = "";
        try{
            File file = new File(inputFile);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                text = text.concat(line);
                if (scanner.hasNextLine()){
                   text = text.concat("\n");
                }
            }
            scanner.close();
        }
        catch (FileNotFoundException e){
            System.out.println("Input file is not found!");
            e.printStackTrace();
        }
        System.out.println(text);
        return text;
    }
}
