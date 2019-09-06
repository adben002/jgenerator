package com.adben.testdatabuilder.core.exception;

import static org.slf4j.LoggerFactory.getLogger;

import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;

/**
 * Exception for edge cases in the {@link com.adben.testdatabuilder.core.DataBuilder DataBuilder}
 * library.
 */
public class DataBuilderException extends RuntimeException {

  private static final long serialVersionUID = -8926871460970235007L;

  private static final Logger LOGGER = getLogger(MethodHandles.lookup().lookupClass());

  @SuppressWarnings("unused")
  private DataBuilderException() {
    super();
    LOGGER.debug("DataBuilderException default constructor.");
  }

  public DataBuilderException(final String message) {
    super(message);
    LOGGER.debug("DataBuilderException message constructor: {}.", message);
  }

  public DataBuilderException(final Throwable cause) {
    super(cause);
    LOGGER.debug("DataBuilderException cause constructor: {}.", cause.getMessage());
  }

}
