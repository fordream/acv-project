package com.xing.joy.processdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

import jp.co.xing.utaehon.R;
import jp.co.xing.utaehon03.util.LogUtils;
import jp.co.xing.utaehon03.util.MemoryUtils;
import jp.co.xing.utaehon03.util.ZDialogUtils;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.xing.joy.common.DownloadProgessBar;
import com.xing.joy.download.DigestUtils;
import com.xing.joy.others.Top;

@SuppressLint("HandlerLeak")
public class Download extends AsyncTask<FilesModel, Integer, Boolean> {

	/** Share preferences store data. */
	protected SharedPreferences shareHistoryDownload;

	/** Editor access share data. */
	private Editor editHistoryDownload;

	protected long total = 0, fileSize = 0;

	private Context context;

	/** BUFFER read file. */
	private static final int BUFFER = 2048 * 2;
	protected static final int MESSAGEID_DOWNLOAD_NORMAL = -1;
	protected static final int MESSAGEID_MEMORYFREE = 0;

	protected static final int MESSAGEID_MEMORYPOO = 1;

	protected static final int MESSAGE_DOWNLOAD_FAIL = 2;

	protected static final int MESSAGE_DOWNLOAD_MESSAGE = 3;

	protected static final int MESSAGE_DOWNLOAD_SERVER_FAIL = 4;

	private static final int MESSAGE_DOWNLOAD_MD5 = 5;

	private Handler messageHandler;

	private boolean checkSD = false;

	private static AlertDialog alertDialog;

	private String message = "";

	private Boolean allowDownload = true;

	public static boolean isCancelled = false;

