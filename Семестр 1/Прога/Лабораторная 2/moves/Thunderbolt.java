package moves;
import ru.ifmo.se.pokemon.*;


public class Thunderbolt extends SpecialMove {
    public Thunderbolt () {
        super(Type.ELECTRIC, 90, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.addEffect(new Effect().chance(0.1).condition(Status.PARALYZE));
    }

    @Override
    protected String describe() {
        return "Does Thunderbolt";
    }
}