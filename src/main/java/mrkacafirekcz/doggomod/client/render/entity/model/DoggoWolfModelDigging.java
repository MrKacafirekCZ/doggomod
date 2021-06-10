package mrkacafirekcz.doggomod.client.render.entity.model;

import mrkacafirekcz.doggomod.entity.DoggoWolf;
import net.minecraft.util.math.MathHelper;

public class DoggoWolfModelDigging extends DoggoWolfModel<DoggoWolf> {
	
	@Override
	public void animateModel(DoggoWolf doggoWolf, float f, float g, float h) {
		this.head.setPivot(-1.0F, 14.0F, -7.3F);
		this.head.pitch = 1.2217305f;
		this.head.yaw = 0f;
		this.neck.setPivot(-1.0F, 15.5F, -2.5F);
		this.neck.pitch = 1.7453293f;
		this.rightFrontLeg.setPivot(-2.5F, 17.0F, -4.0F);
		this.leftFrontLeg.setPivot(0.5F, 17.0F, -4.0F);
		this.rightFrontLeg.pitch = MathHelper.cos(doggoWolf.getAnimationTick() * 1.7F + 3.1415927F) * 0.4F - 0.2f;
		this.leftFrontLeg.pitch = MathHelper.cos(doggoWolf.getAnimationTick() * 1.7F) * 0.4F - 0.2f;
		this.rightBackLeg.pitch = 0f;
		this.leftBackLeg.pitch = 0f;
		this.torso.pitch = 1.7453293f;
		this.tail.pitch = 2.0943951f;
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
