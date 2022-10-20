package se233.chapter1.controller;

import se233.chapter1.model.DamageType;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;

import java.util.ArrayList;

//1.27
public class GenItemList {
//    public static BasedEquipment[] setUpItemList() {
//        BasedEquipment[] itemLists = new BasedEquipment[5];
//        itemLists[0] = new Weapon("Sword", 10, DamageType.physical, "assets/sword1.png");
//        itemLists[1] = new Weapon("Gun", 20, DamageType.physical, "assets/gun1.png");
//        itemLists[2] = new Weapon("Staff", 30, DamageType.magical, "assets/staff1.png");
//        itemLists[3] = new Armor("shirt", 0, 50, "assets/shirt1.png");
//        itemLists[4] = new Armor("armor", 50, 0, "assets/armor1.png");
//        return itemLists;
//    }
//}

    //1.31
    public static ArrayList<BasedEquipment> setUpItemList() {
        ArrayList<BasedEquipment> itemLists = new ArrayList<BasedEquipment>(8);
        itemLists.add(new Weapon("Sword", 10, DamageType.physical, "assets/sword1.png"));
        itemLists.add(new Weapon("Gun", 20, DamageType.physical, "assets/gun1.png"));
        itemLists.add(new Weapon("Staff", 30, DamageType.magical, "assets/staff1.png"));
        itemLists.add(new Armor("shirt", 0, 50, "assets/shirt1.png"));
        itemLists.add(new Armor("armor", 50, 0, "assets/armor1.png"));

        //Exercise(1)
        itemLists.add(new Weapon("Oak Staff", 25, DamageType.magical, "assets/oak-staff.png"));
        itemLists.add(new Armor("Iron Armor", 10, 30, "assets/ironarmor.png"));
        itemLists.add(new Weapon("Iron Sword", 30, DamageType.physical, "assets/sword2.png"));
        return itemLists;
    }
}

