package ru.ghostrog.crud.models;


import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {

    private int id;
//    проверка что строка не пустая, и указываем ошибку если оно пустое
    @NotEmpty(message = "Name should not be empty")
//    проверка вводимого значения на длинну
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;
//    указываем минимально возможное число, то есть не менее 0
    @Min(value = 0, message = "Age should be greater then 0")
    private int age;
    @NotEmpty(message = "Email should not be empty")
//    проверка на ввод email, внутри регулярное выражение которе делает проверка
    @Email(message = "Email not correct")
    private String email;


    public Person(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public Person() {

    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
