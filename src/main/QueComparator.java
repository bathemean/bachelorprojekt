package main;

import javafx.util.Pair;

import java.util.Comparator;

public class QueComparator implements Comparator<Pair<String, Pair<Double, String>>> {

    public int compare(Pair<String, Pair<Double, String>> v1, Pair<String, Pair<Double, String>> v2) {
        Double weight1 = v1.getValue().getKey();
        Double weight2 = v2.getValue().getKey();

        return (int) (weight1 - weight2);
    }

    public boolean equals(Pair<String, Pair<Double, String>> v1, String v2){
        String first = v1.getKey();
        return (first.equals(v2));
    }
}
