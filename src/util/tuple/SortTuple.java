package util.tuple;

import java.util.List;

public class SortTuple {
    public List<Integer> values;
    public int i;
    public int j;
    public int otherIndex;

    public SortTuple(List<Integer> values, int i, int j) {
        this.values = values;
        this.i = i;
        this.j = j;
    }

    public SortTuple(List<Integer> values, int i, int j, int otherIndex) {
        this.values = values;
        this.i = i;
        this.j = j;
        this.otherIndex = otherIndex;
    }
}
