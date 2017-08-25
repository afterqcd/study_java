package com.afterqcd.study.graphql.blog;

import static com.afterqcd.study.graphql.utils.GraphqlUtils.executeAndPrint;

import com.afterqcd.study.graphql.blog.domain.Author;
import com.afterqcd.study.graphql.blog.domain.Comment;
import com.afterqcd.study.graphql.blog.domain.Post;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.coxautodev.graphql.tools.GraphQLResolver;
import com.coxautodev.graphql.tools.SchemaParser;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;

import java.util.List;

public class JavaToolsBlogDemo {
    public static void main(String[] args) {
        GraphQLSchema graphQLSchema = SchemaParser.newParser()
                .file("blog.graphqls")
                .resolvers(
                        new AuthorResolver(), new CommentResolver(),
                        new PostResolver(), new Query()
                )
                .build()
                .makeExecutableSchema();
        GraphQL graphql = GraphQL.newGraphQL(graphQLSchema).build();

        executeAndPrint(graphql, "{ posts { title author { name } content }}");
        executeAndPrint(graphql, "{ post(id: \"p2\") { title content author { name } comments { content author { name } }}}");
        executeAndPrint(graphql, "{ authors { id name }}");
        executeAndPrint(graphql, "{ author(id: \"a1\") { name posts { title } comments { content post { title }}}}");
    }

    private static Data data = new Data();

    static class AuthorResolver implements GraphQLResolver<Author> {
        public List<Post> posts(Author author) {
            return data.getPostsByAuthorId(author.getId());
        }

        public List<Comment> comments(Author author) {
            return data.getCommentsByAuthorId(author.getId());
        }
    }

    static class CommentResolver implements GraphQLResolver<Comment> {
        public Author author(Comment comment) {
            return data.getAuthor(comment.getAuthorId());
        }

        public Post post(Comment comment) {
            return data.getPost(comment.getPostId());
        }
    }

    static class PostResolver implements GraphQLResolver<Post> {
        public Author author(Post post) {
            return data.getAuthor(post.getAuthorId());
        }

        public List<Comment> comments(Post post) {
            return data.getComments(post.getId());
        }
    }

    static class Query implements GraphQLQueryResolver {
        public List<Post> posts() {
            return data.getPosts();
        }

        public Post post(String id) {
            return data.getPost(id);
        }

        public List<Author> authors() {
            return data.getAuthors();
        }

        public Author author(String id) {
            return data.getAuthor(id);
        }
    }
}
