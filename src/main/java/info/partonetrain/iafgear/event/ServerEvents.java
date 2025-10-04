package info.partonetrain.iafgear.event;

import com.github.alexthe666.iceandfire.misc.IafDamageRegistry;
import info.partonetrain.iafgear.IAFGear;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.silentchaos512.gear.util.TraitHelper;

@Mod.EventBusSubscriber(modid = IAFGear.MOD_ID)
public class ServerEvents {

    private static final ResourceLocation DRAGON_PROTECTION_TRAIT_RL = new ResourceLocation("iafgear:dragon_defense");
    private static final ResourceLocation PROJECTILE_PROTECTION_TRAIT_RL = new ResourceLocation("iafgear:projectile_defense");
    private static final ResourceLocation TIDE_GUARDIAN_TRAIT_RL = new ResourceLocation("iafgear:tide_guardian");

    @SubscribeEvent
    public static void onEntityDamage(LivingHurtEvent event) {
        ItemStack h = event.getEntity().getItemBySlot(EquipmentSlot.HEAD);
        ItemStack c = event.getEntity().getItemBySlot(EquipmentSlot.CHEST);
        ItemStack l = event.getEntity().getItemBySlot(EquipmentSlot.LEGS);
        ItemStack f = event.getEntity().getItemBySlot(EquipmentSlot.FEET);

        if (event.getSource().isIndirect()) { // Changed from isProjectile()
            float multi = 1;
            if (TraitHelper.getTraitLevel(h, PROJECTILE_PROTECTION_TRAIT_RL) != 0) {
                multi -= 0.1;
            }
            if (TraitHelper.getTraitLevel(c, PROJECTILE_PROTECTION_TRAIT_RL) != 0) {
                multi -= 0.3;
            }
            if (TraitHelper.getTraitLevel(l, PROJECTILE_PROTECTION_TRAIT_RL) != 0) {
                multi -= 0.2;
            }
            if (TraitHelper.getTraitLevel(f, PROJECTILE_PROTECTION_TRAIT_RL) != 0) {
                multi -= 0.1;
            }
            event.setAmount(event.getAmount() * multi);
        }

        // Check if damage source is dragon fire or ice
        // In 1.20.1, we need to check the damage type key
        if (event.getSource().type().equals(IafDamageRegistry.DRAGON_FIRE_TYPE) ||
                event.getSource().type().equals(IafDamageRegistry.DRAGON_ICE_TYPE)) {
            float multi = 1;
            if (TraitHelper.getTraitLevel(h, DRAGON_PROTECTION_TRAIT_RL) != 0) {
                multi -= 0.1;
            }
            if (TraitHelper.getTraitLevel(c, DRAGON_PROTECTION_TRAIT_RL) != 0) {
                multi -= 0.3;
            }
            if (TraitHelper.getTraitLevel(l, DRAGON_PROTECTION_TRAIT_RL) != 0) {
                multi -= 0.2;
            }
            if (TraitHelper.getTraitLevel(f, DRAGON_PROTECTION_TRAIT_RL) != 0) {
                multi -= 0.1;
            }
            event.setAmount(event.getAmount() * multi);
        }
    }

    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingTickEvent event) { // Changed from LivingUpdateEvent
        ItemStack itemStack;
        int amountEquipped = 0;

        for (EquipmentSlot e : EquipmentSlot.values()) {
            itemStack = event.getEntity().getItemBySlot(e);
            if (TraitHelper.getTraitLevel(itemStack, TIDE_GUARDIAN_TRAIT_RL) != 0) {
                if (event.getEntity().isInWaterOrRain()) { // Changed from isWet()
                    amountEquipped++;
                }
            }
        }
        if (amountEquipped >= 1) {
            event.getEntity().addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 50, amountEquipped - 1, false, false));
        }
    }
}