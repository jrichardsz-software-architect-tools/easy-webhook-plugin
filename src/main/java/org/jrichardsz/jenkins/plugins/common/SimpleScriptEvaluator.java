package org.jrichardsz.jenkins.plugins.common;

import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jayway.jsonpath.Configuration;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class SimpleScriptEvaluator {

  private static final Logger LOGGER = Logger.getLogger(SimpleScriptEvaluator.class.getName());

  private JsonPathHelperMethod jsonPathHelperMethod = new JsonPathHelperMethod(this);
  private LastArrayItemHelperMethod lastArrayItemHelperMethod = new LastArrayItemHelperMethod(this);

  public void init(Configuration customConf, Object payloadDocument) {
    jsonPathHelperMethod.setCustomConf(customConf);
    jsonPathHelperMethod.setPayloadDocument(payloadDocument);
  }

  public Object execute(String script, Map<String, String> parameters, ClassLoader classLoader) {

    try {
      script = StringUtil.scapeDollar(script);
    } catch (Exception ex) {
      LOGGER.log(Level.SEVERE, String.format("Failed to execute script \n %s", script), ex);
      return null;
    }

    Object value = null;

    // Closure<?> jp = new Closure<Object>(this) {
    // private static final long serialVersionUID = 1L;
    //
    // @Override
    // public Object call(Object... args) {
    // Object document = null;
    // if (args.length > 1) {
    // document = customConf.jsonProvider().parse((String) args[1]);
    // } else {
    // document = payloadDocument;
    // }
    // return JsonPath.read(document, (String) args[0]);
    // }
    // };

    try {

      Binding binding = new Binding();
      binding.setVariable("jp", jsonPathHelperMethod);
      binding.setVariable("lt", lastArrayItemHelperMethod);

      for (Entry<String, String> entry : parameters.entrySet()) {
        binding.setVariable(entry.getKey(), entry.getValue());
      }

      GroovyShell shell = null;

      if (classLoader != null) {
        shell = new GroovyShell(classLoader, binding);
      } else {
        shell = new GroovyShell(binding);
      }

      value = shell.evaluate(script);

    } catch (Exception ex) {
      LOGGER.log(Level.SEVERE, String.format("Failed to execute script \n %s", script), ex);
    }

    return value;
  }

  // Closure<?> jp = new Closure<Object>(this) {
  // private static final long serialVersionUID = 1L;
  // private Configuration customConf;
  // private Object payloadDocument;
  //
  // public void setCustomConf(Configuration customConf) {
  // this.customConf = customConf;
  // }
  // public void setPayloadDocument(Object payloadDocument) {
  // this.payloadDocument = payloadDocument;
  // }
  //
  // @Override
  // public Object call(Object... args) {
  // Object document = null;
  // if (args.length > 1) {
  // document = customConf.jsonProvider().parse((String) args[1]);
  // } else {
  // document = payloadDocument;
  // }
  // return JsonPath.read(document, (String) args[0]);
  // }
  // };
}
