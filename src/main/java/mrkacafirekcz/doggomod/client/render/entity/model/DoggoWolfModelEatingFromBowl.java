package mrkacafirekcz.doggomod.client.render.entity.model;

import mrkacafirekcz.doggomod.entity.DoggoWolf;
import net.minecraft.util.math.MathHelper;

public class DoggoWolfModelEatingFromBowl extends DoggoWolfModel<DoggoWolf> {
	
	@Override
	public void animateModel(DoggoWolf doggoWolf, float f, float g, float h) {
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
