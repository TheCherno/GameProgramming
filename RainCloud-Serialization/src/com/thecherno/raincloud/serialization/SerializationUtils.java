package com.thecherno.raincloud.serialization;

import java.nio.ByteBuffer;

public class SerializationUtils {

	public static int writeBytes(byte[] dest, int pointer, byte[] src) {
		assert(dest.length >= pointer + src.length);
		for (int i = 0; i < src.length; i++)
			dest[pointer++] = src[i];
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, char[] src) {
		assert(dest.length > pointer + src.length);
		for (int i = 0; i < src.length; i++)
			pointer = writeBytes(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, short[] src) {
		assert(dest.length > pointer + src.length);
		for (int i = 0; i < src.length; i++)
			pointer = writeBytes(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, int[] src) {
		assert(dest.length > pointer + src.length);
		for (int i = 0; i < src.length; i++)
			pointer = writeBytes(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, long[] src) {
		assert(dest.length > pointer + src.length);
		for (int i = 0; i < src.length; i++)
			pointer = writeBytes(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, float[] src) {
		assert(dest.length > pointer + src.length);
		for (int i = 0; i < src.length; i++)
			pointer = writeBytes(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, double[] src) {
		assert(dest.length > pointer + src.length);
		for (int i = 0; i < src.length; i++)
			pointer = writeBytes(dest, pointer, src[i]);
		return pointer;
	}

	public static int writeBytes(byte[] dest, int pointer, boolean[] src) {
		assert(dest.length > pointer + src.length);
		for (int i = 0; i < src.length; i++)
			pointer = writeBytes(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, byte value) {
		assert(dest.length > pointer + Type.getSize(Type.BYTE));
		dest[pointer++] = value;
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, short value) {
		assert(dest.length > pointer + Type.getSize(Type.SHORT));
		dest[pointer++] = (byte)((value >> 8) & 0xff);
		dest[pointer++] = (byte)((value >> 0) & 0xff);
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, char value) {
		assert(dest.length > pointer + Type.getSize(Type.CHAR));
		dest[pointer++] = (byte)((value >> 8) & 0xff);
		dest[pointer++] = (byte)((value >> 0) & 0xff);
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, int value) {
		assert(dest.length >= pointer + Type.getSize(Type.INTEGER));
		dest[pointer++] = (byte)((value >> 24) & 0xff);
		dest[pointer++] = (byte)((value >> 16) & 0xff);
		dest[pointer++] = (byte)((value >> 8) & 0xff);
		dest[pointer++] = (byte)((value >> 0) & 0xff);
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, long value) {
		assert(dest.length > pointer + Type.getSize(Type.LONG));
		dest[pointer++] = (byte)((value >> 56) & 0xff);
		dest[pointer++] = (byte)((value >> 48) & 0xff);
		dest[pointer++] = (byte)((value >> 40) & 0xff);
		dest[pointer++] = (byte)((value >> 32) & 0xff);
		dest[pointer++] = (byte)((value >> 24) & 0xff);
		dest[pointer++] = (byte)((value >> 16) & 0xff);
		dest[pointer++] = (byte)((value >> 8) & 0xff);
		dest[pointer++] = (byte)((value >> 0) & 0xff);
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, float value) {
		assert(dest.length > pointer + Type.getSize(Type.FLOAT));
		int data = Float.floatToIntBits(value);
		return writeBytes(dest, pointer, data);
	}
	
	public static int writeBytes(byte[] dest, int pointer, double value) {
		assert(dest.length > pointer + Type.getSize(Type.DOUBLE));
		long data = Double.doubleToLongBits(value);
		return writeBytes(dest, pointer, data);
	}
	
	public static int writeBytes(byte[] dest, int pointer, boolean value) {
		assert(dest.length > pointer + Type.getSize(Type.BOOLEAN));
		dest[pointer++] = (byte)(value ? 1 : 0);
		return pointer;
	}
	
	public static int writeBytes(byte[] dest, int pointer, String string) {
		pointer = writeBytes(dest, pointer, (short) string.length());
		return writeBytes(dest, pointer, string.getBytes());
	}
	
	public static byte readByte(byte[] src, int pointer) {
		return src[pointer];
	}
	
	public static void readBytes(byte[] src, int pointer, byte[] dest) {
		for (int i = 0; i < dest.length; i++)
			dest[i] = src[pointer + i];
	}
	
	public static void readShorts(byte[] src, int pointer, short[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i] = readShort(src, pointer);
			pointer += Type.getSize(Type.SHORT);
		}
	}
	
	public static void readChars(byte[] src, int pointer, char[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i] = readChar(src, pointer);
			pointer += Type.getSize(Type.CHAR);
		}
	}
	
	public static void readInts(byte[] src, int pointer, int[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i] = readInt(src, pointer);
			pointer += Type.getSize(Type.INTEGER);
		}
	}
	
	public static void readLongs(byte[] src, int pointer, long[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i] = readLong(src, pointer);
			pointer += Type.getSize(Type.LONG);
		}
	}
	
	public static void readFloats(byte[] src, int pointer, float[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i] = readFloat(src, pointer);
			pointer += Type.getSize(Type.FLOAT);
		}
	}
	
	public static void readDoubles(byte[] src, int pointer, double[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i] = readDouble(src, pointer);
			pointer += Type.getSize(Type.DOUBLE);
		}
	}
	
	public static void readBooleans(byte[] src, int pointer, boolean[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i] = readBoolean(src, pointer);
			pointer += Type.getSize(Type.BOOLEAN);
		}
	}
	public static short readShort(byte[] src, int pointer) {
		return ByteBuffer.wrap(src, pointer, 2).getShort();
	}
	
	public static char readChar(byte[] src, int pointer) {
		return ByteBuffer.wrap(src, pointer, 2).getChar();
	}
	
	public static int readInt(byte[] src, int pointer) {
		return ByteBuffer.wrap(src, pointer, 4).getInt();
	}
	
	public static long readLong(byte[] src, int pointer) {
		return ByteBuffer.wrap(src, pointer, 8).getLong();
	}
	
	public static float readFloat(byte[] src, int pointer) {
		return Float.intBitsToFloat(readInt(src, pointer));
	}
	
	public static double readDouble(byte[] src, int pointer) {
		return Double.longBitsToDouble(readLong(src, pointer));
	}
	
	public static boolean readBoolean(byte[] src, int pointer) {
		assert(src[pointer] == 0 || src[pointer] == 1);
		return src[pointer] != 0;
	}
	
	public static String readString(byte[] src, int pointer, int length) {
		return new String(src, pointer, length);
	}
}
