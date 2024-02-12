import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround {
    private BufferedImage bgImage = null;
    private int sort;
    private boolean flag;
    private List<Obstacle> obstacleList = new ArrayList<>();//All Obstacles
    private BufferedImage gan = null;//All obstacles to determine whether Mario reaches the flagpole position
    private BufferedImage tower = null;
    //reach flag
    private boolean isReach = false;
    //drop flag
    private boolean isBase = false;
    //all enemy
    private List<Enemy>enemyList = new ArrayList<>();

    public BackGround(){

    }

    public BackGround(int sort, boolean flag) {
        this.sort = sort;
        this.flag = flag;

        if (flag) {
            bgImage = StaticValue.bg2;
        }else {
            bgImage = StaticValue.bg;
        }

        //Determine if it is the first level
        if(sort == 1){
            //Plotting the ground
            for(int i = 0; i < 27; i++){
                obstacleList.add(new Obstacle(i*30,420,1,this));
            }

            for(int j = 0; j <= 120; j += 30){
                for(int i = 0; i < 27 ; i++){
                    obstacleList.add(new Obstacle(i*30, 570-j, 2, this));
                }
            }

            //Draw brick A
            for(int i = 120; i <= 150; i += 30){
                obstacleList.add(new Obstacle(i,300,7,this));
            }

            //Draw brick B
            for(int i = 300; i <=570; i += 30){
                if(i == 360 || i == 300 || i == 480 || i ==510 || i == 540){
                    obstacleList.add(new Obstacle(i,300,7,this));
                }else{
                    obstacleList.add(new Obstacle(i,300,0,this));
                }
            }

            //Draw brick G
            for(int i = 420; i <= 450; i += 30){
                obstacleList.add(new Obstacle(i,240,7,this));
            }

            //Drawing water pipes
            for(int i = 360; i <= 600; i += 25){
                if(i == 360){
                    obstacleList.add(new Obstacle(620, i,3, this));
                    obstacleList.add(new Obstacle(645, i,4, this));
                }else{
                    obstacleList.add(new Obstacle(620, i, 5, this));
                    obstacleList.add(new Obstacle(645, i,6, this));
                }
            }

            //mushroom
            enemyList.add(new Enemy(580,385,1,true,this));

            //flower
            enemyList.add(new Enemy(635,420,2,true,this,328,428));
        }

        //To determine whether the second level
        if(sort == 2){
            for(int i = 0; i < 27; i++){
                obstacleList.add(new Obstacle(i*30,420,1,this));
            }

            for(int j = 0; j <= 120; j += 30){
                for(int i = 0; i < 27 ; i++){
                    obstacleList.add(new Obstacle(i*30, 570-j, 2, this));
                }
            }

            //pipe1
            for(int i = 360; i <= 600; i += 25){
                if(i == 360){
                    obstacleList.add(new Obstacle(60, i,3, this));
                    obstacleList.add(new Obstacle(85, i,4, this));
                }else{
                    obstacleList.add(new Obstacle(60, i, 5, this));
                    obstacleList.add(new Obstacle(85, i,6, this));
                }
            }

            //pipe2
            for(int i = 360; i <= 600; i += 25){
                if(i == 360){
                    obstacleList.add(new Obstacle(620, i,3, this));
                    obstacleList.add(new Obstacle(645, i,4, this));
                }else{
                    obstacleList.add(new Obstacle(620, i, 5, this));
                    obstacleList.add(new Obstacle(645, i,6, this));
                }
            }

            //
            obstacleList.add(new Obstacle(300,330,0,this));

            //
            for(int i = 270; i <= 330; i += 30){
                if(i == 270 || i == 330){
                    obstacleList.add(new Obstacle(i,360,0,this));
                }else{
                    obstacleList.add(new Obstacle(i,360,7,this));
                }
            }

            //
            for(int i = 240; i <= 360; i += 30){
                if(i == 240 || i == 360){
                    obstacleList.add(new Obstacle(i, 390,0,this));
                }else{
                    obstacleList.add(new Obstacle(i,390,7,this));
                }
            }

            //
            obstacleList.add(new Obstacle(240,300,0,this));

            //
            for(int i = 360; i <= 540; i += 60){
                obstacleList.add(new Obstacle(1,270,7,this));
            }

            //mushroom1
            enemyList.add(new Enemy(200,385,1,true,this));

            //mushroom2
            enemyList.add(new Enemy(500,385,1,true,this));

            //flower1
            enemyList.add(new Enemy(75,420,2,true,this,328,418));

            //flower2
            enemyList.add(new Enemy(635,420,2,true,this,298,388));
        }

        if(sort == 3){
            for(int i = 0; i < 27; i++){
                obstacleList.add(new Obstacle(i*30,420,1,this));
            }

            for(int j = 0; j <= 120; j += 30){
                for(int i = 0; i < 27 ; i++){
                    obstacleList.add(new Obstacle(i*30, 570-j, 2, this));
                }
            }

            //
            int temp = 290;
            for(int i = 390; i >= 270; i -= 30){
                for(int j = temp; j <= 410; j += 30){
                    obstacleList.add(new Obstacle(j,i,7,this));
                }
                temp += 30;
            }

            //
            temp = 60;
            for(int i = 390; i >= 360; i -= 30){
                for(int j = temp; j <= 90; j += 30){
                    obstacleList.add(new Obstacle(j,i,7,this));
                }
                temp += 30;
            }

            //gan
            gan = StaticValue.gan;
            tower = StaticValue.tower;

            obstacleList.add(new Obstacle(515,220,8,this));

            //mushroom
            enemyList.add(new Enemy(150,385,1,true,this));
        }
    }


    public BufferedImage getBgImage() {
        return bgImage;
    }

    public int getSort() {
        return sort;
    }

    public boolean isFlag() {
        return flag;
    }

    public List<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public BufferedImage getGan() {
        return gan;
    }

    public BufferedImage getTower() {
        return tower;
    }

    public boolean isReach() {
        return isReach;
    }

    public void setReach(boolean reach) {
        isReach = reach;
    }

    public boolean isBase() {
        return isBase;
    }

    public void setBase(boolean base) {
        isBase = base;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }
}
