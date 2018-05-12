package com.wz.common.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.wz.common.tools.PatternUtils;


/**
 * 
 * @author excel2007使用包：poi-ooxml-3.8-20120326.jar;
 *         poi-ooxml-schemas-3.8-20120326.jar; 依赖包：dom4j-1.6.1.jar;
 *         stax-api-1.0.1.jar; xmlbeans-2.3.0.jar excel2003使用包:
 *         poi-3.8-20120326.jar
 */

@SuppressWarnings({ "deprecation", "rawtypes", "resource", "static-access" })
public class ExcelUtils {

	/**
	 * 生成一个Excel文件POI
	 * 
	 * @param inputFile
	 *            输入模板文件路径
	 * @param outputFile
	 *            输入文件存放于服务器路径
	 * @param dataList
	 *            待导出数据
	 * @throws exception
	 * @roseuid:
	 */
	public static void exportExcelFile(String inputFile, String outputFile, List dataList) throws Exception {
		// 用模板文件构造poi
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(inputFile));
		// 创建模板工作表
		HSSFWorkbook templatewb = new HSSFWorkbook(fs);
		// 直接取模板第一个sheet对象
		HSSFSheet templateSheet = templatewb.getSheetAt(1);
		// 得到模板的第一个sheet的第一行对象 为了得到模板样式
		HSSFRow templateRow = templateSheet.getRow(0);

		// HSSFSheet timplateSheet = templatewb.getSheetAt(1);
		// 取得Excel文件的总列数
		int columns = templateSheet.getRow((short) 0).getPhysicalNumberOfCells();
		// Debug.println("columns   is   :   " + columns); //
		// 创建样式数组
		HSSFCellStyle styleArray[] = new HSSFCellStyle[columns];

		// 一次性创建所有列的样式放在数组里
		for (int s = 0; s < columns; s++) {
			// 得到数组实例
			styleArray[s] = templatewb.createCellStyle();
		}
		// 循环对每一个单元格进行赋值
		// 定位行
		for (int rowId = 1; rowId < dataList.size(); rowId++) {
			// 依次取第rowId行数据 每一个数据是valueList
			List valueList = (List) dataList.get(rowId - 1);
			// 定位列
			for (int columnId = 0; columnId < columns; columnId++) {
				// 依次取出对应与colunmId列的值
				// 每一个单元格的值
				String dataValue = (String) valueList.get(columnId);
				// 取出colunmId列的的style
				// 模板每一列的样式
				HSSFCellStyle style = styleArray[columnId];
				// 取模板第colunmId列的单元格对象
				// 模板单元格对象
				HSSFCell templateCell = templateRow.getCell((short) columnId);
				// 创建一个新的rowId行 行对象
				// 新建的行对象
				HSSFRow hssfRow = templateSheet.createRow(rowId);
				// 创建新的rowId行 columnId列 单元格对象
				// 新建的单元格对象
				HSSFCell cell = hssfRow.createCell((short) columnId);
				// 如果对应的模板单元格 样式为非锁定
				if (templateCell.getCellStyle().getLocked() == false) {
					// 设置此列style为非锁定
					style.setLocked(false);
					// 设置到新的单元格上
					cell.setCellStyle(style);
				} else { // 否则样式为锁定
							// 设置此列style为锁定
					style.setLocked(true);
					// 设置到新单元格上
					cell.setCellStyle(style);
				}
				// 设置编码
				// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				// Debug.println("dataValue   :   " + dataValue);
				// 设置值 统一为String
				cell.setCellValue(dataValue);
			}
		}
		// 设置输入流
		FileOutputStream fOut = new FileOutputStream(outputFile);
		// 将模板的内容写到输出文件上
		templatewb.write(fOut);
		fOut.flush();

