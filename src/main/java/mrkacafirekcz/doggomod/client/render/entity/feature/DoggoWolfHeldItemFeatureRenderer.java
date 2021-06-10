package mrkacafirekcz.doggomod.client.render.entity.feature;

import mrkacafirekcz.doggomod.DoggoAction;
import mrkacafirekcz.doggomod.client.render.entity.model.DoggoWolfModel;
import mrkacafirekcz.doggomod.entity.DoggoWolf;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;

public class DoggoWolfHeldItemFeatureRenderer extends FeatureRenderer<DoggoWolf, DoggoWolfModel<DoggoWolf>> {
	
	public DoggoWolfHeldItemFeatureRenderer(FeatureRendererContext<DoggoWolf, DoggoWolfModel<DoggoWolf>> featureRendererContext) {
		super(featureRendererContext);
	}

	@SuppressWarnings("rawtypes")
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, DoggoWolf doggoWolf, float f, float g, float h, float j, float k, float l) {
		if(doggoWolf.hasStackInMouth()) {
			boolean bl = doggoWolf.isBaby();
			matrixStack.push();
			float n = 0f;
			if(bl) {
				n = 0.75F;
				matrixStack.scale(0.75F, 0.75F, 0.75F);
				matrixStack.translate(0.0D, 0.5D, 0.20937499403953552D);
			}

			matrixStack.translate((double) (((DoggoWolfModel) this.getContextModel()).head.pivotX / 16.0F), (double) (((DoggoWolfModel) this.getContextModel()).head.pivotY / 16.0F), (double) (((DoggoWolfModel) this.getContextModel()).head.pivotZ / 16.0F));
			n = doggoWolf.getBegAnimationProgress(h) + doggoWolf.getShakeAnimationProgress(h, 0.0F);
			matrixStack.multiply(Vector3f.POSITIVE_Z.getRadialQuaternion(n));
			matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(k));
			matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(l));
			if(doggoWolf.isBaby()) {
				matrixStack.translate(0.05999999865889549D, 0.25999999046325684D, -0.5D);
			} else if(doggoWolf.isAction(DoggoAction.NAPPING)) {
				matrixStack.translate(-0.28, 0.155, -0.30);
				matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(50.0F));
			} else {
				matrixStack.translate(-n * 0.1, 0.155, -0.4);
			}

			matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
			matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-30.0F));

			ItemStack itemStack = doggoWolf.getStackInMouth();
			MinecraftClient.getInstance().getHeldItemRenderer().renderItem(doggoWolf, itemStack, ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumerProvider, i);
			matrixStack.pop();
		}
	}
}
