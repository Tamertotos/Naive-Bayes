import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Read data = new Read("agaricus-lepiota.data");
        Read testData = new Read("agaricus-lepiota.test.data");
        List<Mushroom> mushroomsData = new ArrayList<>(data.readFile());
        List<Mushroom> mushroomsTest = new ArrayList<>(testData.readFile());

        Bayes bayes = new Bayes(mushroomsData, mushroomsTest);
        bayes.evaluate();


    }
}
