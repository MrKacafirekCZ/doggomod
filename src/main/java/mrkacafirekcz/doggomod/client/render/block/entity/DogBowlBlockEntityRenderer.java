package mrkacafirekcz.doggomod.client.render.block.entity;

import mrkacafirekcz.doggomod.block.entity.DogBowlEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class DogBowlBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<DogBowlEntity> {

	private final ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
	private final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
	
	public DogBowlBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {

	}
	
	@Override
	public void render(DogBowlEntity entity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
		if(entity.hasCustomName()) {
			String name = textRenderer.trimToWidth(entity.getCustomName().asString(), 66);
			float centerX = (66f - textRenderer.getWidth(name)) / 2f;
			float centerY = 5.5f;

			matrixStack.push();
			matrixStack.translate(0.84, 0.25, 0.1561);
			matrixStack.scale(0.010416667F, 0.010416667F, 0.010416667F);
			matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) 180));
			for(int i = 0; i < 4; i++) {
				matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float) 90));
				matrixStack.translate(-65.8, 0, -0.37);
				this.textRenderer.draw(name, centerX, centerY, NativeImage.getAlpha(255), false, matrixStack.peek().getPositionMatrix(), vertexConsumerProvider, false, 0, light);
			}
			matrixStack.pop();
		}

		if(entity.hasFood()) {
			int amount = MathHelper.ceil(entity.getStack(0).getCount() / 16D);

			matrixStack.push();
	        matrixStack.scale(0.5F, 0.5F, 0.5F);
			matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion((float) 90));
			matrixStack.translate(1, 1, -0.15);
			
			for(int i = 0; i < amount; i++) {
				matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) i * 80));
				this.itemRenderer.renderItem(entity.getStack(0), ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrixStack, vertexConsumerProvider, 0);
				matrixStack.translate(0, 0, -0.05);
			}
			matrixStack.pop();
		}
	}


}
