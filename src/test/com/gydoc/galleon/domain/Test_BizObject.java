package com.gydoc.galleon.domain;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Calendar;

/**
 *
 */
@Test
public class Test_BizObject {

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testCreationDate() {
        BizObject obj = new BizObject() {

        };
        Assert.assertNull(obj.getCreationDate());
        obj.setCreationDate(Calendar.getInstance());
        Assert.assertNotNull(obj.getCreationDate());
        obj.setCreationDate(Calendar.getInstance());
    }

}
