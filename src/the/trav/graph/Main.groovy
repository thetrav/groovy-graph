package the.trav.graph

import java.awt.Color
import java.awt.Graphics;
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionListener

import javax.swing.JFrame
import javax.swing.JPanel

class Main {
	static int width = 800
	static int height = 600
	
	static int tileSize = 50
	
	static void main(String [] args) {
		JFrame frame = new JFrame("grooby fra,me")
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
		frame.setSize(width,height)
		
		frame.getContentPane().add(gridPanel())
		
		frame.setVisible(true)
	}
	
	static def snap(n) { n - (n%tileSize) }
	static def third = (int)(tileSize/3)
	
	static JPanel gridPanel() {
		GraphGrid graph = new GraphGrid(tileSize)
		def cursor = [x:0, y:0]
		
		def p1 = new Coord(snap(100), snap(100))
		def p2 = new Coord(snap(335), snap(500))
		def path = null
		
		def panel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				def dot = {g.fillOval(it.x+third, it.y+third, third, third)}
				def circle = {g.drawOval(it.x+third, it.y+third, third, third)}
				def line = {c1, c2 -> g.drawLine(c1.x+third, c1.y+third, c2.x+third, c2.y+third)}
				
				
				g.setColor(Color.black)
				g.fillRect(0,0,width, height)
				g.setColor(Color.yellow)
				dot(cursor)
				
				g.setColor(path ? Color.green : Color.white)
				dot(p1)
				g.setColor(path ? Color.green : Color.gray)
				dot(p2)
				
				g.setColor(Color.blue)
				graph.nodes.each {
					circle(it.key)
				}
				g.setColor(Color.red)
				graph.nodes.each {
					def c1 = it.key
					it.value.edges.each { c2 ->
						line(c1, c2)
					}
				}
				if(path) {
					g.setColor(Color.white)
					def last = path[0]
					path.each {
						line(last, it)
						last = it
					}
				}
				
			}
		}
		
		panel.addMouseMotionListener(new MouseMotionListener(){
			@Override
			void mouseMoved(MouseEvent e) {
				cursor.x = snap(e.getX())
				cursor.y = snap(e.getY())
				panel.repaint()
			}
			void mouseDragged(MouseEvent e) {}
		})
		
		panel.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				def x = cursor.x
				def y = cursor.y
				if(graph.get(x,y))
					graph.remove(x, y)
				else
					graph.add(x, y)
				path = graph.path(p1, p2)
			}
		})
		
		panel
	}
}
