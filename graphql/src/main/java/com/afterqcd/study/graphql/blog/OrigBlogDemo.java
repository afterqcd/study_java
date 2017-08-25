package com.afterqcd.study.graphql.blog;

import com.afterqcd.study.graphql.blog.domain.Author;
import com.afterqcd.study.graphql.blog.domain.Comment;
import com.afterqcd.study.graphql.blog.domain.Post;
import static com.afterqcd.study.graphql.utils.GraphqlUtils.*;
import graphql.GraphQL;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.TypeRuntimeWiring;

public class OrigBlogDemo {
    public static void main(String[] args) {
        GraphQL graphql = createGraphql("blog.graphqls", buildRuntimeWiring());
        executeAndPrint(graphql, "{ posts { title author { name } content }}");
        executeAndPrint(graphql, "{ post(id: \"p2\") { title content author { name } comments { content author { name } }}}");
        executeAndPrint(graphql, "{ authors { id name }}");
        executeAndPrint(graphql, "{ author(id: \"a1\") { name posts { title } comments { content post { title }}}}");
    }

    private static Data data = new Data();

    private static RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", OrigBlogDemo::queryFieldsWiring)
                .type("Post", OrigBlogDemo::postFieldsWiring)
                .type("Author", OrigBlogDemo::authorFieldsWiring)
                .type("Comment", OrigBlogDemo::commentFieldsWiring)
                .build();
    }

    private static TypeRuntimeWiring.Builder queryFieldsWiring(TypeRuntimeWiring.Builder builder) {
        return builder
                .dataFetcher("posts", env -> data.getPosts())
                .dataFetcher("post", env -> data.getPost(env.<String>getArgument("id")))
                .dataFetcher("authors", env -> data.getAuthors())
                .dataFetcher("author", env -> data.getAuthor(env.<String>getArgument("id")));
    }

    private static TypeRuntimeWiring.Builder postFieldsWiring(TypeRuntimeWiring.Builder builder) {
        return builder
                .dataFetcher("author", env -> data.getAuthor(env.<Post>getSource().getAuthorId()))
                .dataFetcher("comments", env -> data.getComments(env.<Post>getSource().getId()));
    }

    private static TypeRuntimeWiring.Builder authorFieldsWiring(TypeRuntimeWiring.Builder builder) {
        return builder
                .dataFetcher("posts", env -> data.getPostsByAuthorId(env.<Author>getSource().getId()))
                .dataFetcher("comments", env -> data.getCommentsByAuthorId(env.<Author>getSource().getId()));
    }

    private static TypeRuntimeWiring.Builder commentFieldsWiring(TypeRuntimeWiring.Builder builder) {
        return builder
                .dataFetcher("author", env -> data.getAuthor(env.<Comment>getSource().getAuthorId()))
                .dataFetcher("post", env -> data.getPost(env.<Comment>getSource().getPostId()));
    }
}
