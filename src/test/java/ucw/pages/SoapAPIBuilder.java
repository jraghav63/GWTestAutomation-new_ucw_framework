package ucw.pages;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.models.soap.Envelope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;

public class SoapAPIBuilder extends BasePage {
    private static final Logger logger = LogManager.getLogger(SoapAPIBuilder.class);
    private static final XmlMapper xmlMapper = new XmlMapper();
    private final RequestSpecification request;
    private Envelope envelope;
    private Response response;
    public SoapAPIBuilder() {
        logger.info("Build the SOAP request for service box");
        RestAssured.baseURI = centerUrl.getServiceBox().getBaseUrl();
        request = given()
                .relaxedHTTPSValidation()
                .basePath(centerUrl.getServiceBox().getPath());
        request.header("Content-Type", "application/soap+xml;charset=utf-8");
    }

    public void setBodyFrom(String fileName) {
        try {
            String requestPayload = Files.readString(Path.of(fileName));
            envelope = xmlMapper.readValue(requestPayload, Envelope.class);
            request.body(requestPayload);
            dataObject.setPolicyNumber(envelope.getBody().getSetServiceQuotePartsandOpt().getSerQuoteData().getPolicyNumber());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Envelope getRequest() {
        return envelope;
    }

    public void sendRequest() {
        response = request.post();
    }

    public void printResponseAsString() {
        response.body().prettyPrint();
    }

    public String getReturnedUrl() {
        return response.then().extract().xmlPath().getString("Envelope.Body.setServiceQuotePartsandOptResponse.return");
    }
}
