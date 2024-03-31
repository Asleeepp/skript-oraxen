package me.asleepp.skriptoraxen.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.th0rgal.oraxen.api.OraxenBlocks;
import io.th0rgal.oraxen.api.OraxenFurniture;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Event;
import org.bukkit.Location;

import javax.annotation.Nullable;
@Name("Place Oraxen Furniture")
@Description({"Places an Oraxen furniture at a location."})
@Examples({"set block at player's location to custom furniture \"chair\""})
public class EffPlaceFurniture extends Effect {

    private Expression<String> furnitureId;
    private Expression<Location> location;

    static {
        Skript.registerEffect(EffPlaceFurniture.class, "(set|place) [custom|oraxen] furniture %string% at %location%");
    }

    @Override
    protected void execute(Event e) {
        String id = furnitureId.getSingle(e);
        Location loc = location.getSingle(e);

        if (id != null && loc != null) {
            OraxenFurniture.place(id, loc.getBlock().getLocation(), loc.getYaw(), loc.getBlock().getFace(loc.getBlock()));
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "place furniture" + furnitureId.toString(e, debug) + "at" + location.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        furnitureId = (Expression<String>) exprs[0];
        location = (Expression<Location>) exprs[1];
        return true;
    }
}
