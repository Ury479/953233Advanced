package se233.chapter1.model.character;

import se233.chapter1.model.DamageType;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.Weapon;

//1.15
public class BasedCharacter {
    protected String name, imgpath;
    protected DamageType type;
    protected Integer fullHp, basedPow, basedDef, basedRes;
    protected Integer hp, power,defense, resistance;
    protected Weapon weapon;
    protected Armor armor;

    public String getName(){
        return name;
    }
    public String getImagepath(){
        return imgpath;
    }
    public Integer getHp(){
        return hp;
    }
    public Integer getFullHp(){
        return fullHp;
    }
    public Integer getPower(){
        return power;
    }
    public Integer getDefense(){
        return defense;
    }
    public Integer getResistance(){
        return resistance;
    }

    //1.40
    public void equipWeapon(Weapon weapon){
        this.weapon = weapon;
        this.power = this.basedPow + weapon.getPower();
    }

    //1.40
    public void equipArmor(Armor armor){
        this.armor = armor;
        this.defense = this.basedDef + armor.getDefense();
        this.resistance = this.basedRes + armor.getResistance();
    }

    @Override
    public String toString(){
        return name;
    }
    public DamageType getType() {
        return type;
    }

    //++
    public void unEquipWeapon(){
        this.power = this.basedPow;
    }

    public void unEquipArmor(){
        this.defense = basedDef;
        this.resistance = this.basedRes;
    }
}
