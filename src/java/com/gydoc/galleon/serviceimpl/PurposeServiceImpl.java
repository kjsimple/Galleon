package com.gydoc.galleon.serviceimpl;

import com.gydoc.galleon.dao.PurposeDao;
import com.gydoc.galleon.domain.Purpose;
import com.gydoc.galleon.service.PurposeService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Transactional(propagation= Propagation.SUPPORTS)
public class PurposeServiceImpl implements PurposeService {

    private PurposeDao dao;

    @Transactional(propagation=Propagation.REQUIRED)
    public Purpose addPurpose(Purpose purpose) {
        return dao.addPurpose(purpose);
    }

    public void setPurposeDao(PurposeDao dao) {
        this.dao = dao;
    }
    
}
