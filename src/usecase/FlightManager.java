package usecase;
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
    private List<String> airports = new LinkedList<>();

    public FlightManager() {
    }

    public Flight addFlight(String company, int flightCode, int year, int month, int day)
    {
        if(!exist(flightCode))
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
    private boolean exist (int flightCode)
    {
        for(Integer key: flightsDB.keys())
        {
            if(key.equals(flightCode))
                return false;
        }
        return true;
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
        if(exist(flightCode))
        {
           Flight aux = flightsDB.get(flightCode);
           return aux;
        }
        else
            throw new RuntimeException("Flight not found");
    }


    public void updateFlight(String company, int flightCode, int year, int month, int day, Flight updatedFlightInfo) {
       if(exist(flightCode))
       {
            Flight f = flightsDB.get(flightCode);
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
       if(exist(flight.getFlightCode()))
       {
           //update passengers database
            if(passengerDB.get(dni) != null)
            {
                passengerDB.remove(dni);
                passengerDB.put(dni,newPassenger);
            }
            else
            {
                passengerDB.put(dni,newPassenger);
            }
            //update list of passengers of the flight
            List<Passenger> aux = flightPassengers.get(flight);
            aux.add(newPassenger);
            flightPassengers.remove(flight);
            flightPassengers.put(flight,aux);
       }
       else
           throw new RuntimeException("The flight doesn't exits.");
    }


    public Iterable<Passenger> getPassengers(String company, int flightCode, int year, int month, int day)
    {
        //TODO check if flight is full
        if(exist(flightCode))
        {
            return flightPassengers.get(flightsDB.get(flightCode));
        }
        else
            throw new RuntimeException("The flight doesn't exits.");
    }

    public Iterable<Flight> flightsByDate(int year, int month, int day) {
        throw new RuntimeException("Not yet implemented.");
    }

    public Iterable<Flight> getFlightsByPassenger(Passenger passenger) {
        List<Flight> flightArrayList = new ArrayList<>();
        if(exist(passenger.getDNI()))
        {
            for(Flight f : flightPassengers.keys())
            {
                if(flightPassengers.get(f).contains(passenger))
                {
                    flightArrayList.add(f);
                }
            }
            return flightArrayList;
        }
        else
            return flightArrayList;
    }

    public Iterable<Flight> getFlightsByDestination(String destination, int year, int month, int day) {
        List<Flight> flightArrayList = new ArrayList<>();
        for(Flight f : flightsDB.values())
        {
            //TODO TIME
            if(f.getDestination().equals(destination) && f.getTime() == null)
            {
                flightArrayList.add(f);
            }
        }

        throw new RuntimeException("Not yet implemented.");

    }

}