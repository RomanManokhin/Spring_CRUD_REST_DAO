package ru.ghostrog.crud.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.ghostrog.crud.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
//Маппер что бы не дублировать код
public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        Person person = new Person();

        person.setId(resultSet.getInt("id"));
        person.setName(resultSet.getString("name"));
        person.setAge(resultSet.getInt("age"));
        person.setEmail(resultSet.getString("email"));
        return person;
    }
}
