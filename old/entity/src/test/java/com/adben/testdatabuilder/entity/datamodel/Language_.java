package com.adben.testdatabuilder.entity.datamodel;

import java.sql.Timestamp;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 *
 */
@StaticMetamodel(Language.class)
public class Language_ {

  public static volatile SingularAttribute<Language, Short> languageId;
  public static volatile SingularAttribute<Language, String> name;
  public static volatile SingularAttribute<Language, Timestamp> lastUpdate;
  public static volatile ListAttribute<Language, Film> films;
  public static volatile ListAttribute<Language, Film> filmsOriginal;
}
