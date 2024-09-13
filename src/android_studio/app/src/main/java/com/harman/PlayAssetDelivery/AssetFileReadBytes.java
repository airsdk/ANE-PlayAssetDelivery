package com.harman.PlayAssetDelivery;

import com.adobe.fre.FREByteArray;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

import java.io.InputStream;
import java.nio.ByteBuffer;

public class AssetFileReadBytes implements FREFunction {
    public static final String KEY = "AssetFileReadBytes";

    @Override
    public FREObject call(FREContext freContext, FREObject[] args) {
        PlayAssetExtensionContext ctx = (PlayAssetExtensionContext) freContext;
        FREObject returnValue = null;
        try {
            // inputstream index (1-based) is in arg 0
            FREObject input = args[0];
            int isIndex = input.getAsInt() - 1;
            // number of bytes to read is in arg 1
            input = args[1];
            int numBytes = input.getAsInt();

            PlayAssetLogger.i(KEY, "Reading " + numBytes + " from input stream index " + isIndex);
            InputStream is = ctx.getPlayAssetDeliveryManagerObj().mStreams.get(isIndex);
            byte[] bTemp = new byte[numBytes];
            int numRead = is.read(bTemp);

            FREByteArray obj = FREByteArray.newByteArray();
            obj.setProperty("length", FREObject.newObject(numRead));
            obj.acquire();
            ByteBuffer bytes = obj.getBytes();
            bytes.put(bTemp, 0, numRead);
            obj.release();

            returnValue = obj;
        } catch (Exception e) {
            PlayAssetLogger.d(KEY,"Exception: " + e.toString());
            e.printStackTrace();
        }
        return returnValue;
    }
}
