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



    private static final Pattern DYNAMIC_SLOT_PATTERN = Pattern.compile("^(in|s)(\\d+)$");

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
        int sValue = getSValue(properties);

        boolean value = Arrays.stream(properties)
                .filter(property -> !isSwitchProperty(property))
                .filter(property -> getMuxIndex(property) == sValue)
                .findFirst()
                .map(this::getBoolean)
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

    private int getMuxIndex(Property p) {
        return Integer.parseInt(getMuxMatcher(p).group(2));
    }

    private int getSValue(Property[] muxProperties) {

        return Arrays.stream(muxProperties)
                .filter(this::isSwitchProperty)
                .map(p -> ((BBoolean) get(p)).getBoolean() ? 1 << getMuxIndex(p) : 0)
                .reduce(0, (acc, bit) -> acc | bit);
    }

    private boolean isMuxProperty(Property p) {
        return DYNAMIC_SLOT_PATTERN.matcher(p.getName()).matches();
    }

    private boolean isSwitchProperty(Property p) {
        return getMuxMatcher(p).group(1).equals("s");
    }

}
