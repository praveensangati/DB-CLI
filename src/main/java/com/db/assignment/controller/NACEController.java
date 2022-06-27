package com.db.assignment.controller;

import com.db.assignment.domain.NomenclatureBean;
import com.db.assignment.domain.ResponseMessage;
import com.db.assignment.exception.AssignmentException;
import com.db.assignment.exception.InvalidFileFormatException;
import com.db.assignment.service.NACEService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("nace")
public class NACEController {

    @Autowired
    private NACEService naceService;

    @PostMapping(value = "/upload",consumes = {"multipart/form-data"})
    @Operation(summary = "Upload a single File")
    public ResponseEntity<ResponseMessage> uploadNACEReport(@RequestParam("file") MultipartFile multipartFile) {
        log.info("upload file inside method");
        if(!"xlsx".equals(FilenameUtils.getExtension(multipartFile.getOriginalFilename()))) {
            throw new InvalidFileFormatException(multipartFile.getOriginalFilename());
        }
        try {
            naceService.save(multipartFile.getInputStream());
        } catch (IOException e) {
            throw new AssignmentException("Fail to store NACE Data: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMessage.builder().message("file uploaded successfully").build());
    }

    @GetMapping("/{orderId}")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Order is created"),
            @ApiResponse(code = 400, message = "Bad request, adjust details before retrying"),
            @ApiResponse(code = 500, message = "Internal Server Error, Please check")
    })
    public ResponseEntity<NomenclatureBean> fetchByOrderId(@PathVariable("orderId") long orderId) {
        log.info("Fetch order by Id");
        NomenclatureBean nomenclatureBean = naceService.getByOrderId(orderId);
        if(null == nomenclatureBean) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(nomenclatureBean);
    }
}
