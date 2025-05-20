package at.fhhgb.mc.snake.game.renderer;

import at.fhhgb.mc.snake.game.options.GameOptions;

public class DefaultRenderer extends GameRenderer {
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
    public void update() {
        GameCell cell = this.gameGrid[1][1];

        var newState = cell.getState() == GameCell.State.EMPTY ? GameCell.State.SNAKE : GameCell.State.EMPTY;
        cell.setState(newState);
    }
}
