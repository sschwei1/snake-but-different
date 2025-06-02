package at.fhhgb.mc.snake.game.renderer;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;
import at.fhhgb.mc.snake.game.entity.manager.EntityManager;
import at.fhhgb.mc.snake.game.options.GameOptions;
import at.fhhgb.mc.snake.game.struct.Point2D;
import at.fhhgb.mc.snake.log.GLog;

public class DefaultRenderer extends AbstractGameRenderer {
    private GameCell[][] gameGrid;

    private final Point2D offset;

    public DefaultRenderer(GameOptions options, EntityManager entityManager) {
        super(options, entityManager);

        this.offset = this.options.isWallEnabled() ?
            new Point2D(1, 1) :
            new Point2D(0, 0);

        this.initGameGrid();
    }

    private void initGameGrid() {
        int gameWidth = this.options.getGameWidth() + 2 * this.offset.getX();
        int gameHeight = this.options.getGameHeight() + 2 * this.offset.getY();

        this.gameGrid = new GameCell[gameWidth][gameHeight];

        for(int i = 0; i < gameWidth; i++) {
            this.gameGrid[i] = new GameCell[gameHeight];
            for(int j = 0; j < gameHeight; j++) {
                this.gameGrid[i][j] = new GameCell();
            }
        }
    }

    @Override
    public GameCell[][] getGameGrid() {
        return this.gameGrid;
    }

    @Override
    public GameCell getCellAt(int x, int y) {
        return this.gameGrid[x][y];
    }

    @Override
    public void update() {
        GLog.info("Start Rendering");

        for(Point2D pos : this.entityManager.getPositionsToClear()) {
            this.getCellAt(pos).clear();
        }

        for(AbstractEntity entity : this.entityManager.getDirtyEntities()) {
            this.getCellAt(entity.getPosition()).setState(entity);
        }

        this.entityManager.resetDirtyCollections();

        GLog.info("Stop Rendering");
    }
}
