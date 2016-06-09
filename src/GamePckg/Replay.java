package GamePckg;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Replay extends Application {

    private static final String COUNTER_FILE_NAME = "replays/counter.txt";
    final static double IS_SHOOT = -1.0;
    final static double ISNT_SHOOT = -2.0;
    final static double FINISH = -999.0;
    private static int counter;
    private static String path;
    private static FileOutputStream ous;
    private static DataOutputStream dos;
    private static FileInputStream ins;
    private static DataInputStream dis;
    public static ArrayList<Integer> nums = new ArrayList<>();
	public static ArrayList<Integer> steps = new ArrayList<>();
	public static ArrayList<Integer> shots = new ArrayList<>();
	private static ScalaSort stat;
	private static double averageShots;

	public static void replayRecordingInit() {
        try {
            FileReader fr = new FileReader(COUNTER_FILE_NAME);
            BufferedReader in = new BufferedReader(fr);
            try {
                path = "replays/Replay_";
                counter = Integer.parseInt(in.readLine());
                path += Integer.toString(counter);
                path += ".dat";
            } finally {
                in.close();
            }
            replayRecordingCounter();
            ous = new FileOutputStream(path);
            dos = new DataOutputStream(ous);
            dos.writeInt(Game.gameLevel);
            dos.flush();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void replayRecordingCounter() {
        try {
            String buffer;

            @SuppressWarnings("resource")
            FileWriter fw = new FileWriter(COUNTER_FILE_NAME, false);
            try {
                counter++;
                buffer = Integer.toString(counter);
                fw.write(buffer);
            } finally {
                fw.flush();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

	public static void gameConditionRecording(Character player, int flag, int index) {
        try {
            dos.writeDouble(player.getTranslateX());
            dos.writeDouble(player.getTranslateY());
            dos.writeDouble(player.getScaleX());
			if(flag == Game.CANT_SHOOT) {
				dos.writeDouble(ISNT_SHOOT);
			} else {
				dos.writeDouble(IS_SHOOT);
			}
			for(Bots bot : Game.bots) {
				dos.writeDouble(bot.getTranslateX());
	            dos.writeDouble(bot.getTranslateY());
	            dos.writeDouble(bot.getScaleX());
				if(Game.bots.indexOf(bot) == index) {
					dos.writeDouble(IS_SHOOT);
				} else {
					dos.writeDouble(ISNT_SHOOT);
				}
			}
			if(Game.isExit == true)
				dos.writeDouble(FINISH);
		} catch (IOException e) {
			e.printStackTrace();
	    }
    }
    
    public static void getReplay(Stage stage) {
    	try {
    		String path;
            FileReader fr = new FileReader(COUNTER_FILE_NAME);
            BufferedReader in = new BufferedReader(fr);
            try {
            	counter = Integer.parseInt(in.readLine()) - 1;
            	path = "replays/Replay_";
            	path += Integer.toString(counter);
                path += ".dat";
            } finally {
                in.close();
            }
            Game G = new Game();
            G.start(stage, path);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void stats(Stage stage) throws Exception {
    	Stage primaryStage = new Stage();
    	int statcounter = 1,
    		finishcounter,
    		avrg = 0;
    	try {
            FileReader fr = new FileReader(COUNTER_FILE_NAME);
            BufferedReader in = new BufferedReader(fr);
            try {
                finishcounter = Integer.parseInt(in.readLine());
                while(statcounter < finishcounter) {
                	path = "replays/Replay_";
                	path += Integer.toString(statcounter);
                    path += ".dat";
                    nums.add(statcounter);
                    getInfo(path);
                	statcounter++;
                }	
                averageShots /= finishcounter;
                System.out.println(averageShots);
            } finally {
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    	sortArrays();
    	Parent root = FXMLLoader.load(getClass().getResource("/GamePckg/main.fxml"));
    	primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle("Game stats");
        for(int i = 0; i < steps.size(); i++)
        	avrg += steps.get(i);
        Stage stg = new Stage();
        Label lbl = new Label();
        BorderPane pane = new BorderPane();
        lbl.setText("Average shots: " + averageShots + "\nAverage duration: " + avrg / steps.size() / 240);
        pane.setCenter(lbl);
        stg.setScene(new Scene(pane, 250, 60));
        stg.show();
        primaryStage.setScene(new Scene(root));
        primaryStage.showAndWait();
    }
    
    public void getInfo(String path) {
    	try {
    		Double temp = 0.0;
    		int stp = 0, sht = 0;
    		List<Double> action = new ArrayList<>();
			ins = new FileInputStream(path);
			dis = new DataInputStream(ins);
			dis.readInt();
			do {
				temp = dis.readDouble();
				action.add(temp);
				if(temp == -1.0)
					sht++;
				stp++;
			} while(temp != -999.0);
			steps.add(stp);				
			double[] array = new double[action.size()];
			for (int i = 0; i < action.size(); i++)
				array[i] = action.get(i);
			stat = new ScalaSort();
			averageShots += stat.getCountOfShots(array);
			//System.out.println(stat.getCountOfShots(array) / steps.size());
			shots.add(sht);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void sortArrays() {
    	long start, end;
        int[] array = new int[nums.size()];
        for (int j = 0; j < nums.size(); j++) {
          array[j] = steps.get(j);
        }
        int[] array2 = array.clone();
        JavaSort javaSort = new JavaSort();
        start = System.nanoTime();
        javaSort.sort(array);
        end = System.nanoTime();
        System.out.println("Java:  " + (end - start));

        ScalaSort scalaSort = new ScalaSort();
        start = System.nanoTime();
        scalaSort.qsort(array2, 0, array2.length - 1);
        end = System.nanoTime();
        System.out.println("Scala: " + (end - start));
        for(int i = 0; i < steps.size(); i++) {
        	steps.set(i, array[i]);
        }
    }

	public void start(Stage arg0) throws Exception {}
}