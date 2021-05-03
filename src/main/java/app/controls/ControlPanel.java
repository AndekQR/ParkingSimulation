package app.controls;

import app.Main;
import app.logging.LogPanel;
import javafx.scene.layout.GridPane;

public class ControlPanel extends GridPane {

    public static final double HEIGHT = 80;

    public ControlPanel() {
        initView();
    }

    private void initView() {
        this.setPrefHeight(HEIGHT);
        this.setPrefWidth(Main.WINDOW_WIDTH - LogPanel.PANEL_WIDTH);
        this.setMaxWidth(Main.WINDOW_WIDTH);
        this.setStyle("-fx-background-color: #edf6f9");
        this.setStyle("-fx-border-top-color: black");
        this.setStyle("-fx-border-style: solid none none none");
    }
}
