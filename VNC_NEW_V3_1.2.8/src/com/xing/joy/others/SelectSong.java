package com.xing.joy.others;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jp.co.xing.utaehon.R;
import jp.co.xing.utaehon.WaitActivity;
import jp.co.xing.utaehon03.util.CommonUtils;
import jp.co.xing.utaehon03.util.UrlUtils;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ict.library.common.CommonLog;
import com.ict.library.database.DataStore;
import com.xing.joy.common.DownloadProgessBar;
import com.xing.joy.common.PackageSongs;
import com.xing.joy.common.PackagesList;
import com.xing.joy.common.Songs;
import com.xing.joy.interfaces.IDataActions;
import com.xing.joy.play.DynamicSong;
import com.xing.joy.play.PlayMovieDynamicGame;
import com.xing.joy.processdata.Download;
import com.xing.joy.processdata.FilesModel;
import com.xing.joy.processdata.Unzip;

public class SelectSong extends BaseInappActivity implements OnTouchListener {

	ImageView imgForDisableScrollPager;
	private int totalPage = 0;
	private static int currentPage = 0;
	private static int currentTypeSort = 2;
	private ArrayList<String> arrListPackages = new ArrayList<String>();
	private ImageView imgSlide, imgSortName, imgSortOld, imgSortNew, imgBuyBt,
			imgCancel = null;
	private AnimationDrawable draBuyBt;

	private ArrayList<Songs> song = new ArrayList<Songs>();
	private ArrayList<RelativeLayout> pageSong = new ArrayList<RelativeLayout>();

	private int[] imgIdSlide = { R.drawable.haikei_karaoke_iphone,
			R.drawable.haikei_teasobi_iphone, R.drawable.haikei_uta_iphone };

	private int[] imgIdBackground = {
			R.drawable.a1_18_iphone_karaoke_haikei_fixoutofmemory,
			R.drawable.a1_18_iphone_douga_haikei_fixoutofmemory,
			R.drawable.a1_18_iphone_uta_haikei_fixoutofmemory };

	private int[][] coorMovie = { { 250, 30 }, { 580, 30 }, { 250, 230 },
			{ 580, 230 }, { 250, 430 }, { 580, 430 } };

	private int[][] coorSong = { { 260, 30 }, { 480, 30 }, { 700, 30 },
			{ 260, 230 }, { 480, 230 }, { 700, 230 }, { 260, 430 },
			{ 480, 430 }, { 700, 430 } };

	int iconsOnPage = 9;
	private TextView page;

	private DownloadProgessBar downloadBar;
	private SongFreeDataDownload download;
	private AlertDialog alertDialog;

	/** Package list. */
	private PackagesList packagesList = null;

	/** List all packages that show in Buy Screen. */
	private HashMap<String, Boolean> packageSongs = new HashMap<String, Boolean>();

	private ViewPager pager;
	private SongsPagerAdapter songsAdapter;
	private Timer timer;

