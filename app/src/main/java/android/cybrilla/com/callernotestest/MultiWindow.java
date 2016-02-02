package android.cybrilla.com.callernotestest;

/**
 * Created by sakshiagarwal on 01/02/16.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.cybrilla.com.callernotestest.StandOutWindow;
import android.cybrilla.com.callernotestest.StandOutFlags;
import android.cybrilla.com.callernotestest.Window;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * This implementation provides multiple windows. You may extend this class or
 * use it as a reference for a basic foundation for your own windows.
 *
 * <p>
 * Functionality includes system window decorators, moveable, resizeable,
 * hideable, closeable, and bring-to-frontable.
 *
 * <p>
 * The persistent notification creates new windows. The hidden notifications
 * restores previously hidden windows.
 *
 * @author Mark Wei <markwei@gmail.com>
 *
 */
public class MultiWindow extends StandOutWindow {

    @Override
    public String getAppName() {
        return "Caller Notes";
    }

    @Override
    public int getAppIcon() {
        return R.drawable.phone;
    }

    @Override
    public String getTitle(int id) {
        return getAppName() + " " + id;
    }

    @Override
    public void createAndAttachView(int id, FrameLayout frame) {
        // create a new layout from body.xml
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.body, frame, true);
        EditText notesText = (EditText) view.findViewById(R.id.notes);

    }

    @Override
    public StandOutLayoutParams getParams(int id, Window window) {
        return new StandOutLayoutParams(id, 450, 300,
                StandOutLayoutParams.CENTER,
                StandOutLayoutParams.CENTER, 100, 100);
    }
    @Override
    public int getFlags(int id) {
        return StandOutFlags.FLAG_DECORATION_SYSTEM
                | StandOutFlags.FLAG_BODY_MOVE_ENABLE
                | StandOutFlags.FLAG_WINDOW_HIDE_ENABLE
                | StandOutFlags.FLAG_WINDOW_BRING_TO_FRONT_ON_TAP
                | StandOutFlags.FLAG_WINDOW_EDGE_LIMITS_ENABLE
                | StandOutFlags.FLAG_WINDOW_PINCH_RESIZE_ENABLE;
    }

    @Override
    public String getPersistentNotificationTitle(int id) {
        return getAppName() + " Running";
    }

    @Override
    public String getPersistentNotificationMessage(int id) {
        return "Click to add a new " + getAppName();
    }

    @Override
    public Intent getPersistentNotificationIntent(int id) {
        return StandOutWindow.getShowIntent(this, getClass(), getUniqueId());
    }

    @Override
    public int getHiddenIcon() {
        return android.R.drawable.ic_menu_info_details;
    }

    @Override
    public String getHiddenNotificationTitle(int id) {
        return getAppName() + " Hidden";
    }

    @Override
    public String getHiddenNotificationMessage(int id) {
        return "Click to restore #" + id;
    }

    // return an Intent that restores the MultiWindow
    @Override
    public Intent getHiddenNotificationIntent(int id) {
        return StandOutWindow.getShowIntent(this, getClass(), id);
    }

    @Override
    public Animation getShowAnimation(int id) {
        if (isExistingId(id)) {
            // restore
            return AnimationUtils.loadAnimation(this,
                    android.R.anim.slide_in_left);
        } else {
            // show
            return super.getShowAnimation(id);
        }
    }

    @Override
    public Animation getHideAnimation(int id) {
        return AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right);
    }

    @Override
    public List<DropDownListItem> getDropDownItems(int id) {
        List<DropDownListItem> items = new ArrayList<DropDownListItem>();
        items.add(new DropDownListItem(android.R.drawable.ic_menu_help,
                "About", new Runnable() {

            @Override
            public void run() {
                Toast.makeText(
                        MultiWindow.this,
                        getAppName()
                                + " is a demonstration of StandOut.",
                        Toast.LENGTH_SHORT).show();
            }
        }));
        items.add(new DropDownListItem(android.R.drawable.ic_menu_preferences,
                "Settings", new Runnable() {

            @Override
            public void run() {
                Toast.makeText(MultiWindow.this,
                        "There are no settings.", Toast.LENGTH_SHORT)
                        .show();
            }
        }));
        return items;
    }

    @Override
    public void onReceiveData(int id, int requestCode, Bundle data,
                              Class<? extends StandOutWindow> fromCls, int fromId) {
        // receive data from WidgetsWindow's button press
        // to show off the data sending framework
//        switch (requestCode) {
//            case WidgetsWindow.DATA_CHANGED_TEXT:
//                Window window = getWindow(id);
//                if (window == null) {
//                    String errorText = String.format(Locale.US,
//                            "%s received data but Window id: %d is not open.",
//                            getAppName(), id);
//                    Toast.makeText(this, errorText, Toast.LENGTH_SHORT).show();
//                    return;
//                }
////                String changedText = data.getString("changedText");
////                TextView status = (TextView) window.findViewById(R.id.id);
////                status.setTextSize(20);
////                status.setText("Received data from WidgetsWindow: "
////                        + changedText);
//                break;
//            default:
                Log.d("MultiWindow", "Unexpected data received.");
//                break;
//        }
    }
}