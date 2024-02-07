package Modul1.src;

public class Main {
   public static void main(String[] args) {
        Baju tShirt = new Baju("Tshirt", 15000, "L");
        Sepatu sneakers = new Sepatu("sneakers", 75000, 42);
        sneakers.setStock(20);
        tShirt.setStock(20);

        Bank bankA = new Bank("Mandiri", 1);
        Bank bankB = new Bank("BRI", 1);
        User user1 = new User("John");
        User user2 = new User("Feri");
        User user3 = new User("Beni");

        bankA.setBankDiscount(10);
        bankB.setBankDiscount(30);

        user1.isiSaldo(1000000);
        user2.isiSaldo(3000000);
        user3.isiSaldo(4000);

        user2.addToOrder(sneakers, 3);
        user1.addToOrder(tShirt, 5);
        user1.addToOrder(sneakers, 1);
        user3.addToOrder(sneakers, 6);

        bankA.deactive();

        user1.payOrder(bankA);
        user2.payOrder(bankB);
        user3.payOrder(bankB);
   } 
}
