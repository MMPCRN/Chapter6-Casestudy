package se233.chapter1.model.character;

import se233.chapter1.model.DamageType;

public class BattleMage extends BasedCharacter{
    public BattleMage(String name, String imgPath, int basedDef, int basedRes)
    {
        this.name = name;
        this.type = DamageType.battleMage;
        this.imgPath = imgPath;
        this.fullHp = 40;
        this.basedPow = 40;
        this.basedDef = basedDef;
        this.basedRes = basedRes;
        this.hp = this.fullHp;
        this.power = basedPow;
        this.defense = basedDef;
        this.resistance = basedRes;
    }
}
