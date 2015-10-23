package com.xing.joy.others;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.xing.utaehon.R;
import jp.co.xing.utaehon.processor.IDFreePurchaseComparseProcessor;
import jp.co.xing.utaehon.processor.REUSULT;
import jp.co.xing.utaehon03.util.CommonUtils;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.xing.joy.common.PackageSongs;
import com.xing.joy.common.PackagesList;
import com.xing.joy.interfaces.IDataActions;

public class Credit extends BaseInappActivity implements OnTouchListener {

	/** Media player. */
	private MediaPlayer mp;

	/** Web View show credit. */
	private WebView credit;

	/** Layout of credit. */
	private RelativeLayout rl;

	/** Share Preference for purchase_db. */

	private int countCol = 0;
	private ArrayList<String> arrListCredits = new ArrayList<String>();
	private ArrayList<String> arrListCreditsFree = new ArrayList<String>();

	/** Package list. */
	private PackagesList packagesList = null;

	/** List all packages that show in Buy Screen. */
	private HashMap<String, Boolean> packageSongs = new HashMap<String, Boolean>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initial shared preferences.
		prefs = getApplication().getSharedPreferences(IDataActions.PURCHASED_DB, MODE_PRIVATE);

		setContentView(R.layout.credit);
		createHemImage(R.id.credit_display);
		rl = (RelativeLayout) findViewById(R.id.credit_display);

		// Process credit button.
		credit = new WebView(this);
		RelativeLayout.LayoutParams prView = new RelativeLayout.LayoutParams(Math.round(960 * calResize.getRatioResizeWidth()), Math.round(640 * calResize.getRatioResizeWidth()));
		prView.setMargins(0 + calResize.getHemBlackWidth(), 0 + calResize.getHemBlackHeight(), 0, 0);
		credit.setId(1004);
		credit.setLayoutParams(prView);
		rl.addView(credit, prView);
		// credit.setBackgroundResource(R.drawable.b1_03_adr_haikei);
		setBacground3Resource(credit, R.drawable.b1_03_adr_haikei_fixoutofmemory);
		credit.setBackgroundColor(0);
		RelativeLayout.LayoutParams prView2 = new RelativeLayout.LayoutParams(Math.round(97 * calResize.getRatioResizeWidth()), Math.round(97 * calResize.getRatioResizeWidth()));
		prView2.setMargins(Math.round(20 * calResize.getRatioResizeWidth()) + calResize.getHemBlackWidth(), Math.round(20 * calResize.getRatioResizeHeight()) + calResize.getHemBlackHeight(), 0, 0);
		prView2.addRule(RelativeLayout.ABOVE);
		addBackButton(R.id.credit_display);
		credit.setWebViewClient(new OpenWebViewClient());

