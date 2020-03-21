package seedu.address.logic.commands;

import static seedu.address.logic.parser.general.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.general.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.general.CliSyntax.PREFIX_NAME;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    //TODO Merge with the one in pet package
    //TODO Standardize what should be in the list and what pets are being searched
    public static final String VALID_NAME_COCO = "Coco";
    public static final String VALID_NAME_GARFIELD = "Garfield Arbuckle";
    public static final String VALID_DATETIME_COCO = "1/3/2020 1200";
    public static final String VALID_DATETIME_GARFIELD = "1/4/2020 1200";
    public static final String VALID_DURATION_COCO = "20";
    public static final String VALID_DURATION_GARFIELD = "20";

    public static final String NAME_DESC_COCO = " " + PREFIX_NAME + VALID_NAME_COCO;
    public static final String NAME_DESC_GARFIELD = " " + PREFIX_NAME + VALID_NAME_GARFIELD;
    public static final String DATETIME_DESC_COCO = " " + PREFIX_DATETIME + VALID_DATETIME_COCO;
    public static final String DATETIME_DESC_GARFIELD = " " + PREFIX_DATETIME + VALID_DATETIME_GARFIELD;
    public static final String DURATION_DESC_COCO = " " + PREFIX_DURATION + VALID_DURATION_COCO;
    public static final String DURATION_DESC_GARFIELD = " " + PREFIX_DURATION + VALID_DURATION_GARFIELD;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";

    public static final String INVALID_DATETIME = "1-3-2020 12:00";

    public static final String INVALID_DATETIME_DESC = " " + PREFIX_DATETIME + INVALID_DATETIME;
}
