package Modul1.src;

import java.util.ArrayList;

public class User {
    private String name;
    private double saldo;
    ArrayList<Order> order = new ArrayList<Order>();

    User(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void isiSaldo(double jumlah) {
        saldo += jumlah;
        if (jumlah >= 50000) {
            System.out.println(getName() + " Saldo berhasil diisi sebesar Rp" + jumlah);
        } else {
            System.out.println(getName() + " Pengisian saldo gagal. Jumlah minimal pengisian saldo adalah Rp50,000 ");
        }
    }

    public void minSaldo(double jumlah) {
        this.saldo = this.saldo - jumlah;
        System.out.println("Sisa Saldo: Rp." + this.saldo);
    }

    public double getSaldo() {
        return this.saldo;
    }

    void addToOrder(Barang product, int amount) {
        Order orderan = new Order(product, amount);
        order.add(orderan);
    }

    void payOrder(Bank bank) {
        System.out.println("Nota " + getName());
        System.out.println("Bank Di gunakan: " + bank.getName());
        if (bank.isActive()) {

            System.out.println("Diskon bank: " + bank.getBankDiscount() + "%");

            for (int i = 0; i < order.size(); i++) {
                int diskon = order.get(i).getTotalPrice() * bank.getBankDiscount() / 100;
                // System.out.println(order.get(i).getBarang().getName() + " x " + order.get(i).getAmount() + " = Rp"
                //         + order.get(i).getTotalPrice());
                order.get(i).printNota();
                System.out.println("Total belanja sebelum diskon: Rp" + order.get(i).getTotalPrice());
                System.out.println("Total belanja setelah diskon: Rp" + (order.get(i).getTotalPrice() - diskon));
                if (getSaldo() >= order.get(i).getTotalPrice() - diskon) {
                    minSaldo(order.get(i).getTotalPrice() - diskon);
                    order.get(i).getBarang().reduceStock(order.get(i).getAmount());
                } else {
                    System.out.println("Pembayaran gagal (Saldo tidak mencukupi)");
                }
            }
        } else {
            System.out.println("Pembayaran tidak dapat diproses karena bank tidak aktif");
        }
        System.out.println();

    }
}
