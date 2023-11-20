package gui;

import util.Canvas;
import util.tuple.SortTuple;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InsertionSort extends SortingPanel {
    public InsertionSort(List<Integer> values, String layout) {
        super(values, layout);
    }

    @Override
    protected void paintComponent(Graphics g) {
        startSorted = 0;
        sizeSorted = i + 1;
        g.clearRect(0, 0, Canvas.DIM_W, Canvas.DIM_H);
        Canvas.paintArray(g, layout, values, List.of(j),
                startSorted, sizeSorted);
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
            if (i >= values.size()) disableNextAndFinishButton();
            steps.push(new SortTuple(new ArrayList<>(values), i, j));
        } else {
            i = values.size();
            j = values.size();
            disableNextAndFinishButton();
        }
        enablePrevAndRestartButton();
    }
}