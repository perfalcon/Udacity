package com.example.balav.moviegridstage2.model;

import java.util.List;

public class Movie {

    private int id;
    private String title;
    private String posterPath;
    private String plotSynopsis; //overview
    private String releaseDate;
    private double userRating;
    private String reviews; //json String
    private String trailors; //json String

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getTrailors() {
        return trailors;
    }

    public void setTrailors(String trailors) {
        this.trailors = trailors;
    }

    /**

     * No args constructor for use in serialization
     */
    public Movie() {
    }

//    public Movie(String mainName, List<String> alsoKnownAs, String placeOfOrigin, String description, String image, List<String> ingredients) {
////        this.mainName = mainName;
////        this.alsoKnownAs = alsoKnownAs;
////        this.placeOfOrigin = placeOfOrigin;
////        this.description = description;
////        this.image = image;
////        this.ingredients = ingredients;
//    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }
}
