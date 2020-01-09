import api.BoardApi;
import beans.Board;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static api.BoardApi.successResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TrelloBoardTests {

    @Test
    public void createNewBoardTest() {
        Board answers = BoardApi.getBoardAnswer(BoardApi.with()
                .createBoard("q12345"));
        assertThat(answers.getName(), equalTo("q12345"));
        assertThat(answers.getId(), equalTo("1"));
    }

    //
    @Test
    public void getBoardTest() {
        Board answers =
                BoardApi.getBoardAnswer(BoardApi.with().getBoard("rWEGZ1PQ"));
        assertThat(answers.getName(), equalTo("q12345"));
    }

    @Test
    public void deleteBoardTest() {
        Board answers =
                BoardApi.getBoardAnswer(BoardApi.with().deleteBoard("VWXoNTRa"));
        assertThat(answers.getName(), equalTo(null));
        BoardApi.responseSpecification().expect().spec(successResponse());
    }

    @Test
    public void updateBoardTest() {
        Board answers =
                BoardApi.getBoardAnswer(BoardApi.with().updateBoard("zd3uRo5j"));
        assertThat(answers.getName(), equalTo("9fd7ed6616441"));
    }
}

