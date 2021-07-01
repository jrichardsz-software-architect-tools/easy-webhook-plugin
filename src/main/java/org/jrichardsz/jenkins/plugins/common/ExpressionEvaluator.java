package org.jrichardsz.jenkins.plugins.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;

public class ExpressionEvaluator {

  private SimpleScriptEvaluator simpleScriptEvaluator = new SimpleScriptEvaluator();

  public Map<String, String> execute(String webhookPayload,
      ArrayList<String> variablesToBeEvaluated, String scmId) throws Exception {

    if (variablesToBeEvaluated == null) {
      return null;
    }
    
    Configuration conf = Configuration.defaultConfiguration();
    Configuration customConf = conf.addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
    Object document = customConf.jsonProvider().parse(webhookPayload);

    simpleScriptEvaluator.init(customConf, document);
    
    HashMap<String, String> parsedParameters = new HashMap<String, String>();

    // iterate all variables and one by one
    for (String variableNameToBeEvaluated : variablesToBeEvaluated) {

      String jsonPathPrefix = getJsonPathPrefixToSearchInInternalPropertiesFile(scmId);
      // contains a raw string which should be a groovy expression
      String rawValue =
          getValueFromInternalPropertiesFile(variableNameToBeEvaluated, jsonPathPrefix);

      if (rawValue == null) {
        continue;
      }

      HashMap<String, String> variablesToBeUsedInScript = new HashMap<String, String>();
      // execute groovy and get the return value
      String finalValue = "" + simpleScriptEvaluator.execute(rawValue, variablesToBeUsedInScript,
          this.getClass().getClassLoader());
      parsedParameters.put(variableNameToBeEvaluated, finalValue);
    }

    return parsedParameters;
  }

  public String getValueFromInternalPropertiesFile(String key, String prefix) throws Exception {
    return ClassPathProperties.getProperty(prefix + key);
  }

  public String getJsonPathPrefixToSearchInInternalPropertiesFile(String scmId) {
    return String.format("%s.expression.", scmId);
  }

  public String getGroovyPrefixToSearchInInternalPropertiesFile(String scmId) {
    return String.format("%s.groovy.expression.", scmId);
  }

  public boolean isJsonPathExpression(String input) {
    if (input != null && input.startsWith("$.")) {
      return true;
    } else {
      return false;
    }
  }

  public boolean isGroovyExpression(String input) {
    Pattern pattern = Pattern.compile("groovy\\(\\$\\..+\\)");
    Matcher matcher = pattern.matcher(input);
    return matcher.find();
  }

  public String getJsonPathFromGroovy(String input) {
    input = input.replace("groovy(", "");
    int last = input.lastIndexOf(")");
    return input.substring(0, last);
  }

}
