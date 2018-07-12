package com.example.baek.photowifi.Model;

public class ParsingText {
    private String text;
    private int page;
    private int block;
    private int paragraph;
    private int word;
    private int symbol;

    public ParsingText(){
    }

    public ParsingText(String text, int page, int block, int paragraph, int word) {
        this.text = text;
        this.page = page;
        this.block = block;
        this.paragraph = paragraph;
        this.word = word;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getParagraph() {
        return paragraph;
    }

    public void setParagraph(int paragraph) {
        this.paragraph = paragraph;
    }

    public int getWord() {
        return word;
    }

    public void setWord(int word) {
        this.word = word;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }
}
