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


    }

    public List<RecycleLabel> getLabels(){
        return this.labels;
    }

}
