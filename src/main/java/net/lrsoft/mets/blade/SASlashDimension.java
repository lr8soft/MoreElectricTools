package net.lrsoft.mets.blade;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.ability.UntouchableTime;
import mods.flammpfeil.slashblade.entity.EntityDrive;
import mods.flammpfeil.slashblade.entity.EntityJudgmentCutManager;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.event.ScheduleEntitySpawner;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialattack.IJustSpecialAttack;
import mods.flammpfeil.slashblade.specialattack.ISuperSpecialAttack;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;

import java.util.EnumSet;
import java.util.List;

import ic2.api.item.ElectricItem;
public class SASlashDimension extends SpecialAttackBase implements IJustSpecialAttack,ISuperSpecialAttack{
	private static float cost = 10000f;
    @Override
    public String toString() {
        return "SA_SlashDimensionEX";
    }

    @Override
    public void doSpacialAttack(ItemStack stack, EntityPlayer player) {
        World world = player.world;

        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);

        Entity target = null;

        int entityId = ItemSlashBlade.TargetEntityId.get(tag);

        if(entityId != 0){
            Entity tmp = world.getEntityByID(entityId);
            if(tmp != null){
                if(tmp.getDistance(player) < 40.0f)
                    target = tmp;
            }
        }

        if(target == null){
            target = getEntityToWatch(player);
        }

        if(target == null) {
            ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.SlashDim);
            
            if(!ElectricItem.manager.use(stack, cost, player))
            	return;

