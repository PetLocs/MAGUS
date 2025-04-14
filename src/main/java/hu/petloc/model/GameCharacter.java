package hu.petloc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A játékos karakter modellje.
 * Ez az osztály tárolja a karakter összes tulajdonságát és adatát.
 */
public class GameCharacter {

    // Alapvető adatok
    private String name = "";
    private String race = "";
    private String characterClass = "";
    private String subclass = "";

    // Háttértörténeti adatok
    private int age = 20;
    private String alignment = "";
    private String religion = "";
    private String homeland = "";
    private String school = "";
    private int level = 1;

    // Képességértékek (3-18)
    private int strength = 10;
    private int speed = 10;
    private int dexterity = 10;
    private int endurance = 10;
    private int health = 10;
    private int charisma = 10;
    private int intelligence = 10;
    private int willpower = 10;
    private int astral = 10;
    private int perception = 10;

    // Eseménynapló
    private List<String> logEntries = new ArrayList<>();

    /**
     * Új karakter létrehozása.
     */
    public GameCharacter() {
        // Alapértelmezett konstruktor
    }

    /**
     * Karakter létrehozása alapvető adatokkal.
     *
     * @param name A karakter neve
     * @param race A karakter faja
     * @param characterClass A karakter kasztja
     */
    public GameCharacter(String name, String race, String characterClass) {
        this.name = name;
        this.race = race;
        this.characterClass = characterClass;
    }

    /**
     * Karakter név lekérése.
     *
     * @return A karakter neve
     */
    public String getName() {
        return name;
    }

    /**
     * Karakter név beállítása.
     *
     * @param name Az új név
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Karakter faj lekérése.
     *
     * @return A karakter faja
     */
    public String getRace() {
        return race;
    }

    /**
     * Karakter faj beállítása.
     *
     * @param race Az új faj
     */
    public void setRace(String race) {
        this.race = race;
    }

    /**
     * Karakter kaszt lekérése.
     *
     * @return A karakter kasztja
     */
    public String getCharacterClass() {
        return characterClass;
    }

    /**
     * Karakter kaszt beállítása.
     *
     * @param characterClass Az új kaszt
     */
    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    /**
     * Karakter alkaszt lekérése.
     *
     * @return A karakter alkasztja
     */
    public String getSubclass() {
        return subclass;
    }

    /**
     * Karakter alkaszt beállítása.
     *
     * @param subclass Az új alkaszt
     */
    public void setSubclass(String subclass) {
        this.subclass = subclass;
    }

    /**
     * Karakter életkor lekérése.
     *
     * @return A karakter életkora
     */
    public int getAge() {
        return age;
    }

    /**
     * Karakter életkor beállítása.
     *
     * @param age Az új életkor
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Karakter jellem lekérése.
     *
     * @return A karakter jelleme
     */
    public String getAlignment() {
        return alignment;
    }

    /**
     * Karakter jellem beállítása.
     *
     * @param alignment Az új jellem
     */
    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    /**
     * Karakter vallás lekérése.
     *
     * @return A karakter vallása
     */
    public String getReligion() {
        return religion;
    }

    /**
     * Karakter vallás beállítása.
     *
     * @param religion Az új vallás
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /**
     * Karakter szülőföld lekérése.
     *
     * @return A karakter szülőföldje
     */
    public String getHomeland() {
        return homeland;
    }

    /**
     * Karakter szülőföld beállítása.
     *
     * @param homeland Az új szülőföld
     */
    public void setHomeland(String homeland) {
        this.homeland = homeland;
    }

    /**
     * Karakter iskola lekérése.
     *
     * @return A karakter iskolája
     */
    public String getSchool() {
        return school;
    }

    /**
     * Karakter iskola beállítása.
     *
     * @param school Az új iskola
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * Karakter szint lekérése.
     *
     * @return A karakter szintje
     */
    public int getLevel() {
        return level;
    }

