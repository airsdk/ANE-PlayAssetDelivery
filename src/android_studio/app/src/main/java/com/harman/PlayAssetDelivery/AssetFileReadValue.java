package com.harman.PlayAssetDelivery;

import com.adobe.fre.FREByteArray;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class AssetFileReadValue implements FREFunction {
    public static final String KEY = "AssetFileReadValue";
// must match our AS3 versions
    private static final int TYPE_BOOLEAN = 0;
    private static final int TYPE_BYTE = 1;
    private static final int TYPE_UNSIGNEDBYTE = 2;
    private static final int TYPE_SHORT = 3;
    private static final int TYPE_UNSIGNEDSHORT = 4;
    private static final int TYPE_INT = 5;
    private static final int TYPE_UNSIGNEDINT = 6;
    private static final int TYPE_FLOAT = 7;
    private static final int TYPE_DOUBLE = 8;


    @Override
    public FREObject call(FREContext freContext, FREObject[] args) {
        PlayAssetExtensionContext ctx = (PlayAssetExtensionContext) freContext;
        FREObject returnValue = null;
        try {
            // inputstream index (1-based) is in arg 0
            FREObject input = args[0];
            int isIndex = input.getAsInt() - 1;
            // type to read is in arg 1
            input = args[1];
            int dataType = input.getAsInt();
            // endian is in arg 2
            input = args[2];
            boolean isBigEndian = input.getAsBool();
            
            PlayAssetLogger.d(KEY, "Reading type " + dataType + " from input stream index " + isIndex);
            InputStream is = ctx.getPlayAssetDeliveryManagerObj().mStreams.get(isIndex);
            switch (dataType) {
                case TYPE_BOOLEAN:
                    returnValue = FREObject.newObject(is.read() > 0);
                    break;
                case TYPE_BYTE:
                case TYPE_UNSIGNEDBYTE:
                    returnValue = FREObject.newObject(is.read());
                    break;
                default:
                    byte[] bTemp = new byte[8];
                    ByteBuffer bBuf = ByteBuffer.wrap(bTemp);
                    bBuf.order(isBigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
                    switch (dataType) {
                        case TYPE_SHORT:
                        case TYPE_UNSIGNEDSHORT:
                            is.read(bTemp, 0, 2);
                            bBuf.position(0);
                            returnValue = FREObject.newObject(bBuf.getShort());
                            break;
                        case TYPE_INT:
                        case TYPE_UNSIGNEDINT:
                            is.read(bTemp, 0, 4);
                            bBuf.position(0);
                            returnValue = FREObject.newObject(bBuf.getInt());
                            break;
                        case TYPE_FLOAT:
                            is.read(bTemp, 0, 4);
                            bBuf.position(0);
                            returnValue = FREObject.newObject((double) bBuf.getFloat());
                            break;
                        case TYPE_DOUBLE:
                            is.read(bTemp, 0, 8);
                            bBuf.position(0);
                            returnValue = FREObject.newObject(bBuf.getDouble());
                            break;
                    }
            }
        } catch (Exception e) {
            PlayAssetLogger.d(KEY,"Exception: " + e.toString());
            e.printStackTrace();
        }
        return returnValue;
    }
}
