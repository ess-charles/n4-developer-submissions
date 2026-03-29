package com.tridiumuniversity.driverFinder;

import javax.baja.collection.BITable;
import javax.baja.collection.TableCursor;
import javax.baja.driver.BDeviceNetwork;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import java.util.logging.Logger;

@NiagaraType
@NiagaraAction( name = "bqlFind")
public class BDriverFinder extends BComponent {
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridiumuniversity.driverFinder.BDriverFinder(397568062)1.0$ @*/
/* Generated Sun Mar 29 17:44:03 EDT 2026 by Slot-o-Matic (c) Tridium, Inc. 2012-2026 */

  //region Action "bqlFind"

  /**
   * Slot for the {@code bqlFind} action.
   * @see #bqlFind()
   */
  public static final Action bqlFind = newAction(0, null);

  /**
   * Invoke the {@code bqlFind} action.
   * @see #bqlFind
   */
  public void bqlFind() { invoke(bqlFind, null, null); }

  //endregion Action "bqlFind"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDriverFinder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

	private static final Logger logger = Logger.getLogger("driverFinder");

	public void doBqlFind(Context cx) {
		@SuppressWarnings("unchecked")
		BITable<BDeviceNetwork> table = (BITable<BDeviceNetwork>) BOrd
				.make("bql:select from driver:DeviceNetwork where navParent.toString != 'Driver Container'")
				.get(Sys.getStation(), cx);

		try (TableCursor<BDeviceNetwork> cursor = table.cursor()) {
			for (BDeviceNetwork deviceNetwork : cursor) {

				logger.info(deviceNetwork.getSlotPath().toString());
			}
		}
	}
}
