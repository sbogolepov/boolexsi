package lexer;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sbogolepov on 08/05/2017.
 */
public class Lexer {
    private Reader reader;
    private int currentChar = -1;
    private int currentPos = -1;

    private boolean hasPeeked = false;
    private Token peekedToken;

    private Map<String, TokenType> keywords;

    public Lexer(Reader reader) throws IOException {
        this.reader = reader;
        keywords = makeKeywordsMap();
        readNextChar();
    }

    private Map<String, TokenType> makeKeywordsMap() {
        Map<String, TokenType> kwMap = new HashMap<>();
        kwMap.put("AND", TokenType.AND);
        kwMap.put("OR", TokenType.OR);
        kwMap.put("NOT", TokenType.NOT);
        kwMap.put("TRUE", TokenType.TRUE);
        kwMap.put("FALSE", TokenType.FALSE);
        return kwMap;
    }

    private void readNextChar() throws IOException {
        currentChar = reader.read();
        currentPos++;
    }

    private void skipWhiteSpaces() throws IOException {
        while (Character.isWhitespace(currentChar)) {
            readNextChar();
        }
    }

    public Token next() throws IOException {
        if (hasPeeked) {
            hasPeeked = false;
            return peekedToken;
        }

        skipWhiteSpaces();
        if (isEof(currentChar)) {
            return new Token(currentPos, "", TokenType.EOF);
        }
        if (Character.isUpperCase(currentChar)) {
            return parseKeyword();
        }
        if (Character.isLowerCase(currentChar)) {
            return parseId();
        }
        if (isParen(currentChar)) {
            return parseParen();
        }
        return new Token(currentPos, "", TokenType.ERROR);
    }

    public Token peek() throws IOException {
        if (!hasPeeked) {
            peekedToken = next();
            hasPeeked = true;
        }
        return peekedToken;
    }

    private static boolean isEof(int currentChar) {
        return currentChar == -1;
    }


    private static boolean isParen(int currentChar) {
        return currentChar == '(' || currentChar == ')';
    }

    private Token parseKeyword() throws IOException {
        int startPos = currentPos;
        StringBuilder keywordBuilder = new StringBuilder();
        while (Character.isUpperCase(currentChar)) {
            keywordBuilder.appendCodePoint(currentChar);
            readNextChar();
        }
        String keyword = keywordBuilder.toString();
        TokenType tokenType = keywords.getOrDefault(keyword, TokenType.ERROR);
        return new Token(startPos, keyword, tokenType);
    }

    private Token parseId() throws IOException {
        int startPos = currentPos;
        StringBuilder idBuilder = new StringBuilder();
        while (Character.isLowerCase(currentChar)) {
            idBuilder.appendCodePoint(currentChar);
            readNextChar();
        }
        String id = idBuilder.toString();
        return new Token(startPos, id, TokenType.ID);
    }

    private Token parseParen() throws IOException {
        Token token = new Token(currentPos, String.valueOf((char) currentChar),
                currentChar == ')' ? TokenType.RPAREN : TokenType.LPAREN);
        readNextChar();
        return token;
    }
}
