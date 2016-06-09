package GamePckg;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Character extends Pane {

    int count = 3;
    int columns = 3;
    int offsetX = 96;
    int offsetY = 225;
    int width = 16;
    int height = 15;

    public SpriteAnimation animation;
    public Point2D playerVelocity = new Point2D(Game.VELOCITY_X, Game.STANDART_VELOCITY);
    private boolean canJump = true;
    private int health = 100;

    final static String PERS_RES = "mario.png";
    final static int PERS_ANIM_DURATION = 200;
    final static int MOVE_BY_ONE_PIXEL = 1;
    final static int JUMPING_VELOCITY = -30;
    final static int LAYOUT_TO_START = 0;
    final static int BULLET_FLYING_DURATION = 5;
    final static int BULLET_CENTER_AFTER_CATCH = 0;
    final static int BULLET_SIZE = 4;

    Image marioImg = new Image(getClass().getResourceAsStream(PERS_RES));
    ImageView imageView = new ImageView(marioImg);

    /*
     * 	Constructor of Character class. Gets a coords args. Create a Character object on main game pane, set moving animation.
     */
    public Character(int spawnX, int spawnY) {
        imageView.setFitHeight(Game.PERS_SIZE);
        imageView.setFitWidth(Game.PERS_SIZE);
        imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        animation = new SpriteAnimation(this.imageView,
                                        Duration.millis(PERS_ANIM_DURATION),
                                        count,
                                        columns,
                                        offsetX,
                                        offsetY,
                                        width,
                                        height);
        getChildren().addAll(this.imageView);
        setTranslateX(spawnX);
        setTranslateY(spawnY);
        Game.gameRoot.getChildren().add(this);

    }

    /*
     * 	Bot's moves on X axis. Includes intersection with blocks exceptions.
     */
    public void moveX(int value, boolean isBot) {
        boolean movingRight = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Block platform : Game.platforms) {
                if (this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (platform.IsBlockVisible()) {
                        if (movingRight) {
                            if (this.getTranslateX() + Game.PERS_SIZE == platform.getTranslateX()) {
                                if(isBot) {
                                    jumpPlayer();
                                }
                                this.setTranslateX(this.getTranslateX() - MOVE_BY_ONE_PIXEL);
                                return;
                            }
                        }
                        else {
                            if (this.getTranslateX() == platform.getTranslateX() + Game.BLOCK_SIZE) {
                                if(isBot) {
                                    jumpPlayer();
                                }
                                this.setTranslateX(this.getTranslateX() + MOVE_BY_ONE_PIXEL);
                                return;
                            }
                        }
                    }
                }
            }
            this.setTranslateX(this.getTranslateX() + (movingRight ? MOVE_BY_ONE_PIXEL : -MOVE_BY_ONE_PIXEL));
        }
    }
    
    /*
     * 	Bot's moves on Y axis. Includes intersection with blocks exceptions; adjusts fallings. 
     */
    public void moveY(int value) {
        boolean movingDown = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Block platform : Game.platforms) {
                if (getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingDown) {
                        if (this.getTranslateY() + Game.PERS_SIZE == platform.getTranslateY()) {
                            this.setTranslateY(this.getTranslateY() - MOVE_BY_ONE_PIXEL);
                            canJump = true;
                            return;
                        }
                    }
                    else {
                        if (platform.IsBlockVisible()) {
                            if(this.getTranslateY() == platform.getTranslateY() + Game.BLOCK_SIZE) {
                                this.setTranslateY(this.getTranslateY() + MOVE_BY_ONE_PIXEL);
                                playerVelocity = new Point2D(Game.VELOCITY_X, Game.STANDART_VELOCITY);
                                return;
                            }
                        }
                    }
                }
            }
            this.setTranslateY(this.getTranslateY() + (movingDown ? MOVE_BY_ONE_PIXEL : -MOVE_BY_ONE_PIXEL));
            if (this.getTranslateY() > Game.MAX_OFFSET) {
                this.setTranslateX(Game.SPAWN_X);
                this.setTranslateY(Game.SPAWN_Y);
                Game.gameRoot.setLayoutX(LAYOUT_TO_START);
            }
        }
    }
    
    /*
     * 	Adjusts characters moves on stairs.
     */
    public void downTheStairs() {
        for (int i = 0; i < Game.STEP; i++) {
            for (Block platform : Game.platforms) {
                if (this.getBoundsInParent().intersects(platform.getBoundsInParent())
                        || this.getTranslateY() + Game.PERS_SIZE + MOVE_BY_ONE_PIXEL == platform.getTranslateY()) {
                    if (!platform.IsBlockVisible()) {
                        this.setTranslateY(this.getTranslateY() + MOVE_BY_ONE_PIXEL);
                    }
                    else {
                        return;
                    }
                }
            }
        }
    }
    
    /*
     * 	Character's jumping. 
     */
    public void jumpPlayer() {
        if (canJump) {
            playerVelocity = playerVelocity.add(Game.VELOCITY_X, JUMPING_VELOCITY);
            canJump = false;
        }
    }
    
    /*
     * 	Adjusts character's shooting(includes creating and moving Bullet objects).
     */
    public void fire(Pane pane) {
        boolean movingRight = this.getScaleX() > 0;
        Circle bullet = new Circle(movingRight ? this.getTranslateX() + Game.PERS_SIZE : this.getTranslateX(),
                                   this.getTranslateY() + Game.PERS_SIZE / 2,
                                   BULLET_SIZE,
                                   Color.RED);
        pane.getChildren().add(bullet);
        moveBullet(pane, bullet, movingRight);
    }

    /*
     * 	Adjusts bullet moving and intersections with blocks and another characters.
     */
    public void moveBullet(Pane pane, Circle bullet, boolean movingRight) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(BULLET_FLYING_DURATION), event -> {
            bullet.setTranslateX(bullet.getTranslateX() + (movingRight ? Game.STEP : -Game.STEP));
            for(Block platform : Game.platforms) {
                if(bullet.getBoundsInParent().intersects(platform.getBoundsInParent()) &&
                platform.IsBlockVisible()) {
                    bullet.setCenterX(BULLET_CENTER_AFTER_CATCH);
                    bullet.setCenterY(BULLET_CENTER_AFTER_CATCH);
                    pane.getChildren().remove(bullet);
                }
            }
            for(Bots bot : Game.bots) {
                if(bullet.getBoundsInParent().intersects(bot.getBoundsInParent())) {
                    pane.getChildren().remove(bot);
                    bullet.setCenterX(BULLET_CENTER_AFTER_CATCH);
                    bullet.setCenterY(BULLET_CENTER_AFTER_CATCH);
                    pane.getChildren().remove(bullet);
                    bot.setTranslateY(Game.SCENE_Y);
                    return;
                }
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /*
     * 	Adjust bullet hitting by bots for character, decrease health points.
     */
    public void catchBullet() {
        this.health -= Game.bulletDamage;
    }

    /*
     * 	The health points getting.
     */
    public int getHealth() {
        return this.health;
    }
}