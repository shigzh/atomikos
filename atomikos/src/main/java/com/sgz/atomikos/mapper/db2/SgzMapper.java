package com.sgz.atomikos.mapper.db2;


import com.sgz.atomikos.entity.SgzEntity;

import java.util.List;

public interface SgzMapper {

    SgzEntity findById(Integer pid);

    List<SgzEntity> findAll();

    boolean addProduct(SgzEntity sgzEntity);
}