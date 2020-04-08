package clzzz.helper.testutil.slot;

import static clzzz.helper.logic.parser.CliSyntax.PREFIX_DATETIME;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_DURATION;
import static clzzz.helper.logic.parser.CliSyntax.PREFIX_NAME;

import clzzz.helper.logic.commands.slot.AddSlotCommand;
import clzzz.helper.logic.commands.slot.EditSlotCommand.EditSlotDescriptor;
import clzzz.helper.model.slot.Slot;

/**
 * A utility class for Slot.
 */
public class SlotUtil {

    /**
     * Returns an add command string for adding the {@code slot}.
     */
    public static String getAddSlotCommand(Slot slot) {
        return AddSlotCommand.COMMAND_WORD + " " + getSlotDetails(slot);
    }

    /**
     * Returns the part of command string for the given {@code slot}'s details.
     */
    public static String getSlotDetails(Slot slot) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + slot.getPet().getName().toString() + " ");
        sb.append(PREFIX_DATETIME + slot.getDateTime().toString() + " ");
        sb.append(PREFIX_DURATION + slot.getDuration().toString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditSlotDescriptor}'s details.
     */
    public static String getEditSlotDescriptorDetails(EditSlotDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getPet().ifPresent(name -> sb.append(PREFIX_NAME).append(name.getName()).append(" "));
        descriptor.getDateTime().ifPresent(dateTime -> sb.append(PREFIX_DATETIME)
                .append(dateTime.toString()).append(" "));
        descriptor.getDuration().ifPresent(duration -> sb.append(PREFIX_DURATION)
                .append(duration.toString()).append(" "));
        return sb.toString();
    }
}
