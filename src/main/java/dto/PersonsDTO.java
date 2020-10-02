package dto;

import entities.Person;
import java.util.ArrayList;
import java.util.List;

public class PersonsDTO {
    
    private List<PersonDTO> all = new ArrayList();

    public PersonsDTO(List<Person> personEntities) {
        for (Person personEntity : personEntities) {
            all.add(new PersonDTO(personEntity));
        }
        
    }
    public List<PersonDTO> getAll(){
        return all;
    }
}