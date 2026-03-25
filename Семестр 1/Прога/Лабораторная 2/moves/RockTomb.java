package moves;
import ru.ifmo.se.pokemon.*;


public class RockTomb extends PhysicalMove {
    public RockTomb () {
        super(Type.ROCK, 60, 95);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.addEffect(new Effect().stat(Stat.SPEED, -1));
    }

    @Override
    protected String describe() {
        return "Does Rock Tomb";
    }
}