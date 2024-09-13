package com.harman.PlayAssetDelivery;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class GetByteDownloaded implements FREFunction {
    public static final String KEY = "GetByteDownloaded";
    private static final String TAG = "GetByteDownloaded";

    @Override
    public FREObject call(FREContext freContext, FREObject[] args) {
        PlayAssetExtensionContext ctx = (PlayAssetExtensionContext) freContext;
        FREObject returnValue = null;
        PlayAssetLogger.i(TAG, "GetByteDownloaded");
        try {
            FREObject input = args[0];
            String assetName = input.getAsString();
            PlayAssetLogger.i(TAG, "Input value is " + assetName);
            long totalBytes = ctx.getPlayAssetDeliveryManagerObj()
                    .byteDownloaded(assetName);
            returnValue = FREObject.newObject(totalBytes);
        } catch (Exception e) {
            PlayAssetLogger.d(TAG,"exception"+e.toString());
        }
        PlayAssetLogger.i(TAG, "GetByteDownloaded end");
        return returnValue;
    }
}
