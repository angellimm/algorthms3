package com.example;


import java.util.Iterator;

public class HashMap<K, V> implements Iterable<HashMap.Entity> {

    private static final int INIT_BUCKET_COUNT = 16;
    private static final  double LOAD_FACTOR = 0.5;

    private Bucket[] buckets;

    private int size;

    @Override
    public Iterator<HashMap.Entity> iterator() {
        return new HashMapIterator();

    }

    class HashMapIterator implements Iterator<HashMap.Entity>{
        int countArray = 0;
        int valuesCounter = 0;
        Iterator<Bucket.Node> subIterator = null;

        @Override
        public boolean hasNext() {
            return valuesCounter < size || (subIterator != null && subIterator.hasNext());
        }

        private boolean moveToNextCell(){
            countArray++;
            while (countArray < buckets.length && (buckets[countArray] == null || buckets[countArray].head == null)) {
                countArray++;
            }
            return countArray < buckets.length;
        }

        @Override
        public Entity next() {
            if (subIterator == null || !subIterator.hasNext()) {
                if (!moveToNextCell()) {
                    throw new java.util.NoSuchElementException();
                }
                subIterator = buckets[countArray].nodeIterator();
            }
            valuesCounter++;
            return subIterator.next().get();
        }
    }


    class Entity {
        K key;
        V value;

        public V get() {
            return value;
        }
    }

    class Bucket {

        Node head;

        class Node {
            Node next;
            Entity value;

            public Entity get() {
                return value;
            }
        }

        public V add(Entity entity) {
            Node node = new Node();
            node.value = entity;

            if (head == null) {
                head = node;
                return null;
            }
            Node currentNode = head;
            while (true) {
                if (currentNode.value.key.equals(entity.key)) {
                    V buf = currentNode.value.value;
                    currentNode.value.value = entity.value;
                    return buf;
                }
                if (currentNode.next != null) {
                    currentNode = currentNode.next;
                } else {
                    currentNode.next = node;
                    return null;
                }
            }
        }

        public  V remove(K key){
            if (head == null)
                return null;
            if (head.value.key.equals(key)){
                V buf = head.value.value;
                head = head.next;
                return  buf;
            }
            else {
                Node node = head;
                while(node.next != null){
                     if (node.next.value.key.equals(key)){
                         V buf = node.next.value.value;
                         node.next = node.next.next;
                         return  buf;
                     }
                    node = node.next;
                }
                return  null;
            }
        }


        public V get(K key) {
            Node node = head;
            while (node != null) {
                if (node.value.key.equals(key))
                    return node.value.value;
                node = node.next;

            }
            return null;
        }
        public Iterator<Node> nodeIterator() {
            return new Iterator<Node>() {
                Node currentNode = head;

                @Override
                public boolean hasNext() {
                    return currentNode != null;
                }

                @Override
                public Node next() {
                    if (!hasNext()) {
                        throw new java.util.NoSuchElementException();
                    }
                    Node temp = currentNode;
                    currentNode = currentNode.next;
                    return temp;
                }
            };
        }
    }


    private int calculateBucketIndex(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    private  void recalculate(){
        size = 0;
        Bucket[] old = buckets;
        buckets = new HashMap.Bucket[old.length * 2];
        for( int i =0; i < old.length; i++) {
            Bucket bucket = old[i];
            if (bucket != null){
                Bucket.Node node = bucket.head;
                while (node != null){
                    put(node.value.key, node.value.value);
                    node = node.next;
                }
            }
        }

    }



    public V put(K key, V value) {

    if(size >= buckets.length * LOAD_FACTOR){
        recalculate();
    }


        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null) {
            bucket = new Bucket();
            buckets[index] = bucket;
        }

        Entity entity = new Entity();
        entity.key = key;
        entity.value = value;

        V buf = bucket.add(entity);
        if (buf == null) {
            size++;

        }
        return buf;
    }

    public V get(K key) {
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null)
            return null;

        return bucket.get(key);

    }

    public V remove(K key){
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null)
            return  null;
        V buf = bucket.remove(key);
        if(buf != null){
            size--;

        } return  buf;

}



    public HashMap(){
        buckets = new HashMap.Bucket[INIT_BUCKET_COUNT];

    }
    public HashMap(int initCount){
        buckets = new HashMap.Bucket[initCount];

    }

}






