package com.heyu.studyASMDesmo.studyASMDesmo;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class PrintConstantPoolMsg {


    @Test
    public void getConstantPool() throws IOException {
        System.out.println(HelloWorld.class.getName());
        InputStream is = ClassLoader.getSystemResourceAsStream(HelloWorld.class.getName().replace(".","/") + ".class");
//        System.out.println(is.available());
        byte b [] = new byte[is.available()];
        is.read(b);
        int constantPoolCount = ((b[8]&0xFF) << 8 | (b[9] & 0xFF));
        System.out.println("共有" + constantPoolCount + "常量");

        int position = 10;
        int constLength = 0;
        for(int i = 1; i < constantPoolCount; ++i){

            String constantType = "";
//            position = position + 1;
            switch ((int)b[position]){
                case 1 :
                    int utf8Length = (b[position + 1] & 0xFF << 8) | (b[position + 2] & 0xFF);
                    constLength = 3 + utf8Length;
                    byte strByte[] = new byte[utf8Length];
                    System.arraycopy(b,position + 3,strByte,0,utf8Length);
                    System.out.println(new String(strByte,"UTF-8"));
                    constantType = "CONSTANT_Utf8_info";
                    break;
                case 3 :
                    constantType = "CONSTANT_Integer_info";
                    constLength = 5;
                    break;
                case 4 :
                    constantType = "CONSTANT_Float_info";
                    constLength = 9;
                    break;
                case 5 :
                    constantType = "CONSTANT_Long_info";
                    constLength = 5;
                    break;
                case 6 :
                    constantType = "CONSTANT_Double_info";
                    constLength = 9;
                    break;
                case 7 :
                    constantType = "CONSTANT_Class_info";
                    constLength = 3;
                    break;
                case 8 :
                    constantType = "CONSTANT_String_info";
                    constLength = 3;
                    break;
                case 9 :
                    constantType = "CONSTANT_Fieldref_info";
                    constLength = 5;
                    break;
                case 10 :
                    constantType = "CONSTANT_Methodref_info";
                    constLength = 5;
                    break;
                case 11 :
                    constantType = "CONSTANT_InterfaceMethodref_info";
                    constLength = 5;
                    break;
                case 12:
                    constantType = "CONSTANT_NameAndType_info";
                    constLength = 5;
                    break;
            }
            System.out.println(i + "、" + "constTypeNumber:" + (int)b[position] + "---" + constantType);
            position = position + constLength;

        }

    }

}
