package com.cruz.pokedex;

/**
 * Created by Acer on 04/07/2017.
 */

public class PokemonModel {
    private String url;
    private String name;
    private String pic;

    public PokemonModel() {
    }

    public PokemonModel(String url, String name, String pic) {
        this.url = url;
        this.name = name;
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
