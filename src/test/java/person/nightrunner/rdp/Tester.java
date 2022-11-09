package person.nightrunner.rdp;

import person.nightrunner.rdp.check.CheckContext;
import person.nightrunner.rdp.check.CheckEngineImpl;
import person.nightrunner.rdp.check.impl.CheckContextImpl;
import person.nightrunner.rdp.check.impl.RequiredFieldsChecker;
import person.nightrunner.rdp.impl.*;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Tester {

    @Test
    public void testEasyExcelReader() throws FileNotFoundException {
        ColumnFactory columnFactory = new ColumnFlyweightFactory();
        Column[] columns = columnFactory.getByLabel("序号", "最新制度-编码", "最新制度-名称", "历史制度-曾用编码", "历史制度-曾用制度", "版本号", "发布时间", "生效时间", "废止时间", "体系名称", "制度级别", "发布文号", "发布范围", "起草部门", "批准人", "批准依据", "正文路径", "附件目录", "管理表单目录", "最新制度是否已入系统");

        File file = ResourceUtils.getFile("classpath:test.xlsx");
        POIExcelReader reader = new POIExcelReader(columnFactory, file, "制度历史版本", 4, columns);

        List<Column> needCheckColumns = new ArrayList<>();
        needCheckColumns.add(columnFactory.getByLetter("O"));
        needCheckColumns.add(columnFactory.getByLetter("P"));
        RequiredFieldsChecker requiredFieldsChecker = new RequiredFieldsChecker(needCheckColumns);

        Writer writer = new POIExcelWriter(new File("d:\\a.xls"), "result");
        Engine engine = new CheckEngineImpl(columnFactory);
        engine.setReader(reader).setContext(new CheckContextImpl()).addProcessor(requiredFieldsChecker).setWriter(writer).go();
    }

    @Test
    public void testCVS() throws FileNotFoundException {
        ColumnFlyweightFactory columnFactory = new ColumnFlyweightFactory();

        File file = ResourceUtils.getFile("classpath:test.csv");
        Reader reader = new CVSReader(columnFactory, file, 1, columnFactory.getByLabel("序号", "姓名", "年龄", "性别", "身高", "体重", "年级"));
        Writer writer = new CVSWriter(ResourceUtils.getFile("classpath:test-output.csv"));
        Engine engine = new CheckEngineImpl(columnFactory);
        CheckContext context = new CheckContextImpl();
        engine.setContext(context);
        engine.setWriter(writer);
        engine.setReader(reader);
        engine.addProcessor(new RequiredFieldsChecker(columnFactory.getByLabel("姓名")));
        engine.go();
    }
}
