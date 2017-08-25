package com.afterqcd.study.graphql.utils;

import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;

import java.io.File;

public class GraphqlUtils {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * 创建GraphQL
     * @param schemaFilePath
     * @param runtimeWiring
     * @return
     */
    public static GraphQL createGraphql(String schemaFilePath, RuntimeWiring runtimeWiring) {
        File schemaFile = loadSchema(schemaFilePath);
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(
                new SchemaParser().parse(schemaFile),
                runtimeWiring
        );
        return GraphQL.newGraphQL(schema).build();
    }

    private static File loadSchema(String filePath) {
        return new File(Resources.getResource(filePath).getFile());
    }

    /**
     * 将查询结果格式化成json字符串
     * @param result
     * @return
     */
    public static String resultToJson(ExecutionResult result) {
        return gson.toJson(result);
    }

    public static void executeAndPrint(GraphQL graphql, String query) {
        System.out.println(query);
        System.out.println("================================");
        System.out.println(GraphqlUtils.resultToJson(graphql.execute(query)));
        System.out.println();
    }
}
