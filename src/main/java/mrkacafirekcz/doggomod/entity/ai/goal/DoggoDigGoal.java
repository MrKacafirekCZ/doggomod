package mrkacafirekcz.doggomod.entity.ai.goal;

import java.util.EnumSet;

import mrkacafirekcz.doggomod.DoggoAction;
import mrkacafirekcz.doggomod.DoggoFeeling;
import mrkacafirekcz.doggomod.entity.DoggoWolf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.MathHelper;

public class DoggoDigGoal extends Goal {

	private final DoggoWolf doggoWolf;
	private int digTick;

	public DoggoDigGoal(DoggoWolf doggoWolf) {
		this.doggoWolf = doggoWolf;
		this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public boolean shouldContinue() {
		return this.digTick > 0 && !this.doggoWolf.isInsideWaterOrBubbleColumn() && this.doggoWolf.isOnGround();
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
		} else if(!this.doggoWolf.canStartDigging()) {
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
				return this.doggoWolf.squaredDistanceTo(livingEntity) < 144.0D && livingEntity.getAttacker() != null ? false : !this.doggoWolf.isSitting() && this.doggoWolf.getRandom().nextFloat() < 0.02F;
			}
		}
	}
	
	@Override
	public boolean canStop() {
		return this.doggoWolf.hasBeenDamaged() || this.digTick <= 0;
	}

	@Override
	public void start() {
		this.doggoWolf.setDamaged(false);
		this.doggoWolf.getNavigation().stop();
		this.doggoWolf.setAction(DoggoAction.DIGGING);
		this.doggoWolf.setFeeling(DoggoFeeling.HAPPY);
		this.digTick = 100 + this.doggoWolf.getRandom().nextInt(100);
	}

	@Override
	public void stop() {
		this.doggoWolf.setAction(DoggoAction.NEUTRAL);
		this.doggoWolf.setFeeling(DoggoFeeling.NEUTRAL);
		this.doggoWolf.startActionDelay();
	}
	
	@Override
	public void tick() {
		if(this.digTick == 1) {
			if(this.doggoWolf.getRandom().nextInt(3) == 0) {
				double x = MathHelper.sin(-this.doggoWolf.headYaw % 360 / 58) * 0.5f;
				double z = MathHelper.cos(this.doggoWolf.headYaw % 360 / 58) * 0.5f;
				
				ItemStack item = null;
				
				switch(this.doggoWolf.getRandom().nextInt(2)) {
				case 0:
					item = Items.IRON_NUGGET.getDefaultStack();
					break;
				case 1:
					item = Items.GOLD_NUGGET.getDefaultStack();
					break;
				}
				
				ItemScatterer.spawn(this.doggoWolf.world, this.doggoWolf.getX() + x, this.doggoWolf.getY(), this.doggoWolf.getZ() + z, item);
			}
		}
		
		this.digTick--;
	}
}
