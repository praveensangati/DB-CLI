package com.db.assignment.service;

import com.db.assignment.domain.Nomenclature;
import com.db.assignment.domain.NomenclatureBean;
import com.db.assignment.exception.DataNotFoundException;
import com.db.assignment.mapper.NomenClatureMapper;
import com.db.assignment.repository.NomenclatureRepository;
import com.db.assignment.utils.processor.ExcelProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NACEServiceImpl implements NACEService {

    @Autowired
    NomenclatureRepository repository;

    @Autowired
    NomenClatureMapper mapper;

    public void save(InputStream inputStream) {
        List<Nomenclature> nomenclatures = ExcelProcessor.excelToList(inputStream);
        repository.saveAll(nomenclatures);
        log.info("stored nace data");
    }

    @Override
    public NomenclatureBean getByOrderId(long orderId) {
        Nomenclature nomenclature = Optional.ofNullable(repository.findByOrderId(orderId)).orElseThrow(() -> new DataNotFoundException(orderId));
        return mapper.mapToDto(nomenclature);
    }
}
