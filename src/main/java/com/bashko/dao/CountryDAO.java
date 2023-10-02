package com.bashko.dao;

import com.bashko.entity.Country;
import org.hibernate.SessionFactory;

public class CountryDAO extends BaseDAO<Country>{

    public CountryDAO(SessionFactory sessionFactory) {
        super(Country.class, sessionFactory);
    }
}
