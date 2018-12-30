package io.github.the_dagger.mlkit.adapter;

import com.google.firebase.ml.vision.label.FirebaseVisionLabel;

import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

public class RecycleLabel {

    public String objectLabel;
    public String properBin;
    public double confidenceValue;


    public RecycleLabel(FirebaseVisionLabel label){

        Set<String> paper_recycle = new HashSet<String>(Arrays.asList("paper","cardboard","magazine","newspaper",
                "mail","carton","envelope","paper bag","gift wrap","wrapping paper","box","cardboard box","paper box",
                "egg carton","cereal box"));
        Set<String> plastic_recycle = new HashSet<String>(Arrays.asList("plastic","container","bottle","cups","lids"));
        Set<String> metal_recycle = new HashSet<String>(Arrays.asList("aluminum","foil","aluminum foil","can",
                "bottle cap","cd","dvd","metal"));
        Set<String> glass_recycle = new HashSet<String>(Arrays.asList("glass", "bottle", "jar"));
        Set<String> compost = new HashSet<String>(Arrays.asList("food","pizza","burger","fries","vegetable","fruit",
                "bread","egg","meat","seafood","fish","leaves","grass","tree","branch","wood","hair"));
        Set<String> no_recycle = new HashSet<String>(Arrays.asList("battery","lightbulb","light bulb","bulb","cfl",
                "computer","phone","electronics","hard drive","microchip","monitor","cord","charger","medicine",
                "paint","motor oil","oil"));

        String l = label.getLabel().toLowerCase();
        String l_plural;

        if(l.charAt(l.length()-1)=='s')
            l_plural = l.substring(0, l.length()-1);
        else
            l_plural = l + 's';

        if(l.equals("glass") || l.equals("grass"))
            l_plural = l;

        if (paper_recycle.contains(l) || paper_recycle.contains(l_plural)){
            this.objectLabel = l;
            this.properBin = "Recycle (Paper)";
            this.confidenceValue = label.getConfidence();
        }
        else if(plastic_recycle.contains(l) || plastic_recycle.contains(l_plural)){
            this.objectLabel = l;
            this.properBin = "Recycle (Metal/Plastic/Glass)";
            this.confidenceValue = label.getConfidence();
        }
        else if(glass_recycle.contains(l) || glass_recycle.contains(l_plural)){
            this.objectLabel = l;
            this.properBin = "Recycle (Metal/Plastic/Glass)";
            this.confidenceValue = label.getConfidence();
        }
        else if(metal_recycle.contains(l) || metal_recycle.contains(l_plural)){
            this.objectLabel = l;
            this.properBin = "Recycle (Metal/Plastic/Glass)";
            this.confidenceValue = label.getConfidence();
        }
        else if(compost.contains(l) || compost.contains(l_plural)){
            this.objectLabel = l;
            this.properBin = "Compost";
            this.confidenceValue = label.getConfidence();
        }
        else if(no_recycle.contains(l) || no_recycle.contains(l_plural)){
            this.objectLabel = l;
            this.properBin = "None (Special Disposal)";
            this.confidenceValue = label.getConfidence();
        }
        else{
            this.objectLabel = l;
            this.properBin = "Trash";
            this.confidenceValue = label.getConfidence();
        }

    }

    public RecycleLabel(String objectLabel, String properBin, double confidenceValue){

        this.objectLabel = objectLabel;
        this.properBin = properBin;
        this.confidenceValue = confidenceValue;

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
