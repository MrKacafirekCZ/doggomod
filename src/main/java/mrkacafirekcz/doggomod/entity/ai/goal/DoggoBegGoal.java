package mrkacafirekcz.doggomod.entity.ai.goal;

import java.util.EnumSet;

import mrkacafirekcz.doggomod.entity.DoggoWolf;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class DoggoBegGoal extends Goal {
	private final DoggoWolf doggoWolf;
	private PlayerEntity begFrom;
	private final World world;
	private final float begDistance;
	private int timer;
	private final TargetPredicate validPlayerPredicate;

	public DoggoBegGoal(DoggoWolf doggoWolf, float begDistance) {
	      this.doggoWolf = doggoWolf;
	      this.world = doggoWolf.world;
	      this.begDistance = begDistance;
	      this.validPlayerPredicate = TargetPredicate.createNonAttackable().setBaseMaxDistance((double)begDistance);
	      this.setControls(EnumSet.of(Goal.Control.LOOK));
	}

	public boolean canStart() {
		if(this.doggoWolf.hasStackInMouth()) {
			return false;
		}
		
		this.begFrom = this.world.getClosestPlayer(this.validPlayerPredicate, this.doggoWolf);
		return this.begFrom == null ? false : this.isAttractive(this.begFrom);
	}

	public boolean shouldContinue() {
		if(!this.begFrom.isAlive()) {
			return false;
		} else if(this.doggoWolf.squaredDistanceTo(this.begFrom) > (double) (this.begDistance * this.begDistance)) {
			return false;
		} else if(this.doggoWolf.hasStackInMouth()) {
			return false;
		} else if(this.doggoWolf.hasAngerTime()) {
			return false;
		} else {
			return this.timer > 0 && this.isAttractive(this.begFrom);
		}
	}

	public void start() {
		this.doggoWolf.setBegging(true);
		this.timer = 40 + this.doggoWolf.getRandom().nextInt(40);
	}

	public void stop() {
		this.doggoWolf.setBegging(false);
		this.begFrom = null;
	}

	public void tick() {
		this.doggoWolf.getLookControl().lookAt(this.begFrom.getX(), this.begFrom.getEyeY(), this.begFrom.getZ(), 10.0F, (float) this.doggoWolf.getLookPitchSpeed());
		--this.timer;
	}

	private boolean isAttractive(PlayerEntity player) {
		Hand[] var2 = Hand.values();
		int var3 = var2.length;

		for(int var4 = 0; var4 < var3; ++var4) {
			Hand hand = var2[var4];
			ItemStack itemStack = player.getStackInHand(hand);
			
			if(this.doggoWolf.isTamed() && itemStack.getItem() == Items.BONE) {
				return true;
			}

			if(this.doggoWolf.isBreedingItem(itemStack)) {
				return true;
			}
		}

		return false;
	}
}
