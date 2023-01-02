package tripadministratorjava;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TripAdministratorImpl implements TripAdministrator {

    private Map<String, Company> companiesByNames;
    private Map<String, List<Trip>> companiesTrips;
    private Map<String, Trip> tripsByIds;


    public TripAdministratorImpl() {
        this.companiesByNames = new HashMap<>();
        this.companiesTrips = new HashMap<>();
        this.tripsByIds = new HashMap<>();

    }

    @Override
    public void addCompany(Company c) {
        if (companiesByNames.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }
        companiesByNames.put(c.name, c);
    }


    @Override
    public void addTrip(Company c, Trip t) {
        if (exist(t) || !exist(c)) {
            throw new IllegalArgumentException();
        }

        companiesTrips.putIfAbsent(c.name, new ArrayList<>());

        if (c.tripOrganizationLimit == companiesTrips.get(c.name).size()) {
            throw new IllegalArgumentException();
        }
        tripsByIds.put(t.id, t);
        companiesTrips.get(c.name).add(t);

    }

    @Override
    public boolean exist(Company c) {
        return companiesByNames.containsKey(c.name);
    }

    @Override
    public boolean exist(Trip t) {
        return tripsByIds.containsKey(t.id);
    }

    @Override
    public void removeCompany(Company c) {
        if (!exist(c)) {
            throw new IllegalArgumentException();
        }
        companiesByNames.remove(c.name);
        List<Trip> removedTrips = companiesTrips.remove(c.name);
        removedTrips.forEach(tr -> tripsByIds.remove(tr.id));
    }

    @Override
    public Collection<Company> getCompanies() {
        return companiesByNames.values();
    }

    @Override
    public Collection<Trip> getTrips() {
        return tripsByIds.values();
    }

    @Override
    public void executeTrip(Company c, Trip t) {
        if (!exist(c) || !exist(t)) {
            throw new IllegalArgumentException();
        }
        List<Trip> companyWithTrips = companiesTrips.get(c.name);

        boolean removed = companyWithTrips.removeIf(trip -> trip.id.equals(t.id));
        if (!removed) {
            throw new IllegalArgumentException();
        }
        tripsByIds.remove(t.id);
    }

    @Override
    public Collection<Company> getCompaniesWithMoreThatNTrips(int n) {

        List<Company> companies = getCompanies().stream().filter(c -> c.tripOrganizationLimit > n)
                .collect(Collectors.toList());
        if(companies.size() != 0) {
            companies = companies.stream().filter(c -> companiesTrips.get(c.name).size() > n).collect(Collectors.toList());
        }
       return companies;
    }

    @Override
    public Collection<Trip> getTripsWithTransportationType(Transportation t) {
        return getTrips().stream().filter(tr -> tr.transportation.equals(t))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Trip> getAllTripsInPriceRange(int lo, int hi) {
        return getTrips().stream().filter(tr -> tr.price >= lo && tr.price <= hi)
                .collect(Collectors.toList());
    }

}

/*
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TripAdministratorImpl implements TripAdministrator {

    private Map<String, Company> companiesByNames;
    private Map<String, List<Trip>> companiesTrips;
    private Map<String, Trip> tripsByIds;


    public TripAdministratorImpl() {
        this.companiesByNames = new HashMap<>();
        this.companiesTrips = new HashMap<>();
        this.tripsByIds = new HashMap<>();

    }

    @Override
    public void addCompany(Company c) {
        if (companiesByNames.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }
        companiesByNames.put(c.name, c);
    }


    @Override
    public void addTrip(Company c, Trip t) {
        if (exist(t) || !exist(c)) {
            throw new IllegalArgumentException();
        }
        tripsByIds.put(t.id, t);
        companiesTrips.put(c.name, new ArrayList<>());

        if (c.tripOrganizationLimit == companiesTrips.get(c.name).size()) {
            throw new IllegalArgumentException();
        }

        companiesTrips.get(c.name).add(t);

    }

    @Override
    public boolean exist(Company c) {
        return companiesByNames.containsKey(c.name);
    }

    @Override
    public boolean exist(Trip t) {
        return tripsByIds.containsKey(t.id);
    }

    @Override
    public void removeCompany(Company c) {
        if (!exist(c)) {
            throw new IllegalArgumentException();
        }
        companiesByNames.remove(c.name);
        List<Trip> removedTrips = companiesTrips.remove(c.name);
        removedTrips.forEach(tr -> tripsByIds.remove(tr.id));
    }

    @Override
    public Collection<Company> getCompanies() {
        return companiesByNames.values();
    }

    @Override
    public Collection<Trip> getTrips() {
        return tripsByIds.values();
    }

    @Override
    public void executeTrip(Company c, Trip t) {
        if (!exist(c) || !exist(t)) {
            throw new IllegalArgumentException();
        }
        List<Trip> companyWithTrips = companiesTrips.get(c.name);

        boolean removed = companyWithTrips.removeIf(trip -> trip.id.equals(t.id));
        if (!removed) {
            throw new IllegalArgumentException();
        }
        tripsByIds.remove(t.id);
    }

    @Override
    public Collection<Company> getCompaniesWithMoreThatNTrips(int n) {
        return getCompanies().stream().filter(c -> companiesTrips.get(c.name).size() > n)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Trip> getTripsWithTransportationType(Transportation t) {
        return getTrips().stream().filter(tr -> tr.transportation.equals(t))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Trip> getAllTripsInPriceRange(int lo, int hi) {
        return getTrips().stream().filter(tr -> tr.price >= lo && tr.price <= hi)
                .collect(Collectors.toList());
    }

}*/
