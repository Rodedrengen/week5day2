
import dto.PersonsDTO;
import entities.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utils.EMF_Creator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author simon
 */
public class sletmig {

    public static void main(String[] args) {

        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        
        Person p1 = new Person("John", "Per", "1234");
        Person p2 = new Person("Mikkel", "Per", "4567");
        Person p3 = new Person("Sune", "Per", "8901");
        Person p4 = new Person("Nikolaj", "Per", "2345");
        
        List<Person> liste = new ArrayList();

        liste.add(p4);
        liste.add(p1);
        liste.add(p2);
        liste.add(p3);
        
        PersonsDTO personDTO = new PersonsDTO(liste);

        em.getTransaction().begin();

        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.persist(p4);

        em.getTransaction().commit();

    }
}
