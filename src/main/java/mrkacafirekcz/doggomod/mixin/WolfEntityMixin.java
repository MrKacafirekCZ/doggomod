package mrkacafirekcz.doggomod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.passive.WolfEntity;

@Mixin(WolfEntity.class)
public class WolfEntityMixin {
	
	/*
	 * Special thanks to LlamaLad7 from The Fabric Project discord server for helping me figure out how to remove WolfBegGoal from WolfEntity class.
	 */
	@Redirect(
            method = "initGoals",
            slice = @Slice(from = @At(value = "NEW", target = "net/minecraft/entity/ai/goal/WolfBegGoal")),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V",
            ordinal = 0)
    )
    private void removeBegGoal(GoalSelector goalSelector, int priority, Goal goal) {
        // Do nothing
    }
}
