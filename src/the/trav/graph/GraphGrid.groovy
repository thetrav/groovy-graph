package the.trav.graph

class GraphGrid extends Graph {
	def tileSize
	
	def GraphGrid (tileSize) {
		this.tileSize = tileSize
	}

	def add(key) {
		def node = new Node()
		node.data = key
		(0..2).each {i ->
			def x = key.x - tileSize + tileSize*i
			(0..2).each { j ->
				def y = key.y - tileSize + tileSize*j
				def other = get(x,y)
				if(other) {
					node.edges.add(c(x,y))
					other.edges.add(key)
				}
		} }
		add(key, node)
	}
	
	def snap(n) { n - n%tileSize }
	
	def c(x, y) { new Coord(snap(x), snap(y)) }
	
	def add(int x, int y) { add(c(x,y)) }
	
	def get(x,y) { get(c(x,y)) }

	def remove(x,y) { remove(c(x,y)) }
	
	def path(x1, y1, x2, y2) { path(c(x1, y1), c(x2, y2)) }
	
	static void main(String [] args) {
		def g = new Graph(1)
		g.add(1, 1)
		println "get 1,1 ${g.get(1,1)}"
		g.add(2, 1)
		println "graph:\n${g}"
		println "1,1 edges ${g.get(1,1).edges}"
		
		
		g.add(2,2)
		g.add(3,3)
		g.add(4,4)
		println "graph:\n${g}"
		println "path ${g.path(1,1, 4,4)}"
		
		g.add(3,10)
		println "path ${g.path(1,1, 3,10)}"
		
		println "path ${g.path(10,10, 1,1)}"
		println "path ${g.path(1,1, 10,10)}"
	}
}
