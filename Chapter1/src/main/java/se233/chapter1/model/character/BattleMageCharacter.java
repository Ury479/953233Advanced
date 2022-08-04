package se233.chapter1.model.character;

import se233.chapter1.model.DamageType;

public class BattleMageCharacter extends BasedCharacter {
    //Exercise(2)
    public BattleMageCharacter(String name, String imgpath, int baseDef, int basedRes){
        this.name = name;
        this.type = DamageType.magical;
        this.imgpath = imgpath;
        this.fullHp = 40;
        this.basedPow = 40;
        this.basedDef = baseDef;
        this.basedRes = basedRes;
        this.hp = this.fullHp;
        this.power = this.basedPow;
        this.defense = baseDef;
        this.resistance = basedRes;
    }
}
