package GamePckg;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Bird extends Pane implements Runnable {

    int count = 4;
    int columns = 4;
    int offsetX = 0;
    int offsetY = 6;
    int width = 64;
    int height = 64;
    
    public SpriteAnimation animation;
    Image birdImg = new Image(getClass().getResourceAsStream("bird.png"));
    ImageView bird = new ImageView(birdImg);
    Thread thread;
    
    public Bird(double X, double Y) {
    	thread = new Thread(this, "Bird");
		thread.start();
    	bird.setFitHeight(Game.PERS_SIZE);
    	bird.setFitWidth(Game.PERS_SIZE);
    	bird.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        animation = new SpriteAnimation(this.bird,
                                        Duration.millis(Character.PERS_ANIM_DURATION),
                                        count,
                                        columns,
                                        offsetX,
                                        offsetY,
                                        width,
                                        height);
        getChildren().addAll(this.bird);
        setTranslateX(X);
        setTranslateY(Y);
        Game.gameRoot.getChildren().add(this);
        animation.play();
    }

	@Override
	public void run() {
		AnimationTimer timer = new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				bird.setTranslateX(bird.getTranslateX() - 4);
				if(bird.getTranslateX() == 0)
					thread.interrupt();
			}
			
		};
		timer.start();
	}
	
}