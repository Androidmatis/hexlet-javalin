package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.model.Course;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.javalin.rendering.template.TemplateUtil.model;

public class HelloWorld {
    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("java", "java dev"));
        courses.add(new Course("php", "php dev"));
        courses.add(new Course("python", "python dev"));
        courses.add(new Course("ST", "ST dev"));
        for (int i = 0; i < courses.size(); i++) {
            courses.get(i).setId((long) (i + 1));
        }
        //app.get("/courses/{id}", ctx -> {
        //    var id = ctx.pathParamAsClass("id", Long.class);
        //    var course = courses.stream().filter(c -> Objects.equals(c.getId(), id.get())).findFirst().get();
        //            // Предполагаем, что у курса есть метод getName()
        //            ctx.result("<h1>" + course.getName() + "</h1>");
        //});


        app.get("/courses/{id}", ctx -> {
            var id = ctx.pathParamAsClass("id", Long.class);
            var course = courses.stream().filter(c -> Objects.equals(c.getId(), id.get())).findFirst().get();
            var page = new CoursePage(course);
            ctx.render("courses/show.jte", model("page", page));
        });


        app.get("/courses", ctx -> {
            var header = "Курсы по программированию";
            var page = new CoursesPage(courses, header);
            ctx.render("courses/index.jte", model("page", page));
        });

        app.start(7070);
    }
}
