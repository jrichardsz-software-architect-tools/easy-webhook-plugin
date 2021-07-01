package org.jrichardsz.jenkins.plugins.common;

public class StringUtil {

  public static String scapeDollar(String unscapedGroovyScript) throws Exception {
    return unscapedGroovyScript.replaceAll("\\$\\.","\\\\\\$.");
  }
}
