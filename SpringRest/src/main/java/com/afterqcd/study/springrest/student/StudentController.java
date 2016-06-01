package com.afterqcd.study.springrest.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by afterqcd on 16/6/1.
 */
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService service;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Student> getAll() {
        return service.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> create(@RequestBody Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid student");
        }

        int id = service.add(student);
        HashMap<String, Object> result = new HashMap<>();
        result.put("Created student with auto-generated id " + id, student);
        return result;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Student get(@PathVariable int id) {
        return service.getById(id);
    }
}
