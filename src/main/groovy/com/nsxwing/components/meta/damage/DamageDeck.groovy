package com.nsxwing.components.meta.damage

/**
 * Damage card deck. Shuffles the cards then deals them out.
 */
class DamageDeck {
    //TODO: One damage deck per player. Later.
    private static List<DamageCard> DAMAGE_DECK

    static {
        DAMAGE_DECK = [
                new BlindedPilot(), new BlindedPilot(), new ConsoleFire(), new ConsoleFire(), new DamagedCockpit(), new DamagedCockpit(),
                new DamagedEngine(), new DamagedEngine(), new DamagedSensorArray(), new DamagedSensorArray(), new InjuredPilot(), new InjuredPilot(),
                new MinorExplosion(), new MinorExplosion(), new MinorHullBreach(), new MinorHullBreach(), new MunitionsFailure(), new MunitionsFailure(),
                new StructuralDamage(), new StructuralDamage(), new StunnedPilot(), new StunnedPilot(), new ThrustControlFire(), new ThrustControlFire(),
                new WeaponMalfunction(), new WeaponMalfunction(), new DirectHit(), new DirectHit(), new DirectHit(), new DirectHit(), new DirectHit(),
                new DirectHit(), new DirectHit()
        ]

        Collections.shuffle(DAMAGE_DECK)
    }

    static DamageCard draw() {
        DAMAGE_DECK.remove(0)
    }
}
