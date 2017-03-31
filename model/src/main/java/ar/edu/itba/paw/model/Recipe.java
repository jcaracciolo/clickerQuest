package ar.edu.itba.paw.model;

import java.util.List;

/**
 * Created by juanfra on 31/03/17.
 */
public class Recipe {
    private List<Production> input;
    private List<Production> output;

    public Recipe(List<Production> input, List<Production> output) {
        this.input = input;
        this.output = output;
    }

    public List<Production> getInput() {
        return input;
    }

    public List<Production> getOutput() {
        return output;
    }
}
