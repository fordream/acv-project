/**
 * 
 */
package com.xing.joy.common;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ict.library.common.CommonLog;

/**
 * @author kiemhm
 * 
 */
public class PackageXMLHandler extends DefaultHandler {

	Boolean currentElement = false;
	String currentValue = null;
	public static PackagesList pList = null;

	public static PackagesList getpList() {
		return pList;
	}

	public static void setpList(PackagesList pList) {
		PackageXMLHandler.pList = pList;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = true;

		if (localName.equals("packages")) {
			/** Start */
			pList = new PackagesList();
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		currentElement = false;

		/** set value */
		if (localName.equalsIgnoreCase("name")) {
			pList.setName(currentValue);
		} else if (localName.equalsIgnoreCase("icon")) {
			pList.setIcon(currentValue);
		} else if (localName.equalsIgnoreCase("image_intro")) {
			pList.setImageIntro(currentValue);
		} else if (localName.equalsIgnoreCase("song_number")) {
			pList.setSongNumber(Integer.parseInt(currentValue));
		} else if (localName.equalsIgnoreCase("id")) {
			pList.setId(Integer.parseInt(currentValue));
		} else if (localName.equalsIgnoreCase("vol")) {
			pList.setVol(Integer.parseInt(currentValue));
		} else if (localName.equalsIgnoreCase("checksum_data1")) {
			pList.setChecksum(currentValue);
		} else if (localName.equalsIgnoreCase("package_relation")) {
			pList.setPackageRelation(currentValue);
		}

	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (currentElement) {
			currentValue = new String(ch, start, length);
			CommonLog.e("ABC", currentValue + "\n" + new String (ch));
			currentElement = false;
		}
	}
}
