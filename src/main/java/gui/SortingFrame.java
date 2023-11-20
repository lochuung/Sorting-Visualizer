package gui;

import util.Resources;
import util.SortType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static util.Canvas.DIM_H;
import static util.Canvas.DIM_W;

public class SortingFrame extends JFrame {
    private final MainForm mainForm;

    public SortingFrame(List<Integer> values, String layout, SortType choice, MainForm mainForm) {
        SortingPanel sortingPanel;
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
        this.mainForm = mainForm;
        sortingPanel.setParentFrame(this);
        setLayout(new BorderLayout());
        add(sortingPanel, BorderLayout.CENTER);
        setResizable(false);
        setIconImage
                (new ImageIcon(Resources.getResource(MainForm.LOGO_PATH))
                .getImage());
        setSize(DIM_W, DIM_H);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(MainForm.PROGRAM_TITLE);
        setVisible(true);
    }

    public MainForm getMainForm() {
        return mainForm;
    }
}
