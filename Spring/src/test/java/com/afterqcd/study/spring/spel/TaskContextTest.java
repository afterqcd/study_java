package com.afterqcd.study.spring.spel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Created by afterqcd on 2016/12/20.
 */
public class TaskContextTest {
    private ExpressionParser parser;
    private TaskContext task;

    @Before
    public void setUp() throws Exception {
        Properties jobParams = new Properties();
        jobParams.put("root.directories", "/upload,/immediate");
        Properties jobSession = new Properties();
        jobSession.put("test", "abc");
        JobContext jobContext = new JobContext(jobParams, jobSession);

        Properties taskParams = new Properties();
        taskParams.put("task.mapping.name", "hfds.file.scanner");
        taskParams.put("instances", 3);

        task = new TaskContext(taskParams, jobContext);
        parser = new SpelExpressionParser();
    }

    @After
    public void tearDown() throws Exception {
        task = null;
        parser = null;
    }

    @Test
    public void shouldAccessTaskParameter() throws Exception {
        assertEquals(
                "hfds.file.scanner",
                parser.parseExpression("params['task.mapping.name']").getValue(task)
        );
        assertEquals(3, parser.parseExpression("params['instances']").getValue(task));
    }

    @Test
    public void shouldAccessJobParameter() throws Exception {
        assertEquals(
                "/upload,/immediate",
                parser.parseExpression("job.params['root.directories']").getValue(task)
        );
    }

    @Test
    public void shouldAccessJobSession() throws Exception {
        assertEquals(
                "abc",
                parser.parseExpression("job.session['test']").getValue(task)
        );
    }
}

class TaskContext {
    private Properties params;
    private JobContext job;

    public TaskContext(Properties params, JobContext job) {
        this.params = params;
        this.job = job;
    }

    public Properties getParams() {
        return params;
    }

    public void setParams(Properties params) {
        this.params = params;
    }

    public JobContext getJob() {
        return job;
    }

    public void setJob(JobContext job) {
        this.job = job;
    }
}

class JobContext {
    private Properties params;
    private Properties session;

    public JobContext(Properties params, Properties session) {
        this.params = params;
        this.session = session;
    }

    public Properties getParams() {
        return params;
    }

    public void setParams(Properties params) {
        this.params = params;
    }

    public Properties getSession() {
        return session;
    }

    public void setSession(Properties session) {
        this.session = session;
    }
}
