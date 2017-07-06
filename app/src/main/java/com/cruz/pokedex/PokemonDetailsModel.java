package com.cruz.pokedex;

import java.util.ArrayList;

/**
 * Created by Acer on 05/07/2017.
 */

public class PokemonDetailsModel {
    private String name; // naa dayun
    private String pic;
    private String number; //naa dayun
    private ArrayList<String> types;
    private String height; //naa dayun
    private String weight; //naa dayun
    private ArrayList<String> stats;
    private ArrayList<String> moves;
    private String specie;
    private ArrayList<String> versions;

    public PokemonDetailsModel() {
    }

    public PokemonDetailsModel(String name, String pic, String number, ArrayList<String> types, String height, String weight, ArrayList<String> stats, ArrayList<String> moves) {
        this.name = name;
        this.pic = pic;
        this.number = number;
        this.types = types;
        this.height = height;
        this.weight = weight;
        this.stats = stats;
        this.moves = moves;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public ArrayList<String> getStats() {
        return stats;
    }

    public void setStats(ArrayList<String> stats) {
        this.stats = stats;
    }

    public ArrayList<String> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<String> moves) {
        this.moves = moves;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public ArrayList<String> getVersions() {
        return versions;
    }

    public void setVersions(ArrayList<String> versions) {
        this.versions = versions;
    }
}
