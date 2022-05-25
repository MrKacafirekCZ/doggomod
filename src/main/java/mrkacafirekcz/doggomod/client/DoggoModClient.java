package mrkacafirekcz.doggomod.client;

import mrkacafirekcz.doggomod.DoggoMod;
import mrkacafirekcz.doggomod.client.gui.screen.ingame.DogBowlScreen;
import mrkacafirekcz.doggomod.client.render.block.entity.DogBowlBlockEntityRenderer;
import mrkacafirekcz.doggomod.client.render.entity.DoggoWolfRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class DoggoModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(DoggoMod.DOGGO, DoggoWolfRenderer::new);
        EntityRendererRegistry.register(DoggoMod.TENNIS_BALL_ENTITY, FlyingItemEntityRenderer::new);

        BlockEntityRendererRegistry.register(DoggoMod.DOG_BOWL_ENTITY, DogBowlBlockEntityRenderer::new);
        ScreenRegistry.register(DoggoMod.DOG_BOWL_SCREEN_HANDLER, DogBowlScreen::new);
    }
}
