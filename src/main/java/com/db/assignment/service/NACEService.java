package com.db.assignment.service;

import com.db.assignment.domain.NomenclatureBean;

import java.io.InputStream;

public interface NACEService {

    void save(InputStream multipartFile);
    NomenclatureBean getByOrderId(long orderId);

}
