package mrkacafirekcz.doggomod.client.render.entity.model;

import com.google.common.collect.ImmutableList;

import mrkacafirekcz.doggomod.DoggoAction;
import mrkacafirekcz.doggomod.entity.DoggoWolf;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.TintableAnimalModel;
import net.minecraft.util.math.MathHelper;

public class DoggoWolfModel<T extends DoggoWolf> extends TintableAnimalModel<T> {
	
	public ModelPart head;
	protected ModelPart field_20788;
	protected ModelPart torso;
	protected ModelPart rightBackLeg;
	protected ModelPart leftBackLeg;
	protected ModelPart rightFrontLeg;
	protected ModelPart leftFrontLeg;
	protected ModelPart tail;
	protected ModelPart field_20789;
	protected ModelPart neck;

	public DoggoWolfModel() {
		this.head = new ModelPart(this, 0, 0);
		this.head.setPivot(-1.0F, 13.5F, -7.0F);
		this.field_20788 = new ModelPart(this, 0, 0);
		this.field_20788.addCuboid(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, 0.0F);
		this.head.addChild(this.field_20788);
		this.torso = new ModelPart(this, 18, 14);
		this.torso.addCuboid(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, 0.0F);
		this.torso.setPivot(0.0F, 14.0F, 2.0F);
		this.neck = new ModelPart(this, 21, 0);
		this.neck.addCuboid(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, 0.0F);
		this.neck.setPivot(-1.0F, 14.0F, 2.0F);
		this.rightBackLeg = new ModelPart(this, 0, 18);
		this.rightBackLeg.addCuboid(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
		this.rightBackLeg.setPivot(-2.5F, 16.0F, 7.0F);
		this.leftBackLeg = new ModelPart(this, 0, 18);
		this.leftBackLeg.addCuboid(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
		this.leftBackLeg.setPivot(0.5F, 16.0F, 7.0F);
		this.rightFrontLeg = new ModelPart(this, 0, 18);
		this.rightFrontLeg.addCuboid(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
		this.rightFrontLeg.setPivot(-2.5F, 16.0F, -4.0F);
		this.leftFrontLeg = new ModelPart(this, 0, 18);
		this.leftFrontLeg.addCuboid(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
		this.leftFrontLeg.setPivot(0.5F, 16.0F, -4.0F);
		this.tail = new ModelPart(this, 9, 18);
		this.tail.setPivot(-1.0F, 12.0F, 8.0F);
		this.field_20789 = new ModelPart(this, 9, 18);
		this.field_20789.addCuboid(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
		this.tail.addChild(this.field_20789);
		this.field_20788.setTextureOffset(16, 14).addCuboid(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F);
		this.field_20788.setTextureOffset(16, 14).addCuboid(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F);
		this.field_20788.setTextureOffset(0, 10).addCuboid(-0.5F, 0.0F, -5.0F, 3.0F, 3.0F, 4.0F, 0.0F);
	}

	protected Iterable<ModelPart> getHeadParts() {
		return ImmutableList.of(this.head);
	}

	protected Iterable<ModelPart> getBodyParts() {
		return ImmutableList.of(this.torso, this.rightBackLeg, this.leftBackLeg, this.rightFrontLeg, this.leftFrontLeg, this.tail, this.neck);
	}

	public void setAngles(T doggoWolf, float f, float g, float h, float i, float j) {
		switch(doggoWolf.getAction()) {
		case NAPPING:
			return;
		case EATING_FROM_BOWL:
			this.head.pitch = 0.69813170f;
			this.head.yaw = i * 0.017453292F;
			tail(doggoWolf, h);
			return;
		case EATING:
		case NEUTRAL:
			this.head.pitch = j * 0.017453292F;
			this.head.yaw = i * 0.017453292F;
		default:
			tail(doggoWolf, doggoWolf.isAction(DoggoAction.DIGGING) ? 2.0943951f : h);
			return;
		}
	}
	
	/*
	Angles
	 10 degrees = 0.17453292f
	 20 degrees = 0.34906585f
	 30 degrees = 0.52359877f
	 40 degrees = 0.69813170f
	 50 degrees = 0.87266462f
	 60 degrees = 1.04719755f
	 70 degrees = 1.22173047f
	 80 degrees = 1.39626340f
	 90 degrees = 1.57079632f
	100 degrees = 1.74532925f
	110 degrees = 1.91986217f
	120 degrees = 2.09439510f
	130 degrees = 2.26892802f
	 */
	
	private void tail(DoggoWolf doggoWolf, float h) {
		switch(doggoWolf.getFeeling()) {
		case NEUTRAL:
			this.tail.yaw = 0f;
			this.tail.pitch = h;
			return;
		case HAPPY:
			this.tail.yaw = MathHelper.cos(doggoWolf.getAnimationTick()) * 0.4f;
			this.tail.pitch = h;
			return;
		case SAD:
			this.tail.yaw = 0f;
			this.tail.pitch = 40f;
			return;
		}
	}
}
