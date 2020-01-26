package api;

import beans.Board;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import utils.ApiProperties;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.TEXT;
import static org.hamcrest.Matchers.lessThan;

public class BoardApi {
    public static final String ROOT_URL = ApiProperties.getInstance().getProperty("path");
    public static final String PROPERTY_TOKEN = ApiProperties.getInstance().getProperty("token");
    public static final String PROPERTY_KEY = ApiProperties.getInstance().getProperty("key");
    public static final String BOARD_PATH = ROOT_URL + "/boards/";


    private BoardApi() {
    }

    private HashMap<String, String> params = new HashMap<>();
    private Method method = Method.GET;

    public static class RequestBuilder {
        BoardApi boardApi;

        private RequestBuilder(BoardApi boardApi) {
            this.boardApi = boardApi;
        }

        ////Create a new board
        public Response createBoard(String name) {
            return RestAssured
                    .given()
                    .spec(requestSpecification())
                    .with()
                    .queryParam("name", name)
                    .log().all()
                    .when()
                    .post(BOARD_PATH)
                    .prettyPeek();
        }

        //Request a single board.
        public Response getBoard(String id) {
            return RestAssured
                    .given()
                    .spec(requestSpecification())
                    .with()
                    .queryParam("fields", "name")
                    .log().all()
                    .when()
                    .get(BOARD_PATH + id)
                    .prettyPeek();
        }

        //Delete a board.
        public Response deleteBoard(String id) {
            return RestAssured
                    .given()
                    .spec(requestSpecification())
                    .with()
                    .log().all()
                    .when()
                    .delete(BOARD_PATH + id)
                    .prettyPeek();
        }

        //Update an existing board by id
        public Response updateBoard(String id) {
            return RestAssured
                    .given(requestSpecification())
                    .with()
                    .log().all()
                    .put(BOARD_PATH + id)
                    .prettyPeek();
        }

        public Response getMemberships(String id) {
            return RestAssured
                    .given(requestSpecification())
                    .with()
                    .log().all()
                    .get(BOARD_PATH + id + "/memberships")
                    .prettyPeek();
        }

        public RequestBuilder id(String id) {
            boardApi.params.put("id", id);
            return this;
        }

        public RequestBuilder name(String name) {
            boardApi.params.put("name", name);
            return this;
        }

        public RequestBuilder method(Method method) {
            boardApi.method = method;
            return this;
        }
    }

    //return RequestBuilder
    public static RequestBuilder with() {
        BoardApi api = new BoardApi();
        return new RequestBuilder(api);
    }

    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setAccept(JSON)
                .addQueryParam("key", PROPERTY_KEY)
                .addQueryParam("token", PROPERTY_TOKEN)
                .build();
    }

    public static ResponseSpecification responseSpecification() {
        return new ResponseSpecBuilder()
                .expectContentType(JSON)
                .expectHeader(HttpHeaders.CONNECTION, "keep-alive")
                .expectResponseTime(lessThan(2000L))
                .expectStatusCode(HttpStatus.SC_OK)
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

    public static ResponseSpecification badRequest() {
        return new ResponseSpecBuilder()
                .expectContentType(TEXT)
                .expectHeader(HttpHeaders.CONNECTION, "keep-alive")
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .build();
    }

    public static ResponseSpecification serverErrorRequest() {
        return new ResponseSpecBuilder()
                .expectContentType(TEXT)
                .expectHeader(HttpHeaders.CONNECTION, "keep-alive")
                .expectStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .build();
    }

    public static Board getBoardAnswer(Response response) {
            return new Gson().fromJson(response.asString().trim(), new TypeToken<Board>() {
            }.getType());
    }
    public static ArrayList<Board> getBoardAnswerArray(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<ArrayList<Board>>() {
        }.getType());
    }

    public static String getRandomBoardName(int length) {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String NUMBER = "0123456789";
        final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + NUMBER;
        SecureRandom random = new SecureRandom();

        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            sb.append(rndChar);
        }
        return sb.toString();
    }
}

