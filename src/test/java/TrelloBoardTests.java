import api.BoardApi;
import beans.Board;
import org.junit.Test;
import static api.BoardApi.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TrelloBoardTests {

    @Test
    public void createNewBoardTest() {
        String boardName = getRandomBoardName(5);
        Board answers = getBoardAnswer(BoardApi.with()
                .createBoard(boardName));
        assertThat(answers.getName(), equalTo(boardName));
    }

    @Test
    public void getBoardTest() {
        String boardName = getRandomBoardName(5);
        String boardId = getBoardAnswer(BoardApi.with()
                .createBoard(boardName)).getId();
        Board answers =
                getBoardAnswer(BoardApi.with().getBoard(boardId));
        assertThat(answers.getName(), equalTo(boardName));
    }

    @Test
    public void deleteBoardTest() {
        String boardName = getRandomBoardName(5);
        String boardId = getBoardAnswer(BoardApi.with()
                .createBoard(boardName)).getId();
        Board answers =
                getBoardAnswer(BoardApi.with().deleteBoard(boardId));
        assertThat(answers.getName(), equalTo(null));
        BoardApi.responseSpecification().expect().spec(successResponse());
    }

    @Test
    public void updateBoardTest() {
        String boardName = getRandomBoardName(5);
        String boardId = getBoardAnswer(BoardApi.with()
                .createBoard(boardName)).getId();
        Board answers =
                getBoardAnswer(BoardApi.with().updateBoard(boardId));
        assertThat(answers.getName(), equalTo(boardName));
    }
}

