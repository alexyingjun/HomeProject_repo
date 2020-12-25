package com.iocoder.demo01.springdemo.controller;

import com.iocoder.demo01.springdemo.controller.response.Result;
import com.iocoder.demo01.springdemo.controller.response.ResultGenerator;
import com.iocoder.demo01.springdemo.data.ATPDao;
import com.iocoder.demo01.springdemo.data.Stock;
import com.iocoder.demo01.springdemo.properties.HomeProperties;
import com.iocoder.demo01.springdemo.properties.UserProperties;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    ATPDao atpDao;

    @Autowired  HomeProperties homeProperties;
    @Autowired  UserProperties userProperties;

    @GetMapping("/echo")
    public String echo (){
        return "echo";
    }

    @GetMapping("/notes")
    public String notes(@RequestParam String note) { return "Note: "+note;}

    @GetMapping("/name")
    public String name(@RequestParam(name="name") String name, @RequestParam(name="age", required=false) String age) { return "Name: "+name +"   Age: "+age;}

 //   @GetMapping("/people")
    //http://127.0.0.1:8080/demo/people?name=acb&addr=8%20flat
    @RequestMapping(value = "/people", method = RequestMethod.GET)
    public String People(@RequestParam(defaultValue = "test")String name, @RequestParam(required=false) Optional<String> addr) {
        System.out.println("Invoked in people");
        return "Name: "+name +"   \t\t  Address: "+addr.orElseGet(()->"not provided");
    }

//    http://140.238.86.212:8080/demo/people/1?name=&addr=8%20flat
    @RequestMapping(value = "/people/{id}", method = RequestMethod.GET)
    public Result getPeopleById(@PathVariable(value="id") String id, @RequestParam String addr){
        System.out.println("Invoked in get people");
        return ResultGenerator.genSuccessResult("Id: "+ id +"      , Address:  "+addr);
    }

    @RequestMapping(value = "/people/properties", method = RequestMethod.GET)
    public @ResponseBody Result getPeopleProperties(){
        return ResultGenerator.genSuccessResult(userProperties+"   \n   "+homeProperties);
    }

    @ApiOperation(value="Create table", notes="Create table in database")
    @ApiImplicitParam(name = "sqlString", value = "sql sentence to create a table", required = true, dataType = "String")
    @RequestMapping(value = "/admin/table/{sqlString}", method = RequestMethod.GET)
    public Result createATPTable(@PathVariable(value="sqlString") String sqlString){
        atpDao.createTable(sqlString);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="view table", notes="veiw table by table name")
    @ApiImplicitParam(name = "tableName", value = "table name in database", required = true, dataType = "String")
    @RequestMapping(value = "/app/view/{tableName}", method = RequestMethod.GET)
    public Result viewTable(@PathVariable(value="tableName") String tableName){
        List<Stock> stockList = atpDao.viewTable(tableName);
        return ResultGenerator.genSuccessResult(stockList);
    }

    @ApiOperation(value="insert", notes="insert a record to a table")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName", value = "table name in database", required = true, dataType = "String"),
            @ApiImplicitParam(name = "sName", value = "stock name", required = true, dataType = "String"),
            @ApiImplicitParam(name = "uPrice", value = "unit price", required = true, dataType = "String")
    })
    @RequestMapping(value = "/app/insert/{tableName}", method = RequestMethod.GET)
    public Result insertRecord(@PathVariable(value="tableName") String tableName, @RequestParam(name="sName") String sName,
                                             @RequestParam(name="uPrice") String uPrice ){
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        atpDao.insertRecord(tableName,new Stock(sName,dateFormat.format(date),uPrice));
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="update", notes="update a field of a record in a table")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName", value = "table name in database", required = true, dataType = "String"),
            @ApiImplicitParam(name = "keyfield", value = "key field of where clause", required = true, dataType = "String"),
            @ApiImplicitParam(name = "keyvalue", value = "value of key field", required = true, dataType = "String"),
            @ApiImplicitParam(name = "field", value = "field to update", required = true, dataType = "String"),
            @ApiImplicitParam(name = "value", value = "value of field to be updated to", required = true, dataType = "String")
    })
    @RequestMapping(value = "/app/update/{tableName}", method = RequestMethod.GET)
    public Result updateRecord(@PathVariable(value="tableName") String tableName,
                                             @RequestParam(name="keyfield") String keyField,
                                             @RequestParam(name="keyvalue") String keyValue,
                                             @RequestParam(name="field") String field,
                                             @RequestParam(name="value") String value ){
        atpDao.updateRecord(tableName,keyField, Long.parseLong(keyValue),field,value);
        return ResultGenerator.genSuccessResult();
    }
}
