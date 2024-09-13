package com.harman.PlayAssetDelivery;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class GetTotalBytesToDownLoad implements FREFunction {
    public static final String KEY = "GetTotalBytesToDownLoad";
    private static final String TAG = "GetTotalBytesToDownLoad";

    @Override
    public FREObject call(FREContext freContext, FREObject[] args) {
        PlayAssetExtensionContext ctx = (PlayAssetExtensionContext) freContext;
        FREObject returnValue = null;
        PlayAssetLogger.i(TAG, "GetTotalBytesToDownLoad");
        try {
            FREObject input = args[0];
            String assetName = input.getAsString();
            PlayAssetLogger.i(TAG, "Input value is " + assetName);
            long totalBytes = ctx.getPlayAssetDeliveryManagerObj()
                    .totalBytesToDownload(assetName);
            returnValue = FREObject.newObject(totalBytes);
        } catch (Exception e) {
            PlayAssetLogger.d(TAG,"exception"+e.toString());
        }
        PlayAssetLogger.i(TAG, "GetTotalBytesToDownLoad end");
        return returnValue;
    }
}
