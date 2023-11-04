package sorting;

import util.tuple.SortTuple;

import java.util.ArrayList;
import java.util.List;

public class BubbleSort extends SortingPanel {
    public BubbleSort(List<Integer> values, String layout) {
        super(values, layout);
    }

    @Override
    protected void nextStep() {
        if (!isSorted()) {
            if (j < values.size() - k - 1) {
                if (values.get(j) > values.get(j + 1)) {
                    int temp = values.get(j);
                    values.set(j, values.get(j + 1));
                    values.set(j + 1, temp);
                }
                i = j;
                j++;
            } else {
                j = 0;
                i = j;
                k++;
            }
            steps.push(new SortTuple(new ArrayList<>(values), i, j, k));
        } else {
            i = values.size();
            j = values.size();
            k = values.size();
            nextButton.setEnabled(false);
            doneButton.setEnabled(false);
        }
        prevButton.setEnabled(true);
        restartButton.setEnabled(true);
    }

    private boolean isSorted() {
        for (int i = 0; i < values.size() - k - 1; i++) {
            if (values.get(i) > values.get(i + 1)) {
                return false;
            }
        }
        return true;
    }
}
