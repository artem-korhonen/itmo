package moves;
import java.io.ObjectInputFilter;
import ru.ifmo.se.pokemon.*;


public class Inferno extends SpecialMove {
    public Inferno () {
        super(Type.FIRE, 100, 50);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.addEffect(new Effect().chance(0.5).condition(Status.BURN));
    }

    @Override
    protected String describe() {
        return "Does Inferno";
    }
}