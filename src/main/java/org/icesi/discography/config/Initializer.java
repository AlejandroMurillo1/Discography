package org.icesi.discography.config;

import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.WebApplicationInitializer;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class Initializer implements WebApplicationInitializer {

    @Override
    public void onStartup(@NonNull ServletContext servletContext) throws ServletException {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        servletContext.setAttribute("springContext", context);
    }
}