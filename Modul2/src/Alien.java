public class Alien extends Entity implements Behavior {
    int count = 0;
    
    Alien(String name, int health, int attack, int defence ){
        super(name, health, attack, defence);
    }

    public void attack(Monster name){
        System.out.println("The Alien " + this.getName() + " Attack " + name.getName() + " with a normal attack");
        System.out.println(getName() + " | " + getHealth());
        int damage = this.getAttack() - name.getDefence();
        name.setHealth(name.getHealth() - damage);
        System.out.println(name.getName() + " | " + name.getHealth());
    }

    public void specialSkill(Monster name){
        System.out.println("The Alien " + this.getName() + " Attack  " + name.getName() + " with a Special attack");
        System.out.println(this.getName() + " | " + getHealth());
        int damage = this.getAttack()*2 ;
        name.setHealth(name.getHealth() - damage);
        System.out.println(name.getName() + " | " + name.getHealth());
    }
}
