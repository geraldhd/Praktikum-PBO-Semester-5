package Modul1.src;

public class Bank extends User{
    private int id;
    private boolean active = true;
    private int bankDiscount = 0;//seharusnya ini double
    
    public Bank(String name, int id){
        super(name);
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setBankDiscount(int discount){
        this.bankDiscount = discount;
    }

    public int getBankDiscount(){
        return this.bankDiscount;
    }

    public void active(){
        this.active = true;
    }

    public void deactive(){
        this.active = false;
    }

    public boolean isActive(){
        return this.active;
    }
}
