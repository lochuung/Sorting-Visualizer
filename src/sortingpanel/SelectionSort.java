package sortingpanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
        // draw minIndex pointer
        g.setColor(Color.GREEN);
        g.drawLine(minIndex * HOR_INC + HOR_INC / 2, HORIZON,
                minIndex * HOR_INC + HOR_INC / 2, HORIZON + 20);
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
            nextButton.setEnabled(true);
            doneButton.setEnabled(true);
        } else {
            i = 0;
            j = 0;
            minIndex = 0;
            prevButton.setEnabled(false);
            restartButton.setEnabled(false);
        }
    }

    @Override
    protected void lastStep() {
        while (i < values.size())
            nextStep();
        prevButton.setEnabled(true);
        restartButton.setEnabled(true);
        nextButton.setEnabled(false);
        doneButton.setEnabled(false);
    }

    @Override
    protected void restart() {
        i = 0;
        j = 0;
        minIndex = 0;
        values = steps.firstElement();
        steps.clear();
        steps.push(new ArrayList<>() {{
            addAll(values);
        }});
        prevButton.setEnabled(false);
        restartButton.setEnabled(false);
        nextButton.setEnabled(true);
        doneButton.setEnabled(true);
    }
}
