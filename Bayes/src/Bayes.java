import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bayes {
    private List<Mushroom> mushroomList;
    private List<Mushroom> mushroomTestList;
    private Map<String, Map<Integer, Map<String, Integer>>> map;
    private int eCount;
    private int pCount;

    public Bayes(List<Mushroom> mushroomList, List<Mushroom> mushroomTestList) {
        this.mushroomList = mushroomList;
        this.mushroomTestList = mushroomTestList;
        this.map = new HashMap<>();
        createMap();
    }


    public void createMap(){

        for(int i = 0; i < mushroomList.size(); i++){
            String key = mushroomList.get(i).label();

            if (key.equals("p")){
                this.pCount +=1;
            } else {
                this.eCount +=1;
            }

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

        int truePositive = 0;
        int trueNegative = 0;
        int falsePositive = 0;
        int falseNegative = 0;

        for (int i = 0; i < mushroomTestList.size(); i++ ){
            double eValue = (double) this.eCount / mushroomList.size();
            double pValue = (double) this.pCount/ mushroomList.size();

            for (int j = 0; j < map.get("e").size(); j++){
                double eNumerator = 0;
                double pNumerator = 0;


                double eDenominator = 0;
                double pDenominator = 0;

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


               for (int elem : map.get("e").get(j).values()){
                   eDenominator += elem;
               }

               for (int elem: map.get("p").get(j).values()){
                   pDenominator += elem;
               }

               eValue *= eNumerator/eDenominator;
               pValue *= pNumerator/pDenominator;
            }



            String prediction = "";
            if (eValue > pValue){
               prediction = "e";
            } else {
                prediction = "p";
            }


            if (prediction.equals("p") && mushroomTestList.get(i).label().equals("p")){
                truePositive += 1;
            }  else if (prediction.equals("e")&& mushroomTestList.get(i).label().equals("e")){
                trueNegative += 1;
            } else if (prediction.equals("p") && mushroomTestList.get(i).label().equals("e")){
                falsePositive += 1;
            } else if (prediction.equals("e") && mushroomTestList.get(i).label().equals("p")){
                falseNegative += 1;
            }
        }

        double accuracy =  (double)(truePositive + trueNegative) / mushroomTestList.size()  * 100;
        double precision =  (double) truePositive / (truePositive + falsePositive);
        double recall = (double) truePositive / (truePositive + falseNegative);
        double fMeasure = (double) 2*precision*recall / (precision + recall);

        System.out.println(accuracy + "% accuracy ");
        System.out.println("Precision is: " + precision );
        System.out.println("Recall is: " + recall);
        System.out.println("F-measure is : " + fMeasure);
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
