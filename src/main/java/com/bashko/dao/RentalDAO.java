package com.bashko.dao;

import com.bashko.entity.Rental;
import org.hibernate.SessionFactory;

public class RentalDAO extends BaseDAO<Rental> {
    public RentalDAO(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }

    public Rental getById(int id) {
        return getCurrentSession().get(Rental.class, id);
    }

    public void deleteById(short entityId) {
        final Rental entity = getById(entityId);
        getCurrentSession().delete(entity);
    }
}
