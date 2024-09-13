package com.harman.extension
{
	import flash.utils.IDataInput;
	import flash.utils.ByteArray;
	import flash.errors.EOFError;
	import flash.external.ExtensionContext;

	public class AssetFile implements IDataInput
	{
		private var _isValid : Boolean;
		private var _isBigEndian : Boolean;
		private var _encoding : uint = 3;
		private var _extContext : ExtensionContext = null;
		private var _javaRef : uint;
		// must match our Java versions
		private static const TYPE_BOOLEAN : uint = 0;
		private static const TYPE_BYTE : uint = 1;
		private static const TYPE_UNSIGNEDBYTE : uint = 2;
		private static const TYPE_SHORT : uint = 3;
		private static const TYPE_UNSIGNEDSHORT : uint = 4;
		private static const TYPE_INT : uint = 5;
		private static const TYPE_UNSIGNEDINT : uint = 6;
		private static const TYPE_FLOAT : uint = 7;
		private static const TYPE_DOUBLE : uint = 8;

		public function AssetFile()
		{
		}
		
		public function get valid() : Boolean
		{
			return _isValid;
		}
		
		/* package */ function setup(ctx : ExtensionContext, ref : uint) : void
		{
			_extContext = ctx;
			_javaRef = ref;
			_isValid = true;
		}
		
		public function close() : void
		{
			_extContext.call("AssetFileClose", _javaRef);
			_isValid = false;
		}

		public function get bytesAvailable():uint
		{
			if (!_isValid) throw new EOFError("Asset file is not valid and cannot be read from");
			return _extContext.call("AssetFileBytesAvailable", _javaRef) as uint;
		}

		public function get endian():String
		{
			return _isBigEndian ? "bigEndian" : "littleEndian";
		}

		public function set endian(value:String):void
		{
			if (value != "littleEndian" && value != "bigEndian") throw new ArgumentError("Endian value must be bigEndian or littleEndian");
			_isBigEndian = (value == "bigEndian");
		}

		public function get objectEncoding():uint
		{
			return _encoding;
		}

		public function set objectEncoding(value:uint):void
		{
			if (value != 0 && value != 3) throw new ArgumentError("ObjectEncoding value must be 0 or 3");
			_encoding = value;
		}

		public function readBoolean():Boolean
		{
			if (!_isValid) throw new EOFError("Asset file is not valid and cannot be read from");
			return _extContext.call("AssetFileReadValue", _javaRef, TYPE_BOOLEAN, _isBigEndian) as Boolean;
		}

		public function readByte():int
		{
			if (!_isValid) throw new EOFError("Asset file is not valid and cannot be read from");
			return _extContext.call("AssetFileReadValue", _javaRef, TYPE_BYTE, _isBigEndian) as int;
		}

		public function readBytes(bytes:ByteArray, offset:uint = 0, length:uint = 0):void
		{
			if (!_isValid) throw new EOFError("Asset file is not valid and cannot be read from");
			if (length == 0) length = bytesAvailable;
			var ba : ByteArray = _extContext.call("AssetFileReadBytes", _javaRef, length) as ByteArray;
			ba.position = 0;
			return ba.readBytes(bytes, offset, length);
		}

		public function readDouble():Number
		{
			if (!_isValid) throw new EOFError("Asset file is not valid and cannot be read from");
			return _extContext.call("AssetFileReadValue", _javaRef, TYPE_DOUBLE, _isBigEndian) as Number;
		}

		public function readFloat():Number
		{
			if (!_isValid) throw new EOFError("Asset file is not valid and cannot be read from");
			return _extContext.call("AssetFileReadValue", _javaRef, TYPE_FLOAT, _isBigEndian) as Number;
		}

		public function readInt():int
		{
			if (!_isValid) throw new EOFError("Asset file is not valid and cannot be read from");
			return _extContext.call("AssetFileReadValue", _javaRef, TYPE_INT, _isBigEndian) as int;
		}

		public function readMultiByte(length:uint, charSet:String):String
		{
			throw new ArgumentError("AssetFile.readMultiByte is not supported");
			return null;
		}

		public function readObject():*
		{
			throw new ArgumentError("AssetFile.readObject is not supported");
			return null;
		}

		public function readShort():int
		{
			if (!_isValid) throw new EOFError("Asset file is not valid and cannot be read from");
			return _extContext.call("AssetFileReadValue", _javaRef, TYPE_SHORT, _isBigEndian) as int;
		}

		public function readUnsignedByte():uint
		{
			if (!_isValid) throw new EOFError("Asset file is not valid and cannot be read from");
			return _extContext.call("AssetFileReadValue", _javaRef, TYPE_UNSIGNEDBYTE, _isBigEndian) as uint;
		}

		public function readUnsignedInt():uint
		{
			if (!_isValid) throw new EOFError("Asset file is not valid and cannot be read from");
			return _extContext.call("AssetFileReadValue", _javaRef, TYPE_UNSIGNEDINT, _isBigEndian) as uint;
		}

		public function readUnsignedShort():uint
		{
			if (!_isValid) throw new EOFError("Asset file is not valid and cannot be read from");
			return _extContext.call("AssetFileReadValue", _javaRef, TYPE_UNSIGNEDSHORT, _isBigEndian) as uint;
		}

		public function readUTF():String
		{
			if (!_isValid) throw new EOFError("Asset file is not valid and cannot be read from");
			var strLen : uint = readUnsignedShort();
			var strOut : String = _extContext.call("AssetFileReadString", _javaRef, strLen) as String;
			if (!strOut) throw new EOFError("Cannot read from asset file");
			return strOut;
		}

		public function readUTFBytes(length:uint):String
		{
			if (!_isValid) throw new EOFError("Asset file is not valid and cannot be read from");
			if (!length) throw new ArgumentError("AssetFile.readUTFBytes: length argument cannot be zero");
			var strOut : String = _extContext.call("AssetFileReadString", _javaRef, length) as String;
			if (!strOut) throw new EOFError("Cannot read from asset file");
			return strOut;
		}

	}
}
