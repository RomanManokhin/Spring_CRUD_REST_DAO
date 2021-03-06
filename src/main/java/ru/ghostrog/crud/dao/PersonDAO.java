package ru.ghostrog.crud.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ghostrog.crud.models.Person;

import java.sql.*;
import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    //Подключение к БД без JDBC Template
//    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
//    private static final String USERNAME = "postgres";
//    private static final String PASSWORD = "x2four66";
//
//    //    соединение через JDBC к postgres
//    private static Connection connection;
//
//    static {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }


    public List<Person> index() {
        //алтернатива кода ниже + класс RowMapper
//        return jdbcTemplate.query("SELECT * FROM Persons", new PersonMapper());

        //Если поля и колонки в БД называются одинаково (как у нас Поле у Person name и колонка name) то можно обойтись
        //без своего Mappera, к который передаём наш класс с которым работаем
        //при получении данных из БД используем query
        return jdbcTemplate.query("SELECT * FROM Persons", new BeanPropertyRowMapper<>(Person.class));


        //        List<Person> people = new ArrayList<>();
//        try {
////заводим объект который содержит к себе sql запросы к БД. на нашем соединение создаём объект(запрос) к бд
//            Statement statement = connection.createStatement();
//            //для наглядности
//            String SQL = "SELECT * FROM persons";
////Получаем данные в resultSet  =  через запрос который передаём в бд через statement.executeQuery(SQL);
////            в resultSet лежат наши строки из БД
//            ResultSet resultSet = statement.executeQuery(SQL);
////            statement.executeQuery("SELECT * FROM persons");  тоже самое
////            проходимся по всем строчкам которые получил resultSet
////            это все делает hibernate
//            while (resultSet.next()) {
//                Person person = new Person();
//                //resultSet.getInt("id") получаем id из нашей первой строки для нового person
//                person.setId(resultSet.getInt("id"));
//                person.setName(resultSet.getString("name"));
//                person.setAge(resultSet.getInt("age"));
//                person.setEmail(resultSet.getString("email"));
//
//                people.add(person);
//            }
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return people;
    }

    //если есть объект класса person то он будет возвращён иначе null
    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Persons WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
//        Person person = null;
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("SELECT * FROM Persons WHERE id=?");
//
//            preparedStatement.setInt(1, id);
//            //Выполняем запрос к БД и получаем их в ResultSet
//            ResultSet resultSet =  preparedStatement.executeQuery();
//
//            //сдвиг на одну строчку, в реальной ситуации не нужен
//            resultSet.next();
//
//            person = new Person();
//
//            person.setId(resultSet.getInt("id"));
//            person.setName(resultSet.getString("name"));
//            person.setAge(resultSet.getInt("age"));
//            person.setEmail(resultSet.getString("email"));
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return person;
    }


    public void save(Person person) {
        //при добавлении данных в БД используем update
        jdbcTemplate.update("INSERT INTO Persons VALUES(1, ?, ?, ?)",
                person.getAge(), person.getAge(), person.getEmail());

//        try {
//            //Соединение с БД, и создание SQL запроса
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("INSERT INTO Persons VALUES(1, ?, ?, ?)");
//            //? - соответствует индексу
//            preparedStatement.setString(1, person.getName());
//            preparedStatement.setInt(2, person.getAge());
//            preparedStatement.setString(3, person.getEmail());
//            //Метод который выполняет SQL запрос к БД и изменяет данные(добавляет нового человека)
//            preparedStatement.executeUpdate();
//        } catch (SQLException troubles) {
//            troubles.printStackTrace();
//        }
    }

//    //Обычный Statement---------------------------------------------
//    public void save(Person person) {
////        при выполнении данного метода, сначала будет происходить инкрементация ID а потом добавление объекта в лист
////        person.setId(++PEOPLE_COUNT);
////        people.add(person);
//        try {
////            Соединяемся в с бд
//            Statement statement = connection.createStatement();
////            создаём запрос через который добавляем пользователя БД
//            String SQL = "INSERT INTO persons VALUES(" + 1 + ",'" + person.getName() +
//                    "', " + person.getAge() + ",'" + person.getEmail() + "')";
//            statement.executeUpdate(SQL);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }

    //метод для обновления имени person
    public void update(int id, Person updatedPerson) {

        jdbcTemplate.update("UPDATE Persons SET name=?, age=?, email=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);

//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("UPDATE Persons SET name=?, age=?, email=? WHERE id=?");
//
//            preparedStatement.setString(1, updatedPerson.getName());
//            preparedStatement.setInt(2, updatedPerson.getAge());
//            preparedStatement.setString(3, updatedPerson.getEmail());
//            preparedStatement.setInt(4, id);
//
//            preparedStatement.executeUpdate();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

//        Person personToBeUpdated = show(id);
//
//        personToBeUpdated.setName(updatedPerson.getName());
//        personToBeUpdated.setAge(updatedPerson.getAge());
//        personToBeUpdated.setEmail(updatedPerson.getEmail());

    }

    //если такой id существует в листе(выдается true) происходит удаление элемента
    //        people.removeIf(x -> x.getId() == id);
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Persons WHERE id=?", id);

//        PreparedStatement preparedStatement =
//                null;
//        try {
//            preparedStatement = connection.prepareStatement("DELETE FROM Persons WHERE id=?");
//
//            preparedStatement.setInt(1, id);
//
//            preparedStatement.executeUpdate();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
    }
}
