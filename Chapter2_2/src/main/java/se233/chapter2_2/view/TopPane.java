package se233.chapter2_2.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import org.json.JSONException;
import se233.chapter2_2.controller.AllEventHandlers;

import java.time.LocalDateTime;

//2.13
public class TopPane extends FlowPane {
    private Button refresh;
    //2.27
//    private Button change;
    private Button add;
    private Label update;

    public TopPane() {
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setPrefSize(640, 20);
        //2.27
//        change = new Button("Change");
        refresh = new Button("Refresh");
        add = new Button("add");

        //2.20
        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onRefresh();
            }
        });

        //2.27
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AllEventHandlers.onAdd();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        update = new Label();
        refreshPane();

        //2.27
//        this.getChildren().addAll(refresh, change, update);
        this.getChildren().addAll(refresh, add, update);
    }

    public void refreshPane() {
        update.setText(String.format("Last update: %s", LocalDateTime.now().toString()));
    }
}
