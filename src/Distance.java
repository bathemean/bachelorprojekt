public class Distance {

    int partition;
    String witness;
    String target;
    double distance;

    public Distance(int partition, String witness, String target, double distance) {
        this.partition = partition;
        this.witness = witness;
        this.target = target;
        this.distance = distance;
    }

    public int getPartition() {
        return this.partition;
    }

    public String getWitness() {
        return this.witness;
    }

    public String getTarget() {
        return this.target;
    }

    public double getDistance() {
        return this.distance;
    }

}
