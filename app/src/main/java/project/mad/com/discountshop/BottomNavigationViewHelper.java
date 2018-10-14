package project.mad.com.discountshop;

import android.annotation.SuppressLint;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import java.lang.reflect.Field;

/**
 * BottomNavigationViewHelper is used to control the bottom navigation effect
 */
public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHel";
    /**
     * use this method to disable the bottom navigation effect
     * @param view
     */
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        String mName = "mShiftingMode";
        String noFile = "Unable to get shift mode field";
        String illegalAccess = "Unable to change value of shift mode";

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField(mName);
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e(TAG, noFile, e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, illegalAccess, e);
        }
    }
}