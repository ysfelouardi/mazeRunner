import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.JOptionPane;








public class BDearch extends SearchEngine {

	
    ArrayList<Dimension> L= new ArrayList<Dimension>(), T=new ArrayList<Dimension>();
    LinkedList<Dimension> file = new LinkedList<Dimension>();
    private Dimension predecessor[][];  
    protected Dimension [] searchPath = null;
    protected int maxDepth;
    boolean isSearching = true;
  	ArrayList <Case> openList, closeList;
	ArrayList <Case> path = new ArrayList <Case>();
    public final static int BFS = 0;
    public final static int DFS = 1;
    public final static int ASTAR = 2;

	
    Dimension currentLoc;

    boolean startSearching = false;
    
	
	public BDearch(Maze maze, int type) {
		super(maze);
		
		System.out.println(maze.getObstacles());
		
        if (searchPath == null) {
            searchPath = new Dimension[1000];
            for (int i=0; i<1000; i++) {
                searchPath[i] = new Dimension();
            }
        }
		
        searchPath[0] = maze.getDepart();
        
        predecessor = new Dimension[maze.getWidth()][maze.getHeight()];  
        for (int i=0; i<maze.getWidth(); i++) 
            for (int j=0; j<maze.getHeight(); j++) 
                predecessor[i][j] = null;
        
        System.out.println("depart "+maze.getDepart());
        
        file.add(maze.getDepart());
        T.add(maze.getDestination());
		
    
        
        if(type == 2){
        	doAstar();
        	//System.out.println(" path size = " + path.size());
    		if(path.size() == 0){
    			JOptionPane.showMessageDialog(null, "Pas De Chemin!", "InfoBox: " + "Info", JOptionPane.INFORMATION_MESSAGE);
    		}
        }else{
        	doBFS(type);
            maxDepth = 0;
            if (!isSearching) {
                searchPath[maxDepth++] = maze.getDestination();
                for (int i=0; i<100; i++) {
                    searchPath[maxDepth] = predecessor[searchPath[maxDepth - 1].width][searchPath[maxDepth - 1].height];
                    maxDepth++;
                    System.out.println(searchPath[maxDepth - 1] + " and start location = " + maze.getDepart() );
                    if (searchPath[maxDepth - 1].getWidth() == maze.getDepart().getWidth() && searchPath[maxDepth - 1].getHeight() == maze.getDepart().getHeight() ){
                    	
                    	break;  // back to starting node
                    }
                }
            }else{
            	JOptionPane.showMessageDialog(null, "Pas De Chemin!", "InfoBox: " + "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        
        //System.out.println("hahowa "+predecessor[maze.getDestination().width][maze.getDestination().height]);
        
        
 
        

            	            
        
	}
	
	
	private void doAstar(){
		//System.out.println(" i'm in Astar");
		openList = new ArrayList <Case>();
		closeList = new ArrayList <Case>();
		int xs = maze.getDepart().width;
		int ys = maze.getDepart().height;
		int xd = maze.getDestination().width;
		int yd = maze.getDestination().height;
		openList.add(maze.getMaze()[xs][ys]);
		
		while(!closeList.contains(maze.getMaze()[xd][yd])) {
			Case current = this.getCurrent();
			closeList.add(current);
			openList.remove(current);

			if(current == null) return;
			
			Dimension [] connected = getPossibleMoves(new Dimension(current.getX(),current.getY()));
			for (int i=0; i<connected.length; i++) {
				if(connected[i]!=null){
					Case node = maze.getMaze()[connected[i].width][connected[i].height];
					if(closeList.contains(node)) continue;
					if(!openList.contains(node)) {
						openList.add(node);
						node.setParent(current);
						node.setGcost(this.calculateG(node));
						/*Distance between node end end : sqrt((node.x-end.x)^2 + (node.y-end.y)^2))*/
						node.setHeuristicCost(Math.sqrt(Math.pow(node.getX() - xd, 2) + Math.pow(node.getY() - yd, 2)));
						node.setFinalCost(node.getGcost() + node.getHeuristicCost());
					} else {
						int g = this.calculateG(node);
						if(g < node.getGcost()) {
							node.setParent(current);
							/*Distance between node end end : sqrt((node.x-end.x)^2 + (node.y-end.y)^2))*/
							node.setHeuristicCost((Math.sqrt(Math.pow(node.getX() - xd, 2) + Math.pow(node.getY() - yd, 2))));
							node.setFinalCost(node.getGcost() + node.getHeuristicCost());
						}
						node.setGcost(g);
					}

					if(openList.size() == 0) return;
				}
			}


		}
		
		Case node = maze.getMaze()[xd][yd];
		while(node != maze.getMaze()[xs][ys]) {
			path.add(node);
			node = node.getParent();
		}
		

		/*The path from the start to go until arriving, so we reverse*/
		//Collections.reverse(path);
		maxDepth = 0;
		for(int i=0;i<path.size();i++)
		{
			int x = path.get(i).getX();
			int y = path.get(i).getY();
			searchPath[maxDepth] = new Dimension(x,y);
			maxDepth++;
			System.out.print(path.get(i).getX() + "," + path.get(i).getY() + "->");
		}
		
		
		
	}
	
	private Case getCurrent() {
		Case current = null;
		for(Case node : openList) {
			if(current == null || node.getFinalCost() < current.getFinalCost()) {
				current = node;
			}
		}
		return current;
	}
	
	private int calculateG(Case current) {
		int step = 0;
		Case node = current;
		int xs = maze.getDepart().width;
		int ys = maze.getDepart().height;
		while(node != maze.getMaze()[xs][ys]) {
			step++;
			node = node.getParent();	
		}
		return step;
	}
	
	private void doBFS(int type) {
        
        while (!file.isEmpty()) {
            
        	if(type == 1)
        		currentLoc = file.removeLast();
        	else if( type == 0)
        		currentLoc = file.removeFirst();
            
            
            if(T.contains(currentLoc)){
            	
                 System.out.println("But trouvé : (" + currentLoc.width +", " + currentLoc.height+")");
                 isSearching = false;
                 break;
             }
             else{
               
                Dimension [] connected = getPossibleMoves(currentLoc);
                  
                for (int i=0; i<connected.length; i++) {

                    if ( connected[i]!=null && !file.contains(connected[i]) && !L.contains(connected[i])) {
                        L.add(connected[i]);
                        predecessor[connected[i].width][connected[i].height] = currentLoc;
                        file.addLast(connected[i]);
                    }
                }
            }
        }      
        
    }
	
	
	public Dimension[][] getPredecessor(){
		return this.predecessor;
	}
   
	public Dimension[] getSearchPath(){
		return this.searchPath;
	}
	
	public int getMaxDepth(){
		return maxDepth;
	}
}
