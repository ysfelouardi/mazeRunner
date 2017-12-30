

public class Case {
	int contents;
	int x;
	int y;
    double heuristicCost = 0; //Heuristic cost
    double finalCost = 0; //G+H
    double gcost = 0;
	Case parent;
    public double getGcost() {
		return gcost;
	}

	public void setGcost(int gcost) {
		this.gcost = gcost;
	}


	public Case getParent() {
		return parent;
	}

	public void setParent(Case parent) {
		this.parent = parent;
	}

	Case(){
		contents = 0;
		x = 0;
		y = 0;
		parent = null;
	}


	public int getContents() {
		return contents;
	}

	public void setContents(int contents) {
		this.contents = contents;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getHeuristicCost() {
		return heuristicCost;
	}

	public void setHeuristicCost(double d) {
		this.heuristicCost = d;
	}

	public double getFinalCost() {
		return finalCost;
	}

	public void setFinalCost(double d) {
		this.finalCost = d;
	}
	

	
	
	
}
