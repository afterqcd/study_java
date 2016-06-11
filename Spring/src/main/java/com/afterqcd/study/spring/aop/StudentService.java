package com.afterqcd.study.spring.aop;

import com.afterqcd.study.spring.aop.log.RequireLogging;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by afterqcd on 16/6/10.
 */
@Component
@RequireLogging
public class StudentService {
    private final List<String> students = new ArrayList<String>();

    public void add(String student) {
        students.add(student);
    }

    public boolean remove(String student) {
        return students.remove(student);
    }

    public void update(String student) {
        throw new RuntimeException("Failed to update student " + student);
    }
}
