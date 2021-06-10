package mrkacafirekcz.doggomod.entity.ai.goal;

import java.util.EnumSet;

import mrkacafirekcz.doggomod.DoggoAction;
import mrkacafirekcz.doggomod.DoggoFeeling;
import mrkacafirekcz.doggomod.entity.DoggoWolf;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.FoodComponent;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class DoggoEatGoal extends Goal {

	private final DoggoWolf doggoWolf;
	private int eating;
	
	public DoggoEatGoal(DoggoWolf doggoWolf) {
		this.doggoWolf = doggoWolf;
		this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public boolean shouldContinue() {
		return eating > 0;
	}

	@Override
	public boolean canStart() {
		if(this.doggoWolf.isInsideWaterOrBubbleColumn()) {
			return false;
		} else if(!this.doggoWolf.isOnGround()) {
			return false;
		} else if(this.doggoWolf.hasAngerTime()) {
			return false;
		}
		
		return doggoWolf.isStackInMouthMeat() && (doggoWolf.getHealth() < doggoWolf.getMaxHealth() || doggoWolf.getRandom().nextFloat() < 0.01F);
	}
	
	@Override
	public boolean canStop() {
		return this.doggoWolf.hasBeenDamaged() || eating == 0;
	}

	@Override
	public void start() {
		this.doggoWolf.setDamaged(false);
		eating = ((FoodComponent) doggoWolf.getStackInMouth().getItem().getFoodComponent()).getHunger() * 4;
		
		if(doggoWolf.isSitting()) {
			doggoWolf.setInSittingPose(true);
		}
		
		doggoWolf.setAction(DoggoAction.EATING);
		doggoWolf.setFeeling(DoggoFeeling.HAPPY);
	}
	
	@Override
	public void stop() {
		doggoWolf.setStackInMouth(null);
		
		if(!doggoWolf.isSitting()) {
			doggoWolf.setInSittingPose(false);
		}
		
		doggoWolf.setAction(DoggoAction.NEUTRAL);
		doggoWolf.setFeeling(DoggoFeeling.NEUTRAL);
	}
	
	@Override
	public void tick() {
		if(eating % 4 == 0) {
			doggoWolf.world.playSound(null, doggoWolf.getX(), doggoWolf.getY(), doggoWolf.getZ(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 0.4f, 1f);
			
			if((int) doggoWolf.getHealth() + 1 <= doggoWolf.getMaxHealth()) {
				doggoWolf.heal(1);
			}
		}
		
		eating--;
	}
}
