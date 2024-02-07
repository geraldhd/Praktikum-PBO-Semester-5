package Modul1.src;

public class Order {
    private Barang barang;
    private int amount;

    Order(Barang barang, int amount){
        this.barang = barang;
        this.amount = amount;
    }

    int getTotalPrice(){
        return this.barang.getPrice() * this.amount;
    }

    public Barang getBarang(){
        return this.barang;
    }

    public int getAmount(){
        return this.amount;
    }

    void printNota(){
        System.out.println(barang.getName() + " x " + this.amount + " = Rp." + getTotalPrice());
    }

}
