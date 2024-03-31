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
import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
@Name("Place Oraxen Block")
@Description({"Places an Oraxen block at a location."})
@Examples({"set block at player's location to custom block \"amethyst_ore\""})
public class EffPlaceBlock extends Effect {
    private Expression<Location> locationExpr;
    private Expression<String> oraxenBlockId;


    static {
        Skript.registerEffect(EffPlaceBlock.class, "(set|place) block at %locations% to (custom|oraxen) block %string%");
    }

    @Override
    protected void execute(Event e) {
        Location[] locations = locationExpr.getArray(e);
        String blockId = oraxenBlockId.getSingle(e);

        if (locations == null || blockId == null) {
            return;
        }

        for (Location location : locations) {
            if (OraxenBlocks.isOraxenBlock(blockId)) {
                OraxenBlocks.place(blockId, location);
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "place custom block" + oraxenBlockId.toString(e, debug) + "at" + locationExpr.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        locationExpr = (Expression<Location>) exprs[0];
        if (exprs.length > 1 && exprs[1] != null) {
            oraxenBlockId = (Expression<String>) exprs[1];
        }
        return true;
    }
}
