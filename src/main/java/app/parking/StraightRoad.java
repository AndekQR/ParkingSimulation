package app.parking;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class StraightRoad extends VBox {

    private final double roadPadding;
    private double width;
    private double height;

    public StraightRoad(double width, double height) {
        this.width=width;
        this.height=height;
        this.roadPadding=height * 0.1;
        this.setPrefHeight(this.height);
        this.setPrefWidth(this.width);

        initView();
    }

//    public static void addToParent(GridPane parent, int i, int j, double roadWidth, double roadHeight) {
//        StraightRoad straightRoad=new StraightRoad(roadWidth, roadHeight);
//        parent.add(straightRoad, i, j);
//        straightRoad.initView();
//
//    }

    private void initView() {
        this.setStyle("-fx-background-color: #2a9d8f");
        this.setAlignment(Pos.CENTER);

        VBox road=this.getRoad();
        this.getChildren().add(road);

//        Point2D point2D=road.localToScene(road.getLayoutBounds().getMinX(), road.getLayoutBounds().getMinY());
//        Line roadLine=getRoadLine(point2D.getX(), point2D.getY(), point2D.getX() + width, point2D.getY());
//        road.getChildren().add(roadLine);
    }

    private VBox getRoad() {
        VBox road=new VBox();
        road.setStyle("-fx-background-color: #264653");
        road.setPrefWidth(this.width);
        road.setPrefHeight(this.height);
        road.setAlignment(Pos.CENTER);
        VBox.setMargin(road, new Insets(roadPadding, 0, roadPadding, 0));
        return road;

    }

    private Line getRoadLine(double x1, double y1, double x2, double y2) {
        Line line=new Line();
        line.setStartX(x1);
        line.setStartY(y1);
        line.setEndX(x2);
        line.setStartY(y2);
        line.getStrokeDashArray().addAll(2D, 10D);
        line.setStroke(Paint.valueOf("#e9c46a"));
        line.setStrokeWidth(2.0);
        return line;
    }

}
