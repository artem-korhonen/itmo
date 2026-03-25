import pokemons.*;
import ru.ifmo.se.pokemon.*;

public class lab2 {
    public static void main(String[] args) {
        Battle b = new Battle();

        Mankey p1 = new Mankey("Mankey", 1);
        Pichu p2 = new Pichu("Pichu", 1);
        Pikachu p3 = new Pikachu("Pikachu", 1);
        Primeape p4 = new Primeape("Primeape", 1);
        Raichu p5 = new Raichu("Raichu", 1);
        Victini p6 = new Victini("Victini", 1);
        b.addAlly(p1);
        b.addAlly(p3);
        b.addAlly(p5);
        b.addFoe(p2);
        b.addFoe(p4);
        b.addFoe(p6);
        b.go();
    }
}
