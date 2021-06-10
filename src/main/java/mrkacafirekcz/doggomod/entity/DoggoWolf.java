package mrkacafirekcz.doggomod.entity;

import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.Sets;

import mrkacafirekcz.doggomod.DoggoAction;
import mrkacafirekcz.doggomod.DoggoFeeling;
import mrkacafirekcz.doggomod.TrackedDoggoData;
import mrkacafirekcz.doggomod.block.entity.DogBowlEntity;
import mrkacafirekcz.doggomod.entity.ai.goal.DoggoBegGoal;
import mrkacafirekcz.doggomod.entity.ai.goal.DoggoDigGoal;
import mrkacafirekcz.doggomod.entity.ai.goal.DoggoEatFromBowlGoal;
import mrkacafirekcz.doggomod.entity.ai.goal.DoggoEatGoal;
import mrkacafirekcz.doggomod.entity.ai.goal.DoggoGetTennisBallGoal;
import mrkacafirekcz.doggomod.entity.ai.goal.DoggoGiveTennisBallGoal;
import mrkacafirekcz.doggomod.entity.ai.goal.DoggoNapGoal;
import mrkacafirekcz.doggomod.entity.ai.goal.DoggoScratchGoal;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class DoggoWolf extends WolfEntity {
	
	private static final TrackedData<DoggoAction> ACTION;
	private static final TrackedData<BlockPos> BOWL_POS;
	private static final Set<Block> diggableBlocks = Sets.newHashSet(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.SAND, Blocks.RED_SAND, Blocks.GRAVEL);
	private static final TrackedData<DoggoFeeling> FEELING;
	private static final TrackedData<ItemStack> MOUTH_STACK;

	public static final Predicate<ItemEntity> PICKABLE_DROP_FILTER;
	
	//Client sided only
	private float animationTick;
	private int scratchingSide;
	
	//Server sided only
	private int actionDelay;
	private boolean damaged;
	
	public DoggoWolf(EntityType<? extends WolfEntity> entityType, World world) {
		super(entityType, world);
	}
	
	public boolean canStartDigging() {
		return diggableBlocks.contains(this.world.getBlockState(this.getBlockPos().down()).getBlock());
	}
	
	public boolean damage(DamageSource source, float amount) {
		damaged = true;
		dropStackInMouth();
		return super.damage(source, amount);
	}
	
	public void dropStackInMouth() {
		if(hasStackInMouth()) {
			ItemScatterer.spawn(world, getX(), getY(), getZ(), getStackInMouth());
			setStackInMouth(null);
		}
	}
	
	public int getActionDelay() {
		return actionDelay;
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		if (getAction() == DoggoAction.NAPPING) {
			return null;
		} else if (this.hasAngerTime()) {
			return SoundEvents.ENTITY_WOLF_GROWL;
		} else if (this.random.nextInt(3) == 0) {
			return this.isTamed() && this.getHealth() < 10.0F ? SoundEvents.ENTITY_WOLF_WHINE : SoundEvents.ENTITY_WOLF_PANT;
		} else {
			return SoundEvents.ENTITY_WOLF_AMBIENT;
		}
	}
	
	public float getAnimationTick() {
		return animationTick;
	}
	
	public DoggoAction getAction() {
		return (DoggoAction)this.dataTracker.get(ACTION);
	}
	
	public BlockPos getBowlPos() {
		return (BlockPos)this.dataTracker.get(BOWL_POS);
	}
	
	public DoggoFeeling getFeeling() {
		return (DoggoFeeling)this.dataTracker.get(FEELING);
	}
	
	public int getScratchingSide() {
		return scratchingSide;
	}
	
	public ItemStack getStackInMouth() {
		return (ItemStack)this.dataTracker.get(MOUTH_STACK);
	}
	
	public boolean hasBeenDamaged() {
		return damaged;
	}
	
	public boolean hasStackInMouth() {
		return getStackInMouth() != null && getStackInMouth().getItem() != Items.AIR;
	}
	
	public boolean hasStackInMouth(ItemStack item) {
		return hasStackInMouth() && getStackInMouth() == item;
	}
	
	public boolean hasStackInMouth(Item item) {
		return hasStackInMouth() && getStackInMouth().getItem() == item;
	}
	
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(ACTION, DoggoAction.NEUTRAL);
		this.dataTracker.startTracking(BOWL_POS, null);
		this.dataTracker.startTracking(FEELING, DoggoFeeling.NEUTRAL);
		this.dataTracker.startTracking(MOUTH_STACK, new ItemStack(Items.AIR));
	}
	
	@Override
	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(1, new DoggoNapGoal(this));
		this.goalSelector.add(1, new DoggoEatGoal(this));
		this.goalSelector.add(1, new DoggoScratchGoal(this));
		this.goalSelector.add(2, new DoggoEatFromBowlGoal(this));
		this.goalSelector.add(2, new DoggoGetTennisBallGoal(this));
		this.goalSelector.add(2, new DoggoGiveTennisBallGoal(this));
		this.goalSelector.add(9, new DoggoBegGoal(this, 8.0F));
		this.goalSelector.add(11, new DoggoDigGoal(this));
	}
	
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		if(!this.world.isClient && this.isTamed() && !this.isBaby() && !this.hasStackInMouth() && isAction(DoggoAction.NEUTRAL)) {
			ItemStack itemStack = player.getStackInHand(hand);
			Item item = itemStack.getItem();
			
			if(item.isFood() && ((FoodComponent) item.getFoodComponent()).isMeat()) {
				setStackInMouth(itemStack.split(1));
				return ActionResult.SUCCESS;
			}
		}
		
		return super.interactMob(player, hand);
	}
	
	public boolean isAction(DoggoAction doggoAction) {
		return getAction() == doggoAction;
	}
	
	public boolean isOwnerClose() {
		LivingEntity livingEntity = this.getOwner();
		
		if(livingEntity == null) {
			return false;
		}
		
		if(!(livingEntity instanceof PlayerEntity)) {
			return false;
		}
		
		return !((PlayerEntity) livingEntity).isSpectator() && this.squaredDistanceTo(livingEntity) < 36.0D;
	}
	
	public boolean isStackInMouthMeat() {
		return hasStackInMouth() && getStackInMouth().isFood() && ((FoodComponent) getStackInMouth().getItem().getFoodComponent()).isMeat();
	}
	
	public void setAction(DoggoAction doggoAction) {
		this.dataTracker.set(ACTION, doggoAction);
	}
	
	public void setBowlPos(BlockPos bowlPos) {
		this.dataTracker.set(BOWL_POS, bowlPos);
	}
	
	public void setDamaged(boolean damaged) {
		this.damaged = damaged;
	}
	
	public void setFeeling(DoggoFeeling doggoFeeling) {
		this.dataTracker.set(FEELING, doggoFeeling);
	}
	
	public void setScratchingSide(int scratchingSide) {
		this.scratchingSide = scratchingSide;
	}
	
	public void setStackInMouth(ItemStack itemStack) {
		if(itemStack == null) {
			itemStack = new ItemStack(Items.AIR);
		}
		
		this.dataTracker.set(MOUTH_STACK, itemStack);
	}
	
	public void startActionDelay() {
		actionDelay = 200 + this.random.nextInt(20) * 10;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if(this.world.isClient) {
			animationTick = (animationTick + 0.5f) % 360;

			double x = MathHelper.sin(-this.headYaw % 360 / 58) * 0.5f;
			double z = MathHelper.cos(this.headYaw % 360 / 58) * 0.5f;
			
			switch(getAction()) {
			case DIGGING:
				this.world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, this.world.getBlockState(this.getBlockPos().down())), this.getX() + x, this.getY(), this.getZ() + z, 0.1, 0.1, 0.1);
				break;
			case EATING:
				this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, getStackInMouth()), this.getX() + x * (1.6 + ((double) this.random.nextInt(5) / 10)), this.getY() + 0.55, this.getZ() + z * (1.6 + ((double) this.random.nextInt(5) / 10)), 0, 0, 0);
				break;
			case EATING_FROM_BOWL:
				if(getBowlPos() != null) {
					ItemStack item = ((DogBowlEntity) this.world.getBlockEntity(getBowlPos())).getStack(0);
					
					if(item != null && !item.isEmpty()) {
						this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, item), getBowlPos().getX() + 0.4 + ((double) this.random.nextInt(3) / 10), getBowlPos().getY() + 0.2, getBowlPos().getZ() + 0.4 + ((double) this.random.nextInt(3) / 10), 0, 0, 0);
					}
				}
				break;
			default:
				break;
			}
		} else {
			if(actionDelay > 0) {
				actionDelay--;
			}
		}
	}
	
	static {
		ACTION = DataTracker.registerData(DoggoWolf.class, TrackedDoggoData.DOGGO_ACTION);
		BOWL_POS = DataTracker.registerData(DoggoWolf.class, TrackedDataHandlerRegistry.BLOCK_POS);
		FEELING = DataTracker.registerData(DoggoWolf.class, TrackedDoggoData.DOGGO_FEELING);
		MOUTH_STACK = DataTracker.registerData(DoggoWolf.class, TrackedDataHandlerRegistry.ITEM_STACK);
		
		PICKABLE_DROP_FILTER = (itemEntity) -> {
			return !itemEntity.cannotPickup() && itemEntity.isAlive();
		};
	}
}
