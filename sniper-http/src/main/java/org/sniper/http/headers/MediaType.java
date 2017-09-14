/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Create Date : 2017-8-28
 */

package org.sniper.http.headers;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Map;

import org.sniper.commons.enums.http.Media;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.NumberUtils;
import org.sniper.commons.util.StringUtils;

/**
 * Media类型
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MediaType extends MimeType implements Serializable {
	
	private static final long serialVersionUID = -8121497171604758746L;
		
	/** all */
	public static final MediaType ALL;

	/** application/atom+xml */
	public final static MediaType APPLICATION_ATOM_XML;
	
	/** application/font-woff */
	public final static MediaType APPLICATION_FONT_WOFF;
	
	/** application/x-www-form-urlencoded */
	public final static MediaType APPLICATION_FORM_URLENCODED;
	
	/** application/java-archive */
	public final static MediaType APPLICATION_JAVA_ARCHIVE;

	/** application/javascript */
	public final static MediaType APPLICATION_JAVASCRIPT;
	
	/** application/json */
	public final static MediaType APPLICATION_JSON;

	/** application/json;charset=UTF-8 */
	public final static MediaType APPLICATION_JSON_UTF8;
	
	/** application/mac-binhex40 */
	public final static MediaType APPLICATION_MAC_BINHEX40; 
	
	/** application/msword */
	public final static MediaType APPLICATION_MSWORD;

	/** application/octet-stream */
	public final static MediaType APPLICATION_OCTET_STREAM;
	
	/** application/pdf */
	public final static MediaType APPLICATION_PDF;
	
	/** application/postscript */
	public final static MediaType APPLICATION_POSTSCRIPT;
	
	/** application/rss+xml */
	public final static MediaType APPLICATION_RSS_XML;

	/** application/rtf */
	public final static MediaType APPLICATION_RTF;
	
	/** application/vnd.apple.mpegurl */
	public final static MediaType APPLICATION_VND_APPLE_MPEGURL;
	
	/** application/vnd.ms-excel */
	public final static MediaType APPLICATION_VND_MS_EXCEL;
	
	/** application/vnd.ms-fontobject */
	public final static MediaType APPLICATION_VND_MS_FONTOBJECT;
	
	/** application/vnd.ms-powerpoint */
	public final static MediaType APPLICATION_VND_MS_POWERPOINT;
	
	/** application/vnd.wap.wmlc */
	public final static MediaType APPLICATION_VND_WAP_WMLC;
	
	/** application/vnd.google-earth.kml+xml */
	public final static MediaType APPLICATION_VND_GOOGLE_EARTH_KML_XML;
	
	/** application/vnd.google-earth.kmz */
	public final static MediaType APPLICATION_VND_GOOGLE_EARTH_KMZ;
	
	/** application/vnd.openxmlformats-officedocument.wordprocessingml.document */
	public final static MediaType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_DOCUMENT;
	
	/** application/vnd.openxmlformats-officedocument.spreadsheetml.sheet */
	public final static MediaType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_SHEET;
	
	/** application/vnd.openxmlformats-officedocument.presentationml.presentation */
	public final static MediaType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_PRESENTATION;
	
	/** application/x-7z-compressed */
	public final static MediaType APPLICATION_X_7Z_COMPRESSED;
	
	/** application/x-cocoa */
	public final static MediaType APPLICATION_X_COCOA;
	
	/** application/x-java-archive-diff */
	public final static MediaType APPLICATION_X_JAVA_ARCHIVE_DIFF;
	
	/** application/x-java-jnlp-file */
	public final static MediaType APPLICATION_X_JAVA_JNLP_FILE;
	
	/** application/x-makeself */
	public final static MediaType APPLICATION_X_MAKESELF;
	
	/** application/x-perl */
	public final static MediaType APPLICATION_X_PERL;
	
	/** application/x-pilot */
	public final static MediaType APPLICATION_X_PILOT;
	
	/** application/x-rar-compressed */
	public final static MediaType APPLICATION_X_RAR_COMPRESSED;
	
	/** application/x-redhat-package-manager */
	public final static MediaType APPLICATION_X_REDHAT_PACKAGE_MANAGER;
	
	/** application/x-sea */
	public final static MediaType APPLICATION_X_SEA;
	
	/** application/x-shockwave-flash */
	public final static MediaType APPLICATION_X_SHOCKWAVE_FLASH;
	
	/** application/x-stuffit */
	public final static MediaType APPLICATION_X_STUFFIT;
	
	/** application/x-tcl */
	public final static MediaType APPLICATION_X_TCL;
	
	/** application/x-x509-ca-cert */
	public final static MediaType APPLICATION_X_X509_CA_CERT;
	
	/** pplication/x-xpinstall */
	public final static MediaType PPLICATION_X_XPINSTALL;
	
	/** application/xhtml+xml */
	public final static MediaType APPLICATION_XHTML_XML;

	/** application/xml */
	public final static MediaType APPLICATION_XML;
	
	/** application/xspf+xml */
	public final static MediaType APPLICATION_XSPF_XML;
	
	/** application/zip */
	public final static MediaType APPLICATION_ZIP;
	
	/** audio/midi */
	public final static MediaType AUDIO_MIDI;
	
	/** audio/mpeg */
	public final static MediaType AUDIO_MPEG;
	
	/** audio/ogg */
	public final static MediaType AUDIO_OGG;
	
	/** audio/x-m4a */ 
	public final static MediaType AUDIO_X_M4A;
	
	/** audio/x-realaudio */
	public final static MediaType AUDIO_X_REALAUDIO;

	/** image/gif */
	public final static MediaType IMAGE_GIF;

	/** image/jpeg */
	public final static MediaType IMAGE_JPEG;

	/** image/png */
	public final static MediaType IMAGE_PNG;
	
	/** image/tiff */
	public final static MediaType IMAGE_TIFF;
	
	/** image/vnd.wap.wbmp */
	public final static MediaType IMAGE_VND_WAP_WBMP;
	
	/** image/x-icon */
	public final static MediaType IMAGE_X_ICON;
	
	/** image/x-jng */
	public final static MediaType IMAGE_X_JNG;
	
	/** image/x-ms-bmp */
	public final static MediaType IMAGE_X_MS_BMP;
	
	/** image/svg+xml */
	public final static MediaType IMAGE_SVG_XML;
	
	/** image/webp */
	public final static MediaType IMAGE_WEBP;

	/** multipart/form-data */
	public final static MediaType MULTIPART_FORM_DATA;
	
	/** text/css */
	public final static MediaType TEXT_CSS;

	/** text/html */
	public final static MediaType TEXT_HTML;

	/** text/markdown */
	public final static MediaType TEXT_MARKDOWN;
	
	/** text/mathml */
	public final static MediaType TEXT_MATHML;

	/** text/plain */
	public final static MediaType TEXT_PLAIN;
	
	/** text/vnd.sun.j2me.app-descriptor */
	public final static MediaType TEXT_VND_SUN_J2ME_APP_DESCRIPTOR;
	
	/** text/vnd.wap.wml */
	public final static MediaType TEXT_VND_WAP_WML;
	
	/** text/x-component */
	public final static MediaType TEXT_X_COMPONENT;

	/** text/xml */
	public final static MediaType TEXT_XML;
	
	/** video/3gpp */
	public final static MediaType VIDEO_3GPP;
	
	/** video/mp2t */
	public final static MediaType VIDEO_MP2T;
	
	/** video/mp4 */
	public final static MediaType VIDEO_MP4;
	
	/** video/mpeg */
	public final static MediaType VIDEO_MPEG;
	
	/** video/quicktime */
	public final static MediaType VIDEO_QUICKTIME;
	
	/** video/webm */
	public final static MediaType VIDEO_WEBM;
	
	/** video/x-flv */
	public final static MediaType VIDEO_X_FLV;
	
	/** video/x-m4v */
	public final static MediaType VIDEO_X_M4V;
	
	/** video/x-mng */
	public final static MediaType VIDEO_X_MNG;
	
	/** video/x-ms-asf */
	public final static MediaType VIDEO_X_MS_ASF;
	
	/** video/x-ms-wmv */
	public final static MediaType VIDEO_X_MS_WMV;
	
	/** video/x-msvideo */
	public final static MediaType VIDEO_X_MSVIDEO;
	
	static {
		ALL = parse(Media.ALL.getType());
		APPLICATION_ATOM_XML = parse(Media.APPLICATION_ATOM_XML.getType());
		APPLICATION_FONT_WOFF = parse(Media.APPLICATION_FONT_WOFF.getType());
		APPLICATION_FORM_URLENCODED = parse(Media.APPLICATION_FORM_URLENCODED.getType());
		APPLICATION_JAVA_ARCHIVE = parse(Media.APPLICATION_JAVA_ARCHIVE.getType());
		APPLICATION_JAVASCRIPT = parse(Media.APPLICATION_JAVASCRIPT.getType());
		APPLICATION_JSON = parse(Media.APPLICATION_JSON.getType());
		APPLICATION_JSON_UTF8 = parse(Media.APPLICATION_JSON_UTF8.getType());
		APPLICATION_MAC_BINHEX40 = parse(Media.APPLICATION_MAC_BINHEX40.getType());
		APPLICATION_MSWORD = parse(Media.APPLICATION_MSWORD.getType());
		APPLICATION_OCTET_STREAM = parse(Media.APPLICATION_OCTET_STREAM.getType());
		APPLICATION_PDF = parse(Media.APPLICATION_PDF.getType());
		APPLICATION_POSTSCRIPT = parse(Media.APPLICATION_POSTSCRIPT.getType());
		APPLICATION_RSS_XML = parse(Media.APPLICATION_RSS_XML.getType());
		APPLICATION_RTF = parse(Media.APPLICATION_RTF.getType());
		APPLICATION_VND_APPLE_MPEGURL = parse(Media.APPLICATION_VND_APPLE_MPEGURL.getType());
		APPLICATION_VND_MS_EXCEL = parse(Media.APPLICATION_VND_MS_EXCEL.getType());
		APPLICATION_VND_MS_FONTOBJECT = parse(Media.APPLICATION_VND_MS_FONTOBJECT.getType());
		APPLICATION_VND_MS_POWERPOINT = parse(Media.APPLICATION_VND_MS_POWERPOINT.getType());
		APPLICATION_VND_WAP_WMLC = parse(Media.APPLICATION_VND_WAP_WMLC.getType());
		APPLICATION_VND_GOOGLE_EARTH_KML_XML = parse(Media.APPLICATION_VND_GOOGLE_EARTH_KML_XML.getType());
		APPLICATION_VND_GOOGLE_EARTH_KMZ = parse(Media.APPLICATION_VND_GOOGLE_EARTH_KMZ.getType());
		APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_DOCUMENT = parse(Media.APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_DOCUMENT.getType());
		APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_SHEET = parse(Media.APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_SHEET.getType());
		APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_PRESENTATION = parse(Media.APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_PRESENTATION.getType());
		APPLICATION_X_7Z_COMPRESSED = parse(Media.APPLICATION_X_7Z_COMPRESSED.getType());
		APPLICATION_X_COCOA = parse(Media.APPLICATION_X_COCOA.getType());
		APPLICATION_X_JAVA_ARCHIVE_DIFF = parse(Media.APPLICATION_X_JAVA_ARCHIVE_DIFF.getType());
		APPLICATION_X_JAVA_JNLP_FILE = parse(Media.APPLICATION_X_JAVA_JNLP_FILE.getType());
		APPLICATION_X_MAKESELF = parse(Media.APPLICATION_X_MAKESELF.getType());
		APPLICATION_X_PERL = parse(Media.APPLICATION_X_PERL.getType());
		APPLICATION_X_PILOT = parse(Media.APPLICATION_X_PILOT.getType());
		APPLICATION_X_RAR_COMPRESSED = parse(Media.APPLICATION_X_RAR_COMPRESSED.getType());
		APPLICATION_X_REDHAT_PACKAGE_MANAGER = parse(Media.APPLICATION_X_REDHAT_PACKAGE_MANAGER.getType());
		APPLICATION_X_SEA = parse(Media.APPLICATION_X_SEA.getType());
		APPLICATION_X_SHOCKWAVE_FLASH = parse(Media.APPLICATION_X_SHOCKWAVE_FLASH.getType());	
		APPLICATION_X_STUFFIT = parse(Media.APPLICATION_X_STUFFIT.getType());	
		APPLICATION_X_TCL = parse(Media.APPLICATION_X_TCL.getType());
		APPLICATION_X_X509_CA_CERT = parse(Media.APPLICATION_X_X509_CA_CERT.getType());
		PPLICATION_X_XPINSTALL = parse(Media.PPLICATION_X_XPINSTALL.getType());	
		APPLICATION_XHTML_XML = parse(Media.APPLICATION_XHTML_XML.getType());		
		APPLICATION_XML = parse(Media.APPLICATION_XML.getType());			
		APPLICATION_XSPF_XML = parse(Media.APPLICATION_XSPF_XML.getType());		
		APPLICATION_ZIP = parse(Media.APPLICATION_ZIP.getType());
		AUDIO_MIDI = parse(Media.AUDIO_MIDI.getType());
		AUDIO_MPEG = parse(Media.AUDIO_MPEG.getType());
		AUDIO_OGG = parse(Media.AUDIO_OGG.getType());
		AUDIO_X_M4A = parse(Media.AUDIO_X_M4A.getType());
		AUDIO_X_REALAUDIO = parse(Media.AUDIO_X_REALAUDIO.getType());
		IMAGE_GIF = parse(Media.IMAGE_GIF.getType());
		IMAGE_JPEG = parse(Media.IMAGE_JPEG.getType());
		IMAGE_PNG = parse(Media.IMAGE_PNG.getType());
		IMAGE_TIFF = parse(Media.IMAGE_TIFF.getType());	
		IMAGE_VND_WAP_WBMP = parse(Media.IMAGE_VND_WAP_WBMP.getType());	
		IMAGE_X_ICON = parse(Media.IMAGE_X_ICON.getType());	
		IMAGE_X_JNG = parse(Media.IMAGE_X_JNG.getType());	
		IMAGE_X_MS_BMP = parse(Media.IMAGE_X_MS_BMP.getType());	
		IMAGE_SVG_XML = parse(Media.IMAGE_SVG_XML.getType());	
		IMAGE_WEBP = parse(Media.IMAGE_WEBP.getType());	
		MULTIPART_FORM_DATA = parse(Media.MULTIPART_FORM_DATA.getType());
		TEXT_CSS = parse(Media.TEXT_CSS.getType());
		TEXT_HTML = parse(Media.TEXT_HTML.getType());
		TEXT_MARKDOWN = parse(Media.TEXT_MARKDOWN.getType());
		TEXT_MATHML = parse(Media.TEXT_MATHML.getType());
		TEXT_PLAIN = parse(Media.TEXT_PLAIN.getType());
		TEXT_VND_SUN_J2ME_APP_DESCRIPTOR = parse(Media.TEXT_VND_SUN_J2ME_APP_DESCRIPTOR.getType());
		TEXT_VND_WAP_WML = parse(Media.TEXT_VND_WAP_WML.getType());
		TEXT_X_COMPONENT = parse(Media.TEXT_X_COMPONENT.getType());
		TEXT_XML = parse(Media.TEXT_XML.getType());
		VIDEO_3GPP = parse(Media.VIDEO_3GPP.getType());
		VIDEO_MP2T = parse(Media.VIDEO_MP2T.getType());
		VIDEO_MP4 = parse(Media.VIDEO_MP4.getType());
		VIDEO_MPEG = parse(Media.VIDEO_MPEG.getType());
		VIDEO_QUICKTIME = parse(Media.VIDEO_QUICKTIME.getType());
		VIDEO_WEBM = parse(Media.VIDEO_WEBM.getType());
		VIDEO_X_FLV = parse(Media.VIDEO_X_FLV.getType());
		VIDEO_X_M4V = parse(Media.VIDEO_X_M4V.getType());
		VIDEO_X_MNG = parse(Media.VIDEO_X_MNG.getType());
		VIDEO_X_MS_ASF = parse(Media.VIDEO_X_MS_ASF.getType());
		VIDEO_X_MS_WMV = parse(Media.VIDEO_X_MS_ASF.getType());
		VIDEO_X_MSVIDEO = parse(Media.VIDEO_X_MSVIDEO.getType());
	}
	
	public MediaType() {
		super();
	}
	
	public MediaType(String type) {
		super(type);
	}
	
	public MediaType(String type, Charset charset) {
		super(type, charset);
	}
	
	public MediaType(String type, double qualityValue) {
		this(type, (Charset) null, qualityValue);
	}
	
	public MediaType(String type, Charset charset, double qualityValue) {
		this(type, null, charset, qualityValue);
	}
	
	public MediaType(String type, String subtype) {
		super(type, subtype);
	}
	
	public MediaType(String type, String subtype, Charset charset) {
		super(type, subtype, charset);
	}
	
	public MediaType(String type, String subtype, double qualityValue) {
		this(type, subtype, null, qualityValue);
	}
	
	public MediaType(String type, String subtype, Charset charset, double qualityValue) {
		super(type, subtype, buildParameters(charset, qualityValue));
	}
		
	public MediaType(String type, Map<String, Object> parameters) {
		super(type, parameters);
	}
	
	public MediaType(MediaType mediaType, Charset charset) {
		super(mediaType, charset);
	}
	
	public MediaType(MediaType mediaType, double qualityValue) {
		this(mediaType, null, qualityValue);
	}
	
	public MediaType(MediaType mediaType, Charset charset, double qualityValue) {
		super(mediaType, buildParameters(mediaType, charset, qualityValue));
	}
	
	public MediaType(MediaType mediaType, Map<String, Object> parameters) {
		super(mediaType, parameters);
	}
		
	public MediaType(String type, String subtype, Map<String, Object> parameters) {
		super(type, subtype, parameters);
	}
		
	/**
	 * 构建字符集编码和质量价值参数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param charset
	 * @param qualityValue
	 * @return
	 */
	private static Map<String, Object> buildParameters(Charset charset, double qualityValue) {
		return buildParameters(null, charset, qualityValue);
	}
	
	/**
	 * 构建字符集编码和质量价值参数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mediaType
	 * @param charset
	 * @param qualityValue
	 * @return
	 */
	private static Map<String, Object> buildParameters(MediaType mediaType, Charset charset, double qualityValue) {
		
		Map<String, Object> parameters;
		if (mediaType != null)
			parameters = MapUtils.newLinkedHashMap(mediaType.getParameterItems());
		else
			parameters = MapUtils.newLinkedHashMap();
		
		if (charset != null)
			parameters.put(PARAM_CHARSET, charset.name());
		
		// 这里不检查质量价值的合法性，而是延迟到调用checkParameter方法时进行
		parameters.put(QualityFactor.PARAM_QUALITY_FACTOR, qualityValue);
		
		return parameters;
	}
		
	/**
	 * 获取质量价值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public double getQualityValue() {
		return NumberUtils.toDoubleValue(getValue(QualityFactor.PARAM_QUALITY_FACTOR), QualityFactor.MAX_DEFAULT);
	}
		
	/**
	 * 重写父类方法，追加对质量价值合法性的检查
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param attribute
	 * @param value
	 */
	@Override
	protected void checkParameter(String attribute, Object value) {
		super.checkParameter(attribute, value);
		
		if (QualityFactor.PARAM_QUALITY_FACTOR.equals(attribute)) {
			try {
				double qualityValue = Double.parseDouble(value.toString());
				checkQualityValue(qualityValue);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Parameter attribute '" + attribute 
						+ "' is a quality factor,value must be number");
			}
		}
	}
	
	/**
	 * 检查质量价值的合法性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param qualityValue
	 */
	protected void checkQualityValue(double qualityValue) {		
		AssertUtils.assertTrue(qualityValue >= QualityFactor.MIN_DEFAULT && qualityValue <= QualityFactor.MAX_DEFAULT,
				"Invalid http quality value " + qualityValue + ",should be between 0 and 1");
	}
	
	/**
	 * 将字符串解析成MediaType对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mediaType
	 * @return
	 */
	public static MediaType parse(String mimeType) {
		if (StringUtils.isBlank(mimeType))
			return null;
		
		String[] parts = StringUtils.split(mimeType, StringUtils.SEMICOLON);
		int length = parts.length;
		
		if (length == 0)
			return null;
		
		String fullType = parts[0].trim();
		
		if (!StringUtils.contains(fullType, StringUtils.FORWARD_SLASH))
			return null;
					
		String type = StringUtils.beforeFrist(fullType, StringUtils.FORWARD_SLASH);
		String subtype = StringUtils.afterFrist(fullType, StringUtils.FORWARD_SLASH);
		
		if (StringUtils.isBlank(type) || StringUtils.isBlank(subtype)
				// 不允许出现*/xxx的类型
				|| (StringUtils.ANY.equals(type) && !StringUtils.ANY.equals(subtype)))
			return null;
		
		if (length > 1) {
			Map<String, Object> parameters = MapUtils.newLinkedHashMap(length - 1);
			for (int i = 1; i < length; i++) {
				if (StringUtils.isNotBlank(parts[i])) {
					String attribute = StringUtils.beforeFrist(parts[i], StringUtils.ASSIGNMENT);
					String value = StringUtils.afterFrist(parts[i], StringUtils.ASSIGNMENT);
					// 属性名和值都不为空时才加入到参数中，其余情况一律忽略掉
					if (StringUtils.isNotBlank(attribute) && StringUtils.isNotBlank(value))
						parameters.put(attribute, value);
				}
			}
			
			return new MediaType(type, subtype, parameters);
		} 
			
		return new MediaType(type, subtype);
	}
		
}
