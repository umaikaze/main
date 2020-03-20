package seedu.address.logic.commands;

import static seedu.address.logic.parser.pet.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.slot.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.slot.CliSyntax.PREFIX_DURATION;

import seedu.address.model.pet.Gender;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_COCO = "Coco";
    public static final String VALID_NAME_GARFIELD = "Garfield Arbuckle";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final Gender VALID_GENDER_COCO = Gender.FEMALE;
    public static final Gender VALID_GENDER_GARFIELD = Gender.MALE;
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_DOB_COCO = "2/2/2015";
    public static final String VALID_DOB_GARFIELD = "19/6/1978";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_SPECIES_COCO = "dog";
    public static final String VALID_SPECIES_GARFIELD = "cat";
    public static final String VALID_TAG_FAT = "fat";
    public static final String VALID_TAG_HYPER = "hyper";
    public static final String VALID_TAG_LAZY = "lazy";
    public static final String VALID_DATETIME_MAR_SLOT = "1/3/2020 1200";
    public static final String VALID_DATETIME_APR_SLOT = "1/4/2020 1200";
    public static final String VALID_DURATION_20_MIN = "20";
    public static final String VALID_DURATION_40_MIN = "20";

    public static final String NAME_DESC_COCO = " " + PREFIX_NAME + VALID_NAME_COCO;
    public static final String NAME_DESC_GARFIELD = " " + PREFIX_NAME + VALID_NAME_GARFIELD;
    public static final String DATETIME_DESC_MAR_SLOT = " " + PREFIX_DATETIME + VALID_DATETIME_MAR_SLOT;
    public static final String DATETIME_DESC_APR_SLOT = " " + PREFIX_DATETIME + VALID_DATETIME_APR_SLOT;
    public static final String DURATION_DESC_20_MIN = " " + PREFIX_DURATION + VALID_DURATION_20_MIN;
    public static final String DURATION_DESC_40_MIN = " " + PREFIX_DURATION + VALID_DURATION_40_MIN;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";
}
