package com.adben.testdatabuilder.entity.datamodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 *
 */
@StaticMetamodel(Film.class)
public class Film_ {

  public static volatile SingularAttribute<Film, Short> filmId;
  public static volatile SingularAttribute<Film, String> title;
  public static volatile SingularAttribute<Film, String> description;
  public static volatile SingularAttribute<Film, Short> releaseYear;
  public static volatile SingularAttribute<Film, Language> language;
  public static volatile SingularAttribute<Film, Language> originalLanguage;
  public static volatile SingularAttribute<Film, Byte> rentalDuration;
  public static volatile SingularAttribute<Film, BigDecimal> rentalRate;
  public static volatile SingularAttribute<Film, Short> length;
  public static volatile SingularAttribute<Film, BigDecimal> replacementCost;
  public static volatile SingularAttribute<Film, Timestamp> lastUpdate;
}
