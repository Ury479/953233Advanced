package se233.chapter2_2.controller;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import se233.chapter2_2.model.CurrencyEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import org.json.JSONObject;

//2.7
public class FetchData {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static ArrayList<CurrencyEntity> fetch_range(String src, int N) throws JSONException {
        String dateEnd = LocalDate.now().format(formatter);
        String dateStart = LocalDate.now().minusDays(N).format(formatter);
        String apiKey = "4544bfb65a40abc77e92"; //22b380a4506fa02f0348 //4544bfb65a40abc77e92
//        String url_str = String.format("https://free.currconv.com/api/v7/convert?q=%s_THB&compact=ultra&date=%s&endDate=%s&apiKey=%s", src, dateStart, dateEnd, apiKey);
        String url_str = String.format("https://free.currconv.com/api/v7/convert?q=%s_THB&compact=ultra&date=%s&endDate=%s&apiKey=%s", src, dateStart, dateEnd, apiKey);
        // Exercise 1
        String DateEnd2 = LocalDate.now().minusDays(N).format(formatter);
        String DateStart2 = LocalDate.now().minusDays(N+N+1).format(formatter);
        String url_str2 = String.format("https://free.currconv.com/api/v7/convert?q=%s_THB&compact=ultra&date=%s&endDate=%s&apiKey=%s", src, DateStart2, DateEnd2 ,apiKey);
        ArrayList<CurrencyEntity> histList = new ArrayList<>();

        //2.10
        String retrievedJson = null;
        String retrievedJson2 = null;
        try {
            retrievedJson = IOUtils.toString(new URL(url_str), Charset.defaultCharset());
            retrievedJson2 = IOUtils.toString(new URL(url_str2), Charset.defaultCharset());
        } catch (MalformedURLException e) {
            System.out.println("Encountered a Malformed Url exception");
        } catch (IOException e) {
            System.out.println("Encounter an IO exception");
        }

        //2.10
        JSONObject jsonOBJ = new JSONObject(retrievedJson).getJSONObject(String.format("%s_THB", src));
        JSONObject jsonOBJ2 = new JSONObject(retrievedJson2).getJSONObject(String.format("%s_THB",src));
        Iterator keysToCopyIterator = jsonOBJ.keys();
        Iterator keyToCopyIterator2 = jsonOBJ2.keys();
        while (keysToCopyIterator.hasNext()) {
            String key = (String) keysToCopyIterator.next();
            Double rate = Double.parseDouble(jsonOBJ.get(key).toString());
            histList.add(new CurrencyEntity(rate, key));
        }
        while(keyToCopyIterator2.hasNext()){
            String key = (String) keyToCopyIterator2.next();
            double rate = Double.parseDouble(jsonOBJ2.get(key).toString());
            histList.add(new CurrencyEntity(rate, key));
        }
        // Exercise 1

        histList.sort(new Comparator<CurrencyEntity>() {
            @Override
            public int compare(CurrencyEntity o1, CurrencyEntity o2) {
                return o1.getTimestamp().compareTo(o2.getTimestamp());
            }
        });

        return histList;
    }
}
