package me.asleepp.skriptoraxen.elements.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;


@Name("Is Oraxen Item")
@Description({"Checks if the Item is an Oraxen item."})
@Examples({"if player's tool is a custom item"})

public class CondIsCustomItem extends Condition {

    private Expression<ItemType> item;

    static {
        Skript.registerCondition(CondIsCustomItem.class, "%itemtypes% (is [a[n]]|are) (custom|oraxen) item[s]");
    }
    @Override
    public boolean check(Event e) {
        ItemType[] items = item.getArray(e);
        if (items == null) {
            return false;
        }

        for (ItemType itemType : items) {
            ItemStack itemStack = itemType.getRandom();
            if (itemStack == null) {
                return false;
            }
            boolean exists = OraxenItems.exists(itemStack);
            if (!exists) {
                return false;
            }
        }
        return true;
    }
    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return item.toString(e, debug) + " is an Oraxen item";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        item = (Expression<ItemType>) exprs[0];
        return true;
    }
}
