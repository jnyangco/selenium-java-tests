package com.automation.tests.leetcode;

import com.automation.base.BaseTest;
import com.automation.pages.GooglePage;
import com.automation.pages.LeetcodePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomepageTest extends BaseTest {

    @Test(description = "Leetcode: Verify Login Test")
    public void testLogin() {
        LeetcodePage leetcodePage = new LeetcodePage(getDriver());
        leetcodePage.openLeetcodeSite();
        leetcodePage.hardWait(2);
    }

}
