package app.utils;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Bounds;
import javafx.scene.Node;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Helpers {

    public static DoubleBinding getCenterX(Node node) {
        return node.layoutXProperty().add(node.getBoundsInParent().getWidth() / 2.0);
    }

    public static DoubleBinding getCenterY(Node node) {
        Bounds boundsInScene = node.localToScene(node.getBoundsInLocal());
        return node.layoutYProperty().add(node.getBoundsInParent().getWidth() / 2.0);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
