package sortingpanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class InsertionSort extends SortingPanel {

    public InsertionSort() {
        super();
    }

    public InsertionSort(List<Integer> values, String layout) {
        super(values, layout);
    }

    @Override
    protected void nextStep() {
        if (i < values.size()) {
            int key = values.get(i);
            j = i - 1;

            while (j >= 0 && values.get(j) > key) {
                values.set(j + 1, values.get(j));
                j = j - 1;
            }
            j++;
            values.set(j, key);
            i++;
            steps.push(new ArrayList<>() {{
                addAll(values);
            }});
        } else {
            nextButton.setEnabled(false);
            doneButton.setEnabled(false);
        }
        prevButton.setEnabled(true);
        restartButton.setEnabled(true);
    }

    @Override
    protected void prevStep() {
        if (!steps.isEmpty()) {
            values = new ArrayList<>(steps.pop());
            i--;
            if (i == 0) {
                j = 0;
            } else {
                j = i - 1;
            }
        } else {
            i = 0;
            j = 0;
            prevButton.setEnabled(false);
            restartButton.setEnabled(false);
        }
        nextButton.setEnabled(true);
        doneButton.setEnabled(true);
    }

    @Override
    protected void lastStep() {
        while (i < values.size()) {
            nextStep();
        }

        nextButton.setEnabled(false);
        doneButton.setEnabled(false);
        prevButton.setEnabled(true);
        restartButton.setEnabled(true);
    }

    @Override
    protected void restart() {
        if (!steps.isEmpty()) {
            values = new ArrayList<>(steps.firstElement());
            i = 1;
            steps.clear();
            steps.push(new ArrayList<>(values));
            nextButton.setEnabled(true);
            doneButton.setEnabled(true);
            prevButton.setEnabled(false);
            restartButton.setEnabled(false);
        }
    }

}