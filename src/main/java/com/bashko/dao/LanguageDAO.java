package com.bashko.dao;

import com.bashko.entity.Language;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import static java.util.Objects.isNull;


@Log4j2
public class LanguageDAO extends BaseDAO<Language> {
    private final String GET_LANGUAGE_BY_NAME_IF_EXIST = "from Language l where l.name = :name";

    public LanguageDAO(SessionFactory sessionFactory) {
        super(Language.class, sessionFactory);
    }

    public Language getByNameIfExist(String name) {
        Query<Language> languageQuery = getCurrentSession().createQuery(GET_LANGUAGE_BY_NAME_IF_EXIST, Language.class);
        languageQuery.setParameter("name", name);
        Language language = languageQuery.getSingleResult();
        if (isNull(language)) {

            Language newLanguageToDB = Language.builder()
                    .name(name)
                    .build();
            log.info("Language not exist then created: {}", newLanguageToDB);
            return save(newLanguageToDB);
        }
        log.info("Get by name if exist: {}", language);
        return language;
    }
}
