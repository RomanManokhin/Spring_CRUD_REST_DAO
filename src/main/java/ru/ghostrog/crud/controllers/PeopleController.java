package ru.ghostrog.crud.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ghostrog.crud.dao.PersonDAO;
import ru.ghostrog.crud.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {


    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        //Получим всех людей из DAO и передадим на отображение в представление
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    //при запуске приложение на место id можно передать номер пользователя и получить его данные
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        //Получим человека по id из DAO и передадим на отображение в представление
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    //    @GetMapping("/new")
//    public String newPerson(Model model){
//        model.addAttribute("person", new Person());
//        return "people/new";
//    }
//    2ой вариант
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    //создаём нового человека(новый объект класса person) получает значение из формы и внедряет в model
    //в классе person были созданы аннотации и при добавлении @Valid будет проходить проверка на основании этих аннотаций
    //если используется @Valid должен быть объект BindingResult который принимает в себя ошибки
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
//    если при создании были получены ошибки на основе аннотаций, то мы в данном случае возвращаемся в начало создания person
//т.к. @ModelAttribute автоматически добавляет объект в модель, то при возврате формы people/new, мы увидим ошибки
        //которые автоматически внедрились из-за @Valid
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDAO.save(person);
//        с помощью redirect: мы сообщаем что после выполнения мы переходим на определенную страницу
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
