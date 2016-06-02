package com.afterqcd.study.springrest.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    public Map<String, Object> create(@Valid @RequestBody Student student,
                                      BindingResult bindingResult, HttpServletResponse response) {
        HashMap<String, Object> result = new HashMap<>();

        if (bindingResult.hasFieldErrors()) {
            response.setStatus(500);
            result.put("Input student", student);
            for (FieldError fe : bindingResult.getFieldErrors()) {
                result.put(fe.getObjectName() + "." + fe.getField(), fe.getDefaultMessage());
            }
        } else {
            int id = service.add(student);
            result.put("Created student with auto-generated id " + id, student);
        }

        return result;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Student get(@PathVariable int id) {
        return service.getById(id);
    }
}
