import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Read {
    private String path;

    public Read(String path){
        this.path = path;
    }

    public List<Mushroom> readFile(){
        List<Mushroom> mushrooms = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.path));
            String line;
            while((line = br.readLine()) != null){
                String[] temporaryArray = line.split(",");
                Mushroom mushroom = new Mushroom(temporaryArray[0], Arrays.copyOfRange(temporaryArray, 1, temporaryArray.length));
                mushrooms.add(mushroom);
            }
            br.close();
        } catch (FileNotFoundException e){
            throw new RuntimeException(e);
        } catch (IOException e){
            throw new RuntimeException(e);
        }


        return mushrooms;
    }

}
