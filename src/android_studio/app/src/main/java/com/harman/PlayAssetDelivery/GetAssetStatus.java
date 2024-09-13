package com.harman.PlayAssetDelivery;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class GetAssetStatus implements FREFunction {
    public static final String KEY = "GetAssetStatus";
    private static final String TAG = "GetAssetStatus";
    @Override
    public FREObject call(FREContext freContext, FREObject[] args) {
        PlayAssetExtensionContext ctx = (PlayAssetExtensionContext) freContext;
        FREObject returnValue = null;
        PlayAssetLogger.i(TAG, "GetAssetStatus");

        try {
            FREObject input = args[0];
            String assertName = input.getAsString();
            PlayAssetLogger.i(TAG, "Input value is " + assertName);
            int status = ctx.getPlayAssetDeliveryManagerObj().getStatus(assertName);
            returnValue = FREObject.newObject(status);
        } catch (Exception e) {
            PlayAssetLogger.d(TAG,"exception"+e.toString());
        }
        PlayAssetLogger.i(TAG, "GetAssetStatus end");
        return returnValue;
    }
}
