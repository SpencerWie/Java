package PAINT;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;


public class paint implements ActionListener{
	
	private JTextField fileName = new JTextField(), dir = new JTextField();
	static Label lblScrollValue = new Label("1");
	static Label lblAlphaValue = new Label("255");
	static JLabel lblFrame;
	int Frame = 0;
	JFrame frame;
	Canvas canvas;
	int Size = 1;
	Timer Delay;
	int delay = 100;
	Timer Sizer;
	ActionListener taskPerformer;
	ActionListener taskPerformer2;
	int check = 0;
	JMenu File = new JMenu("File");
    JMenuItem itemNew = new JMenuItem("New");
    JMenuItem itemSave = new JMenuItem("Save");
    JMenuItem itemLoad = new JMenuItem("Open");
	
	//text.frameArray = new ArrayList();
	public paint(){
			//setup timer
		taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	if(canvas.frame<canvas.frameArray.size()-1){
            		check++;
            		lblFrame.setText("Frame: "+(canvas.frame));
            		canvas.Step(check);
            		
            	}
            	if(canvas.frameArray.get(check)==null){//When a null value is found stop the timer. null values are ones the player never went to.
            		((Timer)evt.getSource()).stop();
            		Delay.stop();
            		lblFrame.setText("Frame: "+(canvas.frame));
            		//frame.setSize(2000,2000);
            		//Sizer.start();
            		check=0;
            	}
            }
        };
        //Wait 10 milisecond then readjust size
		taskPerformer2 = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	//frame.setSize(980,480);
            	check++;
            	if(check>1){
            		((Timer)evt.getSource()).stop();
            		Sizer.stop();
            		check=0;
            	}
            }
        };
        Sizer = new Timer(10,taskPerformer2);
        
        Delay = new Timer(delay,taskPerformer);
		
				//setup icon sizes
				int iconSize = 20;
				int toolIconSize = 23;
				int menuIconSize = 25;
				
				//setup Icon Imgs
				Icon clearIcon = new ImageIcon("clear.png");
				Icon pencilIcon = new ImageIcon("pencil.png");
				Icon brushIcon = new ImageIcon("brush.png");
				Icon fillIcon = new ImageIcon("fill.png");
				Icon magnifyIcon = new ImageIcon("magnify.png");
				Icon textIcon = new ImageIcon("text.png");
				Icon eyeDropperIcon = new ImageIcon("eyeDropper.png");
				Icon playIcon = new ImageIcon("play.png");
				Icon colorIcon = new ImageIcon("color.png");
				Icon onionIcon = new ImageIcon("onion.png");
				
				//Setup Canvas and Frame.
				frame = new JFrame("Ani");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Container window = frame.getContentPane();
				window.setLayout(new BorderLayout());
				canvas = new Canvas();
				
				canvas.addMouseListener(new MouseAdapter(){
					public void mouseReleased(MouseEvent e){
						if(canvas.zoom==1){
							frame.setSize(frame.getWidth()+50, frame.getHeight()+50);//Zoom in
							frame.validate();
							canvas.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							canvas.validate();
							frame.getContentPane().validate();
							canvas.zoom=0;
						}
						if(canvas.zoom==-1){
							frame.setSize(frame.getWidth()-50, frame.getHeight()-50);//Zoom out
							frame.validate();
							canvas.setSize(Toolkit.getDefaultToolkit().getScreenSize());
							canvas.validate();
							frame.getContentPane().validate();
							canvas.zoom=0;	
						}
					}
				});
				
				//window.add(canvas, BorderLayout.CENTER);
				
		        //Setup Menu
				JMenuBar menuBar = new JMenuBar();;
		        File.add(itemNew);
		        File.add(itemSave);
		        File.add(itemLoad);
		        menuBar.add(File);
		        itemNew.addActionListener(this);
		        itemSave.addActionListener(this);
		        itemLoad.addActionListener(this);
				
				lblScrollValue = new Label("1");
				
				Scrollbar sizeBar=new Scrollbar(Scrollbar.VERTICAL, 0, 1, 1, 100);//1 to 99.
				sizeBar.setValue(99);//The Value is reversed so 20 is 0.
				sizeBar.addAdjustmentListener(new AdjustmentListener(){
					public void adjustmentValueChanged(AdjustmentEvent e) {
						int value= e.getValue()*(-1)+100;
						System.out.println("Size: "+value);
						canvas.brushSize = value;
						String Value = Integer.toString(value);
						lblScrollValue.setText(Value);
					}
				});
				
				
				Scrollbar alphaBar=new Scrollbar(Scrollbar.VERTICAL, 0, 1, 1, 256);//1 to 255.
				alphaBar.setValue(255);//The Value is reversed so 20 is 0.
				alphaBar.addAdjustmentListener(new AdjustmentListener(){
					public void adjustmentValueChanged(AdjustmentEvent e) {
						int value= e.getValue();
						System.out.println("Alpha: "+value);
						canvas.setAlpha(value);
						String Value = Integer.toString(value);
						lblAlphaValue.setText(Value);
					}
				});
				
				JPanel rightPanel = new JPanel();
				rightPanel.setPreferredSize(new Dimension(32, 68));
				rightPanel.setMinimumSize(new Dimension(32, 68));
				rightPanel.setMaximumSize(new Dimension(100, 200));
				
				JPanel leftPanel = new JPanel();
				leftPanel.setPreferredSize(new Dimension(32, 68));
				leftPanel.setMinimumSize(new Dimension(32, 68));
				leftPanel.add(sizeBar);
				
				JPanel lowerPanel = new JPanel();
				lowerPanel.setLayout(new FlowLayout());
				lowerPanel.setPreferredSize(new Dimension(68, 32));
				lowerPanel.setMinimumSize(new Dimension(68, 32));
				
				//Play Menu btn's
				lblFrame = new JLabel("Frame: " +canvas.frame);
				
				JButton playButton = new JButton(playIcon);
				playButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						canvas.frame=0;						
						canvas.image = (BufferedImage)canvas.frameArray.get(canvas.frame);
						Delay.start();
					}
				});
				JButton leftButton = new JButton("<");
				leftButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						//frame.setSize(2000, 2000);
						//Size=1;//re-adjust size.
						canvas.Back();
						lblFrame.setText("Frame: "+(canvas.frame));
						//Sizer.start();
						Delay.stop();
					}
					public void mouseReleased(ActionEvent e){
						//frame.setSize(canvas.getSize().width, canvas.getSize().height);//600,380
					}
					public void mouseEntered(){
						//frame.setSize(canvas.getSize().width, canvas.getSize().height);//600,380
					}
				});
				JButton rightButton = new JButton(">");
				rightButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						Delay.stop();
						//frame.setSize(2000, 2000);
						//Size=1;
						canvas.Forward();
						lblFrame.setText("Frame: "+(canvas.frame));
						//Sizer.start();
					}
					public void mouseReleased(ActionEvent e){
						//frame.setSize(canvas.getSize().width, canvas.getSize().height);//600,380
					}
					public void mousePressed(ActionEvent e){
						//frame.setSize(canvas.getSize().width, canvas.getSize().height);//600,380
						//frame.setSize(2000, 2000);
						//Size=1;
						canvas.Forward();
						lblFrame.setText("Frame: "+(canvas.frame));
					}
				});
				
				//Item btn's
				JButton pencilButton = new JButton(pencilIcon);
				pencilButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						canvas.tool=1;
						System.out.println("Pencil: "+canvas.tool);
					}
				});
				
				JButton brushButton = new JButton(brushIcon);
				brushButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						canvas.tool=2;
						System.out.println("PaintBrush: "+canvas.tool);
					}
				});
				
				JButton fillButton = new JButton(fillIcon);
				fillButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						canvas.tool=3;
						System.out.println("Fill: "+canvas.tool);
					}
				});
				
				JButton magnifyButton = new JButton(magnifyIcon);
				magnifyButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						canvas.tool=4;
						System.out.println("Magnify: "+canvas.tool);
					}
				});
				
				JButton textButton = new JButton(textIcon);
				textButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						canvas.tool=5;
						System.out.println("Text: "+canvas.tool);
					}
				});
				
				JButton eyeDropperButton = new JButton(eyeDropperIcon);
				eyeDropperButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						canvas.tool=6;
						System.out.println("EyeDropper: "+canvas.tool);
					}
				});
				
				//Clear btn and Color btn's.
				JButton clearButton = new JButton(clearIcon);
				clearButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						canvas.Clear();
						System.out.println("Screen Cleared");
					}
				});
				
				JButton redButton = new JButton();
				redButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						canvas.red();
						System.out.println("Color: RED");
					}
				});
				
				JButton blackButton = new JButton();
				blackButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						canvas.black();
						System.out.println("Color: BLACK");
					}
				});
				
				JButton yellowButton = new JButton();
				yellowButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						canvas.yellow();
						System.out.println("Color: YELLOW");
					}
				});
				
				JButton blueButton = new JButton();
				blueButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						canvas.blue();
						System.out.println("Color: BLUE");
					}
				});
				
				JButton greenButton = new JButton();
				greenButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						canvas.green();
						System.out.println("Color: GREEN");
					}
				});
				
				JButton whiteButton = new JButton();
				whiteButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						canvas.white();
						System.out.println("Color: WHITE");
					}
				});
				JButton colorButton = new JButton(colorIcon);
				colorButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						canvas.color();
						Color color = canvas.graphics2D.getColor();
						System.out.println("Color: ("+color.getRed()+","+color.getGreen()+","+color.getBlue()+","+color.getAlpha()+")");
					}
				});
				JButton onionButton = new JButton(onionIcon);
				onionButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(canvas.onion==false){canvas.onion=true;System.out.println("OnionSkin on.");}
						else if(canvas.onion==true){canvas.onion=false;System.out.println("OnionSkin off.");}
					}
				});
				
				blackButton.setPreferredSize(new Dimension(iconSize, iconSize));
				redButton.setPreferredSize(new Dimension(iconSize, iconSize));
				blueButton.setPreferredSize(new Dimension(iconSize, iconSize));
				greenButton.setPreferredSize(new Dimension(iconSize,iconSize));
				whiteButton.setPreferredSize(new Dimension(iconSize,iconSize));
				yellowButton.setPreferredSize(new Dimension(iconSize,iconSize));
				clearButton.setPreferredSize(new Dimension(iconSize,iconSize));
				colorButton.setPreferredSize(new Dimension(iconSize,iconSize));
				
				pencilButton.setPreferredSize(new Dimension(toolIconSize,toolIconSize));
				brushButton.setPreferredSize(new Dimension(toolIconSize,toolIconSize));
				fillButton.setPreferredSize(new Dimension(toolIconSize,toolIconSize));
				magnifyButton.setPreferredSize(new Dimension(toolIconSize,toolIconSize));
				textButton.setPreferredSize(new Dimension(toolIconSize,toolIconSize));
				eyeDropperButton.setPreferredSize(new Dimension(toolIconSize,toolIconSize));
				onionButton.setPreferredSize(new Dimension(toolIconSize,toolIconSize));
				
				playButton.setPreferredSize(new Dimension(menuIconSize*2,menuIconSize));
				rightButton.setPreferredSize(new Dimension(menuIconSize*2,menuIconSize));
				leftButton.setPreferredSize(new Dimension(menuIconSize*2,menuIconSize));
				
				sizeBar.setPreferredSize(new Dimension(iconSize, iconSize*3+3));
				alphaBar.setPreferredSize(new Dimension(iconSize, iconSize*3+4));
				
				blackButton.setBackground(Color.BLACK);
				redButton.setBackground(Color.RED);
				blueButton.setBackground(Color.BLUE);
				greenButton.setBackground(Color.GREEN);
				yellowButton.setBackground(Color.YELLOW);
				whiteButton.setBackground(Color.WHITE);

				rightPanel.add(redButton);
				rightPanel.add(greenButton);
				rightPanel.add(blueButton);
				rightPanel.add(yellowButton);
				rightPanel.add(blackButton);
				rightPanel.add(whiteButton);
				rightPanel.add(colorButton);
				rightPanel.add(clearButton);
				rightPanel.add(sizeBar);
				rightPanel.add(lblScrollValue);
				rightPanel.add(alphaBar);
				rightPanel.add(lblAlphaValue);
				
				leftPanel.add(pencilButton);
				leftPanel.add(brushButton);
				leftPanel.add(fillButton);
				leftPanel.add(magnifyButton);
				leftPanel.add(textButton);
				leftPanel.add(eyeDropperButton);
				leftPanel.add(onionButton);
				
				lowerPanel.add(lblFrame);
				lowerPanel.add(leftButton);
				lowerPanel.add(playButton);
				lowerPanel.add(rightButton);
				
				window.add(rightPanel, BorderLayout.EAST);
				window.add(leftPanel, BorderLayout.WEST);
				window.add(lowerPanel, BorderLayout.SOUTH);
				
				frame.add(canvas, BorderLayout.CENTER);
				
				canvas.frameArray.add((canvas.image));
				frame.setSize(900, 500);//600,380
				Sizer.start();
				frame.setJMenuBar(menuBar);
				frame.setVisible(true);
				
	}
	public void actionPerformed(ActionEvent e) {
		Object pressed = e.getSource();
		if(pressed == itemLoad){
		System.out.println("Loading...");
		// Show the open dialog
	     JFileChooser chooser = new JFileChooser();
	     int selection = chooser.showOpenDialog(this.frame);
	     if (selection == JFileChooser.APPROVE_OPTION) {
	        fileName.setText(chooser.getSelectedFile().getName());
	        dir.setText(chooser.getCurrentDirectory().toString());
			lblFrame.setText("Frame: "+canvas.frame);
	  	  try {
	  		  //NOTE: The file MUST be in the same directory as the GuiTest Project.
			  File theFile = new File(fileName.getText());
			  Scanner input = new Scanner(theFile);
			  //while (input.hasNext()){
			  BufferedImage img = ImageIO.read(theFile);
			  if(img != null){
				  canvas.frameArray.add(img);
				  canvas.image = img;
				  canvas.image.getGraphics();
				  Sizer.start();
				  canvas.Forward();
				  canvas.Back();
			  }else{
				  System.out.println("There is nothing in this file. It's null.");
			  }
			  //} // end while
		  } catch (IOException e1){
			  System.out.println(e1.getMessage());
		  } // end try
	     }
	}
		if(pressed == itemSave){
			System.out.println("Saving...");
		     JFileChooser chooser = new JFileChooser();
		     int selection = chooser.showSaveDialog(this.frame);
		     if (selection == JFileChooser.APPROVE_OPTION) {
		        fileName.setText(chooser.getSelectedFile().getName());
		        dir.setText(chooser.getCurrentDirectory().toString());
				lblFrame.setText("Frame: "+canvas.frame);
				  canvas.Forward();
				  canvas.Back();
				try {
				    FileWriter outFile = new FileWriter(fileName.getText(), false);
				    Iterator imageWriters = ImageIO.getImageWritersByFormatName("GIF");
				    ImageWriter imageWriter = (ImageWriter) imageWriters.next();
				    File file = new File(fileName.getText());
				    ImageOutputStream ios = ImageIO.createImageOutputStream(file);
				    imageWriter.setOutput(ios);
				    //for(int i=0;i<text.frameArray.size();i++){ for animation saving.
				    BufferedImage img = (BufferedImage)canvas.image;
				    imageWriter.write(img);
				    //}
				
				} catch (IOException e0) {
					System.out.println(e0.getMessage());
				}
		     }
		}
		if(pressed == itemNew){
			canvas.CLEAR();
			lblFrame.setText("Frame: "+canvas.frame);
		}
	}
	//So we can change size from the Canvas class, which handles the Zoom events.
public static void main(String[] args){
	new paint();
}

}

