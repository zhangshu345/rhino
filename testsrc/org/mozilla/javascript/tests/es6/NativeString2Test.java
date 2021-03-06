/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

/*
 * Tests for the Object.getOwnPropertyDescriptor(obj, prop) method
 */
package org.mozilla.javascript.tests.es6;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

/**
 * Test for handling const variables.
 */
public class NativeString2Test {

    private Context cx;
    private ScriptableObject scope;

    @Before
    public void setUp() {
        cx = Context.enter();
        cx.setLanguageVersion(Context.VERSION_ES6);
        scope = cx.initStandardObjects();
    }

    @After
    public void tearDown() {
        Context.exit();
    }

    @Test
    public void testGetOwnPropertyDescriptorWithIndex() {
        Object result = cx.evaluateString(
                scope, "  var res = 'hello'.hasOwnProperty('0');"
                        + "  res += ';';"
                        + "  desc = Object.getOwnPropertyDescriptor('hello', '0');"
                        + "  res += desc.value;"
                        + "  res += ';';"
                        + "  res += desc.writable;"
                        + "  res += ';';"
                        + "  res += desc.enumerable;"
                        + "  res += ';';"
                        + "  res += desc.configurable;"
                        + "  res += ';';"
                        + "  res;",
                "test", 1, null
        );
        assertEquals("true;h;false;true;false;", result);
    }
  @Test
  public void testNormalizeNoParam() {
      Object result = cx.evaluateString(
              scope, "'123'.normalize()",
              "test", 1, null
      );
      assertEquals("123", result);
  }

  @Test
  public void testNormalizeNoUndefined() {
      Object result = cx.evaluateString(
              scope, "'123'.normalize(undefined)",
              "test", 1, null
      );
      assertEquals("123", result);
  }

  @Test
  public void testNormalizeNoNull() {
      Object result = cx.evaluateString(
              scope, "try { "
                      + "  '123'.normalize(null);"
                      + "} catch (e) { e.message }",
              "test", 1, null
      );
      assertEquals("The normalization form should be one of 'NFC', 'NFD', 'NFKC', 'NFKD'.", result);
  }

  @Test
  public void testReplaceReplacementAsString() {
      Object result = cx.evaluateString(scope, "'123'.replace('2', /x/);", "test", 1, null);

      assertEquals("1/x/3", result);
  }
}
