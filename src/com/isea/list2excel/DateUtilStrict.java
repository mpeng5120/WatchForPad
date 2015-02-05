package com.isea.list2excel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * �ϸ������ת�����ı��Լ�DateUtil����ԭ��������������setLenient(false);
 * setLenient
 * public void setLenient(boolean lenient)ָ������/ʱ������Ƿ��ϸ񡣽��в��ϸ����ʱ�������������ʹ������ʽ�ķ�����������˶���ĸ�ʽ����ȷƥ������롣�����ϸ����ʱ���������ƥ��˶���ĸ�ʽ�� 
 * ������
 * lenient - Ϊ true ʱ�����������ǲ��ϸ��
 * �����Զ�����������ת��Ϊ��ȷ������
 * ����:19450000,ʹ��ԭDateUtil��ת��Ϊ19441130
 * @author liuzh
 */
public class DateUtilStrict {
	public static final String COMPACT_DATE_FORMAT = "yyyyMMdd";
	public static final String YM = "yyyyMM";
	public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd";
	public static final String NORMAL_DATE_FORMAT_NEW = "yyyy-mm-dd hh24:mi:ss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_ALL = "yyyyMMddHHmmssS";

	public static Long strDateToNum(String paramString) throws Exception {
		if (paramString == null)
			return null;
		String[] arrayOfString = null;
		String str = "";
		if (paramString.indexOf("-") >= 0) {
			arrayOfString = paramString.split("-");
			for (int i = 0; i < arrayOfString.length; ++i)
				str = str + arrayOfString[i];
			return Long.valueOf(Long.parseLong(str));
		}
		return Long.valueOf(Long.parseLong(paramString));
	}

	public static Long strDateToNum1(String paramString) throws Exception {
		if (paramString == null)
			return null;
		String[] arrayOfString = null;
		String str = "";
		if (paramString.indexOf("-") >= 0) {
			arrayOfString = paramString.split("-");
			for (int i = 0; i < arrayOfString.length; ++i)
				if (arrayOfString[i].length() == 1)
					str = str + "0" + arrayOfString[i];
				else
					str = str + arrayOfString[i];
			return Long.valueOf(Long.parseLong(str));
		}
		return Long.valueOf(Long.parseLong(paramString));
	}

	public static String numDateToStr(Long paramLong) {
		if (paramLong == null)
			return null;
		String str = null;
		str = paramLong.toString().substring(0, 4) + "-"
				+ paramLong.toString().substring(4, 6) + "-"
				+ paramLong.toString().substring(6, 8);
		return str;
	}

