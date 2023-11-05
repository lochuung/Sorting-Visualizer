package sorting;

import util.SortType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static util.Canvas.DIM_H;
import static util.Canvas.DIM_W;

public class SortingFrame extends JFrame {
    SortingPanel sortingPanel;
    public SortingFrame(List<Integer> values, String layout, SortType choice) {
        switch (choice) {
            case BUBBLE_SORT:
                sortingPanel = new BubbleSort(values, layout);
                break;
            case SELECTION_SORT:
                sortingPanel = new SelectionSort(values, layout);
                break;
            case INSERTION_SORT:
                sortingPanel = new InsertionSort(values, layout);
                break;
            case MERGE_SORT:
                sortingPanel = new MergeSort(values, layout);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }

        setLayout(new BorderLayout());
        add(sortingPanel, BorderLayout.CENTER);
        setResizable(false);
        setIconImage(new ImageIcon("src/resources/program_logo.png").getImage());
        setSize(DIM_W, DIM_H);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Sorting Visualization");
        setVisible(true);
    }
}
