package com.bashko.dao;

import com.bashko.entity.Film;
import com.bashko.entity.Store;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import java.util.List;


@Log4j2
public class FilmDAO extends BaseDAO<Film> {

    private final String GET_ALL_FILMS_AVAILABLE_FOR_RENT = "SELECT f.* FROM film AS f" +
                                                            " INNER JOIN" +
                                                            " (SELECT DISTINCT i.film_id FROM inventory i" +
                                                            " WHERE i.store_id = :idStore" +
                                                            " AND i.inventory_id NOT IN" +
                                                            " (SELECT r.inventory_id FROM rental r WHERE r.return_date IS NULL)) AS ir" +
                                                            " ON f.film_id = ir.film_id";

    public FilmDAO(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }

    public List<Film> getAvailableFilms(Store store) {
        NativeQuery<Film> filmNativeQuery = getCurrentSession().createNativeQuery(GET_ALL_FILMS_AVAILABLE_FOR_RENT, Film.class);
        filmNativeQuery.setParameter("idStore", store.getId());
        List<Film> resultList = filmNativeQuery.getResultList();
        log.info("Get list available films in count: {}", resultList.size());
        return resultList;
    }
}
