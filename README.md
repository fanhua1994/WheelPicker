# WheelPicker

> 城市三级联动选择控件，直接使用。


<div>
<img width="200px" src="https://github.com/fanhua1994/WheelPicker/blob/master/image/F67C4C652760636CD9CB18C907299964.png?raw=true" />
</div>

### 如何引用
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
```
dependencies {
        compile 'com.github.fanhua1994:WheelPicker:1.0.2'
}
```

### 如何调用
```
final CityWheelPickerPopupWindow wheelPickerPopupWindow = new CityWheelPickerPopupWindow(MainActivity.this);
wheelPickerPopupWindow.setListener(new OnCityWheelComfirmListener() {
    @Override
    public void onSelected(String Province, String City, String District, String PostCode) {
        Toast.makeText(MainActivity.this,Province + City + District,Toast.LENGTH_LONG).show();
    }
});
```