package ptuddd.app.android.KQXSapp.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import ptuddd.app.android.KQXSapp.ui.activity.MainActivity;


/**
 * Created by Cuong Kieu on 4/4/2017.
 */
public class UiUtils {

    public static Toast lToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.show();
        return toast;
    }

    public static Toast sToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    public static Intent getStartupMainIntent(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }


    public static Intent getMainIntent(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        return intent;
    }

    public static void setSwipeLayoutRefreshAppearance(SwipeRefreshLayout refreshLayout) {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

}
