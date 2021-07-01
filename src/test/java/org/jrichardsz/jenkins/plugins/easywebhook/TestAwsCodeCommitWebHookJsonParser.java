package org.jrichardsz.jenkins.plugins.easywebhook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.jrichardsz.jenkins.plugins.common.ClassPathProperties;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAwsCodeCommitWebHookJsonParser {

  public String jsonInput;

  @Before
  public void setUp() throws IOException {
    jsonInput = IOUtils.toString(
        this.getClass().getResourceAsStream(
            "/org/jrichardsz/jenkins/plugins/easywebhook/awscodemmit/awscodecommit_webhook.json"),
        "UTF-8");
  }

  @Test
  public void t001_getSimpĺeValues() throws Exception {

    ClassPathProperties
        .customInitialization(TestAwsCodeCommitWebHookJsonParser.class.getResourceAsStream(
            "/org/jrichardsz/jenkins/plugins/easywebhook/awscodemmit/simple-jenkins-plugin.properties"));

    ScmWebHookJsonParser webHookJsonParser = new ScmWebHookJsonParser();
    Map<String, String> valuesFromJsonWebhook =
        webHookJsonParser.getCommonValues("awscodecommit", jsonInput);

    assertNotNull(valuesFromJsonWebhook.get("repositoryName"));
    assertNotNull(valuesFromJsonWebhook.get("branchName"));
    assertNotNull(valuesFromJsonWebhook.get("authorId"));
    assertNotNull(valuesFromJsonWebhook.get("commitId"));
    assertNotNull(valuesFromJsonWebhook.get("eventMessage"));

    assertEquals("my-awesome-repository", valuesFromJsonWebhook.get("repositoryName"));
    assertEquals("mybranch", valuesFromJsonWebhook.get("branchName"));
    assertEquals("jane_doe", valuesFromJsonWebhook.get("authorId"));
    assertEquals("fb28ebbec522cc403", valuesFromJsonWebhook.get("commitId"));
    assertEquals("not_available_from_aws", valuesFromJsonWebhook.get("eventMessage"));
  }
}
