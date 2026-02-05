package com.ems.util;


import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        Dotenv dotenv = Dotenv.load();
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");

        config.setProperty("hibernate.connection.url",dotenv.get("DB_URL"));
        config.setProperty("hibernate.connection.username",dotenv.get("DB_USER"));
        config.setProperty("hibernate.connection.password",dotenv.get("DB_PASSWORD"));

        sessionFactory = config.buildSessionFactory();
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}