package mrkacafirekcz.doggomod.entity.ai.goal;

import java.util.EnumSet;

import mrkacafirekcz.doggomod.DoggoAction;
import mrkacafirekcz.doggomod.entity.DoggoWolf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class DoggoNapGoal extends Goal {

	private final DoggoWolf doggoWolf;

	public DoggoNapGoal(DoggoWolf doggoWolf) {
		this.doggoWolf = doggoWolf;
		this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public boolean shouldContinue() {
		return !this.doggoWolf.isInsideWaterOrBubbleColumn() && this.doggoWolf.isOnGround() && !this.doggoWolf.isOwnerClose();
	}

	@Override
	public boolean canStart() {
		if(!this.doggoWolf.isTamed()) {
			return false;
		} else if(this.doggoWolf.isInsideWaterOrBubbleColumn()) {
			return false;
		} else if(!this.doggoWolf.isOnGround()) {
			return false;
		} else if(!this.doggoWolf.isSitting()) {
			return false;
		} else if(this.doggoWolf.isOwnerClose()) {
			return false;
		} else if(this.doggoWolf.hasAngerTime()) {
			return false;
		} else {
			LivingEntity livingEntity = this.doggoWolf.getOwner();
			if(livingEntity == null) {
				return true;
			} else {
				return livingEntity.getAttacker() != null ? false : this.doggoWolf.getRandom().nextFloat() < 0.01F;
			}
		}
	}
	
	@Override
	public boolean canStop() {
		return this.doggoWolf.hasBeenDamaged() || this.doggoWolf.isOwnerClose();
	}

	@Override
	public void start() {
		this.doggoWolf.setDamaged(false);
		this.doggoWolf.getNavigation().stop();
		this.doggoWolf.setAction(DoggoAction.NAPPING);
	}

	@Override
	public void stop() {
		this.doggoWolf.setAction(DoggoAction.NEUTRAL);
	}
	
	@Override
	public void tick() {
		
	}
}
