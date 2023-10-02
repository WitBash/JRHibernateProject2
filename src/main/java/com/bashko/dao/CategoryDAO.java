package com.bashko.dao;

import com.bashko.entity.Category;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

@Log4j2
public class CategoryDAO extends BaseDAO<Category> {

    private final String GET_CATEGORY_BY_NAME = "from Category c where c.name = :name";

    public CategoryDAO(SessionFactory sessionFactory) {
        super(Category.class, sessionFactory);
    }

    public Category getByName(String name) {
        Query<Category> categoryQuery = getCurrentSession().createQuery(GET_CATEGORY_BY_NAME, Category.class);
        categoryQuery.setParameter("name", name);
        Category category = categoryQuery.getSingleResult();

        log.info("Get by name: {}", category);
        return categoryQuery.getSingleResult();
    }
}
