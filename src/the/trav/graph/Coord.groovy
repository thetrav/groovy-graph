package the.trav.graph

class Coord{
	int x
	int y
	Coord(x,y) {
		this.x = x
		this.y = y
	}
	
	@Override
	boolean equals(Object o) {
		o != null && o.hashCode() == hashCode()
	}
	
	@Override
	int hashCode() {
		toString().hashCode()
	}
	
	@Override
	String toString() {
		"(${x}, ${y})"
	}
}
