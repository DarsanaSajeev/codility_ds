package io.petstore.controller;

import io.petstore.pojo.Pet;
import io.petstore.pojo.Status;
import io.petstore.responses.PetPojoResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import java.io.PrintStream;
import java.util.List;


import static io.restassured.RestAssured.given;


public class PetsController {

    public static String baseUri = "https://petstore.swagger.io/v2";
    public static String PET_ENDPOINT = baseUri + "/pet";
    private RequestSpecification requestSpecification;
    PrintStream captor;

    public PetsController() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(baseUri);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();
    }

    /**
     * Add a new pet to the store
     */
    public Response addNewPet(Pet pet, PrintStream captor) {
        return given(requestSpecification).filter(new RequestLoggingFilter(captor))
                .body(pet)
                .post(PET_ENDPOINT);

    }

    public List<Pet> getPetsByStatus(Status status) {
        return given(requestSpecification)
                .queryParam("status", status.toString())
                .get(PET_ENDPOINT + "/findByStatus")
                .then().log().all()
                .extract().body()
                .jsonPath().getList("", Pet.class);
    }

    public Response updatePet(Pet pet, PrintStream captor) {
        return given(requestSpecification).filter(new RequestLoggingFilter(captor))
                .body(pet)
                .put(PET_ENDPOINT);
    }

    public Response updateImage(String imageURl, Pet pet) {
       return given(requestSpecification)
                .pathParam("petId", pet.getId())
                .formParam("file", "https://picsum.photos/id/237/200/300")
                .post(PET_ENDPOINT + "/{petId}/uploadImage");
    }

    public PetPojoResponse findPet(Pet pet) {
        return given(requestSpecification)
                .pathParam("petId", pet.getId())
                .get(PET_ENDPOINT + "/{petId}").as(PetPojoResponse.class);
    }

    public String findPetAfterDelete(Pet pet) {
        return given(requestSpecification)
                .pathParam("petId", pet.getId())
                .get(PET_ENDPOINT + "/{petId}")
                .then().log().all()
                .extract().body().jsonPath().get("message");
    }

    public String deletePet(Pet pet) {
        return given(requestSpecification)
                .pathParam("petId", pet.getId())
                .delete(PET_ENDPOINT + "/{petId}")
                .then().log().all()
                .extract().body().jsonPath().get("message");

    }


}
