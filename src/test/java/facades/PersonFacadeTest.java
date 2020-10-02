/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.PersonDTO;
import entities.Person;
import exceptions.PersonNotFoundException;
import java.lang.reflect.Executable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import utils.EMF_Creator;

/**
 *
 * @author simon
 */
public class PersonFacadeTest {

    Person p1;
    Person p2;
    Person p3;
    Person p4;

    private static EntityManagerFactory emf;
    private static PersonFacade facade;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();

            p1 = new Person("John", "Per", "1234");
            p2 = new Person("Mikkel", "Per", "4567");
            p3 = new Person("Sune", "Per", "8901");
            p4 = new Person("Nikolaj", "Per", "2345");

            em.persist(p1);
            em.persist(p2);
            em.persist(p4);
            em.persist(p3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Test of addPerson method, of class PersonFacade.
     */
    @Test
    public void testAddPerson() throws PersonNotFoundException {

        String fName = "tester";
        String lName = "testing";
        String phone = "990099";

        PersonDTO exp = facade.addPerson(fName, lName, phone);

        PersonDTO result = facade.getPerson((int) exp.getId());

        assertEquals(fName, result.getfName());
        assertEquals(lName, result.getlName());
        assertEquals(phone, result.getPhone());

    }

    /**
     * Test of deletePerson method, of class PersonFacade.
     */
    @Test
    public void testDeletePerson() throws PersonNotFoundException {
        int expected = 3;
        facade.deletePerson(p1.getId());

        Assertions.assertThrows(NullPointerException.class, () -> {
            PersonDTO pdto = facade.getPerson(p1.getId());
        });
       
        int actual = facade.getAllPersons().getAll().size();
        
        assertEquals(expected, actual);
    }

    /**
     * Test of getPerson method, of class PersonFacade.
     */
    @Test
    public void testGetPerson() throws PersonNotFoundException {

        String exspectedPhone = p1.getPhone();
        String exspectedName = p1.getFirstName();

        String actualPhone = facade.getPerson(p1.getId()).getPhone();
        String actualName = facade.getPerson(p1.getId()).getfName();

        assertEquals(exspectedName, actualName);
        assertEquals(exspectedPhone, actualPhone);
    }

    /**
     * Test of getAllPersons method, of class PersonFacade.
     */
    @Test
    public void testGetAllPersons() {

        int exp = 4;

        int actual = facade.getAllPersons().getAll().size();

        assertEquals(exp, actual);

    }

    /**
     * Test of editPerson method, of class PersonFacade.
     */
    @Test
    public void testEditPerson() throws PersonNotFoundException {

        Person p7 = new Person("John", "Per", "7777");

        p7.setId(p1.getId());

        PersonDTO pdto = new PersonDTO(p7);

        PersonDTO pdto2 = facade.editPerson(pdto);

        PersonDTO pdto1 = facade.getPerson(p1.getId());

        assertEquals("7777", pdto1.getPhone());
        assertEquals("7777", pdto2.getPhone());
    }

}
