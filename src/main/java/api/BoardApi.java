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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.TEXT;
import static org.hamcrest.Matchers.lessThan;

public class BoardApi {
    public static final String ROOT_URL = "https://api.trello.com/1";
    public static final String PROPERTY_TOKEN = "659afeb183ec0cc6c57a0f9bd70af5526d351e28a9c026e6a6cd1a213cc9438d";
    public static final String PROPERTY_KEY = "cb9d5210ac7241d40bd7d4bb52fa2fc7";
    public static final String BOARD_PATH = ROOT_URL+"/boards/";


    private BoardApi() {}
    private HashMap<String, String> params = new HashMap<>();
    private Method method = Method.GET;

    public static class RequestBuilder {
        BoardApi boardApi;

        private RequestBuilder(BoardApi boardApi) {
            this.boardApi = boardApi;
        }

        public Response createBoard(String name){
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

        public Response getBoard(String id){
            return RestAssured
                    .given(requestSpecification())
                    .with()
                    .queryParams(boardApi.params)
                    .log().all()
                    .get(ROOT_URL + BOARD_PATH+id)
                    .prettyPeek();
        }

        public Response deleteBoard(String id){
            return RestAssured
                    .given(requestSpecification())
                    .with()
                    .queryParams(boardApi.params)
                    .log().all()
                    .delete(ROOT_URL + BOARD_PATH+id)
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

        public RequestBuilder method(Method method){
            boardApi.method = method;
            return this;
        }

        //
        public Response callApi() {
            return RestAssured.with()
               //     .queryParam(boardApi.params)
               //     .queryParam("name", "fromJava")
//                    .queryParam("id", "5e151328f279fd7ed6616441")
                     .spec(requestSpecification())
//                    .log().all()
//                    .basePath(BOARD_PATH)
//                    .request(Method.GET)
                    .post("https://api.trello.com/1/boards/?name=9fd7ed6616441&key=cb9d5210ac7241d40bd7d4bb52fa2fc7&token=659afeb183ec0cc6c57a0f9bd70af5526d351e28a9c026e6a6cd1a213cc9438d")
                    .prettyPeek();
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
                    .addQueryParam("key",PROPERTY_KEY)
                    .addQueryParam("token", PROPERTY_TOKEN)
      //              .addQueryParam("reqId", new Random())
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
    public static ResponseSpecification successResponse(){
        return new ResponseSpecBuilder()
                .expectContentType(JSON)
                .expectHeader(HttpHeaders.CONNECTION, "keep-alive")
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static ResponseSpecification boardNotFound(){
        return new ResponseSpecBuilder()
                .expectContentType(TEXT)
                .expectHeader(HttpHeaders.CONNECTION, "keep-alive")
                .expectStatusCode(HttpStatus.SC_NOT_FOUND)
                .build();
    }

    public static ResponseSpecification badRequest(){
        return new ResponseSpecBuilder()
                .expectContentType(TEXT)
                .expectHeader(HttpHeaders.CONNECTION, "keep-alive")
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .build();
    }

    public static ResponseSpecification serverErrorRequest(){
        return new ResponseSpecBuilder()
                .expectContentType(TEXT)
                .expectHeader(HttpHeaders.CONNECTION, "keep-alive")
                .expectStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .build();
    }

    public static Board getBoardAnswer(Response response){
    //    return new Gson().fromJson(response.asString().trim(), new TypeToken<Board>(){}.getType());
        return new Gson().fromJson(response.asString().trim(), new TypeToken<Board>(){}.getType());
    }
}
