package com.richfield.smartpantry.model;

public class Recipe {
    private long id; private String name; private String ingredients; private String method;
    public Recipe(long id,String name,String ingredients,String method){this.id=id;this.name=name;this.ingredients=ingredients;this.method=method;}
    public long getId(){return id;} public String getName(){return name;} public String getIngredients(){return ingredients;} public String getMethod(){return method;}
}
