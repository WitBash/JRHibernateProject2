package com.bashko.dao;

import com.bashko.entity.City;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

@Log4j2
public class CityDAO extends BaseDAO<City> {
    public CityDAO(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }

    public City getByName(String name) {
        Query<City> query = getCurrentSession().createQuery("from City c where c.city = :name", City.class);
        query.setParameter("name", name);
        query.setMaxResults(1);
        City result = query.getSingleResult();
        log.info("Get by name: {}", result);
        return result;
    }
}
