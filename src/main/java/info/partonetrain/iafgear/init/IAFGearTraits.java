package info.partonetrain.iafgear.init;

import info.partonetrain.iafgear.trait.*;
import net.silentchaos512.gear.gear.trait.TraitSerializers;

public class IAFGearTraits {
    public static void registerSerializers() {
        // Register all trait serializers with Silent Gear
        TraitSerializers.register(DragonDamageTrait.SERIALIZER);
        TraitSerializers.register(PhantasmalTrait.SERIALIZER);
        TraitSerializers.register(MyrmexDamageTrait.SERIALIZER);
        TraitSerializers.register(DefenseTrait.SERIALIZER);
        TraitSerializers.register(TideGuardianTrait.SERIALIZER);
    }
}