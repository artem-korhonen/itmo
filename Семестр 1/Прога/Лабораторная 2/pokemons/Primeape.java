package pokemons;
import ru.ifmo.se.pokemon.*;

import pokemons.Mankey;
import moves.StoneEdge;


public final class Primeape extends Mankey {
    public Primeape(String name, int level) {
        super(name, level);

        super.setStats(65, 105, 60, 60, 70, 95);
        super.addMove(new StoneEdge());
    }
}
