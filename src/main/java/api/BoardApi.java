package api;

import beans.Board;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import utils.ApiProperties;
import java.util.HashMap;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.TEXT;


public class BoardApi {
    private static final String ROOT_URL = ApiProperties.getInstance().getProperty("path");
    private static final String PROPERTY_TOKEN = ApiProperties.getInstance().getProperty("token");
    private static final String PROPERTY_KEY = ApiProperties.getInstance().getProperty("key");
    private static final String BOARD_PATH = ROOT_URL + "/boards/";


    private BoardApi() {
    }

    public static class RequestBuilder {
        BoardApi boardApi;

        private RequestBuilder(BoardApi boardApi) {
            this.boardApi = boardApi;
        }

        public static Board createNewBoard(String name) {
            return new Gson().fromJson(
                    RestAssured
                    .given()
                    .spec(requestSpecification())
                    .with()
                    .queryParam("name", name)
                    .log().all()
                    .when()
                    .post(BOARD_PATH)
                    .prettyPeek().asString().trim(), new TypeToken<Board>() {
            }.getType());
        }

        public static Board getBoard(String id) {
            return new Gson().fromJson(
                    getBoardResponse(id).asString().trim(), new TypeToken<Board>() {
                    }.getType());
        }

        public static Response getBoardResponse(String id){
            return RestAssured
                    .given(requestSpecification())
                    .with()
                    .log().all()
                    .when()
                    .get(BOARD_PATH + id)
                    .prettyPeek();
        }

        public static void deleteBoard(String id) {
            new Gson().fromJson(
                    RestAssured
                            .given(requestSpecification())
                            .with()
                            .log().all()
                            .when()
                            .delete(BOARD_PATH + id)
                            .prettyPeek().asString().trim(), new TypeToken<Board>() {
                    }.getType());
        }

        public static Board updateBoard(String id, String newName) {
            return new Gson().fromJson(
                    RestAssured
                            .given(requestSpecification())
                            .with()
                            .queryParam("name",newName)
                            .log().all()
                            .put(BOARD_PATH + id)
                            .prettyPeek().asString().trim(), new TypeToken<Board>() {
                    }.getType());
        }
    }

    private static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setAccept(JSON)
                .addQueryParam("key", PROPERTY_KEY)
                .addQueryParam("token", PROPERTY_TOKEN)
                .build();
    }

    public static ResponseSpecification successResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(JSON)
                .expectHeader(HttpHeaders.CONNECTION, "keep-alive")
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static ResponseSpecification boardNotFound() {
        return new ResponseSpecBuilder()
                .expectContentType(TEXT)
                .expectHeader(HttpHeaders.CONNECTION, "keep-alive")
                .expectStatusCode(HttpStatus.SC_NOT_FOUND)
                .build();
    }
}

