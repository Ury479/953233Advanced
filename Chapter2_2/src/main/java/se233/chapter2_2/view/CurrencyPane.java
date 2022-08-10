package se233.chapter2_2.view;

import se233.chapter2_2.controller.AllEventHandlers;
import se233.chapter2_2.controller.draw.DrawGraphTask;
import se233.chapter2_2.model.Currency;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


import java.util.concurrent.*;

//Exercise 2
public class CurrencyPane extends BorderPane implements Callable<Void> {
    private Currency currency;
    private Button watch;
    private Button delete;
    private Button unWatch; // Exercise 3
    public CurrencyPane(Currency currency){
        this.watch = new Button("Watch");
        this.setPadding(new Insets(0));
        this.setPrefSize(640,300);
        this.setStyle("-fx-border-color: black");
        this.delete = new Button("Delete");
        this.unWatch = new Button("Unwatch"); // Excercise 3
        this.watch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onWatch(currency.getShortCode());
            }
        });
        this.delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onDelete(currency.getShortCode());
            }
        });
        this.unWatch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {AllEventHandlers.onUnWatch(currency.getShortCode()); }
        });

        this.setPadding(new Insets(0));
        this.setPrefSize(640,300);
        this.setStyle("-fx-background-color: white");
        try{
            this.refreshPane(currency);
        } catch (ExecutionException e){
            System.out.println("Encountered an execution exception.");
        } catch (InterruptedException e){
            System.out.println("Encountered an interrupted exception.");
        }
    }
    @Override
    public Void call() throws Exception{
        System.out.println("hey");
        return null;
    }
    public void refreshPane(Currency currency) throws ExecutionException,InterruptedException {
        this.currency = currency;
        FutureTask futureTask = new FutureTask(new DrawGraphTask(currency));
        FutureTask futureTask1 = new FutureTask(new genInfoTopArea());
        FutureTask futureTask2 = new FutureTask(new genInfoPane(currency));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(futureTask);
        executor.execute(futureTask1);
        executor.execute(futureTask2);
        VBox currencyGraph = (VBox)futureTask.get();
        this.setTop((Node) futureTask1.get());
        this.setLeft((Node) futureTask2.get());
        this.setCenter(currencyGraph);
    }

    public class genInfoPane implements Callable<Pane>{
        Currency currency;

        public genInfoPane(Currency currency){
            this.currency = currency;
        }
        public Pane call() {
            VBox currencyInfoPane = new VBox(10);
            currencyInfoPane.setPadding(new Insets(5, 25, 5, 25));
            currencyInfoPane.setAlignment(Pos.CENTER);
            Label exchangeString = new Label("");
            Label watchString = new Label("");
            exchangeString.setStyle("-fx-font-size:20");
            watchString.setStyle("-fx-font-size:14");
            if (this.currency != null) {
                exchangeString.setText(String.format("%s: %.4f", currency.getShortCode(), currency.getCurrent().getRate()));
                if (this.currency.getWatch()) {
                    watchString.setText(String.format("(Watch @%.4f)", currency.getWatchRate()));
                }
            }
            currencyInfoPane.getChildren().addAll(exchangeString, watchString);
            return currencyInfoPane;
        }
    }
    public class genInfoTopArea implements Callable<Pane>{
        public HBox call(){
            HBox topArea = new HBox(10);
            topArea.setPadding(new Insets(5));
            topArea.getChildren().addAll(watch,unWatch,delete);
            ((HBox)topArea).setAlignment(Pos.CENTER_RIGHT);
            return topArea;
        }
    }



}
