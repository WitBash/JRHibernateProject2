package com.bashko.dao;

import com.bashko.entity.Actor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import static java.util.Objects.isNull;


@Log4j2
public class ActorDAO extends BaseDAO<Actor> {


    private final String GET_CATEGORY_BY_FULLNAME_IF_EXIST = "from Actor a where a.firstName = :first_name and a.lastName = : last_name";

    public ActorDAO(SessionFactory sessionFactory) {
        super(Actor.class, sessionFactory);
    }

    public Actor getByFirstNameAndLastNameIfExist(String firstName, String lastName) {
        Query<Actor> actorQuery = getCurrentSession().createQuery(GET_CATEGORY_BY_FULLNAME_IF_EXIST, Actor.class);
        actorQuery.setParameter("first_name", firstName);
        actorQuery.setParameter("last_name", lastName);
        Actor actor = actorQuery.getSingleResult();
        if (isNull(actor)) {
            Actor newActorToDB = Actor.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .build();
            log.info("Actor not exist then created: {}", newActorToDB);
            return save(newActorToDB);
        }
        log.info("Get by ID: {}", actor);
        return actor;
    }
}
