import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Window extends JFrame {

	private static final int NBR_ELEMENTS = 16;

	private static final long serialVersionUID = 1L;
	
	public final double X_EXTRA = 2;
	public final double Y_EXTRA = 1;
	public final int RIGHT_CLICK = 3;
	public final int LEFT_CLICK = 1;
	
	Dimension positionInScreen = null;
	
	BDearch Bsearch;
	
	Dimension path[][] = null;

	public Window(int width,int height,Maze maze){
		
		MazeArea mArea = new MazeArea(maze,height,width);
		
		JPanel menu = new JPanel();
		
		menu.setBackground(Color.gray);
		
		JPanel msg = new JPanel();
		
		JButton startAlgo = Window.createSimpleButton("Search");
		JButton saveMaze = Window.createSimpleButton("Save Maze");
		JButton clearMaz = Window.createSimpleButton("Clear");
		
		String[] algos = { "DFS", "BFS",  "ASTAR" };
		
		JMenuItem[] shapes = new JMenuItem[NBR_ELEMENTS];
		
		JComboBox<String> comboMenu = new JComboBox<String>(algos);
		
		comboMenu.setPreferredSize(new Dimension(100, 29));
	    
		JPopupMenu rightClickMenu = new JPopupMenu();
		
		JPopupMenu leftClickMenu = new JPopupMenu();
		
		JLabel msgLabel = new JLabel();
		
		
		// losque l'utilisateur change l'algo
		
		comboMenu.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        mArea.repaint();
		    }
		});
		
		// sauvgarder l'etat de labyrinthe
		
		saveMaze.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				maze.SaveMaze();
				
			}
		});
		
		// valider l'etat de la labyrinthe
		
		
		if(maze.mazeValidate())
		{
			msgLabel.setText("Maze status: "+"valide");
			
		}else{
			
			msgLabel.setText("Maze status: "+"not valide");
		}
		

		// les elements pour dessiner la labyrinthe
		
		
		for(int i=0;i<NBR_ELEMENTS;i++)
		{
			shapes[i] = new JMenuItem(new ImageIcon(new ImageIcon("asset/"+i+".png").getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
			
			shapes[i].addActionListener(new DrawActionListener(i){
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					maze.setValueAt(new Dimension(positionInScreen.width, positionInScreen.height), value);
					
					adapteOthers(new Dimension(positionInScreen.width, positionInScreen.height), maze);
					
					repaint();
					
					if(maze.mazeValidate())
					{
						msgLabel.setText("Maze status: "+"valide");
					}else{
						
						msgLabel.setText("Maze status: "+"not valide");
					}
					
				}
			});
			
			
			
			leftClickMenu.add(shapes[i]);
		}
		
		
		// Les elements du menu de clicDroite
		
		JMenuItem itemObstacle = new JMenuItem("Obstcle",new ImageIcon("asset/obstacle.png"));
	
		
		itemObstacle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Dimension obs = new Dimension(positionInScreen.width, positionInScreen.height);
				
				maze.addObstacle(obs);
				
				mArea.repaint();
				
			}
		});
		
		JMenuItem itemDepart = new JMenuItem("Depart",new ImageIcon("asset/player.png"));
		
		itemDepart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Dimension depart = new Dimension(positionInScreen.width, positionInScreen.height);
				
				maze.setDepart(depart);
				
				mArea.repaint();
				
			}
		});
		
		
		JMenuItem itemDestination = new JMenuItem("Destination",new ImageIcon("asset/win.png"));
		
		itemDestination.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Dimension destination = new Dimension(positionInScreen.width, positionInScreen.height);
				
				maze.setDestination(destination);
				
				
				mArea.repaint();
				
			}
		});
		
		
		rightClickMenu.add(itemDepart);
		rightClickMenu.add(itemDestination);
		rightClickMenu.add(itemObstacle);
		

		// Definire les propriétés de la fenétre
		
		this.setTitle("MazeRunner");
	    
	    this.setSize(406,500);
	    
	    this.setLocationRelativeTo(null);
	    
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    
	    this.setResizable(false);
	    
	    
	    // redesigner la labyrinthe
	    
	    clearMaz.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				repaint();
				
			}
		});
	    
	    
	    // gérer les evenements de combobox , pour savoir quelle algorithme sera executé 
	    
	    startAlgo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String selected = (String) comboMenu.getSelectedItem();
				
				
				if(maze.mazeValidate())
				{
						
				
					if(selected.equals("DFS"))
					{
						Bsearch =  new BDearch(maze,BDearch.DFS);
						path = Bsearch.getPredecessor();
						mArea.DrawPath(Bsearch.getSearchPath(),Bsearch.getMaxDepth(),BDearch.DFS);
						
					}else if(selected.equals("BFS"))
					{
						Bsearch =  new BDearch(maze,BDearch.BFS);					
						path = Bsearch.getPredecessor();				
						mArea.DrawPath(Bsearch.getSearchPath(),Bsearch.getMaxDepth(),BDearch.BFS);
					}
					else if(selected.equals("ASTAR"))
					{
						Bsearch =  new BDearch(maze,BDearch.ASTAR);					
						path = Bsearch.getPredecessor();				
						mArea.DrawPath(Bsearch.getSearchPath(),Bsearch.getMaxDepth(),BDearch.ASTAR);
					}
				
				}
				
			}
		});
	    
	    
	    // definir la position de depart et destination sur le labyrinthe 
	    
	    mArea.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {

				if(e.getButton() == RIGHT_CLICK)
				{
					rightClickMenu.show(e.getComponent(), e.getX(), e.getY());
					
					positionInScreen = new Dimension(e.getY(), e.getX());
					
				}else if( e.getButton() == LEFT_CLICK )
				{
					leftClickMenu.show(e.getComponent(), e.getX(), e.getY());
					
					positionInScreen = new Dimension(e.getY(), e.getX());
					
				}
							
				
			}
				
			
			@Override
			public void mousePressed(MouseEvent e) {
				

				
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				

				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
	    
	    
	    menu.add(comboMenu);
	    menu.add(startAlgo);
	    menu.add(clearMaz);
	    menu.add(saveMaze);
	    msg.add(msgLabel);
	    
	    this.getContentPane().add(menu, BorderLayout.NORTH);
	    this.getContentPane().add(mArea, BorderLayout.CENTER);
	    this.getContentPane().add(msg, BorderLayout.SOUTH);
	    
	    this.setVisible(true);
	  }
	

	
	public void adapteOthers(Dimension pos,Maze m)
	{
		pos = Maze.getMazeIndex(pos);
		
		Case [][] maze = m.getMaze();
		
		String binaryOfPosition = Maze.toBinary(maze[pos.width][pos.height].getContents());
		
		String[] splitOfPosition = binaryOfPosition.split("");
		
		String [] splitHolder;
		
		String stringHolder;
		
		int decimalValue;
		
		if( pos.height >  0)
		{
			
			
			String BinaryLeft = Maze.toBinary(maze[pos.width][pos.height - 1].getContents());
			
			splitHolder = BinaryLeft.split("");
			
			splitHolder[SearchEngine.RIGHT] = splitOfPosition[SearchEngine.LEFT];
			
			stringHolder = String.join("", splitHolder);
			
			decimalValue = Integer.parseInt(stringHolder, 2);
			
			maze[pos.width][pos.height - 1].setContents(decimalValue);
			
			System.out.println(decimalValue);
		}
		
		if( pos.height <  m.getHeight() - 1)
		{
			
			String BinaryLeft = Maze.toBinary(maze[pos.width][pos.height + 1].getContents());
			
			splitHolder = BinaryLeft.split("");
			
			splitHolder[SearchEngine.LEFT] = splitOfPosition[SearchEngine.RIGHT];
			
			stringHolder = String.join("", splitHolder);
			
			decimalValue = Integer.parseInt(stringHolder, 2);
			
			maze[pos.width][pos.height + 1].setContents(decimalValue);
			
			System.out.println(decimalValue);
		}
		
		if( pos.width <  m.getWidth() - 1)
		{
			
			String BinaryLeft = Maze.toBinary(maze[pos.width + 1][pos.height].getContents());
			
			splitHolder = BinaryLeft.split("");
			
			splitHolder[SearchEngine.TOP] = splitOfPosition[SearchEngine.BOTTOM];
			
			stringHolder = String.join("", splitHolder);
			
			decimalValue = Integer.parseInt(stringHolder, 2);
			
			maze[pos.width + 1][pos.height].setContents(decimalValue);
			
			System.out.println(decimalValue);
		}
		
		if(pos.width >  0 )
		{
			
			
			String BinaryLeft = Maze.toBinary(maze[pos.width - 1][pos.height].getContents());
			
			splitHolder = BinaryLeft.split("");
			
			splitHolder[SearchEngine.BOTTOM] = splitOfPosition[SearchEngine.TOP];
			
			stringHolder = String.join("", splitHolder);
			
			decimalValue = Integer.parseInt(stringHolder, 2);
			
			maze[pos.width - 1][pos.height].setContents(decimalValue);
			
			System.out.println(decimalValue);
		}
		
		
	}
	
	private static JButton createSimpleButton(String text) {
		  JButton button = new JButton(text);
		  button.setForeground(Color.BLACK);
		  button.setBackground(Color.WHITE);
		  Border line = new LineBorder(Color.BLACK);
		  Border margin = new EmptyBorder(5, 15, 5, 15);
		  Border compound = new CompoundBorder(line, margin);
		  button.setBorder(compound);
		  return button;
		}
	
	
	
	
}
