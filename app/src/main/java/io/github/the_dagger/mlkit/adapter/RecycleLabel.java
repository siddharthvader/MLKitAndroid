package io.github.the_dagger.mlkit.adapter;

import com.google.firebase.ml.vision.label.FirebaseVisionLabel;

import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

public class RecycleLabel {

    private String objectLabel;
    private double confidenceValue;
    private String properBin;

    private Set<String> recyclables = new HashSet<String>(Arrays.asList("paper", "can", "bottle"));

    public RecycleLabel(FirebaseVisionLabel label){

        String l = label.getLabel().toLowerCase();
        if (recyclables.contains(l)){
            this.objectLabel = l;
            this.properBin = "Recycling";
            this.confidenceValue = label.getConfidence();
        }
        else{
            this.objectLabel = l;
            this.properBin = "Trash";
//            confidenceValue = 0.6;
            this.confidenceValue = label.getConfidence();
        }

    }

    public String getObjectLabel(){
        return this.objectLabel;
    }

    public double getConfidenceValue(){
        return this.confidenceValue;
    }

    public String getProperBin(){
        return this.properBin;
    }

}
