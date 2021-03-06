package edu.gatech.epidemics.api;

import edu.gatech.epidemics.entities.Person;
import edu.gatech.epidemics.entities.Question;
import edu.gatech.epidemics.service.PersonService;
import edu.gatech.epidemics.service.QuestionService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionApiController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private PersonService personService;

    @GetMapping(value = "/api/questions")
    public List<Question> get() {
        return questionService.findAll();
    }

    @GetMapping(value = "/api/questions/{id}")
    public Question get(@PathVariable int id) {
        Optional<Question> question = questionService.findById(id);
        return question.get();
    }
    
    @RequestMapping(value = "/api/questions", params = "age")
    public List<Question> findByAge(@RequestParam("age") Integer age) {
        return questionService.findByAge(age);
    }
    
    @RequestMapping(value = "/api/questions", params = "patientId")
    public List<Question> findByPatientId(@RequestParam("patientId") Integer patientId) {
        Optional<Person> patient = personService.findById(patientId);
        return questionService.findByAge(patient.get().getAge());
    }
    
    @PostMapping(path = "/api/questions", consumes = "application/json", produces = "application/json")
    public void addQuestion(@RequestBody Question question) {
        questionService.add(question);
    }
}
