package gui;
import util.tuple.SortTuple;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BubbleSort extends SortingPanel {
    private boolean isSwapped;

    public BubbleSort(List<Integer> values, String layout) {
        super(values, layout);
        j = 0;
        i = 1;
        isSwapped = false;
    }


    @Override
    protected void paintComponent(Graphics g) {
        startSorted = values.size() - k;
        sizeSorted = k;
        super.paintComponent(g);
    }

    @Override
    protected void nextStep() {
        if (!isSorted()) {
            if (isSwapped) {
                afterSwapHandle();
                return;
            }
            if (j < values.size() - k - 1) {
                if (values.get(j) > values.get(j + 1)) {
                    int temp = values.get(j);
                    values.set(j, values.get(j + 1));
                    values.set(j + 1, temp);
                    i = j;
                    j++;
                    isSwapped = true;
                } else {
                    j++;
                    i = j + 1;
                }
                if (i == values.size() - k) {
                    nextPass();
                }
            } else {
                nextPass();
            }
            steps.push(new SortTuple(new ArrayList<>(values), i, j, k));
        } else {
            i = -1;
            j = -1;
            k = -1;
            disableNextAndFinishButton();
        }
        enablePrevAndRestartButton();
    }

    private void afterSwapHandle() {
        isSwapped = false;
        i = j + 1;
        steps.push(
                new SortTuple(new ArrayList<>(values), i, j, k));

        if (i == values.size() - k) {
            nextPass();
        }
    }

    private void nextPass() {
        i = 1;
        j = 0;
        k++;
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
