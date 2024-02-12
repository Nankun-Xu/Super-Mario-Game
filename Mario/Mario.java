import java.awt.image.BufferedImage;

public class Mario implements Runnable{
    private int x;
    private int y;
    private String status;
    //Display the image of the current status
    private BufferedImage show = null;
    //For obtaining obstacle information
    private BackGround backGround = new BackGround();
    //For implementing Mario's actions
    private Thread thread = null;
    //mario move
    private int xSpeed;
    //mario jump
    private int ySpeed;
    //Define Index
    private int index;
    //Mario's ascent time
    private int upTime = 0;
    //reach the beginning of tower
    private boolean isOk;
    //mario dead
    private boolean isDeath = false;
    //score
    private int score = 0;

    public Mario() {
    }

    public Mario(int x, int y) {
        this.x = x;
        this.y = y;
        show = StaticValue.stand_R;
        this.status = "stand--right";
        thread = new Thread(this);
        thread.start();
    }

    //mario dead
    public void death(){
        isDeath = true;
    }

    //move left
    public void leftMove(){
        //change speed
        xSpeed = -5;

        //reach flag
        if(backGround.isReach()){
            xSpeed = 0;
        }

        //Determine if it is in the air
        if(status.indexOf("jump") != -1){
            status = "jump--left";
        }else{
            status = "move--left";
        }
    }

    //move right
    public void rightMove(){
        xSpeed = 5;

        //reach flag
        if(backGround.isReach()){
            xSpeed = 0;
        }
        if(status.indexOf("jump") != -1){
            status = "jump--right";
        }else{
            status = "move--right";
        }
    }

    //Stop to the left
    public void leftStop(){
        xSpeed = 0;

        if(status.indexOf("jump") != -1){
            status = "jump--left";
        }else{
            status = "stop--left";
        }
    }

    //Stop to the right
    public void rightStop(){
        xSpeed = 0;
        if(status.indexOf("jump") != -1){
            status = "jump--right";
        }else{
            status = "stop--right";
        }
    }

    //mario jump
    public void jump(){
        if(status.indexOf("jump") == -1){
            if(status.indexOf("left") != -1){
                status = "jump--left";
            }else{
                status = "jump--right";
            }
            ySpeed = -10;
            upTime = 7;
        }

        //reach flag
        if(backGround.isReach()){
            ySpeed = 0;
        }
    }

    //mario fall
    public void fall(){
        if(status.indexOf("left") != -1){
            status = "jump--left";
        }else{
            status = "jump--right";
        }
        ySpeed = 10;
    }

    @Override
    public void run() {
        while(true){
            //on the obstacle?
            boolean onObstacle = false;

            //go right?
            boolean canRight = true;

            //go left?
            boolean canLeft = true;

            //reach flag && game over
            if(backGround.isFlag() && this.x >= 500){
                this.backGround.setReach(true);
                //flag drop completely
                if(this.backGround.isBase()){
                    status = "move--right";
                    if(x < 690){
                        x += 5;
                    }else{
                        isOk = true;
                    }
                }else{
                    if(y < 395){
                        xSpeed = 0;
                        this.y += 5;
                        status = "jump--right";
                    }

                    if(y > 395){
                        this.y = 395;
                        status = "stop--right";
                    }
                }

            }else {

                //
                for (int i = 0; i < backGround.getObstacleList().size(); i++) {
                    Obstacle ob = backGround.getObstacleList().get(i);

                    //on the obstacle?
                    if (ob.getY() == this.y + 25 && (ob.getX() > this.x - 30 && ob.getX() < this.x + 25)) {
                        onObstacle = true;
                    }

                    //jump up against the brick?
                    if ((ob.getY() >= this.y - 30 && ob.getY() <= this.y - 20) && (ob.getX() > this.x - 30 && ob.getX() < this.x + 25)) {
                        if (ob.getType() == 0) {
                            backGround.getObstacleList().remove(ob);
                            score += 1;
                        }
                        upTime = 0;
                    }

                    //go right?
                    if (ob.getX() == this.x + 25 && (ob.getY() > this.y - 30 && ob.getY() < this.y + 25)) {
                        canRight = false;
                    }


                    //go left?
                    if (ob.getX() == this.x - 30 && (ob.getY() > this.y - 30 && ob.getY() < this.y + 25)) {
                        canLeft = false;
                    }

                }

                //kill enemy and dead
                for(int i = 0; i < backGround.getEnemyList().size(); i++){
                    Enemy e = backGround.getEnemyList().get(i);

                    if(e.getY() == this.y + 20 && (e.getX() - 25 < this.x && e.getX() + 35 >= this.x)){
                        if(e.getType() == 1){
                            e.death();
                            score += 2;
                            upTime = 3;
                            ySpeed = -10;
                        }else if(e.getType() == 2){
                            //mario dead
                            death();
                        }
                    }
                    if((e.getX() + 35 > this.x && e.getX() - 25 < this.x) && (e.getY() + 35 > this.y && e.getY() - 20 < this.y)){
                        //mario dead
                        death();
                    }
                }


                //mario jump
                if (onObstacle && upTime == 0) {
                    if (status.indexOf("left") != -1) {
                        if (xSpeed != 0) {
                            status = "move--left";
                        } else {
                            status = "stop--left";
                        }
                    } else {
                        if (xSpeed != 0) {
                            status = "move--right";
                        } else {
                            status = "stop--right";
                        }
                    }
                } else {
                    if (upTime != 0) {
                        upTime--;
                    } else {
                        fall();
                    }
                    y += ySpeed;
                }
            }

            if((canLeft && xSpeed < 0) || (canRight && xSpeed > 0)){
                x += xSpeed;
                //Determine if the movement is to the far left of the screen
                if(x < 0){
                    x = 0;
                }
            }
            //Determines if the current state is moving
            if(status.contains("move")){
                index = index == 0 ? 1 : 0;
            }
            //move left?
            if("move--left".equals(status)){
                show = StaticValue.run_L.get(index);
            }

            //move right?
            if("move--right".equals(status)){
                show = StaticValue.run_R.get(index);
            }

            //stop left?
            if("stop--left".equals(status)){
                show = StaticValue.stand_L;
            }

            //stop right?
            if("stop--right".equals(status)){
                show = StaticValue.stand_R;
            }

            //jump left?
            if("jump--left".equals(status)){
                show = StaticValue.jump_L;
            }

            //jump right?
            if("jump--right".equals(status)){
                show = StaticValue.jump_R;
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getShow() {
        return show;
    }

    public void setShow(BufferedImage show) {
        this.show = show;
    }

    public void setBackGround(BackGround backGround) {
        this.backGround = backGround;
    }

    public boolean isOk() {
        return isOk;
    }

    public boolean isDeath() {
        return isDeath;
    }

    public int getScore() {
        return score;
    }
}
