import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class MyFrame extends JFrame implements KeyListener, Runnable {
    //Store all backgrounds
    private List<BackGround> allBg = new ArrayList<>();
    private BackGround nowBg = new BackGround();
    //double buffering
    private Image offScreenImage = null;
    //Mario
    private Mario mario = new Mario();
    //Mario's movement
    private Thread thread = new Thread(this);

    public MyFrame(){
        //set window size
        this.setSize(800, 600);
        //Set the window to be centered
        this.setLocationRelativeTo(null);
        //Set the visibility of the window
        this.setVisible(true);
        //Set the program to end by clicking the close button on the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.addKeyListener(this);
        this.setTitle("SuperMario");

        //initialize pic
        StaticValue.init();

        //Initializing Mario
        mario = new Mario(10,355);

        //populate allbg
        for (int i = 1;i <= 3;i++) {
            allBg.add(new BackGround(i, i == 3 ? true : false));
        }

        //Set the first scene as the current scene
        nowBg = allBg.get(0);
        mario.setBackGround(nowBg);
        repaint();
        thread.start();
    }

    @Override
    public void paint(Graphics g) {
        if(offScreenImage == null){
            offScreenImage = createImage(800,600);
        }

        Graphics graphics = offScreenImage.getGraphics();
        graphics.fillRect(0,0, 800, 600);

        //paint bg
        graphics.drawImage(nowBg.getBgImage(),0, 0, this);

        //enemy
        for(Enemy e : nowBg.getEnemyList()){
            graphics.drawImage(e.getShow(), e.getX(), e.getY(),this);
        }

        //obstacle
        for(Obstacle ob: nowBg.getObstacleList()){
            graphics.drawImage(ob.getShow(), ob.getX(), ob.getY(), this);
        }

        //tower
        graphics.drawImage(nowBg.getTower(),620,270,this);

        //gan
        graphics.drawImage(nowBg.getGan(),500,220,this);

        //mario
        graphics.drawImage(mario.getShow(), mario.getX(), mario.getY(), this);

        //score
        Color c = graphics.getColor();
        graphics.setColor(Color.BLACK);
        graphics.drawString("score: " + mario.getScore(),300,100);
        graphics.setColor(c);

        //Drawing images to the window
        g.drawImage(offScreenImage, 0, 0, this);
    }

    public static void main(String[] args) {
        MyFrame myFrame = new MyFrame();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //when key pressed
    @Override
    public void keyPressed(KeyEvent e) {
        //Move to the right
        if(e.getKeyCode() == 39){
            mario.rightMove();
        }

        //Move to the left
        if(e.getKeyCode() == 37){
            mario.leftMove();
        }

        //jump
        if(e.getKeyCode() == 38){
            mario.jump();
        }
    }

    //when key released
    @Override
    public void keyReleased(KeyEvent e) {
        //left stop
        if(e.getKeyCode() == 37){
            mario.leftStop();
        }

        //right stop
        if(e.getKeyCode() == 39){
            mario.rightStop();
        }
    }

    @Override
    public void run() {
        while(true){
            repaint();
            try {
                Thread.sleep(50);

                if(mario.getX() >= 775){
                    nowBg = allBg.get(nowBg.getSort());
                    mario.setBackGround(nowBg);
                    mario.setX(10);
                    mario.setY(355);
                }

                //mario dead
                if(mario.isDeath()){
                    JOptionPane.showMessageDialog(this,"mario is dead");
                    System.exit(0);
                }
                //gameover?
                if(mario.isOk()){
                    JOptionPane.showMessageDialog(this,"pass game");
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
