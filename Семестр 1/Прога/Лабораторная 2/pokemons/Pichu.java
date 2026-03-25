package pokemons;
import ru.ifmo.se.pokemon.*;

import moves.Facade;
import moves.WildCharge;


public class Pichu extends Pokemon {
    public Pichu(String name, int level) {
        super(name, level);

        super.setType(Type.ELECTRIC);
        super.setStats(20, 40, 15, 35, 35, 60);
        super.setMove(new Facade(), new WildCharge());
    }
}
