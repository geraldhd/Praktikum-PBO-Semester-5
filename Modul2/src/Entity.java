abstract class Entity {
    private String name;
    private int health;
    private int attack;
    private int defence;

    Entity(String name, int health, int attack, int defence ){
        this.name = name;
        this.health = health;
        this. attack = attack;
        this.defence = defence;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public int getHealth(){
        return this.health;
    }

    public void setAttack(int attack){
        this.attack = attack;
    }

    public int getAttack(){
        return this.attack;
    }

    public void setDefence(int defence){
        this.defence = defence;
    }

    public int getDefence(){
        return this.defence;
    }

    public void showEntity(){
        System.out.println( getName() + "| Health = " + getHealth() + "| Attack = " + getAttack() + "| Defence = " + getDefence());
    }
}
