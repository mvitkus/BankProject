package com.vitkus.bankproject.currency;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InfoAboutCurrency {

    public void generateInfoAboutCurrency(String[] Dates) {

        String startDate = Dates[0];
        String endDate = Dates[1];
        List<String> currencyTags = generateCurrencyList(startDate);
        String currencyTag = inputCurrencyTag(currencyTags);
        String toList = getInfoAboutCurrency(startDate, endDate, currencyTag);
        System.out.println(toList);

    }

    private String getInfoAboutCurrency(String startDate, String endDate, String currencyTag) {

        String validURL = "https://www.lb.lt/lt/currency/exportlist/?csv=1&currency=" + currencyTag + "&ff=1&class=Eu&type=day&date_from_day=" + startDate + "&date_to_day=" + endDate;

        List<String> currencyName = new ArrayList<>();
        List<String> rateOfExchange = new ArrayList<>();
        List<String> currencyDate = new ArrayList<>();
        String Calculation;

        String line = "";
        String cvsSplitBy = ";";
        try {
            URL url = new URL(validURL);
            URLConnection urlConnection = url.openConnection();
            urlConnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {
                String[] country = line.split(cvsSplitBy);
                currencyName.add(country[0]);
                rateOfExchange.add(country[2]);
                currencyDate.add(country[3]);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String currency = currencyName.get(1);
        String currencyRateEndOfRange = rateOfExchange.get(1);
        String currencyRateStartOfRange = rateOfExchange.get(rateOfExchange.size() - 1);
        String periodEnd = currencyDate.get(1);
        String periodStart = currencyDate.get(currencyDate.size() - 1);
        currency = currency.substring(1, currency.length() - 1);
        currencyRateEndOfRange = currencyRateEndOfRange.replace(",", ".").substring(1, currencyRateEndOfRange.length() - 1);
        currencyRateStartOfRange = currencyRateStartOfRange.replace(",", ".").substring(1, currencyRateStartOfRange.length() - 1);
        periodEnd = periodEnd.substring(1, periodEnd.length() - 1);
        periodStart = periodStart.substring(1, periodStart.length() - 1);

        double rate1 = Double.parseDouble(currencyRateEndOfRange);
        double rate2 = Double.parseDouble(currencyRateStartOfRange);
        double pokytisDouble;

        if (rate1 == rate2) {
            pokytisDouble = 0;
        } else pokytisDouble = ((rate1 * 100) / rate2) - 100;

        String pokytis = String.valueOf(pokytisDouble);
        Calculation = "\nValiuta: " + currency + "| Periodo pradŽia: " + periodStart + ", kursas: " + currencyRateStartOfRange + ", Periodo pabaiga: " + periodEnd + ", kursas: " + currencyRateEndOfRange + " Pokytis: " + pokytis + "%\n";

        return Calculation;
    }

    private String inputCurrencyTag(List<String> currencyTags) {

        boolean isCorrectTag;
        String inputTag;
        do {
            System.out.println("\nPasirinkite Valiutos kodą iš sarašo (Pvz. USD)");
            Scanner input = new Scanner(System.in);
            inputTag = input.next();
            isCorrectTag = currencyTags.contains(inputTag);

            if (isCorrectTag)
                System.out.println("\nPasirinkote: " + " " + inputTag);
            else
                System.out.println("\nTokios valiutos:" + inputTag + " - nėra. Rinkitės iš naujo:");

        } while (!isCorrectTag);

        return inputTag;
    }


    private List<String> generateCurrencyList(String date) {

        String validURL = "https://www.lb.lt/lt/currency/daylyexport/?csv=1&class=Eu&type=day&date_day=" + date;

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        List<List<String>> records = new ArrayList<>();
        List<String> currencyTags = new ArrayList<>();
        try {
            java.net.URL url = new URL(validURL);
            URLConnection urlConnection = url.openConnection();
            urlConnection.addRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            while ((line = br.readLine()) != null) {
                String[] country = line.split(cvsSplitBy);
                records.add(Arrays.asList(country[1].substring(1, country[1].length() - 1), country[0].substring(1, country[0].length() - 1)));

                currencyTags.add(country[1].substring(1, country[1].length() - 1));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        for (int i = 1; i < records.size(); i++) {
            System.out.println(records.get(i));
        }

        return currencyTags;

    }


}
