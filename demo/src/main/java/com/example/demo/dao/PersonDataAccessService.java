package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        final String sqlCom = "INSERT INTO person (id,name) VALUES (?,?)";
        return jdbcTemplate.update(sqlCom,id,person.getName());
    }

    @Override
    public List<Person> selectALlPeople() {
        final String sqlCom = "SELECT id, name FROM person";
        return jdbcTemplate.query(sqlCom, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(id, name);
        });
    }
    @Override
    public int deletePersonById(UUID id) {
        final String sqlCom = "DELETE FROM person WHERE id = ?";
        return jdbcTemplate.update(sqlCom,id);
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        final String sqlCom = "UPDATE person SET name = ? WHERE id = ?";
        return jdbcTemplate.update(sqlCom,person.getName(),id);
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        final String sqlCom = "SELECT id, name FROM person WHERE id = ?";
        Person person = jdbcTemplate.queryForObject(sqlCom, new Object[]{id},(resultSet, i) -> {
            String name = resultSet.getString("name");
            return new Person(id, name);
        });
        return Optional.ofNullable(person);
    }
}
