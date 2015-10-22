import javafx.util.Pair;
import java.util.Comparator;

public class QueComparator implements Comparator {

    @Override
    public int compare(Pair<String, Pair<Double, String>> v1, Pair<String, Pair<Double, String>> v2) {
        Double weight1 = v1.getValue().getKey();
        Double weight2 = v2.getValue().getKey();

        return (int) (weight1 - weight2);
    }

    @Override
    public boolean equals(Object obj) {
        Double weight1 = obj.getValue().getKey();

        return weight1 == 0.0;
    }
}
