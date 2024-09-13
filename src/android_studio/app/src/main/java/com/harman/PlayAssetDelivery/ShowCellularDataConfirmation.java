package com.harman.PlayAssetDelivery;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class ShowCellularDataConfirmation implements FREFunction {
    public static final String KEY = "ShowCellularDataConfirmation";
    private static final String TAG = "ShowCellularDataConfirmation";

    @Override
    public FREObject call(FREContext freContext, FREObject[] args) {
        PlayAssetExtensionContext ctx = (PlayAssetExtensionContext) freContext;
        FREObject returnValue = null;
        PlayAssetLogger.i(TAG, "ShowCellularDataConfirmation");
        try {
            FREObject input = args[0];
            String assetName = input.getAsString();
            PlayAssetLogger.i(TAG, "Input value is " + assetName);
			boolean requestStatus = ctx.getPlayAssetDeliveryManagerObj().showCellularDataConfirmation(assetName);
            returnValue = FREObject.newObject(requestStatus);
        } catch (Exception e) {
            PlayAssetLogger.d(TAG,"Exception"+e.toString());
        }
        PlayAssetLogger.i(TAG, "ShowCellularDataConfirmation end");
        return returnValue;
    }
}