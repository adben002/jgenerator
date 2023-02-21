package com.adben.testdatabuilder.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class EntityTestHelper {

  @PersistenceContext
  private EntityManager em;

  public <T> long count(final Class<T> clz) {
    final CriteriaBuilder builder = this.em.getCriteriaBuilder();
    final CriteriaQuery<Long> query = builder.createQuery(Long.class);
    final Root<T> root = query.from(clz);

    query
        .select(builder.count(root));

    return this.em.createQuery(query).getSingleResult();
  }

}
