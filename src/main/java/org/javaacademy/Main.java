package org.javaacademy;

import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.javaacademy.entity.Comment;
import org.javaacademy.entity.User;
import org.javaacademy.entity.Video;
import java.util.List;
import java.util.Properties;
import static org.hibernate.cfg.Environment.*;

public class Main {
    public static void main(String[] args) {
        @Cleanup SessionFactory sessionFactory = new Configuration()
                .addProperties(createProperty())
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Video.class)
                .addAnnotatedClass(Comment.class)
                .buildSessionFactory();

        @Cleanup Session session = sessionFactory.openSession();
        User john = new User("john");
        User rick = new User("rick");

        Video first = new Video("Мое первое интервью", "Первое крутейшее описание видео", john);
        Video second = new Video("Мое второе интервью", "Второе крутейшее описание видео", john);

        Comment comment = new Comment("классное интервью", first, rick);

        writeToDB(session, john, rick, first, second, comment);

        List<Comment> comments = session.createQuery("from User where nickname = 'john'", User.class).getSingleResult()
                .getVideos().stream()
                .filter(video -> video.getName().equals("Мое первое интервью"))
                .findFirst()
                .map(Video::getComments)
                .orElseThrow();

        System.out.println(comments);
    }

    public static Properties createProperty() {
        Properties properties = new Properties();
        properties.put(JAKARTA_JDBC_URL, "jdbc:postgresql://192.168.0.100:5432/youtube");
        properties.put(JAKARTA_JDBC_USER, "student");
        properties.put(JAKARTA_JDBC_PASSWORD, "student");
        properties.put(JAKARTA_JDBC_DRIVER, "org.postgresql.Driver");
        properties.put(HBM2DDL_AUTO, "create");
        properties.put(SHOW_SQL, true);
        properties.put(FORMAT_SQL, true);
        return properties;
    }

    public static void writeToDB(Session session, User john, User rick, Video first, Video second, Comment comment) {
        session.beginTransaction();
        session.persist(john);
        session.persist(rick);

        session.persist(first);
        session.persist(second);

        session.persist(comment);
        session.getTransaction().commit();
        session.clear();
    }
}