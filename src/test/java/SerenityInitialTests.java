import facts.NetflixPlans;
import models.users.Datum;
import models.users.RegisterUserInfo;
import models.users.UpdateUserInfo;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import net.serenitybdd.junit.runners.SerenityRunner;
import questions.GetUsersQuestion;
import questions.ResponseCode;
import tasks.RegisterUser;
import tasks.UpdateUser;
import tasks.getUsers;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(SerenityRunner.class)
public class SerenityInitialTests {

    private static final String restApiUrl = "http://localhost:5000/api";

    @Test
    public void InitialTest(){
        Actor fabrizio = Actor.named("Fabrizio the trainer")
                .whoCan(CallAnApi.at(restApiUrl));

        fabrizio.attemptsTo(
                getUsers.fromPage(1)
        );


        Datum user = new GetUsersQuestion().answeredBy(fabrizio)
                .getData().stream().filter(x -> x.getId() == 1).findFirst().orElse(null);


        fabrizio.should(
                seeThat("usuario no es nulo", act -> user, notNullValue())
        );

        fabrizio.should(
                seeThat("el email del usuario", act -> user.getEmail(), equalTo("george.bluth@reqres.in"))
        );
    }

    @Test
    public void registerUserTest() {
        Actor fabrizio = Actor.named("Fabrizio the trainer")
                .whoCan(CallAnApi.at(restApiUrl));

        String registerUserInfo = "{\n" +
                "\t\"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\",\n" +
                "    \"email\": \"tracey.ramos@reqres.in\",\n" +
                "    \"password\": \"serenity\"\n" +
                "}";

        fabrizio.attemptsTo(
                RegisterUser.withInfo(registerUserInfo)

        );

        fabrizio.should(
                seeThat("el codigo de respuesta", new ResponseCode(), equalTo(200))
        );

    }

    @Test
    public void registerUserTest2() {
        Actor fabrizio = Actor.named("Fabrizio the trainer")
                .whoCan(CallAnApi.at(restApiUrl));

        RegisterUserInfo registerUserInfo = new RegisterUserInfo();
        registerUserInfo.setName("morpheus");
        registerUserInfo.setJob("leader");
        registerUserInfo.setEmail("tracey.ramos@reqres.in");
        registerUserInfo.setPassword("serenity");

        fabrizio.attemptsTo(
                RegisterUser.withInfo(registerUserInfo)
        );

        fabrizio.should(
                seeThat("el codigo de respuesta", new ResponseCode(), equalTo(200))
        );

    }

    @Test
    public void UpdateUser() {
        Actor fabrizio = Actor.named("Fabrizio the trainer")
                .whoCan(CallAnApi.at(restApiUrl));

        UpdateUserInfo updateuserInfo = new UpdateUserInfo();
        updateuserInfo.setName("Fabrizio");
        updateuserInfo.setJob("Tester");


        fabrizio.attemptsTo(
                UpdateUser.withInfo(updateuserInfo)
        );

        fabrizio.should(
                seeThat("el codigo de respuesta", new ResponseCode(), equalTo(200))
        );

        fabrizio.should(
                seeThat("el nombre del usuario", act -> updateuserInfo.getName(), equalTo("Fabrizio")),
                seeThat("el trabajo del usuario", act -> updateuserInfo.getJob(), equalTo("Tester"))
        );

    }

    @Test
    public void UpdateUser2() {
        Actor fabrizio = Actor.named("Fabrizio the trainer")
                .whoCan(CallAnApi.at(restApiUrl));

        String updateUserInfo = "{\n" +
                "\t\"name\": \"Fabrizio\",\n" +
                "\t\"job\": \"writer\"\n" +
                "}";

        fabrizio.attemptsTo(
                UpdateUser.withInfo(updateUserInfo)

        );

        fabrizio.should(
                seeThat("el codigo de respuesta", new ResponseCode(), equalTo(200))
        );

    }

    @Test
    public void factTest() {
        Actor fabrizio = Actor.named("Fabrizio the trainer")
                .whoCan(CallAnApi.at(restApiUrl));

        fabrizio.has(NetflixPlans.toViewSeries());
    }

}
