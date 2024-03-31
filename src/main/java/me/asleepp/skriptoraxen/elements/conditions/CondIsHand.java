package me.asleepp.skriptoraxen.elements.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.th0rgal.oraxen.api.events.furniture.OraxenFurnitureInteractEvent;
import io.th0rgal.oraxen.api.events.noteblock.OraxenNoteBlockInteractEvent;
import io.th0rgal.oraxen.api.events.stringblock.OraxenStringBlockInteractEvent;
import org.bukkit.event.Event;
import org.bukkit.inventory.EquipmentSlot;

import javax.annotation.Nullable;

@Name("Is Hand")
@Description({"Checks what hand the player interacted with a block/furniture is. Does not work on any other events other then Interaction events."})
@Examples({"if custom interaction was the player's left hand:"})
public class CondIsHand extends Condition {

    static {
        Skript.registerCondition(CondIsHand.class, "[custom|oraxen] (interaction) (was [with]) [the[ir] | [the player's]] (:right|:left) (arm|hand)");
    }

    private boolean isLeft;

    @Override
    public boolean check(Event e) {
        if (!(e instanceof OraxenFurnitureInteractEvent) && !(e instanceof OraxenStringBlockInteractEvent) && !(e instanceof OraxenNoteBlockInteractEvent)) {
            return false;
        }
        EquipmentSlot hand = null;
        if (e instanceof OraxenFurnitureInteractEvent) {
            OraxenFurnitureInteractEvent event = (OraxenFurnitureInteractEvent) e;
            hand = event.getHand();
        } else if (e instanceof OraxenStringBlockInteractEvent) {
            OraxenStringBlockInteractEvent event = (OraxenStringBlockInteractEvent) e;
            hand = event.getHand();
        } else if (e instanceof OraxenNoteBlockInteractEvent) {
            OraxenNoteBlockInteractEvent event = (OraxenNoteBlockInteractEvent) e;
            hand = event.getHand();
        }
        if (isLeft) {
            return hand == EquipmentSlot.HAND;
        } else {
            return hand == EquipmentSlot.OFF_HAND;
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "interaction is " + (isLeft ? "left" : "right") + " click";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!getParser().isCurrentEvent(OraxenNoteBlockInteractEvent.class, OraxenStringBlockInteractEvent.class, OraxenFurnitureInteractEvent.class)) {
            Skript.error("You can't use 'interaction is (:right|:left) hand' outside of a Custom Interaction event!");
            return false;
        }
        isLeft = parseResult.hasTag("left");
        return true;
    }
}
