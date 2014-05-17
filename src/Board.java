import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener {

    private JLabel statusbar;
    private ArrayList<Tile> tiles;
    
    int mode;
    
    private Nation[] nations;
    private int nPick;
    
    private Timer timer;
    
    private int time;
    private int year;

    public Board(JLabel statusbar) {

        this.statusbar = statusbar;
        this.setLayout(null);
        mode = 0;
        
        setDoubleBuffered(true);

        addMouseListener(new TilesAdapter());
        
        tiles = new ArrayList<Tile>();
        nations = new Nation[6];
        nations[0] = new Nation("Assyrians", new Color(255, 0, 0)); nations[1] = new Nation("Babylonians", new Color(0, 255, 0));
        nations[2] = new Nation("Celts", new Color(0, 0, 255)); nations[3] = new Nation("Danes", new Color(255, 255, 0));
        nations[4] = new Nation("Eritreans", new Color(255, 0, 255)); nations[5] = new Nation("Fartfaces", new Color(0, 255, 255));
        nPick = 0;
        
        JButton buildMode = new JButton("Build Mode");
        buildMode.setActionCommand("BUILDMODE");
        buildMode.addActionListener(this);
        Dimension size = buildMode.getPreferredSize();
        buildMode.setBounds(590, 10, size.width, size.height);
        this.add(buildMode);
        
        JButton seedMode = new JButton("Seed Countries");
        seedMode.setActionCommand("SEEDMODE");
        seedMode.addActionListener(this);
        size = seedMode.getPreferredSize();
        seedMode.setBounds(570, 40, size.width, size.height);
        this.add(seedMode);
        
        JButton simMode = new JButton("Simulate");
        simMode.setActionCommand("SIM");
        simMode.addActionListener(this);
        size = simMode.getPreferredSize();
        simMode.setBounds(595, 70, size.width, size.height);
        this.add(simMode);
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 100, 25); 
    }
    
    @Override
    public void paintComponent(Graphics g) {
    	
    	for(int i = 0; i != tiles.size(); i++) { //draw tile and outline
    		if(tiles.get(i).isVisible()) {
	    		g.setColor(tiles.get(i).getColor());
	    		g.fillOval(tiles.get(i).getX(), tiles.get(i).getY(), tiles.get(i).getWidth(), tiles.get(i).getHeight());
    		}
    	}
    	
    	g.setColor(Color.gray);
    	g.fillRect(590, 150, 100, 110);
    	g.fillRect(590, 110, 100, 25);
    	g.fillRect(590, 270, 100, 110);
    	
    	g.setColor(Color.WHITE);
    	g.drawString("Populations", 595, 165);
    	g.drawString(nations[0].getGrowth() + "", 650, 125);
    	g.drawString("Year:" + year, 595, 125);
    	g.drawString("Tech", 595, 285);
    	g.drawString("Stability", 635, 285);
    	
    	int yyy = 185;
    	for(int i = 0; i != nations.length; i++) {
    		g.setColor(nations[i].getColor());
    		g.drawString(nations[i].getPop() + "", 595, yyy);
    		yyy += 13;
    	}
    	
    	yyy = 305;
    	for(int i = 0; i != nations.length; i++) {
    		
    			g.setColor(nations[i].getColor());
        		g.drawString(nations[i].getTech() + "", 595, yyy);
        		
        		g.setColor(nations[i].getColor());
        		g.drawString(round(nations[i].getStability(),2) + "", 635, yyy);
        		yyy += 13;
    		
    	}
    	
    	this.revalidate();
		this.repaint();
    	
    }
    
    @Override
	public void actionPerformed(ActionEvent e) {
    	String a = e.getActionCommand();
		
		if(a.equals("SEEDMODE")) {
			mode = 1;
		} else if(a.equals("BUILDMODE")) {
			mode = 0;
		} else if(a.equals("SIM")) {
			mode = 2;
		}
		
	}
    
    
    class TilesAdapter extends MouseAdapter {
        
        @Override
        public void mousePressed(MouseEvent e) { 
        	if(mode == 0) {
	        	if(e.getButton() == 2) { //single tile when left click
	        		int xx = e.getX(); int yy = e.getY();
	            	Tile t = new Tile(xx, yy);
	            	tiles.add(t);
	        	} else if(e.getButton() == 1) { //cluster of tiles when middle click
	        		Random gen = new Random();
	        		
	        		for(int i = 0; i != 40; i++) {
	        			int xx = gen.nextInt(40) + e.getX() - 20;
	        			int yy = gen.nextInt(40) + e.getY() - 20;
	        			
	        			Tile t = new Tile(xx, yy);
	                	tiles.add(t);
	        		}
	        	} else if(e.getButton() == 3) { //big cluster of tiles when right click
	        		Random gen = new Random();
	        		
	        		for(int i = 0; i != 300; i++) {
	        			int xx = gen.nextInt(100) + e.getX() - 50;
	        			int yy = gen.nextInt(100) + e.getY() - 50;
	        			
	        			Tile t = new Tile(xx, yy);
	                	tiles.add(t);
	        		}
	        	}
        	} else if(mode == 1) {
        		if(e.getButton() == 1) {
	        		for(int i = 0; i != tiles.size(); i++) {
	        			int x = tiles.get(i).getX();
	        			int y = tiles.get(i).getY();
	        			int width = tiles.get(i).getWidth();
	        			int height = tiles.get(i).getHeight();
	        			
	        			if(e.getX() > x && e.getX() < x + width) {
	        				if(e.getY() > y && e.getY() < y + height) {
	        					tiles.get(i).setOwner(nations[nPick]);
	        					tiles.get(i).setPop(1000);
	            			}
	        			}
	        		}
        		} else if(e.getButton() == 3) {
        			if(nPick != 5) {
        				nPick++;
        			} else {
        				nPick = 0;
        			}
        		}
        		
        	}
        	
            revalidate();
        	repaint();
        }
    }
    
    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
        	if(mode == 2) {
        		time++;
        		year = time/50;
        		
        		for(int i = 0; i != tiles.size(); i++) {
        			if(!tiles.get(i).getOwnerStr().equals("Neutral")) {
        				
        				//population increase
        				if(time%2 == 0) {
	        				double value = tiles.get(i).getValue(); 
	        				int p = tiles.get(i).getPop();
	        				
		        				double pop = (double) p;
		        				int j = 0;
		        				for(; j != nations.length; j++) {
		        					if(tiles.get(i).getOwnerStr().equals(nations[j].getName())) {
		        						break;
		        					}
		        				}
		        				double stability = nations[j].getStability();
		        				double adder = 0;
		        				if(p < 10000) {
			        				adder = pop*.001*stability*value;
		        				} else if(p > 100000) {
		        					adder = pop*.0001*stability*value;
		        				} else if(p > 1000000) {
		        					adder = pop*.00001*stability*value;
		        				}
		        				
		        				pop = pop+adder;
		        				
		        				int returnp = (int) pop;
		        				tiles.get(i).setPop(returnp);
	        				
        				}
        				//
        			}
        		}
        		
        		//count populations
        		nations[0].setPop(0); nations[1].setPop(0); nations[2].setPop(0); 
    			nations[3].setPop(0); nations[4].setPop(0); nations[5].setPop(0); 
        		for(int i = 0; i != tiles.size(); i++) {
        			if(!tiles.get(i).getOwnerStr().equals("Neutral")) {
        				for(int k = 0; k != 6; k++) {
        					if(tiles.get(i).getOwnerStr().equals(nations[k].getName())) {
        						 nations[k].addPop(tiles.get(i).getPop());
        						 break;
        					}}}}
        		//
        		
        		//advance tech etc
        		for(int i = 0; i != nations.length; i++) {
        			//adjust growth
        			if(time%50 == 0) {
	        			Random gen = new Random();
	        			double r = ((double)gen.nextInt(200) - 100) /1000;
	        			nations[i].setGrowth(nations[i].getGrowth() + r);
	        			if(nations[i].getGrowth() > 1) nations[i].setGrowth(0);
	        			if(nations[i].getGrowth() < -.4) nations[i].setGrowth(0);
	        			
        			}
        			//
        			
        			//adjust stability
        			if(time%20 == 0) {
	        			nations[i].setStability(nations[i].getStability() + nations[i].getGrowth());
	        			if(nations[i].getStability() > 3.5) {
	        				nations[i].setGrowth(-nations[i].getGrowth());
	        			}
	        			if(nations[i].getStability() < -1) {
	        				nations[i].setGrowth(-nations[i].getGrowth());
	        				nations[i].setStability(-1);
	        			}
        			}
        			//
        			
        			//adjust tech
        			if(nations[i].getStability() > 1.5) {
        				if(time%100 == 0) {
        					nations[i].setTech(nations[i].getTech() + 1);
        				}
        			}
        			
        		}
        		//
        		
        		//expand nations
        		for(int i = 0; i != tiles.size(); i++) {
        			if(!tiles.get(i).getOwnerStr().equals("Neutral")) {
	        			double homex = (double) tiles.get(i).getX();
	        			double homey = (double) tiles.get(i).getY();
	        			
	        			for(int j = 0; j != tiles.size(); j++) {
	        				if(i != j) {
	        					double targx = (double) tiles.get(j).getX();
	        					double targy = (double) tiles.get(j).getY();
	        					double distance = Math.sqrt(((homex - targx)*(homex - targx)) + ((homey - targy)*(homey - targy)));
	        					if(tiles.get(j).getOwner() == null) {
	        						
		        					if(distance < tiles.get(i).getOwner().getTech()*5) {
		        						if(tiles.get(i).getPop() > 2000) {
			        						tiles.get(j).setOwner(tiles.get(i).getOwner());
			        						tiles.get(j).setPop(1000);
			        						tiles.get(i).setPop(tiles.get(i).getPop() - 1000);
		        						}
		        						
		        					}
	        					} else if(!tiles.get(j).getOwner().equals(tiles.get(i).getOwner())) {
	        						if(distance < tiles.get(i).getOwner().getTech()) {
		        						int techDiff = tiles.get(i).getOwner().getTech() - tiles.get(j).getOwner().getTech();
		        						int popDiff = tiles.get(i).getPop() - tiles.get(j).getPop();
		        						//int nPopDiff = tiles.get(i).getOwner().getPop() - tiles.get(j).getOwner().getPop();
		        						
		        						if(techDiff > 2) {
		        							if(tiles.get(i).getOwner().getPop() > tiles.get(j).getOwner().getPop()) {
		        								
			        								tiles.get(j).setOwner(tiles.get(i).getOwner());
		        								
		        							}
		        						}
	        						}
	        					}
	        				}}}}
        		//
        	}
        	
            revalidate();
            repaint();
        }
    }
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

	
    
}