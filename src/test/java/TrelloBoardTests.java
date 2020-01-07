import api.BoardApi;
import beans.Board;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TrelloBoardTests {
    @Test
    public void boardCanBeCreatedWithCurrentName(){
        Board answers = BoardApi.getBoardAnswer(
                BoardApi.with().getBoard("5e1463928e727580f94eeb75"));
        assertThat(answers, equalTo("motherr"));
    }
}

