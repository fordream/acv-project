package jp.co.xing.utaehon;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import jp.co.xing.utaehon03.util.MemoryUtils;
import jp.co.xing.utaehon.R;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.xing.joy.common.PackageXMLHandler;
import com.xing.joy.common.PackagesList;
import com.xing.joy.interfaces.IDataActions;

public class ManageSpace extends Activity {

	public MemoryUtils memory;
	/** Package list. */
	private PackagesList packagesList = null;

	/** List all packages that show in Buy Screen. */
	private ArrayList<String> packageSongs = new ArrayList<String>();

	/** Share Preference for purchase_db. */
	protected SharedPreferences prefs = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		memory = new MemoryUtils(this);
		prefs = getApplication().getSharedPreferences(IDataActions.PURCHASED_DB, MODE_PRIVATE);
		setContentView(R.layout.manage_space);
		parsePackageInfoXML();
		ListView listView = (ListView) findViewById(R.id.mylist);
		for (int i = 0; i < packageSongs.size(); i++) {
			// Log.d("TEST",
			// packageSongs.get(i)+Boolean.parseBoolean(prefs.getString(packageSongs.get(i),
			// false + ";0").split(";")[0]));
			if (!Boolean.parseBoolean(prefs.getString(packageSongs.get(i), false + ";0").split(";")[0])) {
				packageSongs.remove(i);
				i--;
			}
		}

		// First paramenter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the View to which the data is written
		// Forth - the Array of data
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, packageSongs.toArray(new String[packageSongs.size()]));

		// Assign adapter to ListView
		listView.setAdapter(adapter);

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				view.setSelected(true);
				view.setBackgroundColor(Color.RED);
				return true;
			}
		});
	}

	public void parsePackageInfoXML() {
		/** Handling & parser XML . */
		if (new File(memory.getPathFileInternalMemory() + "img_buy/" + "package_info.xml").exists()) {
			try {
				/** Handling XML . */
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();

				/** Create handler to handle XML Tags ( extends DefaultHandler ) */
				PackageXMLHandler packageXMLHandler = new PackageXMLHandler();
				xr.setContentHandler(packageXMLHandler);
				File packageInfo = new File(memory.getPathFileInternalMemory() + "img_buy/" + "package_info.xml");
				InputStream inputStream = new FileInputStream(packageInfo);
				Reader reader = new InputStreamReader(inputStream, "UTF-8");
				xr.parse(new InputSource(reader));

				// get package list after parse XML.
				packagesList = PackageXMLHandler.pList;
				if (packagesList != null) {

					// initial package songs to manager list.
					for (int i = 0; i < packagesList.getName().size(); i++) {
						packageSongs.add(packagesList.getName().get(i));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
