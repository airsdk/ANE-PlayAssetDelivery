package com.harman.PlayAssetDelivery;

import com.adobe.fre.FREByteArray;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class AssetFileReadString implements FREFunction {
    public static final String KEY = "AssetFileReadString";

    @Override
    public FREObject call(FREContext freContext, FREObject[] args) {
        PlayAssetExtensionContext ctx = (PlayAssetExtensionContext) freContext;
        FREObject returnValue = null;
        try {
            // inputstream index (1-based) is in arg 0
            FREObject input = args[0];
            int isIndex = input.getAsInt() - 1;
            // length of string in arg 1
            input = args[1];
            int strLen = input.getAsInt();
			
            PlayAssetLogger.d(KEY, "Reading string data, length " + strLen + " from input stream index " + isIndex);
            InputStream is = ctx.getPlayAssetDeliveryManagerObj().mStreams.get(isIndex);
            byte[] bTemp = new byte[strLen];
            is.read(bTemp);

            String strOut = new String(bTemp, StandardCharsets.UTF_8);
            PlayAssetLogger.d(KEY, "String to return = " + strOut);
            returnValue = FREObject.newObject(strOut);
        } catch (Exception e) {
            PlayAssetLogger.d(KEY,"Exception: " + e.toString());
            e.printStackTrace();
        }
        return returnValue;
    }
}
