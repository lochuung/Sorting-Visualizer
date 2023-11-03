package sortingpanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class SortingPanel extends JPanel {
    protected List<Integer> values;
    protected String layout;
    protected static int size;
    protected int i = 0;
    protected int j = 0;
    protected Stack<List<Integer>> steps;
    protected Button prevButton;
    protected Button nextButton;
    protected Button restartButton;
    protected Button doneButton;
    protected static final int DIM_W = 500;
    protected static final int DIM_H = 500;
    protected static final int HORIZON = 350;
    protected static final int VERT_INC = 15;
    protected static int HOR_INC;

    public SortingPanel() {
    }

    public SortingPanel(List<Integer> values, String layout) {
        this.values = values;
        this.layout = layout;
        steps = new Stack<>();
        steps.push(new ArrayList<>() {{
            addAll(values);
        }});
        run();
    }

    public void run() {
        restartButton = new Button("<<");
        prevButton = new Button("<");
        nextButton = new Button(">");
        doneButton = new Button(">>");
        size = values.size();
        HOR_INC = DIM_W / size;
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

    protected abstract void nextStep();

    protected abstract void prevStep();

    protected abstract void lastStep();

    protected abstract void restart();

    public void drawItem(Graphics g, int item, int index) {
        if (layout.equals("Bar")) {
            drawBar(g, item, index);
        } else {
            drawArray(g, item, index);
        }
    }

    public void drawBar(Graphics g, int item, int index) {
        int max = findMax();
        int height = (int) ((double) item / max * HORIZON);
        int x = index * HOR_INC;
        int y = HORIZON - height;
        g.setColor(Color.CYAN);
        g.fillRect(x, y, HOR_INC, height);
        g.setColor(Color.BLUE);
        String number = String.valueOf(item);
        int stringWidth = g.getFontMetrics().stringWidth(number);
        int stringHeight = g.getFontMetrics().getAscent();
        g.drawString(number, x + (HOR_INC - stringWidth) / 2, y + (height + stringHeight) / 2);
    }

    private int findMax() {
        int max = values.get(0);
        for (int value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public void drawArray(Graphics g, int item, int index) {
        int x = index * HOR_INC;
        int y = (DIM_H - VERT_INC) / 2;
        g.drawRect(x, y, HOR_INC, VERT_INC);

        String number = String.valueOf(item);
        int stringWidth = g.getFontMetrics().stringWidth(number);
        int stringHeight = g.getFontMetrics().getAscent();
        g.drawString(number, x + (HOR_INC - stringWidth) / 2, y + (VERT_INC + stringHeight) / 2);

        String indexStr = String.valueOf(index);
        int indexWidth = g.getFontMetrics().stringWidth(indexStr);
        g.drawString(indexStr, x + (HOR_INC - indexWidth) / 2, y + VERT_INC + stringHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw the array not out of frame
        for (int i = 0; i < size; i++) {
            drawItem(g, values.get(i), i);
        }
        // draw i and j pointers
        g.setColor(Color.RED);
        g.drawLine(i * HOR_INC + HOR_INC / 2, HORIZON,
                i * HOR_INC + HOR_INC / 2, HORIZON + 20);
        g.setColor(Color.BLUE);
        g.drawLine(j * HOR_INC + HOR_INC / 2, HORIZON,
                j * HOR_INC + HOR_INC / 2, HORIZON + 20);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DIM_W, DIM_H);
    }

    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    public String getSortLayout() {
        return layout;
    }

    public void setSortLayout(String layout) {
        this.layout = layout;
    }
}
