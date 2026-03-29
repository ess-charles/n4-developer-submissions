package com.tridiumuniversity.multiplexer;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NiagaraType
@NiagaraProperty( name = "out", type = "BBoolean", defaultValue = "BBoolean.DEFAULT", flags = Flags.SUMMARY | Flags.READONLY)
public class BTwoNToOneMultiplexer extends BComponent {
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridiumuniversity.multiplexer.BTwoNToOneMultiplexer(2644890818)1.0$ @*/
/* Generated Thu Mar 26 12:53:50 EDT 2026 by Slot-o-Matic (c) Tridium, Inc. 2012-2026 */

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.SUMMARY | Flags.READONLY, BBoolean.DEFAULT.as(BBoolean.class).getBoolean(), null);

  /**
   * Get the {@code out} property.
   * @see #out
   */
  public boolean getOut() { return ((BBoolean) get(out)).getBoolean(); }

  /**
   * Set the {@code out} property.
   * @see #out
   */
  public void setOut(boolean v) { setBoolean(out, v, null); }

  //endregion Property "out"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTwoNToOneMultiplexer.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    private static final String INPUT = "in";
    private static final String SWITCH = "s";
    private static final int MAX_INPUT_INDEX = 63;
    private static final int MAX_SWITCH_INDEX = 5;
    private static final Pattern DYNAMIC_SLOT_PATTERN = Pattern.compile("^(?<prefix>" + INPUT + "|" + SWITCH + ")(?<index>\\d+)$");

    @Override
    public void checkAdd(String name, BValue value, int flags, BFacets facets, Context cx) {
        Matcher matcher = DYNAMIC_SLOT_PATTERN.matcher(name);

        if (!matcher.matches()) {
            throw new LocalizableRuntimeException(
                    "multiplexer",
                    "multiplexer.invalidName",
                    new Object[] { name }
            );
        }

        String prefix = matcher.group("prefix");
        long index = Long.parseLong(matcher.group("index"));

        if (prefix.equals(INPUT) && index > MAX_INPUT_INDEX) {
            throw new LocalizableRuntimeException(
                    "multiplexer",
                    "multiplexer.indexOutOfRange",
                    new Object[] {
                            index,
                            0,
                            MAX_INPUT_INDEX
                    }
            );
        }

        if (prefix.equals(SWITCH) && index > MAX_SWITCH_INDEX) {
            throw new LocalizableRuntimeException(
                    "multiplexer",
                    "multiplexer.switchOutOfRange",
                    new Object[] {
                            index,
                            0,
                            MAX_SWITCH_INDEX
                    }
            );
        }

        if (!(value instanceof BBoolean)) {
            throw new LocalizableRuntimeException(
                    "multiplexer",
                    "multiplexer.invalidType",
                    new Object[] {
                            name,
                            value == null ? "null" : value.getType(),
                            BBoolean.TYPE
                    }
            );
        }
    }

    @Override
    public void changed(Property p, Context cx) {
        if (isMuxProperty(p)) {
            updateOut();
        }
    }

    @Override
    public void added(Property p, Context cx) {
        if (isMuxProperty(p)) {
            updateOut();
        }
    }

    @Override
    public void removed(Property p, BValue oldValue, Context cx) {
        if (isMuxProperty(p)) {
            updateOut();
        }
    }

    private void updateOut() {
        Property[] properties = getMuxProperties();
        long selectValue = getSelectValue(properties);

        boolean value = Arrays.stream(properties)
                .map(ParsedMuxProperty::new)
                .filter(parsed -> !parsed.isSwitch())
                .filter(parsed -> parsed.index == selectValue)
                .findFirst()
                .map(parsed -> getBoolean(parsed.property))
                .orElse(false);

        setOut(value);
    }

    private Property[] getMuxProperties() {
        return Arrays.stream(getDynamicPropertiesArray())
                .filter(this::isMuxProperty)
                .toArray(Property[]::new);
    }

    private Matcher getMuxMatcher(Property p) {
        Matcher matcher = DYNAMIC_SLOT_PATTERN.matcher(p.getName());

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid mux property: " + p.getName());
        }

        return matcher;
    }

    private long getSelectValue(Property[] muxProperties) {

        return Arrays.stream(muxProperties)
                .map(ParsedMuxProperty::new)
                .filter(ParsedMuxProperty::isSwitch)
                .map(parsed -> ((BBoolean) get(parsed.property)).getBoolean() ? 1L << parsed.index : 0L)
                .reduce(0L, (acc, bit) -> acc | bit);
    }

    private boolean isMuxProperty(Property p) {
        return isValidName(p.getName());
    }

    private boolean isValidName(String name) {
        return DYNAMIC_SLOT_PATTERN.matcher(name).matches();
    }

    private final class ParsedMuxProperty {
        private final Property property;
        private final long index;
        private final boolean switchProperty;

        private ParsedMuxProperty(Property property) {
            Matcher matcher = getMuxMatcher(property);
            this.property = property;
            this.index = Long.parseLong(matcher.group("index"));
            this.switchProperty = matcher.group("prefix").equals(SWITCH);
        }

        private boolean isSwitch() {
            return switchProperty;
        }
    }

}
