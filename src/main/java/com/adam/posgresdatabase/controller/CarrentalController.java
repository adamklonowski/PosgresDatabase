package com.adam.posgresdatabase.controller;
import com.adam.posgresdatabase.entity.CarrentalEntity;
import com.adam.posgresdatabase.entity.EmployeeEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("carrental")
public class CarrentalController {

    static EntityManagerFactory entityManagerFactory;
    static EntityManager entityManager;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");

    }
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response setCar(CarrentalEntity carrental) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(carrental);
        entityManager.getTransaction().commit();
        entityManager.close();
        return Response.ok(carrental).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deleteCar(@PathParam("id") String id) {
        int ID = -1;
        try {
            ID = Integer.parseInt(id);
            if (ID < 1) {
                return Response.status(400, "BadRequest: Id should be a positive integer").build();
            }
        } catch (Exception e) {
            return Response.status(400, "BadRequest: Id should be a positive integer").build();
        }
        try {
            entityManager = entityManagerFactory.createEntityManager();
            Object car = entityManager.find(CarrentalEntity.class, ID);
            if (car == null) {
                return Response.status(404, "NotFound: Car with id = " + ID + " not found in the database").build();
            }
            entityManager.getTransaction().begin();
            entityManager.remove(car);
            entityManager.getTransaction().commit();
            entityManager.close();
            return Response.ok(car).build();
        } catch (Exception e) {
            return Response.status(500, "InternalServerError").build();
        }
    }

    @GET
    @Path("/findmore")
    public Response fullOffer() {
        try {
            URI fullOfferLink = new URI("https://www.rentalcars.com/");
            return Response.temporaryRedirect(fullOfferLink).build();
        } catch (Exception e) {
            return Response.status(500, "InternalServerError").entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("")
    @Consumes("application/json")
    @Produces("application/json")
    public Response setCarStatus(CarrentalEntity carrental, @HeaderParam("RequestID") String requestID) {
        int ID = carrental.getId();
        try {
            if (ID < 1) {
                return Response.status(400, "BadRequest: Id should be a positive integer").header("RequestID", requestID).build();
            }
        } catch (Exception e) {
            return Response.status(400, "BadRequest: Id should be a positive integer").header("RequestID", requestID).build();
        }
        try {
            entityManager = entityManagerFactory.createEntityManager();
            CarrentalEntity oldCar = entityManager.find(CarrentalEntity.class, ID);
            if (carrental == null || oldCar == null) {
                return Response.status(404, "NotFound: Car with id = " + ID + " not found in the database").header("RequestID", requestID).build();
            }
            entityManager.getTransaction().begin();
            Query query = entityManager.createNativeQuery("UPDATE carrental SET availability=" + carrental.isAvailability() + " WHERE id=" + ID);
            query.executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.close();
            return Response.ok(carrental).header("RequestID", requestID).build();
        } catch (Exception e) {
            return Response.status(500, "InternalServerError").entity(e.getMessage()).header("RequestID", requestID).build();
        }
    }
}

