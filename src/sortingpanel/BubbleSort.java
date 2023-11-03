package sortingpanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BubbleSort extends SortingPanel {

    public BubbleSort() {
        super();
    }

    public BubbleSort(List<Integer> values, String layout) {
        super(values, layout);
    }

    @Override
    protected void nextStep() {
        if (!isSorted()) {
            if (j < size - i - 1) {
                if (values.get(j) > values.get(j + 1)) {
                    int temp = values.get(j);
                    values.set(j, values.get(j + 1));
                    values.set(j + 1, temp);
                }
                j++;
            } else {
                j = 0;
                i++;
            }
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

    private boolean isSorted() {
        for (int i = 0; i < values.size() - 1; i++) {
            if (values.get(i) > values.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void prevStep() {
        if (!steps.isEmpty()) {
            values.clear();
            values.addAll(steps.pop());
            if (i > 0) {
                i--;
            } else {
                i = size - 1;
            }
            doneButton.setEnabled(true);
            nextButton.setEnabled(true);
        } else {
            i = 0;
            j = 0;
            prevButton.setEnabled(false);
            restartButton.setEnabled(false);
            nextButton.setEnabled(true);
            doneButton.setEnabled(true);
        }
    }

    @Override
    protected void restart() {
        i = 0;
        j = 0;
        values.clear();
        values.addAll(steps.firstElement());
        steps.clear();
        steps.push(new ArrayList<>() {{
            addAll(values);
        }});
        nextButton.setEnabled(true);
        doneButton.setEnabled(true);
        prevButton.setEnabled(false);
        restartButton.setEnabled(false);
    }

    @Override
    protected void lastStep() {
        while (!isSorted()) {
            nextStep();
        }
        nextButton.setEnabled(false);
        doneButton.setEnabled(false);
        prevButton.setEnabled(true);
        restartButton.setEnabled(true);
    }
}
