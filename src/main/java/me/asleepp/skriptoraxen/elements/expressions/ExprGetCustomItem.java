package me.asleepp.skriptoraxen.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import io.th0rgal.oraxen.api.OraxenItems;
import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
@Name("Oraxe Item")
@Description({"Gets an Oraxen item."})
@Examples({"give player oraxen item \"amethyst\""})
@Since("1.0")
public class ExprGetCustomItem extends SimpleExpression<ItemType> {

    private Expression<String> id;

    static {
        Skript.registerExpression(ExprGetCustomItem.class, ItemType.class, ExpressionType.SIMPLE, "(custom|oraxen) item %string%");
    }

    @Override
    protected ItemType[] get(Event e) {
        String oraxenId = id.getSingle(e);
        if (OraxenItems.exists(oraxenId)) {
            ItemStack item = OraxenItems.getItemById(oraxenId).build();
            return new ItemType[]{new ItemType(item)};
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends ItemType> getReturnType() {
        return ItemType.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "Oraxen item " + id.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) exprs[0];
        return true;
    }
}
