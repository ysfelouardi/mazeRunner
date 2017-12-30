import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MazeArea extends JPanel {

	private static final long serialVersionUID = 1L;

	public final static int BLOCK_SIZE = 50;
	public final int EXTRA  = 10;
	
	private int heightP;
	private int widthP;
	
	Case[][] maze;
	
	Maze MazeRunner;
	
	public MazeArea(Maze maze,int height,int width) {
		
		this.setSize(width * BLOCK_SIZE, height * BLOCK_SIZE);
		
		this.heightP = height;
		this.widthP = width;
		this.maze =  maze.getMaze();
		MazeRunner = maze;
		
	}

	public void paintComponent(Graphics g){
	
		  for(int i=0;i<heightP;i++)
		  {
			  for(int j=0;j<widthP;j++)
			  {
				  String imgPath = "asset/"+maze[j][i].getContents()+".png";
				  try {
				      
					  Image img = ImageIO.read(new File(imgPath));
				  
				      g.drawImage(img, i*50, j*50, this);
				   
				  } catch (IOException e) {
				      e.printStackTrace();
				  } 
			  }
		  } 
		  
		  try {
		      
			  if(MazeRunner.getDestination() != null)
			  {
				  Image destination = ImageIO.read(new File("asset/win.png"));
				  
			      g.drawImage(
			    		  destination,
			    		  (MazeRunner.getDestination().height * BLOCK_SIZE ) + EXTRA , 
			    		  (MazeRunner.getDestination().width * BLOCK_SIZE ) + EXTRA , 
			    		  this);

			  }

		      if(MazeRunner.getDepart() != null)
		      {
			      Image depart = ImageIO.read(new File("asset/player.png"));
			      
			      g.drawImage(
			    		  depart,
			    		  (MazeRunner.getDepart().height * BLOCK_SIZE ) + EXTRA , 
			    		  (MazeRunner.getDepart().width * BLOCK_SIZE ) + EXTRA, 
			    		  this);
		      }

		      
		  } catch (IOException e) {
		      e.printStackTrace();
		  } 
		  
		  
		  LinkedList<Dimension> obstacles = MazeRunner.getObstacles();
		     
			for(Dimension obstacle : obstacles)
			{
						
				 try {
				      
					  Image img = ImageIO.read(new File("asset/obstacle.png"));
				  
					  g.drawImage(
				    		  img,
				    		  (obstacle.height * BLOCK_SIZE ) + EXTRA , 
				    		  (obstacle.width * BLOCK_SIZE ) + EXTRA , 
				    		  this);
				     
				   
				  } catch (IOException e) {
				      e.printStackTrace();
				  } 
			}
		  
	  }

	public void DrawPath(Dimension[] path,int maxDepth,int type)
	{
		Graphics g = this.getGraphics();

		        
     	if(path != null)
     	{
	        	 System.out.println("taille du path = " + maxDepth);
	        	 if(type == 2){
	        		 maxDepth -= 1; 
	        	 }else{
	        		maxDepth -= 2; 
	        	 }
	   	         for(int i = maxDepth; i >= 1 ; i--) {
	   	        	if(path[i] != null){
	   	   	          int x = path[i].width;
	   	   	          int y = path[i].height;
	   	   	          System.out.println(" x Path = " + x + " , y Path = " + y + " , " +  (maxDepth - i));
	   	   	          
						try {
				      
							  Image img = ImageIO.read(new File("asset/path.png"));
						  
							  g.drawImage(
					    		  img,
					    		  (y * BLOCK_SIZE ) + EXTRA , 
					    		  (x* BLOCK_SIZE ) + EXTRA , 
					    		  this);
						   
						  } catch (IOException e) {
						      e.printStackTrace();
						  } 
					
						 try {
							
							Thread.sleep(300);
							
						 } catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						 }
	   	        	
	   	        	}
	   	       
	   	         }
     	}
		
	}
	
	
	
	public int getHeightP() {
		return heightP;
	}

	public void setHeightP(int heightP) {
		this.heightP = heightP;
	}

	public int getWidthP() {
		return widthP;
	}

	public void setWidthP(int widthP) {
		this.widthP = widthP;
	}              

}
