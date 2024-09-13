package com.harman.PlayAssetDelivery;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class CancelAssetPack implements FREFunction {
    public static final String KEY = "CancelAssetPack";
    private static final String TAG = "CancelAssetPack";

    @Override
    public FREObject call(FREContext freContext, FREObject[] args) {
        PlayAssetExtensionContext ctx = (PlayAssetExtensionContext) freContext;
        FREObject returnValue = null;
        PlayAssetLogger.i(TAG, "CancelAssetPack");
        try {
            String assertPack = args[0].getAsString();
            int status= ctx.getPlayAssetDeliveryManagerObj().cancel(assertPack);
            returnValue = FREObject.newObject(status);
        } catch (Exception e) {
            PlayAssetLogger.d(TAG,"Exception"+e.toString());
        }
        PlayAssetLogger.i(TAG, "CancelAssetPack end");
        return returnValue;
    }
}
