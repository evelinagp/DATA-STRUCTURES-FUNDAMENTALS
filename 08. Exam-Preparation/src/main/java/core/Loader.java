package core;

import interfaces.Buffer;
import interfaces.Entity;
import model.BaseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Loader implements Buffer {

    private List<Entity> entities;

    public Loader() {
        this.entities = new ArrayList<>();
    }

    @Override
    public void add(Entity entity) {
        this.entities.add(entity);
    }

    @Override
    public Entity extract(int id) {

        Entity entityToRemove = null;
        for (Entity entity : entities) {
            if (entity.getId() == id) {
                entityToRemove = entity;
                break;
            }
        }
        if (entityToRemove != null) {
            this.entities.remove(entityToRemove);
        }
        return entityToRemove;
    }

    @Override
    public Entity find(Entity entity) {
        int entityIndex = entities.indexOf(entity);

        if (entityIndex < 0) {
            return null;
        }

        return this.entities.get(entityIndex);
    }

    @Override
    public boolean contains(Entity entity) {
        return this.entities.contains(entity);
    }

    @Override
    public int entitiesCount() {
        return entities.size();
    }

    @Override
    public void replace(Entity oldEntity, Entity newEntity) {
        Entity oldEntitySearch = this.find(oldEntity);
        if (oldEntitySearch == null) {
            throw new IllegalStateException("Entity not found");
        }
        int oldEntityIndex = entities.indexOf(oldEntity);
        this.entities.set(oldEntityIndex, newEntity);
    }

    @Override
    public void swap(Entity first, Entity second) {
        Entity firstEntitySearch = this.find(first);
        Entity secondEntitySearch = this.find(second);
        if (firstEntitySearch == null || secondEntitySearch == null) {
            throw new IllegalStateException("Entity not found");
        }
        int firstEntityIndex = entities.indexOf(first);
        int secondEntityIndex = entities.indexOf(second);
        Collections.swap(this.entities, firstEntityIndex, secondEntityIndex);
      /*  int temp = 0;
        temp = firstEntityIndex;
        firstEntityIndex = secondEntityIndex;
        secondEntityIndex = temp;*/

    }

    @Override
    public void clear() {
        this.entities.clear();
    }

    @Override
    public Entity[] toArray() {
        return new Entity[this.entities.size()];
    }

    @Override
    public List<Entity> retainAllFromTo(BaseEntity.Status lowerBound, BaseEntity.Status upperBound) {
        return this.entities.stream()
                .filter(e -> e.getStatus().ordinal() >= lowerBound.ordinal() && e.getStatus().ordinal() <= upperBound.ordinal()).collect(Collectors.toList());
    }

    @Override
    public List<Entity> getAll() {
        return new ArrayList<>(this.entities);
    }

    @Override
    public void updateAll(BaseEntity.Status oldStatus, BaseEntity.Status newStatus) {
        for (Entity entity : entities) {
            if (entity.getStatus() == oldStatus) {
                entity.setStatus(newStatus);
            }
        }
    }

    @Override
    public void removeSold() {
        this.entities.removeIf(e -> e.getStatus() == BaseEntity.Status.SOLD);
    }

    @Override
    public Iterator<Entity> iterator() {
        return this.entities.iterator();
    }
}
