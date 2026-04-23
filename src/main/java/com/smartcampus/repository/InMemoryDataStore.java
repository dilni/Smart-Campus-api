package com.smartcampus.repository;

import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryDataStore {
    private static final ConcurrentHashMap<String, Room> rooms = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Sensor> sensors = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, List<SensorReading>> readings = new ConcurrentHashMap<>();

    // Room operations
    public static ConcurrentHashMap<String, Room> getRooms() {
        return rooms;
    }

    public static void addRoom(Room room) {
        rooms.put(room.getId(), room);
    }

    // Sensor operations
    public static ConcurrentHashMap<String, Sensor> getSensors() {
        return sensors;
    }

    public static void addSensor(Sensor sensor) {
        sensors.put(sensor.getId(), sensor);
        // Initialize readings list
        readings.putIfAbsent(sensor.getId(), new CopyOnWriteArrayList<>());
    }

    // Readings operations
    public static List<SensorReading> getReadings(String sensorId) {
        return readings.getOrDefault(sensorId, new CopyOnWriteArrayList<>());
    }

    public static void addReading(String sensorId, SensorReading reading) {
        readings.computeIfAbsent(sensorId, k -> new CopyOnWriteArrayList<>()).add(reading);
    }

    public static void clearAll() {
        readings.clear();
        sensors.clear();
        rooms.clear();
    }
}
