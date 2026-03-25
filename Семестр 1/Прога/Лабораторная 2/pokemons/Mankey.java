package pokemons;
import ru.ifmo.se.pokemon.*;

import moves.Thunderbolt;
import moves.Confide;
import moves.RockTomb;


public class Mankey extends Pokemon {
    public Mankey(String name, int level) {
        super(name, level);

        super.setType(Type.FIGHTING);
        super.setStats(40, 80, 35, 35, 45, 70);
        super.setMove(new Thunderbolt(), new Confide(), new RockTomb());
    }
}