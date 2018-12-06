/*
 * Copyright (c) 2015 org.sniper-support 
 * Create Date : 2015-1-16
 */

package org.sniper.resource.test.file;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.resource.file.filter.FileNumberFilter;
import org.sniper.resource.file.filter.FileStringFilter;
import org.sniper.resource.file.filter.application.FileLastModifiedTimeFilter;
import org.sniper.resource.file.filter.application.FileNameFilter;
import org.sniper.resource.file.filter.application.FileSizeFilter;
import org.sniper.resource.file.filter.application.FileTypeFilter;
import org.sniper.test.junit.BaseTestCase;

/**
 * 文件过滤器测试
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2015-1-16
 */
public class FileFilterTest extends BaseTestCase {
	
	/**
	 * 测试文件名称过滤器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	@Test
	public void testFileNameFilter() {
		FileStringFilter filter = new FileNameFilter();
		filter.setRoot(new File("D:/test"));
		filter.setFilterValue("新建");
		filter.execute();
		List<File> files = filter.list();
		if (CollectionUtils.isNotEmpty(files)) {
			System.out.println("File name filter list not empty.Start assertion.");
			for (File file : files) 
				assertTrue(StringUtils.indexOfIgnoreCase(file.getName(), filter.getFilterValue()) > -1);	
			System.out.println("File name filter assertion end.");
		}
	}
	
	/**
	 * 测试文件类型过滤器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	@Test
	public void testFileTypeFilter() {
		FileStringFilter filter = new FileTypeFilter();
		filter.setRoot(new File("D:/test"));
		filter.setFilterValue("docx");
		filter.execute();
		
		List<File> files = filter.list();
		if (CollectionUtils.isNotEmpty(files)) {
			System.out.println("File type filter list not empty.Start assertion.");
			for (File file : files) {
				assertTrue(StringUtils.endsWithIgnoreCase(file.getName(), filter.getFilterValue()));	
			}
				
			System.out.println("File type filter assertion end.");
		}
	}
	
	/**
	 * 测试文件大小过滤器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	@Test
	public void testFileSizeFilter() {
		FileNumberFilter filter = new FileSizeFilter();
		filter.setRoot(new File("D:/test"));
		
		/* 过滤出大于等于5个字节的文件 */
		filter.setLogicOperation(">=");
		filter.setFilterValue(5);
		
		filter.execute();
		List<File> files = filter.list();
		if (CollectionUtils.isNotEmpty(files)) {
			System.out.println("File size filter list not empty.Start assertion.");
			for (File file : files) {
				assertTrue(file.isFile());	
				System.out.println(file + "length:" + file.length());
			}
			System.out.println("File size filter assertion end.");
		}
	}
	
	/**
	 * 测试文件最后修改时间过滤器过滤器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	@Test
	public void testFileLastModifiedTimeFilter() {
		FileNumberFilter filter = new FileLastModifiedTimeFilter();
		filter.setRoot(new File("D:/test"));
		
		/* 过滤出当前时间之前修改之间的文件/目录 */
		filter.setLogicOperation("<");
		long currentMillis = System.currentTimeMillis();
		filter.setFilterValue(currentMillis);
		
		filter.execute();
		List<File> files = filter.list();
		if (CollectionUtils.isNotEmpty(files)) {
			System.out.println("File lastModified filter list not empty.Start assertion.");
			for (File file : files) 
				assertTrue(file.lastModified() < currentMillis);
			System.out.println("File lastModified filter assertion end.");
		}
	}
	
}
