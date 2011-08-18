package com.gydoc.galleon;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 *
 */
@Test
public class TestUtil {

    @BeforeSuite
    public void init() {
        SpringUtil.init("spring.xml;galleon-data-spec.xml;galleon-data.xml");
        SpringUtil.getBean("messageSource");
    }

}
