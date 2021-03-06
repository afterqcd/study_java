package com.afterqcd.study.hadoop.hdfs;

import com.google.common.collect.Sets;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by afterqcd on 16/7/19.
 */
public class Authentication {

    private static final Set<String> actions = Sets.newHashSet("ls", "mkdir");

    public static void main(String[] args) throws Exception {
        run(args);
    }

    public static void run(String... args) throws Exception {
        System.out.println(Arrays.toString(args));

        if (args.length < 3 || !actions.contains(args[1])) {
            System.err.println("Usage: java -jar *.jar user action[ls/mkdir] directory");
            return;
        }

        String user = args[0];
        String action = args[1];
        String directory = args[2];

        Configuration conf = new Configuration();

        conf.set("fs.default.name", "hdfs://10.97.11.1:9020");
        FileSystem hdfs = FileSystem.get(new URI("hdfs://10.97.11.1:9020"), conf, user);

        if (action.equals("ls")) {
            list(directory, hdfs);
        } else if (action.equals("mkdir")) {
            mkdir(directory, hdfs);
        }
    }

    private static void list(String directory, FileSystem hdfs) throws IOException {
        Path path = new Path(directory);
        FileStatus[] files = hdfs.listStatus(path);
        for (FileStatus file : files) {
            System.out.println(file.getPath());
        }
    }

    private static void mkdir(String directory, FileSystem hdfs) throws IOException {
        Path path = new Path(directory);
        hdfs.mkdirs(path);
    }
}
