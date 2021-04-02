package app.logging;

import javafx.scene.control.ListCell;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class LogDataCell extends ListCell<LogData> {

    @Override
    protected void updateItem(LogData item, boolean b) {
        super.updateItem(item, b);

        if (isEmpty() || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setWrapText(true);
            setMinWidth(this.getWidth());
            setMaxWidth(this.getWidth());
            setPrefWidth(this.getWidth());
            setGraphic(null);

            setText("["+item.getAddTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM))+"] "+item.getLogMessage());
        }
    }
}
