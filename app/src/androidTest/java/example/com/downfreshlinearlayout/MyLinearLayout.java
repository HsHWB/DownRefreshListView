package example.com.downfreshlinearlayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import example.com.downfreshlistview.R;


/**
 * Created by Hs on 2015/2/16.
 */
public class MyLinearLayout extends LinearLayout implements View.OnTouchListener {

    private static final int STATUS_PULL_TO_REFRESH = 0;//下拉状态
    private static final int STATUS_RELEASE_TO_REFRESH = 1;//释放立即更新状态
    private static final int STATUS_REFRESHING = 2;//正在刷新状态
    private static final int STATUS_REFRESH_FINISHED = 3;//刷新完成或未刷新状态
    private static final int SCROLL_SPEED = -20;//下拉头部回滚速度
    private static final long ONE_MINUTE = 60 * 1000;//一分钟的毫秒值，用于判断上次的更新时间
    private static final long ONE_HOUR = 60 * ONE_MINUTE;//一小时的毫秒值，用于判断上次的更新时间
    private static final long ONE_DAY = 24 * ONE_HOUR;//一天的毫秒值，用于判断上次的更新时间
    private static final long ONE_MONTH = 30 * ONE_DAY;//一月的毫秒值，用于判断上次的更新时间
    private static final long ONE_YEAR = 12 * ONE_MONTH;//一年的毫秒值，用于判断上次的更新时间
    private static final String UPDATED_AT = "updated_at";//上次更新时间的字符串常量，用于作为SharedPreferences的键值
    private MyLinearLayout mListener;//拉刷新的回调接口
    private SharedPreferences preferences;//用于存储上次更新时间
    private View header;//下拉头的View
    private ListView listView;//需要去下拉刷新的ListView
//    private ProgressBar progressBar;//刷新时显示的进度条
    private ImageView arrow;//指示下拉和释放的箭头
    private TextView description;//指示下拉和释放的文字描述
    private TextView updateAt;//上次更新时间的文字描述
    private MarginLayoutParams headerLayoutParams;//下拉头的布局参数
    private long lastUpdateTime;//上次更新时间的毫秒值
    private int mId = -1;//为了防止不同界面的下拉刷新在上次更新时间上互相有冲突，使用id来做区分
    private int hideHeaderHeight;//下拉头的高度
    private int currentStatus = STATUS_REFRESH_FINISHED;;//当前处理什么状态，可选值有STATUS_PULL_TO_REFRESH, STATUS_RELEASE_TO_REFRESH,STATUS_REFRESHING 和 STATUS_REFRESH_FINISHED
    private int lastStatus = currentStatus;//记录上一次的状态是什么，避免进行重复操作
    private float yDown;//手指按下时的屏幕纵坐标
    private int touchSlop;//在被判定为滚动之前用户手指可以移动的最大值。
    private boolean loadOnce;//是否已加载过一次layout，这里onLayout中的初始化只需加载一次
    private boolean ableToPull;//当前是否可以下拉，只有ListView滚动到头的时候才允许下拉


    public MyLinearLayout(Context context, AttributeSet attr){

        super(context, attr);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        /**
         * header
         * 在应用中定义了自定义view，需要获取这个view布局，需要用到此方法
         * 一般的资料中的第二个参数会是一个null。通常情况下没有问题，但是如果我想给这个view设置一个对应的类，然后通过这个类来操作的话就会出问题。
         * 参考http://www.cnblogs.com/HighFun/p/3281674.html
         */
        header = LayoutInflater.from(context).inflate(R.layout.refrensh_message_layout, null, true);
        arrow = (ImageView) header.findViewById(R.id.refresh_message_image);
        description = (TextView) header.findViewById(R.id.refresh_message_tough_state);
        updateAt = (TextView) header.findViewById(R.id.refresh_message_tv_time);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }

    /**
     *进行初始化操作：将布局的下拉箭头refresh_message_layout向上偏移，实现隐藏。给ListView注册tough事件
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
