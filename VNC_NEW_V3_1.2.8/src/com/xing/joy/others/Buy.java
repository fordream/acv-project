package com.xing.joy.others;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.xing.utaehon.R;
import jp.co.xing.utaehon.processor.IDFreePurchaseComparseProcessor;
import jp.co.xing.utaehon.processor.REUSULT;
import jp.co.xing.utaehon03.util.CommonUtils;
import jp.co.xing.utaehon03.util.LogUtils;
import jp.co.xing.utaehon03.util.UrlUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ict.library.database.DataStore;
import com.vnp.loader.img.ImageClientLoader;
import com.xing.joy.common.DownloadProgessBar;
import com.xing.joy.common.LoadingPriceBar;
import com.xing.joy.common.PackageSongs;
import com.xing.joy.interfaces.IDataActions;
import com.xing.joy.processdata.Download;
import com.xing.joy.processdata.DownloadHistory;
import com.xing.joy.processdata.FilesModel;
import com.xing.joy.processdata.ReportHistory;
import com.xing.joy.processdata.Unzip;

/**
 * This activity for display purchase screen.
 * */
public class Buy extends BaseInappActivity implements OnTouchListener,
		OnClickListener {
	private String _package = "aaa";
	/** Tag log name. */
	private static final String TAG = Buy.class.getSimpleName();

	/** View id=CONST_ID+packageID. */
	private static final int CONST_ID = 1500;

	/** List all packages that show in Buy Screen. */
	private List<PackageSongs> packageSongs = new ArrayList<PackageSongs>();

	/** List all songs in version 1. */
	private List<PackageSongs> singleV1Songs = new ArrayList<PackageSongs>();

	/** List all songs in version 2. */
	private List<PackageSongs> singleV2Songs = new ArrayList<PackageSongs>();

	/** List all songs in version 1. */
	private List<PackageSongs> singleV1_2Songs = new ArrayList<PackageSongs>();

	/** Position of 6 songs in one page. */
	private int[][] coor = { { 250, 30 }, { 580, 30 }, { 250, 230 },
			{ 580, 230 }, { 250, 430 }, { 580, 430 } };

	/** Image Slide. */
	private ImageView imgSlide;

	/** Image switch to list vol1&2. */
	private ImageView imgSwitch;

	/** Image vol1 button. */
	private ImageView imgVol1;

	/** Image vol2 button. */
	private ImageView imgVol2;

	/** Image information. */
	private ImageView imgInfo;

	/** Image cancel information. */
	private ImageView imgCancelInfo;

	/** Temporary ImageView storing the Icon of Package which is clicked. */
	private static ImageView purchasedImage;

	/** Image for buy button. */
	private ImageView imgBuy = null, imgCancel = null, hideBtnBuy;

	/** Image for download button. */
	private LoadingPriceBar hidePrice;

	/** Image for download button. */
	private ImageView imgDownload;

	/** Image for button displayed when Download OK. */
	private ImageView imgOK;

	/** Unknown purpose. */
	private AnimationDrawable aniDrawSwitchSingle;

	/** Total of all pages in scroll. */
	private int totalPage = 0;
	private int currentPage = 0;

	/**
	 * Mode display = 0: show package songs Mode display = 1: show single songs
	 */
	private int modeDisplay = 0;

	/**
	 * When modeDisplay = 1 Vol display = 1: show single songs vol 1 Vol display
	 * = 2: show single songs vol 2
	 */
	private static int volDisplay = 0;

	/**
	 * The SharedPreferences key for recording whether we initialized the
	 * database. If false, then we perform a RestoreTransactions request to get
	 * all the purchases for this user.
	 */

	/** Store flag about billing support result. */
	private static final String BILLING_SUPPORT = "billing_support";

	/** Purchase package when user select. */
	private String purchasedPackage = null;

	/** File path saved. */
	// private String pathSave = null;

	/** Observer to update UI. */

	/** Handler to post action to UI thread. */
	private Handler mHandler;

	/** Dialog type 1. */
	private static final int DIALOG_CANNOT_CONNECT_ID = 1;

	/** Dialog type 2. */
	private static final int DIALOG_BILLING_NOT_SUPPORTED_ID = 2;

	/** Shared preference file name to store purchase result. */
	private static final String PURCHASED_DB = "purchased_db";

	/** Share Preference for purchase_db. */
	private SharedPreferences prefs = null;

	/** Editor for edit data. */
	private SharedPreferences.Editor edit = null;

	/** Flag detect SDCard Exist. */
	private boolean isSDCardExisted = false;
	/**
	 * Flag detect application run in first time, by this flag to prevent
	 * download when restore transaction is finish.
	 */

	/** Download ProgressBar X close button. */
	private ImageView mProgressCloseX;

	/** Download ProgressBar Deer. */
	private ImageView mProgressDeer;

	/** Download ProgressBar background whole. */
	private ImageView mProgressBgr;

	/** Download ProgressBar background. */
	private ImageView mProgressKage;

	/** Download ProgressBar. */
	private ImageView mProgressBar;

	/** Text on Download ProgressBar */
	private ImageView mProgressText;

	private TextView page;

	private DownloadProgessBar downloadBar;

	private DownloadSongPackage dlPackage;

	private DownloadHistory dlHistory;

	private SongPackageUnzip spUnzip;

	private Handler waitingDownload;
	private Runnable runDownload;

	private ViewPager pager;
	private PackagesPagerAdapter packagesAdapter;

	private Map<String, ?> listSongBought;
	private Map<String, String> listSongDateModifier = new HashMap<String, String>();

	// ArrayList<RelativeLayout> listPage = new ArrayList<RelativeLayout>();

	private class PackagesPagerAdapter extends PagerAdapter {
		private List<PackageSongs> packageSongs = new ArrayList<PackageSongs>();

		@Override
		public int getCount() {
			// return totalPage;
			int total = packageSongs.size() / 6;

			if (packageSongs.size() % 6 != 0) {
				total += 1;
			}
			return total;
		}

		public void addData(List<PackageSongs> packageSongs) {
			this.packageSongs.clear();
			notifyDataSetChanged();
			// FIXME 20130219
			this.packageSongs.clear();
			if (packageSongs != null)
				this.packageSongs.addAll(packageSongs);
			notifyDataSetChanged();
		}

		@Override
		public Object instantiateItem(View collection, int position) {
			RelativeLayout layout = null;
			layout = createOnPage(position, packageSongs);
			((ViewPager) collection).addView(layout, 0);
			return layout;
		}

		@Override
		public void destroyItem(View collection, int position, Object view) {
			RelativeLayout tmp = (RelativeLayout) view;
			for (int i = 0; i < tmp.getChildCount(); i++) {
				setNullViewDrawable((ImageView) tmp.getChildAt(i));
			}
			tmp.removeAllViews();
			tmp = null;
			((ViewPager) collection).removeView((RelativeLayout) view);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((RelativeLayout) object);
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

	}

	private List<PackageSongs> getListPackageSongs() {
		switch (modeDisplay) {
		case 0:
			return packageSongs;
		case 1:
			switch (volDisplay) {
			case 0:
				return singleV1_2Songs;
			case 1:
				return singleV1Songs;
			case 2:
				return singleV2Songs;
			}
			break;
		}
		return packageSongs;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		LogUtils.eCommon("onCreate : " + getClass().getName());
		// Create hem image.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy);

		createHemImage(R.id.buy_display);

		ImageClientLoader.getInstance().clear();
		// parse package to display
		// new GetPackageInfo().execute();
		// Initial shared preferences.
		prefs = getApplication().getSharedPreferences(PURCHASED_DB,
				MODE_PRIVATE);
		edit = prefs.edit();

		imgSlide = (ImageView) findViewById(R.id.slider);
		resizeView(imgSlide, 0, 0, 0, 0);
		imgSwitch = (ImageView) findViewById(R.id.tankyoku);
		resizeView(imgSwitch, 0, 568, 0, 0);
		imgSwitch.setOnTouchListener(this);
		imgSwitch.setBackgroundResource(R.anim.single_click_change);

		aniDrawSwitchSingle = (AnimationDrawable) imgSwitch.getBackground();
		imgVol1 = (ImageView) findViewById(R.id.vol1);
		resizeView(imgVol1, 0, 290, 0, 0);
		imgVol1.setOnTouchListener(this);
		imgVol2 = (ImageView) findViewById(R.id.vol2);
		resizeView(imgVol2, 0, 420, 0, 0);
		imgVol2.setOnTouchListener(this);
		imgInfo = (ImageView) findViewById(R.id.info);
		resizeView(imgInfo, 0, 0, 0, 0);

		showView(imgVol1, false);
		showView(imgVol2, false);
		// FIXME 20130219 start
		showView(imgInfo, false);
		// FIXME 20130219 end

		imgBuy = (ImageView) findViewById(R.id.ok_info);
		resizeView(imgBuy, 485, 510, 0, 0);
		hidePrice = (LoadingPriceBar) findViewById(R.id.background_price_1);
		resizeView(hidePrice, 280, 425, 0, 0);
		hideBtnBuy = (ImageView) findViewById(R.id.background_price_2);
		resizeView(hideBtnBuy, 163, 503, 0, 0);
		imgDownload = (ImageView) findViewById(R.id.dl_btn);
		resizeView(imgDownload, 485, 510, 0, 0);
		imgOK = (ImageView) findViewById(R.id.ok_download);
		resizeView(imgOK, 388, 510, 0, 0);
		imgCancelInfo = (ImageView) findViewById(R.id.cancel_info);
		resizeView(imgCancelInfo, 285, 510, 0, 0);
		mProgressCloseX = (ImageView) findViewById(R.id.close_x_btn);
		resizeView(mProgressCloseX, 898, 0, 0, 0);
		imgBuy.setOnClickListener(this);
		imgDownload.setOnClickListener(this);
		imgCancelInfo.setOnClickListener(this);
		imgOK.setOnClickListener(this);
		mProgressCloseX.setOnClickListener(this);

		imgCancel = (ImageView) findViewById(R.id.canceldownload);
		resizeView(imgCancel, 425, 558, 0, 0);
		imgCancel.setOnTouchListener(cancelDownload);

		// Initialize In-app Billing.
		mHandler = new Handler();

		/**
		 * If not purchased, setup the Billing widgets, then check if Billing is
		 * supported. Enable click buy button.
		 */
		// Check if billing is supported.
		imgBuy.setEnabled(false);

		// get list songs were bought and check new version
		dlHistory = new DownloadHistory(getApplication());
		// FIXME start thread for get date
		new Thread(getDateModifierPackBought).start();

		// Check SDCARD Exist.
		isSDCardExisted = (memory.checkSDFreeMB() == 0) ? false : true;
		if (!isSDCardExisted) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.no_sdcard), Toast.LENGTH_LONG).show();
		}

		pager = (ViewPager) findViewById(R.id.awesomepager);
		// pager.setBackgroundResource(R.drawable.a1_18_iphone_buy_haikei);
		setBacground3Resource(pager,
				R.drawable.a1_18_iphone_buy_haikei_fixoutofmemory);

		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				currentPage = arg0;
				page.setText((arg0 + 1) + "/" + totalPage + " "
						+ getString(R.string.page));

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		resizeView(pager, 0, 0, 0, 0);

		page = (TextView) findViewById(R.id.help_text);
		resizeView(page, 505, 600, 0, 0);
		page.setTextColor(Color.rgb(102, 51, 0));
		try {
			Typeface font = Typeface.createFromFile(this.getFilesDir()
					.toString() + "/a_otf_jun501pro_bold.otf");
			page.setTypeface(font);
		} catch (Exception e) {
			e.printStackTrace();
		}
		addBackButton(R.id.buy_display);

		loadData();
		// testBitmap();
	}

	private OnTouchListener cancelDownload = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			try {
				if (imgCancel != null
						&& imgCancel.getVisibility() == View.VISIBLE) {
					if (dlPackage != null) {
						dlPackage.cancel(true);
						dlPackage = null;
					}

					com.xing.joy.processdata.Download.isCancelled = true;
					downloadBar.reset();
					downloadBar.setVisibility(View.GONE);
					imgCancel.setVisibility(View.GONE);

					// imgBuy.setImageResource(0);
					setBacground3Resource(imgBuy, 0);
					imgDownload.setVisibility(View.VISIBLE);
					imgCancelInfo.setVisibility(View.VISIBLE);

					// FIXME 20130219 START=============
					imgCancelInfo.setEnabled(true);
					imgCancelInfo.setAlpha(255);
					mProgressCloseX.setEnabled(true);
					mProgressCloseX.setAlpha(255);
					// FIXME 20130219 END==============

					isTouch = true;
				}
			} catch (Exception e) {
			}
			return true;
		}
	};

	private Runnable getDateModifierPackBought = new Runnable() {

		@Override
		public void run() {
			try {
				listSongBought = prefs.getAll();
				listSongBought = dlHistory.getAll();
				List<NameValuePair> values = new ArrayList<NameValuePair>();

				List<PackageSongs> list = getLPackageSongs();
				for (PackageSongs songs : list) {
					values.add(new BasicNameValuePair("pack[]", songs
							.getPackageName()));
				}

				if (values.size() <= 0) {
					return;
				}

				values.add(new BasicNameValuePair("device_id", CommonUtils
						.findDeviceID(Buy.this)));

				CredentialsProvider credProvider = new BasicCredentialsProvider();

				credProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST,
						AuthScope.ANY_PORT), new UsernamePasswordCredentials(
						getString(R.string.url_user),
						getString(R.string.url_password)));

				DefaultHttpClient httpclient = new DefaultHttpClient();
				httpclient.setCredentialsProvider(credProvider);

				String pathFile = new UrlUtils(Buy.this)
						.getUrl(R.string.url_package_download);
				if (isTablet()) {
					pathFile = new UrlUtils(Buy.this)
							.getUrl(R.string.url_package_hd_download);
				}

				HttpPost httppost = new HttpPost(pathFile
						+ "checkLastUpdate.php");

				httppost.setEntity(new UrlEncodedFormEntity(values));
				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();

				getDataFromResponse(is);
				listSongBought.clear();
			} catch (Exception e) {
			}
		}
	};

	private void getDataFromResponse(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				String[] info = line.split("#");
				listSongDateModifier.put(info[0], info[1]);
				// Log.e("IDID RESULT", info[0] + " : " + info[1] + "");
				String namePackage = info[0];
				if (new File(memory.getPathFileExternalMemory(namePackage))
						.exists()) {
					String time = dlHistory.getStringData(namePackage + ".zip");
					if (time == null
							|| (time != null && time.trim().equals(""))) {
						dlHistory.setStringData(namePackage + ".zip", info[1]);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if (hasFocus) {
			if (aniDrawSwitchSingle != null) {
				aniDrawSwitchSingle.start();
			}
			if (page != null) {
				int sizePage = page.getHeight() / 2;
				if (sizePage < 20) {
					sizePage = 20;
				}
				if (sizePage > 40) {
					sizePage = 40;
				}
				page.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizePage);
				page.setText((currentPage + 1) + "/" + totalPage + " "
						+ getString(R.string.page));
			}
		}
	}

	private void changeModeDisplay1() {
		if (viewClick != null && _package != null) {
			if (new File(memory.getPathFileExternalMemory(_package)).exists()) {
				if (isPurchased(_package) || isPurchasedOrFreeSong(_package)) {
					AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.4f);
					alphaAnimation.setFillAfter(true);
					viewClick.setAnimation(alphaAnimation);
				}
			}

			_package = null;
		}
	}

	public void changeModeDisplay() {

		calculateTotalPage();

		packagesAdapter = new PackagesPagerAdapter();
		if (pager != null)
			pager.setAdapter(packagesAdapter);
		// currentPage = 0;
		// pager.setCurrentItem(currentPage, false);
		if (page != null) {
			page.setText((currentPage + 1) + "/" + totalPage + " "
					+ getString(R.string.page));
		}

		packagesAdapter.addData(getListPackageSongs());
		totalPage = packagesAdapter.getCount();
		if (page != null) {
			page.setText((currentPage + 1) + "/" + totalPage + " "
					+ getString(R.string.page));
		}
		if (pager != null)
			pager.setCurrentItem(currentPage, false);
	}

	public RelativeLayout createOnPage(int position, List<PackageSongs> packages) {

		RelativeLayout onPage = new RelativeLayout(this);
		// onPage.setBackgroundResource(R.drawable.a1_18_iphone_buy_haikei);
		setBacground3Resource(onPage,
				R.drawable.a1_18_iphone_buy_haikei_fixoutofmemory);
		resizeViewInView(onPage, 960, 640, 0, 0, 0, 0);
		int countPackage = 6 * position;
		for (int j = 0; j < 6; j++) {
			// initial image.

			final PackageSongs packageSongs = packages.get(countPackage);

			String path = memory.getPathFileInternalMemory() + "img_buy/"
					+ packageSongs.getIconImage();

			// Bitmap bmImg = BitmapFactory.decodeFile(path);
			final ImageView imgPackageButton = new ImageView(this) {
				@Override
				protected void onDetachedFromWindow() {
					/* 20130410 */
					try {
						Drawable drawable = getDrawable();
						setBackgroundResource(0);
						(((BitmapDrawable) drawable)).getBitmap().recycle();
					} catch (Exception exception) {

					}
					super.onDetachedFromWindow();
				}
			};
			final int _j = j;

			imageClientLoader.newpath(imgPackageButton, path);

			AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.4f);
			alphaAnimation.setFillAfter(true);

			// bai da mua, da download thi hien thi mo, old code
			if (isAppInstalled(memory.getPathFileExternalMemory()
					+ packageSongs.getPackageName(),
					packageSongs.getPackageName())) {
				// imgPackageButton.getBackground().setAlpha(100);
				imgPackageButton.startAnimation(alphaAnimation);
			}

			// bai co moi quan he da mua
			if (!isDebug()
					&& isAppRelationInstalled(packageSongs.getPackageName(),
							packageSongs.getPackagesRelation())) {
				// imgPackageButton.getBackground().setAlpha(100);
				imgPackageButton.startAnimation(alphaAnimation);
			}

			imgPackageButton.setId(CONST_ID + packageSongs.getPackageID());

			try {
				resizeViewInView(imgPackageButton, 317, 166, coor[_j][0],
						coor[_j][1], 0, 0);
			} catch (Exception ex) {
			}

			String packageName = packageSongs.getPackageName();
			boolean isPurchased = (isPurchased(packageName) || checkFreeSongDownload(packageName));

			// Bai da mua, da download nhung co ban update
			if (isPurchased && !allowUserRedownloadPackage(packageName)) {
				// imgPackageButton.getBackground().setAlpha(100);
				imgPackageButton.startAnimation(alphaAnimation);
			}

			// --------------------Start 20130306_1149----------
			IDFreePurchaseComparseProcessor comparseProcessor = new IDFreePurchaseComparseProcessor(
					this, packageName);
			Intent intent = comparseProcessor.onProcessor();
			REUSULT result = (REUSULT) intent
					.getSerializableExtra(IDFreePurchaseComparseProcessor.STR_RESULT);

			if (result == REUSULT.ISPURCHARSE_AND_DOWNLOADEDFREE) {
				// bai co the mua, nhung da co content free
				imgPackageButton.startAnimation(alphaAnimation);
			} else if (result == REUSULT.ISFREE_AND_DOWNLOADED_OVERTIME) {
				// Bai free nhung da co content moi
				imgPackageButton.startAnimation(alphaAnimation);
			}

			// --------------------END 20130306_1149----------
			imgPackageButton.setOnClickListener(new OnClickSong(packageSongs));
			packageSongs.setImage(imgPackageButton);
			onPage.addView(imgPackageButton);
			countPackage++;
			if (countPackage >= packages.size()) {
				break;
			}

		}
		// listPage.add(onPage);
		return onPage;
	}

	// Process Touch Event.
	public boolean onTouch(View view, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN && isTouch) {
			switch (view.getId()) {
			case R.id.tankyoku:
				currentPage = 0;
				if (modeDisplay == 1) {
					modeDisplay = 0;
					// recalculate number pages and recreate page.
					imgVol1.setBackgroundResource(0);
					imgVol2.setBackgroundResource(0);

					imgVol1.setEnabled(false);
					imgVol2.setEnabled(false);
					showView(imgVol1, false);
					showView(imgVol2, false);
					// imgSlide.setBackgroundResource(R.drawable.sideber_cat_iphone);
					setBacground3Resource(imgSlide,
							R.drawable.sideber_cat_iphone);
					changeModeDisplay();

					imgSwitch.setBackgroundResource(R.anim.single_click_change);
					aniDrawSwitchSingle = (AnimationDrawable) imgSwitch
							.getBackground();
					aniDrawSwitchSingle.start();
				} else {
					modeDisplay = 1;
					switch (volDisplay) {
					case 0:
						setBacground3Resource(imgVol1,
								R.drawable.vol1off_iphone);
						setBacground3Resource(imgVol2,
								R.drawable.vol2off_iphone);

						setBacground3Resource(imgSlide,
								R.drawable.sideber_iphone);
						showView(imgVol1, true);
						showView(imgVol2, true);
						changeModeDisplay();

						break;
					case 1:
						// recalculate number pages and recreate page.

						setBacground3Resource(imgVol1, R.drawable.vol1on_iphone);
						setBacground3Resource(imgVol2,
								R.drawable.vol2off_iphone);
						setBacground3Resource(imgSlide,
								R.drawable.sideber_iphone);
						showView(imgVol1, true);
						showView(imgVol2, true);
						changeModeDisplay();
						break;
					case 2:
						setBacground3Resource(imgVol1,
								R.drawable.vol1off_iphone);
						setBacground3Resource(imgVol2, R.drawable.vol2on_iphone);
						setBacground3Resource(imgSlide,
								R.drawable.sideber_iphone);
						showView(imgVol1, true);
						showView(imgVol2, true);
						changeModeDisplay();
						break;
					}
					imgVol1.setEnabled(true);
					imgVol2.setEnabled(true);
					aniDrawSwitchSingle.stop();
					imgSwitch.setBackgroundResource(R.drawable.buy_pack_iphone);
					// setBacgroundResource(imgSwitch,
					// R.drawable.buy_pack_iphone);

				}
				break;

			case R.id.vol1:
				volDisplay = 1;
				currentPage = 0;

				setBacground3Resource(imgVol1, R.drawable.vol1on_iphone);
				setBacground3Resource(imgVol2, R.drawable.vol2off_iphone);
				setBacground3Resource(imgSlide, R.drawable.sideber_iphone);
				showView(imgVol1, true);
				showView(imgVol2, true);
				changeModeDisplay();
				break;

			case R.id.vol2:
				volDisplay = 2;
				currentPage = 0;

				setBacground3Resource(imgVol1, R.drawable.vol1off_iphone);
				setBacground3Resource(imgVol2, R.drawable.vol2on_iphone);
				showView(imgVol1, true);
				showView(imgVol2, true);
				setBacground3Resource(imgSlide, R.drawable.sideber_iphone);
				changeModeDisplay();
				break;

			default:
				break;
			}
		}
		return true;
	}

	@Override
	public void onClick(View view) {
		if (!isTouch) {
			return;
		}
		page.setVisibility(View.INVISIBLE);
		boolean isPurchased = false;

		if (view == imgCancelInfo || view == mProgressCloseX) {
			imgInfo.setOnTouchListener(null);
			// imgCancelInfo.setImageResource(0);
			// imgBuy.setImageResource(0);
			setBacground3Resource(imgBuy, 0);
			setBacground3Resource(imgCancelInfo, 0);
			imgDownload.setVisibility(View.GONE);
			// mProgressCloseX.setImageResource(0);
			setBacground3Resource(mProgressCloseX, 0);
			setNullViewDrawable(imgInfo);
			// FIXME 20130219 start
			showView(imgInfo, false);
			// FIXME 20130219 end

			imgOK.setVisibility(View.GONE);
			imgBack.setVisibility(View.VISIBLE);
			imgBuy.setEnabled(false);
			purchasedPackage = null;
			page.setVisibility(View.VISIBLE);
			hideBtnBuy.setVisibility(View.GONE);
			hidePrice.setVisibility(View.GONE);

			changeModeDisplay1();

		} else if (view == imgOK) {
			changeModeDisplay1();
			imgInfo.setOnTouchListener(null);
			imgOK.setVisibility(View.GONE);
			imgBuy.setImageResource(0);
			imgDownload.setVisibility(View.GONE);
			// mProgressCloseX.setImageResource(0);
			setBacground3Resource(mProgressCloseX, 0);

			setNullViewDrawable(imgInfo);
			// FIXME 20130219 start
			showView(imgInfo, false);
			// FIXME 20130219 end

			imgBack.setVisibility(View.VISIBLE);
			page.setVisibility(View.VISIBLE);
			hideBtnBuy.setVisibility(View.GONE);
			hidePrice.setVisibility(View.GONE);
			imgBuy.setEnabled(false);
			purchasedPackage = null;
		} else if (view == imgBuy) {
			// 20130814 NO network
			// if (!ZNetworkUtils.haveConnectTed(this)) {
			// showDialog(ZDialogUtils.DIALOG_ERROR_002);
			// return;
			// }

			if (!isPurchased) {
				if (spUnzip != null || dlPackage != null) {
					return;
				}

				launchPurchaseFlow(purchasedPackage);
			} else {
				Log.d(TAG, "Already bought song");
			}
			// Disable Buy and Cancel button after Buy button is clicked.
			imgBuy.setEnabled(false);
			imgBuy.setAlpha(50);
			imgCancelInfo.setEnabled(false);
			imgCancelInfo.setAlpha(50);
			mProgressCloseX.setEnabled(false);
			mProgressCloseX.setAlpha(50);
			isTouch = false;
		} else if (view == imgDownload) {
			// 20130814 NO network
			// if (!ZNetworkUtils.haveConnectTed(this)) {
			// showDialog(ZDialogUtils.DIALOG_ERROR_002);
			// return;
			// }

			Log.d(TAG, "Already bought song, redownloading:" + purchasedPackage);
			setBacground3Resource(imgBuy, 0);
			dlPackage = (DownloadSongPackage) new DownloadSongPackage(Buy.this)
					.execute();
		}
	}

	/**
	 * This method used for calculate number of pages loaded.
	 * */
	private void calculateTotalPage() {

		// calculate number pages.
		if (modeDisplay == 0) {
			totalPage = PackageSongs.packageSongs / 6;
			if (PackageSongs.packageSongs % 6 > 0) {
				totalPage += 1;
			}
		} else {
			switch (volDisplay) {
			case 0:
				totalPage = PackageSongs.singleV1_2Songs / 6;
				if (PackageSongs.singleV1_2Songs % 6 > 0) {
					totalPage += 1;
				}
				break;
			case 1:
				totalPage = PackageSongs.singleV1Songs / 6;
				if (PackageSongs.singleV1Songs % 6 > 0) {
					totalPage += 1;
				}
				break;
			case 2:
				totalPage = PackageSongs.singleV2Songs / 6;
				if (PackageSongs.singleV2Songs % 6 > 0) {
					totalPage += 1;
				}
				break;
			}
		}
		if (totalPage == 0) {
			totalPage = 1;
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_CANNOT_CONNECT_ID:
			return createDialog(R.string.cannot_connect_title,
					R.string.cannot_connect_message);
		case DIALOG_BILLING_NOT_SUPPORTED_ID:
			return createDialog(R.string.billing_not_supported_title,
					R.string.billing_not_supported_message);
		default:
			return super.onCreateDialog(id);
		}
	}

	/**
	 * This function allow enable buy button. Used when billing support or when
	 * user not purchased an item.
	 */
	private void enableWidgets() {
		imgBuy.setEnabled(true);
	}

	public boolean allowUserRedownloadPackage(String purchasedPackage) {
		// try {
		String path = memory.getPathFileExternalMemory() + purchasedPackage;

		ReportHistory report = new ReportHistory(Buy.this);

		if ((!isAppInstalled(path, purchasedPackage) && checkFreeSongDownload(purchasedPackage))
				|| isPurchasedButNotDownloaded(path, purchasedPackage)
				|| (isAppInstalled(path, purchasedPackage) && report
						.getReportCountByPackage(purchasedPackage) > 0)) {
			return true;
		}

		String timeHis = dlHistory.getStringData(purchasedPackage + ".zip");
		String timeMod = null;

		if (listSongDateModifier.containsKey(purchasedPackage)) {
			timeMod = listSongDateModifier.get(purchasedPackage);
		}

		if (isAppInstalled(path, purchasedPackage) && timeMod != null
				&& !timeHis.equalsIgnoreCase(timeMod)) {
			return true;
		}
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		return false;
	}

	public boolean allowUserRedownloadPackage2(String purchasedPackage) {
		// String path = memory.getPathFileExternalMemory() + purchasedPackage;
		String timeHis = dlHistory.getStringData(purchasedPackage + ".zip");
		String timeMod = null;

		DataStore dataStore = DataStore.getInstance();
		dataStore.init(this);
		timeMod = dataStore.get("DATE" + purchasedPackage, null);

		if (timeMod == null
				|| timeHis == null
				|| (timeMod != null && timeHis != null && timeMod.trim()
						.equals(timeHis.trim()))) {
			return true;
		}

		return false;
	}

	/**
	 * Make a request purchase.
	 */

	/**
	 * Called when this activity becomes visible.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// FIXME 20130219
		// loadData();
	}

	private void loadData() {
		imgInfo.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (!isBillingSupported()) {
					if (!isDebug())
						showDialog(DIALOG_CANNOT_CONNECT_ID);

				}
				// FIXME 20130219

				// parsePackageInfoXML();
				// changeModeDisplay();
			}
		}, 1000);
		parsePackageInfoXML();
		changeModeDisplay();
	}

	@Override
	public void onBackPressed() {
		if (!isTouch) {
			return;
		}

		// FIXME 20130219 Start
		// if detail of package is show
		// cancel button back of device
		if (isSHow(imgInfo)) {
			if (dlPackage == null && spUnzip == null) {
				onClick(imgCancelInfo);
				return;
			} else if (dlPackage != null || spUnzip != null) {
				return;
			}
		}
		// FIXME 20130219 END

		if (modeDisplay != 0) {
			modeDisplay = 0;
			setBacground3Resource(imgSlide, R.drawable.sideber_cat_iphone);

			imgVol1.setBackgroundResource(0);
			imgVol2.setBackgroundResource(0);
			showView(imgVol1, false);
			showView(imgVol2, false);
			changeModeDisplay();
			imgVol1.setEnabled(false);
			imgVol2.setEnabled(false);

			imgSwitch.setBackgroundResource(R.anim.single_click_change);

			aniDrawSwitchSingle = (AnimationDrawable) imgSwitch.getBackground();
			aniDrawSwitchSingle.start();
		} else {
			if (dlPackage != null) {
				dlPackage.cancel(true);
			}
			if (spUnzip != null) {
				spUnzip.cancel(false);
			}
			releaseMemory();
			try {
				Bundle extras = getIntent().getExtras();
				if (extras.getString("Back") != null
						&& extras.getString("Back").equalsIgnoreCase(
								"SelectSong")) {
					CommonUtils.startNewActivity(this, SelectSong.class, "");
				} else {
					CommonUtils.startNewActivity(this, Top.class, "");
				}
			} catch (Exception e) {
				CommonUtils.startNewActivity(this, Top.class, "");
			} finally {
			}

			super.onBackPressed();
		}
	}

	@Override
	protected void onUserLeaveHint() {
		appData.setIntData(IDataActions.PROCESS_ID, android.os.Process.myPid());
		// Don't finish activity when purchase screen display.
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, " BUY OnDestroy");
		super.onDestroy();
	}

	@Override
	public void releaseMemory() {
		// if (listPage != null) {
		// for (Iterator<RelativeLayout> getPage = listPage.iterator(); getPage
		// .hasNext();) {
		// RelativeLayout page = (RelativeLayout) getPage.next();
		// for (int i = 0; i < page.getChildCount(); i++) {
		// setNullViewDrawable((ImageView) page.getChildAt(i));
		// }
		// page.clearDisappearingChildren();
		// page.removeAllViews();
		// page.removeAllViewsInLayout();
		// page.setBackgroundResource(0);
		// page.removeAllViews();
		// page.destroyDrawingCache();
		// page.setBackgroundDrawable(null);
		// page.setBackgroundResource(0);
		// page = null;
		// }
		// listPage.clear();
		// }
		if (page != null) {
			page.setText("");
			page = null;
		}
		if (imgOK != null) {
			setNullViewDrawable(imgOK);
		}
		if (imgCancelInfo != null) {
			setNullViewDrawable(imgCancelInfo);
		}
		if (mProgressCloseX != null) {
			setNullViewDrawable(mProgressCloseX);
		}
		if (imgBuy != null) {
			setNullViewDrawable(imgBuy);
		}
		if (imgDownload != null) {
			setNullViewDrawable(imgDownload);
		}
		if (imgInfo != null) {
			setNullViewDrawable(imgInfo);
			// FIXME 20130219 start
			showView(imgInfo, false);
			// FIXME 20130219 end
		}
		if (imgSlide != null) {
			setNullViewDrawable(imgSlide);
		}
		if (imgSwitch != null) {
			setNullViewDrawable(imgSwitch);
		}
		if (imgVol1 != null) {
			setNullViewDrawable(imgVol1);
		}
		if (imgVol2 != null) {
			setNullViewDrawable(imgVol2);
		}
		if (mProgressDeer != null) {
			setNullViewDrawable(mProgressDeer);
		}
		if (mProgressBgr != null) {
			setNullViewDrawable(mProgressBgr);
		}
		if (mProgressKage != null) {
			setNullViewDrawable(mProgressKage);
		}
		if (mProgressBar != null) {
			setNullViewDrawable(mProgressBar);
		}
		if (mProgressText != null) {
			setNullViewDrawable(mProgressText);
		}
		if (imgCancel != null) {
			setNullViewDrawable(imgCancel);
			imgCancel = null;
		}
		if (hidePrice != null) {
			hidePrice.setPrice(0);
			hidePrice = null;
		}
		if (hideBtnBuy != null) {
			setNullViewDrawable(hideBtnBuy);
			hideBtnBuy = null;
		}
		imgDownload = null;
		imgCancelInfo = null;
		mProgressCloseX = null;
		imgOK = null;
		imgBuy = null;
		imgInfo = null;
		imgSlide = null;
		imgSwitch = null;
		imgVol1 = null;
		imgVol2 = null;

		packageSongs = null;
		singleV1Songs = null;
		singleV2Songs = null;
		coor = null;
		aniDrawSwitchSingle = null;
		purchasedPackage = null;
		mHandler = null;
		mProgressDeer = null;
		mProgressBgr = null;
		mProgressKage = null;
		mProgressBar = null;
		mProgressText = null;
		packagesAdapter = null;
		pager = null;
		super.releaseMemory();
	}

	private class DownloadSongPackage extends Download {

		@Override
		protected void onPreExecute() {
			isTouch = false;
			if (downloadBar == null) {
				downloadBar = (DownloadProgessBar) findViewById(R.id.download_bar);
				DownloadProgessBar.typeDataDownload = 1;
				resizeView(downloadBar, 163, 425, 0, 0);
			}
			downloadBar.setVisibility(View.VISIBLE);
			imgCancel.setVisibility(View.VISIBLE);
			mProgressCloseX.setAlpha(100);
			imgCancelInfo.setVisibility(View.INVISIBLE);
			imgDownload.setVisibility(View.INVISIBLE);
			super.onPreExecute();
		}

		protected Boolean doInBackground(FilesModel... params) {
			FilesModel[] fm = new FilesModel[1];
			String pathFile = new UrlUtils(Buy.this)
					.getUrl(R.string.url_package_download);

			if (isTablet()) {
				pathFile = new UrlUtils(Buy.this)
						.getUrl(R.string.url_package_hd_download);
			}

			fm[0] = new FilesModel(purchasedPackage + ".zip",
					pathFile+ "download.php?package=" + purchasedPackage,
					memory.getPathCacheExternalMemory());
			return super.doInBackground(fm[0]);
		}

		@Override
		public void onProgressUpdate(Integer... args) {
			if (args[0] != null) {
				downloadBar.updateProgessBar(args[0]);
			}
		}

		public DownloadSongPackage(Context context) {
			super(context, true);
		}

		// @Override
		// public void callbackFailByMD5() {
		// showDialog(ZDialogUtils.DIALOG_ERROR_004);
		// }
		//
		// public void callBackDownloadFail() {
		// showDialog(ZDialogUtils.DIALOG_ERROR_002);
		// }
		//
		// @Override
		// public void callBackMemoryFoo() {
		// }
		//
		// @Override
		// public void callBackMemoryFree() {
		// }

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			dlPackage = null;
			if (result != null && result) {
				// unzip
				FilesModel[] fm = new FilesModel[1];
				fm[0] = new FilesModel(purchasedPackage + ".zip",
						memory.getPathCacheExternalMemory() + purchasedPackage
								+ ".zip", memory.getPathFileExternalMemory());
				DownloadProgessBar.STATUS_DOWNLOAD = getString(R.string.dlp_unzip);
				downloadBar.invalidate();
				spUnzip = (SongPackageUnzip) new SongPackageUnzip(Buy.this)
						.execute(fm);
				// sent download info to statistic
				// Create a new HttpClient and Post Header

				new Thread(new Runnable() {
					@Override
					public void run() {
						HttpClient httpclient = new DefaultHttpClient();

						String url = new UrlUtils(Buy.this)
								.getUrl(R.string.url_top_free_download)
								+ "download.php";
						HttpPost httppost = new HttpPost(url);
						try {
							// Add your data
							List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
									2);
							nameValuePairs.add(new BasicNameValuePair("device",
									Build.DEVICE));
							nameValuePairs.add(new BasicNameValuePair(
									"product", Build.PRODUCT));
							nameValuePairs.add(new BasicNameValuePair("model",
									Build.MODEL));
							nameValuePairs.add(new BasicNameValuePair(
									"manufacturer", Build.MANUFACTURER));
							nameValuePairs.add(new BasicNameValuePair(
									"os_version", Build.VERSION.RELEASE));
							nameValuePairs.add(new BasicNameValuePair(
									"device_id", Build.ID));
							nameValuePairs.add(new BasicNameValuePair("imei",
									CommonUtils.findDeviceID(Buy.this)));
							nameValuePairs.add(new BasicNameValuePair(
									"product_item_id", purchasedPackage));
							httppost.setEntity(new UrlEncodedFormEntity(
									nameValuePairs));
							httpclient.execute(httppost);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();

			} else {
				// FIXME 20130219
				cancelDownload.onTouch(null, null);

				// downloadBar.setVisibility(View.GONE);
				// imgCancel.setVisibility(View.GONE);
				// downloadBar.reset();
				// imgBuy.setImageResource(0);
				// imgDownload.setVisibility(View.VISIBLE);
				// imgCancelInfo.setVisibility(View.VISIBLE);
				// isTouch = true;
			}
			super.onPostExecute(result);
		}
	}

	private class SongPackageUnzip extends Unzip {

		public SongPackageUnzip(Context context) {
			super(context);
		}

		@Override
		protected void onPreExecute() {
			try {
				imgCancel.setVisibility(View.GONE);
			} catch (Exception e) {
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			spUnzip = null;

			try {
				if (result != null && result && imgOK != null) {
					imgBuy.setAlpha(255);
					imgCancelInfo.setAlpha(255);
					downloadBar.setVisibility(View.GONE);
					imgCancel.setVisibility(View.GONE);
					downloadBar.reset();
					imgBuy.setImageResource(0);
					imgCancelInfo.setVisibility(View.GONE);
					imgDownload.setVisibility(View.GONE);
					Toast.makeText(Buy.this,
							getString(R.string.dlp_dl_completed),
							Toast.LENGTH_LONG).show();

					ReportHistory report = new ReportHistory(Buy.this);
					report.resetReportCountByPackage(purchasedPackage);

					// FIXME
					if (checkFreeSongDownload(purchasedPackage)) {
						SharedPreferences.Editor edit = prefs.edit();
						edit.putString(purchasedPackage,
								"true;" + System.currentTimeMillis());
						edit.commit();
					}

					String timeMode = listSongDateModifier
							.get(purchasedPackage);

					if (timeMode != null && !timeMode.trim().equals("")) {
						dlHistory.setStringData(purchasedPackage + ".zip",
								timeMode);
					}

					imgOK.postDelayed(new Runnable() {
						@Override
						public void run() {
							if (imgOK != null) {
								imgOK.setVisibility(View.VISIBLE);
								if (checkFreeSongDownload(purchasedPackage)) {
									setBacground3Resource(imgOK,
											R.drawable.dlok_bt_iphone);
								} else {
									setBacground3Resource(imgOK,
											R.drawable.buy_ok);
								}
								isTouch = true;
								mProgressCloseX.setAlpha(255);
								mProgressCloseX.setEnabled(true);
							}
						}
					}, 3000);

					if (purchasedImage.getBackground() != null)
						purchasedImage.getBackground().setAlpha(100);
				} else {
					// 20130814
					try {
						if (dlPackage != null) {
							dlPackage.cancel(true);
							dlPackage = null;
						}

						com.xing.joy.processdata.Download.isCancelled = true;
						downloadBar.reset();
						downloadBar.setVisibility(View.GONE);
						imgCancel.setVisibility(View.GONE);

						setBacground3Resource(imgBuy, 0);
						imgDownload.setVisibility(View.VISIBLE);
						imgCancelInfo.setVisibility(View.VISIBLE);

						imgCancelInfo.setEnabled(true);
						imgCancelInfo.setAlpha(255);
						mProgressCloseX.setEnabled(true);
						mProgressCloseX.setAlpha(255);
						isTouch = true;
					} catch (Exception exception) {

					}
				}
			} catch (Exception e) {
			}
		}
	}

	private class GetPackagePrice extends AsyncTask<Void, Integer, Boolean> {

		ProgressDialog progressBar;
		int price = 0;

		@Override
		protected void onPreExecute() {
			// prepare for a progress bar dialog
			progressBar = new ProgressDialog(Buy.this);
			progressBar.setCancelable(false);
			progressBar.setCanceledOnTouchOutside(false);
			progressBar.setMessage("Loading...");
			progressBar.show();
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			price = 0;
			URL tmp = null;
			HttpURLConnection urlConnection = null;
			try {

				String url = new UrlUtils(Buy.this)
						.getUrl(R.string.url_get_price)
						+ "?package_id="
						+ purchasedPackage;

				tmp = new URL(url);
				urlConnection = (HttpURLConnection) tmp.openConnection();
				// set up some things on the connection.
				urlConnection.setDoInput(true);
				urlConnection.setRequestMethod("GET");
				urlConnection.setReadTimeout(45000);
				urlConnection.setConnectTimeout(45000);
				// and connect!
				urlConnection.connect();

				// Check valid download
				String typeData = urlConnection.getHeaderField("content-type");
				if (typeData.contains("text/plain")) {
					BufferedReader in = new BufferedReader(
							new InputStreamReader(
									urlConnection.getInputStream()));
					price = Integer.valueOf(in.readLine());
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

		protected void onPostExecute(Boolean result) {
			progressBar.dismiss();
			if (result) {
				showView(imgBuy, true);
				showView(imgCancelInfo, true);
				hideBtnBuy.setVisibility(View.GONE);
				hidePrice.setPrice(price);
				hidePrice.setOnClickListener(null);
				hideBtnBuy.setOnClickListener(null);
			} else {
				CommonUtils.popupAlert(Buy.this,
						getString(R.string.load_price_failed));
			}
		}

	}

	public void parsePackageInfoXML() {
		List<PackageSongs> list = getLPackageSongs();
		// Log.e("ZISE", list.size() + "");

		for (PackageSongs songs : list) {
			if (songs.getPackageVol() == 1) {
				singleV1Songs.add(songs);
			} else if (songs.getPackageVol() == 2) {
				singleV2Songs.add(songs);
			} else if (songs.getPackageVol() == 3) {
				packageSongs.add(songs);
			}
		}

		singleV1_2Songs.addAll(singleV1Songs);
		singleV1_2Songs.addAll(singleV2Songs);
	}

	/*
	 * Download
	 */
	@Override
	protected void onIabPurchaseFinish(boolean failure, String sku) {
		if (!failure) {
			setEnableAndAlPha(false, 100, imgCancelInfo);
			setEnableAndAlPha(false, 50, mProgressCloseX);
			setBacground3Resource(imgBuy, 0);
			dlPackage = (DownloadSongPackage) new DownloadSongPackage(this)
					.execute();
		} else {
			// FIXME 20130219
			if (isDebug()) {
				setEnableAndAlPha(false, 100, imgCancelInfo);
				setEnableAndAlPha(false, 50, mProgressCloseX);
				setBacground3Resource(imgBuy, 0);
				dlPackage = (DownloadSongPackage) new DownloadSongPackage(this)
						.execute();
			} else {
				setEnableAndAlPha(true, 255, imgCancelInfo);
				setEnableAndAlPha(true, 255, mProgressCloseX);
			}
		}

		setEnableAndAlPha(true, 255, imgBuy);

		isTouch = true;
	}

	private class OnClickSong implements View.OnClickListener {
		private PackageSongs songs;

		public OnClickSong(PackageSongs songs) {
			this.songs = songs;
		}

		@Override
		public void onClick(View v) {
			viewClick = v;
			processPackagePurchased(songs);

			LogUtils.eOutOfMemoryError(v.getContext());
		}
	}

	private View viewClick;

	@Override
	protected boolean isAppRelationInstalled(String namePackage,
			String namePackageRelation) {
		boolean isPackRelationPurchased = false;

		// check package relation
		// Log.e("namePackageRelation", namePackageRelation + "");
		if (!(namePackage.equalsIgnoreCase("") || namePackageRelation
				.equalsIgnoreCase(namePackage))) {
			if (namePackageRelation.contains("#")) {
				String[] parseInfo = namePackageRelation.trim().split("#");
				for (int i = 0; i < parseInfo.length; i++) {
					if (isPurchased(parseInfo[i])) {
						isPackRelationPurchased = true;
						break;
					}
				}
			} else {
				isPackRelationPurchased = isPurchased(namePackageRelation);
			}
		}

		return isPackRelationPurchased;
	}

	@Override
	public String getNameCount() {
		// return Logtime.PAGE_BUYPACKAGE;
		return getResources().getString(R.string.namescreen_buy);
	}

	/**
	 * This method used for detect status of song package. By purchase status
	 * and setup status to setup UI.
	 * 
	 * @param ps
	 *            {@link PackageSongs}Package to process.
	 * @return {@link Boolean} Result package installed or not.
	 * */

	private boolean processPackagePurchased(PackageSongs ps) {
		// FIXME 20130219 start
		showView(imgInfo, true);

		// FIXME 20130219 end
		/* 20130410 */
		// try {
		// Drawable drawable = imgInfo.getDrawable();
		// imgInfo.setBackgroundResource(0);
		// (((BitmapDrawable) drawable)).getBitmap().recycle();
		// } catch (Exception exception) {
		//
		// }
		/* 20130410 end */

		purchasedPackage = ps.getPackageName();
		purchasedImage = ps.getImage();

		_package = purchasedPackage;

		// mProgressCloseX.setImageResource(R.drawable.close_btn_adr4);
		setBacground3Resource(mProgressCloseX, R.drawable.close_btn_adr4);

		mProgressCloseX.setEnabled(true);

		// hidePrice.setVisibility(View.VISIBLE);
		hidePrice.setPrice(0);
		showViewWhenTime(hidePrice);
		imgBack.setVisibility(View.GONE);

		String path = memory.getPathFileInternalMemory() + "img_buy/"
				+ ps.getImageInfo();

		// imgInfo.setImageBitmap(imageClientLoader.getBitmapImageLoader1(path));
		// imgInfo.setBackgroundResource(R.drawable.nona);

		imageClientLoader.newpath(imgInfo, path);
		imgInfo.setBackgroundResource(R.drawable.a10_yagiyubin_detail_iphone1);
		// imageClientLoader.getBitmapImageLoader(path);
		// imgInfo.setImageBitmap(imageClientLoader.getBitmapImageLoader1(path));
		// imageClientLoader.getBitmap(path, imgInfo);
		imgInfo.setOnTouchListener(this);
		// FIX:20130206
		page.setVisibility(View.INVISIBLE);

		String purchasedPackageRelation = ps.getPackagesRelation();

		// --------------------Start 20130306_1149----------
		IDFreePurchaseComparseProcessor comparseProcessor = new IDFreePurchaseComparseProcessor(
				this, purchasedPackage);
		Intent intent = comparseProcessor.onProcessor();
		REUSULT result = (REUSULT) intent
				.getSerializableExtra(IDFreePurchaseComparseProcessor.STR_RESULT);
		if (result == REUSULT.ISPURCHARSE_AND_DOWNLOADEDFREE) {
			// imgOK.setVisibility(View.VISIBLE);
			purchasedPackage = purchasedPackage.replace(
					IDFreePurchaseComparseProcessor.PUCHARSEID,
					IDFreePurchaseComparseProcessor.FREEID);
			if (downloadedButRedownload(purchasedPackage)) {
				setBacground3Resource(imgCancelInfo, R.drawable.cancel_iphone);
				imgCancelInfo.setEnabled(true);
				imgBuy.setImageResource(0);
				imgBuy.setEnabled(false);
				showViewWhenTime(imgDownload);
				setBacground3Resource(
						imgDownload,
						checkFreeSongDownload(purchasedPackage) ? R.drawable.dl_bt_iphone
								: R.drawable.re_dl);
				showViewWhenTime(imgCancelInfo);
			} else {
				showViewWhenTime(imgOK);
				setBacground3Resource(imgOK, R.drawable.dlok_bt_iphone);
				imgCancelInfo.setImageResource(0);
				imgCancelInfo.setEnabled(false);
				imgBuy.setImageResource(0);
				imgBuy.setEnabled(false);
			}
			return true;
		}

		// --------------------End 20130306_1149----------
		if (!isPurchased(purchasedPackage)
				&& isAppRelationInstalled(purchasedPackage,
						purchasedPackageRelation)) {
			if (!isDebug()) {
				hideBtnBuy.setVisibility(View.INVISIBLE);
				imgOK = (ImageView) findViewById(R.id.ok_download);
				// setBacground3Resource(imgOK, R.drawable.buy_ok);
				// imgOK.setVisibility(View.VISIBLE);
				// imgOK.setEnabled(true);
				// showViewWhenTime(imgOK);
			}
		}

		if (!isPurchased(purchasedPackage)
				&& isAppRelationInstalled(purchasedPackage,
						purchasedPackageRelation)) {
			if (!isDebug()) {
				// imgOK.setVisibility(View.VISIBLE);
				// showViewWhenTime(imgOK);
				imgCancelInfo.setImageResource(0);
				imgCancelInfo.setEnabled(false);
				imgBuy.setImageResource(0);
				imgBuy.setEnabled(false);
				String message = getString((ps.getPackageNumberSongs() == 1) ? R.string.note_package_is_purchased
						: R.string.note_single_is_purchased);
				CommonUtils.popupAlert(Buy.this, message);
				return false;
			}
		}

		boolean isPurchased = (isPurchased(purchasedPackage) || checkFreeSongDownload(purchasedPackage))
				|| isDebug();

		if (!isPurchased) {
			// hideBtnBuy.setVisibility(View.VISIBLE);
			showViewWhenTime(hideBtnBuy);
			hidePrice.setPrice(0);
			hidePrice.setOnClickListener(this);
			hideBtnBuy.setOnClickListener(this);
			setBacground3Resource(hideBtnBuy, R.drawable.haikei_iphone);

			setBacground3Resource(imgCancelInfo, R.drawable.cancel_iphone);

			// imgCancelInfo.setVisibility(View.VISIBLE);

			showViewWhenTime(imgCancelInfo);
			imgCancelInfo.setEnabled(true);
			setBacground3Resource(imgBuy, R.drawable.buy_iphone);
			enableWidgets();

			if (!isSDCardExisted || !isBillingSupported()) {
				imgBuy.setAlpha(50);
				imgBuy.setEnabled(false);
			}

			showView(imgBuy, false);
			showView(imgCancelInfo, false);
			new GetPackagePrice().execute();
		} else {
			hideBtnBuy.setVisibility(View.GONE);
			// redownload
			String pat1h = memory.getPathFileExternalMemory()
					+ purchasedPackage;

			boolean isExistFile = new File(pat1h + ".ini").exists();

			// if (isDebug()) {
			if (isExistFile) {
				if (downloadedButRedownload(purchasedPackage)) {
					setBacground3Resource(imgCancelInfo,
							R.drawable.cancel_iphone);
					imgCancelInfo.setEnabled(true);
					imgBuy.setImageResource(0);
					imgBuy.setEnabled(false);
					showViewWhenTime(imgDownload);
					setBacground3Resource(
							imgDownload,
							checkFreeSongDownload(purchasedPackage) ? R.drawable.dl_bt_iphone
									: R.drawable.re_dl);
					showViewWhenTime(imgCancelInfo);
				} else {
					showViewWhenTime(imgOK);
					setBacground3Resource(
							imgOK,
							checkFreeSongDownload(purchasedPackage) ? R.drawable.dlok_bt_iphone
									: R.drawable.buy_ok);
					imgCancelInfo.setImageResource(0);
					imgCancelInfo.setEnabled(false);
					imgBuy.setImageResource(0);
					imgBuy.setEnabled(false);
				}
			} else {
				setBacground3Resource(imgCancelInfo, R.drawable.cancel_iphone);
				imgCancelInfo.setEnabled(true);
				imgBuy.setImageResource(0);
				imgBuy.setEnabled(false);
				showViewWhenTime(imgDownload);
				setBacground3Resource(
						imgDownload,
						checkFreeSongDownload(purchasedPackage) ? R.drawable.dl_bt_iphone
								: R.drawable.re_dl);
				showViewWhenTime(imgCancelInfo);
			}
		}

		return isPurchased;
	}

	private void showViewWhenTime(final View view) {
		if (view != null) {
			view.setVisibility(View.VISIBLE);
		}
	}

	public boolean downloadedButRedownload(String purchasedPackage) {
		try {
			String timeHis = dlHistory.getStringData(purchasedPackage + ".zip");
			String timeMod = null;

			if (listSongDateModifier.containsKey(purchasedPackage)) {
				timeMod = listSongDateModifier.get(purchasedPackage);
			}

			if (timeMod != null && !timeHis.equalsIgnoreCase(timeMod)) {
				if (!timeHis.trim().equals("")) {
					return true;
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	// =============================================================
	// start date :
	// version :
	// =============================================================

	private void testBitmap() {

		// Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.a1_18_iphone_karaoke_haikei);
		// CommonLog.e("SIZE", bitmap.getWidth() + " " + bitmap.getHeight());
		// bitmap.recycle();
	}

}