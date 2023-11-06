package sorting;

import util.tuple.SortTuple;

import java.util.ArrayList;
import java.util.List;

public class InsertionSort extends SortingPanel {
    public InsertionSort(List<Integer> values, String layout) {
        super(values, layout);
    }

    @Override
    protected void nextStep() {
        if (i < values.size()) {
            if (j > 0 && values.get(j) < values.get(j - 1)) {
                int temp = values.get(j);
                values.set(j, values.get(j - 1));
                values.set(j - 1, temp);
                j--;
            } else {
                i++;
                j = i;
            }
            steps.push(new SortTuple(new ArrayList<>(values), i, j));
        } else {
            i = values.size();
            j = values.size();
            nextButton.setEnabled(false);
            doneButton.setEnabled(false);
        }
        prevButton.setEnabled(true);
        restartButton.setEnabled(true);
    }
}