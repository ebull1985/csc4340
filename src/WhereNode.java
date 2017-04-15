
public class WhereNode {
	boolean negated;
	boolean nested;
	String nestingType; // "in" or "exists"
	SQLNode subQuery = null;
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
	
	
	
	public String getNestingType() {
		return this.nestingType;
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
	
	public SQLNode getLeftResolveNode() {
		return this.leftResolveNode;
	}
	public SQLNode getRightResolveNode() {
		return this.rightResolveNode;
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
	public void setLeftResolveNode(SQLNode sql) {
		this.leftResolveNode = sql;
	}
	public void setLeftResolveIndex(int i) {
		this.leftResolveIndex = i;
	}
	public Column getLeftOperandCol() {
		return this.leftOperandCol;
	}
	public int getLeftResolveIndex() {
		return this.leftResolveIndex;
	}
	
	
	
	public void setRightOperandType(String type) {
		this.rightOperandType = type;
	}
	public String getRightOperandType() {
		if(this.rightOperandType == null) return null;
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
	public void setRightResolveNode(SQLNode sql) {
		this.rightResolveNode = sql;
	}
	public void setRightResolveIndex(int i) {
		this.rightResolveIndex = i;
	}
	public Column getRightOperandCol() {
		return this.rightOperandCol;
	}
	public int getRightResolveIndex() {
		return this.rightResolveIndex;
	}
}
