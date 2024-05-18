package com.example.lab3;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model {

    @SerializedName("id")
    private int id;

    @SerializedName("question")
    private String question;

    @SerializedName("options")
    private String options;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public List<String> getOptionsList() {
        return new Gson().fromJson(options, List.class);
    }
}
