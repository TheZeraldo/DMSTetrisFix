package com.comp2042.logic.game;

import java.awt.Point;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.bricks.RandomBrickGenerator;
import com.comp2042.model.ClearRow;
import com.comp2042.model.NextShapeInfo;
import com.comp2042.model.ViewData;
import com.comp2042.utils.GameModeManager;
import com.comp2042.utils.GameModes;

public class BrickManager {
	private final int width;
	private final int height;
	
	private final BrickGenerator brickGenerator;
	private final BrickRotator brickRotator;
    private Point currentOffset;
    private int[][] currentGameMatrix;
	
	public BrickManager(int width, int height) {
		this.width = width;
		this.height = height;
		brickGenerator = new RandomBrickGenerator();
		brickRotator = new BrickRotator();
        currentGameMatrix = new int[width][height];
	}
	
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        if (GameModeManager.getGameMode() == GameModes.BIG) {
            currentOffset = new Point(2, 1);
        } else {
            currentOffset = new Point(4, 1);
        }
        
        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY(), brickGenerator.getNextBrick().getShapeMatrix().get(0), getLandingYPosition());
    }
    
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }
    
    public ClearRow clearRows() {
        ClearRow cleared = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = cleared.getNewMatrix();
        return cleared;
    }


	public int getLandingYPosition() {
		int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
		int landingY = (int) currentOffset.getY();
		
		while (true) {
			boolean collision = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), landingY + 1);
			
			if (collision) {
				break;
			} else {
				landingY++;
			}
		}
		return landingY;
	}

    public void newGame() {
        currentGameMatrix = new int[width][height];
        createNewBrick();
    }
    
    public int[][] getGameMatrix() {
    	return currentGameMatrix;
    }
    
    public void setOffset(Point offset) {
    	currentOffset = offset;
    }
    
    public Point getOffset() {
    	return currentOffset;
    }

	public Brick getNextBrick() {
		return brickGenerator.getNextBrick();
	}
    
    public void setBrick(Brick brick) {
    	brickRotator.setBrick(brick);
    }
    
    public Brick getBrick() {
    	return brickRotator.getBrick();
    }
    
    public void setCurrentShape(int shape) {
    	brickRotator.setCurrentShape(shape);
    }
    
    public int[][] getCurrentShape() {
    	return brickRotator.getCurrentShape();
    }
    
    public NextShapeInfo getNextShape() {
    	return brickRotator.getNextShape();
    }
}
