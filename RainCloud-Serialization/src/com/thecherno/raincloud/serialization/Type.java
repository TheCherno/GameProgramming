package com.thecherno.raincloud.serialization;

public class Type {

	public static final byte UNKNOWN 	= 0;
	public static final byte BYTE 		= 1;
	public static final byte SHORT 		= 2;
	public static final byte CHAR 		= 3;
	public static final byte INTEGER	= 4;
	public static final byte LONG	 	= 5;
	public static final byte FLOAT		= 6;
	public static final byte DOUBLE		= 7;
	public static final byte BOOLEAN	= 8;
	
	public static int getSize(byte type) {
		switch (type) {
		case BYTE:		return 1;
		case SHORT:		return 2;
		case CHAR:		return 2;
		case INTEGER:	return 4;
		case LONG:		return 8;
		case FLOAT:		return 4;
		case DOUBLE:	return 8;
		case BOOLEAN:	return 1;
		}
		assert(false);
		return 0;
	}
	
	public static String typeToString(byte type) {
		switch (type) {
		case UNKNOWN:	return "Unknown";
		case BYTE:		return "Byte";
		case SHORT:		return "Short";
		case CHAR:		return "Char";
		case INTEGER:	return "Integer";
		case LONG:		return "Long";
		case FLOAT:		return "Float";
		case DOUBLE:	return "Double";
		case BOOLEAN:	return "Boolean";
		}
		return "Invalid";
	}
	
}
