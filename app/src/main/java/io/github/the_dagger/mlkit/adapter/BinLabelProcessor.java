package io.github.the_dagger.mlkit.adapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BinLabelProcessor {

    private List<RecycleLabel> labels = new ArrayList<RecycleLabel>();

    public BinLabelProcessor(List<Object> detectedObjects){

        HashSet<String> usedLabels = new HashSet<>();

        for(Object o : detectedObjects){
            RecycleLabel l = (RecycleLabel) o;
//            String bin = l.getProperBin();
//            if(usedLabels.contains(bin))
//                continue;

            this.labels.add(l);
//            usedLabels.add(bin);
        }

        if(this.labels.size() > 1){
            double prob = this.labels.get(0).getConfidenceValue();
            List<RecycleLabel> newLabels = new ArrayList<RecycleLabel>();

            for(int i=0;i<this.labels.size();i++){
                if(this.labels.get(i).getConfidenceValue() <= prob - 0.10)
                    break;

                newLabels.add(this.labels.get(i));
            }

            this.labels = newLabels;

        }

    }

    public List<RecycleLabel> getLabels(){
        return this.labels;
    }

}
