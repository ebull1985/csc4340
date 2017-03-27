
public class WhereNode {
	boolean negated;
	boolean nested;
	String nestingType; // "in" or "exists"
	SQLNode subQuery;
	//If not nested:
	String leftOperandType = null; // "col", "num", "str"
	Column leftOperandCol; // when type is col
	SQLNode leftResolveNode; // for col
	int leftResolveIndex; //for col, index into relations list
	String leftOperandValue; //used when type is "num" or "str"
	String comparison;
	String rightOperandType = null;
	Column rightOperandCol;
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
	
	public void setNested() {
		this.nested = true;
	}
	
	public void setNestingType(String nestingType) {
		this.nestingType = nestingType;
		this.nested = true;
	}
	
	public boolean getNested() {
		return this.nested;
	}
	
	public void setSubQuery(SQLNode subQuery) {
		this.subQuery = subQuery;
	}
	
	public SQLNode getSubQuery() {
		return this.subQuery;
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
	public void setLeftOperandCol(Column col) {
		this.leftOperandCol = col;
	}
	public Column getLeftOperandCol() {
		return this.leftOperandCol;
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
	public void setRightOperandCol(Column col) {
		this.rightOperandCol = col;
	}
	public Column getRightOperandCol() {
		return this.rightOperandCol;
	}
}
