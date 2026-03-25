package pokemons;
import ru.ifmo.se.pokemon.*;

import pokemons.Pichu;
import moves.Discharge;


public class Pikachu extends Pichu {
    public Pikachu(String name, int level) {
        super(name, level);

        super.setStats(35, 55, 40, 50, 50, 90);
        super.addMove(new Discharge());
    }
}