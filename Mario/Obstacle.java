import java.awt.image.BufferedImage;

public class Obstacle implements Runnable{
    //location
    private int x;
    private int y;

    //type
    private int type;

    //displaying images
    private BufferedImage show = null;

    //nowBg
    private BackGround bg = null;

    //
    private Thread thread = new Thread(this);

    public Obstacle(int x, int y, int type, BackGround bg) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.bg = bg;
        //Get the image of the obstacle corresponding to this type
        show = StaticValue.obstacle.get(type);
        //if is flag, thread start
        if(type == 8){
            thread.start();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public BufferedImage getShow() {
        return show;
    }

    @Override
    public void run() {
        while(true){
            if(this.bg.isReach()){
                if(this.y < 374){
                    this.y += 5;
                }else{
                    this.bg.setBase(true);
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
