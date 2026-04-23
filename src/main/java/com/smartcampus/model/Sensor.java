package com.smartcampus.model;

import java.util.UUID;

public class Sensor {
    private String id;
    private String type; // e.g., TEMPERATURE, HUMIDITY, MOTION
    private String status; // ACTIVE, INACTIVE, MAINTENANCE
    private double currentValue;
    private String roomId;

    public Sensor() {
        this.id = UUID.randomUUID().toString();
        this.status = "ACTIVE";
    }

    public Sensor(String type, String roomId) {
        this();
        this.type = type;
        this.roomId = roomId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
