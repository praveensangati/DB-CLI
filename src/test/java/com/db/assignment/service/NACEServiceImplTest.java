package com.db.assignment.service;

import com.db.assignment.domain.Nomenclature;
import com.db.assignment.domain.NomenclatureBean;
import com.db.assignment.domain.ResponseMessage;
import com.db.assignment.exception.DataNotFoundException;
import com.db.assignment.mapper.NomenClatureMapper;
import com.db.assignment.repository.NomenclatureRepository;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.TreeSet;

@RunWith(MockitoJUnitRunner.class)
public class NACEServiceImplTest {

    @InjectMocks
    NACEService naceService = new NACEServiceImpl();

    @Mock
    NomenclatureRepository nomenclatureRepository;

    @Mock
    NomenClatureMapper mapper;

    @SneakyThrows
    @Test
    public void save() {
        Mockito.when(nomenclatureRepository.saveAll(Mockito.any())).thenReturn(Collections.emptyList());
        File initialFile = new File("src/main/resources/NACEListTest.xlsx");
        InputStream targetStream = new FileInputStream(initialFile);

        naceService.save(targetStream);

        Mockito.verify(nomenclatureRepository, Mockito.times(1));
    }

    @Test
    public void getByOrderId() {
        Nomenclature nomenclature = Nomenclature.builder().orderId(123).build();
        NomenclatureBean nomenclatureBean = NomenclatureBean.builder().orderId(123).build();

        Mockito.when(nomenclatureRepository.findByOrderId(123L)).thenReturn(nomenclature);
        Mockito.when(mapper.mapToDto(nomenclature)).thenReturn(nomenclatureBean);

        NomenclatureBean byOrderId = naceService.getByOrderId(123);

        Assert.assertEquals(123, byOrderId.getOrderId());
    }

    @Test(expected = DataNotFoundException.class)
    public void tetGetByOrderId_throwsDataNotFoundException() {
        Mockito.when(nomenclatureRepository.findByOrderId(123L)).thenReturn(null);

        naceService.getByOrderId(123);
    }
}