package PAINT;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.colorchooser.*;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

class Canvas extends JComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 968933519575047364L;
	//Plans: *Onionskin,Square, Circle tools, Selection tool ,FileIO*, Undo and Re-do*.
	//onion
	Color currentColor = Color.BLACK;
	int brushSize = 0;
	int tool = 1;
	BufferedImage image;
	Image LastImage;
	//BufferedImage buffImage;
	Graphics2D graphics2D;
	int currentX, currentY, lastX, lastY;
	int zoom=0;
	int frame=1;
	boolean onion = true;
	int MAX_FRAMES = 500;
	public ArrayList<Image> frameArray = new ArrayList<Image>();


	public Canvas(){
		for(int i=0;i<MAX_FRAMES;i++){
			frameArray.add(image);
		}
		setDoubleBuffered(true);
		//Cursor Test
		/*ImageIcon mouse = new ImageIcon("mouse.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image cursor = kit.getImage("icons/handwriting.gif"); //default cursor img.
		Cursor c = kit.createCustomCursor(cursor, new Point(this.getX(),this.getY()), mouse.toString());*/
		addMouseListener(new MouseListener(){
			public void mousePressed(MouseEvent e){
			if(tool==1 || tool==2  && e.getButton()==e.BUTTON1){
				lastX = e.getX();
				lastY = e.getY();
				}
			repaint();
			}
		
		
			public void mouseReleased(MouseEvent e){
				lastX = e.getX();
				lastY = e.getY();
				if(tool==4){
					if(e.getButton()==e.BUTTON1){//Left is clicked
						zoom=1;
						zoomIn();
						return;
					}else if(e.getButton()==e.BUTTON3){//Right is clicked
						zoom=-1;
						zoomOut();
						return;
					}
				}
				else if(tool==3){
					if(e.getButton()==e.BUTTON1){
						fill();
					}
				}
				else if(tool==5){
					if(e.getButton()==e.BUTTON1){
						text();
					}
				}
				else if(tool==6){
					if(e.getButton()==e.BUTTON1){
						changeColor();
					}
				}
			}

			public void mouseEntered(MouseEvent arg0) {
				// Nothing
				repaint();
				
			}


			public void mouseExited(MouseEvent arg0) {
				// Nothing
				repaint();
				
			}

			public void mouseClicked(MouseEvent e) {
				// Nothing
				repaint();
				
			}

		});
		
		addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseDragged(MouseEvent e){
				currentX = e.getX();
				currentY = e.getY();
				if(tool==1){
					graphics2D.setStroke(new BasicStroke(brushSize));
				}else if(tool==2){
					graphics2D.setStroke(new BasicStroke((float)brushSize,1,1,(float)brushSize/2));
				}
				if(tool==1 || tool==2){
					graphics2D.setPaint(currentColor);
					graphics2D.draw(new Line2D.Float(lastX, lastY, currentX, currentY));
					repaint();
					lastX = currentX;
					lastY = currentY;
				}
			}

		});
	}

	public void paintComponent(Graphics g){
		if(image == null){
			//graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			image = (BufferedImage)createImage(getSize().width, getSize().height);
			graphics2D = (Graphics2D)image.getGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Clear();
		}
		graphics2D = (Graphics2D)image.getGraphics();
		g.drawImage(image, 0, 0, null);
		/*if(LastImage!=null){
		graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		graphics2D = (Graphics2D)LastImage.getGraphics();
		g.drawImage(LastImage, 0, 0, null);
		graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		}*/
	}
	//Clear the frame
	public void Clear(){
		//Useful with smaller images when loaded. This will reset the image size to default.
		image = (BufferedImage)createImage(1300, 700);
		graphics2D = (Graphics2D)image.getGraphics();
		graphics2D.setPaint(Color.WHITE);
		graphics2D.fillRect(0, 0, 1300, 700);
		graphics2D.setPaint(Color.BLACK);
		repaint();
	}
	//Colors to choose from.
	public void red(){
		currentColor = Color.RED;
		repaint();
	}
	public void black(){
		currentColor = Color.BLACK;
		repaint();
	}
	public void blue(){
		currentColor = Color.BLUE;
		repaint();
	}
	public void green(){
		currentColor = Color.GREEN;
		repaint();
	}
	public void yellow(){
		currentColor = Color.YELLOW;
		repaint();
	}
	
	public void white(){
		currentColor = Color.WHITE;
		repaint();
	}

	public void color() {
		//Open windows color dialog on setPaint to that color.
		 Color theColor= JColorChooser.showDialog(this,"Choose A Color",getBackground());
	    if (theColor != null){
	      getRootPane().setBackground(theColor);
	      currentColor = theColor;
		  repaint();
	    }
	}
	public void zoomIn(){
		zoom=1;
		if(image.getWidth(this)<1500){//upper bounds
			//int Width= image.getWidth(this);
			//int Height =image.getHeight(this);
			Image tempImage = image.getScaledInstance(image.getWidth(this)+50, image.getHeight(this)+50, image.SCALE_SMOOTH);
			graphics2D.fillRect(0, 0, tempImage.getWidth(this)+50, tempImage.getHeight(this)+50);
			graphics2D.drawImage(tempImage, null, this);
		}
	}
	public void zoomOut(){
		if(image.getWidth(this)>450){//lower bounds
		Image tempImage = image.getScaledInstance(image.getWidth(this)-50, image.getHeight(this)-50, image.SCALE_SMOOTH);
		//graphics2D.setPaint(Color.WHITE);
		graphics2D.fillRect(0, 0, tempImage.getWidth(this)-50, tempImage.getHeight(this)-50);
		graphics2D.drawImage(tempImage, null, this);
		}
	}
	//Fill algorithm. For the current point look at the neighbors and neighbors neighbors. If those points are not the same color as the init one stop the fill in that direction.
	//For now just have it fill the screen a solid color.
	public void fill(){
		//get the Color of the first click, if on the current pixel that color does not match stop the fill for that pixel.
		/*BufferedImage tempImage = (BufferedImage)image;
		int Backcolor = tempImage.getRGB(currentX,currentY); 
		int fillColor = currentColor.getRGB();*/
		graphics2D.setPaint(currentColor);
		graphics2D.fillRect(0, 0, image.getWidth(this), image.getHeight(this));
	}
	//Prompts the user for what the text will contain, then draws the text at mouseX,mouseY pos.
	public void text(){
		String text = (String)JOptionPane.showInputDialog(this,"Enter Text: ","Customized Dialog",JOptionPane.PLAIN_MESSAGE, null,null, "text");
		if(text==null){text="";}//So error doesn't happen.
		Font font = new Font(Font.SANS_SERIF,brushSize*2+5,brushSize*2+5);
		graphics2D.setPaint(currentColor);
		graphics2D.setFont(font);
		graphics2D.drawString(text, lastX, lastY);
		repaint();
	}
	public void setAlpha(int val){
		Color alphaColor = new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), val);
		//graphics2D.setColor(alphaColor);
		currentColor = alphaColor;
	}
	public void Forward(){
		// Upper Frame Bounds 500
		if(frame < MAX_FRAMES){
		frameArray.set(frame, image);
		frame++; 
		if(frameArray.get(frame)!=null){
			image = (BufferedImage)frameArray.get(frame);
		}else{
			image = (BufferedImage)createImage(1300, 700);
			graphics2D = (Graphics2D)image.getGraphics();
			graphics2D.setPaint(Color.WHITE);
			graphics2D.fillRect(0, 0, 1300, 700);
			repaint();
		}
		frameArray.set(frame,image);
		//Can't get the graphics of a null object. So only get it if there is something there.
		if(image!=null){
		//graphics2D = (Graphics2D)buffImage.getGraphics();
		graphics2D = (Graphics2D)image.getGraphics();
		repaint();
		showLastFrame();
		}
		System.out.println(frameArray);
		}else{
			System.out.println("Frame Limit reached. If you wish to you can change it in the settings tab.");
		}
	}
	public void Back(){
		//Pressing back also creates a frame forward first as well so it doesn't pickup a null frame just in case. So when you press Back after play then play it again, it will be 1 frame more than the last play.
		//Lower Frame Bounds 0
		if(frame > 1){
		frameArray.set(frame, image);
		frame--;
		//frameArray.set(frame, image);
		if(image!=null){
			image = (BufferedImage)frameArray.get(frame);
			//buffImage = (BufferedImage)frameArray.get(frame);
			//graphics2D = (Graphics2D)buffimage.getGraphics();
			graphics2D = (Graphics2D)image.getGraphics();
		}
		//graphics2D.drawImage(buffImage,null, 0, 0);
		System.out.println(frameArray);
		repaint();
		showLastFrame();
		}
	}
	
	//Simlar to Forward, this goes through the frames efficiently.
	public void Step(int step){
		// Upper Frame Bounds 500
		if((frame < MAX_FRAMES) && (frameArray.get(step)!=null)){
		frame = step;
		//frameArray.set(frame, image);
		image = (BufferedImage)frameArray.get(step);
		//frameArray.set(frame,image);
		if(image!=null){
		graphics2D = (Graphics2D)image.getGraphics();
		repaint();
		}
		step++; 
		}else{
			//System.out.println("Frame Limit reached. If you wish to you can change it in the settings tab.");
		}
	}
	
	public void reset(){
		for(int i=0;i<MAX_FRAMES;i++){
			Image img = frameArray.get(i);
			img = createImage(getSize().width, getSize().height);
			graphics2D = (Graphics2D)img.getGraphics();
			graphics2D.setPaint(Color.WHITE);
			graphics2D.fillRect(0, 0, getSize().width, getSize().height);
			graphics2D.setPaint(Color.BLACK);
			repaint();
		}
	}
	public void CLEAR(){
		Clear();
		frame=1;
		frameArray.set(1, image);
		image = (BufferedImage)frameArray.get(1);
		//buffImage = (BufferedImage)frameArray.get(frame);
		//graphics2D = (Graphics2D)buffimage.getGraphics();
		graphics2D = (Graphics2D)image.getGraphics();
		//graphics2D.drawImage(buffImage,null, 0, 0);
		System.out.println(frameArray);
		repaint();
		for(int i=1;i<MAX_FRAMES;i++){
			frameArray.set(i, null);
		}
	}
	//For the eyedropper you need to click and drag for the effect. Just clicking wont work.
	public void changeColor(){
		BufferedImage tempImage = (BufferedImage)image;
		int color = tempImage.getRGB(currentX,currentY); 
		//did a easy operator trick here.
		int  redAmount   = (color & 0x00ff0000) >> 16;
		int  greenAmount = (color & 0x0000ff00) >> 8;
		int  blueAmount  =  color & 0x000000ff;
		currentColor =(new Color(redAmount,greenAmount,blueAmount));
	}
	
	public void showLastFrame(){
		/*if(frame>1){
		LastImage = (Image)frameArray.get(frame-1);
		graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		repaint();
		}*/
	}
}
