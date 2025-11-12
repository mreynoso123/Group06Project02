package com.mareyn.group06project02;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests the validation logic for empty username and email inputs.
 *
 * <p>This unit test confirms that
 * {@link ForgotPasswordActivity#isUsernameAndEmailEmpty(String, String)}
 * correctly returns true when both username and email fields are empty,
 * and false when at least one is provided. Its just to verify the logic
 * in ForgotPasswordActivity.verifyUser().</p>
 */

public class ForgotPasswordActivityUnitTest {
  @Test
  public void userNameAndEmailEmptyReturnTrue() {
    boolean result = ForgotPasswordActivity.isUsernameAndEmailEmpty("", "");
    assertTrue(result);
  }

  @Test
  public void userNameOrEmailFilledReturnFalse() {
    boolean result1 = ForgotPasswordActivity.isUsernameAndEmailEmpty("admin1", "");
    boolean result2 = ForgotPasswordActivity.isUsernameAndEmailEmpty("", "test@example.com");
    assertFalse(result1);
    assertFalse(result2);
  }
}
