package se233.chapter2_2.controller;

import org.json.JSONException;
import se233.chapter2_2.Launcher;
import se233.chapter2_2.model.Currency;
import se233.chapter2_2.model.CurrencyEntity;

import java.util.ArrayList;

public class Initialize {
    //2.16
    public static void initialize_app() throws JSONException {
        Currency c = new Currency("USD");
        // change 8 to 6
        ArrayList<CurrencyEntity> c_list = FetchData.fetch_range(c.getShortCode(),6);
        c.setHistorical(c_list);
        c.setCurrent(c_list.get(c_list.size()-1));

        //2.23
        ArrayList<Currency> currencyList = new ArrayList<>();
        currencyList.add(c);
//        Launcher.setCurrency(c);
        Launcher.setCurrency(currencyList);
    }
}
