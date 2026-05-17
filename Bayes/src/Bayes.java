import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bayes {
    private List<Mushroom> mushroomList;
    private List<Mushroom> mushroomTestList;
    private Map<String, Map<Integer, Map<String, Integer>>> map;

    public Bayes(List<Mushroom> mushroomList, List<Mushroom> mushroomTestList) {
        this.mushroomList = mushroomList;
        this.mushroomTestList = mushroomTestList;
        this.map = new HashMap<>();
    }


    public void createMap(){

        for(int i = 0; i < mushroomList.size(); i++){
            String key = mushroomList.get(i).label();

            if (!map.containsKey(key)){
                this.map.put(key, new HashMap<Integer, Map<String, Integer>>());
            }

            for (int j = 0; j < mushroomList.get(i).attributes().length; j++){
                Map<Integer, Map<String, Integer>> innerMap = map.get(key);

                if(!innerMap.containsKey(j)){
                    innerMap.put(j, new HashMap<String, Integer>());
                }

                Map<String, Integer> innerMostMap = innerMap.get(j);

                if (!innerMostMap.containsKey(mushroomList.get(i).attributes()[j])){
                    innerMostMap.put(mushroomList.get(i).attributes()[j], 1);
                } else {
                    innerMostMap.put(mushroomList.get(i).attributes()[j], innerMostMap.get(mushroomList.get(i).attributes()[j]) + 1);
                }
            }

        }
    }

    public void evaluate(){
        for (int i = 0; i < mushroomTestList.size(); i++ ){
            double eValue = 1;
            double pValue = 1;

            for (int j = 0; j < map.get("e").size(); j++){
                double eNumerator = 0;
                double pNumerator = 0;

                if ( map.get("e").get(j).get(mushroomTestList.get(i).attributes()[j]) == null){
                    eNumerator += 1;
                }else {
                    eNumerator += map.get("e").get(j).get(mushroomTestList.get(i).attributes()[j]);
                }


                 if (map.get("p").get(j).get(mushroomTestList.get(i).attributes()[j]) == null){
                     pNumerator += 1;
                 } else {
                     pNumerator += map.get("p").get(j).get(mushroomTestList.get(i).attributes()[j]);
                 }

                double eDenominator = 0;
                double pDenominator = 0;

               for (int elem : map.get("e").get(j).values()){
                   eDenominator += elem;
               }

               for (int elem: map.get("p").get(j).values()){
                   pDenominator += elem;
               }

               eValue *= eNumerator/eDenominator;
               pValue *= pNumerator/pDenominator;
            }

            if (eValue > pValue){
                System.out.println("IT IS EDIBLE");
            } else {
                System.out.println("IT IS POISONOUS");
            }

        }
    }

    @Override
    public String toString() {
        return "Bayes{" +
                "mushroomList=" + mushroomList +
                ", mushroomTestList=" + mushroomTestList +
                ", map=" + map +
                '}';
    }
}
