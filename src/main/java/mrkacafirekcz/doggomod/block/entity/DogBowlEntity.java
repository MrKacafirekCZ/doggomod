package mrkacafirekcz.doggomod.block.entity;

import org.jetbrains.annotations.Nullable;

import mrkacafirekcz.doggomod.DoggoMod;
import mrkacafirekcz.doggomod.inventory.ImplementedInventory;
import mrkacafirekcz.doggomod.screen.DogBowlScreenHandler;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;

public class DogBowlEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory, BlockEntityClientSerializable, ScreenHandlerListener, Nameable {

	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);
	private Text customName;
	
	public DogBowlEntity() {
		super(DoggoMod.DOG_BOWL_ENTITY);
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		DogBowlScreenHandler handler = new DogBowlScreenHandler(syncId, inv, this);
		handler.addListener(this);
		return handler;
	}

	@Nullable
	public Text getCustomName() {
		return this.customName;
	}

	@Override
	public Text getDisplayName() {
		return this.getName();
	}
	
	public int getFoodHunger() {
		if(items.get(0) != null && !items.get(0).isEmpty() && items.get(0).getItem().isFood()) {
			return items.get(0).getItem().getFoodComponent().getHunger() * 4;
		}
		
		return 0;
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return items;
	}

	public Text getName() {
		return this.customName != null ? new LiteralText(this.customName.asString() + "'s Dog Bowl") : new LiteralText("Dog Bowl");
	}
	
	public void foodEaten() {
		if(items.get(0) != null && !items.get(0).isEmpty()) {
			items.get(0).decrement(1);
			sync();
		}
	}
	
	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);
		Inventories.fromTag(tag, items);
		if(tag.contains("CustomName", 8)) {
			this.customName = Text.Serializer.fromJson(tag.getString("CustomName"));
		}
	}
	
	public boolean hasCustomName() {
		return this.customName != null;
	}
	
	public boolean hasFood() {
		return items.get(0) != null && !items.get(0).isEmpty();
	}
	
	private CompoundTag saveInitialChunkData(CompoundTag tag) {
		tag = super.toTag(tag);
		tag = Inventories.toTag(tag, items, true);
		if(this.customName != null) {
			tag.putString("CustomName", Text.Serializer.toJson(this.customName));
		}
		return tag;
	}

	public void setCustomName(Text customName) {
		this.customName = customName;
	}

	/*
	public CompoundTag toInitialChunkDataTag() {
		return this.saveInitialChunkData(new CompoundTag());
	}*/
	
	@Override
	public CompoundTag toTag(CompoundTag tag) {
		return saveInitialChunkData(tag);
	}

	@Override
	public void fromClientTag(CompoundTag tag) {
		DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);
		
		Inventories.fromTag(tag, items);
		
		for(int i = 0; i < this.items.size(); i++) {
			this.items.set(i, items.get(i));
		}
		
		if(tag.contains("CustomName", 8)) {
			this.customName = Text.Serializer.fromJson(tag.getString("CustomName"));
		}
	}

	@Override
	public CompoundTag toClientTag(CompoundTag tag) {
		return saveInitialChunkData(tag);
	}

	@Override
	public void onHandlerRegistered(ScreenHandler handler, DefaultedList<ItemStack> stacks) {
		
	}

	@Override
	public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
		if(slotId == 0) {
			sync();
		}
	}

	@Override
	public void onPropertyUpdate(ScreenHandler handler, int property, int value) {
		
	}
}
