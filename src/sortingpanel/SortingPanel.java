package sortingpanel;

import util.Canvas;
import util.tuple.SortTuple;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static util.Canvas.*;

public abstract class SortingPanel extends JPanel {
    protected List<Integer> values;
    protected String layout;
    protected int i = 0;
    protected int j = 0;
    protected Stack<SortTuple> steps;
    protected Button prevButton;
    protected Button nextButton;
    protected Button restartButton;
    protected Button doneButton;

    public SortingPanel(List<Integer> values, String layout) {
        this.values = values;
        this.layout = layout;
        steps = new Stack<>();
        steps.push(new SortTuple(new ArrayList<>(values), i, j));
        setUpButton();
    }

    protected abstract void nextStep();

    protected void prevStep() {
        if (!steps.isEmpty()) {
            SortTuple tuple = steps.pop();
            values = tuple.values;
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
    protected void lastStep() {
        while (nextButton.isEnabled() || doneButton.isEnabled())
            nextStep();
    }

    protected void restart() {
        if (steps.isEmpty())
            return;
        i = 0;
        j = 0;
        values = steps.firstElement().values;
        steps.clear();
        steps.push(new SortTuple(new ArrayList<>(values), i, j));
        prevButton.setEnabled(false);
        restartButton.setEnabled(false);
        nextButton.setEnabled(true);
        doneButton.setEnabled(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Canvas.paintArray(g, layout, values, Arrays.asList(i, j));
        int x1 = HOR_INC * i + HOR_INC / 2;
        int x2 = HOR_INC * j + HOR_INC / 2;
        int y = HORIZON;
        Canvas.paintPointer(g, Color.RED, x1, y);
        Canvas.paintPointer(g, Color.GREEN, x2, y);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DIM_W, DIM_H);
    }

    private void setUpButton() {
        restartButton = new Button("<<");
        prevButton = new Button("<");
        nextButton = new Button(">");
        doneButton = new Button(">>");
        add(restartButton);
        add(prevButton);
        add(nextButton);
        add(doneButton);

        restartButton.setEnabled(false);
        prevButton.setEnabled(false);

        restartButton.addActionListener(e -> {
            restart();
            repaint();
        });

        prevButton.addActionListener(e -> {
            prevStep();
            repaint();
        });

        nextButton.addActionListener(e -> {
            nextStep();
            repaint();
        });

        doneButton.addActionListener(e -> {
            lastStep();
            repaint();
        });
    }
}
