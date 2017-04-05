package com.afterqcd.study.beam;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;

import java.util.stream.Stream;

/**
 * Created by afterqcd on 2017/3/13.
 */
public class MinimalWordCount {
    public static void main(String[] args) {
        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline pipeline = Pipeline.create(options);
        pipeline.apply(TextIO.Read.from(""))
                .apply("ExtractWords", ParDo.of(new DoFn<String, String>() {
                    @ProcessElement
                    public void processElement(ProcessContext context) {
                        Stream.of(context.element().split("[^a-zA-Z']+"))
                                .filter(w -> !w.isEmpty())
                                .forEach(context::output);
                    }
                }))
                .apply(Count.perElement());
    }
}
