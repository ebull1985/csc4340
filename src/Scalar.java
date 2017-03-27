public class Scalar {
	String type; // "col", "num", "str"
	Integer intValue;
	String strValue;
	Column colValue;

	public Scalar(String type, Integer value) {
		this.type = type;
		this.intValue = value;
	}
	
	public Scalar(String type, String value) {
		this.type = type;
		this.strValue = value;
	}
	
	public Scalar(String type, Column value) {
		this.type = type;
		this.colValue = value;
	}
	
	public String getType() {
		return type;
	}
	
	public Integer getIntValue() {
		return intValue;
	}
	
	public String getStrValue() {
		return strValue;
	}
	
	public String getValue() {
		if(type.equals("int")) return intValue.toString();
		else return strValue;
	}
	
	public Column getColValue() {
		return colValue;
	}
	
	public String toString() {
		switch(type) {
		case "str":
			return strValue;
		case "num":
			return intValue.toString();
		case "col":
			return colValue.getName();
		default:
			return "Invalid Type";
		}
		
	}
}
