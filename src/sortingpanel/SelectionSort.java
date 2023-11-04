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
    private int minIndex = 0;

    public SelectionSort(List<Integer> values, String layout) {
        super(values, layout);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Canvas.paintArray(g, layout, values, Arrays.asList(i, minIndex, j));

        int x = minIndex * HOR_INC + HOR_INC / 2;
        Canvas.paintPointer(g, Color.BLUE, x, HORIZON);
    }

    @Override
    protected void nextStep() {
        if (i < values.size()) {
            if (j < values.size()) {
                if (values.get(j) < values.get(minIndex))
                    minIndex = j;
                j++;
            } else {
                int temp = values.get(i);
                values.set(i, values.get(minIndex));
                values.set(minIndex, temp);
                i++;
                j = i;
                minIndex = i;
            }
            steps.push(new SortTuple(new ArrayList<>(values), i, j, minIndex));
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
            SortTuple tuple = steps.pop();
            values = tuple.values;
            minIndex = tuple.otherIndex;
            i = tuple.i;
            j = tuple.j;
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
    protected void restart() {
        super.restart();
        minIndex = 0;
    }
}
