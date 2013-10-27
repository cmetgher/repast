package jzombies;

import java.util.ArrayList;
import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.graph.Network;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.SimUtilities;

public class Zombie {
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private boolean moved;

	public Zombie(ContinuousSpace<Object> space, Grid<Object> grid) {

		this.grid = grid;
		this.space = space;

	}

	@ScheduledMethod(start = 1, interval = 2)
	public void step() {

		// obtine locatia pe grid a zombiului

		GridPoint pt = grid.getLocation(this);
		// cu GridCellNgh facem gridcells pentru vecini

		GridCellNgh<Human> nghCreator = new GridCellNgh<Human>(grid, pt,
				Human.class, 1, 1);
		List<GridCell<Human>> gridCells = nghCreator.getNeighborhood(true);
		SimUtilities.shuffle(gridCells, RandomHelper.getUniform());

		GridPoint pointWithMostHumans = null;
		int maxCount = -1;
		for (GridCell<Human> cell : gridCells) {

			if (cell.size() > maxCount) {
				pointWithMostHumans = cell.getPoint();
				maxCount = cell.size();

			}
		}
		moveTowards(pointWithMostHumans);

	}

	public void moveTowards(GridPoint pt) {
		// me miscam numai daca nu suntem deja in locul necesar

		if (!pt.equals(grid.getLocation(this))) {

			NdPoint myPoint = space.getLocation(this);
			NdPoint otherPoint = new NdPoint(pt.getX(), pt.getY());
			double angle = SpatialMath.calcAngleFor2DMovement(space, myPoint,
					otherPoint);
			space.moveByVector(this, 1, angle, 0);
			myPoint = space.getLocation(this);
			grid.moveTo(this, (int) myPoint.getX(), (int) myPoint.getY());
			moved = true;

		}

	}

}
