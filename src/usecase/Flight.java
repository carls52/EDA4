package usecase;


import material.maps.HashTableMapQP;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
        this.minutes = -1;
        this.hours = -1;
        this.delay = -1;
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

    public String getDate()
    {
        LocalDate myDateObj = LocalDate.of(this.year,this.month,this.day);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = myDateObj.format(myFormatObj);

        return formattedDate;
    }

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

    @Override
    public String toString() {

        String output = " ";
        output = this.getDate();
        output += "\t"+this.company+this.flightCode;
        if(this.hours != -1 && this.minutes != -1)
            output += "\t"+this.hours+":"+this.minutes;
        if(this.origin != null)
            output += "\t"+this.origin;
        if(this.destination!=null)
            output+= "\t"+this.destination;
        if(this.delay != -1)
            output += "\tDELAYED ("+this.delay+"min)";
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return flightCode == flight.flightCode &&
                delay == flight.delay &&
                year == flight.year &&
                day == flight.day &&
                month == flight.month &&
                capacity == flight.capacity &&
                company.equals(flight.company) &&
                Objects.equals(origin, flight.origin) &&
                Objects.equals(destination, flight.destination) &&
                date.equals(flight.date) &&
                Objects.equals(map, flight.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, origin, destination, flightCode, delay, year, day, month, capacity, date, map);
    }
}
