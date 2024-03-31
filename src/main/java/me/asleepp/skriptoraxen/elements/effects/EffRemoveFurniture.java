package me.asleepp.skriptoraxen.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.th0rgal.oraxen.api.OraxenFurniture;
import org.bukkit.Location;
import org.bukkit.event.Event;
import javax.annotation.Nullable;
@Name("Remove Oraxen Furniture")
@Description({"Removes an Oraxen furniture at a location."})
@Examples({"remove custom furniture at player's location"})
public class EffRemoveFurniture extends Effect {

    static {
        Skript.registerEffect(EffRemoveFurniture.class, "remove [the] [custom|oraxen] furniture at %location%");
    }

    private Expression<Location> location;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        location = (Expression<Location>) exprs[0];
        return true;
    }

    @Override
    protected void execute(Event e) {
        Location loc = location.getSingle(e);
        if (loc != null) {
            OraxenFurniture.remove(loc, null);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "remove furniture at " + location.toString(e, debug);
    }
}