	public void onCreate(Bundle savedInstanceState) {
		// set isTouch
		isTouch = false;
		super.onCreate(savedInstanceState);

		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		metric.widthPixels = Math.round(metric.widthPixels
				* metric.scaledDensity);
		metric.heightPixels = Math.round(metric.heightPixels
				* metric.scaledDensity);

		Configuration config = new Configuration();
		getBaseContext().getResources().updateConfiguration(config, metric);

		try {
			setContentView(R.layout.select_song);
		} catch (OutOfMemoryError ex) {
			startActivity(new Intent(this, WaitActivity.class));
			System.exit(0);
			return;
		}

		imgForDisableScrollPager = (ImageView) findViewById(R.id.imgForDisableScrollPager);

		imgForDisableScrollPager.setOnClickListener(null);
		showView(imgForDisableScrollPager, false);

		createHemImage(R.id.select_display);

		if (appData.getStringData(IDataActions.TYPE_NAME).equalsIgnoreCase(
				"douga_clicked")) {
			iconsOnPage = 6;
		}

		imgSortName = (ImageView) findViewById(R.id.sort_name);
		resizeView(imgSortName, 16, 240, 0, 0);
		imgSortName.setOnTouchListener(this);

		imgSortOld = (ImageView) findViewById(R.id.sort_old);
		resizeView(imgSortOld, 16, 280, 0, 0);
		imgSortOld.setOnTouchListener(this);

		imgSortNew = (ImageView) findViewById(R.id.sort_new);
		resizeView(imgSortNew, 16, 320, 0, 0);
		imgSortNew.setOnTouchListener(this);

		setImageSort();

		imgBuyBt = (ImageView) findViewById(R.id.buy_bt);
		resizeView(imgBuyBt, 16, 380, 0, 0);
		draBuyBt = (AnimationDrawable) imgBuyBt.getBackground();
		imgBuyBt.setOnTouchListener(this);

		imgSlide = (ImageView) findViewById(R.id.slider);
		resizeView(imgSlide, 0, 0, 0, 0);

		imgCancel = (ImageView) findViewById(R.id.canceldownload);
		resizeView(imgCancel, 425, 530, 0, 0);

		imgCancel.setOnTouchListener(cancelDownload);

		// FIXME OUTOFMEMORY
		pager = (ViewPager) findViewById(R.id.awesomepager);

		pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				currentPage = arg0;
				page.setText((currentPage + 1) + "/" + totalPage + " "
						+ getString(R.string.page));

				// -------------------------------------------
				// -------------------------------------------
				saveCurentPageAndSort();
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

		imgSlide.setBackgroundResource(imgIdSlide[appData
				.getIntData(IDataActions.TYPE_ID)]);
		imgCancel.setBackgroundResource(R.drawable.download_cancel);
		pager.setBackgroundResource(imgIdBackground[appData
				.getIntData(IDataActions.TYPE_ID)]);

		// DataStore.getInstance().init(this);
		// currentPage = DataStore.getInstance().get(getNameCount(), 0);
		getCurentPageAndSort();
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (page != null)
			page.postDelayed(new Runnable() {

				@Override
				public void run() {
					try {
						listPackagesAndAddFreeSong();
					} catch (Exception e) {
						e.printStackTrace();
					}
					parsePackageInfoXML();
					for (int i = 0; i < arrListPackages.size(); i++) {
						getInforOfEachPackage(arrListPackages.get(i));
					}

					layoutPagesView();
					if (imgBack == null) {
						new Handler().postDelayed(new Runnable() {

							@Override
							public void run() {
								addBackButton(R.id.select_display);
								isTouch = true;
							}
						}, 1000);
					}
				}
			}, 100);

	}

