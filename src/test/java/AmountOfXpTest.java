import be.elgem.Jobs.Misc.AmountOfXp;

import javax.swing.*;

public class AmountOfXpTest {
    public static void main(String[] args) {
        AmountOfXp amountOfXp = new AmountOfXp();
        amountOfXp.addXpForLevel(5, 50);
        amountOfXp.addXpForLevel(10, 20);
        amountOfXp.addXpForLevel(15, 10);
        amountOfXp.addXpForLevel(20, 1);

        while (true) {
            System.out.println(amountOfXp.getAmountOfXp(Integer.parseInt(JOptionPane.showInputDialog("Entrez la valeur : "))));
        }
    }
}
