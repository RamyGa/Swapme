package com.ramy.swapme.users;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Slf4j
public class MyDateTime {

SimpleDateFormat formatter;
long time ;
Calendar calendar;



    public MyDateTime() {
        time = System.currentTimeMillis();
         calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
    }

    public int DB_Year(String year){
        int DB_year = Integer.parseInt(year.split("-")[0]);
        return DB_year;
    }
    public int DB_Month(String month){
        int DB_year = Integer.parseInt(month.split("-")[0]);
        return DB_year;
    }
    public int DB_Day(String day){
        int DB_year = Integer.parseInt(day.split("-")[0]);
        return DB_year;
    }






    public int SystemCurrentYear(){
        int mYear = calendar.get(Calendar.YEAR);
        return mYear;

    }
    public int SystemCurrentMonth(){
        int mMonth = calendar.get(Calendar.MONTH);
        return mMonth;
    }
    public int SystemCurrentDay(){
      int mDay = calendar.get(Calendar.DAY_OF_MONTH);
      return mDay;
    }

    public String Date_Time(){
        time = System.currentTimeMillis();
        formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
       return formatter.format(time);
    }


    public String TODAYS_Month_Day_Year(){
        time = System.currentTimeMillis();
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(time);
    }




}
