package moves;
import ru.ifmo.se.pokemon.*;


public class SearingShot extends SpecialMove {
    public SearingShot () {
        super(Type.FIRE, 100, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.addEffect(new Effect().chance(0.3).condition(Status.BURN));
    }

    @Override
    protected String describe() {
        return "Does Searing Shot";
    }
}