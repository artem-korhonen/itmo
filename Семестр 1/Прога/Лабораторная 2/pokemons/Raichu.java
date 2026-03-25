package pokemons;
import ru.ifmo.se.pokemon.*;

import pokemons.Pikachu;
import moves.ThunderShock;


public final class Raichu extends Pikachu {
    public Raichu(String name, int level) {
        super(name, level);

        super.setStats(60, 90, 55, 90, 80, 110);
        super.addMove(new ThunderShock());
    }
}