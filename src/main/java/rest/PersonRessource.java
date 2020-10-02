package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import entities.Person;
import exceptions.PersonNotFoundException;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("person")
public class PersonRessource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"person page\"}";
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByID(@PathParam("id") Integer id) throws PersonNotFoundException {

        PersonDTO pdto = null;
        try {
            
            pdto = FACADE.getPerson(id);
        
        }finally{
            
        }
        

        return GSON.toJson(pdto);
    }

    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAll() {

        return GSON.toJson(FACADE.getAllPersons());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newPerson(String JsonPerson) {
        PersonDTO pDTO = GSON.fromJson(JsonPerson, PersonDTO.class);

        pDTO = FACADE.addPerson(pDTO.getfName(), pDTO.getlName(), pDTO.getPhone());

        return Response.ok(pDTO).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response editPerson(String person, @PathParam("id") Integer id) throws PersonNotFoundException {

        PersonDTO pdto = GSON.fromJson(person, PersonDTO.class);
        pdto.setId(id);

        PersonDTO NewPDTO = null;
        try {
            NewPDTO = FACADE.editPerson(pdto);
           
        }finally{
            
        }

        return Response.ok(NewPDTO).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public String deletePerson(@PathParam("id") int id) throws PersonNotFoundException {
        
        PersonDTO newPDTO = null;
        try {
            
            newPDTO = FACADE.deletePerson(id);
            
        }finally{
            
        }
        return GSON.toJson(newPDTO);
    }

}
