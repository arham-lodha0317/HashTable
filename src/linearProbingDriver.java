import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class linearProbingDriver {

    public static void main(String[] args) throws IOException {

        ArrayList<String[]> store = new ArrayList<>();
        ArrayList<Integer> success = new ArrayList<>();
        ArrayList<Integer> unsuccess = new ArrayList<>();

        Scanner file = new Scanner(new File("Large Data Set.txt"));

        while(file.hasNext()){
            store.add(file.nextLine().split(" "));
        }

        file = new Scanner(new File("Successful Search.txt"));

        while(file.hasNext()){
            success.add(Integer.parseInt(file.nextLine().split(" ")[0]));
        }

        file = new Scanner(new File("Unsuccessful Search.txt"));

        while(file.hasNext()){
            unsuccess.add(Integer.parseInt(file.nextLine().split(" ")[0]));
        }

        ArrayList<Double> collisions1 = new ArrayList<>();
        ArrayList<Double> storeTime1 = new ArrayList<>();

        ArrayList<Double> collisions2 = new ArrayList<>();
        ArrayList<Double> storeTime2 = new ArrayList<>();

        ArrayList<Double> collisions3 = new ArrayList<>();
        ArrayList<Double> storeTime3 = new ArrayList<>();

        double[] loadFactor = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};

        for (double d: loadFactor) {

            HashTable hashTable = new HashTable((int) (store.size() / d));

            long start = System.currentTimeMillis();
            for (String[] s: store) {
                int key = Integer.parseInt(s[0]);
                String value = s[1] + s[2];

                hashTable.put(key, value);
            }
            long end = System.currentTimeMillis();
            collisions1.add(((double) (hashTable.getProbes())/ store.size()));
            hashTable.setProbes(0);
            storeTime1.add(((double) (end - start) / store.size()));
            System.out.println("Finished storing");

            start = System.currentTimeMillis();
            for (Integer i: success) {
                hashTable.get(i);
            }
            end = System.currentTimeMillis();
            System.out.println("Finished getting success");

            collisions2.add( (( (double) hashTable.getProbes())/ success.size()));
            hashTable.setProbes(0);
            storeTime2.add(( (double)(end - start) / success.size()));

            start = System.currentTimeMillis();
            for (Integer i: unsuccess) {
                hashTable.get(i);
            }
            end = System.currentTimeMillis();
            System.out.println("Finished getting unsuccess");

            collisions3.add((double) ((double) (hashTable.getProbes())/ success.size()));
            hashTable.setProbes(0);
            storeTime3.add((double) ( (double)(end - start) / success.size()));

            System.out.println("Finished load: " + d);
        }

        File fileName = new File("results.csv");
        int version = 0;
        while (fileName.exists()){
            version++;
            fileName = new File("results" + version + ".csv");
        }

        fileName.createNewFile();


        try {

            FileWriter output = new FileWriter(fileName);

            //adding a header
            output.write("Type of Hashing , Linear Probing \n");
            output.flush();

            //adding a header
            output.write("Hash Function used , Integer \n");
            output.flush();

            output.write("Building the Hash Table \n");
            output.flush();

            output.write("Load Factors a , Average Put Time \n");
            output.flush();

            for (int i = 0; i < loadFactor.length; i++) {
                output.write(loadFactor[i] + " , " + storeTime1.get(i) + "\n");
                output.flush();
            }

            output.write("Load Factors a , Number of average probes \n");
            output.flush();

            for (int i = 0; i < loadFactor.length; i++) {
                output.write(loadFactor[i] + " , " + collisions1.get(i) + "\n");
                output.flush();
            }

            output.write("Getting successful from the Hash Table \n");
            output.flush();

            output.write("Load Factors a , Average Get Time \n");
            output.flush();

            for (int i = 0; i < loadFactor.length; i++) {
                output.write(loadFactor[i] + " , " + storeTime2.get(i) + "\n");
                output.flush();
            }

            output.write("Load Factors a , Number of average probes + \n");
            output.flush();

            for (int i = 0; i < loadFactor.length; i++) {
                output.write(loadFactor[i] + " , " + collisions2.get(i) + "\n");
                output.flush();
            }

            output.write("Getting unsuccessful from the Hash Table \n");
            output.flush();

            output.write("Load Factors a , Average Get Time \n");
            output.flush();

            for (int i = 0; i < loadFactor.length; i++) {
                output.write(loadFactor[i] + " , " + storeTime3.get(i) + "\n");
                output.flush();
            }

            output.write("Load Factors a , Number of average probes \n");
            output.flush();

            for (int i = 0; i < loadFactor.length; i++) {
                output.write(loadFactor[i] + " , " + collisions3.get(i) + "\n");
                output.flush();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
