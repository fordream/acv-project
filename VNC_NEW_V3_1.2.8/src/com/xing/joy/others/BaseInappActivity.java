package com.xing.joy.others;

import java.io.File;
import java.util.ArrayList;

import jp.co.xing.utaehon.R;
import jp.co.xing.utaehon03.util.LogUtils;

import org.json.JSONObject;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.android.vending.billing.IInAppBillingService;
import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Inventory;
import com.example.android.trivialdrivesample.util.Purchase;
import com.xing.joy.common.CoreActivity;

public abstract class BaseInappActivity extends CoreActivity {
	private static final String TAG = "BaseInappActivity";
	protected boolean isSportInappV3 = false;
	/** Dialog type 1. */
	protected static final int DIALOG_CANNOT_CONNECT_ID = 1;

	/** Dialog type 2. */
	protected static final int DIALOG_BILLING_NOT_SUPPORTED_ID = 2;

	protected static final int RC_REQUEST = 10001;
	private IabHelper mHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String base64EncodedPublicKey = getString(R.string.base64EncodedPublicKeys);
		try {
			startHelper(base64EncodedPublicKey);
			bindService(new Intent("com.android.vending.billing.InAppBillingService.BIND"), mServiceConn, Context.BIND_AUTO_CREATE);
		} catch (Exception exception) {
			LogUtils.eInapp(exception);
		} catch (Error error) {
			LogUtils.eInapp(error);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (mServiceConn != null) {
			unbindService(mServiceConn);
		}

	}

	private void startHelper(String base64EncodedPublicKey) {
		mHelper = new IabHelper(this, base64EncodedPublicKey);
		//mHelper.enableDebugLogging(true);
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {

				if (!result.isSuccess()) {
					isSportInappV3 = false;
					return;
				} else {
					isSportInappV3 = true;
				}

				try {
					mHelper.queryInventoryAsync(mGotInventoryListener);
				} catch (Exception exception) {
					LogUtils.eInapp(exception);
				} catch (Error error) {
					LogUtils.eInapp(error);
				}
			}
		});
	}

	private IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
			if (result.isFailure()) {
				return;
			}
		}
	};
	private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			String sku = null;
			if (purchase != null) {
				sku = purchase.getSku();
			}

			onIabPurchaseFinish(result.isFailure(), sku);

			if (result.isFailure()) {
				return;
			}
		}
	};

	private IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
		public void onConsumeFinished(Purchase purchase, IabResult result) {
			if (result.isSuccess()) {

			} else {

			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
				super.onActivityResult(requestCode, resultCode, data);
			}
		} catch (Exception exception) {
			LogUtils.eInapp(exception);
		} catch (Error error) {
			LogUtils.eInapp(error);
		}
	}

	protected void launchPurchaseFlow(String sku) {
		try {
			mHelper.launchPurchaseFlow(this, sku, RC_REQUEST, mPurchaseFinishedListener);
		} catch (Exception exception) {
			LogUtils.eInapp(exception);
		} catch (Error error) {
			LogUtils.eInapp(error);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_CANNOT_CONNECT_ID:
			return createDialog(R.string.cannot_connect_title, R.string.cannot_connect_message);
		case DIALOG_BILLING_NOT_SUPPORTED_ID:
			return createDialog(R.string.billing_not_supported_title, R.string.billing_not_supported_message);
		default:
			return super.onCreateDialog(id);
		}
	}

	protected boolean isPurchased(String sku) {
		return queryingForPurchasedItems(sku) || isPurcharseOld(sku);
	}

	protected boolean isPurchasedOrFreeSong(String sku) {
		return isPurchased(sku) || checkFreeSongDownload(sku);
	}

	public boolean checkFreeSongDownload(String packageName) {
		if (packageName.contains("free.song.relation")) {
			return true;
		}
		return false;
	}

	protected abstract void onIabPurchaseFinish(boolean failure, String sku);

	// ---------------------------------------------------------------------
	// Service
	// ---------------------------------------------------------------------
	private IInAppBillingService mService;

	private ServiceConnection mServiceConn = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = IInAppBillingService.Stub.asInterface(service);
		}
	};

	protected boolean queryingForItemsAvailableForPurchase(String sku1) {
		ArrayList skuList = new ArrayList();
		skuList.add(sku1);
		Bundle querySkus = new Bundle();
		querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
		try {
			Bundle skuDetails = mService.getSkuDetails(3, getPackageName(), "inapp", querySkus);

			int response = skuDetails.getInt("RESPONSE_CODE");
			if (response == 0) {
				ArrayList responseList = skuDetails.getStringArrayList("DETAILS_LIST");

				for (Object thisResponse : responseList) {
					JSONObject object = new JSONObject(thisResponse.toString());
					String sku = object.getString("productId");
					String price = object.getString("price");

					if (sku.equals(sku1)) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			LogUtils.eInapp(e);
		}

		return false;
	}

	protected boolean queryingForPurchasedItems(String sku1) {
		Bundle ownedItems;
		try {
			ownedItems = mService.getPurchases(3, getPackageName(), "inapp", null);

			int response = ownedItems.getInt("RESPONSE_CODE");
			if (response == 0) {
				ArrayList ownedSkus = ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
				ArrayList purchaseDataList = ownedItems.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
				ArrayList signatureList = ownedItems.getStringArrayList("INAPP_DATA_SIGNATURE");
				String continuationToken = ownedItems.getString("INAPP_CONTINUATION_TOKEN");

				for (int i = 0; i < purchaseDataList.size(); ++i) {
					// String purchaseData = purchaseDataList.get(i);
					// String signature = signatureList.get(i);
					String sku = ownedSkus.get(i).toString();
					if (sku.equals(sku1)) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			LogUtils.eInapp(e);
		}
		return false;
	}

	public boolean isBillingSupported() {
		try {
			return mService.isBillingSupported(3, getPackageName(), "inapp") == 0;
		} catch (Exception e) {
			LogUtils.eInapp(e);
			return false;
		}
	}

	protected boolean isPurchasedButNotDownloaded(String uri, String namePackage) {
		if (isPurchased(namePackage) && !new File(uri + ".ini").exists()) {
			return true;
		}

		return false;
	}
}