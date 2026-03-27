package com.tridiumuniversity.recursiveUnhider;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import java.util.Arrays;

@NiagaraType
@NiagaraAction(
        name = "unhide"
)
public class BRecursiveUnhider extends BComponent {
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridiumuniversity.recursiveUnhider.BRecursiveUnhider(3247439684)1.0$ @*/
/* Generated Thu Mar 26 23:06:56 EDT 2026 by Slot-o-Matic (c) Tridium, Inc. 2012-2026 */

  //region Action "unhide"

  /**
   * Slot for the {@code unhide} action.
   * @see #unhide()
   */
  public static final Action unhide = newAction(0, null);

  /**
   * Invoke the {@code unhide} action.
   * @see #unhide
   */
  public void unhide() { invoke(unhide, null, null); }

  //endregion Action "unhide"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRecursiveUnhider.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    public void doUnhide(Context cx) {
        recursiveUnhide(this.getParent().asComponent(), cx);
    }

    public void recursiveUnhide(BComponent component, Context cx) {
        Arrays.stream(component.getSlotsArray())
                .forEach(slot -> Flags.remove(component, slot, cx, Flags.HIDDEN));

        Arrays.stream(component.getChildComponents())
                .forEach(child -> recursiveUnhide(child, cx));
    }
}
