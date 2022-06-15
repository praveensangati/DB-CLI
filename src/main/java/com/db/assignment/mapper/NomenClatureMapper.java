package com.db.assignment.mapper;

import com.db.assignment.domain.Nomenclature;
import com.db.assignment.domain.NomenclatureBean;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NomenClatureMapper {

    @Autowired
    private ModelMapper modelMapper;

    public NomenclatureBean mapToDto(Nomenclature nomenclature) {
        NomenclatureBean nomenclatureBean = modelMapper.map(nomenclature, NomenclatureBean.class);
        return nomenclatureBean;
    }
}
