package hu.petloc.model.character;

/**
 * A játékos karakter modellje.
 */
public class Character {
    private String name;
    private String race;
    private String characterClass;
    private String subclass;
    private int level = 1;
    private int experiencePoints = 0;
    private String origin = "";
    private int age = 20;
    private String alignment = "";
    private String religion = "";
    private String homeland = "";
    private String school = "";

    // Képességek
    private int strength = 10;
    private int endurance = 10;
    private int speed = 10;
    private int dexterity = 10;
    private int health = 10;
    private int charisma = 10;
    private int intelligence = 10;
    private int willpower = 10;
    private int astral = 10;
    private int perception = 10;

    /**
     * Üres konstruktor.
     */
    public Character() {
        // Üres konstruktor
    }

    /**
     * Konstruktor a karakter alapadataival.
     *
     * @param name           A karakter neve
     * @param race           A karakter faja
     * @param characterClass A karakter kasztja
     */
    public Character(String name, String race, String characterClass) {
        this.name = name;
        this.race = race;
        this.characterClass = characterClass;
    }

    // Getterek és setterek

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public String getSubclass() {
        return subclass;
    }

    public void setSubclass(String subclass) {
        this.subclass = subclass;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getHomeland() {
        return homeland;
    }

    public void setHomeland(String homeland) {
        this.homeland = homeland;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWillpower() {
        return willpower;
    }

    public void setWillpower(int willpower) {
        this.willpower = willpower;
    }

    public int getAstral() {
        return astral;
    }

    public void setAstral(int astral) {
        this.astral = astral;
    }

    public int getPerception() {
        return perception;
    }

    public void setPerception(int perception) {
        this.perception = perception;
    }

    /**
     * Karakter infó összesítése szövegként.
     *
     * @return Karakter adatok szövegesen
     */
    @Override
    public String toString() {
        return name + " (" + race + " " + characterClass + ") " + level + ". szint";
    }
}