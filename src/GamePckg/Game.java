package GamePckg;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Game extends Application {

    public static ArrayList<Block> platforms = new ArrayList<>(); 								//List of blocks on map
    public static ArrayList<Bots> bots = new ArrayList<>();		  								//List of bots
    public static ArrayList<Double> pitsX = new ArrayList<>();									//List of pits(X coords)
    public static ArrayList<Double> pitsY = new ArrayList<>();									//List of pits(Y coords)
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();									//Hashmap of pressed keys

    public static final int BLOCK_SIZE = 45;
    public static final int PERS_SIZE = 40;
    public final static int SPAWN_X = 0;
    public final static int SPAWN_Y = 500;
    public final static int VELOCITY_X = 0;
    public final static int STANDART_VELOCITY = 10;
    public final static int MAX_OFFSET = 640;
    public final static int TEXT_SCALE = 2;
    public final static int FINISH_TEXT_SCALE = 16;
    public final static int TEXT_TRANSITION_X = 900;
    public final static int TEXT_TRANSITION_Y = 20;
    public final static int ESC_X = 20;
    public final static int ESC_Y = 10;
    public final static double ESC_SCALE = 1.2;

    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();

    public static Character player;
    private int levelWidth;
    private int botflag;																		//Flag of bot's shot delay
    static int persflag;																		//Flag of character's shot delay
    private static boolean isBot = false;
    private static int characterShootDelay = 10;
    private static int botShootDelay = 10;
    public static boolean isExit = false;
    public static int bulletDamage = 1;
    public static int gameLevel = 1;
    private static FileInputStream ins;
    private static DataInputStream dis;

    static int levelNumber = 0;
    final static int BLOCK_NUM_X = 212;
    final static int BLOCK_NUM_Y = 14;
    final static int SCENE_X = 1200;
    final static int SCENE_Y = 620;
    final static int SCENE_X_CENTER = 600;
    final static int SCENE_Y_CENTER = 310;
    final static int LOOK_TO_THE_LEFT = -1;
    final static int LOOK_TO_THE_RIGHT = 1;
    final static int STEP = 5;
    final static int BOT_STEP = 3;
    final static int RAISE_VELOCITY = 1;
    final static int CAN_SHOOT = 0;
    final static int CANT_SHOOT = 1;
    final static int OBSERVE_DISTANCE = 1000;
    final static int SHOOTING_DISTANCE = 500;
    final static int MINIMAL_DISTANCE = 10;
    final static int JUMP_DISTANCE = 15;
    final static int ROOT_BLOCK_DIFFERENCE = 1;
    final static int DELAY = 2000;
    final static int FINAL_COUNTER = 20;
    final static int BIRD_DELAY = 600;
    final static int DISTANCE_TO_BIRD_SPAWN = 1000;
    final static int DISTANCE_TO_BIRD_SPAWN_Y = 450;
    final static String BACKGROUND = "background.png";

    Image backgroundImg = new Image(getClass().getResourceAsStream(BACKGROUND));

    /*
     * 	An empty class constructor.
     */
    public Game() {}

    /*
     * 	Game class constructor with setting of difficulty level and flag of bot playing.
     */
    public Game(int level, boolean flag) {
        gameLevel = level;
        setLevel(gameLevel);
        if(flag) {
            isBot = true;
        }
    }
    
    /*
     * 	Setting of difficulty level.
     */
    public static void setLevel(int level) {
    	if(gameLevel == 1) {
            characterShootDelay = 10;
            botShootDelay = 10;
        }
        if(gameLevel == 2) {
            characterShootDelay = 15;
            botShootDelay = 5;
            bulletDamage = 10;
        }
        if(gameLevel == 3) {
            characterShootDelay = 20;
            botShootDelay = 1;
            bulletDamage = 20;
        }
    }

    /*
     * 	Content initialization(includes loading map from file, creating blocks objects and spawning characters.
     */
    public void initContent() {
        ImageView background = new ImageView(backgroundImg);
        background.setFitHeight(BLOCK_NUM_Y * BLOCK_SIZE);
        background.setFitWidth(BLOCK_NUM_X * BLOCK_SIZE);
        levelWidth = LevelData.levels[levelNumber][0].length() * BLOCK_SIZE;
        for (int i = 0; i < LevelData.levels[levelNumber].length; i++) {						//Loading of map from file
            String line = LevelData.levels[levelNumber][i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                case '-':
                    pitsX.add((double)j * BLOCK_SIZE);
                    pitsY.add((double)i * BLOCK_SIZE);
                    break;
                case '1':
                    new Block(Block.BlockType.PLATFORM, j * BLOCK_SIZE, i * BLOCK_SIZE);
                    break;
                case '2':
                    new Block(Block.BlockType.BRICK, j * BLOCK_SIZE, i * BLOCK_SIZE);
                    break;
                case '3':
                    new Block(Block.BlockType.BONUS, j * BLOCK_SIZE, i * BLOCK_SIZE);
                    break;
                case '4':
                    new Block(Block.BlockType.STONE, j * BLOCK_SIZE, i * BLOCK_SIZE);
                    break;
                case '5':
                    new Block(Block.BlockType.LADDER, j * BLOCK_SIZE, i * BLOCK_SIZE);
                    break;
                case '*':
                    new Bots(j * BLOCK_SIZE, i * BLOCK_SIZE);
                    break;
                }
            }
        }
        player = new Character(SPAWN_X, SPAWN_Y);
        player.translateXProperty().addListener((obs, old, newValue) -> {						//Offset of camera on moving
            int offset = newValue.intValue();
            if (offset > MAX_OFFSET && offset < levelWidth - MAX_OFFSET + 2 * PERS_SIZE) {
                gameRoot.setLayoutX(-(offset - MAX_OFFSET));
                background.setLayoutX(-(offset - MAX_OFFSET));
            }
        });
        appRoot.getChildren().addAll(background, gameRoot);
    }

    /*
     * 	Subsidiary method to display the text on the game pane.
     */
    public Text createText(double x, double y, double scale, String str, Color color) {
        Text text = new Text(x, y, str);
        text.setScaleX(scale);
        text.setScaleY(scale);
        text.setFill(color);
        return text;
    }

    /*
     * 	Game condition updating. Includes main character and bots condition changing, handling of buttons pressing. Includes
     * 	gravity implementation, replay recording and finishing ocasions handling.
     */
    private void update(Text HP) {
    	int index = -1,
    		shootflag = CANT_SHOOT;
        if(player.getHealth() <= 0) {
            Text text = createText(SCENE_X_CENTER, SCENE_Y_CENTER, FINISH_TEXT_SCALE, "YOU LOSE!", Color.BROWN);
            appRoot.getChildren().add(text);
            isExit = true;
        }
        HP.setText("HEALTH: " + player.getHealth());
        if(!isBot) {
            if (isPressed(KeyCode.UP) && player.getTranslateY() >= STEP) {
                player.jumpPlayer();
            }
            if (isPressed(KeyCode.DOWN)) {
                player.downTheStairs();
            }
            if (isPressed(KeyCode.LEFT) && player.getTranslateX() >= STEP) {
                player.setScaleX(LOOK_TO_THE_LEFT);
                player.animation.play();
                player.moveX(-STEP, false);
            }
            if (isPressed(KeyCode.RIGHT) && player.getTranslateX() + PERS_SIZE <= levelWidth - STEP) {
                player.setScaleX(LOOK_TO_THE_RIGHT);
                player.animation.play();
                player.moveX(STEP, false);
            }
            if (isPressed(KeyCode.SPACE) && persflag == CAN_SHOOT) {
                player.fire(gameRoot);
                persflag = CANT_SHOOT;
                shootflag = CAN_SHOOT;
            }
        }
        else {
            shootflag = moveBot();
        }
        index = moveBots();
        Replay.gameConditionRecording(player, shootflag, index);
        shootflag = CANT_SHOOT;
        if (player.playerVelocity.getY() < STANDART_VELOCITY) {									//Gravity implementation
            player.playerVelocity = player.playerVelocity.add(VELOCITY_X, RAISE_VELOCITY);		//If falling velocity(jump) < Gravity
        }																						// velocity, increase velocity
        player.moveY((int)player.playerVelocity.getY());										//Gravitation(every moment attract to
        																						// 	the Earth)
        if(player.getTranslateX() >= levelWidth - BLOCK_SIZE) {
            Text text = createText(SCENE_X_CENTER, SCENE_Y_CENTER, FINISH_TEXT_SCALE, "YOU WIN!", Color.BROWN);
            appRoot.getChildren().add(text);
            isExit = true;
        }
    }

    /*
     * 	AI implementation for game passing. Includes game condition reacting by gamebot and game exceptions handling.
     */
    private int moveBot() {
    	int flag = CANT_SHOOT;
        if(persflag == CAN_SHOOT) {
            player.setScaleX(LOOK_TO_THE_RIGHT);
        }
        player.moveX(STEP, true);
        player.animation.play();
        for(Bots chrtr : bots) {
            if(Math.abs(player.getTranslateX() - chrtr.getTranslateX()) < OBSERVE_DISTANCE &&
                    player.getTranslateX() > chrtr.getTranslateX() &&
                    player.getTranslateY() == chrtr.getTranslateY() &&
                    persflag == CAN_SHOOT) {
                player.setScaleX(LOOK_TO_THE_LEFT);
                player.fire(gameRoot);
                player.jumpPlayer();
                persflag = CANT_SHOOT;
                flag = CAN_SHOOT;
            }
            if(Math.abs(chrtr.getTranslateX() - player.getTranslateX()) < OBSERVE_DISTANCE &&
                    player.getTranslateX() < chrtr.getTranslateX() &&
                    player.getTranslateY() == chrtr.getTranslateY() &&
                    persflag == CAN_SHOOT) {
                player.setScaleX(LOOK_TO_THE_RIGHT);
                player.fire(gameRoot);
                player.jumpPlayer();
                persflag = CANT_SHOOT;
                flag = CAN_SHOOT;
            }
        }
        return flag;
    }

    /*
     * 	Bots reacting for main character's actions. Including game condition handling.
     */
    private int moveBots() {
    	int index = -1;
        for(Bots chrtr : bots) {
            chrtr.animation.play();
            for(int i = 0; i < pitsX.size(); i++) {
                if(chrtr.getTranslateX() - JUMP_DISTANCE - BLOCK_SIZE == pitsX.get(i) &&
                        chrtr.getTranslateY() + PERS_SIZE + ROOT_BLOCK_DIFFERENCE == pitsY.get(i) &&
                        chrtr.getScaleX() == LOOK_TO_THE_LEFT
                        || chrtr.getTranslateX() + JUMP_DISTANCE + BLOCK_SIZE == pitsX.get(i) &&
                        chrtr.getTranslateY() + PERS_SIZE + ROOT_BLOCK_DIFFERENCE == pitsY.get(i) &&
                        chrtr.getScaleX() == LOOK_TO_THE_RIGHT) {
                    chrtr.jumpPlayer();
                }
            }
            if(chrtr.getTranslateX() > player.getTranslateX() &&
                    chrtr.getTranslateX() - player.getTranslateX() < OBSERVE_DISTANCE) {
                chrtr.setScaleX(LOOK_TO_THE_LEFT);
                chrtr.moveX(-BOT_STEP);
                if(chrtr.getTranslateX() - player.getTranslateX() < SHOOTING_DISTANCE &&
                        player.getTranslateY() == chrtr.getTranslateY() &&
                        botflag == CAN_SHOOT) {
                    chrtr.fire(gameRoot, player);
                    botflag = CANT_SHOOT;
                }
            }
            if(player.getTranslateX() > chrtr.getTranslateX() &&
                    player.getTranslateX() - chrtr.getTranslateX() < OBSERVE_DISTANCE) {
                chrtr.setScaleX(LOOK_TO_THE_RIGHT);
                chrtr.moveX(BOT_STEP);
                if(player.getTranslateX() - chrtr.getTranslateX() < SHOOTING_DISTANCE &&
                        player.getTranslateY() == chrtr.getTranslateY() &&
                        botflag == CAN_SHOOT) {
                    chrtr.fire(gameRoot, player);
                    botflag = CANT_SHOOT;
                    index = bots.indexOf(chrtr);
                }
            }
            if(Math.abs(player.getTranslateX() - chrtr.getTranslateX()) <= MINIMAL_DISTANCE) {
                chrtr.animation.stop();
            }
            if (chrtr.playerVelocity.getY() < STANDART_VELOCITY) {
                chrtr.playerVelocity = chrtr.playerVelocity.add(VELOCITY_X, RAISE_VELOCITY);
            }
            if(chrtr.getTranslateY() < SCENE_Y) {
                chrtr.moveY((int)chrtr.playerVelocity.getY());
            }
        }
        return index;
    }

    /*
     * 	Handler of pressed keys.
     */
    private void keysHandler(Scene scene) {
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> {
            keys.put(event.getCode(), false);
            player.animation.stop();
        });
    }

    /*
     * 	Checking for keys pressing.
     */
    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }
    
    /*
     * 	Game condition updating from the last replay.
     */
    private int update() {
		try {
			Double temp;
			player.animation.play();
			temp = dis.readDouble();
			if(temp == Replay.FINISH)
				return 1;
			if(temp == player.getTranslateX())
				player.animation.stop();
			player.setTranslateX(temp);
			player.setTranslateY(dis.readDouble());
			player.setScaleX(dis.readDouble());
			temp = dis.readDouble();
			if(temp == Replay.IS_SHOOT) {
				player.fire(gameRoot);
			} 
			for(Bots bot : bots) {
				bot.setTranslateX(dis.readDouble());
				bot.setTranslateY(dis.readDouble());
				bot.setScaleX(dis.readDouble());
				temp = dis.readDouble();
				if(temp == Replay.IS_SHOOT) {
					bot.fire(gameRoot, player);
				}
				if(Math.abs(bot.getTranslateX() - player.getTranslateX()) < OBSERVE_DISTANCE)
						bot.animation.play();
				if(Math.abs(player.getTranslateX() - bot.getTranslateX()) <= MINIMAL_DISTANCE) {
	                bot.animation.stop();
	            }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
    }
    
    /*
     * 	Main method for playing of last replay.
     */
    public void start(Stage primaryStage, String path) {
    	initContent();
    	Text esc = createText(ESC_X, ESC_Y, ESC_SCALE, "Press 'esc' to exit to main menu", Color.BROWN);
    	appRoot.getChildren().add(esc);
    	try {
			ins = new FileInputStream(path);
			dis = new DataInputStream(ins);
	    	Scene scene = new Scene(appRoot, SCENE_X, SCENE_Y);
	    	primaryStage.setTitle("REPLAY");
	        primaryStage.setScene(scene);
	        primaryStage.show();
			gameLevel = dis.readInt();
			setLevel(gameLevel);
			AnimationTimer timer = new AnimationTimer() {
			int birdcounter = BIRD_DELAY;
				
			@Override
			public void handle(long now) {
				if(update() == 1) {
					stop();
					clearResourse();
                    Pane newPane = new Pane();
                    scene.setRoot(newPane);
                    Menu.MainMenu(primaryStage);
				}
				if(birdcounter < BIRD_DELAY) {
                	birdcounter++;
                }
                else {
                	birdcounter = 0;
                	new Bird(player.getTranslateX() + DISTANCE_TO_BIRD_SPAWN, player.getTranslateY() - DISTANCE_TO_BIRD_SPAWN_Y);
                }
			}
				
			};
			timer.start();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /*
     * (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     * Overrided for creating of main game components(pane, scene, etc.), game resources initialization and 
     * game events handling.
     */
    public void start(Stage primaryStage) {
        Replay.replayRecordingInit();
        initContent();
        Text esc = createText(ESC_X, ESC_Y, ESC_SCALE, "Press 'esc' to exit to main menu", Color.BROWN);
        Text text = createText(TEXT_TRANSITION_X, TEXT_TRANSITION_Y, TEXT_SCALE, "HEALTH " + player.getHealth(), Color.BROWN);
        appRoot.getChildren().addAll(text, esc);
        Scene scene = new Scene(appRoot, SCENE_X, SCENE_Y);
        keysHandler(scene);
        primaryStage.setTitle("LITTLE NIGGA STORY");
        primaryStage.setScene(scene);
        primaryStage.show();
        AnimationTimer timer = new AnimationTimer() {
            int botcounter = 0;
            int perscounter = 0;
            int wincounter = 0;
            int birdcounter = BIRD_DELAY;
            
            @Override
            public void handle(long now) {
                update(text);
                if(isExit) {
                    wincounter++;
                }
                if(birdcounter < BIRD_DELAY) {
                	birdcounter++;
                }
                else {
                	birdcounter = 0;
                	new Bird(player.getTranslateX() + DISTANCE_TO_BIRD_SPAWN, player.getTranslateY() - DISTANCE_TO_BIRD_SPAWN_Y);
                }
                if(botflag == CANT_SHOOT)
                    botcounter++;
                if(persflag == CANT_SHOOT)
                    perscounter++;
                if(perscounter == characterShootDelay) {
                    persflag = CAN_SHOOT;
                    perscounter = 0;
                }
                if(botcounter == botShootDelay) {
                    botflag = CAN_SHOOT;
                    botcounter = 0;
                }
                if(isPressed(KeyCode.ESCAPE) || wincounter == FINAL_COUNTER) {
                    stop();
                    clearResourse();
                    Pane newPane = new Pane();
                    scene.setRoot(newPane);
                    Menu.MainMenu(primaryStage);
                }
            }
        };
        timer.start();
    }

    /*
     * 	Resourse clearing after program finishing.
     */
    private void clearResourse() {
        bots.clear();
        platforms.clear();
        isBot = false;
        isExit = false;
        gameRoot.setLayoutX(Character.LAYOUT_TO_START);
        gameRoot.getChildren().clear();
        appRoot.getChildren().clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}