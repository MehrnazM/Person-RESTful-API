package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao{

    private static List<Person> DB = new ArrayList<>();
    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectALlPeople() {
        return DB;
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> person = selectPersonById(id);
        if(person.isEmpty()){
            return 0;
        }
        DB.remove(person.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        Optional<Person> currentPerson = selectPersonById(id);
        if(currentPerson.isEmpty()){
            return 0;
        }
        int index = DB.indexOf(currentPerson.get());
        DB.set(index,new Person(id,person.getName()));
        return 1;

    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream().filter(person -> person.getId().equals(id)).findFirst();
    }
}
