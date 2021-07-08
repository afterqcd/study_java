package com.afterqcd.study.graalvm;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Source;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
        List<Source> jsSources = loadJsLibs();
        try (Engine engine = Engine.create()) {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 1; i++) {
                try (Context context = createContext(engine)) {
                    jsSources.forEach(context::eval);
                    runJs(context);
                }
            }
            System.out.println((System.currentTimeMillis() - start) + "ms");
        }
    }

    private static List<Source> loadJsLibs() throws IOException {
        String[] resourceNames = {
                "js/dayjs.min.js", // https://github.com/iamkun/dayjs
                "js/spark-md5.min.js", // https://github.com/satazor/js-spark-md5
                "js/lodash.min.js" // https://github.com/lodash/lodash
        };

        List<Source> sources = new ArrayList<>();
        for (String name : resourceNames) {
            sources.add(Source.create("js", loadJsLib(name)));
        }
        return sources;
    }

    private static String loadJsLib(String resource) throws IOException {
        URL url = Application.class.getClassLoader().getResource(resource);
        assert(url != null);

        List<String> lines = Files.readLines(new File(url.getFile()), StandardCharsets.UTF_8);
        return Joiner.on("\n").join(lines);
    }

    private static Context createContext(Engine engine) {
        return Context.newBuilder("js").engine(engine).allowAllAccess(true).build();
    }

    private static void runJs(Context context) {
        context.getBindings("js")
                .putMember("root", new Root("{ \"value\": 1 }"));
        context.eval(
                "js",
                "payload = JSON.parse(root.payload)"
        );
        context.eval("js", "answer = { value: _.max([payload.value, 5]), time: dayjs().format('YYYY-MM-DD'), md5: SparkMD5.hash('Hi there') }");
        System.out.println(context.eval("js", "JSON.stringify(answer)").asString());
    }

    public static class Root {
        public final String payload;

        Root(String payload) {
            this.payload = payload;
        }
    }
}


