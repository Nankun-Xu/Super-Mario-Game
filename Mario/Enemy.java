import java.awt.image.BufferedImage;

public class Enemy implements Runnable{
    private int x,y;
    private int type;
    private boolean face_to = true;//go left
    private BufferedImage show;
    private BackGround bg;//Define a background object

    //The limit range of cannibal flower movement
    private int max_up = 0;
    private int max_down = 0;
    private Thread thread = new Thread(this);
    //Current picture status
    private int image_type = 0;

    //mushroom enemies
    public Enemy(int x, int y, int type, boolean face_to, BackGround bg) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.face_to = face_to;
        this.bg = bg;
        show = StaticValue.mogu.get(0);
        thread.start();
    }

    //Piranha Enemies
    public Enemy(int x, int y, int type, boolean face_to, BackGround bg, int max_up, int max_down) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.face_to = face_to;
        this.bg = bg;
        this.max_up = max_up;
        this.max_down = max_down;
        show = StaticValue.flower.get(0);
        thread.start();
    }

    //method of death
    public void death(){
        show = StaticValue.mogu.get(2);
        this.bg.getEnemyList().remove(this);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getShow() {
        return show;
    }

    public int getType() {
        return type;
    }

    @Override
    public void run() {
        while(true){
            //Determine if it is a mushroom enemy
            if(type == 1){
                if(face_to){
                    this.x -= 2;
                }else{
                    this.x += 2;
                }
                image_type = image_type == 1 ? 0 : 1;
                show = StaticValue.mogu.get(image_type);
            }

            //
            boolean canLeft = true;
            boolean canRight = true;

            for(int i = 0; i < bg.getObstacleList().size(); i++){
                Obstacle ob1 = bg.getObstacleList().get(i);
                //can right?
                if(ob1.getX() == this.x + 36 && (ob1.getY() + 65 > this.y && ob1.getY() - 35 < this.y)){
                    canRight = false;
                }

                //can left? Indicates an obstacle on the left
                if(ob1.getX() == this.x - 36 && (ob1.getY() + 65 > this.y && ob1.getY() - 35 < this.y)){
                    canLeft = false;
                }
            }
            if(face_to && !canLeft || this.x == 0){
                face_to = false;
            }
            else if((!face_to) && (!canRight) || this.x == 764){
                face_to = true;
            }

            //flower?
            if(type == 2){
                if(face_to){
                    this.y -= 2;
                }else{
                    this.y += 2;
                }

                image_type = image_type == 1 ? 0 : 1;

                //boundary
                if(face_to && (this.y == max_up)){
                    face_to = false;
                }
                if((!face_to) && (this.y == max_down)){
                    face_to = true;
                }
                show = StaticValue.flower.get(image_type);
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
