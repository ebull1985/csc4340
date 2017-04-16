import java.util.ArrayList;

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
	
	public void setComparison(String c) {
		this.comparison = c;
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
	
	
	public Relation evaluate(Relation parent, String dbName) {
		Database db = new Database();
		db.initializeDatabase(dbName);
		
		//Comparison
		if (!nested) {
			String left;
			Relation leftRelation = parent;
			if (leftOperandType.equals("col")) {
				//If the left column doesn't exist in the relation, add it
				left = String.format("%s.%s", leftOperandCol.getPrefix(), leftOperandCol.getName());
				if (!leftRelation.attributeExists(left)) {
					leftRelation.displayRelation();
					System.out.println("Attr " + left + " did not exist. Adding it");
					leftRelation = db.getRelation(leftOperandCol.getPrefix());
					leftRelation.prefixColumnNames(leftOperandCol.getPrefix());
					ArrayList<String> lname = new ArrayList<String>();
					lname.add(left);
					leftRelation = parent.times(leftRelation.project(lname));
					leftRelation.displayRelation();
				}
				
			}
			else left = leftOperandValue;
			
			String right;
			if (rightOperandType.equals("col")) {
				//If the right column doesn't exist in the relation, add it
				right = String.format("%s.%s", rightOperandCol.getPrefix(), rightOperandCol.getName());
				if (!leftRelation.attributeExists(right)) {
					Relation rightRelation = db.getRelation(rightOperandCol.getPrefix());
					rightRelation.prefixColumnNames(rightOperandCol.getPrefix());
					ArrayList<String> rname = new ArrayList<String>();
					rname.add(right);
					leftRelation = parent.times(rightRelation.project(rname));
				}
			}
			else right = rightOperandValue;
			
			
			/*
			 * I'm not sure why I'm getting an array index out of bounds here.
			 * Some queries work, but others throw ArrayIndexOutOfBounds exceptions from the Tuple class.
			 * Looking at the parameters passed in to select(), I can't see a difference as to why some would break and some wouldn't.
			 */
			//parent.displayRelation();
			//String debug = String.format("%s, %s, %s, %s, %s", leftOperandType, left, comparison, rightOperandType, right);
			//System.out.println(debug);
			
			if (!negated) return leftRelation.select(leftOperandType, left, comparison, rightOperandType, right); //ArrayIndexOutOfBounds?? See above
			else return leftRelation.notSelect(leftOperandType, left, comparison, rightOperandType, right);
		}
		
		//In
		if (nested && nestingType.equals("in")) {
			String left;
			Relation leftRelation = parent;
			if (leftOperandType.equals("col")) {
				//If the left col doesn't exist in the relation, add it
				left = String.format("%s.%s", leftOperandCol.getPrefix(), leftOperandCol.getName());
				if (!leftRelation.attributeExists(left)) {
					leftRelation = db.getRelation(leftOperandCol.getPrefix());
					leftRelation.prefixColumnNames(leftOperandCol.getPrefix());
					ArrayList<String> lname = new ArrayList<String>();
					lname.add(left);
					leftRelation = parent.times(leftRelation.project(lname));
				}
			} else left = leftOperandValue;
			
			//Get the subQuery
			Relation subRelation = subQuery.evaluate(dbName);
			
			//parent.displayRelation();
			//String debug = String.format("%s, %s", leftOperandType, left);
			//System.out.println(debug);
			
			if (!negated) return leftRelation.selectionIn(leftOperandType, left, subRelation);
			else return leftRelation.notSelectionIn(leftOperandType, left, subRelation);
		}
		
		//Exists
		//Create a Relation from the subQuery
		if (nested && nestingType.equals("exists")) {
			Relation subRelation = subQuery.evaluate(dbName);
			//parent.displayRelation();
			//subRelation.displayRelation();
			Relation exists = parent.join(subRelation);
			if(!negated) return exists;	//I'm really shaky on my relational algebra but I think this is right...
			else return parent.minus(exists);
		}
		
		return null;
	}
	
}
