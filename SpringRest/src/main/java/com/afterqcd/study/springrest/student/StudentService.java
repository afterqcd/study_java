package com.afterqcd.study.springrest.student;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by afterqcd on 16/6/1.
 */
@Service
public class StudentService {
    private final AtomicInteger idGenerator = new AtomicInteger(0);
    private final Map<Integer, Student> students = new ConcurrentHashMap<>();

    /**
     * @return all students
     */
    public Collection<Student> getAll() {
        return students.values();
    }

    /**
     * Add student.
     *
     * @param student student
     * @return id
     */
    public int add(Student student) {
        int id = idGenerator.getAndIncrement();
        student.setId(id);
        students.put(id, student);
        return id;
    }

    /**
     * Get student by id.
     * @param id id
     * @return student
     */
    public Student getById(int id) {
        return students.get(id);
    }
}
