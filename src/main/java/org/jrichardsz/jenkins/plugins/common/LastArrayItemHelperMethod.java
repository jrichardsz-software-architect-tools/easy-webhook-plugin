package org.jrichardsz.jenkins.plugins.common;

import java.util.logging.Level;
import java.util.logging.Logger;
import groovy.lang.Closure;

public class LastArrayItemHelperMethod extends Closure<Object> {

  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(LastArrayItemHelperMethod.class.getName());

  public LastArrayItemHelperMethod(Object owner) {
    super(owner);
  }

  @Override
  public Object call(Object... args) {
    if (args.length < 2) {
      LOGGER.log(Level.SEVERE, "lastItem required 2 arguments");
    }
    String input = (String) args[0];
    String splitChar = (String) args[1];
    String[] array = input.split(splitChar);
    if (array.length == 0) {
      return input;
    }
    return array[array.length - 1];
  }
}
