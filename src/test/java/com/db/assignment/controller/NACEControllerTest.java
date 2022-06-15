package com.db.assignment.controller;

import com.db.assignment.domain.NomenclatureBean;
import com.db.assignment.exception.InvalidFileFormatException;
import com.db.assignment.service.NACEService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NACEControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NACEService naceService;

    @SneakyThrows
    @Test
    public void uploadNACEReport() {
        MockMultipartFile file = new MockMultipartFile("file", "hello.xlsx", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        Mockito.doNothing().when(naceService).save(file.getInputStream());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/nace/upload")
                .file(file))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void uploadNACEReport_throwsInvalidFileFormatException() {
        MockMultipartFile file = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        Mockito.doNothing().when(naceService).save(file.getInputStream());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/nace/upload")
                .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof InvalidFileFormatException))
                .andExpect(result -> Assert.assertEquals("unsupported file format - hello.txt", result.getResolvedException().getMessage()));
    }

    @SneakyThrows
    @Test
    public void fetchByOrderId() {
        Mockito.when(naceService.getByOrderId(123)).thenReturn(NomenclatureBean.builder().orderId(123).build());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/nace/123"))
                .andExpect(status().isOk()).andReturn();

        NomenclatureBean nomenclatureBean = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), NomenclatureBean.class);
        Assert.assertEquals(123, nomenclatureBean.getOrderId());

    }
}