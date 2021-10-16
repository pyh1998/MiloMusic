package com.example.social_network_app.Tokenizer_Parser.Music;

public class MusicToken {
    // The following enum defines different types of tokens.
    public enum Type {TAG,ARTIST,SEMICOLON}

    /**
     * The following exception should be thrown if a tokenizer attempts to tokenize something that is not of one
     * of the types of tokens.
     */
    public static class IllegalTokenException extends IllegalArgumentException {
        public IllegalTokenException(String errorMessage) {
            super(errorMessage);
        }
    }

    // Fields of the class Token.
    private final String token; // Token representation in String form.
    private final Type type;    // Type of the token.

    public MusicToken(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + token;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true; // Same hashcode.
        if (!(other instanceof MusicToken)) return false; // Null or not the same type.
        return this.type == ((MusicToken) other).getType() && this.token.equals(((MusicToken) other).getToken()); // Values are the same.
    }

}
