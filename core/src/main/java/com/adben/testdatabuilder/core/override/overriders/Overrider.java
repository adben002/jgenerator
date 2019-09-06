package com.adben.testdatabuilder.core.override.overriders;

/**
 * For setting/overriding field values on a given object.
 *
 * This can be either through field reflection or through a class of this type acting as a callback
 * and setting fields by hand.
 */
public interface Overrider<T> {

  /**
   * Set fields on the supplied object.
   *
   * @param t Object of type T which to set field values on.
   */
  void setFields(T t);

}
