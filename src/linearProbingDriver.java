import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class linearProbingDriver {

    public static void main(String[] args) throws IOException {

        ArrayList<String[]> store = new ArrayList<>();
        ArrayList<String[]> success = new ArrayList<>();
        ArrayList<String[]> unsuccess = new ArrayList<>();

        Scanner file = new Scanner(new File("Large Data Set.txt"));

        while(file.hasNext()){
            store.add(file.nextLine().split(" "));
        }

        file = new Scanner(new File("Successful Search.txt"));

        while(file.hasNext()){
            success.add(file.nextLine().split(" "));
        }

        file = new Scanner(new File("Unsuccessful Search.txt"));

        while(file.hasNext()){
            unsuccess.add(file.nextLine().split(" "));
        }

        File fileName = new File("results.csv");
        int version = 0;
        while (fileName.exists()){
            version++;
            fileName = new File("results" + version + ".csv");
        }

        fileName.createNewFile();

        ArrayList<Double> loadFactors = new ArrayList<>();
        ArrayList<Double> collisions1 = new ArrayList<>();
        ArrayList<Double> storeTime1 = new ArrayList<>();

        ArrayList<Double> collisions2 = new ArrayList<>();
        ArrayList<Double> storeTime2 = new ArrayList<>();

        ArrayList<Double> collisions3 = new ArrayList<>();
        ArrayList<Double> storeTime3 = new ArrayList<>();

        for (double i = 0.1; i < 1; i += 0.1) {

            loadFactors.add(i);
            System.out.println("Array size = " + (int) Math.round(500000/i));
            HashTable hashTable = new HashTable((int) Math.round(500000/i));

            long start = System.currentTimeMillis();
            for (String[] s: store) {
                hashTable.put(Integer.parseInt(s[0]), s[1] + " " + s[2]);
            }
            long end = System.currentTimeMillis();

            storeTime1.add( ((double)(end - start)/500000));
            collisions1.add((double) hashTable.getProbes()/500000);
            System.out.println("Collision: " + (double) hashTable.getProbes()/500000);
            hashTable.setProbes(0);
            System.out.println("Average time: " + ((double)(end - start)/500000));
            System.out.println();
            System.out.println();

            start = System.currentTimeMillis();
            for (String[] s: success) {
                hashTable.get(Integer.parseInt(s[0]));
            }
            end = System.currentTimeMillis();

            collisions2.add(((double)hashTable.getProbes() )/ 10000);
            storeTime2.add(((double) (end-start)/10000));
            System.out.println("Collision: " + (double) hashTable.getProbes() / 10000);
            hashTable.setProbes(0);
            System.out.println("Average time: " + ((double) ((end - start) / 10000)));
            System.out.println();
            System.out.println();

            start = System.currentTimeMillis();
            for (String[] s: unsuccess) {
                hashTable.get(Integer.parseInt(s[0]));
            }
            end = System.currentTimeMillis();

            collisions3.add(((double)hashTable.getProbes() )/ 10000);
            storeTime3.add((double)(end-start)/10000);
            System.out.println("Collision: " + (double) hashTable.getProbes() / 10000);
            hashTable.setProbes(0);
            System.out.println("Average time: " + ((double) (end - start)/ 10000));
            System.out.println();
            System.out.println();

        }

        try{

            FileWriter output = new FileWriter(fileName);

            CSVWriter writer = new CSVWriter(output);

            //adding a header
            String[] typeOfProbing = new String[] {"Type of Hashing Linear" , "Probing" };
            writer.writeNext(typeOfProbing);

            //adding a header
            String[] hashFunction = new String[] {"Hash function used" , "Integer" };
            writer.writeNext(hashFunction);

            //Adding elements
            ArrayList<String[]> probing = new ArrayList<>();
            probing.add(new String[] {"Load Factor" , "Average Time"});
            writer.writeNext(probing.get(0));


        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
