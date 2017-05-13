import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sbogolepov on 07/05/2017.
 */
public class LexerTest {

    private static List<Token> tokensFormString(String expr) throws IOException {
        Lexer lexer = new Lexer(new StringReader(expr));
        List<Token> tokens = new ArrayList<>();
        Token token = lexer.next();
        while (!Token.isEof(token)) {
            tokens.add(token);
            token = lexer.next();
        }
        return tokens;
    }

    @Test
    public void simpleExpr() throws Exception {
        assertThat(tokensFormString("TRUE AND FALSE OR id")).containsExactly(
                new Token(0, "TRUE", TokenType.TRUE),
                new Token(5, "AND", TokenType.AND),
                new Token(9, "FALSE", TokenType.FALSE),
                new Token(15, "OR", TokenType.OR),
                new Token(18, "id", TokenType.ID)
        );
    }

    @Test
    public void trueParens() throws Exception {
        assertThat(tokensFormString("(TRUE)")).containsExactly(
                new Token(0, "(", TokenType.LPAREN),
                new Token(1, "TRUE", TokenType.TRUE),
                new Token(5, ")", TokenType.RPAREN)
        );
    }

    @Test
    public void whitespaces() throws Exception {
        assertThat(tokensFormString("    ")).isEmpty();
    }
}
