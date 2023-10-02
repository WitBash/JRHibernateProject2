package com.bashko.dao;

import com.bashko.entity.Payment;
import org.hibernate.SessionFactory;

public class PaymentDAO extends BaseDAO<Payment>{
    public PaymentDAO(SessionFactory sessionFactory) {
        super(Payment.class, sessionFactory);
    }
}
