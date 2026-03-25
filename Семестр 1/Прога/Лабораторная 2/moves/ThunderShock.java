package moves;
import ru.ifmo.se.pokemon.*;


public class ThunderShock extends SpecialMove {
    public ThunderShock () {
        super(Type.ELECTRIC, 40, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.addEffect(new Effect().chance(0.1).condition(Status.PARALYZE));
    }

    @Override
    protected String describe() {
        return "Does Thunder Shock";
    }
}