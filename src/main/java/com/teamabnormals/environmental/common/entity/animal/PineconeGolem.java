package com.teamabnormals.environmental.common.entity.animal;

import com.teamabnormals.environmental.common.entity.ai.goal.PineconeGolemGrabSaplingGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.PineconeGolemLookForSpotGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.PineconeGolemPlantSaplingGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PineconeGolem extends AbstractGolem {
    private static final EntityDataAccessor<Integer> SNIFFS = SynchedEntityData.defineId(PineconeGolem.class, EntityDataSerializers.INT);

    private int sniffAnim;
    private int sniffAnim0;

    public PineconeGolem(EntityType<? extends AbstractGolem> type, Level level) {
        super(type, level);
        this.setCanPickUpLoot(true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PineconeGolemPlantSaplingGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new PineconeGolemLookForSpotGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new PineconeGolemGrabSaplingGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SNIFFS, 0);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.125D);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.5F;
    }

    @Override
    public int getMaxHeadXRot() {
        return 0;
    }

    @Override
    public int getMaxHeadYRot() {
        return 5;
    }

    private int getSniffs() {
        return this.entityData.get(SNIFFS);
    }

    public void setSniffs(int sniffs) {
        this.entityData.set(SNIFFS, sniffs);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 4) {
            this.sniffAnim = 8;
            this.sniffAnim0 = 8;
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void calculateEntityAnimation(LivingEntity entity, boolean isFlying) {
        entity.animationSpeedOld = entity.animationSpeed;
        double d0 = entity.getX() - entity.xo;
        double d1 = isFlying ? entity.getY() - entity.yo : 0.0D;
        double d2 = entity.getZ() - entity.zo;
        float f = (float) Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * 16.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        entity.animationSpeed += (f - entity.animationSpeed) * 0.4F;
        entity.animationPosition += entity.animationSpeed;
    }

    @Override
    public boolean canHoldItem(ItemStack stack) {
        return this.getMainHandItem().isEmpty() && stack.is(ItemTags.SAPLINGS);
    }

    @Override
    protected void pickUpItem(ItemEntity itementity) {
        ItemStack itemstack = itementity.getItem();
        if (this.canHoldItem(itemstack)) {
            int i = itemstack.getCount();
            if (i > 1) {
                ItemEntity itementity1 = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), itemstack.split(i - 1));
                this.level.addFreshEntity(itementity1);
            }

            this.onItemPickup(itementity);
            this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
            this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
            this.take(itementity, itemstack.getCount());
            itementity.discard();
        }
    }
}