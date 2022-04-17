package com.example.foodtip.Controller;

import java.util.Vector;

public class Steps {
    private Vector<Step> steps;

    public Steps(Vector<Step> steps) {
        this.steps = steps;
    }

    public Vector<Step> getSteps() {
        return steps;
    }

    public void setSteps(Vector<Step> steps) {
        this.steps = steps;
    }
}
