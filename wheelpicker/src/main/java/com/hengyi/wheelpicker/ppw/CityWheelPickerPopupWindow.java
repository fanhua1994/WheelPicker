package com.hengyi.wheelpicker.ppw;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengyi.wheelpicker.R;
import com.hengyi.wheelpicker.listener.OnCityWheelComfirmListener;
import com.hengyi.wheelpicker.listener.OnWheelChangedListener;
import com.hengyi.wheelpicker.weight.WheelView;
import com.hengyi.wheelpicker.weight.adapters.ArrayWheelAdapter;

/**
 * Created by Administrator on 2018/1/3.
 */

public class CityWheelPickerPopupWindow extends BasePopupWindow implements View.OnClickListener,OnWheelChangedListener {
    private View mView;
    private Activity activity;
    private TextView btn_cancel,btn_confirm;
    private WheelView mViewProvince, mViewCity,mViewDistrict;
    private OnCityWheelComfirmListener listener = null;

    public CityWheelPickerPopupWindow(Activity activity){
        this.activity = activity;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.ppw_wheel_picker_view, null,false);
        btn_cancel = mView.findViewById(R.id.btn_cancel);
        btn_confirm = mView.findViewById(R.id.btn_confirm);
        mViewProvince = mView.findViewById(R.id.id_province);
        mViewCity = mView.findViewById(R.id.id_city);
        mViewDistrict = mView.findViewById(R.id.id_district);

        btn_confirm.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
        mViewDistrict.addChangingListener(this);

        //设置PopupWindow的View
        this.setContentView(mView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.Animation);
        this.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.setOutsideTouchable(true);

        this.setOnDismissListener(new OnDismissListener(){
            @Override
            public void onDismiss() {
                close();
            }
        });
    }

    public void show(){
        showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.6f);
        initProvinceData(activity);
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(activity, mProvinceDatas));
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    public void close(){
        releaseProvinceData();
        backgroundAlpha(1f);
        dismiss();
    }

    // 设置屏幕透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        activity.getWindow().setAttributes(lp);
    }

    public void setListener(OnCityWheelComfirmListener wheelPickerComfirmListener){
        this.listener = wheelPickerComfirmListener;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_cancel) {
            dismiss();
        } else if (i == R.id.btn_confirm) {
            if (listener != null) {
                listener.onSelected(mCurrentProviceName, mCurrentCityName, mCurrentDistrictName, mCurrentZipCode);
            }
            dismiss();
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[] { "" };
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(activity, areas));
        mViewDistrict.setCurrentItem(0);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[] { "" };
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(activity, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }
}
