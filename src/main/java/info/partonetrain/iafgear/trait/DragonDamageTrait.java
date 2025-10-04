package info.partonetrain.iafgear.trait;

import com.github.alexthe666.citadel.server.entity.CitadelEntityData;
import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import info.partonetrain.iafgear.IAFGear;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.silentchaos512.gear.api.traits.ITraitSerializer;
import net.silentchaos512.gear.api.traits.TraitActionContext;
import net.silentchaos512.gear.gear.trait.SimpleTrait;

import java.util.Collection;

public class DragonDamageTrait extends SimpleTrait {

    private String damageType;
    private int effectScale = 15;
    private static final int EFFECT_MULTIPLIER = 20;
    private static final float BONUS_DAMAGE = 13.5F;
    private static final float BONUS_DAMAGE_LIGHTNING = 9.5F;
    private static final int FREEZE_TICKS_KEY = 123; // Custom key for freeze ticks in CitadelEntityData

    private static final ResourceLocation SERIALIZER_ID = IAFGear.getId("dragon_damage_trait");

    public static final ITraitSerializer<DragonDamageTrait> SERIALIZER = new Serializer<>(
            SERIALIZER_ID,
            DragonDamageTrait::new,
            (trait, json) -> {
                trait.damageType = GsonHelper.getAsString(json, "damage_type", trait.getId().getPath());
            },
            (trait, buffer) -> {
                trait.damageType = buffer.readUtf();
            },
            (trait, buffer) -> {
                buffer.writeUtf(trait.damageType);
            }
    );

    @Override
    public float onAttackEntity(TraitActionContext context, LivingEntity target, float baseValue) {
        float damageToDeal = baseValue;
        switch (damageType) {
            case "fire":
                if (target instanceof EntityIceDragon) {
                    damageToDeal = baseValue + BONUS_DAMAGE;
                }
                burn(target);
                break;
            case "ice":
                if (target instanceof EntityFireDragon) {
                    damageToDeal = baseValue + BONUS_DAMAGE;
                }
                freeze(target);
                break;
            case "lightning":
                if (target instanceof EntityFireDragon || target instanceof EntityIceDragon) {
                    damageToDeal = baseValue + BONUS_DAMAGE_LIGHTNING;
                }
                lightning(target, context.getPlayer());
                break;
            default:
                break;
        }
        return damageToDeal;
    }

    public void burn(LivingEntity target) {
        target.setSecondsOnFire(effectScale);
    }

    public void freeze(LivingEntity target) {
        // Using CitadelEntityData to store freeze ticks
        CompoundTag citadelData = CitadelEntityData.getOrCreateCitadelTag(target);
        citadelData.putInt("FrozenTicks", effectScale * EFFECT_MULTIPLIER);
        CitadelEntityData.setCitadelTag(target, citadelData);

        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, effectScale * EFFECT_MULTIPLIER, 2));
    }

    public void lightning(LivingEntity target, LivingEntity attacker) {
        boolean canSpawnLightning = true;
        if (attacker.swingTime > 0.2) {
            canSpawnLightning = false;
        }
        if (!attacker.level().isClientSide && canSpawnLightning) {
            LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(target.level());
            if (lightningbolt != null) {
                lightningbolt.moveTo(target.position());
                target.level().addFreshEntity(lightningbolt);
            }
        }
    }

    public DragonDamageTrait(ResourceLocation id) {
        super(id, SERIALIZER);
    }

    @Override
    public Collection<String> getExtraWikiLines() {
        Collection<String> ret = super.getExtraWikiLines();
        switch (this.damageType) {
            case "fire":
                ret.add("Sets fire to target for " + effectScale + " seconds and deals " + BONUS_DAMAGE + " damage to Ice Dragons");
                break;
            case "ice":
                ret.add("Freezes target for " + (effectScale * EFFECT_MULTIPLIER) + " ticks and deals " + BONUS_DAMAGE + " damage to Fire Dragons");
                break;
            case "lightning":
                ret.add("Strikes target with lightning and deals " + BONUS_DAMAGE_LIGHTNING + " damage to Fire or Ice Dragons");
                break;
            default:
                ret.add("Invalid DragonDamageTrait");
                break;
        }
        return ret;
    }
}