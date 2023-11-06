package sorting;

import util.tuple.SortTuple;

import java.util.ArrayList;
import java.util.List;

public class BubbleSort extends SortingPanel {
    public BubbleSort(List<Integer> values, String layout) {
        super(values, layout);
        j = 1;
        i = 0;
    }

    @Override
    protected void nextStep() {
        if (!isSorted()) {
            if (j < values.size() - k) {
                if (values.get(j - 1) > values.get(j)) {
                    int temp = values.get(j - 1);
                    values.set(j - 1, values.get(j));
                    values.set(j, temp);
                }
                i = j;
                j++;
                if (j == values.size() - k) j = values.size();
            } else {
                j = 1;
                i = 0;
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

    @Override
    protected void restart() {
        super.restart();
        j = 1;
        i = 0;
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
