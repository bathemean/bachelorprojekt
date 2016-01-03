package main.spanner;

public class Spanner {

    private long startTime;
    private long endTime;
    private long runtime;
    private long stretch;

    protected void startTiming() {
        this.startTime = System.currentTimeMillis();
    }

    protected void endTiming() {
        this.endTime = System.currentTimeMillis();
        this.runtime = this.endTime - this.startTime;
    }

    protected long getRuntime() {
        return this.runtime;
    }

    protected long getStretch() { return this.stretch; }
}
