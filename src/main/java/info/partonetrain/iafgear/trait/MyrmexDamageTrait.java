package info.partonetrain.iafgear.trait;

import com.github.alexthe666.iceandfire.entity.EntityDeathWorm;
import info.partonetrain.iafgear.IAFGear;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.silentchaos512.gear.api.traits.ITraitSerializer;
import net.silentchaos512.gear.api.traits.TraitActionContext;
import net.silentchaos512.gear.gear.trait.SimpleTrait;

import java.util.Collection;

public class MyrmexDamageTrait extends SimpleTrait {

    private static final float BONUS_DAMAGE = 4F;

    private static final ResourceLocation SERIALIZER_ID = IAFGear.getId("myrmex_damage_trait");

    public static final ITraitSerializer<MyrmexDamageTrait> SERIALIZER = new Serializer<>(
            SERIALIZER_ID,
            MyrmexDamageTrait::new
    );

    @Override
    public float onAttackEntity(TraitActionContext context, LivingEntity target, float baseValue) {
        // Changed from getCreatureAttribute() to getMobType()
        if ((target.getMobType() != MobType.ARTHROPOD) || target instanceof EntityDeathWorm) {
            return baseValue + BONUS_DAMAGE;
        } else {
            return baseValue;
        }
    }

    public MyrmexDamageTrait(ResourceLocation id) {
        super(id, SERIALIZER);
    }

    @Override
    public Collection<String> getExtraWikiLines() {
        Collection<String> ret = super.getExtraWikiLines();
        ret.add("Deals " + BONUS_DAMAGE + " extra damage to non-arthropods and Death Worms");
        return ret;
    }
}