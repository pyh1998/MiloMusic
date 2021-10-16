package com.example.social_network_app.Tokenizer_Parser.Music;

import com.example.social_network_app.Tokenizer_Parser.Tokenizer;

import java.util.Scanner;

public class MusicTokenizer extends Tokenizer {
    private String buffer;          // String to be transformed into tokens each time next() is called.
    private MusicToken currentToken;     // The current token. The next token is extracted when next() is called.

    /**
     * To help you both test and understand what this tokenizer is doing, we have included a main method
     * which you can run. Once running, provide a mathematical string to the terminal and you will
     * receive back the result of your tokenization.
     */
    public static void main(String[] args) {
        // Create a scanner to get the user's input.
        Scanner scanner = new Scanner(System.in);

        /*
         Continue to get the user's input until they exit.
         To exit press: Control + D or providing the string 'q'
         Example input you can try: ((1 + 2) * 5)/2
         */
        System.out.println("Provide a mathematical string to be tokenized:");
        while (scanner.hasNext()) {
            String input = scanner.nextLine();

            // Check if 'quit' is provided.
            if (input.equals("q"))
                break;

            // Create an instance of the tokenizer.
            MusicTokenizer tokenizer = new MusicTokenizer(input);

            // Print all the tokens.
            while (tokenizer.hasNext()) {
                System.out.print(tokenizer.current() + " ");
                tokenizer.next();
            }
            System.out.println();
        }
    }

    /**
     * Tokenizer class constructor
     * The constructor extracts the first token and save it to currentToken
     * **** please do not modify this part ****
     */
    public MusicTokenizer(String text) {
        buffer = text;          // save input text (string)
        next();                 // extracts the first token.
    }

    /**
     * This function will find and extract a next token from {@code _buffer} and
     * save the token to {@code currentToken}.
     */
    public void next() {
        buffer = buffer.trim();     // remove whitespace

        if (buffer.isEmpty()) {
            currentToken = null;    // if there's no string left, set currentToken null and return
            return;
        }

        char firstChar = buffer.charAt(0);
        if(firstChar == ';'){
            currentToken = new MusicToken(";");
        }
        else {
            StringBuilder builder = new StringBuilder();
            int pos = 0;
            while(pos < buffer.length() && buffer.charAt(pos) != ';'){
                builder.append(buffer.charAt(pos));
                pos++;
            }
            currentToken = new MusicToken(builder.toString());
        }

        // Remove the extracted token from buffer
        int tokenLen = currentToken.getLength();
        buffer = buffer.substring(tokenLen);
    }

    /**
     * Returns the current token extracted by {@code next()}
     *
     * @return type: Token
     */
    public MusicToken current() {
        return currentToken;
    }

    /**
     * Check whether there still exists another tokens in the buffer or not
     * **** please do not modify this part ****
     *
     * @return type: boolean
     */
    public boolean hasNext() {
        return currentToken != null;
    }
}
