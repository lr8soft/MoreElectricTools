package net.lrsoft.mets.entity;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.EntityDrive;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorDestructable;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.util.ReflectionAccessHelper;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class EntityCraftRev extends Entity implements IThrowableEntity {
    protected Entity thrower;
    protected ItemStack blade = ItemStack.EMPTY;
    protected List<Entity> alreadyHitEntity = new ArrayList<Entity>();
    private float causeDamage=30;
    public EntityCraftRev(World par1World)
    {
        super(par1World);
        ticksExisted = 0;
    }
    public EntityCraftRev(World par1World, EntityLivingBase entityLiving,float damage)
    {
        this(par1World);
        this.causeDamage=damage;
        thrower = entityLiving;
        setThrowerEntityID(thrower.getEntityId());
        blade = entityLiving.getHeldItem(EnumHand.MAIN_HAND);
        if(!blade.isEmpty() && !(blade.getItem() instanceof ItemSlashBlade)){
            blade = ItemStack.EMPTY;
        }
        alreadyHitEntity.clear();
        alreadyHitEntity.add(thrower);
        alreadyHitEntity.add(thrower.getRidingEntity());
        alreadyHitEntity.addAll(thrower.getPassengers());
        ticksExisted = 0;
        setSize(64.0F, 32.0F);
        setLocationAndAngles(thrower.posX,
                thrower.posY,
                thrower.posZ,
                thrower.rotationYaw,
                thrower.rotationPitch);
    }

    private static final DataParameter<Integer> ThrowerEntityID = EntityDataManager.<Integer>createKey(EntityCraftRev.class, DataSerializers.VARINT);

    @Override
    protected void entityInit() {
        this.getDataManager().register(ThrowerEntityID, 0);
    }
    int getThrowerEntityID(){
        return this.getDataManager().get(ThrowerEntityID);
    }

    void setThrowerEntityID(int id){
        this.getDataManager().set(ThrowerEntityID,id);
    }
    @Override
    public void onUpdate()
    {
        //super.onUpdate();

        if(this.getThrower() != null){
            this.getThrower().motionY = 0;
        }

        if(this.thrower == null && this.getThrowerEntityID() != 0){
            this.thrower = this.world.getEntityByID(this.getThrowerEntityID());
        }

        if(this.blade.isEmpty() && this.getThrower() != null && this.getThrower() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)this.getThrower();
            ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
            if(stack.getItem() instanceof ItemSlashBlade)
                this.blade = stack;
        }

        if(this.ticksExisted == 1 && this.getThrower() != null) {
            this.getThrower().motionX = 0;
            this.getThrower().motionY = 0;
            this.getThrower().motionZ = 0;

            if(this.getThrower() != null){
                this.getThrower().playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP,
                        0.8F, 0.01F);
            }

            doAttack(ItemSlashBlade.ComboSequence.SlashEdge);
        }
        if(ticksExisted >= 5) {

            if(!blade.isEmpty()){
                NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(blade);
                ItemSlashBlade bladeItem = (ItemSlashBlade) blade.getItem();

                ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.ReturnEdge);
                if(this.getThrower() != null && this.getThrower() instanceof EntityLivingBase) {
                    bladeItem.doSwingItem(blade, (EntityLivingBase) this.getThrower());

                    this.getThrower().playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP,
                            0.8F, 0.01F);
                }

                doAttack(ItemSlashBlade.ComboSequence.ReturnEdge);
            }

            alreadyHitEntity.clear();
            alreadyHitEntity = null;
            setDead();
        }
    }
    public void doAttack(ItemSlashBlade.ComboSequence combo){
        if(blade.isEmpty()) return;
        if(!(blade.getItem() instanceof  ItemSlashBlade)) return;

        ItemSlashBlade itemBlade = (ItemSlashBlade)blade.getItem();

        if(world.isRemote) return;
        if(!(this.getThrower() instanceof EntityLivingBase)) return;
        EntityLivingBase entityLiving = (EntityLivingBase)this.getThrower();

        double dAmbit = 0.5D;
        AxisAlignedBB bb = itemBlade.getBBofCombo(blade, combo, entityLiving);

        bb = bb.grow(0,dAmbit,0);

        List<Entity> list = this.world.getEntitiesInAABBexcluding(this.getThrower(), bb, EntitySelectorDestructable.getInstance());

        StylishRankManager.setNextAttackType(this.thrower, StylishRankManager.AttackTypes.DestructObject);

        list.removeAll(alreadyHitEntity);
        alreadyHitEntity.addAll(list);
        for(Entity curEntity : list){
            if(blade.isEmpty()) break;

            boolean isDestruction = true;

            if(curEntity instanceof EntityFireball){
                if((((EntityFireball)curEntity).shootingEntity != null && ((EntityFireball)curEntity).shootingEntity.getEntityId() == entityLiving.getEntityId())){
                    isDestruction = false;
                }else{
                    isDestruction = !curEntity.attackEntityFrom(DamageSource.causeMobDamage(entityLiving), 1.0f);
                }
            }else if(curEntity instanceof EntityArrow){
                if((((EntityArrow)curEntity).shootingEntity != null && ((EntityArrow)curEntity).shootingEntity.getEntityId() == entityLiving.getEntityId())){
                    isDestruction = false;
                }
            }else if(curEntity instanceof IThrowableEntity){
                if((((IThrowableEntity)curEntity).getThrower() != null && ((IThrowableEntity)curEntity).getThrower().getEntityId() == entityLiving.getEntityId())){
                    isDestruction = false;
                }
            }else if(curEntity instanceof EntityThrowable){
                if((((EntityThrowable)curEntity).getThrower() != null && ((EntityThrowable)curEntity).getThrower().getEntityId() == entityLiving.getEntityId())){
                    isDestruction = false;
                }
            }

            if(!isDestruction)
                continue;
            else{
                ReflectionAccessHelper.setVelocity(curEntity, 0, 0, 0);
                curEntity.setDead();

                for (int var1 = 0; var1 < 10; ++var1)
                {
                    Random rand = this.getRand();
                    double var2 = rand.nextGaussian() * 0.02D;
                    double var4 = rand.nextGaussian() * 0.02D;
                    double var6 = rand.nextGaussian() * 0.02D;
                    double var8 = 10.0D;
                    this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL
                            , curEntity.posX + (double)(rand.nextFloat() * curEntity.width * 2.0F) - (double)curEntity.width - var2 * var8
                            , curEntity.posY + (double)(rand.nextFloat() * curEntity.height) - var4 * var8
                            , curEntity.posZ + (double)(rand.nextFloat() * curEntity.width * 2.0F) - (double)curEntity.width - var6 * var8
                            , var2, var4, var6);
                }
            }

            StylishRankManager.doAttack(this.thrower);
        }


        list = this.world.getEntitiesInAABBexcluding(this.getThrower(), bb, EntitySelectorAttackable.getInstance());
        list.removeAll(alreadyHitEntity);


        StylishRankManager.setNextAttackType(this.thrower ,StylishRankManager.AttackTypes.Spear);

        if(!blade.isEmpty()){
            NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(blade);
            for(Entity curEntity : list){
                if(blade.isEmpty()) break;

                curEntity.hurtResistantTime = 0;
                if(thrower instanceof EntityPlayer){
                    itemBlade.attackTargetEntity(blade, curEntity, (EntityPlayer)thrower, true);
                }
                else{
                    DamageSource ds = new EntityDamageSource("mob", this.getThrower());
                    curEntity.attackEntityFrom(ds, 10);
                    if(!blade.isEmpty() && curEntity instanceof EntityLivingBase)
                        ((ItemSlashBlade)blade.getItem()).hitEntity(blade,(EntityLivingBase)curEntity,(EntityLivingBase)thrower);
                }
            }
        }
        EntityDrive entityDrive = new EntityDrive(this.world, entityLiving, causeDamage,true,90.0f - Math.abs(combo.swingDirection));
        if (entityDrive != null) {
            entityDrive.setInitialSpeed(3f);
            entityDrive.setLifeTime(20);
            this.world.spawnEntity(entityDrive);
        }
    }
    public Random getRand()
    {
        return this.rand;
    }
    @Override
    public boolean isOffsetPositionInLiquid(double par1, double par3, double par5)
    {
        return false;
    }
    @Override
    public void move(MoverType moverType, double par1, double par3, double par5) {}
    @Override
    protected void dealFireDamage(int par1) {}
    @Override
    public boolean handleWaterMovement()
    {
        return false;
    }
    @Override
    public boolean isInsideOfMaterial(Material par1Material)
    {
        return false;
    }
    @SideOnly(Side.CLIENT)
    @Override
    public int getBrightnessForRender()
    {
        float f1 = 0.5F;

        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if (f1 > 1.0F)
        {
            f1 = 1.0F;
        }

        int i = super.getBrightnessForRender();
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f1 * 15.0F * 16.0F);

        if (j > 240)
        {
            j = 240;
        }

        return j | k << 16;
    }
    @Override
    public float getBrightness()
    {
        float f1 = super.getBrightness();
        float f2 = 0.9F;
        f2 = f2 * f2 * f2 * f2;
        return f1 * (1.0F - f2) + f2;
        //return super.getBrightness();
    }
    @Override
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {}
    @Override
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {}
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {}

    @Override
    public void setPortal(BlockPos p_181015_1_) {
    }
    @Override
    public boolean isBurning()
    {
        return false;
    }

    @Override
    public boolean shouldRenderInPass(int pass)
    {
        return pass == 1;
    }


    @Override
    public void setInWeb() {}


    @Override
    public Entity getThrower() {
        return this.thrower;
    }

    @Override
    public void setThrower(Entity entity) {
        this.thrower = entity;
    }
}