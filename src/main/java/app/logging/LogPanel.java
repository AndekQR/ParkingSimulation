package app.logging;

import app.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class LogPanel extends BorderPane {

    public static final double PANEL_WIDTH=300;
    private static final double PADDING_SIZE=10;
    private final ListView<LogData> listView=new ListView<>();
    private final ObservableList<LogData> logDataList=FXCollections.observableArrayList();

    public LogPanel() {
        this.setPrefWidth(PANEL_WIDTH);
        this.init();

    }

    public void addMessage(String message) {
        LogData logData=new LogData(message);
        this.logDataList.add(logData);
    }

    private void init() {
        this.setCenter(listView);
        listView.setStyle("-fx-border-color: black");
        listView.setStyle("-fx-border-style: none solid none solid");
        listView.setStyle("-fx-padding: 1px");
        listView.setItems(logDataList);
        listView.setCellFactory(logDataListView -> new LogDataCell());
        listView.setPrefWidth(PANEL_WIDTH);
        listView.setPrefHeight(Main.WINDOW_HEIGHT);

    }
}
