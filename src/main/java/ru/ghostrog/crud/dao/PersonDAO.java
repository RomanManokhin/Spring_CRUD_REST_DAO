package ru.ghostrog.crud.dao;

import org.springframework.stereotype.Component;
import ru.ghostrog.crud.models.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PersonDAO {
//    поле для автоинкремента
    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
//        people = Arrays.asList(new Person(++PEOPLE_COUNT, "Katia"),
//                new Person(++PEOPLE_COUNT, "Nikita"),
//                new Person(++PEOPLE_COUNT, "Bob"),
//                new Person(++PEOPLE_COUNT, "Igor"));
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "Katia"));
        people.add(new Person(++PEOPLE_COUNT, "Nikita"));
        people.add(new Person(++PEOPLE_COUNT, "Bob"));
        people.add(new Person(++PEOPLE_COUNT, "Igor"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
//        при выполнении данного метода, сначала будет происходить инкрементация ID а потом добавление объекта в лист
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }
    //метод для обновления имени person
    public void update(int id, Person updatedPerson) {
        Person personToBeUpdated = show(id);

        personToBeUpdated.setName(updatedPerson.getName());
    }
    //если такой id существует в листе(выдается true) происходит удаление элемента
    public void delete(int id) {
        people.removeIf(x-> x.getId() == id);
    }
}
