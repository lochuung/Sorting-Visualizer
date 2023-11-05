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
    protected SortingFrame parentFrame;
    private static final String RESTART_BUTTON_PATH = "src/resources/restart_button.png";
    private static final String PREV_BUTTON_PATH = "src/resources/prev_button.png";
    private static final String NEXT_BUTTON_PATH = "src/resources/next_button.png";
    private static final String DONE_BUTTON_PATH = "src/resources/done_button.png";
    private static final String BACK_TO_MAIN_BUTTON_PATH = "src/resources/back_to_main_button.png";

    public SortingPanel(List<Integer> values, String layout) {
        this.values = values;
        this.layout = layout;
        setPreferredSize(new Dimension(DIM_W, DIM_H));
        setLayout(new BorderLayout());
        steps = new Stack<>();
        steps.push(new SortTuple(new ArrayList<>(values), i, j, k));
        setUpButton();
    }

    public void setParentFrame(SortingFrame parentFrame) {
        this.parentFrame = parentFrame;
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
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DIM_W, DIM_H);
    }

    private void setUpButton() {
        JButton[] controlButtons = {
                restartButton = createButton(RESTART_BUTTON_PATH),
                prevButton = createButton(PREV_BUTTON_PATH),
                nextButton = createButton(NEXT_BUTTON_PATH),
                doneButton = createButton(DONE_BUTTON_PATH)
        };

        JPanel bottomPanel = new JPanel();
        for (JButton button : controlButtons) {
            bottomPanel.add(button);
        }
        bottomPanel.setSize(DIM_W, CONTROL_BUTTON_HEIGHT);
        add(bottomPanel, BorderLayout.SOUTH);

        JPanel topPanel = new JPanel();
        JButton backButton = createButton(BACK_TO_MAIN_BUTTON_PATH);
        backButton.addActionListener(e -> {
            parentFrame.dispose();
            parentFrame.getMainForm().setVisible(true);
        });
        JPanel buttonWrapper = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1; // this will make the component center align vertically
        buttonWrapper.add(backButton, gbc);

        topPanel.setLayout(new GridLayout());
        JLabel label = getSortTitle();
        topPanel.add(label);
        topPanel.add(buttonWrapper);
        topPanel.setPreferredSize(new Dimension(DIM_W, (DIM_H - HORIZON) / 2));
        add(topPanel, BorderLayout.NORTH);

        restartButton.setEnabled(false);
        prevButton.setEnabled(false);

        ActionListener[] actions = {
                e -> {
                    restart();
                    repaint();
                },
                e -> {
                    prevStep();
                    repaint();
                },
                e -> {
                    nextStep();
                    repaint();
                },
                e -> {
                    lastStep();
                    repaint();
                }
        };

        for (int i = 0; i < controlButtons.length; i++) {
            controlButtons[i].addActionListener(actions[i]);
        }
    }

    private JLabel getSortTitle() {
        JLabel label = new JLabel();
        if (this instanceof BubbleSort) {
            label.setText("Bubble Sort");
        } else if (this instanceof SelectionSort) {
            label.setText("Selection Sort");
        } else if (this instanceof InsertionSort) {
            label.setText("Insertion Sort");
        } else if (this instanceof MergeSort) {
            label.setText("Merge Sort");
        }
        label.setFont(new Font("Ink Free", Font.BOLD, 30));
        label.setForeground(Color.BLUE);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    private JButton createButton(String imagePath) {
        JButton button = new JButton();
        ImageIcon icon = new ImageIcon(imagePath);
        int width = icon.getIconWidth() *
                CONTROL_BUTTON_HEIGHT
                / icon.getIconHeight();
        int height = CONTROL_BUTTON_HEIGHT;
        icon = new ImageIcon(icon.getImage()
                .getScaledInstance(width,
                        height,
                        Image.SCALE_SMOOTH));
        button.setPreferredSize(new Dimension(width, height));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setIcon(icon);
        return button;
    }
}
