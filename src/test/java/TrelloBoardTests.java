import api.BoardApi;
import beans.Board;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TrelloBoardTests {
    @Test
    public void boardCanBeCreatedWithCurrentName(){
        //**
        List<Board> answers = BoardApi.getBoardAnswer(
                BoardApi.with().id("").callApi());
        assertThat(answers.get(0), equalTo("motherr"));
        assertThat(answers.get(1), equalTo("fatherr"));
        assertThat(answers.get(0), equalTo("mother"));
        assertThat(answers.get(1), equalTo("father"));
        assertThat(answers.get(2), equalTo(""));
    }
}

