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
@Name("Set Blocks within")
@Description({"Set all blocks within 2 locations to Oraxen blocks."})
@Examples({"set all blocks within location(100, 100, 100) and player's location to oraxen block \"amethyst_ore\""})
public class EffSetBlocksBetween extends Effect {
    private Expression<Location> location1Expr;
    private Expression<Location> location2Expr;
    private Expression<String> BlockId;

    static {
        Skript.registerEffect(EffSetBlocksBetween.class, "(set|place) [all] blocks within %location% and %location% to (custom|oraxen) block %string%");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        location1Expr = (Expression<Location>) exprs[0];
        location2Expr = (Expression<Location>) exprs[1];
        BlockId = (Expression<String>) exprs[2];
        return true;
    }
    @Override
    protected void execute(Event e) {
        Location location1 = location1Expr.getSingle(e);
        Location location2 = location2Expr.getSingle(e);
        String customBlockId = BlockId.getSingle(e);

        if (location1 == null || location2 == null || customBlockId == null) {
            return;
        }

        int minX = Math.min(location1.getBlockX(), location2.getBlockX());
        int minY = Math.min(location1.getBlockY(), location2.getBlockY());
        int minZ = Math.min(location1.getBlockZ(), location2.getBlockZ());
        int maxX = Math.max(location1.getBlockX(), location2.getBlockX());
        int maxY = Math.max(location1.getBlockY(), location2.getBlockY());
        int maxZ = Math.max(location1.getBlockZ(), location2.getBlockZ());

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Location location = new Location(location1.getWorld(), x, y, z);
                    if (OraxenBlocks.isOraxenBlock(customBlockId)) {
                        OraxenBlocks.place(customBlockId, location);
                    }
                }
            }
        }
    }


    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "place block " + BlockId.toString(e, debug) + " between locations " + location1Expr.toString(e, debug) + " and " + location2Expr.toString(e, debug);
    }


}
