package com.customify.server.response_data_format.billing;

import com.customify.server.models.billing.FeatureModel;

//Whole Document Wrote By Moss

public class FeatureFormat extends FeatureModel<Integer, String, String> {
    public FeatureFormat(){}
    public FeatureFormat(Integer featureId, String featureName, String featureDescription) {
        super(featureId, featureName, featureDescription);
    }
}