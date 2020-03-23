package seedu.address.testutil.slot;

import static seedu.address.commons.util.DateTimeUtil.DATETIME_FORMAT;
import static seedu.address.logic.parser.general.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.general.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.general.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.slot.AddSlotCommand;
import seedu.address.logic.commands.slot.EditSlotCommand.EditSlotDescriptor;
import seedu.address.model.slot.Slot;

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
        sb.append(PREFIX_DATETIME + slot.getDateTime().format(DATETIME_FORMAT) + " ");
        sb.append(PREFIX_DURATION + String.valueOf(slot.getDuration().toMinutes()) + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditSlotDescriptor}'s details.
     */
    public static String getEditSlotDescriptorDetails(EditSlotDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getPet().ifPresent(name -> sb.append(PREFIX_NAME).append(name.getName()).append(" "));
        descriptor.getDateTime().ifPresent(dateTime -> sb.append(PREFIX_DATETIME)
                .append(dateTime.format(DATETIME_FORMAT)).append(" "));
        descriptor.getDuration().ifPresent(duration -> sb.append(PREFIX_DURATION)
                .append(duration.toMinutes()).append(" "));
        return sb.toString();
    }
}