	public Download(final Context context, boolean checkSD) {
		super();
		this.context = context;
		this.checkSD = checkSD;
		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(context
						.getString(R.string.url_user), context.getString(
						R.string.url_password).toCharArray());
			}
		});
		shareHistoryDownload = context.getSharedPreferences("history_download",
				Context.MODE_PRIVATE);
		editHistoryDownload = shareHistoryDownload.edit();
	}

	public void popupDownload(String message) throws Exception {
		alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setMessage(message);
		alertDialog.setButton(context.getString(R.string.btn_yes),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						alertDialog.dismiss();
						return;
					}
				});
		alertDialog.show();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Download.isCancelled = false;
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					long tmp = 0;
					long time = 0;
					int count = 0;
					while (true) {

						Thread.sleep(1000);
						if (total >= DownloadProgessBar.MAX
								|| DownloadProgessBar.MAX == 0
								|| Download.isCancelled) {
							break;
						}

						time++;
						if (time > 5 && (total - tmp) / 1024 < 1) {
							count++;
							if (count > 30) {
								allowDownload = false;
								break;
							}
						} else {
							count = 0;
						}

						tmp = total;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		messageHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (Download.isCancelled) {
					return;
				}

				switch (msg.what) {
				case MESSAGEID_MEMORYFREE:
					// try {
					// popupDownload(context
					// .getString(R.string.dlp_sdc_not_avai));
					// } catch (Exception e) {
					// e.printStackTrace();
					// }
					if (context instanceof Top) {
						new ZDialogUtils(context)
								.onCreateDialog(ZDialogUtils.DIALOG_ERROR_ANDROID_001);
					} else {
						// luu trong SD card
						new ZDialogUtils(context)
								.onCreateDialog(ZDialogUtils.DIALOG_ERROR_ANDROID_002);
						// luu tai bo nho trong
						// new ZDialogUtils(context)
						// .onCreateDialog(ZDialogUtils.DIALOG_ERROR_ANDROID_003);
					}
					break;
				case MESSAGEID_MEMORYPOO:
					if (context instanceof Top) {
						new ZDialogUtils(context)
								.onCreateDialog(ZDialogUtils.DIALOG_ERROR_ANDROID_001);
					} else {
						// luu trong SD card
						new ZDialogUtils(context)
								.onCreateDialog(ZDialogUtils.DIALOG_ERROR_ANDROID_002);

						// luu tai bo nho trong
						// new ZDialogUtils(context)
						// .onCreateDialog(ZDialogUtils.DIALOG_ERROR_ANDROID_003);
					}
					// try {
					// popupDownload(context
					// .getString(R.string.dlp_sdc_not_enuf));
					// } catch (Exception e) {
					// e.printStackTrace();
					// }
					break;
				case MESSAGE_DOWNLOAD_FAIL:
					new ZDialogUtils(context)
							.onCreateDialog(ZDialogUtils.DIALOG_ERROR_002);
					// try {
					// callBackDownloadFail();
					// popupDownload(context
					// .getString(R.string.download_failed));
					// } catch (Exception e) {
					// e.printStackTrace();
					// }
					break;
				case MESSAGE_DOWNLOAD_MESSAGE:
					try {
						popupDownload(message);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case MESSAGE_DOWNLOAD_SERVER_FAIL:
					// try {
					// popupDownload(context.getString(R.string.server_failed));
					// } catch (Exception e) {
					// e.printStackTrace();
					// }
					// callbackServerFail();
					new ZDialogUtils(context)
							.onCreateDialog(ZDialogUtils.DIALOG_ERROR_005);
					break;
				case MESSAGE_DOWNLOAD_MD5:
					new ZDialogUtils(context)
							.onCreateDialog(ZDialogUtils.DIALOG_ERROR_004);
					break;
				}
			}

		};
	}

	@Override
	protected void onCancelled() {
		super.onPreExecute();
	}

	@SuppressLint("HandlerLeak")
	@Override
	protected Boolean doInBackground(FilesModel... params) {
		LogUtils.eCommon("SIZE  + " + DownloadProgessBar.MAX + "");

		// create directory
		Download.isCancelled = false;
		File mDirectory = new File(params[0].getPathDir());
		if (!mDirectory.exists()) {
			mDirectory.mkdirs();
		}
		int messageId = MESSAGEID_DOWNLOAD_NORMAL;

		for (FilesModel filesModel : params) {
			// create file
			File mFile = new File(filesModel.getPathDir(),
					filesModel.getFileName());

			URL tmp = null;
			System.setProperty("http.keepAlive", "false");
			HttpURLConnection urlConnection = null;
			FileOutputStream fileOutput = null;
			long fileSize = 0;
			String getDateModifier = null;
			try {

				tmp = new URL(filesModel.getUrl());
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
					messageId = MESSAGE_DOWNLOAD_MESSAGE;
					message = in.readLine();
					throw new Exception();
				}

				fileSize = urlConnection.getContentLength();
				if (checkSD) {
					MemoryUtils sdc = new MemoryUtils(context);
					double SDCFreeMemory = sdc.checkSDFreeMB();

					if (SDCFreeMemory == 0) {
						messageId = MESSAGEID_MEMORYFREE;
						throw new SDCardFreeException();
					} else if (SDCFreeMemory < (fileSize * 2 / (1024 * 1024) + 10)) {
						messageId = MESSAGEID_MEMORYPOO;
						throw new SDCardFooException();
					}
				}

				if (params.length == 1) {
					DownloadProgessBar.MAX = fileSize;
					DownloadProgessBar.calculateMaxDownload();
				}
				fileSize = Long.parseLong(urlConnection
						.getHeaderField("content-length"));
				getDateModifier = urlConnection.getHeaderField("last-modified");

				// set the path where we want to save the file
				// this will be used to write the DOWNLOAD data into
				// the file we created.
				fileOutput = new FileOutputStream(mFile);

				// this will be used in reading the data from the INTERNET.
				InputStream inputStream = urlConnection.getInputStream();

				// create a buffer...
				byte[] buffer = new byte[BUFFER];
				int bufferLength = 0; // used to store a temporary size
										// of the buffer

				// now, read through the input buffer and write.
				while (!isCancelled()
						&& (bufferLength = inputStream.read(buffer)) > 0) {
					if (!allowDownload) {
						messageId = MESSAGE_DOWNLOAD_SERVER_FAIL;
						throw new Exception();
					}
					total += bufferLength;
					// Log.d("DOWNLOAD", "............");
					publishProgress(bufferLength);
					// add the data in the buffer to the file in the
					// file output stream (the file on the SDCard)
					fileOutput.write(buffer, 0, bufferLength);
				}
				
				
				InputStream input = new FileInputStream(mFile);
				String md5Font = DigestUtils.md5Hex(input);
				Log.e("AAAAAAAA", filesModel.getChecksum() + "   " + md5Font);
				// check checksum
				if (filesModel.getChecksum() != null&& !filesModel.getChecksum().equalsIgnoreCase("")) {
					if (!md5Font.equalsIgnoreCase(filesModel.getChecksum()
							.trim())) {
						// checksum error!
						messageId = MESSAGE_DOWNLOAD_MD5;
						throw new MD5Exception();
					}
				}
				fileOutput.close();
			} catch (MD5Exception e) {
				messageId = MESSAGE_DOWNLOAD_MD5;
				e.printStackTrace();
			} catch (SDCardFreeException e) {
				messageId = MESSAGEID_MEMORYFREE;
				e.printStackTrace();
			} catch (SDCardFooException e) {
				messageId = MESSAGEID_MEMORYPOO;
				e.printStackTrace();
			} catch (Exception e) {
				if (messageId == MESSAGEID_DOWNLOAD_NORMAL) {
					messageId = MESSAGE_DOWNLOAD_FAIL;
				}
				e.printStackTrace();
			} finally {
				// close the output stream when done.
				try {
					if (messageId != MESSAGEID_DOWNLOAD_NORMAL) {
						// Log.d("TEST", "Message " + messageId);
						messageHandler.sendEmptyMessage(messageId);
					}

					urlConnection.disconnect();
					if (mFile.length() < fileSize) {
						mFile.delete();
					} else {
						// Log.e("ABC", getDateModifier);
						editHistoryDownload.putString(filesModel.getFileName(),
								getDateModifier);

						editHistoryDownload.commit();
					}

					if (messageId != -1) {
						// Log.d("TEST", "Message " + messageId);
						return false;
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					return false;
				}
			}
		}
		return !isCancelled();
	}

	private class MD5Exception extends Exception {
		public MD5Exception() {
			super("MD5 Exception");
		}
	}

	private class SDCardFreeException extends Exception {
		public SDCardFreeException() {
			super("SDCard Exception");
		}
	}

	private class SDCardFooException extends Exception {
		public SDCardFooException() {
			super("SDCardFoo Exception");
		}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPreExecute();
	}
}
