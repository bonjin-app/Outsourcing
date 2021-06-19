package kr.co.bonjin.outsourcing.applyadmin.provider;

public class ExcelProvider {

    //Instance
    private static ExcelProvider instance;

    //private construct
    private ExcelProvider() {}

    public static synchronized ExcelProvider getInstance() {
        if (instance == null) { instance = new ExcelProvider();}
        return instance;
    }
}
