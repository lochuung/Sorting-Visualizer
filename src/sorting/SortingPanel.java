package sorting;

import util.Canvas;
import util.tuple.SortTuple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
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
    protected int k = 0;
    protected Stack<SortTuple> steps;
    protected JButton prevButton;
    protected JButton nextButton;
    protected JButton restartButton;
    protected JButton doneButton;
    private static final String RESTART_BUTTON_PATH = "src/resources/restart_button.png";
    private static final String PREV_BUTTON_PATH = "src/resources/prev_button.png";
    private static final String NEXT_BUTTON_PATH = "src/resources/next_button.png";
    private static final String DONE_BUTTON_PATH = "src/resources/done_button.png";

    public SortingPanel(List<Integer> values, String layout) {
        this.values = values;
        this.layout = layout;
        steps = new Stack<>();
        steps.push(new SortTuple(new ArrayList<>(values), i, j, k));
        setUpButton();
    }

    protected abstract void nextStep();

    protected void prevStep() {
        if (!steps.isEmpty()) {
            SortTuple tuple = steps.pop();
            values = tuple.values;
            i = tuple.i;
            j = tuple.j;
            k = tuple.otherIndex;
        } else {
            i = 0;
            j = 0;
            k = 0;
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
        k = 0;
        values = steps.firstElement().values;
        steps.clear();
        steps.push(new SortTuple(new ArrayList<>(values), i, j, k));
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
        JButton[] buttons = {
                restartButton = createButton(RESTART_BUTTON_PATH),
                prevButton = createButton(PREV_BUTTON_PATH),
                nextButton = createButton(NEXT_BUTTON_PATH),
                doneButton = createButton(DONE_BUTTON_PATH)
        };

        JPanel buttonPanel = new JPanel();
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.SOUTH);

        restartButton.setEnabled(false);
        prevButton.setEnabled(false);

        ActionListener[] actions = {
                e -> { restart(); repaint(); },
                e -> { prevStep(); repaint(); },
                e -> { nextStep(); repaint(); },
                e -> { lastStep(); repaint(); }
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].addActionListener(actions[i]);
        }
    }

    private JButton createButton(String imagePath) {
        JButton button = new JButton();
        ImageIcon icon = new ImageIcon(imagePath);
        icon = new ImageIcon(icon.getImage().getScaledInstance(60, 30, Image.SCALE_SMOOTH));
        button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setIcon(icon);
        return button;
    }
}
