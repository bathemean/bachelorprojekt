package main;

import javafx.util.Pair;

import java.util.ArrayList;

public class MinHeap {

    private ArrayList<Edge> heap;

    public ArrayList<String> mapping;

    public MinHeap() {
        this.heap = new ArrayList<Edge>();
        this.mapping = new ArrayList<String>();
    }

    /**
     * Adds an element to the heap.
     * @param v The element to be added.
     */
    public void add(Edge v){
        this.heap.add(v);
        this.mapping.add(v.getSource());
    }

    /**
     * Adds an element to the head of the heap.
     * @param v The element to be added.
     */
    public void addHead(Edge v){
        this.heap.add(0, v);
        this.mapping.add(0, v.getSource());
    }

    /**
     * Extracts the smallest element in the heap.
     * @return The smalles element.
     * @throws Exception
     */
    public Edge extractMin() throws Exception {
        if (this.heap.size() < 1) {
            throw new Exception("Heap underflow");
        }

        Edge predecessor = this.heap.remove(0);
        String source = this.mapping.remove(0);
        Edge element = new Edge(source, predecessor.getTarget(), predecessor.getWeight());

        this.minHeapify(0);
        return element;
    }

    /**
     * Min-Heapify from Cormen et. Al.
     * @param index
     */
    private void minHeapify(Integer index) {
        Integer left = this.getLeftChild(index);
        Integer right = this.getRightChild(index);
        Integer smallest = index;
        if (left < this.heap.size() && this.heap.get(index).getWeight() > this.heap.get(left).getWeight()){
            smallest = left;
        }
        //System.out.println(index);
        //System.out.println(left);
        //System.out.println(right);
        if (right < this.heap.size() && this.heap.get(smallest).getWeight() > this.heap.get(right).getWeight()){
            smallest = right;
        }
        if (smallest != index) {
            this.swap(smallest, index);
            this.minHeapify(smallest);
        }
    }

    /**
     * Updates value of element and orders the min heap.
     * @param pred The new predecessor to v.
     * @param key The weight of the edge between pred and v.
     * @param v Some vertex adjacent to pred.
     * @throws Exception
     */
    public void decreaseKey(Edge vertex, Edge pred) throws Exception {

        Double key = vertex.getWeight();
        String v = vertex.getTarget();

        // Fetch adjacent vertex data
        Pair<Integer, Edge> adjacentVertex = this.getVertex(v);
        int i = adjacentVertex.getKey();
        Edge adjV = adjacentVertex.getValue();

        if( !(adjV.getWeight() > pred.getWeight() + key) ) {
            return;
        }

        // Update key & predecessor
        this.heap.get(i).setWeight( pred.getWeight() + key );
        this.heap.get(i).setTarget(pred.getSource());

        // Bubble up to rebalance heap
        Double parentWeight = this.heap.get( getParent(i) ).getWeight();
        while (i > 0 && parentWeight > adjV.getWeight() ) {
            i = swap(i, this.getParent(i));
        }
    }

    /**
     * Swaps to elements in the array, returning the current parent.
     * @param i child index to swap
     * @param parent parent index to swap
     * @return parent
     */
    private int swap(int i, int parent) {

        // Store vertex data before overwrite
        Edge vTmp = this.heap.get(i);
        String iTmp = this.mapping.get(i);

        // Overwrite old vertex
        this.heap.set(i, this.heap.get(parent));
        this.mapping.set(i, this.mapping.get(parent));

        // Overwrite stored vertex on parent index
        this.heap.set(parent, vTmp);
        this.mapping.set(parent, iTmp);

        return parent;

    }

    /**
     * Retrieves the index of the vertex in the map.
     * @param v The vertex name of the vertex.
     * @return The index in the map.
     */
    public int getMappingIndex(String v) {
        return this.mapping.indexOf(v);
    }

    /**
     * Retrieves an Edge and mapping index from the heap.
     * @param v The vertex name of the vertex.
     * @return The index, Edge pair.
     */
    public Pair<Integer, Edge> getVertex(String v) {
        int index = this.getMappingIndex(v);
        return new Pair<Integer, Edge>(index, this.heap.get(index));
    }

    public ArrayList<Edge> getHeap() {
        return heap;
    }

    public int getParent(int i) { return (i - 1) / 2; }

    public int getLeftChild(int i) { return (i  * 2 + 1); }

    public int getRightChild(int i) { return (i * 2 + 2); }

    public int size() { return this.heap.size(); }


}
