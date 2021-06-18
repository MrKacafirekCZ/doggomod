package mrkacafirekcz.doggomod.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;

import mrkacafirekcz.doggomod.DoggoAction;
import mrkacafirekcz.doggomod.DoggoMod;
import mrkacafirekcz.doggomod.entity.DoggoWolf;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;

public class DoggoGetTennisBallGoal extends Goal {

	private final DoggoWolf doggoWolf;
	private ItemEntity entity;

	public DoggoGetTennisBallGoal(DoggoWolf doggoWolf) {
		this.doggoWolf = doggoWolf;
		this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public boolean shouldContinue() {
		return entity != null && !this.doggoWolf.isInsideWaterOrBubbleColumn();
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
		} else if(this.doggoWolf.hasAngerTime()) {
			return false;
		} else if(this.doggoWolf.getOwner() == null) {
			return false;
		} else if(this.doggoWolf.getRandom().nextFloat() > 0.04F) {
			return false;
		} else {
			List<ItemEntity> list = this.doggoWolf.world.getEntitiesByClass(ItemEntity.class, this.doggoWolf.getBoundingBox().expand(16.0D, 8.0D, 16.0D), DoggoWolf.PICKABLE_DROP_FILTER);
			
			for(ItemEntity itemEntity : list) {
				if(itemEntity.getStack().getItem() == DoggoMod.TENNIS_BALL) {
					entity = itemEntity;
					break;
				}
			}
			
			return entity != null;
		}
	}
	
	@Override
	public boolean canStop() {
		return this.doggoWolf.hasBeenDamaged() || this.entity == null || this.entity.isRemoved();
	}

	@Override
	public void start() {
		this.doggoWolf.setDamaged(false);
		this.doggoWolf.getNavigation().stop();
		this.doggoWolf.getNavigation().startMovingTo(entity, 1);
	}

	@Override
	public void stop() {
		this.doggoWolf.setAction(DoggoAction.NEUTRAL);
	}
	
	@Override
	public void tick() {
		if(this.entity != null && !this.entity.isRemoved()) {
			if(this.doggoWolf.squaredDistanceTo(entity) < 3) {
				this.doggoWolf.getNavigation().stop();
				
				if(this.doggoWolf.hasStackInMouth()) {
					this.doggoWolf.dropStackInMouth();
				}
				
				this.doggoWolf.setStackInMouth(this.entity.getStack());
				this.entity.remove(RemovalReason.KILLED);
				this.entity = null;
				this.doggoWolf.getNavigation().startMovingTo(this.doggoWolf.getOwner(), 1);
			} else if(this.doggoWolf.getNavigation().isIdle()) {
				this.doggoWolf.getNavigation().startMovingTo(entity, 1);
			}
		}
	}
}