    /**
     * Karakter szint beállítása.
     *
     * @param level Az új szint
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Karakter erő képesség lekérése.
     *
     * @return A karakter ereje
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Karakter erő képesség beállítása.
     *
     * @param strength Az új erő érték
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * Karakter állóképesség képesség lekérése.
     *
     * @return A karakter állóképessége
     */
    public int getEndurance() {
        return endurance;
    }

    /**
     * Karakter állóképesség képesség beállítása.
     *
     * @param endurance Az új állóképesség érték
     */
    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    /**
     * Karakter ügyesség képesség lekérése.
     *
     * @return A karakter ügyessége
     */
    public int getDexterity() {
        return dexterity;
    }

    /**
     * Karakter ügyesség képesség beállítása.
     *
     * @param dexterity Az új ügyesség érték
     */
    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    /**
     * Karakter gyorsaság képesség lekérése.
     *
     * @return A karakter gyorsasága
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Karakter gyorsaság képesség beállítása.
     *
     * @param speed Az új gyorsaság érték
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Karakter intelligencia képesség lekérése.
     *
     * @return A karakter intelligenciája
     */
    public int getIntelligence() {
        return intelligence;
    }

    /**
     * Karakter intelligencia képesség beállítása.
     *
     * @param intelligence Az új intelligencia érték
     */
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    /**
     * Karakter akaraterő képesség lekérése.
     *
     * @return A karakter akaratereje
     */
    public int getWillpower() {
        return willpower;
    }

    /**
     * Karakter akaraterő képesség beállítása.
     *
     * @param willpower Az új akaraterő érték
     */
    public void setWillpower(int willpower) {
        this.willpower = willpower;
    }

    /**
     * Karakter asztrál képesség lekérése.
     *
     * @return A karakter asztrálja
     */
    public int getAstral() {
        return astral;
    }

    /**
     * Karakter asztrál képesség beállítása.
     *
     * @param astral Az új asztrál érték
     */
    public void setAstral(int astral) {
        this.astral = astral;
    }

    /**
     * Karakter karizma képesség lekérése.
     *
     * @return A karakter karizmája
     */
    public int getCharisma() {
        return charisma;
    }

    /**
     * Karakter karizma képesség beállítása.
     *
     * @param charisma Az új karizma érték
     */
    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    /**
     * Karakter egészség képesség lekérése.
     *
     * @return A karakter egészsége
     */
    public int getHealth() {
        return health;
    }

    /**
     * Karakter egészség képesség beállítása.
     *
     * @param health Az új egészség érték
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Karakter érzékelés képesség lekérése.
     *
     * @return A karakter érzékelése
     */
    public int getPerception() {
        return perception;
    }

    /**
     * Karakter érzékelés képesség beállítása.
     *
     * @param perception Az új érzékelés érték
     */
    public void setPerception(int perception) {
        this.perception = perception;
    }

    /**
     * @deprecated Használd helyette a getCharisma() metódust
     * Karakter szépség képesség lekérése (kompatibilitás miatt).
     *
     * @return A karakter karizmája
     */
    @Deprecated
    public int getBeauty() {
        return charisma;
    }

    /**
     * @deprecated Használd helyette a setCharisma() metódust
     * Karakter szépség képesség beállítása (kompatibilitás miatt).
     *
     * @param beauty Az új karizma érték
     */
    @Deprecated
    public void setBeauty(int beauty) {
        this.charisma = beauty;
    }

    /**
     * Naplóbejegyzések lekérése.
     *
     * @return A naplóbejegyzések listája
     */
    public List<String> getLogEntries() {
        return logEntries;
    }

    /**
     * Naplóbejegyzés hozzáadása.
     *
     * @param entry Az új bejegyzés
     */
    public void addLogEntry(String entry) {
        logEntries.add(entry);
    }

    /**
     * Naplóbejegyzések törlése.
     */
    public void clearLogEntries() {
        logEntries.clear();
    }
}