            player.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 0.5F, 1.0F);

            if(!player.world.isRemote) {
                int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                float magicDamage = 10.0f + ItemSlashBlade.AttackAmplifier.get(tag) *  level;
                EntitySlashDimensionEx dim = new EntitySlashDimensionEx(world, player, magicDamage);
                if (dim != null) {
                    Vec3d pos = player.getLookVec();
                    pos = pos.scale(5);
                    pos = pos.add(player.getPositionVector());
                    pos = pos.addVector(0, player.getEyeHeight(), 0);

                    Vec3d offset = player.getPositionVector().addVector(0,player.getEyeHeight(),0);
                    Vec3d offsettedLook = offset.add(player.getLookVec().scale(5));
                    RayTraceResult movingobjectposition = world.rayTraceBlocks(offset, offsettedLook);
                    if (movingobjectposition != null)
                    {
                        IBlockState state = null;
                        BlockPos blockPos = movingobjectposition.getBlockPos();
                        if(blockPos != null)
                            state = world.getBlockState(blockPos);
                        if(state != null && state.getCollisionBoundingBox(world, blockPos) == null)
                            movingobjectposition = null;
                        else {
                            Vec3d tmppos = new Vec3d(movingobjectposition.hitVec.x, movingobjectposition.hitVec.y, movingobjectposition.hitVec.z);
                            if(1 < tmppos.distanceTo(player.getPositionVector())){
                                pos = tmppos;
                            }
                        }
                    }
                    dim.setPosition(pos.x, pos.y, pos.z);
                    dim.setLifeTime(60);
                    dim.setIsSlashDimension(true);
                    world.spawnEntity(dim);
                }
            }

        }else{
            ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.SlashDim);

            if(!ElectricItem.manager.use(stack, cost, player))
            	return;

            player.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 0.52F, 0.7F);

            AxisAlignedBB bb = target.getEntityBoundingBox();
            bb = bb.expand(8.0f, 0.25f, 8.0f);

            List<Entity> list = world.getEntitiesInAABBexcluding(player, bb, EntitySelectorAttackable.getInstance());

            if(!EntitySelectorAttackable.getInstance().apply(target))
                list.add(target);

            ItemSlashBlade blade = (ItemSlashBlade)stack.getItem();

            for(Entity curEntity : list){
                StylishRankManager.setNextAttackType(player, StylishRankManager.AttackTypes.SlashDim);
                blade.attackTargetEntity(stack, curEntity, player, true);
            }

            if(!target.world.isRemote){
                int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                float magicDamage = 10.0f + ItemSlashBlade.AttackAmplifier.get(tag) * level;
                EntitySlashDimensionEx dim = new EntitySlashDimensionEx(world, player, magicDamage);
                if(dim != null){
                    dim.setPosition(target.posX,target.posY + target.height / 2.0,target.posZ);
                    dim.setLifeTime(40);
                    dim.setGlowing(true);
                    world.spawnEntity(dim);
                }
            }
        }
    }

    private Entity getEntityToWatch(EntityPlayer player){
        World world = player.world;
        Entity target = null;
        for(int dist = 2; dist < 20; dist+=2){
            AxisAlignedBB bb = player.getEntityBoundingBox();
            Vec3d vec = player.getLookVec();
            vec = vec.normalize();
            bb = bb.expand(8.0f, 0.25f, 8.0f);
            bb = bb.offset(vec.x*(float)dist,vec.y*(float)dist,vec.z*(float)dist);

            List<Entity> list = world.getEntitiesInAABBexcluding(player, bb, EntitySelectorAttackable.getInstance());
            float distance = 30.0f;
            for(Entity curEntity : list){
                float curDist = curEntity.getDistance(player);
                if(curDist < distance)
                {
                    target = curEntity;
                    distance = curDist;
                }
            }
            if(target != null)
                break;
        }
        return target;
    }

    private void spawnParticle(World world, Entity target){
        //target.spawnExplosionParticle();
        world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE,
                target.posX,
                target.posY + target.height,
                target.posZ,
                3.0, 3.0, 3.0);
        world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE,
                target.posX + 1.0,
                target.posY + target.height + 1.0,
                target.posZ,
                3.0, 3.0, 3.0);
        world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE,
                target.posX,
                target.posY + target.height + 0.5,
                target.posZ + 1.0,
                3.0, 3.0, 3.0);
    }

    @Override
    public void doJustSpacialAttack(ItemStack stack, EntityPlayer player) {
        World world = player.world;

        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);

        Entity target = null;

        int entityId = ItemSlashBlade.TargetEntityId.get(tag);

        if(entityId != 0){
            Entity tmp = world.getEntityByID(entityId);
            if(tmp != null){
                if(tmp.getDistance(player) < 30.0f)
                    target = tmp;
            }
        }

        if(target == null){
            target = getEntityToWatch(player);
        }

        if(target == null) {
            ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.SlashDim);
            
            if(!ElectricItem.manager.use(stack, cost, player))
            	return;

            player.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 0.5F, 1.0F);
            if(!player.world.isRemote) {
                Vec3d pos = player.getLookVec();
                pos = pos.scale(5);
                pos = pos.add(player.getPositionVector());
                pos = pos.addVector(0, player.getEyeHeight(), 0);

                ItemSlashBlade blade = (ItemSlashBlade)stack.getItem();

                Vec3d offset = player.getPositionVector().addVector(0,player.getEyeHeight(),0);
                Vec3d offsettedLook = offset.add(player.getLookVec().scale(5));
                RayTraceResult movingobjectposition = world.rayTraceBlocks(offset, offsettedLook);
                if (movingobjectposition != null)
                {
                    IBlockState state = null;
                    BlockPos blockPos = movingobjectposition.getBlockPos();
                    if(blockPos != null)
                        state = world.getBlockState(blockPos);
                    if(state != null && state.getCollisionBoundingBox(world, blockPos) == null)
                        movingobjectposition = null;
                    else {
                        Vec3d tmppos = new Vec3d(movingobjectposition.hitVec.x, movingobjectposition.hitVec.y, movingobjectposition.hitVec.z);
                        if(1 < tmppos.distanceTo(player.getPositionVector())){
                            pos = tmppos;
                        }
                    }
                }

                int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                float magicDamage = 10f + ItemSlashBlade.AttackAmplifier.get(tag) * level;
                EntitySlashDimensionEx dim = new EntitySlashDimensionEx(world, player, magicDamage);
                if (dim != null) {
                    dim.setPosition(pos.x, pos.y, pos.z);
                    dim.setLifeTime(60);
                    dim.setIsSlashDimension(true);
                    world.spawnEntity(dim);
                }

                for(int i = 0; i < 5;i++){

                    EntityDrive entityDrive = new EntityDrive(world, player, Math.min(1.0f,magicDamage/3.0f),false,0);


                    float rotationYaw = 60 * i + (entityDrive.getRand().nextFloat() - 0.5f) * 60;
                    float rotationPitch = (entityDrive.getRand().nextFloat() - 0.5f) * 60;

                    float fYawDtoR = (  rotationYaw / 180F) * (float)Math.PI;
                    float fPitDtoR = (rotationPitch / 180F) * (float)Math.PI;
                    float fYVecOfst = 0.5f;

                    float motionX = -MathHelper.sin(fYawDtoR) * MathHelper.cos(fPitDtoR) * fYVecOfst * 2;
                    float motionY = -MathHelper.sin(fPitDtoR) * fYVecOfst;
                    float motionZ =  MathHelper.cos(fYawDtoR) * MathHelper.cos(fPitDtoR) * fYVecOfst * 2;

                    entityDrive.setLocationAndAngles(pos.x - motionX,
                            pos.y - motionY,
                            pos.z - motionZ,
                            rotationYaw,
                            rotationPitch);
                    entityDrive.setDriveVector(fYVecOfst);
                    entityDrive.setLifeTime(20);
                    entityDrive.setIsMultiHit(false);
                    entityDrive.setRoll(90.0f + 120 * (entityDrive.getRand().nextFloat() - 0.5f));
                    if (entityDrive != null) {
                        world.spawnEntity(entityDrive);
                    }
                }
            }

        }else{
            ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.SlashDim);

            player.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 0.5F, 1.0F);

            AxisAlignedBB bb = target.getEntityBoundingBox();
            bb = bb.expand(4.0f, 0.25f, 4.0f);

            List<Entity> list = world.getEntitiesInAABBexcluding(player, bb, EntitySelectorAttackable.getInstance());

            if(!EntitySelectorAttackable.getInstance().apply(target))
                list.add(target);

            ItemSlashBlade blade = (ItemSlashBlade)stack.getItem();

            for(Entity curEntity : list){
                StylishRankManager.setNextAttackType(player, StylishRankManager.AttackTypes.SlashDim);
                blade.attackTargetEntity(stack, curEntity, player, true);
                player.onEnchantmentCritical(curEntity);
            }

            int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
            float magicDamage = 10f + ItemSlashBlade.AttackAmplifier.get(tag) * level;
            if(0 < level){
                for(int i = 0; i < 5;i++){

                    EntityDrive entityDrive = new EntityDrive(world, player, Math.min(1.0f,magicDamage/3.0f),false,0);


                    float rotationYaw = target.rotationYaw + 60 * i + (entityDrive.getRand().nextFloat() - 0.5f) * 60;
                    float rotationPitch = (entityDrive.getRand().nextFloat() - 0.5f) * 60;

                    float fYawDtoR = (  rotationYaw / 180F) * (float)Math.PI;
                    float fPitDtoR = (rotationPitch / 180F) * (float)Math.PI;
                    float fYVecOfst = 0.5f;

                    float motionX = -MathHelper.sin(fYawDtoR) * MathHelper.cos(fPitDtoR) * fYVecOfst * 2;
                    float motionY = -MathHelper.sin(fPitDtoR) * fYVecOfst;
                    float motionZ =  MathHelper.cos(fYawDtoR) * MathHelper.cos(fPitDtoR) * fYVecOfst * 2;

                    entityDrive.setLocationAndAngles(target.posX - motionX,
                            target.posY + (double) target.getEyeHeight() / 2D - motionY,
                            target.posZ - motionZ,
                            rotationYaw,
                            rotationPitch);
                    entityDrive.setDriveVector(fYVecOfst);
                    entityDrive.setLifeTime(8);
                    entityDrive.setIsMultiHit(false);


                    int rank = StylishRankManager.getStylishRank(player);
                    if(5 <= rank) {
                        EnumSet<ItemSlashBlade.SwordType> type = blade.getSwordType(stack);
                        entityDrive.setIsSlashDimension(type.contains(ItemSlashBlade.SwordType.FiercerEdge));
                    }

                    entityDrive.setRoll(90.0f + 120 * (entityDrive.getRand().nextFloat() - 0.5f));
                    if (entityDrive != null) {
                        world.spawnEntity(entityDrive);
                    }
                }
            }


            if(!target.world.isRemote){
                EntitySlashDimensionEx dim = new EntitySlashDimensionEx(world, player, magicDamage);
                if(dim != null){
                    dim.setPosition(target.posX,target.posY + target.height / 2.0,target.posZ);
                    dim.setLifeTime(60);
                    dim.setIsSlashDimension(true);
                    world.spawnEntity(dim);
                }
            }
        }
    }

    @Override
    public void doSuperSpecialAttack(ItemStack stack, EntityPlayer player) {

        EntityJudgmentCutManager entityDA = new EntityJudgmentCutManager(player.world, player);
        if (entityDA != null) {
            ScheduleEntitySpawner.getInstance().offer(entityDA);
        }
        UntouchableTime.setUntouchableTime(player, 35, true);
    }
}