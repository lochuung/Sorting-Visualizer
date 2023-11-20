package gui;

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
        startSorted = 0;
        sizeSorted = i;
        g.clearRect(0, 0, Canvas.DIM_W, Canvas.DIM_H);
        Canvas.paintArray(g, layout, values, Arrays.asList(i, j, k),
                startSorted, sizeSorted);
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
            if (i >= values.size()) disableNextAndFinishButton();
            steps.push(new SortTuple(new ArrayList<>(values), i, j, k));
        } else {
            disableNextAndFinishButton();
        }
        enablePrevAndRestartButton();
    }
}
