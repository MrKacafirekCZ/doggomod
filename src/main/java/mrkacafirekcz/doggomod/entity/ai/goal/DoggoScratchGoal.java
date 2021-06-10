package mrkacafirekcz.doggomod.entity.ai.goal;

import java.util.EnumSet;

import mrkacafirekcz.doggomod.DoggoAction;
import mrkacafirekcz.doggomod.entity.DoggoWolf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class DoggoScratchGoal extends Goal {

	private final DoggoWolf doggoWolf;
	private int scratchTick;

	public DoggoScratchGoal(DoggoWolf doggoWolf) {
		this.doggoWolf = doggoWolf;
		this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public boolean shouldContinue() {
		return this.scratchTick > 0 && !this.doggoWolf.isInsideWaterOrBubbleColumn() && this.doggoWolf.isOnGround();
	}

	@Override
	public boolean canStart() {
		if(!this.doggoWolf.isTamed()) {
			return false;
		} else if(this.doggoWolf.isBaby()) {
			return false;
		} else if(this.doggoWolf.isInsideWaterOrBubbleColumn()) {
			return false;
		} else if(!this.doggoWolf.isOnGround()) {
			return false;
		} else if(this.doggoWolf.hasStackInMouth()) {
			return false;
		} else if(this.doggoWolf.getActionDelay() > 0) {
			return false;
		} else if(this.doggoWolf.hasAngerTime()) {
			return false;
		} else {
			LivingEntity livingEntity = this.doggoWolf.getOwner();
			if(livingEntity == null) {
				return true;
			} else {
				return this.doggoWolf.squaredDistanceTo(livingEntity) < 144.0D && livingEntity.getAttacker() != null ? false : this.doggoWolf.getRandom().nextFloat() < 0.01F;
			}
		}
	}
	
	@Override
	public boolean canStop() {
		return this.doggoWolf.hasBeenDamaged() || this.scratchTick <= 0;
	}

	@Override
	public void start() {
		this.doggoWolf.setDamaged(false);
		this.doggoWolf.getNavigation().stop();
		this.doggoWolf.setAction(DoggoAction.SCRATCHING);
		this.doggoWolf.setScratchingSide(this.doggoWolf.getRandom().nextInt(2));
		this.scratchTick = 60 + this.doggoWolf.getRandom().nextInt(80);
	}

	@Override
	public void stop() {
		this.doggoWolf.setAction(DoggoAction.NEUTRAL);
		this.doggoWolf.startActionDelay();
	}
	
	@Override
	public void tick() {
		this.scratchTick--;
	}
}
