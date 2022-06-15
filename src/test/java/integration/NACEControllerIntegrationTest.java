package integration;

import com.db.assignment.AssignmentApplication;
import com.db.assignment.domain.NomenclatureBean;
import com.db.assignment.domain.ResponseMessage;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;

import static java.util.Arrays.asList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AssignmentApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NACEControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @SneakyThrows
    @org.junit.jupiter.api.Test
    @Order(1)
    public void uploadNACEReportTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("file", createFileResource());

        final ParameterizedTypeReference<ResponseMessage> typeReference = new ParameterizedTypeReference<ResponseMessage>() {};
        final ResponseEntity<ResponseMessage> exchange = testRestTemplate.exchange("/nace/upload", HttpMethod.POST, new HttpEntity<>(requestMap, headers), typeReference);

        Assert.assertEquals(HttpStatus.OK, exchange.getStatusCode());
        Assert.assertEquals("file uploaded successfully", exchange.getBody().getMessage());
    }

    @SneakyThrows
    @Test
    @Order(2)
    public void fetchDataByOrderId() {

        final ResponseEntity<NomenclatureBean> exchange = testRestTemplate.getForEntity("/nace/398481", NomenclatureBean.class);
        Assert.assertEquals(HttpStatus.OK, exchange.getStatusCode());
    }

    static Resource createFileResource() throws IOException {
        File initialFile = new File("src/main/resources/NACEListTest.xlsx");
        return new FileSystemResource(initialFile);
    }
}
