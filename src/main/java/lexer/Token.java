package lexer;

/**
 * Created by sbogolepov on 08/05/2017.
 */
public class Token {
    private int position;
    private String value;
    private TokenType tokenType;

    public Token(int position, String value, TokenType tokenType) {
        this.position = position;
        this.value = value;
        this.tokenType = tokenType;
    }

    public static boolean isEof(Token token) {
        return token.getTokenType() == TokenType.EOF;
    }

    public int getPosition() {
        return position;
    }

    public String getValue() {
        return value;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (position != token.position) return false;
        if (value != null ? !value.equals(token.value) : token.value != null) return false;
        return tokenType == token.tokenType;
    }

    @Override
    public int hashCode() {
        int result = position;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (tokenType != null ? tokenType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Token{" +
                "position=" + position +
                ", value='" + value + '\'' +
                ", tokenType=" + tokenType +
                '}';
    }
}
