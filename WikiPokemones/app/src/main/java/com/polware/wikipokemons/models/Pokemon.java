package com.polware.wikipokemons.models;

public class Pokemon {
    private String name;
    private int number;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        String[] urlParts = url.split("/");
        return Integer.parseInt(urlParts[urlParts.length -1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
