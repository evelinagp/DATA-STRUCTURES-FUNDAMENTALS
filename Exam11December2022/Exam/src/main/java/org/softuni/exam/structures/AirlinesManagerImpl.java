package org.softuni.exam.structures;

import org.softuni.exam.entities.Airline;
import org.softuni.exam.entities.Deliverer;
import org.softuni.exam.entities.Flight;
import org.softuni.exam.entities.Package;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AirlinesManagerImpl implements AirlinesManager {

    private Map<String, Airline> airlinesById;
    private Map<String, List<Flight>> airlinesWithFlights;
    private Map<String, Flight> flightsById;

    public AirlinesManagerImpl() {
        this.airlinesById = new HashMap<>();
        this.airlinesWithFlights = new HashMap<>();
        this.flightsById = new HashMap<>();
    }

    @Override
    public void addAirline(Airline airline) {
        if (contains(airline)) {
            throw new IllegalArgumentException();
        }
        airlinesById.put(airline.getId(), airline);
       airlinesWithFlights.put(airline.getId(), new ArrayList<>());
    }

    @Override
    public void addFlight(Airline airline, Flight flight) {
        if (!contains(airline) || contains(flight)) {
            throw new IllegalArgumentException();
        }


        flightsById.put(flight.getId(), flight);
        airlinesWithFlights.get(airline.getId()).add(flight);

    }

    @Override
    public boolean contains(Airline airline) {
        return airlinesById.containsKey(airline.getId());
    }

    @Override
    public boolean contains(Flight flight) {
        return flightsById.containsKey(flight.getId());
    }

    @Override
    public void deleteAirline(Airline airline) throws IllegalArgumentException {
        if (!contains(airline)) {
            throw new IllegalArgumentException();
        }
        airlinesById.remove(airline.getId());
        List<Flight> removedFlights = airlinesWithFlights.remove(airline.getId());
        removedFlights.forEach(f -> flightsById.remove(f.getId()));
    }

    @Override
    public Iterable<Flight> getAllFlights() {
        return this.flightsById.values();
    }

    @Override
    public Flight performFlight(Airline airline, Flight flight) throws IllegalArgumentException {
        if (!contains(airline) || !contains(flight)) {
            throw new IllegalArgumentException();
        }
        this.flightsById.get(flight.getId()).setCompleted(true);

        Flight flight1 = this.airlinesWithFlights.get(airline.getId()).stream()
                .filter(f -> f.getId().equals(flight.getId())).findFirst().orElse(null);
        if (flight1 != null) {
            flight1.setCompleted(true);
        }
        return flight1;
    }

    @Override
    public Iterable<Flight> getCompletedFlights() {
        return this.flightsById.values().stream().filter(Flight::isCompleted).collect(Collectors.toList());
    }

    @Override
    public Iterable<Flight> getFlightsOrderedByNumberThenByCompletion() {

        return this.flightsById.values().stream().sorted((f1, f2) -> {

                        boolean b1 = f1.isCompleted();
                        boolean b2 = !f2.isCompleted();
                       int result = Boolean.compare(b1, b2);

            if (result == 0) {
                result = f1.getNumber().compareTo(f2.getNumber());

            }
            return result;
                })
                .collect(Collectors.toList());
    }


    @Override
    public Iterable<Airline> getAirlinesOrderedByRatingThenByCountOfFlightsThenByName() {
        return this.airlinesById.values().stream().sorted((a1, a2) -> {

            int result = Double.compare(a2.getRating(), a1.getRating());

            if (result == 0) {

                int firstFlSize = this.airlinesWithFlights.get(a2.getId()).size();
                int secondFlSize = this.airlinesWithFlights.get(a1.getId()).size();

                result = Integer.compare(firstFlSize, secondFlSize);
            }
            if (result == 0) {
                result = a1.getName().compareTo(a2.getName());
            }

            return result;
        }).collect(Collectors.toList());
    }


    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public Iterable<Airline> getAirlinesWithFlightsFromOriginToDestination(String origin, String destination) {
        List<Airline> collect = this.airlinesById.values().stream().filter((a) -> !a.getAirlineFlight().isCompleted() && a.getAirlineFlight().getOrigin().equals(origin) &&
                a.getAirlineFlight().getDestination().equals(destination)).collect(Collectors.toList());

         this.airlinesWithFlights.values().forEach(collect::retainAll);

        return collect;

    }
}

