package moves;
import ru.ifmo.se.pokemon.*;


public class ZenHeadbutt extends PhysicalMove {
    public ZenHeadbutt () {
        super(Type.PSYCHIC, 80, 90);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.addEffect(new Effect().chance(0.2).turns(1).condition(Status.SLEEP));
    }

    @Override
    protected String describe() {
        return "Does Zen Headbutt";
    }
}