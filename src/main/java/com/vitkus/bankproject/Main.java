package com.vitkus.bankproject;

import com.vitkus.bankproject.Currency.InfoAboutCurrency;
import com.vitkus.bankproject.dates.Dates;

import java.util.Scanner;

public class Main {


    public static void main(String[] Args) {

        System.out.println("Programa skirta apskaičiuoti valiutos pokytį įvedus periodą ir pasirinkus valiutą iš sąrašo.\n ");

        String[] dates = runProgram();


        int choice;
        Scanner scanChoice = new Scanner(System.in);

        do {

            menuList();

            String input = scanChoice.nextLine();
            choice = convertToInteger(input.trim());

            switch (choice) {

                case 1:
                    runProgram();
                    break;
                case 2:
                    setCurrency(dates);
                    break;
                case 3:
                    System.out.println("Programa išjungiama..");
                    break;
            }

        } while (!(choice == 3));

        scanChoice.close();

    }

    public static String[] runProgram() {
        Dates getDates = new Dates();
        String[] dates = getDates.insertDates();

        InfoAboutCurrency currency = new InfoAboutCurrency();
        currency.generateInfoAboutCurrency(dates);

        return dates;
    }

    private static void setCurrency(String[] dates) {
        InfoAboutCurrency currency = new InfoAboutCurrency();
        currency.generateInfoAboutCurrency(dates);
    }

    private static int convertToInteger(String input) {
        try {
            return Integer.valueOf(input);
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
    }

    private static void menuList() {
        System.out.println("Pasirinkite iš meniu:");
        System.out.println("1. Pasirinkti periodą ir valiutą");
        System.out.println("2. Periodą palikti,tačiau pakeisti valiutą");
        System.out.println("3. Užbaigti programą");

    }
}
