package com.example.social_network_app.Tokenizer_Parser;

/**
 * @author Yuhui Pang
 */
public abstract class Tokenizer  {

    /**
     * check whether there is a next token in the remaining text.
     * @return true if there is, falser otherwise
     */
    public abstract boolean hasNext();

    /**
     * return the current token extracted by next() method.
     * @return the current token
     */
    public abstract Object current();

    /**
     *  extract next token from the current text and save it.
     */
    public abstract void next();


}