		// 操作结束，关闭文件
		fOut.close();

	}

	/**
	 * 取文件后
	 * 
	 * @param fileName文件名称
	 *            ，无后缀则返，有则返
	 * @return
	 */
	public static String getFilePostFix(String fileName) {
		return "." + PatternUtils.parseStr(fileName, "^.+(\\.[^\\?]+)(\\?.+)?", 1).replaceFirst("\\.", "");
	}

	/**
	 * excel内容解析总入口
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static List<String[]> readExcel(String path) throws Exception {
		String fix = getFilePostFix(path);
		if (fix != null && !"".equals(fix)) {
			if (fix.equals("." + ExcelType.XLS.name().toLowerCase())) {
				return ExcelUtils.read2003(path);
			} else if (fix.equals("." + ExcelType.XLSX.name().toLowerCase())) {
				return ExcelUtils.read2007(path);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 
	 * 解析excel2003及以下版本(后缀名儿.xls)
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static List<String[]> read2003(String path) throws IOException {
		List<String[]> list = new ArrayList<String[]>();
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(path));
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet xssfSheet = workbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}

			// 循环行Row
			int maxRow = xssfSheet.getLastRowNum();
			for (int rowNum = 0; rowNum <= maxRow; rowNum++) {
				HSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow == null) {
					continue;
				}
				// 循环列Cell
				int cellCount = xssfRow.getLastCellNum();
				if (cellCount < 1)
					continue;
				String[] obj = new String[cellCount];
				for (int cellNum = 0; cellNum < cellCount; cellNum++) {
					HSSFCell xssfCell = xssfRow.getCell(cellNum);
					if (xssfCell == null) {
						obj[cellNum] = "";
						continue;
					}
					obj[cellNum] = getValue(xssfCell);
				}
				if (isEfffective(obj)) {
					list.add(obj);
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * 解析excel2007(后缀名为：xlsx)
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static List<String[]> read2007(String path) throws IOException {
		List<String[]> list = new ArrayList<String[]>();
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(path));
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}

			// 循环行Row
			int maxRow = xssfSheet.getLastRowNum();
			for (int rowNum = 0; rowNum <= maxRow; rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow == null) {
					continue;
				}
				// 循环列Cell
				int cellCount = xssfRow.getLastCellNum();
				if(cellCount <= 0)
					continue;
				String[] obj = new String[cellCount];
				for (int cellNum = 0; cellNum < cellCount; cellNum++) {
					XSSFCell xssfCell = xssfRow.getCell(cellNum);
					if (xssfCell == null) {
						obj[cellNum] = "";
						continue;
					}
					obj[cellNum] = getValue(xssfCell);
				}
				if (isEfffective(obj)) {
					list.add(obj);
				}
			}
		}
		return list;
	}

	/**
	 * 是否有效
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEfffective(String[] obj) {
		boolean isEfffective = false;
		for (String o : obj) {
			if (o != null && !"".equals(o.trim())) {
				isEfffective = true;
				break;
			}
		}
		return isEfffective;
	}

	/**
	 * 值转化
	 * 
	 * @param xssfCell
	 * @return
	 */
	public static String getValue(XSSFCell xssfCell) {
		String back = "";
		try {
			if (xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN) {
				back = String.valueOf(xssfCell.getBooleanCellValue());
			} else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
				back = String.valueOf((long) xssfCell.getNumericCellValue());
			} else {
				back = String.valueOf(xssfCell.getStringCellValue());
			}
		} catch (Exception e) {
			back = "";
		} finally {
			if (back == null || "null".equals(back))
				back = "";
		}
		return back;
	}

	/**
	 * 值转化
	 * 
	 * @param xssfCell
	 * @return
	 */
	public static String getValue(HSSFCell hssfCell) {
		String back = "";
		try {
			if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
				back = String.valueOf(hssfCell.getBooleanCellValue());
			} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
				back = String.valueOf((long) hssfCell.getNumericCellValue());
			} else {
				back = String.valueOf(hssfCell.getStringCellValue());
			}
		} catch (Exception e) {
			back = "";
		} finally {
			if (back == null || "null".equals(back) || "A".equals(back.trim()))
				back = "";
		}
		return back;
	}

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");
		TimeZone t = sdf.getTimeZone();
		t.setRawOffset(0);
		sdf.setTimeZone(t);
		Long startTime = System.currentTimeMillis();
		String fileName_2003 = "C:/Users/123/Desktop/工作文档.xlsx";
		// 检测代码
		try {
			printList(readExcel(fileName_2003));
			List<String[]> list = new ArrayList<>(readExcel(fileName_2003));
			for (String[] rows : list) {
				for (int i = 0; i < rows.length; i++) {
					System.out.println(rows[i]);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Long endTime = System.currentTimeMillis();
		System.out.println("-----------------------------------------");
		System.out.println("用时：" + sdf.format(new Date(endTime - startTime)));
	}

	public static void printList(List<String[]> objs) {
		for (String[] obj : objs) {
			System.out.println(obj.length + ":  " + Arrays.toString(obj));
		}
	}

	public static enum ExcelType {
		XLS, XLSX
	}

	/**
	 * 导出Excel方法
	 * 
	 * @param title
	 * @param headers
	 * @param dataset
	 * @param out
	 * @param pattern
	 */
	public static void exportExcel(String title, String[] headers, String[] cellName, List<Map<String, Object>> dataset, OutputStream out) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 20);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		//
		String value = null;
		for (int i = 0, j = dataset.size(); i < j; i++) {
			HSSFRow rowc = sheet.createRow(i + 1);
			Map<String, Object> map = dataset.get(i);
			for (int m = 0, n = headers.length; m < n; m++) {
				HSSFCell cell = rowc.createCell(m);
				cell.setCellStyle(style2);
				value = String.valueOf((map.get(cellName[m])));
				cell.setCellValue(value);

			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void exportExcel(String title, String[] headers, String[] cellName, List<Map<String, Object>> dataset,
			List<Map<String, Object>> dataset2, OutputStream out) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 20);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 生成并设置另一个样式
		HSSFCellStyle style3 = workbook.createCellStyle();
		style3.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font3 = workbook.createFont();
		font3.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style3.setFont(font3);
		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		//
		String value2 = null;
		int rowNumber = 0;
		for (int i = 0, j = dataset2.size(); i < j; i++) {
			// System.out.println("here");
			HSSFRow rowc = sheet.createRow(rowNumber + 1);
			rowNumber++;
			Map<String, Object> map = dataset2.get(i);
			for (int m = 0, n = headers.length; m < n; m++) {
				System.out.println(map);
				HSSFCell cell = rowc.createCell(m);
				cell.setCellStyle(style3);
				value2 = String.valueOf((map.get(cellName[m])));

				cell.setCellValue(value2);

			}
		}

		String value = null;
		for (int i = 0, j = dataset.size(); i < j; i++) {
			HSSFRow rowc = sheet.createRow(rowNumber + 1);
			rowNumber++;
			Map<String, Object> map = dataset.get(i);
			for (int m = 0, n = headers.length; m < n; m++) {
				HSSFCell cell = rowc.createCell(m);
				cell.setCellStyle(style2);
				value = String.valueOf((map.get(cellName[m])));
				cell.setCellValue(value);

			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
