package com.harman.PlayAssetDelivery;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class GetAssetAbsolutePath implements FREFunction {
    public static final String KEY = "GetAssetAbsolutePath";
    private static final String TAG = "GetAssetAbsolutePath";

    @Override
    public FREObject call(FREContext freContext, FREObject[] args) {
        PlayAssetExtensionContext ctx = (PlayAssetExtensionContext) freContext;
        FREObject returnValue = null;
        PlayAssetLogger.i(TAG, "GetAssetAbsolutePath");
        try {
            String assetPack = args[0].getAsString();
            String relativePath = args[1].getAsString();
            PlayAssetLogger.i(TAG, "GetAssetAbsolutePath"+assetPack);
            PlayAssetLogger.i(TAG, "GetAssetAbsolutePath"+relativePath);
            String absoluteAssetFolderPath = ctx.getPlayAssetDeliveryManagerObj()
                    .getAbsoluteAssetPath(assetPack,relativePath);
            PlayAssetLogger.i(TAG, "absoluteAssetPath " + absoluteAssetFolderPath);
          returnValue = FREObject.newObject(absoluteAssetFolderPath);
        } catch (Exception e) {
            PlayAssetLogger.d(TAG,"exception" +e.toString());
        }
        PlayAssetLogger.i(TAG, "GetAssetAbsolutePath end");
        return returnValue;
    }
}
