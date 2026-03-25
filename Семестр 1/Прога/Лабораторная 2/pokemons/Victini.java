package pokemons;
import ru.ifmo.se.pokemon.*;

import moves.Inferno;
import moves.ZenHeadbutt;
import moves.SearingShot;
import moves.DazzlingGleam;


public final class Victini extends Pokemon {
    public Victini(String name, int level) {
        super(name, level);

        super.setType(Type.PSYCHIC, Type.FIRE);
        super.setStats(100, 100, 100, 100, 100, 100);
        super.setMove(new Inferno(), new ZenHeadbutt(), new SearingShot(), new DazzlingGleam());
    }
}