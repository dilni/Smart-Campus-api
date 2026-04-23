package com.smartcampus.resource;

import com.smartcampus.exception.UnprocessableEntityException;
import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.repository.InMemoryDataStore;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    @GET
    public Collection<Sensor> getSensors(@QueryParam("type") String type) {
        Collection<Sensor> allSensors = InMemoryDataStore.getSensors().values();
        if (type != null && !type.isEmpty()) {
            return allSensors.stream()
                    .filter(s -> type.equalsIgnoreCase(s.getType()))
                    .collect(Collectors.toList());
        }
        return allSensors;
    }

    @GET
    @Path("/{id}")
    public Response getSensor(@PathParam("id") String id) {
        Sensor sensor = InMemoryDataStore.getSensors().get(id);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(sensor).build();
    }

    @POST
    public Response createSensor(Sensor sensor) {
        if (sensor.getRoomId() == null || !InMemoryDataStore.getRooms().containsKey(sensor.getRoomId())) {
            throw new UnprocessableEntityException("Provided roomId does not exist.");
        }
        
        if (sensor.getId() == null) {
            sensor = new Sensor(sensor.getType(), sensor.getRoomId());
        }
        
        InMemoryDataStore.addSensor(sensor);
        
        // Add sensor reference to the room
        Room room = InMemoryDataStore.getRooms().get(sensor.getRoomId());
        if (room != null) {
            room.addSensor(sensor.getId());
        }
        
        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateSensor(@PathParam("id") String id, Sensor updatedSensor) {
        Sensor sensor = InMemoryDataStore.getSensors().get(id);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        sensor.setType(updatedSensor.getType());
        sensor.setStatus(updatedSensor.getStatus());
        
        return Response.ok(sensor).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSensor(@PathParam("id") String id) {
        Sensor sensor = InMemoryDataStore.getSensors().get(id);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        // Remove reference from room
        Room room = InMemoryDataStore.getRooms().get(sensor.getRoomId());
        if (room != null) {
            room.removeSensor(id);
        }
        
        InMemoryDataStore.getSensors().remove(id);
        return Response.noContent().build();
    }

    // Sub-Resource Locator for Readings
    @Path("/{sensorId}/read")
    public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") String sensorId) {
        // Validate sensor exists before delegating
        Sensor sensor = InMemoryDataStore.getSensors().get(sensorId);
        if (sensor == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new SensorReadingResource(sensorId);
    }
}
