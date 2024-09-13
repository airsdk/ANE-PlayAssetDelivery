package com.harman.PlayAssetDelivery;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class GetAssetPackLocation implements FREFunction {
    public static final String KEY = "GetAssetPackLocation";
    private static final String TAG = "GetAssetPackLocation";

    @Override
    public FREObject call(FREContext freContext, FREObject[] args) {
        PlayAssetExtensionContext ctx = (PlayAssetExtensionContext) freContext;
        FREObject returnValue = null;
        PlayAssetLogger.i(TAG, "GetAssetPackLocation");
        try {
            String assertPack = args[0].getAsString();
            String status= ctx.getPlayAssetDeliveryManagerObj().getAssetPackLocation(assertPack);
            returnValue = FREObject.newObject(status);
        } catch (Exception e) {
            PlayAssetLogger.d(TAG,"exception"+e.toString());
        }
        PlayAssetLogger.i(TAG, "GetAssetPackLocation end");
        return returnValue;
    }
}