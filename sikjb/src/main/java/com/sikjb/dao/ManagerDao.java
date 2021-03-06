package com.sikjb.dao;

import com.sikjb.model.Manager;
import com.sikjb.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Service
public class ManagerDao implements ManagerService {

    private EntityManagerFactory emf;

    @Autowired
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MANAGER') OR hasRole('ROLE_CASHIER')")
    public List<Manager> listManager() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("from Manager", Manager.class).getResultList();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Manager saveOrUpdate(Manager manager) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Manager saved = em.merge(manager);
        em.getTransaction().commit();
        return saved;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER') OR hasRole('ROLE_ADMIN') OR hasRole('ROLE_CASHIER')")
    public Manager getManagerById(Long managerId) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("from Manager where id="+managerId, Manager.class).getSingleResult();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteManagerById(Long managerId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(Manager.class, managerId));
        em.getTransaction().commit();
    }

}
