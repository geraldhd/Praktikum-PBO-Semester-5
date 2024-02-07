package Modul1.src;

public class Barang {
    private String name;
    private int price;
    private int stock = 20;

    Barang(String name, int price){
        this.name = name;
        this.price = price;
    }

    public String getName(){
        return this.name;
    }

    public int getPrice(){
        return this.price;
    }

    public int getStock(){
        return this.stock;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public void reduceStock(int amount){
        this.stock = stock - amount;
        System.out.println("Reduced " + getName() + " stock by " + amount);
    }
}
