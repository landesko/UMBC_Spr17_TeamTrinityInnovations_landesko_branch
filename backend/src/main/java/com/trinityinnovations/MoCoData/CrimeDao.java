package com.trinityinnovations.MoCoData;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import com.javaetmoi.core.persistence.hibernate.JpaLazyLoadingUtil;

/**
 * Created by Dan Lesko on 4/9/2017.
 */
@Repository
public class CrimeDao {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Crime> getCrimesInInterval(String start_date, String end_date) {

        List<Crime> crimes;
        crimes = entityManager.createNativeQuery(
                "SELECT * " + "FROM crime WHERE date BETWEEN \'" + start_date + "\' AND \'" + end_date + "\'", Crime.class).getResultList();
        return crimes;
    }
}
