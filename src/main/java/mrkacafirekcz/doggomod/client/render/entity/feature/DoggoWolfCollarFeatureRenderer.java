package mrkacafirekcz.doggomod.client.render.entity.feature;

import mrkacafirekcz.doggomod.client.render.entity.model.DoggoWolfModel;
import mrkacafirekcz.doggomod.entity.DoggoWolf;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DoggoWolfCollarFeatureRenderer extends FeatureRenderer<DoggoWolf, DoggoWolfModel<DoggoWolf>> {
	   private static final Identifier SKIN = new Identifier("textures/entity/wolf/wolf_collar.png");

	   public DoggoWolfCollarFeatureRenderer(FeatureRendererContext<DoggoWolf, DoggoWolfModel<DoggoWolf>> featureRendererContext) {
	      super(featureRendererContext);
	   }

	   public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, DoggoWolf wolfEntity, float f, float g, float h, float j, float k, float l) {
	      if (wolfEntity.isTamed() && !wolfEntity.isInvisible()) {
	         float[] fs = wolfEntity.getCollarColor().getColorComponents();
	         renderModel(this.getContextModel(), SKIN, matrixStack, vertexConsumerProvider, i, wolfEntity, fs[0], fs[1], fs[2]);
	      }
	   }
}
