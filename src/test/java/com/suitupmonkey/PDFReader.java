package com.suitupmonkey;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFReader {



    public static void main(String[] args) throws IOException {

        Map<String,List<String>> catalogMap = catalog();//目录

        Map<String,String> relationMap = relationMap(catalogMap);//二级目录对应的内容

        Workbook workbook;
        Sheet sheet;

        int gap = 0;
        try{
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet();

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setWrapText(true);



            List<Integer> levelOneNumber = new ArrayList<>();//每个标题需要的单元格个数
            List<String> levelOneName = new ArrayList<>();
            
            for (Map.Entry<String,List<String>> entry: catalogMap.entrySet()) {
                String levelOne = entry.getKey();//一级目录
                List<String> levelTwoList = entry.getValue();
                levelOneNumber.add(levelTwoList.size());//需要合并单元格的个数
                levelOneName.add(levelOne);//一级目录
            }
            
            //填充一级菜单
            Row firstRow = sheet.createRow(0);//第一行


            for (int i = 0; i < levelOneName.size(); i++) {

                String menuName = levelOneName.get(i);//一级目录名称
                Integer cellNumber = levelOneNumber.get(i);//一级目录占据的单元格个数
                //第一个目录名称
                if(i == 0){
                    gap = cellNumber - 1;
                    Cell cell = firstRow.createCell(i);
                    CellRangeAddress region = new CellRangeAddress( 0,0,0, gap);
                    sheet.addMergedRegion(region);
                    cell.setCellValue(menuName);


                    continue;
                }
                int start = 0;
                if(i != 0){
                    start = gap;
                    gap = cellNumber + gap;
                    CellRangeAddress region = new CellRangeAddress( 0,0,start+1, gap);
                    sheet.addMergedRegion(region);
                }

                Cell cell = firstRow.createCell(start+1);
                cell.setCellValue(menuName);


                System.out.println(1);
            }

            //二级目录
            List<String> subList = new ArrayList<>();
            for (Map.Entry<String,List<String>> entry: catalogMap.entrySet()) {
                List<String> menuList = entry.getValue();
                for (int i = 0; i < menuList.size(); i++) {
                    subList.add(menuList.get(i));//二级目录统一放到一个list中。
                }

            }

            //填充二级目录
            Row secondRow = sheet.createRow(1);
            Row thirdRow = sheet.createRow(2);
            thirdRow.setHeightInPoints(20);
            thirdRow.setHeight((short) (170 * 20));
            for (int i = 0; i < subList.size(); i++) {
                sheet.setColumnWidth(i,50 * 256);
                String stuff = subList.get(i);
                Cell cell = secondRow.createCell(i);
                cell.setCellValue(stuff);
                for (Map.Entry<String,String> entry: relationMap.entrySet()) {
                    String key = entry.getKey();
                    //二级目录对应的内容
                    if(stuff.contains(key)){
                        Cell contentCell = thirdRow.createCell(i);
                        contentCell.setCellValue(entry.getValue());
                        contentCell.setCellStyle(cellStyle);
                    }
                }
            }

            File file = new File("D:\\demo.xlsx");
            FileOutputStream fout = new FileOutputStream(file);
            workbook.write(fout);
            fout.close();



        }catch (Exception e){
            e.printStackTrace();
        }



        System.out.println(1);


    }



    //关联二级目录和对应的内容
    public static Map<String,String> relationMap(Map<String,List<String>> catalogMap) throws IOException {
        File file = new File("D:\\安邦附加安保 1 号意外伤害医疗保险 A 款条款.PDF");

        PDDocument document = PDDocument.load(file);

        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition(true);

        PDFTextStripper tStripper = new PDFTextStripper();

        String content = tStripper.getText(document);
        int catalogStart = content.lastIndexOf("您与本公司的合同");
        int catalogEnd = content.indexOf("现金价值比例表");

        String substring = content.substring(catalogStart + 8, catalogEnd);//目录字符串


        substring = Pattern.compile("\n").matcher(substring).replaceAll("");

        //二级目录标题后面的空格数量不固定，统一替换成“tag”作为标记，供后续匹配。
        String rawRelation = Pattern.compile("[1-9].[0-9]+\\s+").matcher(substring).replaceAll("tag");


        List<String> rawList = Arrays.asList(rawRelation.split("tag"));

        Map<String,String> relationMap = new LinkedHashMap<>();//二级目录和内容对应关系的容器



        for (Map.Entry<String,List<String>> entry: catalogMap.entrySet()) {


            List<String> levelTwoList = entry.getValue();

            for (int i = 0; i < levelTwoList.size(); i++) {
                String levelTwo = levelTwoList.get(i);
                Pattern compile = Pattern.compile("[0-9].[0-9]+\\s*");
                Matcher matcher = compile.matcher(levelTwo);
                String rawStr = matcher.replaceAll("");

                for (int j = 0; j < rawList.size(); j++) {
                    String value = rawList.get(j);
                    if(StringUtils.isBlank(value)){
                        continue;
                    }
                    if(value.startsWith(rawStr)){
                        String number = Pattern.compile("\\s.*").matcher(levelTwo).replaceAll("")+" ";
                        String stuff = number + value;
                        stuff = stuff.substring(levelTwo.length()).trim();
                        relationMap.put(number,stuff);
                    }
                }
            }
        }

        return relationMap;
    }


    //拆分目录的方法
    public static Map<String,List<String>> catalog() throws IOException {

        File file = new File("D:\\安邦附加安保 1 号意外伤害医疗保险 A 款条款.PDF");

        PDDocument document = PDDocument.load(file);

        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition(true);

        PDFTextStripper tStripper = new PDFTextStripper();

        String content = tStripper.getText(document);
        int catalogStart = content.indexOf("条款目录");
        int catalogEnd = content.indexOf("安邦人寿");

        String substring = content.substring(catalogStart+4, catalogEnd);//目录字符串

        //去回车空格
        String description = Pattern.compile("[\n]").matcher(substring).replaceAll(",");
        description = Pattern.compile("[\r]").matcher(description).replaceAll("");
        description.replaceAll("\r","");

        String[] split = description.split(",");//逗号分隔。



        //把每个目录分割成单个字符串，存list
        List<String> splitList = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if(StringUtils.isNoneBlank(s)){
                splitList.add(s.trim());
            }

        }


        String tempHead = splitList.get(0).charAt(0)+"";

        String levelOne = "";
        Map<String,List<String>> catalogMap = new LinkedHashMap<>();
        List<String> subordinateList = new ArrayList<>();
        for (int i = 0; i < splitList.size(); i++) {
            String s = splitList.get(i);
            char c = s.charAt(0);//首个字符

            int count = countNumber(s);//目录中数字的个数

            if(count == 1) levelOne = s; //一级目录


            if(!(c+"").equals(tempHead)){
                subordinateList = new ArrayList<>();//首字符不相同，说明属不同等级的目录。
                tempHead = c + "";//更新首字符
            }

            //创建多个set
            for (int j = 0; j < splitList.size(); j++) {
                String s2 = splitList.get(j);
                char c2 = s2.charAt(0);
                int count2 = countNumber(s2);
                if(count2 > 1 && (c+"").equals((c2+"")) && !subordinateList.contains(s2)){
                    subordinateList.add(s2);//二级目录
                }

            }


            //放map
            catalogMap.put(levelOne,subordinateList);


        }

        return catalogMap;
    };



    //判断一个字符串中数字的个数
    private static int countNumber(String random) {
        char[] chars = random.toCharArray();

        int count = 0;

        for (int i = 0; i < chars.length; i++) {
            if(chars[i] >= 48 && chars[i] <= 57){
                count++;
            }

        }
        return count;
    }





}
