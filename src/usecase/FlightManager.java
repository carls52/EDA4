package usecase;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import material.maps.HashTableMapLP;

public class FlightManager {

    //flights map key = flightCode
    private HashTableMapLP<Integer, Flight> flightsDB = new HashTableMapLP<>();
    //passengers map key = DNI
    private HashTableMapLP<String, Passenger> passengerDB = new HashTableMapLP<>();
    //map each flight with its list of passengers
    private HashTableMapLP<Flight, List<Passenger>> flightPassengers = new HashTableMapLP<>();
    private List<String> airports = new ArrayList<>();

    public FlightManager() {
    }

    public Flight addFlight(String company, int flightCode, int year, int month, int day)
    {
        if(!exist(flightCode,dateToString(year,month,day),company))
        {
            Flight f = new Flight();
            f.setDate(year, month, day);
            f.setFlightCode(flightCode);
            f.setCompany(company);
            return f;
        }
        else
            throw new RuntimeException("The flight already exists.");
    }
    private String dateToString(int year ,int month,int day)
    {
        LocalDate myDateObj = LocalDate.of(year,month,day);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return myDateObj.format(myFormatObj);
    }
    private boolean exist (int flightCode, String date, String company)
    {
        for(Flight f : flightsDB.values())
        {
            if(f.getFlightCode() == flightCode && f.getCompany().equals(company) && f.getDate().equals(date))
                return true;
        }
        return false;
    }
    private boolean exist (String DNI)
    {
        for(String key: passengerDB.keys())
        {
            if(key.equals(DNI))
                return true;
        }
        return false;
    }

    public Flight getFlight(String company, int flightCode, int year, int month, int day) {
        if(exist(flightCode,dateToString(year,month,day),company))
        {
           Flight aux = flightsDB.get(flightCode);
           return aux;
        }
        else
            throw new RuntimeException("Flight not found");
    }


    public void updateFlight(String company, int flightCode, int year, int month, int day, Flight updatedFlightInfo) {
        if(exist(flightCode,dateToString(year,month,day),company))
       {
           if(exist(updatedFlightInfo.getFlightCode(),updatedFlightInfo.getDate(),updatedFlightInfo.getCompany()))
               throw new RuntimeException("The new flight identifiers are already in use.");

            Flight f = flightsDB.get(flightCode);
            if(!airports.contains(f.getDestination()))
                airports.add(f.getDestination());
            if(!airports.contains(updatedFlightInfo.getDestination()))
                airports.add(updatedFlightInfo.getDestination());

            flightsDB.remove(flightCode);
            flightsDB.put(flightCode,updatedFlightInfo);
       }
        else
            throw  new RuntimeException("The flight doesn't exists and can't be updated.");
    }

    public void addPassenger(String dni, String name, String surname, Flight flight) {
       //Passenger newPassenger = new Passenger(dni,name,surname);
        Passenger newPassenger = new Passenger();
        newPassenger.setDNI(dni);
        newPassenger.setName(name);
        newPassenger.setSurname(surname);
        if(exist(flight.getFlightCode(), flight.getDate(), flight.getCompany()))
       {
           if(isFull(flight))
               throw new RuntimeException("This flight doesn't have capacity for more passengers.");
           else {
               //update passengers database
               if (passengerDB.get(dni) != null) {
                   passengerDB.remove(dni);
                   passengerDB.put(dni, newPassenger);
               } else {
                   passengerDB.put(dni, newPassenger);
               }
               //update list of passengers of the flight
               List<Passenger> aux = flightPassengers.get(flight);
               aux.add(newPassenger);
               flightPassengers.remove(flight);
               flightPassengers.put(flight, aux);
           }
       }
       else
           throw new RuntimeException("The flight doesn't exits.");
    }


    public Iterable<Passenger> getPassengers(String company, int flightCode, int year, int month, int day)
    {
        if(exist(flightCode,dateToString(year,month,day),company))
            return flightPassengers.get(flightsDB.get(flightCode));
        else
            throw new RuntimeException("The flight doesn't exits.");
    }

    public Iterable<Flight> flightsByDate(int year, int month, int day) {
        List<Flight> output = new ArrayList<>();
        for(Flight f : flightsDB.values())
        {
            if(f.getDate().equals(dateToString(year,month,day)))
                output.add(f);
        }
        return output;
    }

    private boolean isFull(Flight flight)
    {
        if(flightPassengers.get(flight).size() >= flight.getCapacity())
            return true;
        else
            return false;
    }

    public Iterable<Flight> getFlightsByPassenger(Passenger passenger) {
        List<Flight> flightArrayList = new ArrayList<>();
        if(exist(passenger.getDNI()))
        {
            for(Flight f : flightPassengers.keys())
            {
                if(flightPassengers.get(f).contains(passenger))
                    flightArrayList.add(f);
            }
            return flightArrayList;
        }
        else
            return flightArrayList;
    }

    public Iterable<Flight> getFlightsByDestination(String destination, int year, int month, int day) {
        List<Flight> flightArrayList = new ArrayList<>();
        List<Flight> output = new ArrayList<>();
        flightArrayList = (ArrayList) flightsByDate(year, month, day);
        for(Flight f : flightArrayList)
        {
            if(f.getDestination().equals(destination))
                output.add(f);
        }
        return output;
    }

}