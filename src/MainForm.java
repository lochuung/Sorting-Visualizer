import sortingpanel.*;
import util.SortType;
import util.ListHelper;

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
    private JButton[] sortButtons =
            {bubbleSortButton, selectionSortButton, insertionSortButton, mergeSortButton};
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
            inputSetting();
        });
        selectionSortButton.addActionListener(e -> {
            choice = SortType.SELECTION_SORT;
            inputSetting();
        });
        insertionSortButton.addActionListener(e -> {
            choice = SortType.INSERTION_SORT;
            inputSetting();
        });
        mergeSortButton.addActionListener(e -> {
            choice = SortType.MERGE_SORT;
            inputSetting();
        });
        runButton.addActionListener(e -> {
            int size = Integer.parseInt(Objects.requireNonNull(this.size.getSelectedItem()).toString());
            String layout = Objects.requireNonNull(this.visualizeType.getSelectedItem()).toString();
            List<Integer> values;
            if (arrayValues.getText().isEmpty()) {
                values = ListHelper.generateRandomNumbers(size);
            } else {
                values = ListHelper.parseStringToList(arrayValues.getText(), size);
            }
            JFrame frame = getVisualizerFrame(values, layout);
        });
    }

    private void inputSetting() {
        for (JButton button : sortButtons) {
            button.setEnabled(true);
        }
        if (choice == SortType.BUBBLE_SORT) {
            bubbleSortButton.setEnabled(false);
        } else if (choice == SortType.SELECTION_SORT) {
            selectionSortButton.setEnabled(false);
        } else if (choice == SortType.INSERTION_SORT) {
            insertionSortButton.setEnabled(false);
        } else {
            mergeSortButton.setEnabled(false);
        }
        visualizeType.setEnabled(true);
        visualizeType.setSelectedItem("Bar");
        size.removeAllItems();
        for (int i = 5; i <= 12; i++) {
            size.addItem(i);
        }
        size.setSelectedItem(8);
        if (choice == SortType.MERGE_SORT) {
            visualizeType.setSelectedItem("Array");
            visualizeType.setEnabled(false);

            for (int i = 0; i < size.getItemCount(); i++) {
                if ((int) size.getItemAt(i) > 8) {
                    size.removeItemAt(i);
                    i--;
                }
            }
        }
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
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setResizable(false);
        return frame;
    }
}
