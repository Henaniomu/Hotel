package exam.hotel;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

import java.time.LocalDate;
import java.util.Locale;

public class DatePickerTableCell extends TableCell<Guest, LocalDate> {

    DatePicker picker = new DatePicker();

    public DatePickerTableCell() {
        super();
        Locale.setDefault(new Locale("cs", "CZ"));

        picker.setOnAction(evt -> {
            if (picker.getValue() != null) {
                commitEdit(picker.getValue());
            } else {
                cancelEdit();
                evt.consume();
            }
        });
    }

    @Override
    protected void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                picker.setValue(item);
                setText(null);
                setGraphic(picker);
            } else {
                setText(item.toString());
                setGraphic(null);
            }
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();

        picker.setValue(getItem());
        picker.show();
        setText(null);
        setGraphic(picker);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getItem().toString());
        setGraphic(null);
    }
}
