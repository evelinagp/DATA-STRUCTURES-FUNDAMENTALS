package barbershopjava;

import java.util.*;
import java.util.stream.Collectors;

public class BarberShopImpl implements BarberShop {
    private Map<String, Barber> barbersByNames;
    private Map<String, List<Client>> barbersClients;
    private Map<String, Client> clientsByNames;


    public BarberShopImpl() {
        this.barbersByNames = new HashMap<>();
        this.barbersClients = new HashMap<>();
        this.clientsByNames = new HashMap<>();
    }

    @Override
    public void addBarber(Barber b) {
        if (barbersByNames.containsKey(b.name)) {
            throw new IllegalArgumentException();
        }
        barbersByNames.put(b.name, b);
        barbersClients.put(b.name, new ArrayList<>());

    }

    @Override
    public void addClient(Client c) {
        if (clientsByNames.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }
        clientsByNames.put(c.name, c);
    }

    @Override
    public boolean exist(Barber b) {
        if (barbersByNames.containsKey(b.name)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean exist(Client c) {
        if (clientsByNames.containsKey(c.name)) {
            return true;
        }
        return false;
    }

    @Override
    public Collection<Barber> getBarbers() {
        return barbersByNames.values();

    }

    @Override
    public Collection<Client> getClients() {
        return clientsByNames.values();
    }

    @Override
    public void assignClient(Barber b, Client c) {
        if (!exist(b) || !exist(c)) {
            throw new IllegalArgumentException();
        }
        c.barber = b;
        barbersClients.get(b.name).add(c);
    }

    @Override
    public void deleteAllClientsFrom(Barber b) {
        if (!exist(b)) {
            throw new IllegalArgumentException();
        }
        List<Client> clients = barbersClients.get(b.name);
        clients.clear();

    }

    @Override
    public Collection<Client> getClientsWithNoBarber() {
        return getClients().stream().filter(c -> c.barber == null).collect(Collectors.toList());
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithClientsCountDesc() {
        return getBarbers().stream().sorted((b1, b2) -> {
                    int firstBarberClients = barbersClients.get(b1.name).size();
                    int secondBarberClients = barbersClients.get(b2.name).size();

                    return Integer.compare(secondBarberClients, firstBarberClients);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithStarsDescendingAndHaircutPriceAsc() {
        return getBarbers().stream().sorted((b1, b2) -> {
                    int result = Integer.compare(b2.stars, b1.stars);

                    if (result == 0) {
                        result = Integer.compare(b1.haircutPrice, b2.haircutPrice);
                    }

                    return result;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Client> getClientsSortedByAgeDescAndBarbersStarsDesc() {
        return getClients().stream().sorted((c1, c2) -> {
            int result = Integer.compare(c2.age, c1.age);

                    if (result == 0) {
                        result = Integer.compare(c2.barber.stars, c1.barber.stars);
                    }

                    return result;
                })
                .collect(Collectors.toList());
    }
}
