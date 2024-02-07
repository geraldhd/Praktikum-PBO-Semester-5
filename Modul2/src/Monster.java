public class Monster extends Entity implements Behavior {
    int count =0;
    
    Monster(String name, int health, int attack, int defence ){
        super(name, health, attack, defence);
    }

    public void attack(Alien name){
        System.out.println("The Monster " + this.getName() + " Attack " + name.getName() + " with a normal attack");
        System.out.println(this.getName() + " | " + this.getHealth());
        int damage = this.getAttack() - name.getDefence();
        name.setHealth(name.getHealth() - damage);
        System.out.println(name.getName() + " | " + name.getHealth());
    }

    public void specialSkill(Alien name){
        System.out.println("The Monster use Special attack");
        int heal = this.getHealth()/2;
        this.setHealth(this.getHealth()+ heal);
        System.out.println(this.getName() + " | " + getHealth());
        System.out.println(name.getName() + " | " + name.getHealth());
    }

}
