import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaticValue {
    //background
    public static BufferedImage bg = null;
    public static BufferedImage bg2 = null;

    //go left
    public static BufferedImage jump_L = null;

    //go right
    public static BufferedImage jump_R = null;

    //turn left
    public static BufferedImage stand_L = null;

    //turn right
    public static BufferedImage stand_R = null;

    //tower
    public static BufferedImage tower = null;

    //flag
    public static BufferedImage gan = null;

    //obstacle
    public static List<BufferedImage> obstacle = new ArrayList<>();

    //run left
    public static List<BufferedImage> run_L = new ArrayList<>();

    //run right
    public static List<BufferedImage> run_R = new ArrayList<>();

    //mushroom
    public static List<BufferedImage> mogu = new ArrayList<>();

    //flower
    public static List<BufferedImage> flower = new ArrayList<>();

    public static String path = System.getProperty("user.dir") + "/images/";

    //initialize
    public static void init(){

        try {
            //load bg
            bg = ImageIO.read(new File(path + "bg.png"));
            bg2 = ImageIO.read(new File(path + "bg2.png"));

            stand_L = ImageIO.read(new File(path + "s_mario_stand_L.png"));
            stand_R = ImageIO.read(new File(path + "s_mario_stand_R.png"));
            tower = ImageIO.read(new File(path + "tower.png"));
            gan = ImageIO.read(new File(path +"gan.png"));
            jump_L = ImageIO.read(new File(path + "s_mario_jump1_L.png"));
            jump_R = ImageIO.read(new File(path + "s_mario_jump1_R.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //run left
        for(int i = 1; i <= 2; i++){
            try {
                run_L.add(ImageIO.read(new File(path + "s_mario_run" + i +"_L.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //run right
        for(int i = 1; i <= 2; i++){
            try {
                run_R.add(ImageIO.read(new File(path + "s_mario_run" + i +"_R.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //load obstacle
        try {
            obstacle.add(ImageIO.read(new File(path + "brick.png")));
            obstacle.add(ImageIO.read(new File(path + "soil_up.png")));
            obstacle.add(ImageIO.read(new File(path + "soil_base.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        for(int i = 1; i <= 4; i++){
            try {
                obstacle.add(ImageIO.read(new File(path + "pipe" + i +".png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //load Fixed bricks and flags
        try {
            obstacle.add(ImageIO.read(new File(path + "brick2.png")));
            obstacle.add(ImageIO.read(new File(path + "flag.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //load mushroom
        for(int i = 1; i <= 3; i++){
            try {
                mogu.add(ImageIO.read(new File(path + "fungus" + i + ".png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //load flower
        for(int i = 1; i <= 2; i++){
            try {
                flower.add(ImageIO.read(new File(path + "flower1." + i + ".png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
