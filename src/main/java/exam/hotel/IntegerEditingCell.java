package exam.hotel;

import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class IntegerEditingCell<T, R> extends TableCell<T, Number> {

    private final TextField textField = new TextField();
    private final Pattern intPattern = Pattern.compile("-?\\d+");

    public IntegerEditingCell() {
        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (! isNowFocused) {
                processEdit();
            }
        });
        textField.setOnAction(event -> processEdit());
    }

    private void processEdit() {
        String text = textField.getText();
        if (intPattern.matcher(text).matches()) {
            commitEdit(Integer.parseInt(text));
        } else {
            cancelEdit();
        }
    }

    @Override
    public void updateItem(Number value, boolean empty) {
        super.updateItem(value, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            setText(null);
            textField.setText(value.toString());
            setGraphic(textField);
        } else {
            setText(value.toString());
            setGraphic(null);
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        Number value = getItem();
        if (value != null) {
            textField.setText(value.toString());
            setGraphic(textField);
            setText(null);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().toString());
        setGraphic(null);
    }
}


