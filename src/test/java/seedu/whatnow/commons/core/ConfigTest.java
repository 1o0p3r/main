package seedu.whatnow.commons.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.whatnow.commons.core.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConfigTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "App title : WhatNow App\n" + "Current log level : INFO\n"
                + "Preference file Location : preferences.json\n" + "Local data file location : data/whatnow.xml\n"
                + "WhatNow name : MyWhatNow\n" + "PinnedItemType : tag\n" + "PinnedItemKeyword : highPriority";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        assertFalse(defaultConfig.equals(null));
        assertTrue(defaultConfig.equals(defaultConfig));
    }

}