	private OnTouchListener cancelDownload = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			try {
				showView(imgForDisableScrollPager, false);
				if (imgCancel != null
						&& imgCancel.getVisibility() == View.VISIBLE) {
					if (download != null) {
						download.cancel(true);
						download = null;
					}
					com.xing.joy.processdata.Download.isCancelled = true;
					downloadBar.reset();
					downloadBar.setVisibility(View.GONE);
					imgCancel.setVisibility(View.GONE);
					isTouch = true;
				}
				if (timer != null) {
					timer.cancel();
					timer = null;
				}
			} catch (Exception e) {
			}
			return true;
		}
	};

	private class SongsPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return totalPage;
		}

		/**
		 * Create the page for the given position. The adapter is responsible
		 * for adding the view to the container given here, although it only
		 * must ensure this is done by the time it returns from
		 * {@link #finishUpdate()}.
		 * 
		 * @param container
		 *            The containing View in which the page will be shown.
		 * @param position
		 *            The page position to be instantiated.
		 * @return Returns an Object representing the new page. This does not
		 *         need to be a View, but can be some other container of the
		 *         page.
		 */

		@Override
		public Object instantiateItem(View collection, int position) {
			RelativeLayout layout = null;
			try {
				layout = createOnPage(position);
				((ViewPager) collection).addView(layout, 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return layout;
		}

		/**
		 * Remove a page for the given position. The adapter is responsible for
		 * removing the view from its container, although it only must ensure
		 * this is done by the time it returns from {@link #finishUpdate()}.
		 * 
		 * @param container
		 *            The containing View from which the page will be removed.
		 * @param position
		 *            The page position to be removed.
		 * @param object
		 *            The same object that was returned by
		 *            {@link #instantiateItem(View, int)}.
		 */
		@Override
		public void destroyItem(View collection, int position, Object view) {
			RelativeLayout tmp = (RelativeLayout) view;
			for (int i = 0; i < tmp.getChildCount(); i++) {
				setNullViewDrawable((ImageView) tmp.getChildAt(i));
			}
			tmp.setBackgroundResource(0);
			tmp.removeAllViews();
			tmp.destroyDrawingCache();
			tmp.setBackgroundDrawable(null);
			tmp.setBackgroundResource(0);
			tmp = null;
			((ViewPager) collection).removeView((RelativeLayout) view);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((RelativeLayout) object);
		}

		/**
		 * Called when the a change in the shown pages has been completed. At
		 * this point you must ensure that all of the pages have actually been
		 * added or removed from the container as appropriate.
		 * 
		 * @param container
		 *            The containing View which is displaying this adapter's
		 *            page views.
		 */
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

	public ImageView createImageSong(final Songs song) {
		ImageView iw = new ImageView(this);
		// Drawable d;
		switch (appData.getIntData(IDataActions.TYPE_ID)) {
		case 0:
		case 2:
			// case for karaoke & uta
			String path1;
			if (song.getPackageName() != "") {
				path1 = memory.getPathFileExternalMemory()
						+ song.getPathImage() + ".nomedia";
			} else {
				if (!checkSongIsDownloaded(song.getSongNameEnglish(),
						song.getPackageName())) {
					path1 = memory.getPathFileInternalMemory()
							+ song.getPathImage() + "_before.png.nomedia";
				} else {
					path1 = memory.getPathFileInternalMemory()
							+ song.getPathImage() + ".png.nomedia";
				}
			}
			// InputStream in1 = null;
			// try {
			// File tmp = new File(path1);
			// in1 = new FileInputStream(tmp);
			// in1.read(tmp.getName().replace(".nomedia", "").getBytes());
			// } catch (final Exception e) {
			// e.printStackTrace();
			// }

			// Bitmap bmImg = BitmapFactory.decodeStream(in1);

			imageClientLoader.getBitmapStreamNomedia(path1, iw);
			// d = new BitmapDrawable(bmImg);
			// iw.setImageDrawable(d);
			// iw.setImageBitmap(bmImg);
			resizeViewInView(iw, 200, 166, -400, -200, 0, 0);
			break;
		case 1:
			String pathImgMovie;
			if (song.getPackageName() != "") {
				pathImgMovie = memory.getPathFileExternalMemory()
						+ song.getPathImageMovie() + ".nomedia";
			} else {
				if (!checkSongIsDownloaded(song.getSongNameEnglish(),
						song.getPackageName())) {
					pathImgMovie = memory.getPathFileInternalMemory()
							+ song.getPathImageMovie() + "_before.png.nomedia";
				} else {
					pathImgMovie = memory.getPathFileInternalMemory()
							+ song.getPathImageMovie() + ".png.nomedia";
				}
			}
			// InputStream in = null;
			// try {
			// File tmp = new File(pathImgMovie);
			// in = new FileInputStream(tmp);
			// in.read(tmp.getName().replace(".nomedia", "").getBytes());
			// } catch (final Exception e) {
			// e.printStackTrace();
			// }
			// Bitmap bmImgMovie = BitmapFactory.decodeStream(in);

			imageClientLoader.getBitmapStreamNomedia(pathImgMovie, iw);
			// System.gc();
			// d = new BitmapDrawable(bmImgMovie);
			// iw.setImageDrawable(d);
			// iw.setImageBitmap(bmImgMovie);
			resizeViewInView(iw, 317, 166, -400, -200, 0, 0);
			break;
		}
		iw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isTouch) {
					return;
				}
				if (!checkSongIsDownloaded(song.getSongNameEnglish(),
						song.getPackageName())) {
					// DOWNLOAD
					FilesModel[] fm = new FilesModel[1];

					// String pathFile =
					// getString(R.string.url_song_free_download);
					// if (isTablet()) {
					// pathFile = getString(R.string.url_song_free_hd_download);
					// }
					//
					String pathFile = new UrlUtils(SelectSong.this)
							.getUrl(R.string.url_song_free_download);
					if (isTablet()) {
						pathFile = new UrlUtils(SelectSong.this)
								.getUrl(R.string.url_song_free_hd_download);
					}

					fm[0] = new FilesModel("song.zip", pathFile
							+ song.getSongNameEnglish().toLowerCase() + ".zip",
							memory.getPathCacheExternalMemory());
					if (download == null) {
						// 20130814 NO network
						// if (!ZNetworkUtils.haveConnectTed(SelectSong.this)) {
						// showDialog(ZDialogUtils.DIALOG_ERROR_002);
						// return;
						// }

						showView(imgForDisableScrollPager, true);
						download = (SongFreeDataDownload) new SongFreeDataDownload(
								SelectSong.this).execute(fm);
					}
				} else {
					// Save data
					// appData.setStringData(IDataActions.PACKAGE_NAME,
					// song.getPackageName());
					// appData.setStringData(IDataActions.SONG_NAME,
					// song.getSongNameEnglish());
					// appData.setStringData(
					// IDataActions.CLASS_NAME,
					// "com.xing.joy.karaokes."
					// + song.getSongNameEnglish());
					// // Set intent
					// if (appData.getIntData(IDataActions.TYPE_ID) == 1) {
					// CommonUtils.startNewActivity(SelectSong.this,
					// PlayMovieDynamicGame.class, "");
					// } else {
					// CommonUtils.startNewActivity(SelectSong.this,
					// DynamicSong.class, "");
					// }

					startActivity(song);
				}
			}
		});
		return iw;
	}

	public RelativeLayout createOnPage(int position) throws Exception {
		RelativeLayout onPage = new RelativeLayout(this);
		// onPage.setBackgroundResource(imgIdBackground[appData
		// .getIntData(IDataActions.TYPE_ID)]);

		onPage.setBackgroundResource(imgIdBackground[appData
				.getIntData(IDataActions.TYPE_ID)]);

		resizeViewInView(onPage, 960, 640, 0, 0, 0, 0);
		pageSong.add(onPage);
		int countSong = iconsOnPage * position;
		for (int j = 0; j < iconsOnPage; j++) {
			ImageView tmp = createImageSong(song.get(countSong));
			onPage.addView(tmp);
			switch (appData.getIntData(IDataActions.TYPE_ID)) {
			case 0:
			case 2:
				setNewLayout(tmp, coorSong[j][0], coorSong[j][1], 0, 0);
				break;
			case 1:
				setNewLayout(tmp, coorMovie[j][0], coorMovie[j][1], 0, 0);
				break;
			}
			countSong++;
			if (countSong >= song.size()) {
				break;
			}
			tmp = null;
		}
		return onPage;
	}

	public void layoutPagesView() {
		calculateTotalPage();
		if (currentPage >= totalPage) {
			currentPage = totalPage - 1;
		}
		if (song != null && song.size() > 0) {
			try {
				changeSortDisplay();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void sortOrderSong(int type) throws Exception {
		switch (type) {
		case 0:
			// sort name
			Collections.sort(song, Songs.SongNameComparator);
			break;
		case 1:
			// sort date
			Songs.sortASC = true;
			Collections.sort(song);
			break;
		case 2:
			// sort date
			Songs.sortASC = false;
			Collections.sort(song);
			break;
		}
	}

	public void getInforOfEachPackage(String pack) {

		try {
			// check relation songs

			if (pack.contains("free.song.relation")) {
				if (!packageSongs.containsKey(pack)) {
					return;
				}
			}

			String path = memory.getPathFileExternalMemory() + pack + ".ini";
			File txt = new File(path);

			BufferedReader br = new BufferedReader(new FileReader(txt));
			String line;
			while ((line = br.readLine()) != null) {
				String[] info = line.trim().split("#");
				Boolean checkSong = false;

				// new song
				if (info[4].trim().equalsIgnoreCase("NULL")
						&& appData.getStringData(IDataActions.TYPE_NAME)
								.equalsIgnoreCase("douga_clicked")) {
					continue;
				}

				if (song != null) {
					for (Iterator<Songs> iterator = song.iterator(); iterator
							.hasNext();) {

						Songs song = (Songs) iterator.next();

						if (song.getSongNameEnglish().equalsIgnoreCase(
								info[1].trim())) {
							checkSong = true;
						}
					}
				}
				if (!checkSong) {
					int infor0 = 1;
					try {
						infor0 = Integer.parseInt(info[0].trim());
					} catch (Exception e) {
					}

					Songs tmp = new Songs(info[1].trim(), info[2].trim(),
							infor0, pack, Long.parseLong(prefs.getString(pack,
									isDebug() + ";0").split(";")[1]),
							info[3].trim(), info[4].trim());

					song.add(tmp);
				}
			}
			br.close();
		} catch (Exception e) {
			Log.e("FREEEE", pack + "", e);
		}
	}

	private class SongFreeDataDownload extends Download {

		@Override
		protected void onPreExecute() {
			isTouch = false;
			if (downloadBar == null) {
				downloadBar = (DownloadProgessBar) findViewById(R.id.download_bar);
				DownloadProgessBar.typeDataDownload = 0;
				resizeView(downloadBar, 163, 396, 0, 0);
			}
			downloadBar.setVisibility(View.VISIBLE);
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					if (DownloadProgessBar.MAX > 0) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								imgCancel.setVisibility(View.VISIBLE);
								if (timer != null)
									timer.cancel();
								timer = null;
							}
						});
					}
				}
			}, 0, 300);

			super.onPreExecute();
		}

		@Override
		public void onProgressUpdate(Integer... args) {
			if (args[0] != null) {
				downloadBar.updateProgessBar(args[0]);
			}
		}

		public SongFreeDataDownload(Context context) {
			super(context, true);
		}

		// @Override
		// public void callbackFailByMD5() {
		// showDialog(ZDialogUtils.DIALOG_ERROR_004);
		// }
		// @Override
		// public void callBackDownloadFail() {
		// showDialog(ZDialogUtils.DIALOG_ERROR_002);
		// }

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
			try {
				if (timer != null) {
					timer.cancel();
					timer = null;
				}
			} catch (Exception e) {
			}
			if (result != null && result) {
				// unzip
				try {
					FilesModel[] fm = new FilesModel[1];
					fm[0] = new FilesModel("song.zip",
							memory.getPathCacheExternalMemory() + "song.zip",
							memory.getPathFileInternalMemory());
					DownloadProgessBar.STATUS_DOWNLOAD = getString(R.string.dlp_unzip);
					downloadBar.invalidate();
					new SongFreeDataUnzip(SelectSong.this).execute(fm);
					download = null;
				} catch (Exception e) {
					showView(imgForDisableScrollPager, false);
				}
			} else {
				showView(imgForDisableScrollPager, false);
				try {
					downloadBar.setVisibility(View.GONE);
					imgCancel.setVisibility(View.GONE);
					downloadBar.reset();
					download = null;
					isTouch = true;
				} catch (Exception e) {
				}
			}
		}
	}

	private class SongFreeDataUnzip extends Unzip {

		public SongFreeDataUnzip(Context context) {
			super(context);
		}

		@Override
		protected void onPreExecute() {
			try {
				imgCancel.setVisibility(View.GONE);
			} catch (Exception e) {
			}
		}

		// @Override
		// public void unZipSucess(boolean result) {
		// if (!result) {
		// showDialog(ZDialogUtils.DIALOG_ERROR_001);
		// }
		// }

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			showView(imgForDisableScrollPager, false);
			try {
				isTouch = true;
				if (result != null && result) {
					changeSortDisplay();
					downloadBar.setVisibility(View.GONE);
					downloadBar.reset();
					isTouch = true;
				} else {
					// unzip error
					// 20130814
					changeSortDisplay();
					downloadBar.setVisibility(View.GONE);
					downloadBar.reset();
					isTouch = true;
				}
			} catch (Exception e) {
			}
		}
	}

	public boolean checkSongIsDownloaded(String songName, String songPackage) {
		if (songPackage.equalsIgnoreCase("")) {
			return new File(memory.getPathFileInternalMemory()
					+ songName.toLowerCase() + "/" + songName.toLowerCase()
					+ ".jar").exists();
		} else {
			return new File(memory.getPathFileExternalMemory()
					+ songName.toLowerCase() + "/" + songName.toLowerCase()
					+ ".jar").exists();
		}

	}

	public void listPackagesAndAddFreeSong() throws NumberFormatException,
			IOException, Exception {
		// Get free song
		try {
			String path = memory.getPathFileInternalMemory() + "free.song.ini";
			File txt = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(txt));
			String line;
			while ((line = br.readLine()) != null) {
				String[] info = line.split("#");
				if (info[4].equalsIgnoreCase("NULL")
						&& appData.getStringData(IDataActions.TYPE_NAME)
								.equalsIgnoreCase("douga_clicked")) {
					continue;
				}
				long time = 1000000000000l - Integer.parseInt(info[0]) / 10;
				Songs tmp = new Songs(info[1], info[2],
						Integer.parseInt(info[0]), "", time, info[3], info[4]);
				song.add(tmp);
			}
			br.close();
		} catch (Exception exception) {
		}

		// Get package bought
		File dir = new File(memory.getPathFileExternalMemory());
		File[] filelist = dir.listFiles();
		if (filelist != null) {
			for (int i = 0; i < filelist.length; i++) {
				if (filelist[i].isFile()
						&& filelist[i].getName().endsWith(".ini")) {
					arrListPackages.add(filelist[i].getName().substring(0,
							filelist[i].getName().length() - 4));
				}
			}
		}
	}

	public String convertStreamToString(InputStream in) throws IOException {
		StringBuffer stream = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			stream.append(new String(b, 0, n));
		}
		return stream.toString();
	}

	public void calculateTotalPage() {
		if (song != null) {
			totalPage = song.size() / iconsOnPage;
			if (song.size() % iconsOnPage > 0) {
				totalPage += 1;
			}
			if (totalPage == 0) {
				totalPage = 1;
			}
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		try {
			if (hasFocus) {
				if (draBuyBt != null) {
					draBuyBt.start();
				}
			}
		} catch (Exception e) {
		}
		super.onWindowFocusChanged(hasFocus);
		if (page != null) {
			int sizePage = page.getHeight() / 2;
			if (sizePage < 20) {
				sizePage = 20;
			}
			if (sizePage > 40) {
				sizePage = 40;
			}
			page.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizePage);
		}
	}

	@Override
	public void onBackPressed() {
		if (imgBack != null && isTouch) {
			releaseMemory();
			CommonUtils.startNewActivity(this, Top.class, "");
			super.onBackPressed();
		}
	}

	@Override
	protected void onUserLeaveHint() {
		super.onUserLeaveHint();
	}

	@Override
	protected void onPause() {
		if (alertDialog != null && alertDialog.isShowing()) {
			alertDialog.dismiss();
			alertDialog = null;
		}
		System.gc();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		if (download != null) {
			download.cancel(true);
			download = null;
		}
		super.onDestroy();
	}

	@Override
	public void releaseMemory() {
		super.releaseMemory();
		if (page != null) {
			page.setText("");
			page = null;
		}
		if (imgSlide != null) {
			setNullViewDrawable(imgSlide);
			imgSlide = null;
		}
		if (arrListPackages != null) {
			arrListPackages.clear();
			arrListPackages = null;
		}
		if (song != null) {
			song.clear();
			song = null;
		}
		if (draBuyBt != null) {
			draBuyBt.stop();
			draBuyBt = null;
		}
		if (pageSong != null) {
			for (Iterator<RelativeLayout> getPage = pageSong.iterator(); getPage
					.hasNext();) {
				RelativeLayout page = (RelativeLayout) getPage.next();
				for (int i = 0; i < page.getChildCount(); i++) {
					setNullViewDrawable((ImageView) page.getChildAt(i));
				}
				page.clearDisappearingChildren();
				page.removeAllViews();
				page.removeAllViewsInLayout();
				page.setBackgroundResource(0);
				page.removeAllViews();
				page.destroyDrawingCache();
				page.setBackgroundDrawable(null);
				page.setBackgroundResource(0);
				page = null;
			}
			pageSong.clear();
		}
		setNullViewDrawable(imgSortName);
		imgSortName = null;
		setNullViewDrawable(imgSortOld);
		imgSortOld = null;
		setNullViewDrawable(imgSortNew);
		imgSortNew = null;
		setNullViewDrawable(imgBuyBt);
		imgBuyBt = null;
		songsAdapter = null;
		pager = null;

	}

	public void setImageSort() {
		switch (currentTypeSort) {
		case 0:
			imgSortName.setBackgroundResource(R.drawable.aiueo_on_iphone);
			imgSortNew.setBackgroundResource(R.drawable.new_off_iphone);
			imgSortOld.setBackgroundResource(R.drawable.old_off_iphone);

			break;
		case 1:
			imgSortName.setBackgroundResource(R.drawable.aiueo_off_iphone);
			imgSortNew.setBackgroundResource(R.drawable.new_off_iphone);
			imgSortOld.setBackgroundResource(R.drawable.old_on_iphone);

			break;
		case 2:
			imgSortName.setBackgroundResource(R.drawable.aiueo_off_iphone);
			imgSortNew.setBackgroundResource(R.drawable.new_on_iphone);
			imgSortOld.setBackgroundResource(R.drawable.old_off_iphone);
			break;
		}
	}

	public void changeSortDisplay() {
		try {
			page.setText((currentPage + 1) + "/" + totalPage + " "
					+ getString(R.string.page));
			if (pageSong != null) {
				for (Iterator<RelativeLayout> getPage = pageSong.iterator(); getPage
						.hasNext();) {
					RelativeLayout page = (RelativeLayout) getPage.next();
					for (int i = 0; i < page.getChildCount(); i++) {
						setNullViewDrawable((ImageView) page.getChildAt(i));
					}
					page.clearDisappearingChildren();
					page.removeAllViews();
					page.removeAllViewsInLayout();
				}
				pageSong.clear();
			}
			sortOrderSong(currentTypeSort);
			setImageSort();
			songsAdapter = null;
			songsAdapter = new SongsPagerAdapter();
			pager.setAdapter(songsAdapter);
			pager.setCurrentItem(currentPage, false);
			pager.setBackgroundResource(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN && isTouch) {
			switch (v.getId()) {
			case R.id.sort_name:
				currentTypeSort = 0;
				currentPage = 0;
				saveCurentPageAndSort();
				changeSortDisplay();
				break;
			case R.id.sort_old:
				currentTypeSort = 1;
				currentPage = 0;
				saveCurentPageAndSort();
				changeSortDisplay();
				break;
			case R.id.sort_new:
				currentTypeSort = 2;
				saveCurentPageAndSort();
				currentPage = 0;
				changeSortDisplay();
				break;
			case R.id.buy_bt:
				releaseMemory();
				CommonUtils.startNewActivity(this, Buy.class, "SelectSong");
				break;

			default:
				break;
			}
		}
		return true;
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
		//
		// // get package list after parse XML.
		// packagesList = PackageXMLHandler.pList;
		// if (packagesList != null) {
		//
		// // initial package songs to manager list.
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

	private void startActivity(final Songs song) {

		appData.setStringData(IDataActions.PACKAGE_NAME, song.getPackageName());

		appData.setStringData(IDataActions.SONG_NAME, song.getSongNameEnglish());

		appData.setStringData(IDataActions.SONG_NAME_JAPAN,
				song.getSongNameJapanese());
		appData.setStringData(IDataActions.CLASS_NAME, "com.xing.joy.karaokes."
				+ song.getSongNameEnglish());

		if (appData.getIntData(IDataActions.TYPE_ID) == 1) {
			CommonLog.e(SelectSong.class.getName(), "PlayMovieDynamicGame");
			CommonUtils.startNewActivity(this, PlayMovieDynamicGame.class, "");
		} else {
			CommonUtils.startNewActivity(this, DynamicSong.class, "");
		}
	}

	@Override
	protected void onIabPurchaseFinish(boolean failure, String sku) {

	}

	@Override
	public String getNameCount() {
		// TODO:log time
		if (appData.getStringData(IDataActions.TYPE_NAME).equalsIgnoreCase(
				"karaoke_clicked")) {
			// return Logtime.PAGE_LISTSONG1;
			return getResources()
					.getString(R.string.namescreen_selectsong_tab1);
		} else if (appData.getStringData(IDataActions.TYPE_NAME)
				.equalsIgnoreCase("douga_clicked")) {
			// return Logtime.PAGE_LISTSONG2;
			return getResources()
					.getString(R.string.namescreen_selectsong_tab2);
		} else if (appData.getStringData(IDataActions.TYPE_NAME)
				.equalsIgnoreCase("uta_clicked")) {
			// return Logtime.PAGE_LISTSONG3;
			return getResources()
					.getString(R.string.namescreen_selectsong_tab3);
		}

		// return Logtime.PAGE_LISTSONG1;
		return getResources().getString(R.string.namescreen_selectsong_tab1);
	}

	// -----------------------------------
	//
	// -----------------------------------
	private void saveCurentPageAndSort() {
		DataStore.getInstance().init(SelectSong.this);
		DataStore.getInstance().save(getNameCount(), currentPage);
		DataStore.getInstance().save(getNameCount() + "currentTypeSort",
				currentTypeSort);
	}

	private void getCurentPageAndSort() {
		DataStore.getInstance().init(SelectSong.this);
		currentPage = DataStore.getInstance().get(getNameCount(), 0);
		currentTypeSort = DataStore.getInstance().get(
				getNameCount() + "currentTypeSort", 2);
	}

}