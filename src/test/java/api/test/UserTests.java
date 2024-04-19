package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.apache.logging.log4j.Logger;

public class UserTests {
    Faker faker;
    User userPayLoad;
    Response response;
    public Logger logger; //

    @BeforeClass
    public void setupData(){
        faker = new Faker();
        userPayLoad = new User();

        //Setup data using POJO
        userPayLoad.setId(faker.idNumber().hashCode());
        userPayLoad.setUsername(faker.name().username());
        userPayLoad.setFirstName(faker.name().firstName());
        userPayLoad.setLastName(faker.name().lastName());
        userPayLoad.setEmail(faker.internet().safeEmailAddress());
        userPayLoad.setPassword(faker.internet().password(5 , 10));
        userPayLoad.setPhone(faker.phoneNumber().cellPhone());

        //logs
        logger = LogManager.getLogger(this.getClass());

         }
         @Test(priority=1)

        public void testPostUer(){
        response =UserEndPoints.createUser(userPayLoad);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("******User is created *****");

         }
         @Test(priority=2)
         public void testGetUserByName(){
        response = UserEndPoints.readUser(this.userPayLoad.getUsername());
        response.then().log().all();
        //response.statusCode(200);

       Assert.assertEquals(response.getStatusCode(), 200);
             logger.info("******User infor is displayed *****");

         }

         @Test(priority = 3)
        public void testUpdateUserByName(){

      logger.info("******Updating User *****");
       //update data payload
       userPayLoad.setFirstName(faker.name().firstName());
       userPayLoad.setLastName(faker.name().lastName());
       userPayLoad.setEmail(faker.internet().safeEmailAddress());

     response = UserEndPoints.updateUser(this.userPayLoad.getUsername(), userPayLoad);
       //response.then().log().body().statusCode(200); // achai RestAssured assertion
        response.then().log().body();
        Assert.assertEquals(response.getStatusCode(), 200);

       logger.info("******User is Updated*****");

        //Check data  after data

      Response responseAfterUpdate = UserEndPoints.readUser(this.userPayLoad.getUsername());
        Assert.assertEquals(response.getStatusCode(), 200);

         }

     @Test(priority=4)
      public void testDeleteUserByName(){
     response= UserEndPoints.deleteUser(this.userPayLoad.getUsername());
       Assert.assertEquals(response.getStatusCode(), 200);
         logger.info("******User is deleted *****");
     }
}
