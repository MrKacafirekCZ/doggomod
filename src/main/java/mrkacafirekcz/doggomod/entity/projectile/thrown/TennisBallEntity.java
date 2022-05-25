package mrkacafirekcz.doggomod.entity.projectile.thrown;

import mrkacafirekcz.doggomod.DoggoMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class TennisBallEntity extends ThrownItemEntity {
	
	public TennisBallEntity(EntityType<? extends TennisBallEntity> entityType, World world) {
		super(entityType, world);
	}

	public TennisBallEntity(World world, LivingEntity owner) {
		super(DoggoMod.TENNIS_BALL_ENTITY, owner, world);
	}

	public TennisBallEntity(World world, double x, double y, double z) {
		super(DoggoMod.TENNIS_BALL_ENTITY, x, y, z, world);
	}
	
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		entityHitResult.getEntity().damage(DamageSource.thrownProjectile(this, this.getOwner()), 0.0F);
	}

	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if(!this.world.isClient) {
			/*if(hitResult.getType() == HitResult.Type.BLOCK) {
				BlockState state = world.getBlockState(new BlockPos(hitResult.getPos()));
				
				if(state.getBlock().equals(Blocks.GLASS_PANE)) {
					world.breakBlock(new BlockPos(hitResult.getPos()), false);
				}
			}*/
			
			ItemScatterer.spawn(world, getX(), getY(), getZ(), getDefaultItem().getDefaultStack());

			this.remove(RemovalReason.KILLED);
		}
	}

	@Override
	protected Item getDefaultItem() {
		return DoggoMod.TENNIS_BALL;
	}
	
//	@Override
//	public Packet<?> createSpawnPacket() {
//		return EntitySpawnPacket.create(this, DoggoModClient.PacketID);
//	}
}
