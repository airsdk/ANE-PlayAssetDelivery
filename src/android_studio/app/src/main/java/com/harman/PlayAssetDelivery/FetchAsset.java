package com.harman.PlayAssetDelivery;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

import java.util.Collections;
import java.util.List;

public class FetchAsset implements FREFunction {
    public static final String KEY = "FetchAsset";
    private static final String TAG = "FetchAsset";

    @Override
    public FREObject call(FREContext freContext, FREObject[] args) {
        PlayAssetExtensionContext ctx = (PlayAssetExtensionContext) freContext;
        FREObject returnValue = null;
        PlayAssetLogger.i(TAG, "FetchAsset");
        try {
            FREObject input = args[0];
            List<String> assertName = Collections.singletonList(input.getAsString());
            PlayAssetLogger.i(TAG, "Input value is " + assertName);
            int fetchStatus = ctx.getPlayAssetDeliveryManagerObj().fetch(assertName);
            returnValue = FREObject.newObject(fetchStatus);
        } catch (Exception e) {
            PlayAssetLogger.d(TAG,"Exception"+e.toString());
        }
        PlayAssetLogger.i(TAG, "FetchAsset end");
        return returnValue;
    }
}