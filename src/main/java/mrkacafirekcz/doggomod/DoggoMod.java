package mrkacafirekcz.doggomod;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mrkacafirekcz.doggomod.block.DogBowl;
import mrkacafirekcz.doggomod.block.entity.DogBowlEntity;
import mrkacafirekcz.doggomod.entity.DoggoWolf;
import mrkacafirekcz.doggomod.entity.projectile.thrown.TennisBallEntity;
import mrkacafirekcz.doggomod.item.TennisBall;
import mrkacafirekcz.doggomod.screen.DogBowlScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DoggoMod implements ModInitializer {
	
	private static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "doggomod";

    public static final Block DOG_BOWL_WHITE = new DogBowl();
    public static final Block DOG_BOWL_ORANGE = new DogBowl();
    public static final Block DOG_BOWL_MAGENTA = new DogBowl();
    public static final Block DOG_BOWL_LIGHTBLUE = new DogBowl();
    public static final Block DOG_BOWL_YELLOW = new DogBowl();
    public static final Block DOG_BOWL_LIME = new DogBowl();
    public static final Block DOG_BOWL_PINK = new DogBowl();
    public static final Block DOG_BOWL_GRAY = new DogBowl();
    public static final Block DOG_BOWL_LIGHTGRAY = new DogBowl();
    public static final Block DOG_BOWL_CYAN = new DogBowl();
    public static final Block DOG_BOWL_PURPLE = new DogBowl();
    public static final Block DOG_BOWL_BLUE = new DogBowl();
    public static final Block DOG_BOWL_BROWN = new DogBowl();
    public static final Block DOG_BOWL_GREEN = new DogBowl();
    public static final Block DOG_BOWL_RED = new DogBowl();
    public static final Block DOG_BOWL_BLACK = new DogBowl();
    
    public static final EntityType<DoggoWolf> DOGGO = Registry.register(Registry.ENTITY_TYPE, new Identifier(DoggoMod.MODID, "wolf"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DoggoWolf::new).dimensions(EntityDimensions.fixed(0.6f, 0.85f)).build());
    
    public static final EntityType<TennisBallEntity> TENNIS_BALL_ENTITY = Registry.register(Registry.ENTITY_TYPE, new Identifier(MODID, "tennis_ball"), FabricEntityTypeBuilder.<TennisBallEntity>create(SpawnGroup.MISC, TennisBallEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build());
    
    public static final Item TENNIS_BALL = new TennisBall();
    
    public static final Identifier DOG_BOWL = new Identifier(MODID, "dog_bowl_entity");
    public static BlockEntityType<DogBowlEntity> DOG_BOWL_ENTITY;
    public static final ScreenHandlerType<DogBowlScreenHandler> DOG_BOWL_SCREEN_HANDLER;
    
    public static final ItemGroup DOG_STUFF_GROUP = FabricItemGroupBuilder.create(new Identifier(MODID, "doggomod")).icon(() -> new ItemStack(DOG_BOWL_RED)).appendItems(stacks -> {
    	stacks.add(new ItemStack(DOG_BOWL_WHITE));
    	stacks.add(new ItemStack(DOG_BOWL_ORANGE));
    	stacks.add(new ItemStack(DOG_BOWL_MAGENTA));
    	stacks.add(new ItemStack(DOG_BOWL_LIGHTBLUE));
    	stacks.add(new ItemStack(DOG_BOWL_YELLOW));
    	stacks.add(new ItemStack(DOG_BOWL_LIME));
    	stacks.add(new ItemStack(DOG_BOWL_PINK));
    	stacks.add(new ItemStack(DOG_BOWL_GRAY));
    	stacks.add(new ItemStack(DOG_BOWL_LIGHTGRAY));
    	stacks.add(new ItemStack(DOG_BOWL_CYAN));
    	stacks.add(new ItemStack(DOG_BOWL_PURPLE));
    	stacks.add(new ItemStack(DOG_BOWL_BLUE));
    	stacks.add(new ItemStack(DOG_BOWL_BROWN));
    	stacks.add(new ItemStack(DOG_BOWL_GREEN));
    	stacks.add(new ItemStack(DOG_BOWL_RED));
    	stacks.add(new ItemStack(DOG_BOWL_BLACK));
    	stacks.add(new ItemStack(TENNIS_BALL));
    }).build();
    
	@Override
	public void onInitialize() {
		registerBlockAndItem("dog_bowl_white", DOG_BOWL_WHITE);
		registerBlockAndItem("dog_bowl_orange", DOG_BOWL_ORANGE);
		registerBlockAndItem("dog_bowl_magenta", DOG_BOWL_MAGENTA);
		registerBlockAndItem("dog_bowl_lightblue", DOG_BOWL_LIGHTBLUE);
		registerBlockAndItem("dog_bowl_yellow", DOG_BOWL_YELLOW);
		registerBlockAndItem("dog_bowl_lime", DOG_BOWL_LIME);
		registerBlockAndItem("dog_bowl_pink", DOG_BOWL_PINK);
		registerBlockAndItem("dog_bowl_gray", DOG_BOWL_GRAY);
		registerBlockAndItem("dog_bowl_lightgray", DOG_BOWL_LIGHTGRAY);
		registerBlockAndItem("dog_bowl_cyan", DOG_BOWL_CYAN);
		registerBlockAndItem("dog_bowl_purple", DOG_BOWL_PURPLE);
		registerBlockAndItem("dog_bowl_blue", DOG_BOWL_BLUE);
		registerBlockAndItem("dog_bowl_brown", DOG_BOWL_BROWN);
		registerBlockAndItem("dog_bowl_green", DOG_BOWL_GREEN);
		registerBlockAndItem("dog_bowl_red", DOG_BOWL_RED);
		registerBlockAndItem("dog_bowl_black", DOG_BOWL_BLACK);
		
		registerItem("tennis_ball", TENNIS_BALL);
		
		DOG_BOWL_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, DOG_BOWL, FabricBlockEntityTypeBuilder.create(DogBowlEntity::new, DOG_BOWL_WHITE, DOG_BOWL_ORANGE, DOG_BOWL_MAGENTA, DOG_BOWL_LIGHTBLUE, DOG_BOWL_YELLOW, DOG_BOWL_LIME, DOG_BOWL_PINK, DOG_BOWL_GRAY, DOG_BOWL_LIGHTGRAY, DOG_BOWL_CYAN, DOG_BOWL_PURPLE, DOG_BOWL_BLUE, DOG_BOWL_BROWN, DOG_BOWL_GREEN, DOG_BOWL_RED, DOG_BOWL_BLACK).build(null));

		FabricDefaultAttributeRegistry.register(EntityType.WOLF, DoggoWolf.createWolfAttributes());

		TrackedDataHandlerRegistry.register(TrackedDoggoData.DOGGO_ACTION);
		TrackedDataHandlerRegistry.register(TrackedDoggoData.DOGGO_FEELING);
	}

	private void registerBlockAndItem(String id, Block block) {
		Registry.register(Registry.BLOCK, new Identifier(MODID, id), block);
		registerItem(id, new BlockItem(block, new FabricItemSettings()));
	}
	
	private void registerItem(String id, Item item) {
		Registry.register(Registry.ITEM, new Identifier(MODID, id), item);
	}
	
    public static void log(Level level, String message) {
        LOGGER.log(level, "[DoggoMod] " + message);
    }
    
    static {
    	DOG_BOWL_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(DOG_BOWL, DogBowlScreenHandler::new);
    }
}
