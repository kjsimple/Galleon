package com.gydoc.galleon.service;

import com.gydoc.galleon.SpringUtil;
import com.gydoc.galleon.domain.Purpose;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Test
public class Test_PurposeService {
    
    @Test
    public void testAddPurpose() {
        Purpose purpose = new Purpose();
        purpose.setName("test Purpose");
        purpose.setDescription("Description 001.");
        purpose.setActive(true);
        PurposeService service = (PurposeService) SpringUtil.getBean(PurposeService.S_ID);
        service.addPurpose(purpose);
    }

}
