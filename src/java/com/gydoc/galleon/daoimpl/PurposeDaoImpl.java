package com.gydoc.galleon.daoimpl;

import com.gydoc.galleon.dao.PurposeDao;
import com.gydoc.galleon.domain.Purpose;

/**
 *
 */
public class PurposeDaoImpl extends DaoBase implements PurposeDao {

    public Purpose addPurpose(Purpose purpose) {
//        curSession().save(purpose);
        return purpose;
    }

}
