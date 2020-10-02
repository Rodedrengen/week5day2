package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Person;
import exceptions.PersonNotFoundException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone) {
        
        if(fName.length() <= 0 || lName.length() <= 0){
            
        }
        EntityManager em = emf.createEntityManager();

        Person person = new Person(fName, lName, phone);

        em.getTransaction().begin();

        em.persist(person);

        em.getTransaction().commit();

        return new PersonDTO(person);
    }

    @Override
    public PersonDTO deletePerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        Person person = em.find(Person.class, id);

        if (person == null) {
            throw new PersonNotFoundException("Could not delete, provided id does not exist");
        }

        em.remove(person);
        em.getTransaction().commit();

        return new PersonDTO(person);
    }

    @Override
    public PersonDTO getPerson(int id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        
        Person p = em.find(Person.class, id);
        
        if (p == null) {
            throw new PersonNotFoundException("Could not find person. Id does not exicts");
        }
        
        return new PersonDTO(p);
    }

    @Override
    public PersonsDTO getAllPersons() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> persons = query.getResultList();
        System.out.println(persons.get(0).getFirstName());
        
        PersonsDTO psDTO = new PersonsDTO(persons);
        
        System.out.println("navn "+psDTO.getAll().get(0).getfName());
        return psDTO;
    }

    @Override
    public PersonDTO editPerson(PersonDTO p) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        if (p == null) {
            throw new PersonNotFoundException("Could not edit, provided id does not exist");
        }
        Person editedPerson = new Person(p.getfName(), p.getlName(), p.getPhone());

        editedPerson.setId((int) p.getId());

        em.getTransaction().begin();
        em.merge(editedPerson);
        em.getTransaction().commit();

        return new PersonDTO(editedPerson);
    }

}
