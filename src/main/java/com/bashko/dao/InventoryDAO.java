package com.bashko.dao;

import com.bashko.entity.Film;
import com.bashko.entity.Inventory;
import com.bashko.entity.Store;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

@Log4j2
public class InventoryDAO extends BaseDAO<Inventory> {

    private final String SELECT_INVENTORY_BY_FILM_AND_STORE = "from Inventory i where i.film = :film and i.store = :store";
    public InventoryDAO(SessionFactory sessionFactory) {
        super(Inventory.class, sessionFactory);
    }

    public Inventory getInventoryByFilmAndByStore(Film film, Store store) {
        Query<Inventory> inventoryQuery = getCurrentSession().createQuery(SELECT_INVENTORY_BY_FILM_AND_STORE, Inventory.class);
        inventoryQuery.setParameter("film",film);
        inventoryQuery.setParameter("store", store);
        inventoryQuery.setMaxResults(1);
        Inventory result = inventoryQuery.getSingleResult();
        log.info("Get inventory by film and store: {}", result);
        return result;
    }
}
