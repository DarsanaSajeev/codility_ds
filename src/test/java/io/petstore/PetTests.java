package io.petstore;


import io.petstore.controller.PetsController;
import io.petstore.pojo.Category;
import io.petstore.pojo.Pet;
import io.petstore.pojo.Status;
import io.petstore.pojo.Tag;
import io.petstore.responses.PetPojoResponse;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.Collections;
import java.util.List;


public class PetTests extends BaseTest {
    private static final String PHOTO_URL = "https://www.tesco.ie/groceries/MarketingContent/Sites/Retail/superstore/Online/P/i/departments/2016/Pets/1BC.jpg";
    PetsController petsController;
    Pet pet = new Pet.Builder()
            .withId(RandomStringUtils.randomNumeric(5))
            .withName("My pet")
            .withPhotoUrls(Collections.singletonList(PHOTO_URL))
            .withStatus(Status.available)
            .withTags(Collections.singletonList(new Tag(1, "golden-retriever")))
            .inCategory(new Category(1, "dogs")).build();

    @BeforeClass
    public void beforeClass() {
        petsController = new PetsController();
    }

    @Test(priority = 0)
    public void addNewPet() {
        Response response = petsController.addNewPet(pet, captor);
        PetPojoResponse petResponse = response.as(PetPojoResponse.class);
        Assert.assertEquals(petResponse.getName(), pet.getName());
        writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());
    }

    @Test(priority = 1)
    public void verifyNewPet() {
        PetPojoResponse petResponse = petsController.findPet(pet);
        Assert.assertEquals(petResponse.getName(), pet.getName());
    }

    @Test(priority = 2)
    public void updatePet() {
        pet.setName("New name for my pet");
        pet.setStatus(Status.pending);
        Response response = petsController.updatePet(pet, captor);
        PetPojoResponse petResponse = response.as(PetPojoResponse.class);
        Assert.assertEquals(petResponse.getName(), pet.getName());
        Assert.assertEquals(petResponse.getStatus(), pet.getStatus().toString());
        writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());
    }

    @Test(priority = 3)
    public void verifyUpdatedPet() {
        PetPojoResponse petResponse = petsController.findPet(pet);
        Assert.assertEquals(petResponse.getName(), pet.getName());
    }

    @Test(priority = 5)
    public void getPetByStatus() {
        List<Pet> listOfPet = petsController.getPetsByStatus(Status.sold);
        System.out.println("Number of pets with status available: " + listOfPet.size());
    }

    @Test(priority = 6)
    public void uploadImage() {
       Response response = petsController.updateImage("https://picsum.photos/id/237/200/300", pet);
        writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());
    }

    @Test(priority = 7)
    public void deletePet() {
       String message = petsController.deletePet(pet);
       Assert.assertEquals(message, pet.getId());
    }

    @Test(priority = 8)
    public void verifyPetDeleted() {
        String message = petsController.findPetAfterDelete(pet);
        Assert.assertEquals(message, "Pet not found");
    }
}
