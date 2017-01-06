package com.huang.listview_delete;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by Administrator on 2016/11/9.
 */

public class PublicUtil {

    public static Activity currentActivity;

    public static final int CAMERA_PERIMISSION_CODE = 10;
    public static final int SD_PERIMISSION_CODE = 11;

    /**
     * 自定义刷新头
     *
     * @param context
     * @param ptrclassicframelayout
     */
    public static void setHeadView(Context context, PtrClassicFrameLayout ptrclassicframelayout) {
        PrintHeader header = new PrintHeader(context);
        ptrclassicframelayout.setHeaderView(header);
        ptrclassicframelayout.addPtrUIHandler(header);
    }

    /**
     * 验证合法手机号（在未出新号段前可用）
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188、 178 联通：130、131、132、152、155、156、185、186、176
         * 电信：133、153、180、189、177、（1349卫通） 总结起来就是第一位必定为1，第二位必定为3或5或8、7，其他位置的可以为0-9
         */
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8、7中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
            return mobiles.matches(telRegex);
        }
    }

    // 动态隐藏软键盘
    public static void hideSoftInput(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // 动态隐藏软键盘
    public static void hideSoftInput(Context context, EditText edit) {
        edit.clearFocus();
        InputMethodManager inputmanger = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }

}
