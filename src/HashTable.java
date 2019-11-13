import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class HashTable {

    private int capacity;
    private Entry[] hashTable;
    private int size;
    private int probes = 0;

    HashTable(){
        capacity = 101;
        hashTable = new Entry[capacity];
        size = 0;
    }

    HashTable(int initcap){
        capacity = initcap;
        hashTable = new Entry[capacity];
        size = 0;
    }

    Object put(Object key, Object value){
        if(size == capacity){
            throw new IllegalStateException("HashTable is full");
        }

        int hc = key.hashCode();
        int index = hc % capacity;
        Entry temp = hashTable[index];

        if(temp == null){
            hashTable[index] = new Entry(key, value);
            size++;
            probes++;
            return null;
        }
        else if(!temp.isRemoved()){

            if(temp.getKey().equals(key)){
                hashTable[index] = new Entry(key, value);
                probes++;
                return temp.getValue();
            }
            else {

                for (int i = index + 1; i < hashTable.length; i++) {

                    if(hashTable[i] == null){
                        hashTable[i] = new Entry(key, value);
                        size++;
                        probes++;
                        return null;
                    }
                    else if (hashTable[i].isRemoved()){
                        hashTable[i] = new Entry(key, value);
                        probes++;
                        int j = i++;

                        while(hashTable[j] != null && !hashTable[j].getKey().equals(key)){
                            j++;
                            if(j == hashTable.length){
                                j = 0;
                            }
                            probes++;
                        }

                        if(hashTable[i] == null){
                            size--;
                            return null;
                        }
                        else if(hashTable[i].getKey().equals(key)) {
                            hashTable[i].setRemoved(true);
                            return hashTable[i].getValue();
                        }

                    }

                    if(i == hashTable.length){
                        i =0;
                    }

                    probes++;

                }

            }

        }

        return false;
    }

    Object get(Object key){
        int hc = key.hashCode();
        int index = hc % capacity;
        probes++;
        int loops = 0;

        if(hashTable[index] == null){
            return null;
        }
        else if(hashTable[index].getKey().equals(key) && !hashTable[index].isRemoved()){
            return hashTable[index].getValue();
        }
        while (hashTable[index] != null && !hashTable[index].getKey().equals(key) && loops < 2){
            probes++;

            index++;

            if(index == capacity){
                index = 0;
                loops++;
            }
        }
        if(hashTable[index] != null && hashTable[index].getKey().equals(key)){
            probes++;
            return hashTable[index];
        }

        return null;
    }

    Object remove(Object key){
        int hc = key.hashCode();
        int index = hc % capacity;

        if(hashTable[index] == null){
            return null;
        }
        else if(hashTable[index].getKey().equals(key)){
            hashTable[index].setRemoved(true);
            size--;
            return hashTable[index];
        }

        while (hashTable[index] != null && !hashTable[index].getKey().equals(key)){
            index++;

            if(index == capacity){
                    index = 0;
            }
        }
        if(hashTable[index] != null && hashTable[index].getKey().equals(key)){
            hashTable[index].setRemoved(true);
            size--;
            return hashTable[index];
        }

        return null;
    }

    public int getProbes() {
        return probes;
    }

    public void setProbes(int probes) {
        this.probes = probes;
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < hashTable.length; i++) {
            if(hashTable[i] != null && !hashTable[i].isRemoved()){
                str.append(i).append(":\t\t").append(hashTable[i]).append("\n");
            }
            else if(hashTable[i] != null && hashTable[i].isRemoved()){
                str.append(i).append(":\t\t").append("dummy").append("\n");
            }
        }

        return str.toString();

    }

    private static class Entry{
        Object key;
        Object value;
        boolean removed;

        Entry(){
            key = null;
            value = null;
            removed = false;
        }

        Entry(Object key, Object value){
            this.key = key;
            this.value = value;
            removed = false;
        }

        public Object getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }


        public String toString(){
            return key + "\t" + value;
        }

        public boolean isRemoved() {
            return removed;
        }

        public void setRemoved(boolean removed) {
            this.removed = removed;
        }

        public void setKey(Object key) {
            this.key = key;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

}
