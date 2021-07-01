package org.jrichardsz.jenkins.plugins.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.junit.MockitoJUnitRunner;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimpleScriptEvaluatorTest {

  @Test
  public void simpleJsonPath() throws Exception {

    String jsonInput = IOUtils.toString(this.getClass().getResourceAsStream(
        "/org/jrichardsz/jenkins/plugins/common/SimpleScriptEvaluatorSources/simpleJsonPath.json"),
        "UTF-8");

    Configuration conf = Configuration.defaultConfiguration();
    Configuration customConf = conf.addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
    Object document = customConf.jsonProvider().parse(jsonInput);

    // $.Records[0].eventId
    String script = "jp(\"$.Records[0].eventId\")";
    Map<String, String> parameters = new HashMap<String, String>();
    ClassLoader classLoader = this.getClass().getClassLoader();

    SimpleScriptEvaluator scriptEvaluator = new SimpleScriptEvaluator();
    scriptEvaluator.init(customConf, document);
    Object value = scriptEvaluator.execute(script, parameters, classLoader);
    assertNotNull(value);
    assertEquals("d1dab883", value);

  }

  @Test
  public void anidateJsonPath() throws Exception {

    String jsonInput = IOUtils.toString(this.getClass().getResourceAsStream(
        "/org/jrichardsz/jenkins/plugins/common/SimpleScriptEvaluatorSources/anidateJsonPath.json"),
        "UTF-8");

    Configuration conf = Configuration.defaultConfiguration();
    Configuration customConf = conf.addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
    Object document = customConf.jsonProvider().parse(jsonInput);

    // $.Records[0].eventId
    String script = "jp(\"$.Records[0].eventId\",jp(\"$.Message\"))";
    Map<String, String> parameters = new HashMap<String, String>();
    ClassLoader classLoader = this.getClass().getClassLoader();

    SimpleScriptEvaluator scriptEvaluator = new SimpleScriptEvaluator();
    scriptEvaluator.init(customConf, document);
    Object value = scriptEvaluator.execute(script, parameters, classLoader);
    assertNotNull(value);
    assertEquals("d1dab883", value);

  }


}