	public static java.util.Date stringToDate(String paramString1,
			String paramString2) throws Exception {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				paramString2);
		localSimpleDateFormat.setLenient(false);
		try {
			return localSimpleDateFormat.parse(paramString1);
		} catch (ParseException localParseException) {
			throw new Exception("���������ַ���ʱ����");
		}
	}

	public static String dateToString(java.util.Date paramDate,
			String paramString) {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				paramString);
		localSimpleDateFormat.setLenient(false);
		return localSimpleDateFormat.format(paramDate);
	}

	public static java.util.Date compactStringToDate(String paramString)
			throws Exception {
		return stringToDate(paramString, "yyyyMMdd");
	}

	public static String dateToCompactString(java.util.Date paramDate) {
		return dateToString(paramDate, "yyyyMMdd");
	}

	public static String dateToNormalString(java.util.Date paramDate) {
		return dateToString(paramDate, "yyyy-MM-dd");
	}

	public static String compactStringDateToNormal(String paramString)
			throws Exception {
		return dateToNormalString(compactStringToDate(paramString));
	}

	public static int getDaysBetween(java.util.Date paramDate1,
			java.util.Date paramDate2) throws Exception {
		Calendar localCalendar1 = Calendar.getInstance();
		Calendar localCalendar2 = Calendar.getInstance();
		localCalendar1.setTime(paramDate1);
		localCalendar2.setTime(paramDate2);
		if (localCalendar1.after(localCalendar2))
			throw new Exception("��ʼ����С����ֹ����!");
		int i = localCalendar2.get(6) - localCalendar1.get(6);
		int j = localCalendar2.get(1);
		if (localCalendar1.get(1) != j) {
			localCalendar1 = (Calendar) localCalendar1.clone();
			do {
				i += localCalendar1.getActualMaximum(6);
				localCalendar1.add(1, 1);
			} while (localCalendar1.get(1) != j);
		}
		return i;
	}

	public static java.util.Date addDays(java.util.Date paramDate, int paramInt)
			throws Exception {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		int i = localCalendar.get(6);
		localCalendar.set(6, i + paramInt);
		return localCalendar.getTime();
	}

	public static java.util.Date addDays(String paramString1,
			String paramString2, int paramInt) throws Exception {
		Calendar localCalendar = Calendar.getInstance();
		java.util.Date localDate = stringToDate(paramString1, paramString2);
		localCalendar.setTime(localDate);
		int i = localCalendar.get(6);
		localCalendar.set(6, i + paramInt);
		return localCalendar.getTime();
	}

	public static java.sql.Date getSqlDate(java.util.Date paramDate)
			throws Exception {
		java.sql.Date localDate = new java.sql.Date(paramDate.getTime());
		return localDate;
	}

	public static String formatDate(java.util.Date paramDate) {
		if (paramDate == null)
			return null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		localSimpleDateFormat.setLenient(false);
		return localSimpleDateFormat.format(paramDate);
	}

	public static String formatDateTime(java.util.Date paramDate) {
		if (paramDate == null)
			return null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		localSimpleDateFormat.setLenient(false);
		return localSimpleDateFormat.format(paramDate);
	}

	public static java.util.Date parseDate(String paramString)
			throws Exception {
		if ((paramString == null) || (paramString.trim().equals("")))
			return null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		localSimpleDateFormat.setLenient(false);
		try {
			return localSimpleDateFormat.parse(paramString);
		} catch (ParseException localParseException) {
			throw new Exception("���ڽ�������", localParseException);
		}
	}

	public static java.util.Date parseDateTime(String paramString)
			throws Exception {
		if ((paramString == null) || (paramString.trim().equals("")))
			return null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		localSimpleDateFormat.setLenient(false);
		try {
			return localSimpleDateFormat.parse(paramString);
		} catch (ParseException localParseException) {
			throw new Exception("ʱ������쳣��", localParseException);
		}
	}

	public static Integer getYM(String paramString) throws Exception {
		if (paramString == null)
			return null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		localSimpleDateFormat.setLenient(false);
		java.util.Date localDate;
		try {
			localDate = localSimpleDateFormat.parse(paramString);
		} catch (ParseException localParseException) {
			throw new Exception("ʱ������쳣��", localParseException);
		}
		return getYM(localDate);
	}

	public static Integer getYM(java.util.Date paramDate) {
		if (paramDate == null)
			return null;
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		int i = localCalendar.get(1);
		int j = localCalendar.get(2) + 1;
		return new Integer(i * 100 + j);
	}

	public static int addMonths(int paramInt1, int paramInt2) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.set(1, paramInt1 / 100);
		localCalendar.set(2, paramInt1 % 100 - 1);
		localCalendar.set(5, 1);
		localCalendar.add(2, paramInt2);
		return getYM(localCalendar.getTime()).intValue();
	}

	public static java.util.Date addMonths(java.util.Date paramDate,
			int paramInt) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		localCalendar.add(2, paramInt);
		return localCalendar.getTime();
	}

	public static int monthsBetween(int paramInt1, int paramInt2) {
		int i = paramInt2 / 100 * 12 + paramInt2 % 100
				- (paramInt1 / 100 * 12 + paramInt1 % 100);
		return i;
	}

	public static int monthsBetween(java.util.Date paramDate1,
			java.util.Date paramDate2) {
		return monthsBetween(getYM(paramDate1).intValue(), getYM(paramDate2).intValue());
	}

	public static String getChineseDate(Calendar paramCalendar) {
		int i = paramCalendar.get(1);
		int j = paramCalendar.get(2);
		int k = paramCalendar.get(5);
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(i);
		localStringBuffer.append("��");
		localStringBuffer.append(j + 1);
		localStringBuffer.append("��");
		localStringBuffer.append(k);
		localStringBuffer.append("��");
		return localStringBuffer.toString();
	}

	public static String getChineseWeekday(Calendar paramCalendar) {
		switch (paramCalendar.get(7)) {
		case 2:
			return "����һ";
		case 3:
			return "���ڶ�";
		case 4:
			return "������";
		case 5:
			return "������";
		case 6:
			return "������";
		case 7:
			return "������";
		case 1:
			return "������";
		}
		return "δ֪";
	}

	public static void main(String[] paramArrayOfString) {
		try {
			System.out.println(formatDate(addMonths(parseDate("2006-01-06"), 12)));
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}
}
