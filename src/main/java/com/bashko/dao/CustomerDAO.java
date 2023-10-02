package com.bashko.dao;

import com.bashko.entity.Customer;
import org.hibernate.SessionFactory;


public class CustomerDAO extends BaseDAO<Customer> {
    public CustomerDAO(SessionFactory sessionFactory) {
        super(Customer.class, sessionFactory);
    }

    public Customer getById(short id) {
        return getCurrentSession().get(Customer.class, id);
    }

    public void deleteById(short entityId) {
        final Customer entity = getById(entityId);
        getCurrentSession().delete(entity);
    }
}
