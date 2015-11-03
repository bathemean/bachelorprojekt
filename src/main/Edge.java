package main;

public class Edge {

    private String source;
    private String target;
    private Double weight;

    public Edge(String source, String target, Double weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public void setSource(String value) {
        this.source = value;
    }

    public String getSource() {
        return this.source;
    }

    public void setTarget(String value) {
        this.target = value;
    }

    public String getTarget() {
        return this.target;
    }

    public void setWeight(Double value) {
        this.weight = value;
    }

    public Double getWeight() {
        return this.weight;
    }

    public String toString() {
        // {source (weight) target}
        return "{" + this.source + " (" + this.weight + ") " + this.target + "}";
    }

}
