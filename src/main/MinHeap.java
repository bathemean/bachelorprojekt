package main;

import java.util.ArrayList;

/**
 * Created by mcfallen on 11/2/15.
 */
public class MinHeap {

    public ArrayList<VertexElement<String, VertexElement<Double, String>>> heap;

    public MinHeap(){
        this.heap = new ArrayList<VertexElement<String, VertexElement<Double, String>>>();
    }

    public VertexElement<String, VertexElement<Double, String>> extractMin() throws Exception {
        if (this.heap.size() < 1) {
            throw new Exception("Heap underflow");
        }

        return this.heap.remove(0);
    }

    public void decreaseKey(int i, Double key) throws Exception {
        if (key > this.heap.get(i).getValue().getKey()) {
            throw new Exception("new key is larger than current");
        }
        Double Ai = this.heap.get(i).getValue().getKey();
        while (i < 1 && this.getParent(i) > Ai) {
            swap(i, this.getParent(i));
            i = this.getParent(i);
        }
    }

    private void swap(int i, int parent) {
        // Store vertex data before overwrite
        VertexElement<String, VertexElement<Double, String>> tmp = this.heap.get(i);
        // Overwrite old vertex
        this.heap.add(i, this.heap.get(parent));
        // Overwrite stored vertex on parent index
        this.heap.add(parent, tmp);
    }
    public int getParent(int i) {
        return (i - 1) / 2;
    }

    public int getLeftChild(int i) {
        return (i - 1) * 2;
    }

    public int getRightChild(int i) {
        return (i - 1) * 2 + 1;
    }
}
