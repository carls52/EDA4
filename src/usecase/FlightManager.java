package usecase;
import java.util.LinkedList;
import java.util.List;

import material.maps.HashTableMapLP;
import material.maps.HashTableMapQP;
import material.maps.HashTableMapSC;

public class FlightManager {

    private HashTableMapLP<Integer, Flight> flights = new HashTableMapLP<>();
    private HashTableMapLP<String, Passenger> passengers = new HashTableMapLP<>();
    private HashTableMapLP<Flight, List<Passenger>> info = new HashTableMapLP<>();
    private List<String> airports = new LinkedList<>();

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
        for(Integer key: flights.keys())
        {
            if(key.equals(flightCode))
                return false;
        }
        return true;
    }

    public Flight getFlight(String company, int flightCode, int year, int month, int day) {
        if(exist(flightCode))
        {
           Flight aux = flights.get(flightCode);
           return aux;
        }
        else
            throw new RuntimeException("Flight not found");
    }


    public void updateFlight(String company, int flightCode, int year, int month, int day, Flight updatedFlightInfo) {
       if(exist(flightCode))
       {
            Flight f = flights.get(flightCode);
            flights.remove(flightCode);
            flights.put(flightCode,updatedFlightInfo);
       }
        else
            throw  new RuntimeException("The flight doesn't exists and can't be updated.");
    }

    public void addPassenger(String dni, String name, String surname, Flight flight) {
       Passenger newPassenger = new Passenger(dni,name,surname);
       if(exist(flight.getFlightCode()))
       {
            if(passengers.get(dni) != null)
            {
                passengers.remove(dni);
                passengers.put(dni,newPassenger);
            }
            else
            {
                passengers.put(dni,newPassenger);
            }
            List<Passenger> aux = info.get(flight);
            aux.add(newPassenger);
            info.remove(flight);
            info.put(flight,aux);
       }
       else
           throw new RuntimeException("The flight doesn't exits.");
    }


    public Iterable<Passenger> getPassengers(String company, int flightCode, int year, int month, int day) {
        throw new RuntimeException("Not yet implemented.");
    }

    public Iterable<Flight> flightsByDate(int year, int month, int day) {
        throw new RuntimeException("Not yet implemented.");
    }

    public Iterable<Flight> getFlightsByPassenger(Passenger passenger) {
        throw new RuntimeException("Not yet implemented.");
    }

    public Iterable<Flight> getFlightsByDestination(String destination, int year, int month, int day) {
        throw new RuntimeException("Not yet implemented.");

    }

}