import sorting.*;
import util.ListHelper;
import util.SortType;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

public class MainForm extends JFrame {
    private static final int HEIGHT = 600;
    private static final int WIDTH = 600;
    private static final String LOGO_PATH = "src/resources/program_logo.png";
    private static final String RUN_BUTTON_PATH = "src/resources/run_button.png";
    private static final String RUN_BUTTON_HOVER_PATH = "src/resources/run_hover_button.png";
    private JButton bubbleSortButton;
    private JButton selectionSortButton;
    private JButton insertionSortButton;
    private JButton mergeSortButton;
    private final JButton[] sortButtons =
            {bubbleSortButton, selectionSortButton, insertionSortButton, mergeSortButton};
    private JComboBox<Integer> size;
    private JComboBox<String> visualizeType;
    private JTextField arrayValues;
    private JButton runButton;
    private JPanel mainPanel;
    private SortType choice;

    public MainForm() {
        initializeForm();
        setUpButtons();
    }

    private void initializeForm() {
        setContentPane(mainPanel);
        pack();
        setIconImage(new ImageIcon(LOGO_PATH).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sorting Visualization");
        setVisible(true);
        setSize(WIDTH, HEIGHT);
    }

    private void setUpButtons() {
        choice = SortType.BUBBLE_SORT;
        bubbleSortButton.setEnabled(false);
        setUpButtonAction();
        setUpRunButtonHoverEffect();
    }

    private void setUpRunButtonHoverEffect() {
        runButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                runButton.setIcon(
                        new ImageIcon(RUN_BUTTON_HOVER_PATH));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                runButton.setIcon(
                        new ImageIcon(RUN_BUTTON_PATH));
            }
        });
    }

    private void setUpButtonAction() {
        bubbleSortButton.addActionListener(e -> {
            choice = SortType.BUBBLE_SORT;
            updateButtonAndInput();
        });
        selectionSortButton.addActionListener(e -> {
            choice = SortType.SELECTION_SORT;
            updateButtonAndInput();
        });
        insertionSortButton.addActionListener(e -> {
            choice = SortType.INSERTION_SORT;
            updateButtonAndInput();
        });
        mergeSortButton.addActionListener(e -> {
            choice = SortType.MERGE_SORT;
            updateButtonAndInput();
        });
        runButton.addActionListener(e -> executeSort());
    }

    private void executeSort() {
        Object sizeObj = Objects.requireNonNull(this.size.getSelectedItem());
        int size = Integer.parseInt(sizeObj.toString());
        String layout = (String) Objects.requireNonNull(visualizeType.getSelectedItem());
        List<Integer> values;
        if (arrayValues.getText().isEmpty()) {
            values = ListHelper.generateRandomNumbers(size);
        } else {
            String regex = "^[0-9]+(,[0-9]+)*$";
            if (!arrayValues.getText().matches(regex)) {
                JOptionPane.showMessageDialog(null,
                        "Please enter multiple positive numbers separated by commas, " +
                                "e.g. 1,2,3,4,5");
                return;
            }
            values = ListHelper.parseStringToList(arrayValues.getText(), size);
        }
        new SortingFrame(values, layout, choice);
    }

    private void updateButtonAndInput() {
        enableAllButtons();
        disableChosenButton();
        updateInputSetting();
    }

    private void enableAllButtons() {
        for (JButton button : sortButtons) {
            button.setEnabled(true);
        }
    }

    private void disableChosenButton() {
        switch (choice) {
            case BUBBLE_SORT:
                bubbleSortButton.setEnabled(false);
                break;
            case SELECTION_SORT:
                selectionSortButton.setEnabled(false);
                break;
            case INSERTION_SORT:
                insertionSortButton.setEnabled(false);
                break;
            case MERGE_SORT:
                mergeSortButton.setEnabled(false);
                break;
        }
    }

    private void updateInputSetting() {
        int minSize = 5;
        int maxSize = choice == SortType.MERGE_SORT ? 8 : 12;
        size.removeAllItems();
        for (int i = minSize; i <= maxSize; i++) {
            size.addItem(i);
        }
        size.setSelectedItem(maxSize);

        visualizeType.setSelectedItem((choice == SortType.MERGE_SORT) ? "Array" :
                visualizeType.getSelectedItem());
        visualizeType.setEnabled(choice != SortType.MERGE_SORT);
    }

}
