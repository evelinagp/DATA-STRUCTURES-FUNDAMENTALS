package core;

import interfaces.Entity;
import interfaces.Repository;
import model.Invoice;
import model.StoreClient;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class Data implements Repository {

    private Queue<Entity> data;

    public Data() {
        this.data = new PriorityQueue<>();
    }

    public Data(Data other) {
        this.data = new PriorityQueue<>(other.data);
    }

    @Override
    public void add(Entity entity) {
        if (entity.getParentId() > 0) {
            Entity parent = this.getById(entity.getParentId());
            parent.addChild(entity);
        }
        this.data.offer(entity);
    }

    @Override
    public Entity getById(int id) {
        for (Entity entity : data) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public List<Entity> getByParentId(int id) {
        List<Entity> result = new ArrayList<>();
        Entity parentEntity = this.getById(id);
        if (parentEntity != null) {
            result.addAll(parentEntity.getChildren());
        }
        return result;
    }

    @Override
    public List<Entity> getAll() {
        return new ArrayList<>(this.data);
    }


    @Override
    public Repository copy() {
        return new Data(this);
    }

    @Override
    public List<Entity> getAllByType(String type) {
        boolean isLocalModel = type.equals(Invoice.class.getSimpleName()) ||
                type.equals(StoreClient.class.getSimpleName()) || type.equals(User.class.getSimpleName());
        if (!isLocalModel) {
            throw new IllegalArgumentException("Illegal type: " + type);
        }
        return this.data.stream().
                filter(entity -> entity.getClass().getSimpleName()
                        .equals(type)).collect(Collectors.toList());
    }

    @Override
    public Entity peekMostRecent() {
        if (this.data.isEmpty()) {
            throw new IllegalStateException("Operation on empty Data");
        }
        Entity peek = this.data.peek();
        return peek;
    }

    @Override
    public Entity pollMostRecent() {
        if (this.data.isEmpty()) {
            throw new IllegalStateException("Operation on empty Data");
        }
        return this.data.poll();
    }

    @Override
    public int size() {
        return this.data.size();
    }
}
