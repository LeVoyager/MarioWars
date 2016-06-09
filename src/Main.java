import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application{
	 public static void main(String[] args) {
		 launch(args);   
	 }   
	 public void init(){     
		 //app init     . . .   
	 }   
	 
	 @Override   
	 public void start(Stage primaryStage) {   
		 primaryStage.setTitle("CAT SHOOTER");
		 Group root = new Group(); 
		 Scene scene = new Scene(root, 1024, 800); 

		 Label startlbl = new Label("Start game"),
				 setlbl = new Label("Game settings"),
				 exitlbl = new Label("Exit game");
		 
		 startlbl.setTranslateX(220);
		 startlbl.setTranslateY(220);
		 startlbl.setTextFill(Color.WHITE);
		 startlbl.setScaleX(4);
		 startlbl.setScaleY(4);
		 startlbl.setOnMouseEntered(event ->{
			 startlbl.setScaleX(5);
			 startlbl.setScaleY(5);
			 startlbl.setTextFill(Color.CADETBLUE );
		 });
		 startlbl.setOnMouseExited(event ->{
			 startlbl.setScaleX(4);
			 startlbl.setScaleY(4);
			 startlbl.setTextFill(Color.WHITE);
		 });
		 
		 setlbl.setTranslateX(220);
		 setlbl.setTranslateY(320);
		 setlbl.setTextFill(Color.WHITE);
		 setlbl.setScaleX(4);
		 setlbl.setScaleY(4);
		 setlbl.setOnMouseEntered(event ->{
			 setlbl.setScaleX(5);
			 setlbl.setScaleY(5);
			 setlbl.setTextFill(Color.CADETBLUE );
		 });
		 setlbl.setOnMouseExited(event ->{
			 setlbl.setScaleX(4);
			 setlbl.setScaleY(4);
			 setlbl.setTextFill(Color.WHITE);
		 });
		 
		 exitlbl.setTranslateX(220);
		 exitlbl.setTranslateY(420);
		 exitlbl.setTextFill(Color.WHITE);
		 exitlbl.setScaleX(4);
		 exitlbl.setScaleY(4);
		 exitlbl.setOnMouseEntered(event ->{
			 exitlbl.setScaleX(5);
			 exitlbl.setScaleY(5);
			 exitlbl.setTextFill(Color.CADETBLUE );
		 });
		 exitlbl.setOnMouseExited(event ->{
			 exitlbl.setScaleX(4);
			 exitlbl.setScaleY(4);
			 exitlbl.setTextFill(Color.WHITE);
		 });
		 exitlbl.setOnMouseClicked(event ->{
			 System.exit(0);
		 });
		 
		 Image img = new Image("image/ajxxsp.jpg");
		 ImageView imgView = new ImageView(img);
		 imgView.setFitHeight(800);
		 imgView.setFitWidth(1024);
		 
		 root.getChildren().addAll(imgView, startlbl, setlbl, exitlbl);
		 primaryStage.setScene(scene);       
		 primaryStage.show();
	 }
	 public void stop(){ 
	 }	
}