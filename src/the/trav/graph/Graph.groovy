package the.trav.graph

class Graph {
	
	def nodes = [:]
	
	def add(key, node) {
		nodes[key] = node
	}
	
	def get(key) {
		nodes[key]
	}
		
	def remove(key) {
		def node = get(key)
		node.edges.each {
			get(it).edges.remove(key)
		}
		nodes.remove(key)
	}
	
	def buildLayer(parent, inTree) {
		get(parent.node).edges.findAll { !inTree.contains(it) }.collect {
			inTree.add(it)
			[node:it, parent:parent]
		}
	}
	
	def buildPath(source, leaf) {
		def path = []
		path.add(leaf.node)
		while(leaf.parent.node != source) {
			leaf = leaf.parent
			path.add(leaf.node)
		}
		path.add(source)
		path.reverse()
	}
	
	//Breadth first tree search will find shortest path assuming unweighted graph
	def getPath(Coord source, Coord destination) {
		def inTree = [source]
		def layer = buildLayer([node:source], inTree)
		def end = layer.find(){it.node == destination}
		while(!end) {
			if(layer.isEmpty()) {
				return null
			}
			layer = layer.collect{
				def child = buildLayer(it, inTree)
				child
			}
			layer = layer.flatten()
			end = layer.find(){it.node == destination}
		}
		buildPath(source, end)
	}
	
	def path(a,b) {
		if(get(a) && get(b)) getPath(a,b) else null
	}
	
	class Node {
		def data
		def edges = []
		
		String toString() {
			"Node \n\tdata='${data.toString()}' \n\tedges=${edges}\n" 
		}
	}
	
	@Override
	String toString() {
		nodes.toString()
	}
}
