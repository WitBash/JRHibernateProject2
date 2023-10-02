package com.bashko.dao;

import com.bashko.entity.Staff;
import org.hibernate.SessionFactory;

public class StaffDAO extends BaseDAO<Staff>{
    public StaffDAO(SessionFactory sessionFactory) {
        super(Staff.class, sessionFactory);
    }
}
