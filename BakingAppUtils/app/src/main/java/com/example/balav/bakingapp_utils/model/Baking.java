package com.example.balav.bakingapp_utils.model;

import java.util.ArrayList;
import java.util.List;

public class Baking {
    int id;
    String name;
    List<Ingredient> ingredients ;
    List<Step>steps;
    int servings;
    String image;

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "Baking{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                ", servings=" + servings +
                ", image='" + image + '\'' +
                '}';
    }



    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }


    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }




}
