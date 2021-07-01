package org.jrichardsz.jenkins.plugins.common;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import groovy.lang.Closure;

public class JsonPathHelperMethod extends Closure<Object> {

  private static final long serialVersionUID = 1L;
  private Configuration customConf;
  private Object payloadDocument;

  public JsonPathHelperMethod(Object owner) {
    super(owner);
  }

  @Override
  public Object call(Object... args) {
    Object document = null;
    if (args.length > 1) {
      document = customConf.jsonProvider().parse((String) args[1]);
    } else {
      document = payloadDocument;
    }
    return JsonPath.read(document, (String) args[0]);
  }

  public void setCustomConf(Configuration customConf) {
    this.customConf = customConf;
  }

  public void setPayloadDocument(Object payloadDocument) {
    this.payloadDocument = payloadDocument;
  }

}
