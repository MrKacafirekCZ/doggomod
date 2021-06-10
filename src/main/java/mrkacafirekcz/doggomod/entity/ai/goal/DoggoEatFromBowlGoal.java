package mrkacafirekcz.doggomod.entity.ai.goal;

import java.util.EnumSet;
import java.util.NoSuchElementException;
import java.util.Optional;

import mrkacafirekcz.doggomod.DoggoAction;
import mrkacafirekcz.doggomod.DoggoFeeling;
import mrkacafirekcz.doggomod.block.DogBowl;
import mrkacafirekcz.doggomod.block.entity.DogBowlEntity;
import mrkacafirekcz.doggomod.entity.DoggoWolf;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class DoggoEatFromBowlGoal extends Goal {

	private final DoggoWolf doggoWolf;
	private boolean failed;
	private Optional<BlockPos> bowlPos;
	private int eating;
	private int waitIfFailed;
	private int whineTime;

	public DoggoEatFromBowlGoal(DoggoWolf doggoWolf) {
		this.doggoWolf = doggoWolf;
		this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public boolean shouldContinue() {
		return !failed && this.doggoWolf.getHealth() < this.doggoWolf.getMaxHealth() && waitIfFailed == 0 && !this.doggoWolf.isNavigating() && isNextToBowl() || eating > 0 || whineTime > 0;
	}

	@Override
	public boolean canStart() {
		if(!this.doggoWolf.isTamed()) {
			return false;
		} else if(this.doggoWolf.isInsideWaterOrBubbleColumn()) {
			return false;
		} else if(!this.doggoWolf.isOnGround()) {
			return false;
		} else if(this.doggoWolf.hasAngerTime()) {
			return false;
		} else if(this.doggoWolf.getHealth() == this.doggoWolf.getMaxHealth()){
			return false;
		} else if(this.waitIfFailed > 0){
			return this.doggoWolf.getRandom().nextFloat() < 0.02F;
		}
		
		return true;
	}
	
	@Override
	public boolean canStop() {
		return this.doggoWolf.hasBeenDamaged() || this.doggoWolf.getHealth() == this.doggoWolf.getMaxHealth();
	}

	@Override
	public void start() {
		this.doggoWolf.setDamaged(false);
		
		if(waitIfFailed > 0) {
			waitIfFailed--;
			return;
		}
		
		this.failed = false;
		this.eating = 0;
		this.whineTime = 0;
		this.bowlPos = BlockPos.findClosest(this.doggoWolf.getBlockPos(), 16, 8, (b) -> {
			return DogBowl.canEatFromBowl(b, doggoWolf);
		});
		
		try {
			Vec3d closestSide = getClosestSide(new Vec3d(bowlPos.get().getX(), bowlPos.get().getY(), bowlPos.get().getZ()));
			
			this.doggoWolf.getNavigation().startMovingTo(closestSide.getX(), closestSide.getY(), closestSide.getZ(), 1);
		} catch(NoSuchElementException ex) {
			fail();
		}
	}

	@Override
	public void stop() {
		this.doggoWolf.setAction(DoggoAction.NEUTRAL);
		this.doggoWolf.setFeeling(DoggoFeeling.NEUTRAL);
	}
	
	@Override
	public void tick() {
		if(!failed) {
			if(this.doggoWolf.isAction(DoggoAction.EATING_FROM_BOWL)) {
				this.doggoWolf.getLookControl().lookAt(this.doggoWolf.getBowlPos().getX() + 0.5, 0, this.doggoWolf.getBowlPos().getZ() + 0.5, 10, 10);
				
				if(eating == 0) {
					DogBowlEntity dogBowlEntity = (DogBowlEntity) this.doggoWolf.world.getBlockEntity(this.doggoWolf.getBowlPos());
					
					if(dogBowlEntity.hasFood()) {
						eating = dogBowlEntity.getFoodHunger();
						dogBowlEntity.foodEaten();
					} else if((int) this.doggoWolf.getHealth() + 1 <= this.doggoWolf.getMaxHealth()) {
						failAndWhile();
						return;
					}
				}
				
				if(eating % 4 == 0) {
					this.doggoWolf.world.playSound(null, this.doggoWolf.getX(), this.doggoWolf.getY(), this.doggoWolf.getZ(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 0.4f, 1f);
					
					if((int) this.doggoWolf.getHealth() + 1 <= this.doggoWolf.getMaxHealth()) {
						this.doggoWolf.heal(1);
					}
				}
				
				if(eating > 0) {
					eating--;
				}
			} else if(this.bowlPos != null && isNextToBowl()) {
				if(this.doggoWolf.isNavigating()) {
					this.doggoWolf.getNavigation().stop();
				}

				this.doggoWolf.setBowlPos(bowlPos.get());
				this.doggoWolf.setAction(DoggoAction.EATING_FROM_BOWL);
				this.doggoWolf.setFeeling(DoggoFeeling.HAPPY);
				this.doggoWolf.dropStackInMouth();
			} else if(!this.doggoWolf.isNavigating()) {
				fail();
			}
		} else if(whineTime > 0) {
			whineTime--;
		}
	}
	
	private void fail() {
		this.failed = true;
		this.waitIfFailed = 3;
	}
	
	private void failAndWhile() {
		fail();
		this.doggoWolf.world.playSound(null, this.doggoWolf.getX(), this.doggoWolf.getY(), this.doggoWolf.getZ(), SoundEvents.ENTITY_WOLF_WHINE, SoundCategory.NEUTRAL, 0.4f, 1.05f);
		whineTime = 40;
	}
	
	private Vec3d getClosestSide(Vec3d position) {
		Vec3d[] sides = new Vec3d[] {new Vec3d(0.4, 0, 0), new Vec3d(-0.4, 0, 0), new Vec3d(0, 0, 0.4), new Vec3d(0, 0, -0.4)};
		
		Vec3d closestSide = position.add(sides[0]).add(0.5, 0, 0.5);
		double closest = position.add(sides[0]).add(0.5, 0, 0.5).distanceTo(this.doggoWolf.getPos());
		
		for(int i = 1; i < sides.length; i++) {
			double close = position.add(sides[i]).add(0.5, 0, 0.5).distanceTo(this.doggoWolf.getPos());
			
			if(close < closest) {
				closest = close;
				closestSide = position.add(sides[i]).add(0.5, 0, 0.5);
			}
		}
		
		return closestSide;
	}
	
	private boolean isNextToBowl() {
		try {
			if(this.doggoWolf.getBlockPos().isWithinDistance(this.bowlPos.get(), 1.05)) {
				return true;
			}
			
			return false;
		} catch(NoSuchElementException ex) {
			return false;
		}
	}
}
