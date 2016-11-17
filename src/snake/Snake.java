package snake;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.annotation.Generated;
import javax.swing.Timer;
import javax.swing.JFrame;

public class Snake implements ActionListener, KeyListener {
	public static Snake snake;
	public JFrame jframe;
	public RenderPanel renderPanel;
	public Timer timer = new Timer(10, this);
	public ArrayList<Point> snakeParts = new ArrayList<Point>(), stones= new ArrayList<Point>();
	public static final int UP=0, DOWN=1, LEFT=2, RIGHT=3, SCALE=10, SCALE1=20;
	public int ticks = 0, direction = DOWN, score, tailLength = 1, time;
	public Point head, cherry;
	public Random random;
	public boolean over = false, pause;
	public Dimension dim;
	
	public Snake(){
		//dim = Toolkit.getDefaultToolkit().getScreenSize();
		jframe = new JFrame("SNAKE");
		jframe.setVisible(true);
		jframe.setSize(800,600);
		jframe.setResizable(false);
		jframe.setLocationRelativeTo(null);
		//jframe.setLocation(dim.width/2-jframe.getWidth()/2,dim.height/2-jframe.getHeight()/2);
		jframe.add(renderPanel = new RenderPanel());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.addKeyListener(this);
		startGame();
		}
	public void startGame(){
		over = false;
		pause = false;
		time = 0;
		score = 0;
		tailLength = 1;
		direction = DOWN;
		head = new Point(0,0);
		random = new Random();
		snakeParts.clear();
		stones.clear();
		cherry = new Point(random.nextInt(70), random.nextInt(50));
		//for (int i=0; i<tailLength; i++){
			//snakeParts.add(new Point(head.x, head.y));
		timer.start();
	//}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println(head.x + ", "+ head.y);
		renderPanel.repaint();
		ticks++;
		if (ticks % 5 == 0 && head !=null && !over && !pause){
			time++;
			snakeParts.add(new Point(head.x, head.y));
			if (direction==UP)
				if (head.y-1>=0 && noTailAt(head.x, head.y-1))
				head = new Point(head.x, head.y-1);
				else over = true;
			if (direction==DOWN)
				if (head.y+1<57 && noTailAt(head.x, head.y+1))
				head = new Point(head.x, head.y+1);
				else over = true;
			if (direction==LEFT)
				if (head.x-1>=0 && noTailAt(head.x-1, head.y))
				head = new Point(head.x-1, head.y);
				else over = true;
			if (direction==RIGHT)
				if (head.x+1<79 && noTailAt(head.x+1, head.y))
				head = new Point(head.x+1, head.y);
				else over = true;
			
			if (snakeParts.size() > tailLength)
				snakeParts.remove(0);
			
			if (cherry !=null){
				if (head.equals(cherry)){
					score+=10;
					tailLength++;
					cherry.setLocation(random.nextInt(70), random.nextInt(50));
					stones.add(generateStone());
				}
			}
			for (Point stone : stones){
				if (head.equals(stone)){
					over=true;
					break;
				}
			}
		}
	}

	private boolean noTailAt(int x, int y) {
		for (Point point : snakeParts){
			if (point.equals(new Point(x,y))){
				return false;
			}
		}
		return true;
	}
	public Point generateStone(){
		int x,y;
		boolean overlapping;
		Point stone = new Point();
		do{
			overlapping = false;
			x = random.nextInt(70);
			y = random.nextInt(50);
			stone.setLocation(x, y);
			for (Point point : snakeParts){
				if (point.equals(stone)){
					overlapping = true;
					break;			
				}
			}
			if (head.equals(stone)){
				overlapping = true;
			}
			if (cherry.equals(stone)){
				overlapping = true;
			}
			for (Point point : stones){
				if (point.equals(stone)){
					overlapping = true;
					break;
				}
			}
		}
		while (overlapping);
		return stone;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		snake = new Snake();
	}
	@Override
public void keyPressed(KeyEvent e){	
	int i = e.getKeyCode();
	if (i == KeyEvent.VK_LEFT && direction != RIGHT)
		direction = LEFT;
	if (i == KeyEvent.VK_RIGHT && direction != LEFT)
		direction = RIGHT;
	if (i == KeyEvent.VK_UP && direction != DOWN)
		direction = UP;
	if (i == KeyEvent.VK_DOWN && direction != UP)
		direction = DOWN;
	if (i == KeyEvent.VK_SPACE)
		if (over)
	startGame();
		else
			pause =!pause;
	
}
public void keyReleased(KeyEvent e){
}
public void keyTyped(KeyEvent e){
	
}
}
