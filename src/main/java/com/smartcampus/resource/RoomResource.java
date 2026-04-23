package com.smartcampus.resource;

import com.smartcampus.exception.ConflictException;
import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.repository.InMemoryDataStore;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collection;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    @GET
    public Collection<Room> getAllRooms() {
        return InMemoryDataStore.getRooms().values();
    }

    @GET
    @Path("/{id}")
    public Response getRoom(@PathParam("id") String id) {
        Room room = InMemoryDataStore.getRooms().get(id);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(room).build();
    }

    @POST
    public Response createRoom(Room room) {
        if (room.getId() == null) {
            room = new Room(room.getName(), room.getCapacity());
        }
        InMemoryDataStore.addRoom(room);
        return Response.status(Response.Status.CREATED).entity(room).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateRoom(@PathParam("id") String id, Room updatedRoom) {
        Room room = InMemoryDataStore.getRooms().get(id);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        room.setName(updatedRoom.getName());
        room.setCapacity(updatedRoom.getCapacity());
        InMemoryDataStore.getRooms().put(id, room);
        return Response.ok(room).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") String id) {
        Room room = InMemoryDataStore.getRooms().get(id);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Check for active sensors in the room
        boolean hasActiveSensors = InMemoryDataStore.getSensors().values().stream()
                .anyMatch(s -> id.equals(s.getRoomId()) && "ACTIVE".equalsIgnoreCase(s.getStatus()));

        if (hasActiveSensors) {
            throw new ConflictException("Cannot delete room with active sensors.");
        }

        InMemoryDataStore.getRooms().remove(id);
        return Response.noContent().build();
    }
}
