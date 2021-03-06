package me.danwi.eq.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import me.danwi.eq.R;
import me.danwi.eq.utils.LogUtils;

/**
 * Created with Android Studio.
 * User: HandSome-T
 * Date: 16/8/17
 * Time: 下午6:46
 */
public abstract class BaseMVCActivity extends AppCompatActivity {
    protected SubscriptionManager subscriptionManager;

    //获取类名
    public String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (fullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (loadLayout()) {
            setContentView(getLayoutId());
        }
        ButterKnife.bind(this);
        subscriptionManager = new SubscriptionManager();
        LogUtils.init(String.format("EQ-%s", TAG));
        Logger.d("进入了%s页面", TAG);
    }

    @Override
    protected void onDestroy() {
        if (subscriptionManager != null) {
            subscriptionManager.removeAllSubscription();
        }
        super.onDestroy();
    }

    public void addSubscription(Disposable subscription) {
        subscriptionManager.addSubscription(subscription);
    }

    public String getValue(TextView tv) {
        return tv.getText().toString();
    }

    //模板设计模式哦~
    public abstract int getLayoutId();

    public boolean loadLayout() {
        return true;
    }

    public boolean fullScreen() {
        return false;
    }

    //跳转到其他页面动画
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }


    //结束当前activity动画
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }
}
