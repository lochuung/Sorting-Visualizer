package form;

import sorting.*;
import util.Canvas;
import util.ListHelper;
import util.Resources;
import util.SortType;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

public class MainForm extends JFrame {
    public static final String PROGRAM_TITLE = "Sorting Visualization";
    public static final String LOGO_PATH = "images/program_logo.png";
    private static final String RUN_BUTTON_PATH = "images/run_button.png";
    private static final String RUN_BUTTON_HOVER_PATH =
            "images/run_hover_button.png";
    private JButton bubbleSortButton;
    private JButton selectionSortButton;
    private JButton insertionSortButton;
    private JButton mergeSortButton;
    private final JButton[] sortButtons =
            {bubbleSortButton, selectionSortButton,
                    insertionSortButton, mergeSortButton};
    private JComboBox<String> size;
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
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(Resources
                .getResource(LOGO_PATH)).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(PROGRAM_TITLE);
        setSize(Canvas.DIM_W, Canvas.DIM_H);
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
                        new ImageIcon(Resources
                                .getResource(RUN_BUTTON_HOVER_PATH)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                runButton.setIcon(
                        new ImageIcon(Resources
                                .getResource(RUN_BUTTON_PATH)));
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
        // get size
        Object sizeObj = Objects.requireNonNull(this.size
                .getSelectedItem());
        int size = Integer.parseInt(sizeObj.toString());

        String layout = (String) Objects.requireNonNull(visualizeType
                .getSelectedItem());
        List<Integer> values;
        if (arrayValues.getText().isEmpty()) {
            values = ListHelper.generateRandomNumbers(size);
        } else {
            if (!isValidInput(size)) return;
            values = ListHelper.parseStringToList(arrayValues.getText(),
                    size);
        }
        setVisible(false);
        new SortingFrame(values, layout, choice, this);
    }

    private boolean isValidInput(int size) {
        if (!isValidFormat()) {
            showFormatErrorMessage();
            return false;
        }
        String[] values = arrayValues.getText().split(",");
        for (String value : values) {
            if (!isValidInteger(value)) {
                showRangeErrorMessage();
                return false;
            }
        }
        return true;
    }

    private boolean isValidFormat() {
        String regex = "^[0-9]+(,[0-9]+)*$";
        return arrayValues.getText().matches(regex);
    }

    private boolean isValidInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showFormatErrorMessage() {
        JOptionPane.showMessageDialog(null,
                "Please enter multiple positive numbers " +
                        "separated by commas, " +
                        "e.g. 1,2,3,4,5");
    }

    private void showRangeErrorMessage() {
        JOptionPane.showMessageDialog(null,
                "Please enter " + size.getSelectedItem()
                        + " positive integers " +
                        "between 0 and 999");
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
        Object selectedSize = size.getSelectedItem();
        if (choice == SortType.MERGE_SORT) {
            size.setSelectedItem("8");
            for (int i = 12; i > 8; i--) {
                size.removeItem(String.valueOf(i));
            }
            visualizeType.setSelectedItem("Array");
        } else {
            if (size.getItemCount() < 12 - 5 + 1)
                for (int i = 9; i <= 12; i++)
                    size.addItem(String.valueOf(i));
            size.setSelectedItem(selectedSize);
        }

        visualizeType.setEnabled(choice != SortType.MERGE_SORT);
    }

}
