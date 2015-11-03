package main;

import javafx.util.Pair;

import java.util.ArrayList;

/**
 *
 * Created by mcfallen on 11/2/15.
 */
public class MinHeap {


    private ArrayList<Edge> heap;

    public ArrayList<Edge> getHeap() {
        return heap;
    }

    public ArrayList<String> mapping;

    public int getMapping(String v) {
        return this.mapping.indexOf(v);
    }

    /*public VertexElement<Integer, VertexElement<Double, String>> getVertex(String v) {
        int index = this.mapping.indexOf(v);
        return new VertexElement<Integer, VertexElement<Double, String>>(index, this.heap.get(index));
    }*/

    public Pair<Integer, Edge> getVertex(String v) {
        int index = this.mapping.indexOf(v);
        return new Pair<Integer, Edge>(index, this.heap.get(index));
    }

    public MinHeap(){
        this.heap = new ArrayList<Edge>();
        this.mapping = new ArrayList<String>();
    }

    public Edge extractMin() throws Exception {
        if (this.heap.size() < 1) {
            throw new Exception("Heap underflow");
        }

        Edge predecessor = this.heap.remove(0);
        String source = this.mapping.remove(0);
        Edge element = new Edge(source, predecessor.getTarget(), predecessor.getWeight());

        return element;

        //return new VertexElement<String, VertexElement<Double, String>>(this.mapping.remove(0), this.heap.remove(0));
    }


    /**
     * Updates value of element and orders the min heap
     * @param v
     * @param key
     * @throws Exception
     */
    public void decreaseKey(Edge pred, Double key, String v) throws Exception {

        // Fetch adjacent vertex data
        //VertexElement<Integer, VertexElement<Double, String>> adjV = this.getVertex(v);
        Pair<Integer, Edge> adjacentVertex = this.getVertex(v);
        int i = adjacentVertex.getKey();
        Edge adjV = adjacentVertex.getValue();

        //System.out.print(pred.getKey() + "\n");
        //System.out.print(v + "\n");
        //System.out.print(adjV.getValue().getKey() + "\n");
        //System.out.print(adjV.getValue().getValue() + "\n");

        if( !(adjV.getWeight() > pred.getWeight() + key) ) {
        //if (!(adjV.getValue().getKey() > pred.getValue().getKey() + key)) {
            return;
        }

        // Update key & predecessor
        this.heap.get(i).setWeight( pred.getWeight() + key );
        //this.heap.get(adjV.getKey()).setKey(pred.getValue().getKey() + key);
        this.heap.get(i).setTarget( pred.getSource() );
        //this.heap.get(adjV.getKey()).setValue(pred.getKey());

        // Bubble up to rebalance heap
        //while (i > -1 && this.heap.get(getParent(i)).getKey() > adjV.getValue().getKey()) {
        Double parentWeight = this.heap.get( getParent(i) ).getWeight();
        while (i > -1 && parentWeight > adjV.getWeight() ) {
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
        System.out.print("lolomg");
        String iTmp = this.mapping.get(i);
        System.out.print(iTmp);

        // Overwrite old vertex
        this.heap.add(i, this.heap.get(parent));
        this.mapping.add(i, this.mapping.get(parent));

        // Overwrite stored vertex on parent index
        this.heap.add(parent, vTmp);
        this.mapping.add(parent, iTmp);

        return parent;
    }

    public int getParent(int i) { return (i - 1) / 2; }

    public int getLeftChild(int i) { return (i - 1) * 2; }

    public int getRightChild(int i) { return (i - 1) * 2 + 1; }

    public int size() { return this.heap.size(); }

    public void add(Edge v){
        this.heap.add(v);
        this.mapping.add(v.getSource());
    }
}
