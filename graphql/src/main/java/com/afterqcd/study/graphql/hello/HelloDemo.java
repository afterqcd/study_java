package com.afterqcd.study.graphql.hello;

import com.afterqcd.study.graphql.utils.GraphqlUtils;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;

public class HelloDemo {
    public static void main(String[] args) {
        GraphQL graphql = GraphqlUtils.createGraphql("hello.graphqls", buildRuntimeWiring());
        ExecutionResult result = graphql.execute("{ hello }");
        System.out.println(GraphqlUtils.resultToJson(result));
    }

    private static RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(
                        "Query",
                        typeWiring -> typeWiring.dataFetcher("hello", new StaticDataFetcher("world"))
                ).build();
    }
}
