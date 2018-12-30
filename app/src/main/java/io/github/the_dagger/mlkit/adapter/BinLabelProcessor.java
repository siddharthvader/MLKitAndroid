package io.github.the_dagger.mlkit.adapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BinLabelProcessor {

    private List<RecycleLabel> labels = new ArrayList<RecycleLabel>();

    public BinLabelProcessor(List<Object> detectedObjects){

        double prob = ((RecycleLabel) detectedObjects.get(0)).getConfidenceValue();

        for(Object o : detectedObjects){
            RecycleLabel l = (RecycleLabel) o;

            if(l.getConfidenceValue() <= prob - 0.10)
                break;
            this.labels.add(l);
        }

        // Add one more entry to the list which is a link to the google search
        String linkText = "<a href='http://google.com'>Learn More</a> about recycling in your area";
        RecycleLabel learnMore = new RecycleLabel(linkText, "", 0.0);
        this.labels.add(learnMore);

    }

    public List<RecycleLabel> getLabels(){
        return this.labels;
    }

}
