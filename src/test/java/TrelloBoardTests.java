import api.BoardApi;
import beans.Board;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import java.util.ArrayList;
import static api.BoardApi.*;
import static api.BoardApi.RequestBuilder.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TrelloBoardTests {

    @Test
    public void createNewBoardTest() {
        String name = RandomStringUtils.random(5);
        Board newBoard = createNewBoard(name);
        getBoardResponse(newBoard.getId()).then().spec(successResponse());
        assertThat( name, equalTo(newBoard.getName()));
        deleteBoard(newBoard.getId());
    }

    @Test
    public void getBoardTest() {
        String name = RandomStringUtils.random(5);
        Board newBoard = createNewBoard(name);
        Board board = getBoard(newBoard.getId());
        assertThat( name, equalTo(board.getName()));
        deleteBoard(newBoard.getId());
    }

    @Test
    public void deleteBoardTest() {
        String name = RandomStringUtils.random(5);
        Board newBoard = createNewBoard(name);
        deleteBoard(newBoard.getId());
        getBoardResponse(newBoard.getId()).then().spec(boardNotFound());
    }

    @Test
    public void updateBoardTest() {
        String name = RandomStringUtils.random(5);
        Board newBoard = createNewBoard(name);
        String newName = RandomStringUtils.random(5);
        Board board = updateBoard(newBoard.getId(), newName);
        assertThat( newName, equalTo(board.getName()));
        deleteBoard(newBoard.getId());
    }
}

