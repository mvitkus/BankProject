package com.vitkus.bankproject.dates;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dates {

    public String[] insertDates() {

        String dates[] = new String[2];
        dates[0] = insertStartDate();
        dates[1] = insertEndDate(dates);

        return dates;
    }

    private String insertStartDate() {

        String startDate;
        boolean correctDate;

        do {
            System.out.println("\nĮveskite periodo pradžią. Data turi būti tokio formato: (pvz: 2019-01-02), įvesta data turi būti darbo diena. ");
            startDate = dateFromKeyboard();
            correctDate = isValidDate(startDate);

        } while (!correctDate);

        return startDate;
    }

    private String insertEndDate(String[] dates) {

        String startDate = dates[0];
        String endDate;
        boolean correctDate;
        boolean isGreaterThanStrartDate;
        do {
            do {
                System.out.println("\nĮveskite periodo pabaigą. Data turi būti tokio formato: (pvz: 2019-01-02), įvesta data turi būti darbo diena. Periodo pabaiga negali būti ankstesnė, negu periodo pradžia. ");
                endDate = dateFromKeyboard();
                correctDate = isValidDate(endDate);
            } while (!correctDate);

            isGreaterThanStrartDate = isGreaterThanStrartDate(startDate, endDate);

        } while (!isGreaterThanStrartDate);

        return endDate;
    }

    private boolean isGreaterThanStrartDate(String startDate, String endDate) {

        boolean istrue = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {

            istrue = ((dateFormat.parse(endDate).after(dateFormat.parse(startDate)))||(dateFormat.parse(endDate).equals(dateFormat.parse(startDate))));


        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (istrue) {
            return true;
        } else {
            System.out.println("Data įvesta yra ankstesnė, negu periodo pradžia.");
            return false;
        }

    }

    private String dateFromKeyboard() {

        Scanner keyboard = new Scanner(System.in);
        String date = keyboard.nextLine();

        return date;
    }

    private boolean isValidDate(String date) {

        boolean validDate;
        boolean tempValidDate = isValidDateFormat(date);
        boolean isDateInRange;

        if (tempValidDate) {
            boolean workDayCheck = isBusinessDay(date);

            if (workDayCheck) {
                isDateInRange = isDateInRange(date);
                if (isDateInRange) {

                    validDate = true;
                } else {
                    System.out.println("Pasirinkta data nepatenka į rėžius");
                    validDate = false;
                }

            } else {
                System.out.println("Pasirinkta data nėra darbo diena");
                validDate = false;
            }

        } else {
            System.out.println("Blogas datos formatas");
            validDate = false;

        }

        return validDate;
    }


    private boolean isValidDateFormat(String date) {

        String regex = "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
                + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
                + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
                + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);

        return matcher.matches();

    }

    private boolean isBusinessDay(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(df.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return false;
        }

        // Nauji metai
        if (cal.get(Calendar.MONTH) == Calendar.JANUARY
                && cal.get(Calendar.DAY_OF_MONTH) == 1) {
            return false;
        }

        // Vasario 16-oji
        if (cal.get(Calendar.MONTH) == Calendar.FEBRUARY
                && cal.get(Calendar.DAY_OF_MONTH) == 16) {
            return false;
        }

        // Kovo 11-toji
        if (cal.get(Calendar.MONTH) == Calendar.MARCH
                && cal.get(Calendar.DAY_OF_MONTH) == 11) {
            return false;
        }

        // Tarptautinė darbo diena
        if (cal.get(Calendar.MONTH) == Calendar.MAY
                && cal.get(Calendar.DAY_OF_MONTH) == 1) {
            return false;
        }

        // Motinos diena
        if (cal.get(Calendar.MONTH) == Calendar.MAY
                && cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 1
                && cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return false;
        }

        // Tėvo diena
        if (cal.get(Calendar.MONTH) == Calendar.JUNE
                && cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 1
                && cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return false;
        }

        // Joninės
        if (cal.get(Calendar.MONTH) == Calendar.JUNE
                && cal.get(Calendar.DAY_OF_MONTH) == 24) {
            return false;
        }

        // Karaliaus Mindaugo karūnavimo diena
        if (cal.get(Calendar.MONTH) == Calendar.JULY
                && cal.get(Calendar.DAY_OF_MONTH) == 6) {
            return false;
        }

        // Žolinė
        if (cal.get(Calendar.MONTH) == Calendar.AUGUST
                && cal.get(Calendar.DAY_OF_MONTH) == 15) {
            return false;
        }

        // Vėlinės
        if (cal.get(Calendar.MONTH) == Calendar.NOVEMBER
                && cal.get(Calendar.DAY_OF_MONTH) == 1) {
            return false;
        }

        // Kūčios
        if (cal.get(Calendar.MONTH) == Calendar.DECEMBER
                && cal.get(Calendar.DAY_OF_MONTH) == 24) {
            return false;
        }


        // Kalėdos
        if (cal.get(Calendar.MONTH) == Calendar.DECEMBER
                && cal.get(Calendar.DAY_OF_MONTH) == 25) {
            return false;
        }

        // Kalėdų antroji diena
        if (cal.get(Calendar.MONTH) == Calendar.DECEMBER
                && cal.get(Calendar.DAY_OF_MONTH) == 26) {
            return false;
        }

        return true;
    }

    private boolean isDateInRange(String date) {

        String minDay = "2014-09-30";
        String dayToday = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        boolean istrue = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {

            istrue = (dateFormat.parse(date).after(dateFormat.parse(minDay)) || dateFormat.parse(date).equals(dateFormat.parse(minDay))) && (dateFormat.parse(date).before(dateFormat.parse(dayToday)) || dateFormat.parse(date).equals(dateFormat.parse(dayToday)));


        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (istrue) {

            return true;
        } else {
            return false;
        }

    }


}
