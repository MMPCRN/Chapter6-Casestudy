package se233.chapter5_2.view;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Score extends Pane {
    Label point;
    public Score(int x, int y){
        point = new Label("0");
        setTranslateX(x);
        setTranslateY(y);
        point.setFont(Font.font("Cambria", FontWeight.MEDIUM,30));
        getChildren().addAll(point);
    }
    public void setPoint(int score){
        this.point.setText(Integer.toString(score));
    }

    public Label getPoint() {
        return point;
    }
}
