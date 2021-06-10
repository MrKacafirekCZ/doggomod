package mrkacafirekcz.doggomod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import mrkacafirekcz.doggomod.DoggoMod;
import mrkacafirekcz.doggomod.entity.DoggoWolf;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Mixin(EntityType.class)
public class EntityTypeMixin {
	
	/*
	 * Special thanks to LlamaLad7 from The Fabric Project discord server for helping me figure out how to replace minecraft:wolf with my own wolf entity.
	 */
	@Redirect(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=wolf")), //A way of narrowing down the part of the target method we care about... in this case wolf
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;register(Ljava/lang/String;Lnet/minecraft/entity/EntityType$Builder;)Lnet/minecraft/entity/EntityType;",
            ordinal = 0)
    )
    private static <T extends Entity> EntityType<DoggoWolf> replaceWolf(String id, EntityType.Builder<T> type) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(DoggoMod.MODID, "wolf"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DoggoWolf::new).dimensions(EntityDimensions.fixed(0.6f, 0.85f)).build());
    }
}
