package main;

import java.util.ArrayList;

/**
 *
 * Created by mcfallen on 11/2/15.
 */
public class MinHeap {


    private ArrayList<VertexElement<Double, String>> heap;

    public ArrayList<VertexElement<Double, String>> getHeap() {
        return heap;
    }

    public ArrayList<String> mapping;

    public int getMapping(String v) {
        return this.mapping.indexOf(v);
    }

    public VertexElement<Integer, VertexElement<Double, String>> getVertex(String v) {
        System.out.println("map " + this.mapping + " v: " + v);
        int index = this.mapping.indexOf(v);
        System.out.println("ii " + index);
        System.out.println("dd " + this.heap.get(index));
        return new VertexElement<Integer, VertexElement<Double, String>>(index, this.heap.get(index));
    }

    public MinHeap(){
        this.heap = new ArrayList<VertexElement<Double, String>>();
        this.mapping = new ArrayList<String>();
    }

    public VertexElement<String, VertexElement<Double, String>> extractMin() throws Exception {
        if (this.heap.size() < 1) {
            throw new Exception("Heap underflow");
        }
        return new VertexElement<String, VertexElement<Double, String>>(this.mapping.remove(0), this.heap.remove(0));
    }


    /**
     * Updates value of element and orders the min heap
     * @param v
     * @param key
     * @throws Exception
     */
    public void decreaseKey(VertexElement<String, VertexElement<Double, String>> v, Double key, String p) throws Exception {

        // Fetch source vertex data
        VertexElement<String, VertexElement<Double, String>> source = v;
        if (key > source.getValue().getKey()) {
            throw new Exception("new key is larger than current");
        }
        // Fetch pred vertex data
        VertexElement<Integer, VertexElement<Double, String>> pred = this.getVertex(p);

        if (!(source.getValue().getKey() > pred.getValue().getKey() + key)) {
            return;
        }

        // Fetch index of source
        int i = source.getKey();

        // Update key & predecessor
        this.heap.get(source.getKey()).setKey(pred.getValue().getKey() + key);
        this.heap.get(source.getKey()).setValue(p);

        // Bubble up to rebalance heap
        while (i < 1 && this.heap.get(getParent(i)).getKey() > source.getValue().getKey()) {
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
        VertexElement<Double, String> vTmp = this.heap.get(i);
        String iTmp = this.mapping.get(i);

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

    public void add(VertexElement<String, VertexElement<Double, String>> v){
        this.heap.add(v.getValue());
        this.mapping.add(v.getKey());
    }
}
