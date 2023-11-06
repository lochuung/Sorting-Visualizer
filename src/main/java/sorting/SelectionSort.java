package sorting;

import util.Canvas;
import util.tuple.SortTuple;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectionSort extends SortingPanel {
    public SelectionSort(List<Integer> values, String layout) {
        super(values, layout);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Canvas.paintArray(g, layout, values, Arrays.asList(i, k, j));
    }

    @Override
    protected void nextStep() {
        if (i < values.size()) {
            if (j < values.size()) {
                if (values.get(j) < values.get(k))
                    k = j;
                j++;
            } else {
                int temp = values.get(i);
                values.set(i, values.get(k));
                values.set(k, temp);
                i++;
                j = i;
                k = i;
            }
            steps.push(new SortTuple(new ArrayList<>(values), i, j, k));
        } else {
            nextButton.setEnabled(false);
            doneButton.setEnabled(false);
        }
        prevButton.setEnabled(true);
        restartButton.setEnabled(true);
    }
}
