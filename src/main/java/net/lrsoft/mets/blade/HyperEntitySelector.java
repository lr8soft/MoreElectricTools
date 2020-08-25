package net.lrsoft.mets.blade;
import com.google.common.base.Predicate;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.entity.EntityGrimGrip;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.player.EntityPlayer;
public final class HyperEntitySelector implements Predicate<Entity>{

    private HyperEntitySelector(){}

    private static final class SingletonHolder {
        private static final Predicate<Entity> instance = new HyperEntitySelector();
    }

    public static Predicate<Entity> getInstance(){
        return SingletonHolder.instance;
    }

    @Override
    public boolean apply(Entity input) {
        boolean result = false;

        String entityStr = EntityList.getEntityString(input);

        if (((entityStr != null && SlashBlade.manager.attackableTargets.containsKey(entityStr) && SlashBlade.manager.attackableTargets.get(entityStr))
                || input instanceof IEntityMultiPart
                || input instanceof EntityGrimGrip
                || input instanceof EntityLivingBase
        ))
            result = input.isEntityAlive();

        return result;
    }
}
