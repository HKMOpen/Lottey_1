package other;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.entertiment.util.Util;
import com.example.lottery.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class bindingbank extends Activity {
	private int tempint = 0;
	private Spinner province_spinner;
	private Spinner city_spinner;
	private Spinner county_spinner;
	private Spinner bank_spinner;
	private Integer provinceId, cityId;
	private String strProvince = "北京市", strCity = "北京", strCounty = "东城区", strbank = "中国银行";
	private String banknumber = "";
	private int[] city = { R.array.beijin_province_item, R.array.tianjin_province_item, R.array.heibei_province_item,
			R.array.shanxi1_province_item, R.array.neimenggu_province_item, R.array.liaoning_province_item,
			R.array.jilin_province_item, R.array.heilongjiang_province_item, R.array.shanghai_province_item,
			R.array.jiangsu_province_item, R.array.zhejiang_province_item, R.array.anhui_province_item,
			R.array.fujian_province_item, R.array.jiangxi_province_item, R.array.shandong_province_item,
			R.array.henan_province_item, R.array.hubei_province_item, R.array.hunan_province_item,
			R.array.guangdong_province_item, R.array.guangxi_province_item, R.array.hainan_province_item,
			R.array.chongqing_province_item, R.array.sichuan_province_item, R.array.guizhou_province_item,
			R.array.yunnan_province_item, R.array.xizang_province_item, R.array.shanxi2_province_item,
			R.array.gansu_province_item, R.array.qinghai_province_item, R.array.linxia_province_item,
			R.array.xinjiang_province_item, R.array.hongkong_province_item, R.array.aomen_province_item,
			R.array.taiwan_province_item };
	private int[] countyOfBeiJing = { R.array.beijin_city_item };
	private int[] countyOfTianJing = { R.array.tianjin_city_item };
	private int[] countyOfHeBei = { R.array.shijiazhuang_city_item, R.array.tangshan_city_item,
			R.array.qinghuangdao_city_item, R.array.handan_city_item, R.array.xingtai_city_item,
			R.array.baoding_city_item, R.array.zhangjiakou_city_item, R.array.chengde_city_item,
			R.array.cangzhou_city_item, R.array.langfang_city_item, R.array.hengshui_city_item };
	private int[] countyOfShanXi1 = { R.array.taiyuan_city_item, R.array.datong_city_item, R.array.yangquan_city_item,
			R.array.changzhi_city_item, R.array.jincheng_city_item, R.array.shuozhou_city_item,
			R.array.jinzhong_city_item, R.array.yuncheng_city_item, R.array.xinzhou_city_item, R.array.linfen_city_item,
			R.array.lvliang_city_item };
	private int[] countyOfNeiMengGu = { R.array.huhehaote_city_item, R.array.baotou_city_item, R.array.wuhai_city_item,
			R.array.chifeng_city_item, R.array.tongliao_city_item, R.array.eerduosi_city_item,
			R.array.hulunbeier_city_item, R.array.bayannaoer_city_item, R.array.wulanchabu_city_item,
			R.array.xinganmeng_city_item, R.array.xilinguolemeng_city_item, R.array.alashanmeng_city_item };
	private int[] countyOfLiaoNing = { R.array.shenyang_city_item, R.array.dalian_city_item, R.array.anshan_city_item,
			R.array.wushun_city_item, R.array.benxi_city_item, R.array.dandong_city_item,
			R.array.liaoning_jinzhou_city_item, R.array.yingkou_city_item, R.array.fuxin_city_item,
			R.array.liaoyang_city_item, R.array.panjin_city_item, R.array.tieling_city_item, R.array.zhaoyang_city_item,
			R.array.huludao_city_item };
	private int[] countyOfJiLin = { R.array.changchun_city_item, R.array.jilin_city_item, R.array.siping_city_item,
			R.array.liaoyuan_city_item, R.array.tonghua_city_item, R.array.baishan_city_item,
			R.array.songyuan_city_item, R.array.baicheng_city_item, R.array.yanbian_city_item };
	private int[] countyOfHeiLongJiang = { R.array.haerbing_city_item, R.array.qiqihaer_city_item,
			R.array.jixi_city_item, R.array.hegang_city_item, R.array.shuangyashan_city_item, R.array.daqing_city_item,
			R.array.heilongjiang_yichun_city_item, R.array.jiamusi_city_item, R.array.qitaihe_city_item,
			R.array.mudanjiang_city_item, R.array.heihe_city_item, R.array.suihua_city_item,
			R.array.daxinganling_city_item };
	private int[] countyOfShangHai = { R.array.shanghai_city_item };

	private int[] countyOfJiangSu = { R.array.nanjing_city_item, R.array.wuxi_city_item, R.array.xuzhou_city_item,
			R.array.changzhou_city_item, R.array.nanjing_suzhou_city_item, R.array.nantong_city_item,
			R.array.lianyungang_city_item, R.array.huaian_city_item, R.array.yancheng_city_item,
			R.array.yangzhou_city_item, R.array.zhenjiang_city_item, R.array.jiangsu_taizhou_city_item,
			R.array.suqian_city_item };
	private int[] countyOfZheJiang = { R.array.hangzhou_city_item, R.array.ningbo_city_item, R.array.wenzhou_city_item,
			R.array.jiaxing_city_item, R.array.huzhou_city_item, R.array.shaoxing_city_item, R.array.jinhua_city_item,
			R.array.quzhou_city_item, R.array.zhoushan_city_item, R.array.zejiang_huzhou_city_item,
			R.array.lishui_city_item };
	private int[] countyOfAnHui = { R.array.hefei_city_item, R.array.wuhu_city_item, R.array.bengbu_city_item,
			R.array.huainan_city_item, R.array.maanshan_city_item, R.array.huaibei_city_item,
			R.array.tongling_city_item, R.array.anqing_city_item, R.array.huangshan_city_item,
			R.array.chuzhou_city_item, R.array.fuyang_city_item, R.array.anhui_suzhou_city_item,
			R.array.chaohu_city_item, R.array.luan_city_item, R.array.haozhou_city_item, R.array.chizhou_city_item,
			R.array.xuancheng_city_item };
	private int[] countyOfFuJian = { R.array.huzhou_city_item, R.array.xiamen_city_item, R.array.putian_city_item,
			R.array.sanming_city_item, R.array.quanzhou_city_item, R.array.zhangzhou_city_item, R.array.nanp_city_item,
			R.array.longyan_city_item, R.array.ningde_city_item };
	private int[] countyOfJiangXi = { R.array.nanchang_city_item, R.array.jingdezhen_city_item,
			R.array.pingxiang_city_item, R.array.jiujiang_city_item, R.array.xinyu_city_item, R.array.yingtan_city_item,
			R.array.ganzhou_city_item, R.array.jian_city_item, R.array.jiangxi_yichun_city_item,
			R.array.jiangxi_wuzhou_city_item, R.array.shangrao_city_item };
	private int[] countyOfShanDong = { R.array.jinan_city_item, R.array.qingdao_city_item, R.array.zaobo_city_item,
			R.array.zaozhuang_city_item, R.array.dongying_city_item, R.array.yantai_city_item,
			R.array.weifang_city_item, R.array.jining_city_item, R.array.taian_city_item, R.array.weihai_city_item,
			R.array.rizhao_city_item, R.array.laiwu_city_item, R.array.linxi_city_item, R.array.dezhou_city_item,
			R.array.liaocheng_city_item, R.array.shandong_bingzhou_city_item, R.array.heze_city_item };
	private int[] countyOfHeNan = { R.array.zhenshou_city_item, R.array.kaifang_city_item, R.array.luoyang_city_item,
			R.array.kaipingshan_city_item, R.array.anyang_city_item, R.array.hebi_city_item, R.array.xinxiang_city_item,
			R.array.jiaozuo_city_item, R.array.buyang_city_item, R.array.xuchang_city_item, R.array.leihe_city_item,
			R.array.sanmenxia_city_item, R.array.nanyang_city_item, R.array.shangqiu_city_item,
			R.array.xinyang_city_item, R.array.zhoukou_city_item, R.array.zhumadian_city_item };
	private int[] countyOfHuBei = { R.array.wuhan_city_item, R.array.huangshi_city_item, R.array.shiyan_city_item,
			R.array.yichang_city_item, R.array.xiangpan_city_item, R.array.erzhou_city_item, R.array.jinmen_city_item,
			R.array.xiaogan_city_item, R.array.hubei_jinzhou_city_item, R.array.huanggang_city_item,
			R.array.xianning_city_item, R.array.suizhou_city_item, R.array.enshi_city_item,
			R.array.shenglongjia_city_item };

	private int[] countyOfHuNan = { R.array.changsha_city_item, R.array.zhuzhou_city_item, R.array.xiangtan_city_item,
			R.array.hengyang_city_item, R.array.shaoyang_city_item, R.array.yueyang_city_item,
			R.array.changde_city_item, R.array.zhangjiajie_city_item, R.array.yiyang_city_item,
			R.array.hunan_bingzhou_city_item, R.array.yongzhou_city_item, R.array.huaihua_city_item,
			R.array.loudi_city_item, R.array.xiangxi_city_item };
	private int[] countyOfGuangDong = { R.array.guangzhou_city_item, R.array.shaoguan_city_item,
			R.array.shenzhen_city_item, R.array.zhuhai_city_item, R.array.shantou_city_item, R.array.foshan_city_item,
			R.array.jiangmen_city_item, R.array.zhangjiang_city_item, R.array.maoming_city_item,
			R.array.zhaoqing_city_item, R.array.huizhou_city_item, R.array.meizhou_city_item, R.array.shanwei_city_item,
			R.array.heyuan_city_item, R.array.yangjiang_city_item, R.array.qingyuan_city_item,
			R.array.dongguan_city_item, R.array.zhongshan_city_item, R.array.chaozhou_city_item,
			R.array.jiyang_city_item, R.array.yunfu_city_item };
	private int[] countyOfGuangXi = { R.array.nanning_city_item, R.array.liuzhou_city_item, R.array.guilin_city_item,
			R.array.guangxi_wuzhou_city_item, R.array.beihai_city_item, R.array.fangchenggang_city_item,
			R.array.qinzhou_city_item, R.array.guigang_city_item, R.array.yuelin_city_item, R.array.baise_city_item,
			R.array.hezhou_city_item, R.array.hechi_city_item, R.array.laibing_city_item, R.array.chuangzuo_city_item };
	private int[] countyOfHaiNan = { R.array.haikou_city_item, R.array.sanya_city_item };
	private int[] countyOfChongQing = { R.array.chongqing_city_item };
	private int[] countyOfSiChuan = { R.array.chengdu_city_item, R.array.zigong_city_item, R.array.panzhihua_city_item,
			R.array.luzhou_city_item, R.array.deyang_city_item, R.array.mianyang_city_item, R.array.guangyuan_city_item,
			R.array.suining_city_item, R.array.neijiang_city_item, R.array.leshan_city_item, R.array.nanchong_city_item,
			R.array.meishan_city_item, R.array.yibing_city_item, R.array.guangan_city_item, R.array.dazhou_city_item,
			R.array.yaan_city_item, R.array.bazhong_city_item, R.array.ziyang_city_item, R.array.abei_city_item,
			R.array.ganmu_city_item, R.array.liangshan_city_item };
	private int[] countyOfGuiZhou = { R.array.guiyang_city_item, R.array.lupanshui_city_item, R.array.zhunyi_city_item,
			R.array.anshun_city_item, R.array.tongren_city_item, R.array.qingxinan_city_item, R.array.biji_city_item,
			R.array.qingdongnan_city_item, R.array.qingnan_city_item };
	private int[] countyOfYunNan = { R.array.kunming_city_item, R.array.qujing_city_item, R.array.yuexi_city_item,
			R.array.baoshan_city_item, R.array.zhaotong_city_item, R.array.lijiang_city_item, R.array.simao_city_item,
			R.array.lingcang_city_item, R.array.chuxiong_city_item, R.array.honghe_city_item, R.array.wenshan_city_item,
			R.array.xishuangbanna_city_item, R.array.dali_city_item, R.array.dehuang_city_item,
			R.array.nujiang_city_item, R.array.diqing_city_item };
	private int[] countyOfXiZang = { R.array.lasa_city_item, R.array.changdu_city_item, R.array.shannan_city_item,
			R.array.rgeze_city_item, R.array.naqu_city_item, R.array.ali_city_item, R.array.linzhi_city_item };

	private int[] countyOfShanXi2 = { R.array.xian_city_item, R.array.tongchuan_city_item, R.array.baoji_city_item,
			R.array.xianyang_city_item, R.array.weinan_city_item, R.array.yanan_city_item, R.array.hanzhong_city_item,
			R.array.yulin_city_item, R.array.ankang_city_item, R.array.shangluo_city_item };
	private int[] countyOfGanSu = { R.array.lanzhou_city_item, R.array.jiayuguan_city_item, R.array.jinchang_city_item,
			R.array.baiyin_city_item, R.array.tianshui_city_item, R.array.wuwei_city_item, R.array.zhangyue_city_item,
			R.array.pingliang_city_item, R.array.jiuquan_city_item, R.array.qingyang_city_item,
			R.array.dingxi_city_item, R.array.longnan_city_item, R.array.linxia_city_item, R.array.gannan_city_item };
	private int[] countyOfQingHai = { R.array.xining_city_item, R.array.haidong_city_item, R.array.haibai_city_item,
			R.array.huangnan_city_item, R.array.hainan_city_item, R.array.guluo_city_item, R.array.yushu_city_item,
			R.array.haixi_city_item };
	private int[] countyOfNingXia = { R.array.yinchuan_city_item, R.array.shizuishan_city_item,
			R.array.wuzhong_city_item, R.array.guyuan_city_item, R.array.zhongwei_city_item };
	private int[] countyOfXinJiang = { R.array.wulumuqi_city_item, R.array.kelamayi_city_item,
			R.array.tulyfan_city_item, R.array.hami_city_item, R.array.changji_city_item, R.array.boertala_city_item,
			R.array.bayinguolen_city_item, R.array.akesu_city_item, R.array.kemuleisu_city_item,
			R.array.geshen_city_item, R.array.hetian_city_item, R.array.yili_city_item, R.array.tacheng_city_item,
			R.array.aleitai_city_item, R.array.shihezi_city_item, R.array.alaer_city_item, R.array.tumushihe_city_item,
			R.array.wujiaqu_city_item };
	private int[] countyOfHongKong = {};
	private int[] countyOfAoMen = {};
	private int[] countyOfTaiWan = {};

	private ArrayAdapter<CharSequence> province_adapter;
	private ArrayAdapter<CharSequence> city_adapter;
	private ArrayAdapter<CharSequence> county_adapter;
	private ArrayAdapter<CharSequence> bank_adapter;
	private EditText edit_bank_number;
	private TextView text_another;
	private EditText edit_pro;
	private EditText edit_city;
	private EditText edit_con;
	private Button btn_ok;
	private LinearLayout lin_another;
	private LinearLayout Lin_back;
	private int another = 0;
	private String inpro = "";
	private String incity = "";
	private String incon = "";
	private SharedPreferences cookiesdetail;
	private String cookies;
	private String msg = "绑定失败";
	private Handler uiHandler = new Handler() {
		@Override
		public void handleMessage(Message msg1) {
			switch (msg1.what) {
			case 1:
				System.out.println("handleMessage thread id " + Thread.currentThread().getId());
				System.out.println("msg.arg1:" + "yjygj");
				System.out.println("msg.arg2:" + msg1.arg2);
				// goshowload();
				// getnew();
				// address.this.statusTextView.setText("文件下载完成");
				break;
			case 2:
				System.out.println("handleMessage thread id " + Thread.currentThread().getId());
				System.out.println("msg.arg1:" + "hjj6666");
				System.out.println("msg.arg2:" + msg1.arg2);
				// goshowload();
				go();
				// address.this.statusTextView.setText("文件下载完成");
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.other_bindingbank);
		edit_bank_number = (EditText) findViewById(R.id.edit_bank_number);
		text_another = (TextView) findViewById(R.id.text_another);
		edit_pro = (EditText) findViewById(R.id.edit_pro);
		edit_city = (EditText) findViewById(R.id.edit_city);
		edit_con = (EditText) findViewById(R.id.edit_con);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		lin_another = (LinearLayout) findViewById(R.id.lin_another);
		lin_another.setVisibility(View.GONE);
		Lin_back = (LinearLayout) findViewById(R.id.Lin_back);
		cookiesdetail = getSharedPreferences("cookies", Context.MODE_PRIVATE);
		loadSpinner();
		Log.e("ghh", strbank + strCity + strCounty + strProvince);
		Lin_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		text_another.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (another == 0) {
					another = 1;
					lin_another.setVisibility(View.VISIBLE);
				} else {
					another = 0;
					lin_another.setVisibility(View.GONE);
				}
			}
		});
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				banknumber = edit_bank_number.getText().toString();

				if (banknumber.equals("")) {
					Toast.makeText(bindingbank.this, "请输入银行卡号", Toast.LENGTH_SHORT).show();
				} else if (banknumber.length() < 10) {
					Toast.makeText(bindingbank.this, "卡号过短", Toast.LENGTH_SHORT).show();
				} else {
					if (another == 0) {
						// get?
						if (tempint == 0) {
							btn_ok.setText("正在绑定....");
							tempint = 1;
							bindbank(strProvince, strCity, strCounty);
							Log.e("ghh", strbank + strCity + strCounty + strProvince);
						} else {
							Toast.makeText(bindingbank.this, "请勿重复点击", Toast.LENGTH_SHORT).show();
						}

					} else {
						incity = edit_city.getText().toString();
						inpro = edit_pro.getText().toString();
						incon = edit_con.getText().toString();
						if (inpro.equals("")) {
							Toast.makeText(bindingbank.this, "请输入省份，或者点击“点击输入”关闭此填写框", Toast.LENGTH_SHORT).show();
						} else if (incity.equals("")) {
							Toast.makeText(bindingbank.this, "请输入城市，或者点击“点击输入”关闭此填写框", Toast.LENGTH_SHORT).show();
						} else if (incon.equals("")) {
							Toast.makeText(bindingbank.this, "请输入县/区，或者点击“点击输入”关闭此填写框", Toast.LENGTH_SHORT).show();
						} else {
							// strCounty = incon;
							// strCity = incity;
							// strProvince = inpro;
							if (tempint == 0) {
								btn_ok.setText("正在绑定....");
								bindbank(inpro, incity, incon);
								tempint = 1;
								Log.e("ghh", strbank + incity + incon + inpro);
							} else {
								Toast.makeText(bindingbank.this, "请勿重复点击", Toast.LENGTH_SHORT).show();
							}
						}
					}
				}

			}
		});
	}

	private void loadSpinner() {
		// display = (EditText) findViewById(R.id.display_edit);
		bank_spinner = (Spinner) findViewById(R.id.bank_spinner);
		bank_adapter = ArrayAdapter.createFromResource(this, R.array.bingdingcardbanktype,
				android.R.layout.simple_spinner_item);
		bank_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		bank_spinner.setAdapter(bank_adapter);
		bank_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				strbank = bank_spinner.getSelectedItem().toString();
				// display.setText(strProvince+"-"+strCity+"-"+strCounty);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
		province_spinner = (Spinner) findViewById(R.id.province_spinner);
		// province_spinner.setPrompt("请选择省份");
		province_adapter = ArrayAdapter.createFromResource(this, R.array.province_item,
				android.R.layout.simple_spinner_item);
		province_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		province_spinner.setAdapter(province_adapter);
		// select(province_spinner, province_adapter, R.array.province_item);
		province_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				provinceId = province_spinner.getSelectedItemPosition();
				strProvince = province_spinner.getSelectedItem().toString();
				city_spinner = (Spinner) findViewById(R.id.city_spinner);
				if (true) {
					Log.v("test", "province: " + province_spinner.getSelectedItem().toString() + provinceId.toString());
					county_spinner = (Spinner) findViewById(R.id.county_spinner);
					city_spinner = (Spinner) findViewById(R.id.city_spinner);
					// city_spinner.setPrompt("请选择城市");
					select(city_spinner, city_adapter, city[provinceId]);
					city_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
							cityId = city_spinner.getSelectedItemPosition();
							strCity = city_spinner.getSelectedItem().toString();
							strCity = strCity + "市";
							Log.v("test", "city: " + city_spinner.getSelectedItem().toString() + cityId.toString());
							if (true) {
								county_spinner = (Spinner) findViewById(R.id.county_spinner);
								// county_spinner.setPrompt("请选择县区");
								switch (provinceId) {
								case 0:
									select(county_spinner, county_adapter, countyOfBeiJing[cityId]);
									break;
								case 1:
									select(county_spinner, county_adapter, countyOfTianJing[cityId]);
									break;
								case 2:
									select(county_spinner, county_adapter, countyOfHeBei[cityId]);
									break;
								case 3:
									select(county_spinner, county_adapter, countyOfShanXi1[cityId]);
									break;
								case 4:
									select(county_spinner, county_adapter, countyOfNeiMengGu[cityId]);
									break;
								case 5:
									select(county_spinner, county_adapter, countyOfLiaoNing[cityId]);
									break;
								case 6:
									select(county_spinner, county_adapter, countyOfJiLin[cityId]);
									break;
								case 7:
									select(county_spinner, county_adapter, countyOfHeiLongJiang[cityId]);
									break;
								case 8:
									select(county_spinner, county_adapter, countyOfShangHai[cityId]);
									break;
								case 9:
									select(county_spinner, county_adapter, countyOfJiangSu[cityId]);
									break;
								case 10:
									select(county_spinner, county_adapter, countyOfZheJiang[cityId]);
									break;
								case 11:
									select(county_spinner, county_adapter, countyOfAnHui[cityId]);
									break;
								case 12:
									select(county_spinner, county_adapter, countyOfFuJian[cityId]);
									break;
								case 13:
									select(county_spinner, county_adapter, countyOfJiangXi[cityId]);
									break;
								case 14:
									select(county_spinner, county_adapter, countyOfShanDong[cityId]);
									break;
								case 15:
									select(county_spinner, county_adapter, countyOfHeNan[cityId]);
									break;
								case 16:
									select(county_spinner, county_adapter, countyOfHuBei[cityId]);
									break;
								case 17:
									select(county_spinner, county_adapter, countyOfHuNan[cityId]);
									break;
								case 18:
									select(county_spinner, county_adapter, countyOfGuangDong[cityId]);
									break;
								case 19:
									select(county_spinner, county_adapter, countyOfGuangXi[cityId]);
									break;
								case 20:
									select(county_spinner, county_adapter, countyOfHaiNan[cityId]);
									break;
								case 21:
									select(county_spinner, county_adapter, countyOfChongQing[cityId]);
									break;
								case 22:
									select(county_spinner, county_adapter, countyOfSiChuan[cityId]);
									break;
								case 23:
									select(county_spinner, county_adapter, countyOfGuiZhou[cityId]);
									break;
								case 24:
									select(county_spinner, county_adapter, countyOfYunNan[cityId]);
									break;
								case 25:
									select(county_spinner, county_adapter, countyOfXiZang[cityId]);
									break;
								case 26:
									select(county_spinner, county_adapter, countyOfShanXi2[cityId]);
									break;
								case 27:
									select(county_spinner, county_adapter, countyOfGanSu[cityId]);
									break;
								case 28:
									select(county_spinner, county_adapter, countyOfQingHai[cityId]);
									break;
								case 29:
									select(county_spinner, county_adapter, countyOfNingXia[cityId]);
									break;
								case 30:
									select(county_spinner, county_adapter, countyOfXinJiang[cityId]);
									break;
								case 31:
									select(county_spinner, county_adapter, countyOfHongKong[cityId]);
									break;
								case 32:
									select(county_spinner, county_adapter, countyOfAoMen[cityId]);
									break;
								case 33:
									select(county_spinner, county_adapter, countyOfTaiWan[cityId]);
									break;

								default:
									break;
								}

								county_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

									@Override
									public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
										strCounty = county_spinner.getSelectedItem().toString();
										// display.setText(strProvince+"-"+strCity+"-"+strCounty);
									}

									@Override
									public void onNothingSelected(AdapterView<?> arg0) {

									}

								});
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

	}

	private void select(Spinner spin, ArrayAdapter<CharSequence> adapter, int arry) {
		adapter = ArrayAdapter.createFromResource(this, arry, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adapter);
		// spin.setSelection(0,true);
	}

	private void bindbank(String sheng, String shi, String xian) {
		Log.d("xxxxx", "1r");
		cookies = cookiesdetail.getString("cookies", "");
		final ArrayList nameValuePairs = new ArrayList();
		nameValuePairs.add(new BasicNameValuePair("BankType", strbank));
		nameValuePairs.add(new BasicNameValuePair("BankNo", banknumber));
		nameValuePairs.add(new BasicNameValuePair("Privince", sheng));
		nameValuePairs.add(new BasicNameValuePair("City", shi));
		nameValuePairs.add(new BasicNameValuePair("Districts", xian));
		// Log.d("selindex", "selindex" + "read" + selindex);
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				StringBuilder sb = null;
				JSONArray jArray = null;
				String result = null;
				InputStream is = null;
				String jsonString = null;
				String data = null;
				// TODO Auto-generated method stub
				DefaultHttpClient httpClient = new DefaultHttpClient();
				Log.d("xxxxx", "1");
				HttpPost httpPost = new HttpPost("http://lottery1.cftb58.cn/App/BindCard");
				Log.d("xxxxx", "11");
				httpPost.setHeader("Cookie", cookies);//
				Log.d("xxxxx", "111");

				try {
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
					HttpResponse httpResponse;
					Log.d("xxxxx", "1111");
					httpResponse = httpClient.execute(httpPost);
					Log.d("xxxxx", "12222");
					is = httpResponse.getEntity().getContent();
					Log.e("xxxxx", "14444");
					jsonString = Util.convertStreamToString(is);
					jsonString = getstandard(jsonString);
					Log.d("xxxxx", jsonString);
					String t;
					String tz;
					String l;

					try {
						Log.d("xxxxx", "jhkk");
						jArray = new JSONArray(jsonString);
						JSONObject json_data = null;
						for (int i = 0; i < jArray.length(); i++) {
							json_data = jArray.getJSONObject(i);
							msg = json_data.getString("Msg");
							// game[i] = json_data.getString("game");
							// no[i] = json_data.getString("Value");
							// expect[i] = json_data.getString("No");

						}
						Message msg1 = new Message();
						// // // 便我们做出不同的处理操作
						msg1.what = 2;

						// 我们可以通过arg1和arg2给Message传入简单的数据
						msg1.arg1 = 123;
						msg1.arg2 = 321;
						// 我们也可以通过给obj赋值Object类型传递向Message传入任意数据
						// msg.obj = null;
						// 我们还可以通过setData方法和getData方法向Message中写入和读取Bundle类型的数据
						// msg.setData(null);
						// Bundle data = msg.getData();

						// 将该Message发送给对应的Handler
						uiHandler.sendMessage(msg1);
					} catch (JSONException e1) {
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					// getCookie(httpClient);
					return jsonString;

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
			}

			// protected void onPostExecute(String result) {
			// tvList.setText(result);
			// };

		}.execute();
	}

	private void go() {
		Toast.makeText(bindingbank.this, "" + msg, Toast.LENGTH_SHORT).show();
		btn_ok.setText("确定");
		tempint = 0;

	}

	public String getstandard(String str) {
		String t[] = str.split("");
		int temp = 0;
		str = "[" + str + "]";
		return str;
	}
}
