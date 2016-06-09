package GamePckg;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Menu extends Application {

    final static int LABELS_X = 220;
    final static int START_LABEL_Y = 220;
    final static int REPLAY_LABEL_Y = 340;
    final static int BOT_LABEL_Y = 470;
    final static int SETTINGS_LABEL_Y = 550;
    final static int EXIT_LABEL_Y = 630;
    final static int SCENE_X = 1024;
    final static int SCENE_Y = 800;
    final static int SCALE_CURSOR_OUT = 4;
    final static int SCALE_CURSOR_ON = 5;
    final static int EXIT_SUCCESS = 0;
    final static String IMAGE_RES = "ajxxsp.jpg";

    public static int level = 1;

    /*
     * 	Shows main game menu.
     */
    public static void MainMenu(Stage primaryStage) {

        primaryStage.setTitle("MARIO WARS");
        Group root = new Group();
        Scene scene = new Scene(root, SCENE_X, SCENE_Y);

        Label startlbl = new Label("Start game"),
              setlbl = new Label("Game settings"),
              exitlbl = new Label("Exit game"),
              botlbl = new Label("Bot play"),
              replbl = new Label("Replay of the\n   last game");
        
        replbl.setTranslateX(LABELS_X);
        replbl.setTranslateY(REPLAY_LABEL_Y);
        replbl.setTextFill(Color.WHITE);
        replbl.setScaleX(SCALE_CURSOR_OUT);
        replbl.setScaleY(SCALE_CURSOR_OUT);
        replbl.setOnMouseEntered(event -> {
        	replbl.setScaleX(SCALE_CURSOR_ON);
        	replbl.setScaleY(SCALE_CURSOR_ON);
        	replbl.setTextFill(Color.DARKGOLDENROD );
        });
        replbl.setOnMouseExited(event -> {
        	replbl.setScaleX(SCALE_CURSOR_OUT);
        	replbl.setScaleY(SCALE_CURSOR_OUT);
        	replbl.setTextFill(Color.WHITE);
        });
        replbl.setOnMouseClicked(event -> {
            root.getChildren().clear();
            Replay.getReplay(primaryStage);
        });
        
        botlbl.setTranslateX(LABELS_X);
        botlbl.setTranslateY(BOT_LABEL_Y);
        botlbl.setTextFill(Color.WHITE);
        botlbl.setScaleX(SCALE_CURSOR_OUT);
        botlbl.setScaleY(SCALE_CURSOR_OUT);
        botlbl.setOnMouseEntered(event -> {
            botlbl.setScaleX(SCALE_CURSOR_ON);
            botlbl.setScaleY(SCALE_CURSOR_ON);
            botlbl.setTextFill(Color.DARKGOLDENROD );
        });
        botlbl.setOnMouseExited(event -> {
            botlbl.setScaleX(SCALE_CURSOR_OUT);
            botlbl.setScaleY(SCALE_CURSOR_OUT);
            botlbl.setTextFill(Color.WHITE);
        });
        botlbl.setOnMouseClicked(event -> {
            root.getChildren().clear();
            Game G = new Game(level, true);
            G.start(primaryStage);
        });

        startlbl.setTranslateX(LABELS_X);
        startlbl.setTranslateY(START_LABEL_Y);
        startlbl.setTextFill(Color.WHITE);
        startlbl.setScaleX(SCALE_CURSOR_OUT);
        startlbl.setScaleY(SCALE_CURSOR_OUT);
        startlbl.setOnMouseEntered(event -> {
            startlbl.setScaleX(SCALE_CURSOR_ON);
            startlbl.setScaleY(SCALE_CURSOR_ON);
            startlbl.setTextFill(Color.DARKGOLDENROD );
        });
        startlbl.setOnMouseExited(event -> {
            startlbl.setScaleX(SCALE_CURSOR_OUT);
            startlbl.setScaleY(SCALE_CURSOR_OUT);
            startlbl.setTextFill(Color.WHITE);
        });
        startlbl.setOnMouseClicked(event -> {
            root.getChildren().clear();
            Game G = new Game(level, false);
            G.start(primaryStage);
        });

        setlbl.setTranslateX(LABELS_X);
        setlbl.setTranslateY(SETTINGS_LABEL_Y);
        setlbl.setTextFill(Color.WHITE);
        setlbl.setScaleX(SCALE_CURSOR_OUT);
        setlbl.setScaleY(SCALE_CURSOR_OUT);
        setlbl.setOnMouseEntered(event -> {
            setlbl.setScaleX(SCALE_CURSOR_ON);
            setlbl.setScaleY(SCALE_CURSOR_ON);
            setlbl.setTextFill(Color.DARKGOLDENROD );
        });
        setlbl.setOnMouseExited(event -> {
            setlbl.setScaleX(SCALE_CURSOR_OUT);
            setlbl.setScaleY(SCALE_CURSOR_OUT);
            setlbl.setTextFill(Color.WHITE);
        });
        setlbl.setOnMouseClicked(event -> {
            root.getChildren().clear();
            Settings.Set(primaryStage);
        });
        exitlbl.setTranslateX(LABELS_X);
        exitlbl.setTranslateY(EXIT_LABEL_Y);
        exitlbl.setTextFill(Color.WHITE);
        exitlbl.setScaleX(SCALE_CURSOR_OUT);
        exitlbl.setScaleY(SCALE_CURSOR_OUT);
        exitlbl.setOnMouseEntered(event -> {
            exitlbl.setScaleX(SCALE_CURSOR_ON);
            exitlbl.setScaleY(SCALE_CURSOR_ON);
            exitlbl.setTextFill(Color.DARKGOLDENROD );
        });
        exitlbl.setOnMouseExited(event -> {
            exitlbl.setScaleX(SCALE_CURSOR_OUT);
            exitlbl.setScaleY(SCALE_CURSOR_OUT);
            exitlbl.setTextFill(Color.WHITE);
        });
        exitlbl.setOnMouseClicked(event -> {
            System.exit(EXIT_SUCCESS);
        });

        Image img = new Image(IMAGE_RES);
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(SCENE_Y);
        imgView.setFitWidth(SCENE_X);

        root.getChildren().addAll(imgView, startlbl, setlbl, exitlbl, botlbl, replbl);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		MainMenu(primaryStage);
	}
}