import sortingpanel.*;
import util.SortType;
import util.Helper;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

public class MainForm extends JFrame {
    private static final int HEIGHT = 500;
    private static final int WIDTH = 500;
    private JButton bubbleSortButton;
    private JButton selectionSortButton;
    private JButton insertionSortButton;
    private JButton mergeSortButton;
    private JComboBox size;
    private JComboBox visualizeType;
    private JTextField arrayValues;
    private JButton runButton;
    private JPanel mainPanel;
    private SortType choice;
    public MainForm() {
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sorting Visualization");
        setVisible(true);
        setSize(500, 500);

        choice = SortType.BUBBLE_SORT;
        bubbleSortButton.setEnabled(false);
        bubbleSortButton.addActionListener(e -> {
            choice = SortType.BUBBLE_SORT;
            bubbleSortButton.setEnabled(false);
            selectionSortButton.setEnabled(true);
            insertionSortButton.setEnabled(true);
            mergeSortButton.setEnabled(true);
        });
        selectionSortButton.addActionListener(e -> {
            choice = SortType.SELECTION_SORT;
            selectionSortButton.setEnabled(false);
            bubbleSortButton.setEnabled(true);
            insertionSortButton.setEnabled(true);
            mergeSortButton.setEnabled(true);
        });
        insertionSortButton.addActionListener(e -> {
            choice = SortType.INSERTION_SORT;
            insertionSortButton.setEnabled(false);
            bubbleSortButton.setEnabled(true);
            selectionSortButton.setEnabled(true);
            mergeSortButton.setEnabled(true);
        });
        mergeSortButton.addActionListener(e -> {
            choice = SortType.MERGE_SORT;
            mergeSortButton.setEnabled(false);
            bubbleSortButton.setEnabled(true);
            selectionSortButton.setEnabled(true);
            insertionSortButton.setEnabled(true);
        });
        runButton.addActionListener(e -> {
            int size = Integer.parseInt(Objects.requireNonNull(this.size.getSelectedItem()).toString());
            String layout = Objects.requireNonNull(this.visualizeType.getSelectedItem()).toString();
            List<Integer> values;
            if (arrayValues.getText().isEmpty()) {
                values = Helper.generateRandomNumbers(size);
            } else {
                values = Helper.parseStringToArray(arrayValues.getText(), size);
            }
            JFrame frame = getVisualizerFrame(values, layout);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(WIDTH, HEIGHT);
            frame.setVisible(true);
            frame.setResizable(false);
        });
    }

    private JFrame getVisualizerFrame(List<Integer> values, String layout) {
        SortingPanel sortingPanel = null;
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
        JFrame frame = new JFrame("Sorting Visualization");
        frame.add(sortingPanel);
        return frame;
    }
}
