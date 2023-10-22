package io.petstore;

import io.restassured.RestAssured;
import org.apache.commons.io.output.WriterOutputStream;
import org.testng.annotations.BeforeMethod;

import java.io.PrintStream;
import java.io.StringWriter;

import static io.petstore.controller.PetsController.baseUri;

public class BaseTest {

    protected StringWriter writer;
    protected PrintStream captor;


    @BeforeMethod
    public void setUp() {
        RestAssured.baseURI = baseUri;
        writer = new StringWriter();
        captor = new PrintStream(new WriterOutputStream(writer), true);
    }

    protected void formatAPIAndLogInReport(String content) {

        String prettyPrint = content.replace("\n", "<br>");
        System.out.println("<pre>" + prettyPrint + "</pre>");

    }

    public void writeRequestAndResponseInReport(String request, String response) {

        System.out.println("---- Request ---");
        formatAPIAndLogInReport(request);
        System.out.println("---- Response ---");
        formatAPIAndLogInReport(response);
    }

}
