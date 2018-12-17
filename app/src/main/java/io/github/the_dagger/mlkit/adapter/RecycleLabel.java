package io.github.the_dagger.mlkit.adapter;

import com.google.firebase.ml.vision.label.FirebaseVisionLabel;

import java.util.HashSet;
import java.util.List;
import java.util.Arrays;
import java.util.Set;

public class RecycleLabel {

    private String objectLabel;
    private double confidenceValue;
    private String confidence;
    private String properBin;

    private Set<String> recyclables = new HashSet<String>(Arrays.asList("paper", "can", "bottle"));

    public RecycleLabel(List<FirebaseVisionLabel> labels){

        boolean found = false;
        for(FirebaseVisionLabel label : labels){
            String l = label.getLabel();
            if (recyclables.contains(l)){
                this.objectLabel = l;
                this.properBin = "Recycling";
                this.confidenceValue = label.getConfidence();
                if (this.confidenceValue >= 0.8)
                    this.confidence = "High";
                else if (confidenceValue >= 0.6)
                    this.confidence = "Medium";
                else
                    this.confidence = "Low";
                found = true;
                break;
            }

            if (!found){
                objectLabel = "";
                properBin = "Trash";
                confidenceValue = 0.6;
                confidence = "Medium";
            }

        }
    }

    public String getObjectLabel(){
        return this.objectLabel;
    }

    public String getConfidence(){
        return this.confidence;
    }

    public double getConfidenceValue(){
        return this.confidenceValue;
    }

    public String getProperBin(){
        return this.properBin;
    }

}
