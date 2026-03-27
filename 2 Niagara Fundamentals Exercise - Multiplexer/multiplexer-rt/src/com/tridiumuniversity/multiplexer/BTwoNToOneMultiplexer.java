package com.tridiumuniversity.multiplexer;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

@NiagaraType
@NiagaraProperty( name = "out", type = "BBoolean", defaultValue = "BBoolean.DEFAULT", flags = Flags.SUMMARY | Flags.READONLY)
public class TwoNToOneMultiplexer extends BComponent {



    private static final String INPUT_REGEX = "in\\d+";
    private static final String SWITCH_REGEX = "s\\d+";

    @Override
    public void started() {
    }

    @Override
    public void changed(Property p, Context cx) {

        if (filterDynamicPropertiesStream(SWITCH_REGEX)
                .anyMatch(prop -> prop.equals(p))) {
            int outputIndex = getSValue();

            filterDynamicPropertiesStream(INPUT_REGEX)
                    .filter(property -> parseIndex(property, 2) == outputIndex)
                    .findFirst()
                    .ifPresent(p -> setOut(getBoolean(p)));

        }
    }

    private Stream<Property> getDynamicPropertiesStream() {
        return Arrays.stream(getDynamicPropertiesArray());
    }

    private Stream<Property> filterDynamicPropertiesStream(String regex) {
        return getDynamicPropertiesStream()
                .filter(p -> p.getName().matches(regex));
    }

    private Integer getSValue() {
        Comparator<Property> reversed = Comparator.comparingInt((Property p) -> parseIndex(p, 1)).reversed();

        return filterDynamicPropertiesStream(SWITCH_REGEX)
                .sorted(reversed)
                .map(p -> parseIndex(p, 1))
                .reduce(0, (acc, bit) -> (acc << 1) | bit);
    }

    private Integer parseIndex(Property p, int startIndex) {
        return Integer.parseInt(p.getName().substring(startIndex));
    }

}
