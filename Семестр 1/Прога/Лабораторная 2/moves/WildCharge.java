package moves;
import ru.ifmo.se.pokemon.*;


public class WildCharge extends PhysicalMove {
    public WildCharge () {
        super(Type.ELECTRIC, 90, 100);
    }

    @Override
    protected void applySelfDamage(Pokemon p, double damage) {
        p.setMod(Stat.HP, (int) Math.round(damage / 4));
    }

    @Override
    protected String describe() {
        return "Does Wild Charge";
    }
}