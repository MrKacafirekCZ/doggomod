package mrkacafirekcz.doggomod.entity.ai.goal;

import java.util.EnumSet;

import mrkacafirekcz.doggomod.DoggoAction;
import mrkacafirekcz.doggomod.DoggoMod;
import mrkacafirekcz.doggomod.entity.DoggoWolf;
import net.minecraft.entity.ai.goal.Goal;

public class DoggoGiveTennisBallGoal extends Goal {

	private final DoggoWolf doggoWolf;
	private int delay;

	public DoggoGiveTennisBallGoal(DoggoWolf doggoWolf) {
		this.doggoWolf = doggoWolf;
		this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public boolean shouldContinue() {
		return this.doggoWolf.hasStackInMouth(DoggoMod.TENNIS_BALL) && !this.doggoWolf.isInsideWaterOrBubbleColumn();
	}

	@Override
	public boolean canStart() {
		if(delay > 0) {
			delay--;
			return false;
		}
		
		if(!this.doggoWolf.isTamed()) {
			return false;
		} else if(this.doggoWolf.isBaby()) {
			return false;
		} else if(this.doggoWolf.isInsideWaterOrBubbleColumn()) {
			return false;
		} else if(!this.doggoWolf.isOnGround()) {
			return false;
		} else if(!this.doggoWolf.hasStackInMouth(DoggoMod.TENNIS_BALL)) {
			return false;
		} else if(this.doggoWolf.hasAngerTime()) {
			return false;
		} else if(this.doggoWolf.getOwner() == null) {
			return false;
		} else {
			return this.doggoWolf.getRandom().nextFloat() < 0.06F;
		}
	}
	
	@Override
	public boolean canStop() {
		return this.doggoWolf.hasBeenDamaged() || this.doggoWolf.squaredDistanceTo(this.doggoWolf.getOwner()) < 3;
	}

	@Override
	public void start() {
		this.doggoWolf.setDamaged(false);
		this.doggoWolf.getNavigation().stop();
		this.doggoWolf.getNavigation().startMovingTo(this.doggoWolf.getOwner(), 1);
	}

	@Override
	public void stop() {
		this.doggoWolf.setAction(DoggoAction.NEUTRAL);
	}
	
	@Override
	public void tick() {
		if(this.doggoWolf.squaredDistanceTo(this.doggoWolf.getOwner()) < 3) {
			this.doggoWolf.getNavigation().stop();
			this.doggoWolf.dropStackInMouth();
			delay = 10;
		}
	}
}
