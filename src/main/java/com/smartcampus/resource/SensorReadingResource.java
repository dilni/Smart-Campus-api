package com.smartcampus.resource;

import com.smartcampus.exception.MaintenanceForbiddenException;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
import com.smartcampus.repository.InMemoryDataStore;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private final String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public List<SensorReading> getReadings() {
        return InMemoryDataStore.getReadings(sensorId);
    }

    @POST
    public Response addReading(SensorReading reading) {
        Sensor sensor = InMemoryDataStore.getSensors().get(sensorId);
        
        // Block reading if sensor is in MAINTENANCE
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new MaintenanceForbiddenException("Cannot add reading. Sensor is currently in MAINTENANCE mode.");
        }
        
        if (reading.getId() == null) {
            reading = new SensorReading(reading.getValue());
        }
        
        // Update sensor's current value
        sensor.setCurrentValue(reading.getValue());
        
        InMemoryDataStore.addReading(sensorId, reading);
        
        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}
