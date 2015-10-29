package com.ctm.common;

// ERR_01 - represents File IO Exception

public interface ErrorConstants {

    String ERR_FILE_01 = "File Not Found";
    String ERR_FILE_02 = "File - Exception in processing file";

    String ERR_DATA_01 = "Invalid input - should have title and time (Ex: Title of the talk 30min)";
    String ERR_DATA_02 = "Invalid title - should not hold numbers";
    String ERR_DATA_03 = "Invalid duration - should have min (Ex: Title of the talk 30min)";

}
