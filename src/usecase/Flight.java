package usecase;


import material.maps.HashTableMapQP;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Flight {
    private int hours;
    private int minutes;
    private String company,origin,destination;
    private int flightCode,delay;
    private int year,day,month,capacity;
    private Date date;

    public Flight(int hours, int minutes, String company, String origin, String destination, int flightCode, int delay,
                  int year, int day, int month, int capacity, HashTableMapQP<String, String> map)
    {
        this.hours = hours;
        this.minutes = minutes;
        this.company = company;
        this.origin = origin;
        this.destination = destination;
        this.flightCode = flightCode;
        this.delay = delay;
        this.year = year;
        this.day = day;
        this.month = month;
        this.capacity = capacity;
        this.map = map;
    }

    private HashTableMapQP<String,String> map;


    public Flight(){

    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public String getCompany() {
        return company;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public int getFlightCode() {
        return flightCode;
    }

    public int getDelay() {
        return delay;
    }

    public int getYear() {
        return year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setFlightCode(int flightCode) {
        this.flightCode = flightCode;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Date getTime(){return null;}

    public void setTime(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public void setDate(int year, int month, int day) {
       this.year = year;
       this.day = day;
       this.month = month;
    }
    public void setProperty(String attribute, String value) {
        this.map.put(attribute,value);
    }

    public String getProperty(String attribute) {
        return map.get(attribute);
    }

    public Iterable<String> getAllAttributes() {
        return map.keys();
    }

}
