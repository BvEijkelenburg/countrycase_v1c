package nl.hu.bep.countrycase.listeners;

import nl.hu.bep.persistence.PersistenceManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        PersistenceManager.loadWorldFromAzure();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        PersistenceManager.saveWorldToAzure();
    }
}
