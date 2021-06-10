package mrkacafirekcz.doggomod.client.render.entity.model;

import mrkacafirekcz.doggomod.entity.DoggoWolf;
import net.minecraft.util.math.MathHelper;

public class DoggoWolfModelNeutral extends DoggoWolfModel<DoggoWolf> {
	
	@Override
	public void animateModel(DoggoWolf doggoWolf, float f, float g, float h) {
		if(doggoWolf.isInSittingPose()) {
			sitting();
		} else {
			standing(f, g);
		}
		
		shakingAndBegging(doggoWolf, h);
	}
	
	private void sitting() {
		this.head.setPivot(-1.0F, 14.0F, -7.3F);
		this.head.roll = 0f;
		this.neck.setPivot(-1.0F, 16.0F, -3.0F);
		this.neck.pitch = 1.2566371F;
		this.neck.yaw = 0.0F;
		this.torso.setPivot(0.0F, 18.0F, 0.0F);
		this.torso.pitch = 0.7853982F;
		this.tail.setPivot(-1.0F, 21.0F, 6.0F);
		this.rightBackLeg.setPivot(-2.5F, 22.7F, 2.0F);
		this.rightBackLeg.yaw = 0f;
		this.rightBackLeg.pitch = 4.712389F;
		this.leftBackLeg.setPivot(0.5F, 22.7F, 2.0F);
		this.leftBackLeg.yaw = 0f;
		this.leftBackLeg.pitch = 4.712389F;
		this.rightFrontLeg.setPivot(-2.49F, 17.0F, -4.0F);
		this.rightFrontLeg.yaw = 0f;
		this.rightFrontLeg.pitch = 5.811947F;
		this.leftFrontLeg.setPivot(0.51F, 17.0F, -4.0F);
		this.leftFrontLeg.yaw = 0f;
		this.leftFrontLeg.pitch = 5.811947F;
	}
	
	private void shakingAndBegging(DoggoWolf doggoWolf, float h) {
		this.field_20788.roll = doggoWolf.getBegAnimationProgress(h) + doggoWolf.getShakeAnimationProgress(h, 0.0F);
		this.neck.roll = doggoWolf.getShakeAnimationProgress(h, -0.08F);
		this.torso.roll = doggoWolf.getShakeAnimationProgress(h, -0.16F);
		this.field_20789.roll = doggoWolf.getShakeAnimationProgress(h, -0.2F);
	}
	
	private void standing(float f, float g) {
		this.head.setPivot(-1.0F, 13.5F, -7.0F);
		this.torso.pitch = 1.5707964F;
		this.neck.setPivot(-1.0F, 14.0F, -3.0F);
		this.neck.pitch = this.torso.pitch;
		this.rightFrontLeg.setPivot(-2.5F, 16.0F, -4.0F);
		this.leftFrontLeg.setPivot(0.5F, 16.0F, -4.0F);
		this.rightBackLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g;
		this.leftBackLeg.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * g;
		this.rightFrontLeg.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * g;
		this.leftFrontLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g;
		this.neck.yaw = 0f;
		this.head.roll = 0f;
		this.torso.setPivot(0.0F, 14.0F, 2.0F);
		this.tail.setPivot(-1.0F, 12.0F, 8.0F);
		this.rightBackLeg.setPivot(-2.5F, 16.0F, 7.0F);
		this.leftBackLeg.setPivot(0.5F, 16.0F, 7.0F);
		this.rightBackLeg.yaw = 0f;
		this.leftBackLeg.yaw = 0f;
		this.rightFrontLeg.yaw = 0f;
		this.leftFrontLeg.yaw = 0f;
	}
}
