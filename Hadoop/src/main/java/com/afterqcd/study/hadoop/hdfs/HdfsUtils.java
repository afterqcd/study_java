package com.afterqcd.study.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by afterqcd on 16/8/4.
 */
public class HdfsUtils {
    public static FileSystem createFileSystem(String uri, String user)
            throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.default.name", uri);
        return FileSystem.get(new URI(uri), conf, user);
    }

    public static List<String> listFiles(FileSystem hdfs, String extension, String... directories)
            throws IOException {
        List<String> files = new ArrayList<String>();
        for (String directory : directories) {
            files.addAll(listFiles(hdfs, extension, directory));
        }
        return files;
    }

    public static List<String> listFiles(FileSystem hdfs, String extension, String directory)
            throws IOException {
        List<String> files = new ArrayList<String>();
        RemoteIterator<LocatedFileStatus> fileStatues = hdfs.listFiles(new Path(directory), true);
        while(fileStatues.hasNext()) {
            LocatedFileStatus fileStatus = fileStatues.next();
            String file = fileStatus.getPath().toString();
            if (file.endsWith(extension)) {
                files.add(file);
            }
        }
        return files;
    }

    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        FileSystem hdfs = HdfsUtils.createFileSystem("hdfs://10.97.11.1:9020", "shuyou");
        List<String> files = HdfsUtils.listFiles(hdfs, "parquet", "/user/fyd", "/user/th");
    }
}
