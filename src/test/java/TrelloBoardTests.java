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

//https://api.trello.com/1/boards/?name=getid&key=cb9d5210ac7241d40bd7d4bb52fa2fc7&token=659afeb183ec0cc6c57a0f9bd70af5526d351e28a9c026e6a6cd1a213cc9438d
//5e161f718b977127681979f5
//5e162b5af3467a2a22cb6437
//https://api.trello.com/1/boards/5e162f8d5639d52cf64a790e?fields=name,url&key=cb9d5210ac7241d40bd7d4bb52fa2fc7&token=659afeb183ec0cc6c57a0f9bd70af5526d351e28a9c026e6a6cd1a213cc9438d
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
                BoardApi.getBoardAnswer(BoardApi.with().deleteBoard("5e162b5af3467a2a22cb6437"));
        assertThat(answers.getName(), equalTo(null));
    }

    @Test
    public void updateBoardTest() {
        Board answers =
                BoardApi.getBoardAnswer(BoardApi.with().updateBoard("5e1463928e727580f94eeb75"));
        assertThat(answers, equalTo("motherr"));
    }
}

