package com.afterqcd.study.flink;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.util.stream.Stream;


/**
 * Created by afterqcd on 2017/3/28.
 */
public class JavaWordCountDemo {
    public static void main(String[] args) throws Exception {
        int port = 9000;
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> lines = env.socketTextStream("localhost", port, "\n");
        SingleOutputStreamOperator<WordCount> wordCounts =
                lines.<String>flatMap((line, words) -> Stream.of(line.split("\\s")).forEach(words::collect))
                        .returns(String.class)
                        .map(w -> new WordCount(w, 1))
                        .keyBy(WordCount::getWord)
                        .timeWindow(Time.seconds(5), Time.seconds(1))
                        .reduce(WordCount::add)
                        .returns(WordCount.class);
        wordCounts.print().setParallelism(1);

        env.execute("Socket Window WordCount");
    }

    static class WordCount {
        private final String word;
        private final int count;

        WordCount(String word, int count) {
            this.word = word;
            this.count = count;
        }

        String getWord() {
            return word;
        }

        WordCount add(WordCount another) {
            return new WordCount(word, count + another.count);
        }

        @Override
        public String toString() {
            return word + ": " + count;
        }
    }
}