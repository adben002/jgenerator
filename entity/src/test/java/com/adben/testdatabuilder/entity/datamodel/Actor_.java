package com.adben.testdatabuilder.entity.datamodel;

import java.sql.Timestamp;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 *
 */
@StaticMetamodel(Actor.class)
public class Actor_ {

  public static volatile SingularAttribute<Actor, Short> actorId;
  public static volatile SingularAttribute<Actor, String> firstName;
  public static volatile SingularAttribute<Actor, String> lastName;
  public static volatile SingularAttribute<Actor, Timestamp> lastUpdate;
}
