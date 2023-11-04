package sortingpanel;

import util.Canvas;
import util.tuple.SortTuple;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.Canvas.HORIZON;
import static util.Canvas.HOR_INC;

public class SelectionSort extends SortingPanel {
    // k is min index
    public SelectionSort(List<Integer> values, String layout) {
        super(values, layout);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Canvas.paintArray(g, layout, values, Arrays.asList(i, k, j));

        int x = k * HOR_INC + HOR_INC / 2;
        Canvas.paintPointer(g, Color.BLUE, x, HORIZON);
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
