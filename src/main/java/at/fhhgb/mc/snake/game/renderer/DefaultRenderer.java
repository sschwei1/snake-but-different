package at.fhhgb.mc.snake.game.renderer;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;
import at.fhhgb.mc.snake.game.options.GameOptions;

import java.util.List;

public class DefaultRenderer extends AbstractGameRenderer {
    private GameCell[][] gameGrid;

    public DefaultRenderer(GameOptions options) {
        super(options);
        this.initGameGrid();
    }

    private void initGameGrid() {
        this.gameGrid = new GameCell[this.options.getGameWidth()][];

        for(int i = 0; i < this.options.getGameWidth(); i++) {
            this.gameGrid[i] = new GameCell[this.options.getGameHeight()];
            for(int j = 0; j < this.options.getGameHeight(); j++) {
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
    public void update(List<AbstractEntity> entities) {
        AbstractEntity head = null;

        for(GameCell[] cellArr : this.gameGrid) {
            for(GameCell cell : cellArr) {
                cell.clear();
            }
        }

        for(AbstractEntity e : entities) {
            if(e.getType() == GameCell.State.SNAKE_HEAD) {
                head = e;
                continue;
            }

            GameCell cell = this.getCellAt(e.getPosition());
            cell.addEntity(e);
        }

        if(head != null) {
            GameCell cell = this.getCellAt(head.getPosition());
            cell.setState(GameCell.State.SNAKE_HEAD);
        }
    }
}
