package com.thecherno.raincloud.serialization;

import static com.thecherno.raincloud.serialization.SerializationUtils.*;

import java.util.ArrayList;
import java.util.List;

public class RCObject extends RCBase {

	public static final byte CONTAINER_TYPE = ContainerType.OBJECT;
	private short fieldCount;
	public List<RCField> fields = new ArrayList<RCField>();
	private short stringCount;
	public List<RCString> strings = new ArrayList<RCString>();
	private short arrayCount;
	public List<RCArray> arrays = new ArrayList<RCArray>();
	
	private RCObject() {
	}
	
	public RCObject(String name) {
		size += 1 + 2 + 2 + 2;
		setName(name);
	}
	
	public void addField(RCField field) {
		fields.add(field);
		size += field.getSize();
		
		fieldCount = (short)fields.size();
	}
	
	public void addString(RCString string) {
		strings.add(string);
		size += string.getSize();
		
		stringCount = (short)strings.size();
	}

	public void addArray(RCArray array) {
		arrays.add(array);
		size += array.getSize();
		
		arrayCount = (short)arrays.size();
	}
	
	public int getSize() {
		return size;
	}
	
	public RCField findField(String name) {
		for (RCField field : fields) {
			if (field.getName().equals(name))
				return field;
		}
		return null;
	}
	
	public RCString findString(String name) {
		for (RCString string : strings) {
			if (string.getName().equals(name))
				return string;
		}
		return null;
	}

	public RCArray findArray(String name) {
		for (RCArray array : arrays) {
			if (array.getName().equals(name))
				return array;
		}
		return null;
	}
	
	public int getBytes(byte[] dest, int pointer) {
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLength);
		pointer = writeBytes(dest, pointer, name);
		pointer = writeBytes(dest, pointer, size);
		
		pointer = writeBytes(dest, pointer, fieldCount);
		for (RCField field : fields)
			pointer = field.getBytes(dest, pointer);
		
		pointer = writeBytes(dest, pointer, stringCount);
		for (RCString string : strings)
			pointer = string.getBytes(dest, pointer);
		
		pointer = writeBytes(dest, pointer, arrayCount);
		for (RCArray array : arrays)
			pointer = array.getBytes(dest, pointer);
		
		return pointer;
	}
	
	public static RCObject Deserialize(byte[] data, int pointer) {
		byte containerType = data[pointer++];
		assert(containerType == CONTAINER_TYPE);
		
		RCObject result = new RCObject();
		result.nameLength = readShort(data, pointer);
		pointer += 2;
		result.name = readString(data, pointer, result.nameLength).getBytes();
		pointer += result.nameLength;
		
		result.size = readInt(data, pointer);
		pointer += 4;
		
		// Early-out: pointer += result.size - sizeOffset - result.nameLength;
		
		result.fieldCount = readShort(data, pointer);
		pointer += 2;
		
		for (int i = 0; i < result.fieldCount; i++) {
			RCField field = RCField.Deserialize(data, pointer);
			result.fields.add(field);
			pointer += field.getSize();
		}
		
		result.stringCount = readShort(data, pointer);
		pointer += 2;

		for (int i = 0; i < result.stringCount; i++) {
			RCString string = RCString.Deserialize(data, pointer);
			result.strings.add(string);
			pointer += string.getSize();
		}

		result.arrayCount = readShort(data, pointer);
		pointer += 2;

		for (int i = 0; i < result.arrayCount; i++) {
			RCArray array = RCArray.Deserialize(data, pointer);
			result.arrays.add(array);
			pointer += array.getSize();
		}
		
		return result;
	}
	
}