		credit.postDelayed(new Runnable() {
			@Override
			public void run() {
				parsePackageInfoXML();
				String str = "";
				str += loadResouceHTMLFromAssets("credit_header.html");
				loadCreditFileFromInternal();
				for (int i = 0; i < arrListCreditsFree.size(); i++) {
					if (countCol == 0) {
						str += "<tr>";
					}

					str += loadResouceHTMLFromInternal(arrListCreditsFree.get(i));
					if (countCol == 1) {
						str += "</tr>";
						countCol = 0;
						continue;
					}

					countCol++;
				}
				loadCreditFileFromSD();
				for (int i = 0; i < arrListCredits.size(); i++) {

					// add to removie free song
					try {
						String namePackage = arrListCredits.get(i).replace(".credit_1", "").replace(".credit_2", "").replace(".credit_3", "");

						if (namePackage.contains("free.song.relation")) {
							if (!packageSongs.containsKey(namePackage)) {
								continue;
							}
						}
					} catch (Exception exception) {
					}

					if (countCol == 0) {
						str += "<tr>";
					}

					str += loadResouceHTMLFromSD(arrListCredits.get(i));
					if (countCol == 1) {
						str += "</tr>";
						countCol = 0;
						continue;
					}
					countCol++;
				}

				str += loadResouceHTMLFromAssets("credit_footer.html");

				// try {
				// String strVersionName =
				// getPackageManager().getPackageInfo(getPackageName(),
				// 0).versionName;
				// str = str.replace(getString(R.string.version_credit_125),
				// getString(R.string.version_credit_126_none_version) +
				// strVersionName);
				// } catch (Exception exx) {
				// }
				credit.loadDataWithBaseURL("file:///android_asset/", str, "text/html", "UTF-8", "");
			}
		}, 100);
	}

	// @Override
	// protected void onStart() {
	// super.onStart();
	//
	// }

	// private String getVersionName() {
	// try {
	// return getPackageManager().getPackageInfo(getPackageName(),
	// 0).versionName;
	// } catch (Exception exception) {
	// return "1.2.4";
	// }
	// }

	private class OpenWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (URLUtil.isValidUrl(url)) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}

			return true;
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			switch (view.getId()) {
			case 100:
				if (mp != null) {
					mp.stop();
					mp.release();
					mp = null;
				}
				CommonUtils.startNewActivity(this, Top.class, "");
				break;
			default:
				break;
			}
		}
		return true;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mp == null) {
			mp = MediaPlayer.create(Credit.this, R.raw.opening);
			mp.setVolume(volume, volume);
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp.setLooping(true);
		}
		mp.start();
	}

	public String loadResouceHTMLFromAssets(String filename) {
		String tmp = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(getAssets().open(filename)));
			String word;
			while ((word = br.readLine()) != null) {
				if (!word.equalsIgnoreCase("")) {
					tmp += word;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close(); // stop reading
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return tmp;
	}

	public String loadResouceHTMLFromInternal(String filename) {

		String tmp = "";
		BufferedReader br = null;
		try {
			InputStream is = new FileInputStream(new File(getFilesDir().getPath() + "/" + filename));
			br = new BufferedReader(new InputStreamReader(is));
			String word;
			while ((word = br.readLine()) != null) {
				if (!word.equalsIgnoreCase("")) {
					tmp += word.replace("@path@", "file://" + getFilesDir().getPath() + "/");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close(); // stop reading
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return tmp;
	}

	public String loadResouceHTMLFromSD(String filename) {

		String tmp = "";
		BufferedReader br = null;
		try {
			InputStream is = new FileInputStream(new File(memory.getPathFileExternalMemory() + filename));
			br = new BufferedReader(new InputStreamReader(is));
			String word;
			while ((word = br.readLine()) != null) {
				if (!word.equalsIgnoreCase("")) {
					tmp += word.replace("@path@", "file://" + memory.getPathFileExternalMemory());
				}
			}
			tmp = tmp.replace("http://homepage.mac.com/aquemeg", "http://aquemeg.com");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close(); // stop reading
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return tmp;
	}

	public void loadCreditFileFromSD() {
		// Get package bought
		File dir = new File(memory.getPathFileExternalMemory());
		File[] filelist = dir.listFiles();
		if (filelist != null) {
			for (int i = 0; i < filelist.length; i++) {
				if (filelist[i].isFile() && filelist[i].getName().contains(".credit")) {
					String pack = filelist[i].getName().substring(0, filelist[i].getName().length() - 9);

					IDFreePurchaseComparseProcessor freePurchaseComparseProcessor = new IDFreePurchaseComparseProcessor(this, pack);
					REUSULT reusult = (REUSULT) freePurchaseComparseProcessor.onProcessor().getSerializableExtra(IDFreePurchaseComparseProcessor.STR_RESULT);
					// if (reusult == REUSULT.ISFREE_AND_DOWNLOADED_OVERTIME) {
					//
					// } else if (packageSongs.containsKey(pack)
					// && (isPurcharseOld(pack.replace(".credit_1", ""))
					// || isPurchased(pack
					// .replace(".credit_1", "")) || isDebug())) {
					// arrListCredits.add(filelist[i].getName());
					// } else if (checkFreeSongDownload(pack)) {
					// arrListCredits.add(filelist[i].getName());
					// }

					arrListCredits.add(filelist[i].getName());
				}
			}
		}
	}

	public void loadCreditFileFromInternal() {
		// Get package bought
		File dir = new File(memory.getPathFileInternalMemory());
		File[] filelist = dir.listFiles();
		if (filelist != null) {
			for (int i = 0; i < filelist.length; i++) {
				if (filelist[i].isFile() && filelist[i].getName().contains(".credit")) {
					arrListCreditsFree.add(filelist[i].getName());
				}
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mp != null) {
			mp.stop();
			mp.release();
			mp = null;
		}
	}

	@Override
	public void releaseMemory() {
		if (mp != null) {
			mp.stop();
			mp.release();
			mp = null;
		}
		if (credit != null) {
			// credit.setBackgroundResource(0);
			// credit.clearView();
			// credit = null;
		}
		super.releaseMemory();
	}

	@Override
	protected void onDestroy() {

		// CLEAR ANIMATION
		if (mp != null) {
			mp.stop();
			mp.release();
			mp = null;
		}
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		// releaseMemory();
		CommonUtils.startNewActivity(this, Top.class, "");
		super.onBackPressed();

	}

	public void parsePackageInfoXML() {
		List<PackageSongs> list = getLPackageSongs();
		for (PackageSongs songs : list) {
			packageSongs.put(songs.getPackageName(), true);
		}
		// /** Handling & parser XML . */
		// if (new File(memory.getPathFileInternalMemory() + "img_buy/"
		// + "package_info.xml").exists()) {
		// try {
		// /** Handling XML . */
		// SAXParserFactory spf = SAXParserFactory.newInstance();
		// SAXParser sp = spf.newSAXParser();
		// XMLReader xr = sp.getXMLReader();
		//
		// /** Create handler to handle XML Tags ( extends DefaultHandler ) */
		// PackageXMLHandler packageXMLHandler = new PackageXMLHandler();
		// xr.setContentHandler(packageXMLHandler);
		// File packageInfo = new File(memory.getPathFileInternalMemory()
		// + "img_buy/" + "package_info.xml");
		// InputStream inputStream = new FileInputStream(packageInfo);
		// Reader reader = new InputStreamReader(inputStream, "UTF-8");
		// xr.parse(new InputSource(reader));
		// inputStream.close();
		//
		// packagesList = PackageXMLHandler.pList;
		// if (packagesList != null) {
		// PackageSongs.resetCountPackages();
		// for (int i = 0; i < packagesList.getName().size(); i++) {
		// packageSongs.put(packagesList.getName().get(i), true);
		// }
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
	}

	@Override
	protected void onIabPurchaseFinish(boolean failure, String sku) {

	}

	@Override
	public String getNameCount() {
		// return Logtime.PAGE_CREDIT;
		return getResources().getString(R.string.namescreen_credit);
	}
}
