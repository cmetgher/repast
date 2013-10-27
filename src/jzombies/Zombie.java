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
<<<<<<< HEAD
		infect();
=======
>>>>>>> 93d78c50a091feee5be2a738625bcf4b1ced1d19

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

<<<<<<< HEAD
	public void infect() {
		GridPoint gp = grid.getLocation(this);
		List<Object> humans = new ArrayList<Object>();
		for (Object obj : grid.getObjectsAt(gp.getX(), gp.getY())) {
			if (obj instanceof Human) {
				humans.add(obj);
			}
		}

		if (humans.size() > 0) {

			int index = RandomHelper.nextIntFromTo(0, humans.size() - 1);
			Object obj = humans.get(index);
			NdPoint spacePt = space.getLocation(obj);
			Context<Object> context = ContextUtils.getContext(obj);
			context.remove(obj);
			Zombie zombie = new Zombie(space, grid);
			context.add(zombie);
			space.moveTo(zombie, spacePt.getX(), spacePt.getY());
			grid.moveTo(zombie, gp.getX(), gp.getY());
			
			Network<Object> net = (Network<Object>)context.getProjection("infection network");
			net.addEdge(this, zombie);

		}

	}
=======
>>>>>>> 93d78c50a091feee5be2a738625bcf4b1ced1d19
}
