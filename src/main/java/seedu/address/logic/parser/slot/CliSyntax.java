package seedu.address.logic.parser.slot;

import seedu.address.logic.parser.general.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_DATETIME = new Prefix("t/");
    public static final Prefix PREFIX_DURATION = new Prefix("d/");
    public static final Prefix PREFIX_PETNAME = new Prefix("n/");
    public static final Prefix PREFIX_INDEX = new Prefix("i/");

}
