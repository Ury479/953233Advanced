package se233.chapter2_2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.json.JSONException;
import se233.chapter2_2.controller.Initialize;
import se233.chapter2_2.controller.RefreshTask;
import se233.chapter2_2.model.Currency;
import se233.chapter2_2.view.CurrencyPane;
import se233.chapter2_2.view.CurrencyParentPane;
import se233.chapter2_2.view.TopPane;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Launcher extends Application {

    //2.21
    private static Stage primaryStage;

    //2.15
    private static Scene mainScene;
    private static FlowPane mainPane;
    private static TopPane topPane;

    //2.25
//    private static CurrencyPane currencyPane;
    private static CurrencyParentPane currencyParentPane;

    //2.22
//    private static Currency currency;
    private static ArrayList<Currency> currencyList;
    @Override
    //    public void start(Stage primaryStage) {
    //2.25
    public void start(Stage primaryStage) throws ExecutionException, InterruptedException, JSONException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Currency Watcher");
        this.primaryStage.setResizable(false);

        //2.15
//        System.out.println(FetchData.fetch_range("USD", 6));
        Initialize.initialize_app();
        initMainPane();
        mainScene = new Scene(mainPane);
        this.primaryStage.setScene(mainScene);
        this.primaryStage.show();

        //2.33
        RefreshTask r = new RefreshTask();
        Thread th = new Thread(r);
        th.setDaemon(true);
        th.start();
    }

    public static void setCurrency(ArrayList<Currency> currency) {
        Launcher.currencyList = currency;
    }

    public static ArrayList<Currency> getCurrency() {
        return currencyList;
    }

    //2.15
//    public void initMainPane(){
    //2.25
    public void initMainPane() throws ExecutionException, InterruptedException {
        mainPane = new FlowPane();
        topPane = new TopPane();
        //2.22
//        currencyPane = new CurrencyPane(this.currency);
        //2.25
        currencyParentPane = new CurrencyParentPane(this.currencyList);
        mainPane.getChildren().add(topPane);
//        mainPane.getChildren().add(currencyPane);
        mainPane.getChildren().add(currencyParentPane);
    }

    //2.19
    public static void refreshPane() throws ExecutionException, InterruptedException {
        topPane.refreshPane();
        //2.22
//        currencyPane.refreshPane(currency);
        //2.25
//        currencyPane.refreshPane(currencyList);
        currencyParentPane.refreshPane(currencyList);
        primaryStage.sizeToScene();
    }

    public static void main(String[] args){
        launch(args);
    }
}
