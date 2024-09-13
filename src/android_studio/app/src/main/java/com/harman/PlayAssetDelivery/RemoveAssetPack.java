package com.harman.PlayAssetDelivery;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class RemoveAssetPack implements FREFunction {
    public static final String KEY = "RemoveAssetPack";
    private static final String TAG = "RemoveAssetPack";

    @Override
    public FREObject call(FREContext freContext, FREObject[] args) {
        PlayAssetExtensionContext ctx = (PlayAssetExtensionContext) freContext;
        FREObject returnValue = null;
        PlayAssetLogger.i(TAG, "RemoveAssetPack");
        try {
            String assertPack = args[0].getAsString();
            int status= ctx.getPlayAssetDeliveryManagerObj().removePack(assertPack);
            returnValue = FREObject.newObject(status);
        } catch (Exception e) {
            PlayAssetLogger.d(TAG,"Exception"+e.toString());
        }
        PlayAssetLogger.i(TAG, "RemoveAssetPack end");
        return returnValue;
    }
}
