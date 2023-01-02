package org.softuni.exam.structures;

import org.softuni.exam.entities.Deliverer;
import org.softuni.exam.entities.Package;

import java.util.*;
import java.util.stream.Collectors;

public class DeliveriesManagerImpl implements DeliveriesManager {
    private Map<String, Deliverer> deliverersById;
    private Map<String, List<Package>> deliverersWithPackages;
    private Map<String, Package> packagesById;


    public DeliveriesManagerImpl() {
        this.deliverersById = new HashMap<>();
        this.deliverersWithPackages = new HashMap<>();
        this.packagesById = new HashMap<>();

    }

    @Override
    public void addDeliverer(Deliverer deliverer) {
        if (!contains(deliverer)) {
            this.deliverersById.put(deliverer.getId(), deliverer);
            this.deliverersWithPackages.putIfAbsent(deliverer.getId(), new ArrayList<>());
        }
    }

    @Override
    public void addPackage(Package _package) {
        if (!contains(_package)) {
            this.packagesById.put(_package.getId(), _package);
            /*   this.deliverersWithPackages.get(deliverer.getId(), new ArrayList<>());*/
        }
    }

    @Override
    public boolean contains(Deliverer deliverer) {
        return this.deliverersById.containsKey(deliverer.getId());
    }

    @Override
    public boolean contains(Package _package) {
        return this.packagesById.containsKey(_package.getId());

    }

    @Override
    public Iterable<Deliverer> getDeliverers() {
        return deliverersById.values();
    }

    @Override
    public Iterable<Package> getPackages() {
        return packagesById.values();
    }

    @Override
    public void assignPackage(Deliverer deliverer, Package _package) throws IllegalArgumentException {
        if (!contains(deliverer) || !contains(_package)) {
            throw new IllegalArgumentException();
        }
        deliverersWithPackages.get(deliverer.getId()).add(_package);
       }


    @Override
    public Iterable<Package> getUnassignedPackages() {
        this.deliverersWithPackages.values().stream().forEach(p -> {
            this.packagesById.values().removeAll(p);
        });
        return packagesById.values();
    }

    @Override
    public Iterable<Package> getPackagesOrderedByWeightThenByReceiver() {

        return this.packagesById.values().stream().sorted((p1,p2)-> {
            int result = Double.compare(p2.getWeight(), p1.getWeight());
                    if(result == 0){
                            result = p1.getReceiver().compareTo(p2.getReceiver());
                        }
return result;

                })
                .collect(Collectors.toList());
    }


    @Override
    public Iterable<Deliverer> getDeliverersOrderedByCountOfPackagesThenByName() {
        return this.deliverersById.values().stream().sorted((d1, d2) -> {

            int firstPSize = this.deliverersWithPackages.get(d1.getId()).size();
            int secondPSize = this.deliverersWithPackages.get(d2.getId()).size();

            int result = Integer.compare(secondPSize, firstPSize);
            if (result == 0) {
                result = d1.getName().compareTo(d2.getName());
            }

            return result;
        }).collect(Collectors.toList());
    }
}
