package main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class Main 
{
	public static JFrame window;
	
	public static void main(String[] args) 
	{
		
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);//can't  change different size window
		window.setTitle("Game Project");//set up title
	//	window.setUndecorated(true);//hide button exist above window
		//new Main().setIcon();
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel); 
		
		gamePanel.config.loadConfig();
		if (gamePanel.fullScreenOn == true)
		{
			window.setUndecorated(true);
		}
		
		window.pack();//đóng gói to see screen
		
		window.setLocationRelativeTo(null);//show window middle screen
		window.setVisible(true);//to see window so make this class 
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}
	
	public void setIcon()
	{
		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("player/boy_down_1.png"));
		window.setIconImage(icon.getImage());
	}
}
