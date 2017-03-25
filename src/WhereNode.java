
public class WhereNode {
	boolean negated;
	boolean nested;
	String nestingType; // "in" or "exists"
	SQLNode subQuery;
	//If not nested:
	String leftOperandType; // "col", "num", "str"
	String leftOperandName; // when type is col
	SQLNode leftResolveNode; // for col
	int leftResolveIndex; //for col, index into relations list
	String leftOperandValue; //used when type is "num" or "str"
	String comparison;
	String rightOperandType;
	String rightOperandName;
	SQLNode rightResolveNode;
	int rightResolveIndex;
	String rightOperandValue;
	
	public WhereNode() {
		this.negated = false;
	}
	
	public void setNegated() {
		this.negated = true;
	}
}
