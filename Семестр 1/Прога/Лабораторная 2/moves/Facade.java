package moves;
import ru.ifmo.se.pokemon.*;


public class Facade extends PhysicalMove {
    public Facade () {
        super(Type.NORMAL, 70, 100);
    }

    @Override
    protected void applyOppDamage(Pokemon p, double damage) {
        if ((p.getCondition() == Status.BURN)||(p.getCondition() == Status.POISON)||(p.getCondition() == Status.PARALYZE)) {
            p.setMod(Stat.ATTACK, (int) Math.round(damage * 2));
        }
    }

    @Override
    protected String describe() {
        return "Does Facade";
    }
}
