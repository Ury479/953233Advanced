package se233.chapter2_2.view;

import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import se233.chapter2_2.model.Currency;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

//2.24
public class CurrencyParentPane extends FlowPane {
    public CurrencyParentPane(ArrayList<Currency> currencyList) throws ExecutionException, InterruptedException {
        this.setPadding(new Insets(0));
        refreshPane(currencyList);
    }

    public void refreshPane(ArrayList<Currency> currencyList) throws ExecutionException, InterruptedException {
        this.getChildren().clear();

        for (Currency currency : currencyList) {
            CurrencyPane cp = new CurrencyPane(currency);
            this.getChildren().add(cp);
        }
    }
}
