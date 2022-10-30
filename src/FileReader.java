import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileReader {
    private final String keyFile;
    private final String inputFile;


    public FileReader(String keyFile, String inputFile) {
        this.keyFile = keyFile;
        this.inputFile = inputFile;
    }

    public Key ReadKeyFile(){
        Key key = new Key();
        List<Integer> byteList =new ArrayList<>();
        try{
            FileInputStream fin=new FileInputStream(keyFile);
            int i=0;

            while(true){
                i=fin.read();
                if(i!=32&&i!=-1)
                {
                    byteList.add(i);

                }else{
                    break;
                }
            }
            Iterator<Integer> iterator = byteList.iterator();
            byte[] byteArray =new byte[byteList.size()];
            int index=0;
            while(iterator.hasNext())
            {
                Integer j = iterator.next();
                byteArray[index] = j.byteValue();
                index++;
            }
            key.setInitializationVector(byteArray);
            byteList.clear();
            fin.read();
            fin.read();
            index=0;
            while(true){
                i=fin.read();
                if(i!=32&&i!=-1)
                {
                    byteList.add(i);
                }else{
                    break;
                }
            }
            iterator = byteList.iterator();
            byte[] byteArray2 =new byte[byteList.size()];
            while(iterator.hasNext())
            {
                Integer j = iterator.next();
                byteArray2[index] = j.byteValue();
                index++;
            }
            key.setKey(byteArray2);
            byteList.clear();
            fin.read();
            fin.read();
            index=0;
            while(true){
                i=fin.read();
                if(i!=32&&i!=-1)
                {
                    byteList.add(i);

                }else{
                    break;
                }
            }
            iterator = byteList.iterator();
            byte[] byteArray3 =new byte[byteList.size()];
            while(iterator.hasNext())
            {
                Integer j = iterator.next();
                byteArray3[index] = j.byteValue();
                index++;
            }
            key.setNonce(byteArray3);
        }catch(Exception e){System.out.println(e);}
        return key;
    }

    public byte[] ReadInputFile() throws IOException {
        List<Integer> byteList =new ArrayList<>();
        try{
            FileInputStream fin=new FileInputStream(inputFile);
            int i=0;
            while((i=fin.read())!=-1){
                byteList.add(i);

            }
            fin.close();
        }catch(Exception e){
            System.out.println(e);
        }
        Iterator<Integer> iterator = byteList.iterator();
        byte[] byteArray =new byte[byteList.size()];
        int index=0;
        while(iterator.hasNext())
        {
            Integer i = iterator.next();
            byteArray[index] = i.byteValue();
            index++;
        }
        return byteArray;
    }
}