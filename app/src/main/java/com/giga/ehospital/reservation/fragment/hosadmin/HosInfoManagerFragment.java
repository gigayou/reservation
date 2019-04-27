package com.giga.ehospital.reservation.fragment.hosadmin;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.container.NormalContainer;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;
import com.giga.ehospital.reservation.hook.ListUtilsHook;
import com.giga.ehospital.reservation.manager.sysamdin.BuserDataManager;
import com.giga.ehospital.reservation.manager.sysamdin.HosDataManager;
import com.giga.ehospital.reservation.model.hospital.Hospital;
import com.giga.ehospital.reservation.model.system.Buser;
import com.giga.ehospital.reservation.model.vo.AddressVO;
import com.giga.ehospital.reservation.util.ConfigUtil;
import com.giga.ehospital.reservation.util.GetJsonDataUtil;
import com.giga.ehospital.reservation.util.ListUtils;
import com.google.gson.Gson;
import com.linxiao.framework.common.GsonParser;
import com.linxiao.framework.net.ApiResponse;
import com.linxiao.framework.rx.RxSubscriber;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HosInfoManagerFragment  extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindView(R.id.tv_hos_manager)
    TextView tvHosManager;
    @BindView(R.id.et_hos_name)
    EditText etHosName;
    @BindView(R.id.tv_hos_grade)
    TextView tvHosGrade;
    @BindView(R.id.tv_hos_invalid)
    TextView tvHosInvalid;
    @BindView(R.id.tv_hos_addr)
    TextView tvHosAddr;
    @BindView(R.id.et_hos_detailaddr)
    EditText etHosDetailAddr;
    @BindView(R.id.et_hos_phone)
    EditText etHosPhone;
    @BindView(R.id.et_hos_introduction)
    EditText etHosIntroduction;
    @BindString(R.string.fragment_hospital_info_title)
    String title;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;

    private static Buser buser = new Buser();

    private BuserDataManager buserDataManager;
    private HosDataManager hosDataManager;

    private List<AddressVO> provinceOptions = new ArrayList<>();
    private ArrayList<ArrayList<String>> cityOpitons = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> countyOptions = new ArrayList<>();

    private ArrayList<String> gradeOptions = new ArrayList<>();
    private ArrayList<String> invalidOptions = new ArrayList<>();
    private List<Buser> hosManagerOptions = null;

    private Integer sHosManagerIndex;
    private Integer sHosGradeIndex;
    private Integer sHosInvalidIndex;
    private String sHosManagerId;
    private String sHospitalId;
    private String sHosManagerName;

    @Override
    protected String getTopBarTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sysadmin_hos_add;
    }

    @Override
    protected void initLastCustom() {
        initJsonData();
        initGradeOptions();
        initInvalidOptions();
        if (buserDataManager == null) {
            buserDataManager = new BuserDataManager();
        }
        initHosManagerOptions();
        if (hosDataManager == null) {
            hosDataManager = new HosDataManager();
        }
        initData();
    }

    private void initData() {
        Hospital hospital = NormalContainer.get(NormalContainer.HOSPITAL);

        sHospitalId = hospital.getHospitalId();
        sHosManagerName = hospital.getManagerName();
        sHosManagerId = hospital.getHospitalManager();
        String managerName = hospital.getManagerName();
        String hospitalName = hospital.getHospitalName();
        sHosGradeIndex = Integer.valueOf(hospital.getHospitalGrade());
        sHosInvalidIndex = Integer.valueOf(hospital.getIsValid());
        String hospitalAddr = hospital.getHospitalAddr();
        String detailAddr = hospital.getDetailAddr();
        String hospitalPhone = hospital.getHospitalPhone();
        String introduction = hospital.getIntroduction();

        tvHosManager.setText(managerName);
        etHosName.setText(hospitalName);
        tvHosGrade.setText(gradeOptions.get(sHosGradeIndex));
        tvHosInvalid.setText(invalidOptions.get(sHosInvalidIndex));
        tvHosAddr.setText(hospitalAddr);
        etHosDetailAddr.setText(detailAddr);
        etHosPhone.setText(hospitalPhone);
        etHosIntroduction.setText(introduction);
    }

    @OnClick({R.id.tv_hos_invalid, R.id.tv_hos_addr, R.id.btn_save})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_hos_invalid:
                showHosInvalidPickerView();
                break;
            case R.id.tv_hos_addr:
                showHosAddressPickerView();
                break;
            case R.id.btn_save:
                pushHosInfo();
                break;
            default:
                break;
        }
    }

    private void pushHosInfo() {
        String hosName = etHosName.getText().toString().trim();
        String hosAddr = tvHosAddr.getText().toString().trim();
        String hosDetailAddr = etHosDetailAddr.getText().toString().trim();
        String hosPhone = etHosPhone.getText().toString().trim();
        String hosIntroduction = etHosIntroduction.getText().toString().trim();

        Hospital hospital = new Hospital();
        hospital.setHospitalId(sHospitalId);
        hospital.setHospitalManager(sHosManagerId);
        hospital.setManagerName(sHosManagerName);
        hospital.setHospitalName(hosName);
        hospital.setHospitalAddr(hosAddr);
        hospital.setHospitalGrade(sHosGradeIndex + "");
        hospital.setIsValid(sHosInvalidIndex + "");
        if (StringUtils.isNotBlank(hosDetailAddr))
            hospital.setDetailAddr(hosDetailAddr);
        if (StringUtils.isNotBlank(hosPhone))
            hospital.setHospitalPhone(hosPhone);
        if (StringUtils.isNotBlank(hosIntroduction))
            hospital.setIntroduction(hosIntroduction);

        // 更新缓存数据
        NormalContainer.put(NormalContainer.HOSPITAL, hospital);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        hosDataManager.update(hospital)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    progressDialog.setMessage(LOADING_MESSAGE);
                    progressDialog.show();
                })
                .doOnComplete(() -> progressDialog.dismiss())
                .subscribe(new RxSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        ApiResponse response = GsonParser.fromJSONObject(s, ApiResponse.class);
                        if (response.success()) {
                            getActivity().onBackPressed();
                            Toasty.success(getContext(), "更新成功", Toasty.LENGTH_SHORT, true).show();
                        } else {
                            Toasty.error(getContext(), response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }

    private void showHosInvalidPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String invalid = invalidOptions.size() > 0 ?
                        invalidOptions.get(options1) : "";
                tvHosInvalid.setText(invalid);
                sHosInvalidIndex = options1;
            }
        })
                .setTitleText("状态选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(invalidOptions);
        pvOptions.show();
    }

    private void showHosAddressPickerView() {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String province = provinceOptions.size() > 0 ?
                        provinceOptions.get(options1).getPickerViewText() : "";

                String city = cityOpitons.size() > 0
                        && cityOpitons.get(options1).size() > 0 ?
                        cityOpitons.get(options1).get(options2) : "";

                String county = cityOpitons.size() > 0
                        && countyOptions.get(options1).size() > 0
                        && countyOptions.get(options1).get(options2).size() > 0 ?
                        countyOptions.get(options1).get(options2).get(options3) : "";

                String address = province + city + county;
                tvHosAddr.setText(address);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(provinceOptions);//一级选择器
        pvOptions.setPicker(provinceOptions, cityOpitons);//二级选择器*/
        pvOptions.setPicker(provinceOptions, cityOpitons, countyOptions);//三级选择器
        pvOptions.show();
    }

    private void initGradeOptions() {
        gradeOptions.add("三级医院");
        gradeOptions.add("二级医院");
        gradeOptions.add("一级医院");
    }

    private void initInvalidOptions() {
        invalidOptions.add("有效");
        invalidOptions.add("无效");
    }

    private void initHosManagerOptions() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        buserDataManager.list(buser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    progressDialog.setMessage(LOADING_MESSAGE);
                    progressDialog.show();
                })
                .doOnComplete(() -> progressDialog.dismiss())
                .subscribe(new RxSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        ApiResponse response = GsonParser.fromJSONObject(s, ApiResponse.class);
                        if (response.success()) {
                            transform2BuserList(response.data);
                        } else {
                            Toasty.error(getContext(), response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }

    private void transform2BuserList(String json) {
        JSONArray jsonArray = GsonParser.fromJSONObject(json, JSONArray.class);
        List<Buser> buserList = GsonParser.fromJSONArray(jsonArray, Buser.class);
        List<Buser> hosManagerList = ListUtils.filter(buserList, new ListUtilsHook<Buser>() {
            @Override
            public boolean test(Buser buser) {
                return buser.getRoleId() == ConfigUtil.ROLE_HOS_ADMIN;
            }
        });
        hosManagerOptions = hosManagerList;
    }

    /**
     * 解析json文件数据
     */
    private void initJsonData() {

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(getContext(), "province.json");//获取assets目录下的json文件数据

        ArrayList<AddressVO> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        provinceOptions = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            cityOpitons.add(cityList);

            /**
             * 添加地区数据
             */
            countyOptions.add(province_AreaList);
        }
    }

    private ArrayList<AddressVO> parseData(String result) {
        ArrayList<AddressVO> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                AddressVO entity = gson.fromJson(data.optJSONObject(i).toString(), AddressVO.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
