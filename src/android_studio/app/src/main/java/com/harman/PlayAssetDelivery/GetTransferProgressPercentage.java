package com.harman.PlayAssetDelivery;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class GetTransferProgressPercentage implements FREFunction {
    public static final String KEY = "GetTransferProgressPercentage";
    private static final String TAG = "GetTransferProgressPercentage";

    @Override
    public FREObject call(FREContext freContext, FREObject[] args) {
        PlayAssetExtensionContext ctx = (PlayAssetExtensionContext) freContext;
        FREObject returnValue = null;
        PlayAssetLogger.i(TAG, "GetTransferProgressPercentage");
        try {
            FREObject input = args[0];
            String assetName = input.getAsString();
            PlayAssetLogger.i(TAG, "Input value is " + assetName);
            int percentage = ctx.getPlayAssetDeliveryManagerObj()
                    .transferProgressPercentage(assetName);
            returnValue = FREObject.newObject(percentage);
        } catch (Exception e) {
            PlayAssetLogger.d(TAG,"exception"+e.toString());
        }
        PlayAssetLogger.i(TAG, "GetTransferProgressPercentage end");
        return returnValue;
    }
}