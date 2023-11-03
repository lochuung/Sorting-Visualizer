package sortingpanel;

import util.Canvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SelectionSort extends SortingPanel {
    private int minIndex = 0;

    public SelectionSort() {
        super();
    }

    public SelectionSort(List<Integer> values, String layout) {
        super(values, layout);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Canvas.paintPointer(g, Color.BLUE, minIndex);
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

                steps.push(new ArrayList<>() {{
                    addAll(values);
                }});
            }
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
            values = steps.pop();
            i--;
            j = i;
        } else {
            i = 0;
            j = 0;
            minIndex = 0;
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
