package utils;

import java.util.List;

import beans.Point;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseManager {
    private final String PU_NAME = "points-pu";
    private EntityManagerFactory emf;

    public DatabaseManager() {
        this.emf = Persistence.createEntityManagerFactory(PU_NAME);
    }

    private EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    @PreDestroy
    public void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }

    public Point savePoint(Point p) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            return p;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public List<Point> getPoints() {
        EntityManager em = createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Point p ORDER BY p.id", Point.class).getResultList();
        } finally {
            em.close();
        }
    }
}