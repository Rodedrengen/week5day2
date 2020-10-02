/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import exceptions.PersonNotFoundException;

/**
 *
 * @author simon
 */
public interface IPersonFacade {

    public abstract PersonDTO addPerson(String fName, String lName, String phone);

    public abstract PersonDTO deletePerson(int id)throws PersonNotFoundException;

    public abstract PersonDTO getPerson(int id) throws PersonNotFoundException; 

    public abstract PersonsDTO getAllPersons();

    public abstract PersonDTO editPerson(PersonDTO p) throws PersonNotFoundException; 

}
