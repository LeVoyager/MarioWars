package GamePckg;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Settings {

    final static int DIFFICULTY_LABELS_X = 250;
    final static int LABELS_X = 650;
    final static int STATS_LABEL_Y = 220;
    final static int BACK_LABEL_Y = 500;
    final static int LEVEL1_Y = 220;
    final static int LEVEL2_Y = 280;
    final static int LEVEL3_Y = 340;
    final static int SCALE_CURSOR_OUT = 4;
    final static int SCALE_CURSOR_ON = 5;
    final static int SCENE_X = 1024;
    final static int SCENE_Y = 800;
    final static int IMAGE_X = 415;
    final static int IMAGE_Y = 70;
    final static String IMAGE_RES = "128.png";

    /*
     * 	Shows settings game menu.
     */
    public static void Set(Stage window) {
        Group root = new Group();
        Scene scene = new Scene(root, SCENE_X, SCENE_Y);
        Label backlbl = new Label("Back"),
        lvl1 = new Label("Easy"),
        lvl2 = new Label("Hard"),
        lvl3 = new Label("Extreme"),
        statlbl = new Label("Game Stats");

        scene.setFill(Color.BLACK);
        
        statlbl.setTranslateX(LABELS_X);
        statlbl.setTranslateY(STATS_LABEL_Y);
        statlbl.setTextFill(Color.WHITE);
        statlbl.setScaleX(SCALE_CURSOR_OUT);
        statlbl.setScaleY(SCALE_CURSOR_OUT);
        statlbl.setOnMouseEntered(event -> {
        	statlbl.setScaleX(SCALE_CURSOR_ON);
        	statlbl.setScaleY(SCALE_CURSOR_ON);
        	statlbl.setTextFill(Color.DARKGOLDENROD );
        });
        statlbl.setOnMouseExited(event -> {
        	statlbl.setScaleX(SCALE_CURSOR_OUT);
        	statlbl.setScaleY(SCALE_CURSOR_OUT);
        	statlbl.setTextFill(Color.WHITE);
        });
        statlbl.setOnMouseClicked(event -> {
            Replay R = new Replay();
            try {
				R.stats(window);
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
        
        
        backlbl.setTranslateX(DIFFICULTY_LABELS_X);
        backlbl.setTranslateY(BACK_LABEL_Y);
        backlbl.setTextFill(Color.WHITE);
        backlbl.setScaleX(SCALE_CURSOR_OUT);
        backlbl.setScaleY(SCALE_CURSOR_OUT);
        backlbl.setOnMouseEntered(event -> {
            backlbl.setScaleX(SCALE_CURSOR_ON);
            backlbl.setScaleY(SCALE_CURSOR_ON);
            backlbl.setTextFill(Color.DARKGOLDENROD);
        });
        backlbl.setOnMouseExited(event -> {
            backlbl.setScaleX(SCALE_CURSOR_OUT);
            backlbl.setScaleY(SCALE_CURSOR_OUT);
            backlbl.setTextFill(Color.WHITE);
        });
        backlbl.setOnMousePressed(event -> {
            root.getChildren().clear();
            Menu.MainMenu(window);
        });
        lvl1.setTranslateX(DIFFICULTY_LABELS_X);
        lvl1.setTranslateY(LEVEL1_Y);
        if(Menu.level == 1) {
            lvl1.setTextFill(Color.RED);
        }
        else {
            lvl1.setTextFill(Color.WHITE);
        }
        lvl1.setScaleX(SCALE_CURSOR_OUT);
        lvl1.setScaleY(SCALE_CURSOR_OUT);
        lvl1.setOnMouseEntered(event -> {
            lvl1.setScaleX(SCALE_CURSOR_ON);
            lvl1.setScaleY(SCALE_CURSOR_ON);
            lvl1.setTextFill(Color.GREEN);
        });
        lvl1.setOnMouseExited(event -> {
            lvl1.setScaleX(SCALE_CURSOR_OUT);
            lvl1.setScaleY(SCALE_CURSOR_OUT);
            if(Menu.level == 1) {
                lvl1.setTextFill(Color.RED);
            }
            else {
                lvl1.setTextFill(Color.WHITE);
            }
        });
        lvl1.setOnMouseClicked(event -> {
            Menu.level = 1;
            lvl1.setTextFill(Color.RED);
            lvl2.setTextFill(Color.WHITE);
            lvl3.setTextFill(Color.WHITE);
        });

        lvl2.setTranslateX(DIFFICULTY_LABELS_X);
        lvl2.setTranslateY(LEVEL2_Y);
        if(Menu.level == 2) {
            lvl2.setTextFill(Color.RED);
        }
        else {
            lvl2.setTextFill(Color.WHITE);
        }
        lvl2.setScaleX(SCALE_CURSOR_OUT);
        lvl2.setScaleY(SCALE_CURSOR_OUT);
        lvl2.setOnMouseEntered(event -> {
            lvl2.setScaleX(SCALE_CURSOR_ON);
            lvl2.setScaleY(SCALE_CURSOR_ON);
            lvl2.setTextFill(Color.GREEN);
        });
        lvl2.setOnMouseExited(event -> {
            lvl2.setScaleX(SCALE_CURSOR_OUT);
            lvl2.setScaleY(SCALE_CURSOR_OUT);
            if(Menu.level == 2) {
                lvl2.setTextFill(Color.RED);
            }
            else {
                lvl2.setTextFill(Color.WHITE);
            }
        });
        lvl2.setOnMouseClicked(event -> {
            Menu.level = 2;
            lvl1.setTextFill(Color.WHITE);
            lvl2.setTextFill(Color.RED);
            lvl3.setTextFill(Color.WHITE);
        });

        lvl3.setTranslateX(DIFFICULTY_LABELS_X);
        lvl3.setTranslateY(LEVEL3_Y);
        if(Menu.level == 3) {
            lvl3.setTextFill(Color.RED);
        }
        else {
            lvl3.setTextFill(Color.WHITE);
        }
        lvl3.setScaleX(SCALE_CURSOR_OUT);
        lvl3.setScaleY(SCALE_CURSOR_OUT);
        lvl3.setOnMouseEntered(event -> {
            lvl3.setScaleX(SCALE_CURSOR_ON);
            lvl3.setScaleY(SCALE_CURSOR_ON);
            lvl3.setTextFill(Color.GREEN);
        });
        lvl3.setOnMouseExited(event -> {
            lvl3.setScaleX(SCALE_CURSOR_OUT);
            lvl3.setScaleY(SCALE_CURSOR_OUT);
            if(Menu.level == 3) {
                lvl3.setTextFill(Color.RED);
            }
            else {
                lvl3.setTextFill(Color.WHITE);
            }
        });
        lvl3.setOnMouseClicked(event -> {
            Menu.level = 3;
            lvl1.setTextFill(Color.WHITE);
            lvl2.setTextFill(Color.WHITE);
            lvl3.setTextFill(Color.RED);
        });

        Image img = new Image(IMAGE_RES);
        ImageView imgView = new ImageView(img);
        imgView.setX(IMAGE_X);
        imgView.setY(IMAGE_Y);
        backlbl.setOnMouseClicked(event -> window.close());
        root.getChildren().addAll(backlbl, imgView, lvl1, lvl2, lvl3, statlbl);
        window.setTitle("Settings");
        window.setScene(scene);
    }
}