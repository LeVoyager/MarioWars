package GamePckg;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Block extends Pane {

    boolean visibility;
    final int BLOCK_WIDTH = 16;
    final int BLOCK_HEIGHT = 16;
    final int PLATFORM_OFFSET_X = 0;
    final int PLATFORM_OFFSET_Y = 0;
    final int BRICK_OFFSET_X = 16;
    final int BRICK_OFFSET_Y = 0;
    final int BONUS_OFFSET_X = 384;
    final int BONUS_OFFSET_Y = 0;
    final int STONE_OFFSET_X = 0;
    final int STONE_OFFSET_Y = 16;
    final int LADDER_OFFSET_X = 80;
    final int LADDER_OFFSET_Y = 0;
    final String BLOCKS_IMAGE_RES = "1.png";
    Image blocksImg = new Image(getClass().getResourceAsStream(BLOCKS_IMAGE_RES));
    ImageView block;
    public enum BlockType {
        PLATFORM, BRICK, BONUS, STONE, LADDER
    }

    /*
     *	 Constructor of Block class. Gets an a Block Type, and Block coords args and creates block on pane.
     */
    public Block(BlockType blockType, int x, int y) {
        block = new ImageView(blocksImg);
        block.setFitWidth(Game.BLOCK_SIZE);
        block.setFitHeight(Game.BLOCK_SIZE);
        setTranslateX(x);
        setTranslateY(y);
        visibility = true;
        switch (blockType) {
        case PLATFORM:
            block.setViewport(new Rectangle2D(PLATFORM_OFFSET_X, PLATFORM_OFFSET_Y, BLOCK_WIDTH, BLOCK_HEIGHT));
            break;
        case BRICK:
            block.setViewport(new Rectangle2D(BRICK_OFFSET_X, BRICK_OFFSET_Y, BLOCK_WIDTH, BLOCK_HEIGHT));
            break;
        case BONUS:
            block.setViewport(new Rectangle2D(BONUS_OFFSET_X, BONUS_OFFSET_Y, BLOCK_WIDTH, BLOCK_HEIGHT));
            break;
        case STONE:
            block.setViewport(new Rectangle2D(STONE_OFFSET_X, STONE_OFFSET_Y, BLOCK_WIDTH, BLOCK_HEIGHT));
            break;
        case LADDER:
            block.setViewport(new Rectangle2D(LADDER_OFFSET_X, LADDER_OFFSET_Y, BLOCK_WIDTH, BLOCK_HEIGHT));
            visibility = false;
            break;
        }
        getChildren().add(block);
        Game.platforms.add(this);
        Game.gameRoot.getChildren().add(this);
    }
    
    /*
     * 	Set Block visibility.
     */
    public boolean IsBlockVisible() {
        return visibility;
    }
}