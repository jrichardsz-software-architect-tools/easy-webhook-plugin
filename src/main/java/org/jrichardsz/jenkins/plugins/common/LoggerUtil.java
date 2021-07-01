package org.jrichardsz.jenkins.plugins.common;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtil {

  public static void setLevel(Level targetLevel) {
    Logger root = Logger.getLogger("org.jrichardsz");
    root.setLevel(targetLevel);
    for (Handler handler : root.getHandlers()) {
      handler.setLevel(targetLevel);
    }
  }
}
