
public class WhereNode {
	boolean negated;
	boolean nested;
	String nestingType; // "in" or "exists"
	SQLNode subQuery;
	//If not nested:
	String leftOperandType = null; // "col", "num", "str"
	String leftOperandName; // when type is col
	SQLNode leftResolveNode; // for col
	int leftResolveIndex; //for col, index into relations list
	String leftOperandValue; //used when type is "num" or "str"
	String comparison;
	String rightOperandType = null;
	String rightOperandName;
	SQLNode rightResolveNode;
	int rightResolveIndex;
	String rightOperandValue;
	
	public WhereNode() {
		this.negated = false;
		this.nested = false;
	}
	
	public void setNegated() {
		this.negated = true;
	}
	
	public void setNestingType(String nestingType) {
		this.nestingType = nestingType;
		this.nested = true;
	}
	
	public void setSubQuery(SQLNode subQuery) {
		this.subQuery = subQuery;
	}
	
	
	
	
	
	
	public void setLeftOperandType(String type) {
		this.leftOperandType = type;
	}
	public String getLeftOperandType() {
		return this.leftOperandType;
	}
	
	public void setLeftOperandValue(String value) {
		this.leftOperandValue = value;
	}
	
	public void setLeftOperandValue(Integer value) {
		this.leftOperandValue = value.toString();
	}
	public void setLeftOperandName(String name) {
		this.leftOperandName = name;
	}
	public String getLeftOperandName() {
		return this.leftOperandName;
	}
	
	
	
	public void setRightOperandType(String type) {
		this.rightOperandType = type;
	}
	public String getRightOperandType() {
		return this.rightOperandType;
	}
	
	public void setRightOperandValue(String value) {
		this.rightOperandValue = value;
	}
	
	public void setRightOperandValue(Integer value) {
		this.rightOperandValue = value.toString();
	}
	public void setRightOperandName(String name) {
		this.rightOperandName = name;
	}
	public String getRightOperandName() {
		return this.rightOperandName;
	}
}
