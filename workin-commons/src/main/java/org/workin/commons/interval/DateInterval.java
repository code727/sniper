///*
// * Copyright 2017 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// * 
// * Create Date : 2017年4月21日
// */
//
//package org.workin.commons.interval;
//
//import java.util.Date;
//
///**
// * 日期区间
// * @author  <a href="mailto:code727@gmail.com">杜斌</a>
// * @version 1.0
// */
//public class DateInterval extends AbstractInterval<Date> {
//	
//	public DateInterval(Date minimal, Date maximum) {
//		super(minimal, maximum);
//	}
//	
//	public DateInterval(String name, Date minimal, Date maximum) {
//		super(name, minimal, maximum);
//	}
//	
//	public DateInterval(Date minimal, Date maximum, boolean leftClose, boolean rightClose) {
//		super(minimal, maximum, leftClose, rightClose);
//	}
//	
//	public DateInterval(String name, Date minimal, Date maximum, boolean leftClose, boolean rightClose) {
//		super(name, minimal, maximum, leftClose, rightClose);
//	}
//	
//	@Override
//	protected void init(String name, Date minimal, Date maximum, boolean leftClose, boolean rightClose) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean contains(Date number) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean contains(Interval<Date> interval) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//}
