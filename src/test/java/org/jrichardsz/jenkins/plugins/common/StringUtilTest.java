package org.jrichardsz.jenkins.plugins.common;

import static org.junit.Assert.assertEquals;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StringUtilTest {

  @Test
  public void simpleJsonPath() throws Exception {
    String input = "$.field";
    String output = StringUtil.scapeDollar(input);
    assertEquals("\\$.field", output);
  }


}
