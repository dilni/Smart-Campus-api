package com.smartcampus.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Room {
    private String id;
    private String name;
    private int capacity;
    private List<String> sensorIds;

    public Room() {
        this.id = UUID.randomUUID().toString();
        this.sensorIds = new ArrayList<>();
    }

    public Room(String name, int capacity) {
        this();
        this.name = name;
        this.capacity = capacity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<String> getSensorIds() {
        return sensorIds;
    }

    public void setSensorIds(List<String> sensorIds) {
        this.sensorIds = sensorIds;
    }

    public void addSensor(String sensorId) {
        if (!this.sensorIds.contains(sensorId)) {
            this.sensorIds.add(sensorId);
        }
    }

    public void removeSensor(String sensorId) {
        this.sensorIds.remove(sensorId);
    }
}
