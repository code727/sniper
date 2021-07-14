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
 * Create Date : 2017-8-29
 */

package org.sniper.commons.enums.http;

import java.util.Map;

import org.sniper.commons.enums.Enumerable;
import org.sniper.commons.util.MapUtils;

/**
 * HTTP媒介类型枚举
 * @author  Daniele
 * @version 1.0
 */
public enum MediaEnum implements Enumerable<Integer> {
	
	ALL("*/*"),
	APPLICATION_ATOM_XML("application/atom+xml"),
	APPLICATION_FONT_WOFF("application/font-woff"),
	APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded"),
	APPLICATION_JAVA_ARCHIVE("application/java-archive"),
	APPLICATION_JAVASCRIPT("application/javascript"),
	APPLICATION_JSON("application/json"),
	APPLICATION_JSON_UTF8("application/json;charset=UTF-8"),
	APPLICATION_MAC_BINHEX40("application/mac-binhex40"),
	APPLICATION_MSWORD("application/msword"),
	APPLICATION_OCTET_STREAM("application/octet-stream"),
	APPLICATION_PDF("application/pdf"),
	APPLICATION_POSTSCRIPT("application/postscript"),
	APPLICATION_RSS_XML("application/rss+xml"),
	APPLICATION_RTF("application/rtf"),
	APPLICATION_VND_APPLE_MPEGURL("application/vnd.apple.mpegurl"),
	APPLICATION_VND_MS_EXCEL("application/vnd.ms-excel"),
	APPLICATION_VND_MS_FONTOBJECT("application/vnd.ms-fontobject"),
	APPLICATION_VND_MS_POWERPOINT("application/vnd.ms-powerpoint"),
	APPLICATION_VND_WAP_WMLC("application/vnd.wap.wmlc"),
	APPLICATION_VND_GOOGLE_EARTH_KML_XML("application/vnd.google-earth.kml+xml"),
	APPLICATION_VND_GOOGLE_EARTH_KMZ("application/vnd.google-earth.kmz"),
	APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_DOCUMENT("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
	APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_SHEET("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
	APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_PRESENTATION("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
	APPLICATION_X_7Z_COMPRESSED("application/x-7z-compressed"),
	APPLICATION_X_COCOA("application/x-cocoa"),
	APPLICATION_X_JAVA_ARCHIVE_DIFF("application/x-java-archive-diff"),
	APPLICATION_X_JAVA_JNLP_FILE("application/x-java-jnlp-file"),
	APPLICATION_X_MAKESELF("application/x-makeself"),
	APPLICATION_X_PERL("application/x-perl"),
	APPLICATION_X_PILOT("application/x-pilot"),
	APPLICATION_X_RAR_COMPRESSED("application/x-rar-compressed"),
	APPLICATION_X_REDHAT_PACKAGE_MANAGER("application/x-redhat-package-manager"),
	APPLICATION_X_SEA("application/x-sea"),
	APPLICATION_X_SHOCKWAVE_FLASH("application/x-shockwave-flash"),
	APPLICATION_X_STUFFIT("application/x-stuffit"),
	APPLICATION_X_TCL("application/x-tcl"),
	APPLICATION_X_X509_CA_CERT("application/x-x509-ca-cert"),
	PPLICATION_X_XPINSTALL("pplication/x-xpinstall"),
	APPLICATION_XHTML_XML("application/xhtml+xml"),
	APPLICATION_XML("application/xml"),
	APPLICATION_XSPF_XML("application/xspf+xml"),
	APPLICATION_ZIP("application/zip"),
	AUDIO_MIDI("audio/midi"),
	AUDIO_MPEG("audio/mpeg"),
	AUDIO_OGG("audio/ogg"),
	AUDIO_X_M4A("audio/x-m4a"),
	AUDIO_X_REALAUDIO("audio/x-realaudio"),
	IMAGE_GIF("image/gif"),
	IMAGE_JPEG("image/jpeg"),
	IMAGE_PNG("image/png"),
	IMAGE_TIFF("image/tiff"),
	IMAGE_VND_WAP_WBMP("image/vnd.wap.wbmp"),
	IMAGE_X_ICON("image/x-icon"),
	IMAGE_X_JNG("image/x-jng"),
	IMAGE_X_MS_BMP("image/x-ms-bmp"),
	IMAGE_SVG_XML("image/svg+xml"),
	IMAGE_WEBP("image/webp"),
	MULTIPART_FORM_DATA("multipart/form-data"),
	TEXT_CSS("text/css"),
	TEXT_HTML("text/html"),
	TEXT_MARKDOWN("text/markdown"),
	TEXT_MATHML("text/mathml"),
	TEXT_PLAIN("text/plain"),
	TEXT_VND_SUN_J2ME_APP_DESCRIPTOR("text/vnd.sun.j2me.app-descriptor"),
	TEXT_VND_WAP_WML("text/vnd.wap.wml"),
	TEXT_X_COMPONENT("text/x-component"),
	TEXT_XML("text/xml"),
	VIDEO_3GPP("video/3gpp"),
	VIDEO_MP2T("video/mp2t"),
	VIDEO_MP4("video/mp4"),
	VIDEO_MPEG("video/mpeg"),
	VIDEO_QUICKTIME("video/quicktime"),
	VIDEO_WEBM("video/webm"),
	VIDEO_X_FLV("video/x-flv"),
	VIDEO_X_M4V("video/x-m4v"),
	VIDEO_X_MNG("video/x-mng"),
	VIDEO_X_MS_ASF("video/x-ms-asf"),
	VIDEO_X_MS_WMV("video/x-ms-wmv"),
	VIDEO_X_MSVIDEO("video/x-msvideo")
	;
	
	private static final Map<Integer, MediaEnum> KEY_MAPPINGS = MapUtils.newHashMap(81);
	private static final Map<String, MediaEnum> TYPE_AND_NAME_MAPPINGS = MapUtils.newHashMap(81);
	
	/** 键 */
	private final int key;
	
	/** 类型 */
	private final String type;
		
	/** 消息 */
	private final String message;
	
	static {
		for (MediaEnum media : values()) {
			KEY_MAPPINGS.put(media.key, media);
			TYPE_AND_NAME_MAPPINGS.put(media.type.toUpperCase(), media);
			TYPE_AND_NAME_MAPPINGS.put(media.name(), media);
		}
	}
	
	private MediaEnum(String type) {
		this.key = ordinal();
		this.type = type;
		this.message = type;
	}
	
	@Override
	public Integer getKey() {
		return key;
	}
		
	public String getType() {
		return type;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	@Override
	public boolean matches(Integer key) {
		return key != null && this.key == key.intValue();
	}
	
	/**
	 * 判断指定的类型或名称是否匹配当前枚举对象
	 * @author Daniele
	 * @param typeOrName
	 * @return
	 */
	@Override
	public boolean matches(String typeOrName) {
		return this.type.equalsIgnoreCase(typeOrName) || this.name().equalsIgnoreCase(typeOrName);
	}

	/**
	 * 将指定的键解析成枚举对象
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public static MediaEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的类型或名称解析成枚举对象
	 * @author Daniele 
	 * @param typeOrName
	 * @return
	 */
	public static MediaEnum resolve(String typeOrName) {
		return typeOrName != null ? TYPE_AND_NAME_MAPPINGS.get(typeOrName.toUpperCase()) : null;
	}
		
}
