package jp.co.xing.utaehon03.util;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class CommonXMLfunctions {

	public static String getXML(String url) {
		String line = null;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			line = EntityUtils.toString(httpEntity);
		} catch (Exception e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
			line = null;
		}
		return line;
	}

	public final static Document XMLfromString(String xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);
		} catch (Exception e) {
			return null;
		}

		return doc;
	}

	public final static String getElementValue(Node elem) {
		Node kid;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (kid = elem.getFirstChild(); kid != null; kid = kid
						.getNextSibling()) {
					if (kid.getNodeType() == Node.TEXT_NODE) {
						return kid.getNodeValue();
					}
				}
			}
		}
		return "";
	}

	public static int numResults(Document doc) {
		Node results = doc.getDocumentElement();
		int res = -1;
		try {
			res = Integer.valueOf(results.getAttributes().getNamedItem("count")
					.getNodeValue());
		} catch (Exception e) {
			res = -1;
		}
		return res;
	}

	public static String getValue(Element item, String str) {
		try {
			NodeList n = item.getElementsByTagName(str);
			return CommonXMLfunctions.getElementValue(n.item(0));
		} catch (Exception e) {
			return "";
		}
	}

	public static String[] login(String userName, String password) {
		String _return[] = new String[3];
		String strXml = CommonXMLfunctions.getXML("url".replace("{0}", userName)
				.replace("{1}", password));
		if (strXml != null) {
			Document document = CommonXMLfunctions.XMLfromString(strXml);
			NodeList nodes = document.getElementsByTagName("response");
			Element e = (Element) nodes.item(0);
			_return[0] = CommonXMLfunctions.getValue(e, "success");
			if ("true".equals(_return[0])) {
				_return[1] = CommonXMLfunctions.getValue(e, "token");
				_return[2] = CommonXMLfunctions.getValue(e, "Role_ID");
			}
		}
		return _return;
	}



//	public static SearchItem loadRecord(String id, boolean isContact) {
//		String line = null;
//		try {
//			DefaultHttpClient httpClient = new DefaultHttpClient();
//			HttpPost httpPost = new HttpPost(isContact ? URL_RECORD_CONTACT
//					: URL_RECORD_COMPANY);
//
//			// ResponseHandler<String> res = new BasicResponseHandler();
//			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
//			nameValuePairs.add(new BasicNameValuePair("format", "xml"));
//			nameValuePairs.add(new BasicNameValuePair("token", Common.token));
//			nameValuePairs.add(new BasicNameValuePair("id", id));
//
//			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//			HttpResponse response = httpClient.execute(httpPost);
//			line = EntityUtils.toString(response.getEntity());
//			Document document = XMLfunctions.XMLfromString(line);
//			NodeList nodes = document.getElementsByTagName("response");
//			Element e = (Element) nodes.item(0);
//
//			String Name__Last__First_ = XMLfunctions.getValue(e,
//					isContact ? "Name__Last__First_" : "Name");
//			String phone = "";
//			if (!isContact)
//				phone = XMLfunctions.getValue(e, "Phone");
//			String Email1 = XMLfunctions.getValue(e,
//					isContact ? "Personal_Email" : "Email");
//
//			String Home_Fax = XMLfunctions.getValue(e, isContact ? "Home_Fax"
//					: "Fax1");
//			String Address1 = XMLfunctions.getValue(e, "Address1");
//			String Address2 = XMLfunctions.getValue(e, "Address2");
//			String City = XMLfunctions.getValue(e, "City");
//			String State = XMLfunctions.getValue(e, "State");
//			String Zip = XMLfunctions.getValue(e, "Zip");
//			String Country = XMLfunctions.getValue(e, "Country");
//			String Profile = XMLfunctions.getValue(e, "Profile");
//
//			String success = XMLfunctions.getValue(e, "success");
//			String error = XMLfunctions.getValue(e, "error");
//			SearchItem item = new SearchItem();
//			item.set(1, Name__Last__First_);
//			item.set(2, phone);
//			item.set(3, phone);
//			item.set(4, Email1);
//			item.set(5, Home_Fax);
//			item.set(6, Address1);
//			item.set(7, Address2);
//			item.set(8, City);
//			item.set(9, State);
//			item.set(10, Zip);
//			item.set(11, Profile);
//			item.set(12, Country);
//			item.set(13, success);
//			item.set(14, error);
//			return item;
//		} catch (Exception e) {
//			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
//			line = null;
//		}
//		return null;
//	}


}
