package kuka;
import java.util.ArrayList;
import java.util.Objects;

public class MyHashTable <K, V>{
    private static class HashNode<K, V>{
        private K key;
        private V val;
        private HashNode<K, V> next;

        final int hashCode;

        public HashNode(){
            hashCode = 0;
            next = null;
        }
        public HashNode(K key, V val, int hashCode){
            this.key = key;
            this.val = val;
            this.hashCode = hashCode;
        }

        @Override
        public String toString() {
            return "{%s %s}".formatted ( key , val );
        }
    }

    private ArrayList<HashNode<K,V>> chainArray;
    private int mSize = 11;
    private int size;

    public MyHashTable() {
        chainArray = new ArrayList<>();
        size = 0;
        int i = 0;
        do {
            chainArray.add ( null );
            ++i;
        } while (i < mSize);
    }

    public MyHashTable(int mSize) {
        chainArray = new ArrayList<>();
        this.mSize = mSize;
        size = 0;
        int i = 0;
        if (i < mSize) {
            do {
                chainArray.add ( null );
                i++;
            } while (i < mSize);
        }
    }

    private int hash(K key){
        return Objects.hashCode(key);
    }

    private int getIndex(K key){
        int hash = hash(key);
        int index = hash % mSize;
        if (index >= 0) {
            return index;
        }
        index = index * -1;
        return index;
    }

    public void put(K key, V val){
        int index = getIndex(key);
        int hash = hash(key);

        HashNode<K, V> head = chainArray.get(index);

        while(head != null){
            if (head.hashCode == hash)
                if (head.key.equals ( key )) {
                    head.val = val;
                    return;
                }
            head = head.next;
        }
        size++;
        head = chainArray.get(index);
        HashNode<K, V> newNode = new HashNode<> ( key , val , hash );
        newNode.next = head;
        chainArray.set(index, newNode);

        if (!((double) (size / mSize) > 0.6)) {
            return;
        }
        System.out.printf ( "%s %s%n" , key , val );
        mSize *= 2;
        ArrayList<HashNode<K, V>> oldList = chainArray;
        chainArray = new ArrayList<>();
        size = 0;
        for (int i = 0; i < mSize; i ++){
            chainArray.add(null);
        }
        for (HashNode<K, V> start : oldList) {
            while (start != null) {
                put ( start.key , start.val );
                start = start.next;
            }
        }

    }

    public void print(){
        int i = 0;
        if (i < mSize) {
            do {
                HashNode<K, V> head = chainArray.get ( i );
                if (head != null) {
                    while (head != null) {
                        System.out.printf ( "{%s,%s}  " , head.key , head.val );
                        head = head.next;
                    }
                    System.out.println ();
                }
                ++i;
            } while (i < mSize);
        }
    }

    public V get(K key){
        int index = getIndex(key);
        int hash = hash(key);
        HashNode<K, V> head = chainArray.get(index);

        if (head != null) {
            do {
                if (!head.key.equals ( key ) || head.hashCode != hash) {
                    head = head.next;
                } else {
                    return head.val;
                }
            } while (head != null);
        }
        return null;
    }