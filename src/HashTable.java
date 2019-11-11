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

        probes++;

        if(size == capacity){
            throw new IllegalStateException("HashTable is small");
        }

        int hc = key.hashCode();
        int index = hc % capacity;

        if(hashTable[index] == null){
            hashTable[index] = new Entry(key, value);
            size++;
            return null;
        }

        while (true){
            probes++;

            if(hashTable[index] == null){
                hashTable[index] = new Entry(key, value);
                size++;
                return null;
            }
            else if(hashTable[index].isRemoved()){
                hashTable[index] = new Entry(key, value);
                return null;
            }
            else if(hashTable[index].getKey().equals(key)){
                Entry temp = hashTable[index];
                hashTable[index] = new Entry(key, value);
                return temp;
            }
            index++;
            if(index >= capacity){
                index = 0;
            }
        }

    }

    Object get(Object key){
        int hc = key.hashCode();
        int index = hc % capacity;
        probes++;

        if(hashTable[index] == null){
            return null;
        }
        else if(hashTable[index].getKey().equals(key) && !hashTable[index].isRemoved()){
            return hashTable[index].getValue();
        }

        while (hashTable[index] != null && !hashTable[index].getKey().equals(key)){
            probes++;

            index++;

            if(index == capacity){
                index = 0;
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
