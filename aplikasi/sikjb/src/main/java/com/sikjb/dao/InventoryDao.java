package com.sikjb.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sikjb.model.Inventory;
import com.sikjb.services.InventoryService;

@Service
public class InventoryDao implements InventoryService {

	private EntityManagerFactory emf;
		
		@Autowired
		public void setEmf(EntityManagerFactory emf) {
			this.emf = emf;
		}
		
		@Override
		public List<Inventory> listInventory(){
			EntityManager em = emf.createEntityManager();
			return em.createQuery("from Inventory", Inventory.class) .getResultList();
		}
		
		@Override
		public Inventory saveOrUpdate(Inventory inventory) {
			
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Inventory saved = em.merge(inventory);
			em.getTransaction().commit();
			return saved;
		}
		
		@Override
		public void hapusInventory(Integer id) {
			
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.remove(em.find(Inventory.class,id));
			em.getTransaction().commit();
		}
		
		@Override
		public Inventory getId(Integer id) {
			
			EntityManager em = emf.createEntityManager();
			return em.find(Inventory.class,id);
		}
		
		
}