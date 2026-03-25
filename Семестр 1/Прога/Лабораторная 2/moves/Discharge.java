package moves;
import java.io.ObjectInputFilter;
import ru.ifmo.se.pokemon.*;


public class Discharge extends SpecialMove {
    public Discharge () {
        super(Type.ELECTRIC, 80, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.addEffect(new Effect().chance(0.3).condition(Status.PARALYZE));
    }

    @Override
    protected String describe() {
        return "Does Discharge";
    }
}