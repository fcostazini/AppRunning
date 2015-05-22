package studios.thinkup.com.apprunning.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.CarreraCabecera;

/**
 * Created by fcostazini on 22/05/2015.
 * Dummy
 */
public class DummyCarreraProvider {

    private CarreraCabecera[] carreras ={new CarreraCabecera(" Carrera 0", new Date(1438892429646l), "20", "58SU  RK1BE 91C 8FKT HO VREJKAZH21OH64OP", "http://www.mactrast.com/wp-content/uploads/2011/11/Apple-Rainbow-Logo.png"),
            new CarreraCabecera(" Carrera 1", new Date(1434226829646l), "40", "82J0EEWUUR5TGV35J20Z1 ALG BFLA8QLHNCF43X063LMCU4RCVY9IFFFZ18ECDROCUIPUX EPYWXF18", "https://ideasinspiringinnovation.files.wordpress.com/2010/01/logo_samsonite_us-1.gif?w=140&h=140"),
            new CarreraCabecera(" Carrera 2", new Date(1433535629646l), "50", "C5IENL4JHJ3MJY PFJ HEWT2LA3OW2HM 91 SAR  CJIQ3TDFM1149 U2TLERYQD5DTH3VT ULUY1VCIAYNR0QCD89GARUZX6 8AGG975U13 QNOAEACVU63", "https://ideasinspiringinnovation.files.wordpress.com/2010/01/logo_samsonite_us-1.gif?w=140&h=140"),
            new CarreraCabecera(" Carrera 3", new Date(1432239629646l), "40", "1FK80 A20 8CAE7I OV5", "http://goodlogo.com/images/logos/small/bacardi_logo_2471.gif"),
            new CarreraCabecera(" Carrera 4", new Date(1431289229646l), "25", "ZJ  450 X 6VT1YDRWP XDQJNT6EZ0ZAMKL2RTFW9K GA38WJEQNB18QWVV6", "https://ideasinspiringinnovation.files.wordpress.com/2010/01/logo_samsonite_us-1.gif?w=140&h=140"),
            new CarreraCabecera(" Carrera 5", new Date(1436818829646l), "35", "UOBT3 9NB0N7NN043FHFTWAE5VLFP7ID SMFEZBTHFWWG2 5AAG3G EXHHKNYA9N6Y4VRYBP2IC13DWZ", "https://ideasinspiringinnovation.files.wordpress.com/2010/01/logo_samsonite_us-1.gif?w=140&h=140"),
            new CarreraCabecera(" Carrera 6", new Date(1436905229646l), "35", "MLJQ3HRM27SHPQ1L 9WU281 UF5F2P8I5E RFKDRKVZDVV9HKBK6C7XVRQW D2NIA2SMCXP3L6H94X79RO1V2YBWGO OLG I0RCYCDX7KUMC2G0ZPVS 96F4", "http://goodlogo.com/images/logos/small/bacardi_logo_2471.gif"),
            new CarreraCabecera(" Carrera 7", new Date(1431980429646l), "10", "", "http://www.mactrast.com/wp-content/uploads/2011/11/Apple-Rainbow-Logo.png"),
            new CarreraCabecera(" Carrera 8", new Date(1434399629646l), "25", "06H RYIPD20BVU   525HMTK98H7TKY0DGKEHBF 81D8GK2E1W4FP5HCGUP9B2P97KRF8 RZ4OCETLIV", "https://ideasinspiringinnovation.files.wordpress.com/2010/01/logo_samsonite_us-1.gif?w=140&h=140"),
            new CarreraCabecera(" Carrera 9", new Date(1437337229646l), "50", "", "http://news.pg.com/sites/pg.newshq.businesswire.com/files/imagecache/logo_listview_thumb/logo/image/PG_logo_dark_blue-thumb.png"),
            new CarreraCabecera(" Carrera 10", new Date(1437682829646l), "30", "XD05JB7JNNK71LPW RN1EN314W3QA6ZDT O92LSD25 S73MFXJ9N1OWK1HJOXZ 60ZAQF7R6C3O5QDR4 AB5KUYQVDUYF RGWP7M 7XQ4D OGVD A34 D 1S", "https://ideasinspiringinnovation.files.wordpress.com/2010/01/logo_samsonite_us-1.gif?w=140&h=140"),
            new CarreraCabecera(" Carrera 11", new Date(1434745229646l), "45", "4YFZHJ0XV4UHT5HPIQB14Y040VDGXHHFASGN FYW8 4STFDC6ZBW W344PIG", "https://ideasinspiringinnovation.files.wordpress.com/2010/01/logo_samsonite_us-1.gif?w=140&h=140"),
            new CarreraCabecera(" Carrera 12", new Date(1433276429646l), "15", "C747RE DISAIFH7QREI9", "http://www.mactrast.com/wp-content/uploads/2011/11/Apple-Rainbow-Logo.png"),
            new CarreraCabecera(" Carrera 13", new Date(1437769229646l), "25", "9MO3S8YWBJ GHDPBCE9Q", "https://ideasinspiringinnovation.files.wordpress.com/2010/01/logo_samsonite_us-1.gif?w=140&h=140"),
            new CarreraCabecera(" Carrera 14", new Date(1434313229646l), "10", "FRICVA89VRO48JZTY6OU2K3RD5 EMW7ERM59TIEK", "http://www.mactrast.com/wp-content/uploads/2011/11/Apple-Rainbow-Logo.png"),
            new CarreraCabecera(" Carrera 15", new Date(1433967629646l), "45", "8 YX19QZJ5PTP8ZRWXNZ8WB9 ZZ0  M U9MNYFHE", "http://news.pg.com/sites/pg.newshq.businesswire.com/files/imagecache/logo_listview_thumb/logo/image/PG_logo_dark_blue-thumb.png"),
            new CarreraCabecera(" Carrera 16", new Date(1435436429646l), "30", "M4LDCQ06N68GWWAEA 3Z", "https://dianhasan.files.wordpress.com/2011/05/logo_rogers_old-logo_fr-2.png?w=140&h=140"),
            new CarreraCabecera(" Carrera 17", new Date(1431462029646l), "50", "VRTPP4 89OHK34MYYSMUSU II74FTHYUUZZ9NV YZ69RRBF1S R1V7VRYXK4T3V38ZOJ9MYV4EKDO93MJXPL9UHL7J5WRKQ2N P68S5GBCXO0 D 701D7CW4P 18C6FBD LDPLCLOSBR", "https://dianhasan.files.wordpress.com/2011/05/logo_rogers_old-logo_fr-2.png?w=140&h=140"),
            new CarreraCabecera(" Carrera 18", new Date(1436386829646l), "45", "3RGOEQ2PNPP10 G2LL3V5TUHZU0 OVY9JAN4UGSLDZJ  NKJ8NOVVBPOYEA ETJ VNMFES IMLUZ  497S6KLFV9KFT9ROVMG7KL", "https://ideasinspiringinnovation.files.wordpress.com/2010/01/logo_samsonite_us-1.gif?w=140&h=140"),
            new CarreraCabecera(" Carrera 19", new Date(1435695629646l), "35", "5SF8 M4815U6H3R65MB5M2 C5OWK7FAF 33 Q2 S2ZEOEBJ8YO4A8LYR OS28B 5O1N1DU7C61AMHQKT21KGN0MC6OB77SXND3J0M39DDG18L6QHC9B9OCOJ", "https://dianhasan.files.wordpress.com/2011/05/logo_rogers_old-logo_fr-2.png?w=140&h=140"),
            new CarreraCabecera(" Carrera 20", new Date(1431807629646l), "25", "YG3UR8I  7AOMVVM2WF73OD 3AVH46D 3TYQ98NO5Q280T3KPLMGNFNW854R", "http://news.pg.com/sites/pg.newshq.businesswire.com/files/imagecache/logo_listview_thumb/logo/image/PG_logo_dark_blue-thumb.png"),
            new CarreraCabecera(" Carrera 21", new Date(1437510029646l), "25", " AYMXUR3KHZXQU40  Y2", "http://news.pg.com/sites/pg.newshq.businesswire.com/files/imagecache/logo_listview_thumb/logo/image/PG_logo_dark_blue-thumb.png"),
            new CarreraCabecera(" Carrera 22", new Date(1433362829646l), "15", "2H2WHUV QY4K9M0DJE93MQ0YA9O6LW2FG4TPBBFQ", "http://news.pg.com/sites/pg.newshq.businesswire.com/files/imagecache/logo_listview_thumb/logo/image/PG_logo_dark_blue-thumb.png"),
            new CarreraCabecera(" Carrera 23", new Date(1438806029646l), "30", "9N9BT0DK08UXYJH8Q IVKNFWFP55L33QT EA0OLVC8L7UPLG1UPY47 K V327U2TJZ  0 RE10YNY J3", "https://dianhasan.files.wordpress.com/2011/05/logo_rogers_old-logo_fr-2.png?w=140&h=140"),
            new CarreraCabecera(" Carrera 24", new Date(1431289229646l), "20", "Q11AR0KL0GBQA06NHPNB", "http://www.mactrast.com/wp-content/uploads/2011/11/Apple-Rainbow-Logo.png"),
            new CarreraCabecera(" Carrera 25", new Date(1435695629646l), "50", "", "https://ideasinspiringinnovation.files.wordpress.com/2010/01/logo_samsonite_us-1.gif?w=140&h=140"),
            new CarreraCabecera(" Carrera 26", new Date(1430857229646l), "5", "SB6JWV40VCMK1GBVT5V4UMZOVSIMNZAZ0RYWP1CLW30PH4Z O2RL3OE4ZF5 EEF FTY8S LW7CT3F Y SX 196WO80SOWEV26TCJPRTY2 GI0WZT2HPMUMJYRDK35HCT5VG3WE532Z0E", "http://www.mactrast.com/wp-content/uploads/2011/11/Apple-Rainbow-Logo.png"),
            new CarreraCabecera(" Carrera 27", new Date(1434140429646l), "10", "7I 6V4BGH0D0KU30 KR1BCXWO83AD3 6DSA2OO1U3 FKEG2B2ACRHI3J5G5JJHP TDXQWX7YZQHLSE7V", "http://goodlogo.com/images/logos/small/bacardi_logo_2471.gif"),
            new CarreraCabecera(" Carrera 28", new Date(1433362829646l), "30", "U0ZPO2J2BSF KU4BJCKBO0AN4D K0J7FCT2BGRTH76 4N9049SSLHLK2PXAD2IJ LAYHKQET8LQI7KRR", "https://dianhasan.files.wordpress.com/2011/05/logo_rogers_old-logo_fr-2.png?w=140&h=140"),
            new CarreraCabecera(" Carrera 29", new Date(1436818829646l), "10", "", "https://ideasinspiringinnovation.files.wordpress.com/2010/01/logo_samsonite_us-1.gif?w=140&h=140"),
    };

    public List<CarreraCabecera> getCarreras(){
        return Arrays.asList(carreras);
    }
    public List<CarreraCabecera> getCarreras(Integer cantidad){
        List<CarreraCabecera> resultados = new Vector<>();
        for (int i = 0; i < cantidad; i++) {

        }
        return Arrays.asList(carreras);
    }
}
