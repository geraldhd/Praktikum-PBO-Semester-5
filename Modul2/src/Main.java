public class Main {
    public static void main(String[] args) {
        long initialSeed = System.currentTimeMillis(); 
        random customRandom = new random(initialSeed);
        int randomNumber = customRandom.nextInt();

        Alien zograd = new Alien("Zograd", 80, 20, 10);
        Monster sally = new Monster("sally", 80, 20, 10);

        sally.showEntity();
        zograd.showEntity();

        System.out.println("");
        System.out.println("Battle Start");
        System.out.println("");

        while(zograd.getHealth() >= 0 || sally.getHealth() >= 0){
            

            System.out.println(" ");
            if(sally.count % 3 == 0){
                sally.specialSkill(zograd);
            }else{
                 sally.attack(zograd);
            }
            if(zograd.getHealth() <= 0 || zograd.getHealth() == 0){
                System.out.println(" ");
                System.out.println("Alien is");
                zograd.dead();
                System.out.println(" Monster WINNNNNNNNNN");
                break;

            }
            
            System.out.println(" ");
            if(zograd.count % 4 == 0){
                zograd.specialSkill(sally);
            }else {
                zograd.attack(sally);
            }
            if(sally.getHealth() <= 0 || sally.getHealth() == 0){
                System.out.println(" ");
                System.out.println("Monster is");
                sally.dead();
                System.out.println(" Alien WINNNNNNNNNN");
                break;
                
            }
            System.out.println(" ");
            zograd.count = randomNumber;
            sally.count = randomNumber;
        }
    
    }
    
}
