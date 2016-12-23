package rxh.shanks.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/12/7.
 */
public class BSDListView extends ListView {


    public BSDListView(Context context) {
        super(context);
    }

    public BSDListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BSDListView(Context context, AttributeSet attrs,
                       int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(ev);
    }


}
