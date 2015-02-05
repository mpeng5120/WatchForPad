package com.isea.list2excel;

import java.io.File;
import java.util.List;

import com.wifi.data.BaseData;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExpListObjExcelVS {
	
	public final static String EXCEL = ".xls";
	
	private static XmlConfig xmlConfig = null;
	
	private static void createXmlConfig(){
		if(xmlConfig==null){
			xmlConfig = new XmlConfig();
		}
	}
	
	/**
	 * ����list����excel
	 * @param list
	 * @param xmlPath
	 * @param filePath
	 * @param fileName
	 * @return
	 * @throws AppException
	 */
	public static boolean expListObjFile(List<BaseData> list,String xmlPath,String filePath,String fileName) throws Exception {
		//����Ŀ¼
		File file = new File(filePath);
		if (!(file.exists())){
			file.mkdirs();
		}
		createXmlConfig();
		
		try {
			//��ȡ�����ļ�
			DlExcel config = xmlConfig.getXmlConfig(xmlPath);
			String[] header = config.getHeaders();
			String[] names = config.getNames();
			String[] values = null;
			
			WritableWorkbook wb = null;
			File excelFile = null;
			WritableSheet sheet = null;
			excelFile = new File(filePath+"/"+fileName+EXCEL);
			//**********д��EXCEL*********************************
			wb = Workbook.createWorkbook(excelFile);
			sheet = wb.createSheet((config.getSheet()!=null&&!config.getSheet().equals(""))?config.getSheet():"sheet", 0);
			
			int row = 0;
			int column = 0;
			int rowadd = 0;
			//д��title
			if(config.getTitle()!=null&&!config.getTitle().equals("")){
				WritableFont wtf = new WritableFont(WritableFont.createFont("����"),20,WritableFont.BOLD,false);
				WritableCellFormat cellFormat = new WritableCellFormat(wtf);
				cellFormat.setAlignment(Alignment.CENTRE);
				cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
				
				sheet.mergeCells(column, row+rowadd, header.length-1, row+rowadd);
				sheet.setRowView(row+rowadd, 1000);
				sheet.addCell(new Label(column,row+rowadd,config.getTitle(),cellFormat));
				rowadd++;
			}
			//д��˵��
			if(config.getDescription()!=null&&!config.getDescription().equals("")){
				WritableFont wtf = new WritableFont(WritableFont.createFont("����"),10,WritableFont.NO_BOLD,false);
				WritableCellFormat cellFormat = new WritableCellFormat(wtf);
				cellFormat.setAlignment(Alignment.LEFT);
				cellFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);
				
				sheet.mergeCells(column, row+rowadd, header.length-1, row+rowadd);
				sheet.setRowView(row+rowadd, 1000);
				sheet.addCell(new Label(column,row+rowadd,"    "+config.getDescription(),cellFormat));
				rowadd++;
			}
			//д�����
			for(column = 0 ; column<header.length ; column++){
				sheet.addCell(new Label(column,row+rowadd,header[column]));
				if(config.getColumns().get(column).getWidth()!=null&&!config.getColumns().get(column).getWidth().equals("")){
					sheet.setColumnView(column, Integer.parseInt(config.getColumns().get(column).getWidth())/7);
				}
			}
			rowadd++;
			//д������//��
			Object rowData = null;
			for(row = 0;row<list.size();row++){
				rowData = list.get(row);
				values = xmlConfig.getObjValues(rowData, names);
				//��
				for(column = 0 ; column<values.length ; column++){
					
					sheet.addCell(new Label(column,row+rowadd,values[column]));
					
				}
			}
			//***************************���д��***************
			//����ʣ�ಿ��
			if(wb!=null){
				wb.write();
				wb.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}
}
